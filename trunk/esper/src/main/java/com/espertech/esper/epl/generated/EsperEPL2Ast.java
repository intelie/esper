// $ANTLR 3.1.1 EsperEPL2Ast.g 2008-11-30 22:13:31

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "UNIDIRECTIONAL", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "EVENT_FILTER_EXPR", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "INSERTINTO_EXPRCOL", "CONCAT", "LIB_FUNCTION", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_WINDOW_COL_TYPE_LIST", "CREATE_WINDOW_COL_TYPE", "NUMBERSETSTAR", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "IDENT", "COMMA", "EQUALS", "DOT", "LPAREN", "RPAREN", "STAR", "LBRACK", "RBRACK", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BOR", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "PLUS", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "QUESTION", "ESCAPECHAR", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=144;
    public static final int FLOAT_SUFFIX=269;
    public static final int STAR=211;
    public static final int NUMERIC_PARAM_LIST=95;
    public static final int ISTREAM=57;
    public static final int MOD=230;
    public static final int OUTERJOIN_EXPR=127;
    public static final int BSR=251;
    public static final int LIB_FUNCTION=150;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=88;
    public static final int FULL_OUTERJOIN_EXPR=131;
    public static final int RPAREN=210;
    public static final int LNOT=240;
    public static final int INC=244;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=215;
    public static final int BSR_ASSIGN=252;
    public static final int STREAM_EXPR=126;
    public static final int CAST_EXPR=178;
    public static final int TIMEPERIOD_SECONDS=85;
    public static final int NOT_EQUAL=221;
    public static final int METADATASQL=62;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=98;
    public static final int FOLLOWED_BY=234;
    public static final int HOUR_PART=155;
    public static final int RBRACK=213;
    public static final int MATCH_UNTIL_RANGE_CLOSED=191;
    public static final int GE=225;
    public static final int METHOD_JOIN_EXPR=187;
    public static final int ASC=54;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=100;
    public static final int MINUS_ASSIGN=245;
    public static final int ELSE=29;
    public static final int EVENT_FILTER_NOT_IN=106;
    public static final int NUM_DOUBLE=203;
    public static final int INSERTINTO_STREAM_NAME=167;
    public static final int UNARY_MINUS=151;
    public static final int LCURLY=231;
    public static final int TIMEPERIOD_MILLISEC=86;
    public static final int DBWHERE_CLAUSE=165;
    public static final int MEDIAN=22;
    public static final int EVENTS=48;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=104;
    public static final int GROUP=43;
    public static final int EMAILAT=260;
    public static final int WS=261;
    public static final int ESCAPECHAR=236;
    public static final int SL_COMMENT=262;
    public static final int NULL_TYPE=202;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=189;
    public static final int GT=223;
    public static final int BNOT=241;
    public static final int WHERE_EXPR=115;
    public static final int END=32;
    public static final int INNERJOIN_EXPR=128;
    public static final int LAND=258;
    public static final int NOT_REGEXP=162;
    public static final int MATCH_UNTIL_EXPR=188;
    public static final int EVENT_PROP_EXPR=135;
    public static final int LBRACK=212;
    public static final int VIEW_EXPR=112;
    public static final int LONG_TYPE=197;
    public static final int TIMEPERIOD_SEC=83;
    public static final int ON_SELECT_EXPR=183;
    public static final int MINUTE_PART=156;
    public static final int SUM=17;
    public static final int SQL_NE=220;
    public static final int HexDigit=267;
    public static final int LPAREN=209;
    public static final int IN_SUBSELECT_EXPR=172;
    public static final int AT=76;
    public static final int AS=16;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=89;
    public static final int THEN=31;
    public static final int NOT_IN_RANGE=169;
    public static final int OFFSET=93;
    public static final int LEFT=37;
    public static final int AVG=18;
    public static final int SECOND_PART=157;
    public static final int PREVIOUS=63;
    public static final int DATABASE_JOIN_EXPR=114;
    public static final int IDENT=205;
    public static final int BXOR=219;
    public static final int PLUS=227;
    public static final int CASE2=28;
    public static final int TIMEPERIOD_DAY=77;
    public static final int EXISTS=65;
    public static final int EVENT_PROP_INDEXED=138;
    public static final int TIMEPERIOD_MILLISECOND=87;
    public static final int EVAL_NOTEQUALS_EXPR=121;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=190;
    public static final int CREATE_VARIABLE_EXPR=186;
    public static final int CREATE_WINDOW_COL_TYPE=194;
    public static final int LIKE=8;
    public static final int OUTER=34;
    public static final int BY=42;
    public static final int ARRAY_PARAM_LIST=99;
    public static final int RIGHT_OUTERJOIN_EXPR=130;
    public static final int NUMBERSETSTAR=195;
    public static final int LAST_OPERATOR=175;
    public static final int EVAL_AND_EXPR=118;
    public static final int LEFT_OUTERJOIN_EXPR=129;
    public static final int EPL_EXPR=204;
    public static final int GROUP_BY_EXPR=132;
    public static final int SET=73;
    public static final int RIGHT=38;
    public static final int HAVING=44;
    public static final int INSTANCEOF=68;
    public static final int MIN=20;
    public static final int EVENT_PROP_SIMPLE=136;
    public static final int MINUS=228;
    public static final int SEMI=259;
    public static final int STAR_ASSIGN=247;
    public static final int COLON=214;
    public static final int BAND_ASSIGN=257;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=145;
    public static final int VALUE_NULL=91;
    public static final int NOT_IN_SET=159;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=139;
    public static final int SL=253;
    public static final int WHEN=30;
    public static final int NOT_IN_SUBSELECT_EXPR=173;
    public static final int GUARD_EXPR=110;
    public static final int SR=249;
    public static final int RCURLY=232;
    public static final int PLUS_ASSIGN=243;
    public static final int DAY_PART=154;
    public static final int EXISTS_SUBSELECT_EXPR=171;
    public static final int EVENT_FILTER_IN=105;
    public static final int DIV=229;
    public static final int OBJECT_PARAM_ORDERED_EXPR=97;
    public static final int OctalEscape=266;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=158;
    public static final int PRIOR=64;
    public static final int FIRST=49;
    public static final int ROW_LIMIT_EXPR=92;
    public static final int SELECTION_EXPR=123;
    public static final int LOR=226;
    public static final int CAST=69;
    public static final int LW=67;
    public static final int WILDCARD_SELECT=166;
    public static final int EXPONENT=268;
    public static final int LT=222;
    public static final int PATTERN_INCL_EXPR=113;
    public static final int ORDER_BY_EXPR=133;
    public static final int BOOL_TYPE=201;
    public static final int MOD_ASSIGN=248;
    public static final int CASE=27;
    public static final int IN_SUBSELECT_QUERY_EXPR=174;
    public static final int EQUALS=207;
    public static final int COUNT=25;
    public static final int DIV_ASSIGN=242;
    public static final int SL_ASSIGN=254;
    public static final int PATTERN=60;
    public static final int SQL=61;
    public static final int WEEKDAY=66;
    public static final int FULL=39;
    public static final int INSERT=51;
    public static final int ESCAPE=10;
    public static final int ARRAY_EXPR=153;
    public static final int LAST=50;
    public static final int BOOLEAN_FALSE=90;
    public static final int SELECT=26;
    public static final int INTO=52;
    public static final int FLOAT_TYPE=198;
    public static final int TIMEPERIOD_SECOND=84;
    public static final int COALESCE=21;
    public static final int EVENT_FILTER_BETWEEN=107;
    public static final int SUBSELECT_EXPR=170;
    public static final int NUMERIC_PARAM_RANGE=94;
    public static final int CONCAT=149;
    public static final int CLASS_IDENT=109;
    public static final int ON_EXPR=181;
    public static final int CREATE_WINDOW_EXPR=179;
    public static final int ON_DELETE_EXPR=182;
    public static final int NUM_LONG=237;
    public static final int ON=40;
    public static final int TIME_PERIOD=152;
    public static final int DOUBLE_TYPE=199;
    public static final int DELETE=71;
    public static final int INT_TYPE=196;
    public static final int EVAL_BITWISE_EXPR=117;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=134;
    public static final int TIMEPERIOD_HOURS=80;
    public static final int VARIABLE=74;
    public static final int SUBSTITUTION=177;
    public static final int UNTIL=75;
    public static final int STRING_TYPE=200;
    public static final int ON_SET_EXPR=185;
    public static final int NUM_INT=233;
    public static final int STDDEV=23;
    public static final int ON_EXPR_FROM=184;
    public static final int NUM_FLOAT=238;
    public static final int FROM=33;
    public static final int DISTINCT=45;
    public static final int OUTPUT=47;
    public static final int EscapeSequence=264;
    public static final int WEEKDAY_OPERATOR=176;
    public static final int WHERE=15;
    public static final int CREATE_WINDOW_COL_TYPE_LIST=193;
    public static final int DEC=246;
    public static final int INNER=35;
    public static final int NUMERIC_PARAM_FREQUENCY=96;
    public static final int BXOR_ASSIGN=255;
    public static final int ORDER=53;
    public static final int SNAPSHOT=72;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=141;
    public static final int EVENT_FILTER_PARAM=102;
    public static final int IRSTREAM=58;
    public static final int MAX=19;
    public static final int TIMEPERIOD_DAYS=78;
    public static final int EVENT_FILTER_RANGE=103;
    public static final int ML_COMMENT=263;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=140;
    public static final int BOR_ASSIGN=256;
    public static final int COMMA=206;
    public static final int WHEN_LIMIT_EXPR=146;
    public static final int IS=41;
    public static final int TIMEPERIOD_LIMIT_EXPR=143;
    public static final int ALL=46;
    public static final int TIMEPERIOD_HOUR=79;
    public static final int BOR=218;
    public static final int EQUAL=239;
    public static final int EVENT_FILTER_NOT_BETWEEN=108;
    public static final int IN_RANGE=168;
    public static final int DOT=208;
    public static final int CURRENT_TIMESTAMP=70;
    public static final int INSERTINTO_EXPR=147;
    public static final int HAVING_EXPR=116;
    public static final int UNIDIRECTIONAL=59;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=192;
    public static final int EVAL_EQUALS_EXPR=120;
    public static final int TIMEPERIOD_MINUTES=82;
    public static final int RSTREAM=56;
    public static final int NOT_LIKE=161;
    public static final int EVENT_LIMIT_EXPR=142;
    public static final int NOT_BETWEEN=160;
    public static final int TIMEPERIOD_MINUTE=81;
    public static final int EVAL_OR_EXPR=119;
    public static final int BAND=217;
    public static final int QUOTED_STRING_LITERAL=216;
    public static final int JOIN=36;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=235;
    public static final int OBSERVER_EXPR=111;
    public static final int EVENT_FILTER_IDENT=101;
    public static final int EVENT_PROP_MAPPED=137;
    public static final int UnicodeEscape=265;
    public static final int AVEDEV=24;
    public static final int DBSELECT_EXPR=163;
    public static final int SELECTION_ELEMENT_EXPR=124;
    public static final int CREATE_WINDOW_SELECT_EXPR=180;
    public static final int INSERTINTO_EXPRCOL=148;
    public static final int WINDOW=5;
    public static final int DESC=55;
    public static final int SELECTION_STREAM=125;
    public static final int SR_ASSIGN=250;
    public static final int DBFROM_CLAUSE=164;
    public static final int LE=224;
    public static final int EVAL_IDENT=122;

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
    // EsperEPL2Ast.g:180:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:181:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ) )
            // EsperEPL2Ast.g:181:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? )
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
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression997); 

             setIsPatternWalk(true); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression1001);
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
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1018); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression1020); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:189:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( ((LA50_0>=STRING_LITERAL && LA50_0<=QUOTED_STRING_LITERAL)) ) {
                alt50=1;
            }
            switch (alt50) {
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
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1051); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression1053); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression1055); 
            // EsperEPL2Ast.g:193:41: ( valueExpr )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( ((LA51_0>=IN_SET && LA51_0<=REGEXP)||LA51_0==NOT_EXPR||(LA51_0>=SUM && LA51_0<=AVG)||(LA51_0>=COALESCE && LA51_0<=COUNT)||(LA51_0>=CASE && LA51_0<=CASE2)||(LA51_0>=PREVIOUS && LA51_0<=EXISTS)||(LA51_0>=INSTANCEOF && LA51_0<=CURRENT_TIMESTAMP)||(LA51_0>=EVAL_AND_EXPR && LA51_0<=EVAL_NOTEQUALS_EXPR)||LA51_0==EVENT_PROP_EXPR||(LA51_0>=CONCAT && LA51_0<=LIB_FUNCTION)||LA51_0==ARRAY_EXPR||(LA51_0>=NOT_IN_SET && LA51_0<=NOT_REGEXP)||(LA51_0>=IN_RANGE && LA51_0<=NOT_IN_SUBSELECT_EXPR)||LA51_0==SUBSTITUTION||(LA51_0>=INT_TYPE && LA51_0<=NULL_TYPE)||LA51_0==STAR||(LA51_0>=BAND && LA51_0<=BXOR)||(LA51_0>=LT && LA51_0<=GE)||(LA51_0>=PLUS && LA51_0<=MOD)) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // EsperEPL2Ast.g:193:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression1058);
            	    valueExpr();

            	    state._fsp--;


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
    // $ANTLR end "methodJoinExpression"


    // $ANTLR start "viewListExpr"
    // EsperEPL2Ast.g:196:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:197:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:197:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr1072);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:197:13: ( viewExpr )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==VIEW_EXPR) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // EsperEPL2Ast.g:197:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr1075);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop52;
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
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr1092); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1094); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1096); 
            // EsperEPL2Ast.g:201:30: ( valueExprWithTime )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( ((LA53_0>=IN_SET && LA53_0<=REGEXP)||LA53_0==NOT_EXPR||(LA53_0>=SUM && LA53_0<=AVG)||(LA53_0>=COALESCE && LA53_0<=COUNT)||(LA53_0>=CASE && LA53_0<=CASE2)||LA53_0==LAST||(LA53_0>=PREVIOUS && LA53_0<=EXISTS)||(LA53_0>=LW && LA53_0<=CURRENT_TIMESTAMP)||(LA53_0>=NUMERIC_PARAM_RANGE && LA53_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA53_0>=EVAL_AND_EXPR && LA53_0<=EVAL_NOTEQUALS_EXPR)||LA53_0==EVENT_PROP_EXPR||(LA53_0>=CONCAT && LA53_0<=LIB_FUNCTION)||(LA53_0>=TIME_PERIOD && LA53_0<=ARRAY_EXPR)||(LA53_0>=NOT_IN_SET && LA53_0<=NOT_REGEXP)||(LA53_0>=IN_RANGE && LA53_0<=NOT_IN_SUBSELECT_EXPR)||(LA53_0>=LAST_OPERATOR && LA53_0<=SUBSTITUTION)||(LA53_0>=NUMBERSETSTAR && LA53_0<=NULL_TYPE)||LA53_0==STAR||(LA53_0>=BAND && LA53_0<=BXOR)||(LA53_0>=LT && LA53_0<=GE)||(LA53_0>=PLUS && LA53_0<=MOD)) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // EsperEPL2Ast.g:201:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr1099);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop53;
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
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause1120); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause1122);
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
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause1140); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause1142);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:209:32: ( valueExpr )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( ((LA54_0>=IN_SET && LA54_0<=REGEXP)||LA54_0==NOT_EXPR||(LA54_0>=SUM && LA54_0<=AVG)||(LA54_0>=COALESCE && LA54_0<=COUNT)||(LA54_0>=CASE && LA54_0<=CASE2)||(LA54_0>=PREVIOUS && LA54_0<=EXISTS)||(LA54_0>=INSTANCEOF && LA54_0<=CURRENT_TIMESTAMP)||(LA54_0>=EVAL_AND_EXPR && LA54_0<=EVAL_NOTEQUALS_EXPR)||LA54_0==EVENT_PROP_EXPR||(LA54_0>=CONCAT && LA54_0<=LIB_FUNCTION)||LA54_0==ARRAY_EXPR||(LA54_0>=NOT_IN_SET && LA54_0<=NOT_REGEXP)||(LA54_0>=IN_RANGE && LA54_0<=NOT_IN_SUBSELECT_EXPR)||LA54_0==SUBSTITUTION||(LA54_0>=INT_TYPE && LA54_0<=NULL_TYPE)||LA54_0==STAR||(LA54_0>=BAND && LA54_0<=BXOR)||(LA54_0>=LT && LA54_0<=GE)||(LA54_0>=PLUS && LA54_0<=MOD)) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // EsperEPL2Ast.g:209:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause1145);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop54;
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
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause1163); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause1165);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:213:35: ( orderByElement )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==ORDER_ELEMENT_EXPR) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // EsperEPL2Ast.g:213:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause1168);
            	    orderByElement();

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
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1188); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement1190);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:217:38: ( ASC | DESC )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( ((LA56_0>=ASC && LA56_0<=DESC)) ) {
                alt56=1;
            }
            switch (alt56) {
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
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause1215); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause1217);
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
            int alt63=4;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt63=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt63=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt63=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
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
                    // EsperEPL2Ast.g:225:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1235); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:225:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt57=2;
                    int LA57_0 = input.LA(1);

                    if ( (LA57_0==ALL||(LA57_0>=FIRST && LA57_0<=LAST)||LA57_0==SNAPSHOT) ) {
                        alt57=1;
                    }
                    switch (alt57) {
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
                    int alt58=2;
                    int LA58_0 = input.LA(1);

                    if ( ((LA58_0>=INT_TYPE && LA58_0<=DOUBLE_TYPE)) ) {
                        alt58=1;
                    }
                    else if ( (LA58_0==IDENT) ) {
                        alt58=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 58, 0, input);

                        throw nvae;
                    }
                    switch (alt58) {
                        case 1 :
                            // EsperEPL2Ast.g:225:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr1249);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:225:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr1251); 

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
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1268); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:226:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==ALL||(LA59_0>=FIRST && LA59_0<=LAST)||LA59_0==SNAPSHOT) ) {
                        alt59=1;
                    }
                    switch (alt59) {
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr1281);
                    timePeriod();

                    state._fsp--;

                     leaveNode(tp); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:227:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1296); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:227:33: ( ALL | FIRST | LAST | SNAPSHOT )?
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr1309);
                    crontabLimitParameterSet();

                    state._fsp--;

                     leaveNode(cron); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:228:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr1324); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:228:30: ( ALL | FIRST | LAST | SNAPSHOT )?
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr1337);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:228:67: ( onSetExpr )?
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==ON_SET_EXPR) ) {
                        alt62=1;
                    }
                    switch (alt62) {
                        case 1 :
                            // EsperEPL2Ast.g:228:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr1339);
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
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause1358); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:232:23: ( number | IDENT )
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( ((LA64_0>=INT_TYPE && LA64_0<=DOUBLE_TYPE)) ) {
                alt64=1;
            }
            else if ( (LA64_0==IDENT) ) {
                alt64=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }
            switch (alt64) {
                case 1 :
                    // EsperEPL2Ast.g:232:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause1361);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:232:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause1363); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:232:38: ( number | IDENT )?
            int alt65=3;
            int LA65_0 = input.LA(1);

            if ( ((LA65_0>=INT_TYPE && LA65_0<=DOUBLE_TYPE)) ) {
                alt65=1;
            }
            else if ( (LA65_0==IDENT) ) {
                alt65=2;
            }
            switch (alt65) {
                case 1 :
                    // EsperEPL2Ast.g:232:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause1367);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:232:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause1369); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:232:54: ( COMMA )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==COMMA) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // EsperEPL2Ast.g:232:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause1373); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:232:61: ( OFFSET )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==OFFSET) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // EsperEPL2Ast.g:232:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause1376); 

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
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet1394); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1396);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1398);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1400);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1402);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1404);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:236:121: ( valueExprWithTime )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( ((LA68_0>=IN_SET && LA68_0<=REGEXP)||LA68_0==NOT_EXPR||(LA68_0>=SUM && LA68_0<=AVG)||(LA68_0>=COALESCE && LA68_0<=COUNT)||(LA68_0>=CASE && LA68_0<=CASE2)||LA68_0==LAST||(LA68_0>=PREVIOUS && LA68_0<=EXISTS)||(LA68_0>=LW && LA68_0<=CURRENT_TIMESTAMP)||(LA68_0>=NUMERIC_PARAM_RANGE && LA68_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA68_0>=EVAL_AND_EXPR && LA68_0<=EVAL_NOTEQUALS_EXPR)||LA68_0==EVENT_PROP_EXPR||(LA68_0>=CONCAT && LA68_0<=LIB_FUNCTION)||(LA68_0>=TIME_PERIOD && LA68_0<=ARRAY_EXPR)||(LA68_0>=NOT_IN_SET && LA68_0<=NOT_REGEXP)||(LA68_0>=IN_RANGE && LA68_0<=NOT_IN_SUBSELECT_EXPR)||(LA68_0>=LAST_OPERATOR && LA68_0<=SUBSTITUTION)||(LA68_0>=NUMBERSETSTAR && LA68_0<=NULL_TYPE)||LA68_0==STAR||(LA68_0>=BAND && LA68_0<=BXOR)||(LA68_0>=LT && LA68_0<=GE)||(LA68_0>=PLUS && LA68_0<=MOD)) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // EsperEPL2Ast.g:236:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1406);
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
            int alt69=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt69=1;
                }
                break;
            case GT:
                {
                alt69=2;
                }
                break;
            case LE:
                {
                alt69=3;
                }
                break;
            case GE:
                {
                alt69=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }

            switch (alt69) {
                case 1 :
                    // EsperEPL2Ast.g:240:5: ^(n= LT valueExpr valueExpr )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr1423); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1425);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1427);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(n); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:241:5: ^(n= GT valueExpr valueExpr )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr1439); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1441);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1443);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(n); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:242:5: ^(n= LE valueExpr valueExpr )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr1455); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1457);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1459);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(n); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:243:4: ^(n= GE valueExpr valueExpr )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr1470); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1472);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1474);
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
            int alt72=6;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt72=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt72=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt72=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt72=4;
                }
                break;
            case NOT_EXPR:
                {
                alt72=5;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt72=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }

            switch (alt72) {
                case 1 :
                    // EsperEPL2Ast.g:247:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice1491); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1493);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1495);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:247:42: ( valueExpr )*
                    loop70:
                    do {
                        int alt70=2;
                        int LA70_0 = input.LA(1);

                        if ( ((LA70_0>=IN_SET && LA70_0<=REGEXP)||LA70_0==NOT_EXPR||(LA70_0>=SUM && LA70_0<=AVG)||(LA70_0>=COALESCE && LA70_0<=COUNT)||(LA70_0>=CASE && LA70_0<=CASE2)||(LA70_0>=PREVIOUS && LA70_0<=EXISTS)||(LA70_0>=INSTANCEOF && LA70_0<=CURRENT_TIMESTAMP)||(LA70_0>=EVAL_AND_EXPR && LA70_0<=EVAL_NOTEQUALS_EXPR)||LA70_0==EVENT_PROP_EXPR||(LA70_0>=CONCAT && LA70_0<=LIB_FUNCTION)||LA70_0==ARRAY_EXPR||(LA70_0>=NOT_IN_SET && LA70_0<=NOT_REGEXP)||(LA70_0>=IN_RANGE && LA70_0<=NOT_IN_SUBSELECT_EXPR)||LA70_0==SUBSTITUTION||(LA70_0>=INT_TYPE && LA70_0<=NULL_TYPE)||LA70_0==STAR||(LA70_0>=BAND && LA70_0<=BXOR)||(LA70_0>=LT && LA70_0<=GE)||(LA70_0>=PLUS && LA70_0<=MOD)) ) {
                            alt70=1;
                        }


                        switch (alt70) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:247:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1498);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop70;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:248:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice1512); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1514);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1516);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:248:43: ( valueExpr )*
                    loop71:
                    do {
                        int alt71=2;
                        int LA71_0 = input.LA(1);

                        if ( ((LA71_0>=IN_SET && LA71_0<=REGEXP)||LA71_0==NOT_EXPR||(LA71_0>=SUM && LA71_0<=AVG)||(LA71_0>=COALESCE && LA71_0<=COUNT)||(LA71_0>=CASE && LA71_0<=CASE2)||(LA71_0>=PREVIOUS && LA71_0<=EXISTS)||(LA71_0>=INSTANCEOF && LA71_0<=CURRENT_TIMESTAMP)||(LA71_0>=EVAL_AND_EXPR && LA71_0<=EVAL_NOTEQUALS_EXPR)||LA71_0==EVENT_PROP_EXPR||(LA71_0>=CONCAT && LA71_0<=LIB_FUNCTION)||LA71_0==ARRAY_EXPR||(LA71_0>=NOT_IN_SET && LA71_0<=NOT_REGEXP)||(LA71_0>=IN_RANGE && LA71_0<=NOT_IN_SUBSELECT_EXPR)||LA71_0==SUBSTITUTION||(LA71_0>=INT_TYPE && LA71_0<=NULL_TYPE)||LA71_0==STAR||(LA71_0>=BAND && LA71_0<=BXOR)||(LA71_0>=LT && LA71_0<=GE)||(LA71_0>=PLUS && LA71_0<=MOD)) ) {
                            alt71=1;
                        }


                        switch (alt71) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:248:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1519);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop71;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:249:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice1533); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1535);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1537);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:250:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice1549); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1551);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1553);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:251:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice1565); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1567);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:252:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice1578);
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
            int alt73=16;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt73=1;
                }
                break;
            case SUBSTITUTION:
                {
                alt73=2;
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
                alt73=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt73=4;
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
                alt73=5;
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
                alt73=6;
                }
                break;
            case LIB_FUNCTION:
                {
                alt73=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt73=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt73=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt73=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt73=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt73=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt73=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt73=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt73=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt73=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 73, 0, input);

                throw nvae;
            }

            switch (alt73) {
                case 1 :
                    // EsperEPL2Ast.g:256:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr1591);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:257:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr1597);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:258:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr1603);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:259:5: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr1610);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:260:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr1618);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:261:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr1623);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:262:7: libFunc
                    {
                    pushFollow(FOLLOW_libFunc_in_valueExpr1631);
                    libFunc();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:263:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr1636);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:264:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr1641);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:265:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr1647);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:266:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr1652);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:267:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr1657);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:268:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr1662);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:269:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr1667);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:270:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr1673);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:271:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr1680);
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
            int alt75=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt75=1;
                }
                break;
            case LW:
                {
                alt75=2;
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
                alt75=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt75=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt75=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt75=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt75=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt75=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt75=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt75=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt75=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }

            switch (alt75) {
                case 1 :
                    // EsperEPL2Ast.g:275:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime1693); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:276:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime1702); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:277:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime1709);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:278:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime1717); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime1719);
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
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime1734);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:280:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime1740);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:281:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime1745);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:282:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime1750);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:283:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime1760); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:283:29: ( numericParameterList )+
                    int cnt74=0;
                    loop74:
                    do {
                        int alt74=2;
                        int LA74_0 = input.LA(1);

                        if ( (LA74_0==NUMERIC_PARAM_RANGE||LA74_0==NUMERIC_PARAM_FREQUENCY||(LA74_0>=INT_TYPE && LA74_0<=NULL_TYPE)) ) {
                            alt74=1;
                        }


                        switch (alt74) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:283:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime1762);
                    	    numericParameterList();

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

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:284:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime1773); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:285:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime1780);
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
            int alt76=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt76=1;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt76=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt76=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }

            switch (alt76) {
                case 1 :
                    // EsperEPL2Ast.g:289:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList1793);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:290:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList1800);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:291:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList1806);
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
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator1822); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:295:29: ( constant[true] | eventPropertyExpr | substitution )
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
            case EVENT_PROP_EXPR:
                {
                alt77=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:295:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator1825);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:295:45: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator1828);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:295:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator1830);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:295:77: ( constant[true] | eventPropertyExpr | substitution )
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
                    // EsperEPL2Ast.g:295:78: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator1834);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:295:93: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator1837);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:295:111: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator1839);
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
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator1860); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:299:33: ( constant[true] | eventPropertyExpr | substitution )
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
                    // EsperEPL2Ast.g:299:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator1863);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:299:49: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator1866);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:299:67: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator1868);
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
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator1887); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:303:23: ( constant[true] | eventPropertyExpr | substitution )
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
                    // EsperEPL2Ast.g:303:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator1890);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:303:39: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator1893);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:303:57: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator1895);
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
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator1914); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:307:26: ( constant[true] | eventPropertyExpr | substitution )
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
                    // EsperEPL2Ast.g:307:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator1917);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:307:42: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator1920);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:307:60: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator1922);
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
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr1943); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr1945);
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
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr1964); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr1966);
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
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==IN_SUBSELECT_EXPR) ) {
                alt82=1;
            }
            else if ( (LA82_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt82=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }
            switch (alt82) {
                case 1 :
                    // EsperEPL2Ast.g:319:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr1985); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr1987);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr1989);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:320:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2001); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2003);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2005);
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
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2024); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr2026);
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
            pushFollow(FOLLOW_selectionListElement_in_subQueryExpr2042);
            selectionListElement();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr2044);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:328:45: ( viewExpr )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==VIEW_EXPR) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // EsperEPL2Ast.g:328:46: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_subQueryExpr2047);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop83;
                }
            } while (true);

            // EsperEPL2Ast.g:328:57: ( IDENT )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==IDENT) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // EsperEPL2Ast.g:328:58: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subQueryExpr2052); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:328:66: ( whereClause )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==WHERE_EXPR) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // EsperEPL2Ast.g:328:67: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr2057);
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
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2074); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr2076);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:332:36: ( viewListExpr )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==VIEW_EXPR) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // EsperEPL2Ast.g:332:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr2079);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:332:52: ( IDENT )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==IDENT) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // EsperEPL2Ast.g:332:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr2084); 

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
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==CASE) ) {
                alt90=1;
            }
            else if ( (LA90_0==CASE2) ) {
                alt90=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;
            }
            switch (alt90) {
                case 1 :
                    // EsperEPL2Ast.g:336:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr2105); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:336:13: ( valueExpr )*
                        loop88:
                        do {
                            int alt88=2;
                            int LA88_0 = input.LA(1);

                            if ( ((LA88_0>=IN_SET && LA88_0<=REGEXP)||LA88_0==NOT_EXPR||(LA88_0>=SUM && LA88_0<=AVG)||(LA88_0>=COALESCE && LA88_0<=COUNT)||(LA88_0>=CASE && LA88_0<=CASE2)||(LA88_0>=PREVIOUS && LA88_0<=EXISTS)||(LA88_0>=INSTANCEOF && LA88_0<=CURRENT_TIMESTAMP)||(LA88_0>=EVAL_AND_EXPR && LA88_0<=EVAL_NOTEQUALS_EXPR)||LA88_0==EVENT_PROP_EXPR||(LA88_0>=CONCAT && LA88_0<=LIB_FUNCTION)||LA88_0==ARRAY_EXPR||(LA88_0>=NOT_IN_SET && LA88_0<=NOT_REGEXP)||(LA88_0>=IN_RANGE && LA88_0<=NOT_IN_SUBSELECT_EXPR)||LA88_0==SUBSTITUTION||(LA88_0>=INT_TYPE && LA88_0<=NULL_TYPE)||LA88_0==STAR||(LA88_0>=BAND && LA88_0<=BXOR)||(LA88_0>=LT && LA88_0<=GE)||(LA88_0>=PLUS && LA88_0<=MOD)) ) {
                                alt88=1;
                            }


                            switch (alt88) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:336:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2108);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop88;
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
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr2121); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:337:14: ( valueExpr )*
                        loop89:
                        do {
                            int alt89=2;
                            int LA89_0 = input.LA(1);

                            if ( ((LA89_0>=IN_SET && LA89_0<=REGEXP)||LA89_0==NOT_EXPR||(LA89_0>=SUM && LA89_0<=AVG)||(LA89_0>=COALESCE && LA89_0<=COUNT)||(LA89_0>=CASE && LA89_0<=CASE2)||(LA89_0>=PREVIOUS && LA89_0<=EXISTS)||(LA89_0>=INSTANCEOF && LA89_0<=CURRENT_TIMESTAMP)||(LA89_0>=EVAL_AND_EXPR && LA89_0<=EVAL_NOTEQUALS_EXPR)||LA89_0==EVENT_PROP_EXPR||(LA89_0>=CONCAT && LA89_0<=LIB_FUNCTION)||LA89_0==ARRAY_EXPR||(LA89_0>=NOT_IN_SET && LA89_0<=NOT_REGEXP)||(LA89_0>=IN_RANGE && LA89_0<=NOT_IN_SUBSELECT_EXPR)||LA89_0==SUBSTITUTION||(LA89_0>=INT_TYPE && LA89_0<=NULL_TYPE)||LA89_0==STAR||(LA89_0>=BAND && LA89_0<=BXOR)||(LA89_0>=LT && LA89_0<=GE)||(LA89_0>=PLUS && LA89_0<=MOD)) ) {
                                alt89=1;
                            }


                            switch (alt89) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:337:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2124);
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
            int alt93=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt93=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt93=2;
                }
                break;
            case IN_RANGE:
                {
                alt93=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt93=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 93, 0, input);

                throw nvae;
            }

            switch (alt93) {
                case 1 :
                    // EsperEPL2Ast.g:341:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr2144); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2146);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2154);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:341:51: ( valueExpr )*
                    loop91:
                    do {
                        int alt91=2;
                        int LA91_0 = input.LA(1);

                        if ( ((LA91_0>=IN_SET && LA91_0<=REGEXP)||LA91_0==NOT_EXPR||(LA91_0>=SUM && LA91_0<=AVG)||(LA91_0>=COALESCE && LA91_0<=COUNT)||(LA91_0>=CASE && LA91_0<=CASE2)||(LA91_0>=PREVIOUS && LA91_0<=EXISTS)||(LA91_0>=INSTANCEOF && LA91_0<=CURRENT_TIMESTAMP)||(LA91_0>=EVAL_AND_EXPR && LA91_0<=EVAL_NOTEQUALS_EXPR)||LA91_0==EVENT_PROP_EXPR||(LA91_0>=CONCAT && LA91_0<=LIB_FUNCTION)||LA91_0==ARRAY_EXPR||(LA91_0>=NOT_IN_SET && LA91_0<=NOT_REGEXP)||(LA91_0>=IN_RANGE && LA91_0<=NOT_IN_SUBSELECT_EXPR)||LA91_0==SUBSTITUTION||(LA91_0>=INT_TYPE && LA91_0<=NULL_TYPE)||LA91_0==STAR||(LA91_0>=BAND && LA91_0<=BXOR)||(LA91_0>=LT && LA91_0<=GE)||(LA91_0>=PLUS && LA91_0<=MOD)) ) {
                            alt91=1;
                        }


                        switch (alt91) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:341:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr2157);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop91;
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
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr2176); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2178);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2186);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:342:55: ( valueExpr )*
                    loop92:
                    do {
                        int alt92=2;
                        int LA92_0 = input.LA(1);

                        if ( ((LA92_0>=IN_SET && LA92_0<=REGEXP)||LA92_0==NOT_EXPR||(LA92_0>=SUM && LA92_0<=AVG)||(LA92_0>=COALESCE && LA92_0<=COUNT)||(LA92_0>=CASE && LA92_0<=CASE2)||(LA92_0>=PREVIOUS && LA92_0<=EXISTS)||(LA92_0>=INSTANCEOF && LA92_0<=CURRENT_TIMESTAMP)||(LA92_0>=EVAL_AND_EXPR && LA92_0<=EVAL_NOTEQUALS_EXPR)||LA92_0==EVENT_PROP_EXPR||(LA92_0>=CONCAT && LA92_0<=LIB_FUNCTION)||LA92_0==ARRAY_EXPR||(LA92_0>=NOT_IN_SET && LA92_0<=NOT_REGEXP)||(LA92_0>=IN_RANGE && LA92_0<=NOT_IN_SUBSELECT_EXPR)||LA92_0==SUBSTITUTION||(LA92_0>=INT_TYPE && LA92_0<=NULL_TYPE)||LA92_0==STAR||(LA92_0>=BAND && LA92_0<=BXOR)||(LA92_0>=LT && LA92_0<=GE)||(LA92_0>=PLUS && LA92_0<=MOD)) ) {
                            alt92=1;
                        }


                        switch (alt92) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:342:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr2189);
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
                case 3 :
                    // EsperEPL2Ast.g:343:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr2208); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2210);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2218);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr2220);
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
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr2237); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2239);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2247);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr2249);
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
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==BETWEEN) ) {
                alt95=1;
            }
            else if ( (LA95_0==NOT_BETWEEN) ) {
                alt95=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }
            switch (alt95) {
                case 1 :
                    // EsperEPL2Ast.g:348:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr2274); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2276);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2278);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2280);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:349:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr2291); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2293);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2295);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:349:40: ( valueExpr )*
                    loop94:
                    do {
                        int alt94=2;
                        int LA94_0 = input.LA(1);

                        if ( ((LA94_0>=IN_SET && LA94_0<=REGEXP)||LA94_0==NOT_EXPR||(LA94_0>=SUM && LA94_0<=AVG)||(LA94_0>=COALESCE && LA94_0<=COUNT)||(LA94_0>=CASE && LA94_0<=CASE2)||(LA94_0>=PREVIOUS && LA94_0<=EXISTS)||(LA94_0>=INSTANCEOF && LA94_0<=CURRENT_TIMESTAMP)||(LA94_0>=EVAL_AND_EXPR && LA94_0<=EVAL_NOTEQUALS_EXPR)||LA94_0==EVENT_PROP_EXPR||(LA94_0>=CONCAT && LA94_0<=LIB_FUNCTION)||LA94_0==ARRAY_EXPR||(LA94_0>=NOT_IN_SET && LA94_0<=NOT_REGEXP)||(LA94_0>=IN_RANGE && LA94_0<=NOT_IN_SUBSELECT_EXPR)||LA94_0==SUBSTITUTION||(LA94_0>=INT_TYPE && LA94_0<=NULL_TYPE)||LA94_0==STAR||(LA94_0>=BAND && LA94_0<=BXOR)||(LA94_0>=LT && LA94_0<=GE)||(LA94_0>=PLUS && LA94_0<=MOD)) ) {
                            alt94=1;
                        }


                        switch (alt94) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:349:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr2298);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop94;
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
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==LIKE) ) {
                alt98=1;
            }
            else if ( (LA98_0==NOT_LIKE) ) {
                alt98=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 98, 0, input);

                throw nvae;
            }
            switch (alt98) {
                case 1 :
                    // EsperEPL2Ast.g:353:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr2318); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr2320);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr2322);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:353:33: ( valueExpr )?
                    int alt96=2;
                    int LA96_0 = input.LA(1);

                    if ( ((LA96_0>=IN_SET && LA96_0<=REGEXP)||LA96_0==NOT_EXPR||(LA96_0>=SUM && LA96_0<=AVG)||(LA96_0>=COALESCE && LA96_0<=COUNT)||(LA96_0>=CASE && LA96_0<=CASE2)||(LA96_0>=PREVIOUS && LA96_0<=EXISTS)||(LA96_0>=INSTANCEOF && LA96_0<=CURRENT_TIMESTAMP)||(LA96_0>=EVAL_AND_EXPR && LA96_0<=EVAL_NOTEQUALS_EXPR)||LA96_0==EVENT_PROP_EXPR||(LA96_0>=CONCAT && LA96_0<=LIB_FUNCTION)||LA96_0==ARRAY_EXPR||(LA96_0>=NOT_IN_SET && LA96_0<=NOT_REGEXP)||(LA96_0>=IN_RANGE && LA96_0<=NOT_IN_SUBSELECT_EXPR)||LA96_0==SUBSTITUTION||(LA96_0>=INT_TYPE && LA96_0<=NULL_TYPE)||LA96_0==STAR||(LA96_0>=BAND && LA96_0<=BXOR)||(LA96_0>=LT && LA96_0<=GE)||(LA96_0>=PLUS && LA96_0<=MOD)) ) {
                        alt96=1;
                    }
                    switch (alt96) {
                        case 1 :
                            // EsperEPL2Ast.g:353:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr2325);
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
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr2338); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr2340);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr2342);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:354:37: ( valueExpr )?
                    int alt97=2;
                    int LA97_0 = input.LA(1);

                    if ( ((LA97_0>=IN_SET && LA97_0<=REGEXP)||LA97_0==NOT_EXPR||(LA97_0>=SUM && LA97_0<=AVG)||(LA97_0>=COALESCE && LA97_0<=COUNT)||(LA97_0>=CASE && LA97_0<=CASE2)||(LA97_0>=PREVIOUS && LA97_0<=EXISTS)||(LA97_0>=INSTANCEOF && LA97_0<=CURRENT_TIMESTAMP)||(LA97_0>=EVAL_AND_EXPR && LA97_0<=EVAL_NOTEQUALS_EXPR)||LA97_0==EVENT_PROP_EXPR||(LA97_0>=CONCAT && LA97_0<=LIB_FUNCTION)||LA97_0==ARRAY_EXPR||(LA97_0>=NOT_IN_SET && LA97_0<=NOT_REGEXP)||(LA97_0>=IN_RANGE && LA97_0<=NOT_IN_SUBSELECT_EXPR)||LA97_0==SUBSTITUTION||(LA97_0>=INT_TYPE && LA97_0<=NULL_TYPE)||LA97_0==STAR||(LA97_0>=BAND && LA97_0<=BXOR)||(LA97_0>=LT && LA97_0<=GE)||(LA97_0>=PLUS && LA97_0<=MOD)) ) {
                        alt97=1;
                    }
                    switch (alt97) {
                        case 1 :
                            // EsperEPL2Ast.g:354:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr2345);
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
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==REGEXP) ) {
                alt99=1;
            }
            else if ( (LA99_0==NOT_REGEXP) ) {
                alt99=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }
            switch (alt99) {
                case 1 :
                    // EsperEPL2Ast.g:358:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr2364); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2366);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2368);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:359:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr2379); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2381);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2383);
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
            int alt109=13;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt109=1;
                }
                break;
            case AVG:
                {
                alt109=2;
                }
                break;
            case COUNT:
                {
                alt109=3;
                }
                break;
            case MEDIAN:
                {
                alt109=4;
                }
                break;
            case STDDEV:
                {
                alt109=5;
                }
                break;
            case AVEDEV:
                {
                alt109=6;
                }
                break;
            case COALESCE:
                {
                alt109=7;
                }
                break;
            case PREVIOUS:
                {
                alt109=8;
                }
                break;
            case PRIOR:
                {
                alt109=9;
                }
                break;
            case INSTANCEOF:
                {
                alt109=10;
                }
                break;
            case CAST:
                {
                alt109=11;
                }
                break;
            case EXISTS:
                {
                alt109=12;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt109=13;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 109, 0, input);

                throw nvae;
            }

            switch (alt109) {
                case 1 :
                    // EsperEPL2Ast.g:363:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc2402); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:363:13: ( DISTINCT )?
                    int alt100=2;
                    int LA100_0 = input.LA(1);

                    if ( (LA100_0==DISTINCT) ) {
                        alt100=1;
                    }
                    switch (alt100) {
                        case 1 :
                            // EsperEPL2Ast.g:363:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2405); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2409);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:364:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc2420); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:364:12: ( DISTINCT )?
                    int alt101=2;
                    int LA101_0 = input.LA(1);

                    if ( (LA101_0==DISTINCT) ) {
                        alt101=1;
                    }
                    switch (alt101) {
                        case 1 :
                            // EsperEPL2Ast.g:364:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2423); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2427);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:365:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc2438); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:365:14: ( ( DISTINCT )? valueExpr )?
                        int alt103=2;
                        int LA103_0 = input.LA(1);

                        if ( ((LA103_0>=IN_SET && LA103_0<=REGEXP)||LA103_0==NOT_EXPR||(LA103_0>=SUM && LA103_0<=AVG)||(LA103_0>=COALESCE && LA103_0<=COUNT)||(LA103_0>=CASE && LA103_0<=CASE2)||LA103_0==DISTINCT||(LA103_0>=PREVIOUS && LA103_0<=EXISTS)||(LA103_0>=INSTANCEOF && LA103_0<=CURRENT_TIMESTAMP)||(LA103_0>=EVAL_AND_EXPR && LA103_0<=EVAL_NOTEQUALS_EXPR)||LA103_0==EVENT_PROP_EXPR||(LA103_0>=CONCAT && LA103_0<=LIB_FUNCTION)||LA103_0==ARRAY_EXPR||(LA103_0>=NOT_IN_SET && LA103_0<=NOT_REGEXP)||(LA103_0>=IN_RANGE && LA103_0<=NOT_IN_SUBSELECT_EXPR)||LA103_0==SUBSTITUTION||(LA103_0>=INT_TYPE && LA103_0<=NULL_TYPE)||LA103_0==STAR||(LA103_0>=BAND && LA103_0<=BXOR)||(LA103_0>=LT && LA103_0<=GE)||(LA103_0>=PLUS && LA103_0<=MOD)) ) {
                            alt103=1;
                        }
                        switch (alt103) {
                            case 1 :
                                // EsperEPL2Ast.g:365:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:365:15: ( DISTINCT )?
                                int alt102=2;
                                int LA102_0 = input.LA(1);

                                if ( (LA102_0==DISTINCT) ) {
                                    alt102=1;
                                }
                                switch (alt102) {
                                    case 1 :
                                        // EsperEPL2Ast.g:365:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2442); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc2446);
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
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc2460); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:366:15: ( DISTINCT )?
                    int alt104=2;
                    int LA104_0 = input.LA(1);

                    if ( (LA104_0==DISTINCT) ) {
                        alt104=1;
                    }
                    switch (alt104) {
                        case 1 :
                            // EsperEPL2Ast.g:366:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2463); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2467);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:367:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc2478); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:367:15: ( DISTINCT )?
                    int alt105=2;
                    int LA105_0 = input.LA(1);

                    if ( (LA105_0==DISTINCT) ) {
                        alt105=1;
                    }
                    switch (alt105) {
                        case 1 :
                            // EsperEPL2Ast.g:367:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2481); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2485);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:368:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc2496); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:368:15: ( DISTINCT )?
                    int alt106=2;
                    int LA106_0 = input.LA(1);

                    if ( (LA106_0==DISTINCT) ) {
                        alt106=1;
                    }
                    switch (alt106) {
                        case 1 :
                            // EsperEPL2Ast.g:368:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2499); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2503);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:369:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc2515); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2517);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2519);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:369:38: ( valueExpr )*
                    loop107:
                    do {
                        int alt107=2;
                        int LA107_0 = input.LA(1);

                        if ( ((LA107_0>=IN_SET && LA107_0<=REGEXP)||LA107_0==NOT_EXPR||(LA107_0>=SUM && LA107_0<=AVG)||(LA107_0>=COALESCE && LA107_0<=COUNT)||(LA107_0>=CASE && LA107_0<=CASE2)||(LA107_0>=PREVIOUS && LA107_0<=EXISTS)||(LA107_0>=INSTANCEOF && LA107_0<=CURRENT_TIMESTAMP)||(LA107_0>=EVAL_AND_EXPR && LA107_0<=EVAL_NOTEQUALS_EXPR)||LA107_0==EVENT_PROP_EXPR||(LA107_0>=CONCAT && LA107_0<=LIB_FUNCTION)||LA107_0==ARRAY_EXPR||(LA107_0>=NOT_IN_SET && LA107_0<=NOT_REGEXP)||(LA107_0>=IN_RANGE && LA107_0<=NOT_IN_SUBSELECT_EXPR)||LA107_0==SUBSTITUTION||(LA107_0>=INT_TYPE && LA107_0<=NULL_TYPE)||LA107_0==STAR||(LA107_0>=BAND && LA107_0<=BXOR)||(LA107_0>=LT && LA107_0<=GE)||(LA107_0>=PLUS && LA107_0<=MOD)) ) {
                            alt107=1;
                        }


                        switch (alt107) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:369:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc2522);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop107;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:370:5: ^(f= PREVIOUS valueExpr eventPropertyExpr )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc2537); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2539);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc2541);
                    eventPropertyExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:371:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc2553); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc2557); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc2559);
                    eventPropertyExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:372:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc2571); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2573);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc2575); 
                    // EsperEPL2Ast.g:372:42: ( CLASS_IDENT )*
                    loop108:
                    do {
                        int alt108=2;
                        int LA108_0 = input.LA(1);

                        if ( (LA108_0==CLASS_IDENT) ) {
                            alt108=1;
                        }


                        switch (alt108) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:372:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc2578); 

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
                case 11 :
                    // EsperEPL2Ast.g:373:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc2592); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2594);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc2596); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:374:5: ^(f= EXISTS eventPropertyExpr )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc2608); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc2610);
                    eventPropertyExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:375:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc2621); 



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
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr2641); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:379:19: ( valueExpr )*
                loop110:
                do {
                    int alt110=2;
                    int LA110_0 = input.LA(1);

                    if ( ((LA110_0>=IN_SET && LA110_0<=REGEXP)||LA110_0==NOT_EXPR||(LA110_0>=SUM && LA110_0<=AVG)||(LA110_0>=COALESCE && LA110_0<=COUNT)||(LA110_0>=CASE && LA110_0<=CASE2)||(LA110_0>=PREVIOUS && LA110_0<=EXISTS)||(LA110_0>=INSTANCEOF && LA110_0<=CURRENT_TIMESTAMP)||(LA110_0>=EVAL_AND_EXPR && LA110_0<=EVAL_NOTEQUALS_EXPR)||LA110_0==EVENT_PROP_EXPR||(LA110_0>=CONCAT && LA110_0<=LIB_FUNCTION)||LA110_0==ARRAY_EXPR||(LA110_0>=NOT_IN_SET && LA110_0<=NOT_REGEXP)||(LA110_0>=IN_RANGE && LA110_0<=NOT_IN_SUBSELECT_EXPR)||LA110_0==SUBSTITUTION||(LA110_0>=INT_TYPE && LA110_0<=NULL_TYPE)||LA110_0==STAR||(LA110_0>=BAND && LA110_0<=BXOR)||(LA110_0>=LT && LA110_0<=GE)||(LA110_0>=PLUS && LA110_0<=MOD)) ) {
                        alt110=1;
                    }


                    switch (alt110) {
                	case 1 :
                	    // EsperEPL2Ast.g:379:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr2644);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop110;
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
            int alt112=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt112=1;
                }
                break;
            case MINUS:
                {
                alt112=2;
                }
                break;
            case DIV:
                {
                alt112=3;
                }
                break;
            case STAR:
                {
                alt112=4;
                }
                break;
            case MOD:
                {
                alt112=5;
                }
                break;
            case BAND:
                {
                alt112=6;
                }
                break;
            case BOR:
                {
                alt112=7;
                }
                break;
            case BXOR:
                {
                alt112=8;
                }
                break;
            case CONCAT:
                {
                alt112=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 112, 0, input);

                throw nvae;
            }

            switch (alt112) {
                case 1 :
                    // EsperEPL2Ast.g:383:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr2665); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2667);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2669);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:384:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr2681); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2683);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2685);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:385:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr2697); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2699);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2701);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:386:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr2712); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2714);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2716);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:387:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr2728); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2730);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2732);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:388:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr2743); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2745);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2747);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:389:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr2758); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2760);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2762);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:390:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr2773); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2775);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2777);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:391:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr2789); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2791);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2793);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:391:36: ( valueExpr )*
                    loop111:
                    do {
                        int alt111=2;
                        int LA111_0 = input.LA(1);

                        if ( ((LA111_0>=IN_SET && LA111_0<=REGEXP)||LA111_0==NOT_EXPR||(LA111_0>=SUM && LA111_0<=AVG)||(LA111_0>=COALESCE && LA111_0<=COUNT)||(LA111_0>=CASE && LA111_0<=CASE2)||(LA111_0>=PREVIOUS && LA111_0<=EXISTS)||(LA111_0>=INSTANCEOF && LA111_0<=CURRENT_TIMESTAMP)||(LA111_0>=EVAL_AND_EXPR && LA111_0<=EVAL_NOTEQUALS_EXPR)||LA111_0==EVENT_PROP_EXPR||(LA111_0>=CONCAT && LA111_0<=LIB_FUNCTION)||LA111_0==ARRAY_EXPR||(LA111_0>=NOT_IN_SET && LA111_0<=NOT_REGEXP)||(LA111_0>=IN_RANGE && LA111_0<=NOT_IN_SUBSELECT_EXPR)||LA111_0==SUBSTITUTION||(LA111_0>=INT_TYPE && LA111_0<=NULL_TYPE)||LA111_0==STAR||(LA111_0>=BAND && LA111_0<=BXOR)||(LA111_0>=LT && LA111_0<=GE)||(LA111_0>=PLUS && LA111_0<=MOD)) ) {
                            alt111=1;
                        }


                        switch (alt111) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:391:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2796);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop111;
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
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunc2817); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:395:22: ( CLASS_IDENT )?
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==CLASS_IDENT) ) {
                alt113=1;
            }
            switch (alt113) {
                case 1 :
                    // EsperEPL2Ast.g:395:23: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunc2820); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunc2824); 
            // EsperEPL2Ast.g:395:43: ( DISTINCT )?
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==DISTINCT) ) {
                alt114=1;
            }
            switch (alt114) {
                case 1 :
                    // EsperEPL2Ast.g:395:44: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunc2827); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:395:55: ( valueExpr )*
            loop115:
            do {
                int alt115=2;
                int LA115_0 = input.LA(1);

                if ( ((LA115_0>=IN_SET && LA115_0<=REGEXP)||LA115_0==NOT_EXPR||(LA115_0>=SUM && LA115_0<=AVG)||(LA115_0>=COALESCE && LA115_0<=COUNT)||(LA115_0>=CASE && LA115_0<=CASE2)||(LA115_0>=PREVIOUS && LA115_0<=EXISTS)||(LA115_0>=INSTANCEOF && LA115_0<=CURRENT_TIMESTAMP)||(LA115_0>=EVAL_AND_EXPR && LA115_0<=EVAL_NOTEQUALS_EXPR)||LA115_0==EVENT_PROP_EXPR||(LA115_0>=CONCAT && LA115_0<=LIB_FUNCTION)||LA115_0==ARRAY_EXPR||(LA115_0>=NOT_IN_SET && LA115_0<=NOT_REGEXP)||(LA115_0>=IN_RANGE && LA115_0<=NOT_IN_SUBSELECT_EXPR)||LA115_0==SUBSTITUTION||(LA115_0>=INT_TYPE && LA115_0<=NULL_TYPE)||LA115_0==STAR||(LA115_0>=BAND && LA115_0<=BXOR)||(LA115_0>=LT && LA115_0<=GE)||(LA115_0>=PLUS && LA115_0<=MOD)) ) {
                    alt115=1;
                }


                switch (alt115) {
            	case 1 :
            	    // EsperEPL2Ast.g:395:56: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunc2832);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop115;
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
            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule2854);
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
            int alt119=6;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt119=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt119=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt119=3;
                }
                break;
            case NOT_EXPR:
                {
                alt119=4;
                }
                break;
            case GUARD_EXPR:
                {
                alt119=5;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt119=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 119, 0, input);

                throw nvae;
            }

            switch (alt119) {
                case 1 :
                    // EsperEPL2Ast.g:406:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice2868);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:407:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice2873);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:408:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice2883); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice2885);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:409:5: ^(n= NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_exprChoice2899); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice2901);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:410:5: ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice2915); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice2917);
                    exprChoice();

                    state._fsp--;

                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice2919); 
                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice2921); 
                    // EsperEPL2Ast.g:410:44: ( valueExprWithTime )*
                    loop116:
                    do {
                        int alt116=2;
                        int LA116_0 = input.LA(1);

                        if ( ((LA116_0>=IN_SET && LA116_0<=REGEXP)||LA116_0==NOT_EXPR||(LA116_0>=SUM && LA116_0<=AVG)||(LA116_0>=COALESCE && LA116_0<=COUNT)||(LA116_0>=CASE && LA116_0<=CASE2)||LA116_0==LAST||(LA116_0>=PREVIOUS && LA116_0<=EXISTS)||(LA116_0>=LW && LA116_0<=CURRENT_TIMESTAMP)||(LA116_0>=NUMERIC_PARAM_RANGE && LA116_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA116_0>=EVAL_AND_EXPR && LA116_0<=EVAL_NOTEQUALS_EXPR)||LA116_0==EVENT_PROP_EXPR||(LA116_0>=CONCAT && LA116_0<=LIB_FUNCTION)||(LA116_0>=TIME_PERIOD && LA116_0<=ARRAY_EXPR)||(LA116_0>=NOT_IN_SET && LA116_0<=NOT_REGEXP)||(LA116_0>=IN_RANGE && LA116_0<=NOT_IN_SUBSELECT_EXPR)||(LA116_0>=LAST_OPERATOR && LA116_0<=SUBSTITUTION)||(LA116_0>=NUMBERSETSTAR && LA116_0<=NULL_TYPE)||LA116_0==STAR||(LA116_0>=BAND && LA116_0<=BXOR)||(LA116_0>=LT && LA116_0<=GE)||(LA116_0>=PLUS && LA116_0<=MOD)) ) {
                            alt116=1;
                        }


                        switch (alt116) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:410:44: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice2923);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop116;
                        }
                    } while (true);

                     leaveNode(g); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:411:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice2937); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:411:26: ( matchUntilRange )?
                    int alt117=2;
                    int LA117_0 = input.LA(1);

                    if ( ((LA117_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA117_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt117=1;
                    }
                    switch (alt117) {
                        case 1 :
                            // EsperEPL2Ast.g:411:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice2939);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice2942);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:411:54: ( exprChoice )?
                    int alt118=2;
                    int LA118_0 = input.LA(1);

                    if ( ((LA118_0>=OR_EXPR && LA118_0<=EVERY_EXPR)||LA118_0==FOLLOWED_BY_EXPR||LA118_0==EVENT_FILTER_EXPR||(LA118_0>=GUARD_EXPR && LA118_0<=OBSERVER_EXPR)||LA118_0==MATCH_UNTIL_EXPR) ) {
                        alt118=1;
                    }
                    switch (alt118) {
                        case 1 :
                            // EsperEPL2Ast.g:411:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice2944);
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
            int alt123=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt123=1;
                }
                break;
            case OR_EXPR:
                {
                alt123=2;
                }
                break;
            case AND_EXPR:
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
                    // EsperEPL2Ast.g:415:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp2965); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp2967);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp2969);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:415:48: ( exprChoice )*
                    loop120:
                    do {
                        int alt120=2;
                        int LA120_0 = input.LA(1);

                        if ( ((LA120_0>=OR_EXPR && LA120_0<=EVERY_EXPR)||LA120_0==FOLLOWED_BY_EXPR||LA120_0==EVENT_FILTER_EXPR||(LA120_0>=GUARD_EXPR && LA120_0<=OBSERVER_EXPR)||LA120_0==MATCH_UNTIL_EXPR) ) {
                            alt120=1;
                        }


                        switch (alt120) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:415:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp2972);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop120;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:416:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp2988); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp2990);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp2992);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:416:40: ( exprChoice )*
                    loop121:
                    do {
                        int alt121=2;
                        int LA121_0 = input.LA(1);

                        if ( ((LA121_0>=OR_EXPR && LA121_0<=EVERY_EXPR)||LA121_0==FOLLOWED_BY_EXPR||LA121_0==EVENT_FILTER_EXPR||(LA121_0>=GUARD_EXPR && LA121_0<=OBSERVER_EXPR)||LA121_0==MATCH_UNTIL_EXPR) ) {
                            alt121=1;
                        }


                        switch (alt121) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:416:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp2995);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop121;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:417:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp3011); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3013);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3015);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:417:41: ( exprChoice )*
                    loop122:
                    do {
                        int alt122=2;
                        int LA122_0 = input.LA(1);

                        if ( ((LA122_0>=OR_EXPR && LA122_0<=EVERY_EXPR)||LA122_0==FOLLOWED_BY_EXPR||LA122_0==EVENT_FILTER_EXPR||(LA122_0>=GUARD_EXPR && LA122_0<=OBSERVER_EXPR)||LA122_0==MATCH_UNTIL_EXPR) ) {
                            alt122=1;
                        }


                        switch (alt122) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:417:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3018);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop122;
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
            int alt125=2;
            int LA125_0 = input.LA(1);

            if ( (LA125_0==EVENT_FILTER_EXPR) ) {
                alt125=1;
            }
            else if ( (LA125_0==OBSERVER_EXPR) ) {
                alt125=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 125, 0, input);

                throw nvae;
            }
            switch (alt125) {
                case 1 :
                    // EsperEPL2Ast.g:421:4: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_atomicExpr3037);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:422:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr3049); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3051); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3053); 
                    // EsperEPL2Ast.g:422:39: ( valueExprWithTime )*
                    loop124:
                    do {
                        int alt124=2;
                        int LA124_0 = input.LA(1);

                        if ( ((LA124_0>=IN_SET && LA124_0<=REGEXP)||LA124_0==NOT_EXPR||(LA124_0>=SUM && LA124_0<=AVG)||(LA124_0>=COALESCE && LA124_0<=COUNT)||(LA124_0>=CASE && LA124_0<=CASE2)||LA124_0==LAST||(LA124_0>=PREVIOUS && LA124_0<=EXISTS)||(LA124_0>=LW && LA124_0<=CURRENT_TIMESTAMP)||(LA124_0>=NUMERIC_PARAM_RANGE && LA124_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA124_0>=EVAL_AND_EXPR && LA124_0<=EVAL_NOTEQUALS_EXPR)||LA124_0==EVENT_PROP_EXPR||(LA124_0>=CONCAT && LA124_0<=LIB_FUNCTION)||(LA124_0>=TIME_PERIOD && LA124_0<=ARRAY_EXPR)||(LA124_0>=NOT_IN_SET && LA124_0<=NOT_REGEXP)||(LA124_0>=IN_RANGE && LA124_0<=NOT_IN_SUBSELECT_EXPR)||(LA124_0>=LAST_OPERATOR && LA124_0<=SUBSTITUTION)||(LA124_0>=NUMBERSETSTAR && LA124_0<=NULL_TYPE)||LA124_0==STAR||(LA124_0>=BAND && LA124_0<=BXOR)||(LA124_0>=LT && LA124_0<=GE)||(LA124_0>=PLUS && LA124_0<=MOD)) ) {
                            alt124=1;
                        }


                        switch (alt124) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:422:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr3055);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop124;
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
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr3075); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:426:27: ( IDENT )?
            int alt126=2;
            int LA126_0 = input.LA(1);

            if ( (LA126_0==IDENT) ) {
                alt126=1;
            }
            switch (alt126) {
                case 1 :
                    // EsperEPL2Ast.g:426:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr3077); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr3080); 
            // EsperEPL2Ast.g:426:46: ( valueExpr )*
            loop127:
            do {
                int alt127=2;
                int LA127_0 = input.LA(1);

                if ( ((LA127_0>=IN_SET && LA127_0<=REGEXP)||LA127_0==NOT_EXPR||(LA127_0>=SUM && LA127_0<=AVG)||(LA127_0>=COALESCE && LA127_0<=COUNT)||(LA127_0>=CASE && LA127_0<=CASE2)||(LA127_0>=PREVIOUS && LA127_0<=EXISTS)||(LA127_0>=INSTANCEOF && LA127_0<=CURRENT_TIMESTAMP)||(LA127_0>=EVAL_AND_EXPR && LA127_0<=EVAL_NOTEQUALS_EXPR)||LA127_0==EVENT_PROP_EXPR||(LA127_0>=CONCAT && LA127_0<=LIB_FUNCTION)||LA127_0==ARRAY_EXPR||(LA127_0>=NOT_IN_SET && LA127_0<=NOT_REGEXP)||(LA127_0>=IN_RANGE && LA127_0<=NOT_IN_SUBSELECT_EXPR)||LA127_0==SUBSTITUTION||(LA127_0>=INT_TYPE && LA127_0<=NULL_TYPE)||LA127_0==STAR||(LA127_0>=BAND && LA127_0<=BXOR)||(LA127_0>=LT && LA127_0<=GE)||(LA127_0>=PLUS && LA127_0<=MOD)) ) {
                    alt127=1;
                }


                switch (alt127) {
            	case 1 :
            	    // EsperEPL2Ast.g:426:47: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr3083);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop127;
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
            int alt128=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt128=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt128=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt128=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
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
                    // EsperEPL2Ast.g:430:4: ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange3102); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3104);
                    matchUntilRangeParam();

                    state._fsp--;

                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3106);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:431:5: ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange3114); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3116);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:432:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange3124); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3126);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:433:4: ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange3133); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3135);
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
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam3164); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam3166);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:442:35: ( valueExpr )*
            loop129:
            do {
                int alt129=2;
                int LA129_0 = input.LA(1);

                if ( ((LA129_0>=IN_SET && LA129_0<=REGEXP)||LA129_0==NOT_EXPR||(LA129_0>=SUM && LA129_0<=AVG)||(LA129_0>=COALESCE && LA129_0<=COUNT)||(LA129_0>=CASE && LA129_0<=CASE2)||(LA129_0>=PREVIOUS && LA129_0<=EXISTS)||(LA129_0>=INSTANCEOF && LA129_0<=CURRENT_TIMESTAMP)||(LA129_0>=EVAL_AND_EXPR && LA129_0<=EVAL_NOTEQUALS_EXPR)||LA129_0==EVENT_PROP_EXPR||(LA129_0>=CONCAT && LA129_0<=LIB_FUNCTION)||LA129_0==ARRAY_EXPR||(LA129_0>=NOT_IN_SET && LA129_0<=NOT_REGEXP)||(LA129_0>=IN_RANGE && LA129_0<=NOT_IN_SUBSELECT_EXPR)||LA129_0==SUBSTITUTION||(LA129_0>=INT_TYPE && LA129_0<=NULL_TYPE)||LA129_0==STAR||(LA129_0>=BAND && LA129_0<=BXOR)||(LA129_0>=LT && LA129_0<=GE)||(LA129_0>=PLUS && LA129_0<=MOD)) ) {
                    alt129=1;
                }


                switch (alt129) {
            	case 1 :
            	    // EsperEPL2Ast.g:442:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam3169);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop129;
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
            int alt142=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt142=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt142=2;
                }
                break;
            case LT:
                {
                alt142=3;
                }
                break;
            case LE:
                {
                alt142=4;
                }
                break;
            case GT:
                {
                alt142=5;
                }
                break;
            case GE:
                {
                alt142=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt142=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt142=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt142=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt142=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt142=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt142=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 142, 0, input);

                throw nvae;
            }

            switch (alt142) {
                case 1 :
                    // EsperEPL2Ast.g:446:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator3185); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3187);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:447:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator3194); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3196);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:448:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator3203); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3205);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:449:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator3212); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3214);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:450:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator3221); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3223);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:451:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator3230); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3232);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:452:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator3239); 

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
                    int alt130=2;
                    int LA130_0 = input.LA(1);

                    if ( ((LA130_0>=INT_TYPE && LA130_0<=NULL_TYPE)) ) {
                        alt130=1;
                    }
                    else if ( (LA130_0==EVENT_FILTER_IDENT) ) {
                        alt130=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 130, 0, input);

                        throw nvae;
                    }
                    switch (alt130) {
                        case 1 :
                            // EsperEPL2Ast.g:452:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3248);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:452:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3251);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:452:76: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:452:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3255);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:452:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3258);
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
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator3272); 

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
                            // EsperEPL2Ast.g:453:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3281);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:453:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3284);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:453:80: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:453:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3288);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:453:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3291);
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
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator3305); 

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
                            // EsperEPL2Ast.g:454:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3314);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:454:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3317);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:454:73: ( constant[false] | filterIdentifier )*
                    loop135:
                    do {
                        int alt135=3;
                        int LA135_0 = input.LA(1);

                        if ( ((LA135_0>=INT_TYPE && LA135_0<=NULL_TYPE)) ) {
                            alt135=1;
                        }
                        else if ( (LA135_0==EVENT_FILTER_IDENT) ) {
                            alt135=2;
                        }


                        switch (alt135) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:454:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator3321);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:454:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3324);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop135;
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
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator3339); 

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
                    int alt136=2;
                    int LA136_0 = input.LA(1);

                    if ( ((LA136_0>=INT_TYPE && LA136_0<=NULL_TYPE)) ) {
                        alt136=1;
                    }
                    else if ( (LA136_0==EVENT_FILTER_IDENT) ) {
                        alt136=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 136, 0, input);

                        throw nvae;
                    }
                    switch (alt136) {
                        case 1 :
                            // EsperEPL2Ast.g:455:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3348);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:455:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3351);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:455:77: ( constant[false] | filterIdentifier )*
                    loop137:
                    do {
                        int alt137=3;
                        int LA137_0 = input.LA(1);

                        if ( ((LA137_0>=INT_TYPE && LA137_0<=NULL_TYPE)) ) {
                            alt137=1;
                        }
                        else if ( (LA137_0==EVENT_FILTER_IDENT) ) {
                            alt137=2;
                        }


                        switch (alt137) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:455:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator3355);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:455:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3358);
                    	    filterIdentifier();

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

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:456:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator3373); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:456:27: ( constant[false] | filterIdentifier )
                    int alt138=2;
                    int LA138_0 = input.LA(1);

                    if ( ((LA138_0>=INT_TYPE && LA138_0<=NULL_TYPE)) ) {
                        alt138=1;
                    }
                    else if ( (LA138_0==EVENT_FILTER_IDENT) ) {
                        alt138=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 138, 0, input);

                        throw nvae;
                    }
                    switch (alt138) {
                        case 1 :
                            // EsperEPL2Ast.g:456:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3376);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:456:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3379);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:456:62: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:456:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3383);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:456:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3386);
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
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator3394); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:457:31: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:457:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3397);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:457:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3400);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:457:66: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:457:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3404);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:457:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3407);
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
            int alt143=2;
            int LA143_0 = input.LA(1);

            if ( ((LA143_0>=INT_TYPE && LA143_0<=NULL_TYPE)) ) {
                alt143=1;
            }
            else if ( (LA143_0==EVENT_FILTER_IDENT) ) {
                alt143=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 143, 0, input);

                throw nvae;
            }
            switch (alt143) {
                case 1 :
                    // EsperEPL2Ast.g:461:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom3421);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:462:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom3427);
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
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier3438); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier3440); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier3442);
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
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr3459); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3461);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:469:44: ( eventPropertyAtomic )*
            loop144:
            do {
                int alt144=2;
                int LA144_0 = input.LA(1);

                if ( ((LA144_0>=EVENT_PROP_SIMPLE && LA144_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt144=1;
                }


                switch (alt144) {
            	case 1 :
            	    // EsperEPL2Ast.g:469:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3464);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop144;
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
            int alt145=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt145=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt145=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt145=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt145=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt145=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt145=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 145, 0, input);

                throw nvae;
            }

            switch (alt145) {
                case 1 :
                    // EsperEPL2Ast.g:473:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic3483); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3485); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:474:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic3492); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3494); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic3496); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:475:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic3503); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3505); 
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
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic3520); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3522); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:477:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic3529); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3531); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic3533); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:478:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic3540); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3542); 
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
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod3569); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod3571);
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
            int alt156=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt156=1;
                }
                break;
            case HOUR_PART:
                {
                alt156=2;
                }
                break;
            case MINUTE_PART:
                {
                alt156=3;
                }
                break;
            case SECOND_PART:
                {
                alt156=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt156=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 156, 0, input);

                throw nvae;
            }

            switch (alt156) {
                case 1 :
                    // EsperEPL2Ast.g:486:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef3587);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:486:13: ( hourPart )?
                    int alt146=2;
                    int LA146_0 = input.LA(1);

                    if ( (LA146_0==HOUR_PART) ) {
                        alt146=1;
                    }
                    switch (alt146) {
                        case 1 :
                            // EsperEPL2Ast.g:486:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef3590);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:486:25: ( minutePart )?
                    int alt147=2;
                    int LA147_0 = input.LA(1);

                    if ( (LA147_0==MINUTE_PART) ) {
                        alt147=1;
                    }
                    switch (alt147) {
                        case 1 :
                            // EsperEPL2Ast.g:486:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef3595);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:486:39: ( secondPart )?
                    int alt148=2;
                    int LA148_0 = input.LA(1);

                    if ( (LA148_0==SECOND_PART) ) {
                        alt148=1;
                    }
                    switch (alt148) {
                        case 1 :
                            // EsperEPL2Ast.g:486:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef3600);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:486:53: ( millisecondPart )?
                    int alt149=2;
                    int LA149_0 = input.LA(1);

                    if ( (LA149_0==MILLISECOND_PART) ) {
                        alt149=1;
                    }
                    switch (alt149) {
                        case 1 :
                            // EsperEPL2Ast.g:486:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3605);
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
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef3612);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:487:13: ( minutePart )?
                    int alt150=2;
                    int LA150_0 = input.LA(1);

                    if ( (LA150_0==MINUTE_PART) ) {
                        alt150=1;
                    }
                    switch (alt150) {
                        case 1 :
                            // EsperEPL2Ast.g:487:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef3615);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:487:27: ( secondPart )?
                    int alt151=2;
                    int LA151_0 = input.LA(1);

                    if ( (LA151_0==SECOND_PART) ) {
                        alt151=1;
                    }
                    switch (alt151) {
                        case 1 :
                            // EsperEPL2Ast.g:487:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef3620);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:487:41: ( millisecondPart )?
                    int alt152=2;
                    int LA152_0 = input.LA(1);

                    if ( (LA152_0==MILLISECOND_PART) ) {
                        alt152=1;
                    }
                    switch (alt152) {
                        case 1 :
                            // EsperEPL2Ast.g:487:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3625);
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
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef3632);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:488:15: ( secondPart )?
                    int alt153=2;
                    int LA153_0 = input.LA(1);

                    if ( (LA153_0==SECOND_PART) ) {
                        alt153=1;
                    }
                    switch (alt153) {
                        case 1 :
                            // EsperEPL2Ast.g:488:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef3635);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:488:29: ( millisecondPart )?
                    int alt154=2;
                    int LA154_0 = input.LA(1);

                    if ( (LA154_0==MILLISECOND_PART) ) {
                        alt154=1;
                    }
                    switch (alt154) {
                        case 1 :
                            // EsperEPL2Ast.g:488:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3640);
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
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef3647);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:489:15: ( millisecondPart )?
                    int alt155=2;
                    int LA155_0 = input.LA(1);

                    if ( (LA155_0==MILLISECOND_PART) ) {
                        alt155=1;
                    }
                    switch (alt155) {
                        case 1 :
                            // EsperEPL2Ast.g:489:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3650);
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
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3657);
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
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart3671); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart3673);
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
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart3688); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart3690);
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
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart3705); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart3707);
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
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart3722); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart3724);
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
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart3739); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart3741);
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
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution3756); 
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
            int alt157=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt157=1;
                }
                break;
            case LONG_TYPE:
                {
                alt157=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt157=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt157=4;
                }
                break;
            case STRING_TYPE:
                {
                alt157=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt157=6;
                }
                break;
            case NULL_TYPE:
                {
                alt157=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 157, 0, input);

                throw nvae;
            }

            switch (alt157) {
                case 1 :
                    // EsperEPL2Ast.g:518:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant3772); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:519:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant3781); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:520:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant3790); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:521:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant3799); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:522:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant3815); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:523:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant3831); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:524:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant3844); 
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
    public static final BitSet FOLLOW_eventFilterExpr_in_onExpr142 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x02C0000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onExpr146 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x02C0000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_onExpr149 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x02C0000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr156 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr160 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr164 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr184 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr186 = new BitSet(new long[]{0x0000000000000008L,0x0008000000000000L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr189 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr206 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr209 = new BitSet(new long[]{0x0000000000000000L,0x3000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr213 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr215 = new BitSet(new long[]{0x0000000000000008L,0x0018000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr218 = new BitSet(new long[]{0x0000000000000008L,0x0010000000000000L,0x0000000000000030L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr223 = new BitSet(new long[]{0x0000000000000008L,0x0010000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr228 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr233 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr252 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr255 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_onSetAssignment270 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom284 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom286 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom289 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr307 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr309 = new BitSet(new long[]{0x0000000000000000L,0x0001200000000000L,0x0010000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr312 = new BitSet(new long[]{0x0000000000000000L,0x0001200000000000L,0x0010000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr327 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr330 = new BitSet(new long[]{0x0008000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr359 = new BitSet(new long[]{0x0008000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr370 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert388 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert390 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList407 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList409 = new BitSet(new long[]{0x0000000000000008L,0x1000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList412 = new BitSet(new long[]{0x0000000000000008L,0x1000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList431 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList433 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList436 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement451 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement453 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement455 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement479 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement499 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement502 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement524 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement527 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr563 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr565 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr567 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr570 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr588 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr594 = new BitSet(new long[]{0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr599 = new BitSet(new long[]{0x0000000000000002L,0x0018000010000000L,0x000000000005C030L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr604 = new BitSet(new long[]{0x0000000000000002L,0x0010000010000000L,0x000000000005C030L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr611 = new BitSet(new long[]{0x0000000000000002L,0x0010000010000000L,0x000000000005C020L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr618 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L,0x000000000005C020L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr625 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr632 = new BitSet(new long[]{0x0000000000000002L,0x0000000010000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr656 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr658 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr667 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_insertIntoExprCol_in_insertIntoExpr670 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol689 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol691 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol694 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause712 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause714 = new BitSet(new long[]{0x0000000000000000L,0x3000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_selectionList_in_selectClause727 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause741 = new BitSet(new long[]{0x0000000000000002L,0x4000000000000000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause744 = new BitSet(new long[]{0x0000000000000002L,0x4000000000000000L,0x000000000000000FL});
    public static final BitSet FOLLOW_outerJoin_in_fromClause747 = new BitSet(new long[]{0x0000000000000002L,0x4000000000000000L,0x000000000000000FL});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList764 = new BitSet(new long[]{0x0000000000000002L,0x3000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList767 = new BitSet(new long[]{0x0000000000000002L,0x3000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement793 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement795 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement798 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement812 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement814 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement817 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent850 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent852 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent854 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent857 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent859 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent873 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent875 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent877 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent880 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent882 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent896 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent898 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent900 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent903 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent905 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent919 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent921 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent923 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent926 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent928 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression948 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression951 = new BitSet(new long[]{0x0800000000000008L,0x0001000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression955 = new BitSet(new long[]{0x0800000000000008L,0x0001000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression959 = new BitSet(new long[]{0x0800000000000008L,0x0001000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression963 = new BitSet(new long[]{0x0800000000000008L,0x0001000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression967 = new BitSet(new long[]{0x0800000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression972 = new BitSet(new long[]{0x0800000000000008L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression977 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression997 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression1001 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1018 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression1020 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001800000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1022 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000001800000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1030 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1051 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression1053 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression1055 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression1058 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1072 = new BitSet(new long[]{0x0000000000000002L,0x0001000000000000L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1075 = new BitSet(new long[]{0x0000000000000002L,0x0001000000000000L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr1092 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1094 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1096 = new BitSet(new long[]{0x800400001BE623C8L,0x03C00003C000007BL,0x0003BF0783600080L,0x0000007BCE0807F8L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr1099 = new BitSet(new long[]{0x800400001BE623C8L,0x03C00003C000007BL,0x0003BF0783600080L,0x0000007BCE0807F8L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause1120 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause1122 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause1140 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1142 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1145 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause1163 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1165 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1168 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1188 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement1190 = new BitSet(new long[]{0x00C0000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement1192 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause1215 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause1217 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1235 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1237 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000000020F0L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr1249 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr1251 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1268 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1270 = new BitSet(new long[]{0x800400001BE623C0L,0x03C00003C000007BL,0x0003BF0783600080L,0x0000007BCE0807F8L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr1281 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1296 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1298 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr1309 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr1324 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1326 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr1337 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x02C0000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr1339 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause1358 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause1361 = new BitSet(new long[]{0x0000000000000008L,0x0000000020000000L,0x0000000000000000L,0x00000000000060F0L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause1363 = new BitSet(new long[]{0x0000000000000008L,0x0000000020000000L,0x0000000000000000L,0x00000000000060F0L});
    public static final BitSet FOLLOW_number_in_rowLimitClause1367 = new BitSet(new long[]{0x0000000000000008L,0x0000000020000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause1369 = new BitSet(new long[]{0x0000000000000008L,0x0000000020000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause1373 = new BitSet(new long[]{0x0000000000000008L,0x0000000020000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause1376 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet1394 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1396 = new BitSet(new long[]{0x800400001BE623C0L,0x03C00003C000007BL,0x0003BF0783600080L,0x0000007BCE0807F8L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1398 = new BitSet(new long[]{0x800400001BE623C0L,0x03C00003C000007BL,0x0003BF0783600080L,0x0000007BCE0807F8L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1400 = new BitSet(new long[]{0x800400001BE623C0L,0x03C00003C000007BL,0x0003BF0783600080L,0x0000007BCE0807F8L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1402 = new BitSet(new long[]{0x800400001BE623C0L,0x03C00003C000007BL,0x0003BF0783600080L,0x0000007BCE0807F8L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1404 = new BitSet(new long[]{0x800400001BE623C8L,0x03C00003C000007BL,0x0003BF0783600080L,0x0000007BCE0807F8L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1406 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr1423 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1425 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1427 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr1439 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1441 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1443 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr1455 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1457 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1459 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr1470 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1472 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1474 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice1491 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1493 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1495 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1498 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice1512 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1514 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1516 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1519 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice1533 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1535 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1537 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice1549 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1551 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1553 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice1565 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1567 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice1578 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr1591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr1597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr1603 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr1610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr1618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr1623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunc_in_valueExpr1631 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr1636 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr1641 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr1647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr1652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr1657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr1662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr1667 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr1673 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr1680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime1693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime1702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime1709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime1717 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime1719 = new BitSet(new long[]{0x00C0000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime1721 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime1734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime1740 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime1745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime1750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime1760 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime1762 = new BitSet(new long[]{0x0000000000000008L,0x0000000140000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime1773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime1780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList1793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList1800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList1806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator1822 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator1825 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000080L,0x00000000000007F0L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator1828 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000080L,0x00000000000007F0L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator1830 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000080L,0x00000000000007F0L});
    public static final BitSet FOLLOW_constant_in_rangeOperator1834 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator1837 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator1839 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator1860 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator1863 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator1866 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator1868 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator1887 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator1890 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator1893 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator1895 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator1914 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator1917 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator1920 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator1922 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr1943 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr1945 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr1964 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr1966 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr1985 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr1987 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr1989 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2001 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2003 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2005 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2024 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr2026 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_subQueryExpr2042 = new BitSet(new long[]{0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr2044 = new BitSet(new long[]{0x0000000000000002L,0x0009000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_viewExpr_in_subQueryExpr2047 = new BitSet(new long[]{0x0000000000000002L,0x0009000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_subQueryExpr2052 = new BitSet(new long[]{0x0000000000000002L,0x0008000000000000L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr2057 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2074 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr2076 = new BitSet(new long[]{0x0000000000000008L,0x0001000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr2079 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr2084 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr2105 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2108 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr2121 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2124 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr2144 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2146 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000120000L});
    public static final BitSet FOLLOW_set_in_inExpr2148 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2154 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE2C07F0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2157 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE2C07F0L});
    public static final BitSet FOLLOW_set_in_inExpr2161 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr2176 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2178 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000120000L});
    public static final BitSet FOLLOW_set_in_inExpr2180 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2186 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE2C07F0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2189 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE2C07F0L});
    public static final BitSet FOLLOW_set_in_inExpr2193 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr2208 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2210 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000120000L});
    public static final BitSet FOLLOW_set_in_inExpr2212 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2218 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2220 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000240000L});
    public static final BitSet FOLLOW_set_in_inExpr2222 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr2237 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2239 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000120000L});
    public static final BitSet FOLLOW_set_in_inExpr2241 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2247 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2249 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000240000L});
    public static final BitSet FOLLOW_set_in_inExpr2251 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr2274 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2276 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2278 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2280 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr2291 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2293 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2295 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2298 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr2318 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2320 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2322 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2325 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr2338 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2340 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2342 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2345 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr2364 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2366 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2368 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr2379 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2381 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2383 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc2402 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2405 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2409 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc2420 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2423 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2427 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc2438 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2442 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2446 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc2460 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2463 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2467 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc2478 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2481 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2485 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc2496 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2499 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2503 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc2515 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2517 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2519 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2522 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc2537 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2539 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc2541 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc2553 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc2557 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc2559 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc2571 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2573 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc2575 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc2578 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc2592 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2594 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc2596 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc2608 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc2610 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc2621 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr2641 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr2644 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr2665 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2667 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2669 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr2681 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2683 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2685 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr2697 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2699 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2701 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr2712 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2714 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2716 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr2728 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2730 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2732 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr2743 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2745 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2747 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr2758 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2760 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2762 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr2773 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2775 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2777 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr2789 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2791 = new BitSet(new long[]{0x800000001BE623C0L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2793 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2796 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunc2817 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunc2820 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_libFunc2824 = new BitSet(new long[]{0x800020001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunc2827 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_libFunc2832 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule2854 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice2868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice2873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice2883 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2885 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_exprChoice2899 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2901 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice2915 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2917 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice2919 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice2921 = new BitSet(new long[]{0x800400001BE623C8L,0x03C00003C000007BL,0x0003BF0783600080L,0x0000007BCE0807F8L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice2923 = new BitSet(new long[]{0x800400001BE623C8L,0x03C00003C000007BL,0x0003BF0783600080L,0x0000007BCE0807F8L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice2937 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice2939 = new BitSet(new long[]{0x0000000000007800L,0x0000C01400000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2942 = new BitSet(new long[]{0x0000000000007808L,0x0000C01400000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2944 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp2965 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp2967 = new BitSet(new long[]{0x0000000000007800L,0x0000C01400000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp2969 = new BitSet(new long[]{0x0000000000007808L,0x0000C01400000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp2972 = new BitSet(new long[]{0x0000000000007808L,0x0000C01400000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp2988 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp2990 = new BitSet(new long[]{0x0000000000007800L,0x0000C01400000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp2992 = new BitSet(new long[]{0x0000000000007808L,0x0000C01400000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp2995 = new BitSet(new long[]{0x0000000000007808L,0x0000C01400000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp3011 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3013 = new BitSet(new long[]{0x0000000000007800L,0x0000C01400000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3015 = new BitSet(new long[]{0x0000000000007808L,0x0000C01400000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3018 = new BitSet(new long[]{0x0000000000007808L,0x0000C01400000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_eventFilterExpr_in_atomicExpr3037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr3049 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3051 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3053 = new BitSet(new long[]{0x800400001BE623C8L,0x03C00003C000007BL,0x0003BF0783600080L,0x0000007BCE0807F8L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr3055 = new BitSet(new long[]{0x800400001BE623C8L,0x03C00003C000007BL,0x0003BF0783600080L,0x0000007BCE0807F8L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr3075 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr3077 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr3080 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr3083 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange3102 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3104 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000020000000800L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3106 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange3114 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3116 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange3124 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3126 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange3133 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3135 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_matchUntilRangeParam0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam3164 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam3166 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam3169 = new BitSet(new long[]{0x800000001BE623C8L,0x03C0000000000073L,0x00023F0782600080L,0x0000007BCE0807F0L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator3185 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3187 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator3194 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3196 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator3203 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3205 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator3212 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3214 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator3221 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3223 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator3230 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3232 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator3239 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3241 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3248 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3251 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3255 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000240000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3258 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000240000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3261 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator3272 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3274 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3281 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3284 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3288 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000240000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3291 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000240000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3294 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator3305 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3307 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3314 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000002407F0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3317 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000002407F0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3321 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000002407F0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3324 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000002407F0L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3328 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator3339 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3341 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3348 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000002407F0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3351 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000002407F0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3355 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000002407F0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3358 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000002407F0L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3362 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator3373 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3376 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3379 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3383 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3386 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator3394 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3397 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3400 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L,0x0000000000000000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3404 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3407 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom3421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom3427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier3438 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier3440 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier3442 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr3459 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3461 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000003F00L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3464 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000003F00L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic3483 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3485 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic3492 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3494 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic3496 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic3503 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3505 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001800000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic3507 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic3520 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3522 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic3529 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3531 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic3533 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic3540 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3542 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001800000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic3544 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod3569 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod3571 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef3587 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000078000000L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef3590 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000070000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef3595 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000060000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3600 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef3612 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000070000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef3615 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000060000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3620 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef3632 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000060000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3635 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3640 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3647 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart3671 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart3673 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart3688 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart3690 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart3705 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart3707 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart3722 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart3724 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart3739 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart3741 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution3756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant3772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant3781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant3790 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant3799 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant3815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant3831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant3844 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}