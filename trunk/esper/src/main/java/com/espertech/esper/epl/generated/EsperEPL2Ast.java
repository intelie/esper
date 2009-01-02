// $ANTLR 3.1.1 EsperEPL2Ast.g 2009-01-02 08:33:01

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "INSERTINTO_EXPRCOL", "CONCAT", "LIB_FUNCTION", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_WINDOW_COL_TYPE_LIST", "CREATE_WINDOW_COL_TYPE", "NUMBERSETSTAR", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "IDENT", "COMMA", "EQUALS", "DOT", "LPAREN", "RPAREN", "STAR", "LBRACK", "RBRACK", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BOR", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "PLUS", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "QUESTION", "ESCAPECHAR", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=148;
    public static final int FLOAT_SUFFIX=273;
    public static final int STAR=215;
    public static final int NUMERIC_PARAM_LIST=97;
    public static final int ISTREAM=57;
    public static final int MOD=234;
    public static final int OUTERJOIN_EXPR=131;
    public static final int BSR=255;
    public static final int LIB_FUNCTION=154;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=90;
    public static final int FULL_OUTERJOIN_EXPR=135;
    public static final int RPAREN=214;
    public static final int LNOT=244;
    public static final int INC=248;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=219;
    public static final int BSR_ASSIGN=256;
    public static final int STREAM_EXPR=130;
    public static final int CAST_EXPR=182;
    public static final int TIMEPERIOD_SECONDS=87;
    public static final int NOT_EQUAL=225;
    public static final int METADATASQL=64;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=100;
    public static final int FOLLOWED_BY=238;
    public static final int HOUR_PART=159;
    public static final int RBRACK=217;
    public static final int MATCH_UNTIL_RANGE_CLOSED=195;
    public static final int GE=229;
    public static final int METHOD_JOIN_EXPR=191;
    public static final int ASC=54;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=104;
    public static final int MINUS_ASSIGN=249;
    public static final int ELSE=29;
    public static final int EVENT_FILTER_NOT_IN=110;
    public static final int NUM_DOUBLE=207;
    public static final int INSERTINTO_STREAM_NAME=171;
    public static final int UNARY_MINUS=155;
    public static final int LCURLY=235;
    public static final int TIMEPERIOD_MILLISEC=88;
    public static final int RETAINUNION=60;
    public static final int DBWHERE_CLAUSE=169;
    public static final int MEDIAN=22;
    public static final int EVENTS=48;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=108;
    public static final int GROUP=43;
    public static final int EMAILAT=264;
    public static final int WS=265;
    public static final int ESCAPECHAR=240;
    public static final int SL_COMMENT=266;
    public static final int NULL_TYPE=206;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=193;
    public static final int GT=227;
    public static final int BNOT=245;
    public static final int WHERE_EXPR=119;
    public static final int END=32;
    public static final int INNERJOIN_EXPR=132;
    public static final int LAND=262;
    public static final int NOT_REGEXP=166;
    public static final int MATCH_UNTIL_EXPR=192;
    public static final int EVENT_PROP_EXPR=139;
    public static final int LBRACK=216;
    public static final int VIEW_EXPR=116;
    public static final int LONG_TYPE=201;
    public static final int TIMEPERIOD_SEC=85;
    public static final int ON_SELECT_EXPR=187;
    public static final int MINUTE_PART=160;
    public static final int PATTERN_NOT_EXPR=103;
    public static final int SUM=17;
    public static final int SQL_NE=224;
    public static final int HexDigit=271;
    public static final int LPAREN=213;
    public static final int IN_SUBSELECT_EXPR=176;
    public static final int AT=78;
    public static final int AS=16;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=91;
    public static final int THEN=31;
    public static final int NOT_IN_RANGE=173;
    public static final int OFFSET=95;
    public static final int LEFT=37;
    public static final int AVG=18;
    public static final int SECOND_PART=161;
    public static final int PREVIOUS=65;
    public static final int DATABASE_JOIN_EXPR=118;
    public static final int IDENT=209;
    public static final int PLUS=231;
    public static final int BXOR=223;
    public static final int CASE2=28;
    public static final int TIMEPERIOD_DAY=79;
    public static final int EXISTS=67;
    public static final int EVENT_PROP_INDEXED=142;
    public static final int TIMEPERIOD_MILLISECOND=89;
    public static final int EVAL_NOTEQUALS_EXPR=125;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=194;
    public static final int CREATE_VARIABLE_EXPR=190;
    public static final int CREATE_WINDOW_COL_TYPE=198;
    public static final int LIKE=8;
    public static final int OUTER=34;
    public static final int BY=42;
    public static final int ARRAY_PARAM_LIST=101;
    public static final int RIGHT_OUTERJOIN_EXPR=134;
    public static final int NUMBERSETSTAR=199;
    public static final int PATTERN_FILTER_EXPR=102;
    public static final int LAST_OPERATOR=179;
    public static final int EVAL_AND_EXPR=122;
    public static final int LEFT_OUTERJOIN_EXPR=133;
    public static final int EPL_EXPR=208;
    public static final int GROUP_BY_EXPR=136;
    public static final int SET=75;
    public static final int RIGHT=38;
    public static final int HAVING=44;
    public static final int INSTANCEOF=70;
    public static final int MIN=20;
    public static final int EVENT_PROP_SIMPLE=140;
    public static final int MINUS=232;
    public static final int SEMI=263;
    public static final int STAR_ASSIGN=251;
    public static final int COLON=218;
    public static final int BAND_ASSIGN=261;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=149;
    public static final int VALUE_NULL=93;
    public static final int NOT_IN_SET=163;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=143;
    public static final int SL=257;
    public static final int WHEN=30;
    public static final int NOT_IN_SUBSELECT_EXPR=177;
    public static final int GUARD_EXPR=114;
    public static final int SR=253;
    public static final int RCURLY=236;
    public static final int PLUS_ASSIGN=247;
    public static final int DAY_PART=158;
    public static final int EXISTS_SUBSELECT_EXPR=175;
    public static final int EVENT_FILTER_IN=109;
    public static final int DIV=233;
    public static final int OBJECT_PARAM_ORDERED_EXPR=99;
    public static final int OctalEscape=270;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=162;
    public static final int PRIOR=66;
    public static final int FIRST=49;
    public static final int ROW_LIMIT_EXPR=94;
    public static final int SELECTION_EXPR=127;
    public static final int LOR=230;
    public static final int CAST=71;
    public static final int LW=69;
    public static final int WILDCARD_SELECT=170;
    public static final int EXPONENT=272;
    public static final int LT=226;
    public static final int PATTERN_INCL_EXPR=117;
    public static final int ORDER_BY_EXPR=137;
    public static final int BOOL_TYPE=205;
    public static final int MOD_ASSIGN=252;
    public static final int CASE=27;
    public static final int IN_SUBSELECT_QUERY_EXPR=178;
    public static final int EQUALS=211;
    public static final int COUNT=25;
    public static final int RETAININTERSECTION=61;
    public static final int DIV_ASSIGN=246;
    public static final int SL_ASSIGN=258;
    public static final int PATTERN=62;
    public static final int SQL=63;
    public static final int WEEKDAY=68;
    public static final int FULL=39;
    public static final int INSERT=51;
    public static final int ESCAPE=10;
    public static final int ARRAY_EXPR=157;
    public static final int LAST=50;
    public static final int BOOLEAN_FALSE=92;
    public static final int SELECT=26;
    public static final int INTO=52;
    public static final int FLOAT_TYPE=202;
    public static final int TIMEPERIOD_SECOND=86;
    public static final int COALESCE=21;
    public static final int EVENT_FILTER_BETWEEN=111;
    public static final int SUBSELECT_EXPR=174;
    public static final int NUMERIC_PARAM_RANGE=96;
    public static final int CONCAT=153;
    public static final int CLASS_IDENT=113;
    public static final int ON_EXPR=185;
    public static final int CREATE_WINDOW_EXPR=183;
    public static final int ON_DELETE_EXPR=186;
    public static final int ON=40;
    public static final int NUM_LONG=241;
    public static final int TIME_PERIOD=156;
    public static final int DOUBLE_TYPE=203;
    public static final int DELETE=73;
    public static final int INT_TYPE=200;
    public static final int EVAL_BITWISE_EXPR=121;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=138;
    public static final int TIMEPERIOD_HOURS=82;
    public static final int VARIABLE=76;
    public static final int SUBSTITUTION=181;
    public static final int UNTIL=77;
    public static final int STRING_TYPE=204;
    public static final int ON_SET_EXPR=189;
    public static final int NUM_INT=237;
    public static final int STDDEV=23;
    public static final int ON_EXPR_FROM=188;
    public static final int NUM_FLOAT=242;
    public static final int FROM=33;
    public static final int DISTINCT=45;
    public static final int OUTPUT=47;
    public static final int EscapeSequence=268;
    public static final int WEEKDAY_OPERATOR=180;
    public static final int WHERE=15;
    public static final int CREATE_WINDOW_COL_TYPE_LIST=197;
    public static final int DEC=250;
    public static final int INNER=35;
    public static final int NUMERIC_PARAM_FREQUENCY=98;
    public static final int BXOR_ASSIGN=259;
    public static final int ORDER=53;
    public static final int SNAPSHOT=74;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=145;
    public static final int EVENT_FILTER_PARAM=106;
    public static final int IRSTREAM=58;
    public static final int MAX=19;
    public static final int TIMEPERIOD_DAYS=80;
    public static final int EVENT_FILTER_RANGE=107;
    public static final int ML_COMMENT=267;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=144;
    public static final int BOR_ASSIGN=260;
    public static final int COMMA=210;
    public static final int WHEN_LIMIT_EXPR=150;
    public static final int IS=41;
    public static final int TIMEPERIOD_LIMIT_EXPR=147;
    public static final int ALL=46;
    public static final int TIMEPERIOD_HOUR=81;
    public static final int BOR=222;
    public static final int EQUAL=243;
    public static final int EVENT_FILTER_NOT_BETWEEN=112;
    public static final int IN_RANGE=172;
    public static final int DOT=212;
    public static final int CURRENT_TIMESTAMP=72;
    public static final int INSERTINTO_EXPR=151;
    public static final int HAVING_EXPR=120;
    public static final int UNIDIRECTIONAL=59;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=196;
    public static final int EVAL_EQUALS_EXPR=124;
    public static final int TIMEPERIOD_MINUTES=84;
    public static final int RSTREAM=56;
    public static final int NOT_LIKE=165;
    public static final int EVENT_LIMIT_EXPR=146;
    public static final int NOT_BETWEEN=164;
    public static final int TIMEPERIOD_MINUTE=83;
    public static final int EVAL_OR_EXPR=123;
    public static final int BAND=221;
    public static final int QUOTED_STRING_LITERAL=220;
    public static final int JOIN=36;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=239;
    public static final int OBSERVER_EXPR=115;
    public static final int EVENT_FILTER_IDENT=105;
    public static final int EVENT_PROP_MAPPED=141;
    public static final int UnicodeEscape=269;
    public static final int AVEDEV=24;
    public static final int DBSELECT_EXPR=167;
    public static final int SELECTION_ELEMENT_EXPR=128;
    public static final int CREATE_WINDOW_SELECT_EXPR=184;
    public static final int INSERTINTO_EXPRCOL=152;
    public static final int WINDOW=5;
    public static final int DESC=55;
    public static final int SELECTION_STREAM=129;
    public static final int SR_ASSIGN=254;
    public static final int DBFROM_CLAUSE=168;
    public static final int LE=228;
    public static final int EVAL_IDENT=126;

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

                if ( ((LA19_0>=IN_SET && LA19_0<=REGEXP)||LA19_0==NOT_EXPR||(LA19_0>=SUM && LA19_0<=AVG)||(LA19_0>=COALESCE && LA19_0<=COUNT)||(LA19_0>=CASE && LA19_0<=CASE2)||(LA19_0>=PREVIOUS && LA19_0<=EXISTS)||(LA19_0>=INSTANCEOF && LA19_0<=CURRENT_TIMESTAMP)||(LA19_0>=EVAL_AND_EXPR && LA19_0<=EVAL_NOTEQUALS_EXPR)||LA19_0==EVENT_PROP_EXPR||(LA19_0>=CONCAT && LA19_0<=LIB_FUNCTION)||LA19_0==ARRAY_EXPR||(LA19_0>=NOT_IN_SET && LA19_0<=NOT_REGEXP)||(LA19_0>=IN_RANGE && LA19_0<=NOT_IN_SUBSELECT_EXPR)||LA19_0==SUBSTITUTION||(LA19_0>=INT_TYPE && LA19_0<=NULL_TYPE)||LA19_0==STAR||(LA19_0>=BAND && LA19_0<=BXOR)||(LA19_0>=LT && LA19_0<=GE)||(LA19_0>=PLUS && LA19_0<=MOD)) ) {
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
    // EsperEPL2Ast.g:118:1: createSelectionListElement : (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr ( IDENT )? ) | ( constant[true] IDENT ) ) ) );
    public final void createSelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:119:2: (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr ( IDENT )? ) | ( constant[true] IDENT ) ) ) )
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
                    // EsperEPL2Ast.g:120:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement485); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:120:31: ( ( eventPropertyExpr ( IDENT )? ) | ( constant[true] IDENT ) )
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
                            // EsperEPL2Ast.g:121:16: ( eventPropertyExpr ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:121:16: ( eventPropertyExpr ( IDENT )? )
                            // EsperEPL2Ast.g:121:17: eventPropertyExpr ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement505);
                            eventPropertyExpr();

                            state._fsp--;

                            // EsperEPL2Ast.g:121:35: ( IDENT )?
                            int alt22=2;
                            int LA22_0 = input.LA(1);

                            if ( (LA22_0==IDENT) ) {
                                alt22=1;
                            }
                            switch (alt22) {
                                case 1 :
                                    // EsperEPL2Ast.g:121:36: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement508); 

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
                            pushFollow(FOLLOW_constant_in_createSelectionListElement530);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement533); 

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
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr569); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr571); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr573); 
            // EsperEPL2Ast.g:127:41: ( valueExpr )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( ((LA25_0>=IN_SET && LA25_0<=REGEXP)||LA25_0==NOT_EXPR||(LA25_0>=SUM && LA25_0<=AVG)||(LA25_0>=COALESCE && LA25_0<=COUNT)||(LA25_0>=CASE && LA25_0<=CASE2)||(LA25_0>=PREVIOUS && LA25_0<=EXISTS)||(LA25_0>=INSTANCEOF && LA25_0<=CURRENT_TIMESTAMP)||(LA25_0>=EVAL_AND_EXPR && LA25_0<=EVAL_NOTEQUALS_EXPR)||LA25_0==EVENT_PROP_EXPR||(LA25_0>=CONCAT && LA25_0<=LIB_FUNCTION)||LA25_0==ARRAY_EXPR||(LA25_0>=NOT_IN_SET && LA25_0<=NOT_REGEXP)||(LA25_0>=IN_RANGE && LA25_0<=NOT_IN_SUBSELECT_EXPR)||LA25_0==SUBSTITUTION||(LA25_0>=INT_TYPE && LA25_0<=NULL_TYPE)||LA25_0==STAR||(LA25_0>=BAND && LA25_0<=BXOR)||(LA25_0>=LT && LA25_0<=GE)||(LA25_0>=PLUS && LA25_0<=MOD)) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // EsperEPL2Ast.g:127:42: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr576);
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
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr594);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr600);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr605);
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
                    pushFollow(FOLLOW_whereClause_in_selectExpr610);
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
                    pushFollow(FOLLOW_groupByClause_in_selectExpr617);
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
                    pushFollow(FOLLOW_havingClause_in_selectExpr624);
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
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr631);
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
                    pushFollow(FOLLOW_orderByClause_in_selectExpr638);
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
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr645);
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
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr662); 

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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr673); 
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
                    pushFollow(FOLLOW_insertIntoExprCol_in_insertIntoExpr676);
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
            match(input,INSERTINTO_EXPRCOL,FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol695); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol697); 
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
            	    match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol700); 

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
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause718); 

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

            pushFollow(FOLLOW_selectionList_in_selectClause733);
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
            pushFollow(FOLLOW_streamExpression_in_fromClause747);
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
            	    pushFollow(FOLLOW_streamExpression_in_fromClause750);
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
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause753);
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
            pushFollow(FOLLOW_selectionListElement_in_selectionList770);
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
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList773);
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
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement789); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:164:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement799); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement801);
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
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement804); 

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
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement818); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement820); 
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
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement823); 

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
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin842);
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
    // EsperEPL2Ast.g:172:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:173:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) )
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
                    // EsperEPL2Ast.g:173:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent856); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent858);
                    eventPropertyExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent860);
                    eventPropertyExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:173:65: ( eventPropertyExpr eventPropertyExpr )*
                    loop43:
                    do {
                        int alt43=2;
                        int LA43_0 = input.LA(1);

                        if ( (LA43_0==EVENT_PROP_EXPR) ) {
                            alt43=1;
                        }


                        switch (alt43) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:173:66: eventPropertyExpr eventPropertyExpr
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent863);
                    	    eventPropertyExpr();

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent865);
                    	    eventPropertyExpr();

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
                    // EsperEPL2Ast.g:174:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent879); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent881);
                    eventPropertyExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent883);
                    eventPropertyExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:174:66: ( eventPropertyExpr eventPropertyExpr )*
                    loop44:
                    do {
                        int alt44=2;
                        int LA44_0 = input.LA(1);

                        if ( (LA44_0==EVENT_PROP_EXPR) ) {
                            alt44=1;
                        }


                        switch (alt44) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:174:67: eventPropertyExpr eventPropertyExpr
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent886);
                    	    eventPropertyExpr();

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent888);
                    	    eventPropertyExpr();

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
                    // EsperEPL2Ast.g:175:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent902); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent904);
                    eventPropertyExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent906);
                    eventPropertyExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:175:65: ( eventPropertyExpr eventPropertyExpr )*
                    loop45:
                    do {
                        int alt45=2;
                        int LA45_0 = input.LA(1);

                        if ( (LA45_0==EVENT_PROP_EXPR) ) {
                            alt45=1;
                        }


                        switch (alt45) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:175:66: eventPropertyExpr eventPropertyExpr
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent909);
                    	    eventPropertyExpr();

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent911);
                    	    eventPropertyExpr();

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
                    // EsperEPL2Ast.g:176:4: ^(i= INNERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent925); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent927);
                    eventPropertyExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent929);
                    eventPropertyExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:176:59: ( eventPropertyExpr eventPropertyExpr )*
                    loop46:
                    do {
                        int alt46=2;
                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==EVENT_PROP_EXPR) ) {
                            alt46=1;
                        }


                        switch (alt46) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:176:60: eventPropertyExpr eventPropertyExpr
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent932);
                    	    eventPropertyExpr();

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent934);
                    	    eventPropertyExpr();

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
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression954); 

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
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression957);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:180:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression961);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:180:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression965);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:180:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression969);
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
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression973);
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
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression978); 

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
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression983); 

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
    // EsperEPL2Ast.g:183:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:184:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:184:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1011); 

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
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1013); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1016); 
            // EsperEPL2Ast.g:184:46: ( valueExpr )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( ((LA54_0>=IN_SET && LA54_0<=REGEXP)||LA54_0==NOT_EXPR||(LA54_0>=SUM && LA54_0<=AVG)||(LA54_0>=COALESCE && LA54_0<=COUNT)||(LA54_0>=CASE && LA54_0<=CASE2)||(LA54_0>=PREVIOUS && LA54_0<=EXISTS)||(LA54_0>=INSTANCEOF && LA54_0<=CURRENT_TIMESTAMP)||(LA54_0>=EVAL_AND_EXPR && LA54_0<=EVAL_NOTEQUALS_EXPR)||LA54_0==EVENT_PROP_EXPR||(LA54_0>=CONCAT && LA54_0<=LIB_FUNCTION)||LA54_0==ARRAY_EXPR||(LA54_0>=NOT_IN_SET && LA54_0<=NOT_REGEXP)||(LA54_0>=IN_RANGE && LA54_0<=NOT_IN_SUBSELECT_EXPR)||LA54_0==SUBSTITUTION||(LA54_0>=INT_TYPE && LA54_0<=NULL_TYPE)||LA54_0==STAR||(LA54_0>=BAND && LA54_0<=BXOR)||(LA54_0>=LT && LA54_0<=GE)||(LA54_0>=PLUS && LA54_0<=MOD)) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // EsperEPL2Ast.g:184:47: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1019);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop54;
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


    // $ANTLR start "patternInclusionExpression"
    // EsperEPL2Ast.g:187:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:188:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:188:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1040); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression1042);
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
    // EsperEPL2Ast.g:191:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:192:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:192:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1059); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression1061); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:192:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( ((LA55_0>=STRING_LITERAL && LA55_0<=QUOTED_STRING_LITERAL)) ) {
                alt55=1;
            }
            switch (alt55) {
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
    // EsperEPL2Ast.g:195:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:196:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:196:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1092); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression1094); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression1096); 
            // EsperEPL2Ast.g:196:41: ( valueExpr )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( ((LA56_0>=IN_SET && LA56_0<=REGEXP)||LA56_0==NOT_EXPR||(LA56_0>=SUM && LA56_0<=AVG)||(LA56_0>=COALESCE && LA56_0<=COUNT)||(LA56_0>=CASE && LA56_0<=CASE2)||(LA56_0>=PREVIOUS && LA56_0<=EXISTS)||(LA56_0>=INSTANCEOF && LA56_0<=CURRENT_TIMESTAMP)||(LA56_0>=EVAL_AND_EXPR && LA56_0<=EVAL_NOTEQUALS_EXPR)||LA56_0==EVENT_PROP_EXPR||(LA56_0>=CONCAT && LA56_0<=LIB_FUNCTION)||LA56_0==ARRAY_EXPR||(LA56_0>=NOT_IN_SET && LA56_0<=NOT_REGEXP)||(LA56_0>=IN_RANGE && LA56_0<=NOT_IN_SUBSELECT_EXPR)||LA56_0==SUBSTITUTION||(LA56_0>=INT_TYPE && LA56_0<=NULL_TYPE)||LA56_0==STAR||(LA56_0>=BAND && LA56_0<=BXOR)||(LA56_0>=LT && LA56_0<=GE)||(LA56_0>=PLUS && LA56_0<=MOD)) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // EsperEPL2Ast.g:196:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression1099);
            	    valueExpr();

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
    // $ANTLR end "methodJoinExpression"


    // $ANTLR start "viewListExpr"
    // EsperEPL2Ast.g:199:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:200:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:200:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr1113);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:200:13: ( viewExpr )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==VIEW_EXPR) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // EsperEPL2Ast.g:200:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr1116);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop57;
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
    // EsperEPL2Ast.g:203:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:204:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:204:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr1133); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1135); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1137); 
            // EsperEPL2Ast.g:204:30: ( valueExprWithTime )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( ((LA58_0>=IN_SET && LA58_0<=REGEXP)||LA58_0==NOT_EXPR||(LA58_0>=SUM && LA58_0<=AVG)||(LA58_0>=COALESCE && LA58_0<=COUNT)||(LA58_0>=CASE && LA58_0<=CASE2)||LA58_0==LAST||(LA58_0>=PREVIOUS && LA58_0<=EXISTS)||(LA58_0>=LW && LA58_0<=CURRENT_TIMESTAMP)||(LA58_0>=NUMERIC_PARAM_RANGE && LA58_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA58_0>=EVAL_AND_EXPR && LA58_0<=EVAL_NOTEQUALS_EXPR)||LA58_0==EVENT_PROP_EXPR||(LA58_0>=CONCAT && LA58_0<=LIB_FUNCTION)||(LA58_0>=TIME_PERIOD && LA58_0<=ARRAY_EXPR)||(LA58_0>=NOT_IN_SET && LA58_0<=NOT_REGEXP)||(LA58_0>=IN_RANGE && LA58_0<=NOT_IN_SUBSELECT_EXPR)||(LA58_0>=LAST_OPERATOR && LA58_0<=SUBSTITUTION)||(LA58_0>=NUMBERSETSTAR && LA58_0<=NULL_TYPE)||LA58_0==STAR||(LA58_0>=BAND && LA58_0<=BXOR)||(LA58_0>=LT && LA58_0<=GE)||(LA58_0>=PLUS && LA58_0<=MOD)) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // EsperEPL2Ast.g:204:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr1140);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop58;
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
    // EsperEPL2Ast.g:207:1: whereClause : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:208:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:208:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause1161); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause1163);
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
    // EsperEPL2Ast.g:211:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:212:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:212:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause1181); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause1183);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:212:32: ( valueExpr )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( ((LA59_0>=IN_SET && LA59_0<=REGEXP)||LA59_0==NOT_EXPR||(LA59_0>=SUM && LA59_0<=AVG)||(LA59_0>=COALESCE && LA59_0<=COUNT)||(LA59_0>=CASE && LA59_0<=CASE2)||(LA59_0>=PREVIOUS && LA59_0<=EXISTS)||(LA59_0>=INSTANCEOF && LA59_0<=CURRENT_TIMESTAMP)||(LA59_0>=EVAL_AND_EXPR && LA59_0<=EVAL_NOTEQUALS_EXPR)||LA59_0==EVENT_PROP_EXPR||(LA59_0>=CONCAT && LA59_0<=LIB_FUNCTION)||LA59_0==ARRAY_EXPR||(LA59_0>=NOT_IN_SET && LA59_0<=NOT_REGEXP)||(LA59_0>=IN_RANGE && LA59_0<=NOT_IN_SUBSELECT_EXPR)||LA59_0==SUBSTITUTION||(LA59_0>=INT_TYPE && LA59_0<=NULL_TYPE)||LA59_0==STAR||(LA59_0>=BAND && LA59_0<=BXOR)||(LA59_0>=LT && LA59_0<=GE)||(LA59_0>=PLUS && LA59_0<=MOD)) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // EsperEPL2Ast.g:212:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause1186);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop59;
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
    // EsperEPL2Ast.g:215:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:216:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:216:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause1204); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause1206);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:216:35: ( orderByElement )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==ORDER_ELEMENT_EXPR) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // EsperEPL2Ast.g:216:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause1209);
            	    orderByElement();

            	    state._fsp--;


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
    // $ANTLR end "orderByClause"


    // $ANTLR start "orderByElement"
    // EsperEPL2Ast.g:219:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:220:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:220:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1229); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement1231);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:220:38: ( ASC | DESC )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( ((LA61_0>=ASC && LA61_0<=DESC)) ) {
                alt61=1;
            }
            switch (alt61) {
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
    // EsperEPL2Ast.g:223:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:224:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:224:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause1256); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause1258);
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
    // EsperEPL2Ast.g:227:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;

        try {
            // EsperEPL2Ast.g:228:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) )
            int alt68=4;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt68=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt68=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt68=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt68=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }

            switch (alt68) {
                case 1 :
                    // EsperEPL2Ast.g:228:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1276); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:228:25: ( ALL | FIRST | LAST | SNAPSHOT )?
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

                    // EsperEPL2Ast.g:228:52: ( number | IDENT )
                    int alt63=2;
                    int LA63_0 = input.LA(1);

                    if ( ((LA63_0>=INT_TYPE && LA63_0<=DOUBLE_TYPE)) ) {
                        alt63=1;
                    }
                    else if ( (LA63_0==IDENT) ) {
                        alt63=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 63, 0, input);

                        throw nvae;
                    }
                    switch (alt63) {
                        case 1 :
                            // EsperEPL2Ast.g:228:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr1290);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:228:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr1292); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:229:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1309); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:229:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt64=2;
                    int LA64_0 = input.LA(1);

                    if ( (LA64_0==ALL||(LA64_0>=FIRST && LA64_0<=LAST)||LA64_0==SNAPSHOT) ) {
                        alt64=1;
                    }
                    switch (alt64) {
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr1322);
                    timePeriod();

                    state._fsp--;

                     leaveNode(tp); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:230:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1337); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:230:33: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt65=2;
                    int LA65_0 = input.LA(1);

                    if ( (LA65_0==ALL||(LA65_0>=FIRST && LA65_0<=LAST)||LA65_0==SNAPSHOT) ) {
                        alt65=1;
                    }
                    switch (alt65) {
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr1350);
                    crontabLimitParameterSet();

                    state._fsp--;

                     leaveNode(cron); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:231:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr1365); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:231:30: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt66=2;
                    int LA66_0 = input.LA(1);

                    if ( (LA66_0==ALL||(LA66_0>=FIRST && LA66_0<=LAST)||LA66_0==SNAPSHOT) ) {
                        alt66=1;
                    }
                    switch (alt66) {
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr1378);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:231:67: ( onSetExpr )?
                    int alt67=2;
                    int LA67_0 = input.LA(1);

                    if ( (LA67_0==ON_SET_EXPR) ) {
                        alt67=1;
                    }
                    switch (alt67) {
                        case 1 :
                            // EsperEPL2Ast.g:231:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr1380);
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
    // EsperEPL2Ast.g:234:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:235:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:235:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause1399); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:235:23: ( number | IDENT )
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( ((LA69_0>=INT_TYPE && LA69_0<=DOUBLE_TYPE)) ) {
                alt69=1;
            }
            else if ( (LA69_0==IDENT) ) {
                alt69=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 69, 0, input);

                throw nvae;
            }
            switch (alt69) {
                case 1 :
                    // EsperEPL2Ast.g:235:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause1402);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:235:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause1404); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:235:38: ( number | IDENT )?
            int alt70=3;
            int LA70_0 = input.LA(1);

            if ( ((LA70_0>=INT_TYPE && LA70_0<=DOUBLE_TYPE)) ) {
                alt70=1;
            }
            else if ( (LA70_0==IDENT) ) {
                alt70=2;
            }
            switch (alt70) {
                case 1 :
                    // EsperEPL2Ast.g:235:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause1408);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:235:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause1410); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:235:54: ( COMMA )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==COMMA) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // EsperEPL2Ast.g:235:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause1414); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:235:61: ( OFFSET )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==OFFSET) ) {
                alt72=1;
            }
            switch (alt72) {
                case 1 :
                    // EsperEPL2Ast.g:235:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause1417); 

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
    // EsperEPL2Ast.g:238:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:239:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:239:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet1435); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1437);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1439);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1441);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1443);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1445);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:239:121: ( valueExprWithTime )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( ((LA73_0>=IN_SET && LA73_0<=REGEXP)||LA73_0==NOT_EXPR||(LA73_0>=SUM && LA73_0<=AVG)||(LA73_0>=COALESCE && LA73_0<=COUNT)||(LA73_0>=CASE && LA73_0<=CASE2)||LA73_0==LAST||(LA73_0>=PREVIOUS && LA73_0<=EXISTS)||(LA73_0>=LW && LA73_0<=CURRENT_TIMESTAMP)||(LA73_0>=NUMERIC_PARAM_RANGE && LA73_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA73_0>=EVAL_AND_EXPR && LA73_0<=EVAL_NOTEQUALS_EXPR)||LA73_0==EVENT_PROP_EXPR||(LA73_0>=CONCAT && LA73_0<=LIB_FUNCTION)||(LA73_0>=TIME_PERIOD && LA73_0<=ARRAY_EXPR)||(LA73_0>=NOT_IN_SET && LA73_0<=NOT_REGEXP)||(LA73_0>=IN_RANGE && LA73_0<=NOT_IN_SUBSELECT_EXPR)||(LA73_0>=LAST_OPERATOR && LA73_0<=SUBSTITUTION)||(LA73_0>=NUMBERSETSTAR && LA73_0<=NULL_TYPE)||LA73_0==STAR||(LA73_0>=BAND && LA73_0<=BXOR)||(LA73_0>=LT && LA73_0<=GE)||(LA73_0>=PLUS && LA73_0<=MOD)) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // EsperEPL2Ast.g:239:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1447);
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
    // EsperEPL2Ast.g:242:1: relationalExpr : ( ^(n= LT valueExpr valueExpr ) | ^(n= GT valueExpr valueExpr ) | ^(n= LE valueExpr valueExpr ) | ^(n= GE valueExpr valueExpr ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:243:2: ( ^(n= LT valueExpr valueExpr ) | ^(n= GT valueExpr valueExpr ) | ^(n= LE valueExpr valueExpr ) | ^(n= GE valueExpr valueExpr ) )
            int alt74=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt74=1;
                }
                break;
            case GT:
                {
                alt74=2;
                }
                break;
            case LE:
                {
                alt74=3;
                }
                break;
            case GE:
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
                    // EsperEPL2Ast.g:243:5: ^(n= LT valueExpr valueExpr )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr1464); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1466);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1468);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(n); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:244:5: ^(n= GT valueExpr valueExpr )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr1480); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1482);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1484);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(n); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:245:5: ^(n= LE valueExpr valueExpr )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr1496); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1498);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1500);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(n); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:246:4: ^(n= GE valueExpr valueExpr )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr1511); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1513);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1515);
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
    // EsperEPL2Ast.g:249:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:250:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt77=6;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt77=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt77=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt77=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt77=4;
                }
                break;
            case NOT_EXPR:
                {
                alt77=5;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt77=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;
            }

            switch (alt77) {
                case 1 :
                    // EsperEPL2Ast.g:250:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice1532); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1534);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1536);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:250:42: ( valueExpr )*
                    loop75:
                    do {
                        int alt75=2;
                        int LA75_0 = input.LA(1);

                        if ( ((LA75_0>=IN_SET && LA75_0<=REGEXP)||LA75_0==NOT_EXPR||(LA75_0>=SUM && LA75_0<=AVG)||(LA75_0>=COALESCE && LA75_0<=COUNT)||(LA75_0>=CASE && LA75_0<=CASE2)||(LA75_0>=PREVIOUS && LA75_0<=EXISTS)||(LA75_0>=INSTANCEOF && LA75_0<=CURRENT_TIMESTAMP)||(LA75_0>=EVAL_AND_EXPR && LA75_0<=EVAL_NOTEQUALS_EXPR)||LA75_0==EVENT_PROP_EXPR||(LA75_0>=CONCAT && LA75_0<=LIB_FUNCTION)||LA75_0==ARRAY_EXPR||(LA75_0>=NOT_IN_SET && LA75_0<=NOT_REGEXP)||(LA75_0>=IN_RANGE && LA75_0<=NOT_IN_SUBSELECT_EXPR)||LA75_0==SUBSTITUTION||(LA75_0>=INT_TYPE && LA75_0<=NULL_TYPE)||LA75_0==STAR||(LA75_0>=BAND && LA75_0<=BXOR)||(LA75_0>=LT && LA75_0<=GE)||(LA75_0>=PLUS && LA75_0<=MOD)) ) {
                            alt75=1;
                        }


                        switch (alt75) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:250:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1539);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop75;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:251:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice1553); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1555);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1557);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:251:43: ( valueExpr )*
                    loop76:
                    do {
                        int alt76=2;
                        int LA76_0 = input.LA(1);

                        if ( ((LA76_0>=IN_SET && LA76_0<=REGEXP)||LA76_0==NOT_EXPR||(LA76_0>=SUM && LA76_0<=AVG)||(LA76_0>=COALESCE && LA76_0<=COUNT)||(LA76_0>=CASE && LA76_0<=CASE2)||(LA76_0>=PREVIOUS && LA76_0<=EXISTS)||(LA76_0>=INSTANCEOF && LA76_0<=CURRENT_TIMESTAMP)||(LA76_0>=EVAL_AND_EXPR && LA76_0<=EVAL_NOTEQUALS_EXPR)||LA76_0==EVENT_PROP_EXPR||(LA76_0>=CONCAT && LA76_0<=LIB_FUNCTION)||LA76_0==ARRAY_EXPR||(LA76_0>=NOT_IN_SET && LA76_0<=NOT_REGEXP)||(LA76_0>=IN_RANGE && LA76_0<=NOT_IN_SUBSELECT_EXPR)||LA76_0==SUBSTITUTION||(LA76_0>=INT_TYPE && LA76_0<=NULL_TYPE)||LA76_0==STAR||(LA76_0>=BAND && LA76_0<=BXOR)||(LA76_0>=LT && LA76_0<=GE)||(LA76_0>=PLUS && LA76_0<=MOD)) ) {
                            alt76=1;
                        }


                        switch (alt76) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:251:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1560);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop76;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:252:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice1574); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1576);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1578);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:253:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice1590); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1592);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1594);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:254:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice1606); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1608);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:255:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice1619);
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
    // EsperEPL2Ast.g:258:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:259:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr )
            int alt78=16;
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
            case SUBSTITUTION:
                {
                alt78=2;
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
                alt78=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt78=4;
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
                alt78=5;
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
                alt78=6;
                }
                break;
            case LIB_FUNCTION:
                {
                alt78=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt78=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt78=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt78=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt78=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt78=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt78=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt78=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt78=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt78=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }

            switch (alt78) {
                case 1 :
                    // EsperEPL2Ast.g:259:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr1632);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:260:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr1638);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:261:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr1644);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:262:5: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr1651);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:263:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr1659);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:264:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr1664);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:265:7: libFunc
                    {
                    pushFollow(FOLLOW_libFunc_in_valueExpr1672);
                    libFunc();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:266:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr1677);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:267:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr1682);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:268:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr1688);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:269:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr1693);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:270:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr1698);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:271:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr1703);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:272:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr1708);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:273:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr1714);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:274:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr1721);
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
    // EsperEPL2Ast.g:277:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:278:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt80=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt80=1;
                }
                break;
            case LW:
                {
                alt80=2;
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
                alt80=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt80=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt80=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt80=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt80=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt80=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt80=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt80=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt80=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }

            switch (alt80) {
                case 1 :
                    // EsperEPL2Ast.g:278:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime1734); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:279:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime1743); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:280:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime1750);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:281:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime1758); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime1760);
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
                    // EsperEPL2Ast.g:282:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime1775);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:283:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime1781);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:284:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime1786);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:285:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime1791);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:286:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime1801); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:286:29: ( numericParameterList )+
                    int cnt79=0;
                    loop79:
                    do {
                        int alt79=2;
                        int LA79_0 = input.LA(1);

                        if ( (LA79_0==NUMERIC_PARAM_RANGE||LA79_0==NUMERIC_PARAM_FREQUENCY||(LA79_0>=INT_TYPE && LA79_0<=NULL_TYPE)) ) {
                            alt79=1;
                        }


                        switch (alt79) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:286:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime1803);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt79 >= 1 ) break loop79;
                                EarlyExitException eee =
                                    new EarlyExitException(79, input);
                                throw eee;
                        }
                        cnt79++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:287:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime1814); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:288:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime1821);
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
    // EsperEPL2Ast.g:291:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:292:2: ( constant[true] | rangeOperator | frequencyOperator )
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
            case NUMERIC_PARAM_RANGE:
                {
                alt81=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
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
                    // EsperEPL2Ast.g:292:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList1834);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:293:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList1841);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:294:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList1847);
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
    // EsperEPL2Ast.g:297:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr | substitution ) ( constant[true] | eventPropertyExpr | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:298:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr | substitution ) ( constant[true] | eventPropertyExpr | substitution ) ) )
            // EsperEPL2Ast.g:298:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr | substitution ) ( constant[true] | eventPropertyExpr | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator1863); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:298:29: ( constant[true] | eventPropertyExpr | substitution )
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
                    // EsperEPL2Ast.g:298:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator1866);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:298:45: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator1869);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:298:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator1871);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:298:77: ( constant[true] | eventPropertyExpr | substitution )
            int alt83=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt83=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt83=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt83=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 83, 0, input);

                throw nvae;
            }

            switch (alt83) {
                case 1 :
                    // EsperEPL2Ast.g:298:78: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator1875);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:298:93: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator1878);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:298:111: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator1880);
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
    // EsperEPL2Ast.g:301:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:302:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr | substitution ) ) )
            // EsperEPL2Ast.g:302:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator1901); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:302:33: ( constant[true] | eventPropertyExpr | substitution )
            int alt84=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt84=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt84=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:302:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator1904);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:302:49: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator1907);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:302:67: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator1909);
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
    // EsperEPL2Ast.g:305:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:306:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr | substitution ) ) )
            // EsperEPL2Ast.g:306:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator1928); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:306:23: ( constant[true] | eventPropertyExpr | substitution )
            int alt85=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt85=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt85=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt85=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;
            }

            switch (alt85) {
                case 1 :
                    // EsperEPL2Ast.g:306:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator1931);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:306:39: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator1934);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:306:57: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator1936);
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
    // EsperEPL2Ast.g:309:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:310:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr | substitution ) ) )
            // EsperEPL2Ast.g:310:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator1955); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:310:26: ( constant[true] | eventPropertyExpr | substitution )
            int alt86=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt86=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt86=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:310:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator1958);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:310:42: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator1961);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:310:60: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator1963);
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
    // EsperEPL2Ast.g:313:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:314:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:314:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr1984); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr1986);
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
    // EsperEPL2Ast.g:317:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:318:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:318:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2005); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr2007);
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
    // EsperEPL2Ast.g:321:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:322:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==IN_SUBSELECT_EXPR) ) {
                alt87=1;
            }
            else if ( (LA87_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt87=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 87, 0, input);

                throw nvae;
            }
            switch (alt87) {
                case 1 :
                    // EsperEPL2Ast.g:322:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2026); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2028);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2030);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:323:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2042); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2044);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2046);
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
    // EsperEPL2Ast.g:326:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:327:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:327:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2065); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr2067);
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
    // EsperEPL2Ast.g:330:1: subQueryExpr : selectionListElement subSelectFilterExpr ( whereClause )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:331:2: ( selectionListElement subSelectFilterExpr ( whereClause )? )
            // EsperEPL2Ast.g:331:4: selectionListElement subSelectFilterExpr ( whereClause )?
            {
            pushFollow(FOLLOW_selectionListElement_in_subQueryExpr2083);
            selectionListElement();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr2085);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:331:45: ( whereClause )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==WHERE_EXPR) ) {
                alt88=1;
            }
            switch (alt88) {
                case 1 :
                    // EsperEPL2Ast.g:331:46: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr2088);
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
    // EsperEPL2Ast.g:334:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:335:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:335:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2105); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr2107);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:335:36: ( viewListExpr )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==VIEW_EXPR) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // EsperEPL2Ast.g:335:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr2110);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:335:52: ( IDENT )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==IDENT) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // EsperEPL2Ast.g:335:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr2115); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:335:61: ( RETAINUNION )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==RETAINUNION) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // EsperEPL2Ast.g:335:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr2119); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:335:74: ( RETAININTERSECTION )?
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==RETAININTERSECTION) ) {
                alt92=1;
            }
            switch (alt92) {
                case 1 :
                    // EsperEPL2Ast.g:335:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr2122); 

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
    // EsperEPL2Ast.g:338:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:339:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==CASE) ) {
                alt95=1;
            }
            else if ( (LA95_0==CASE2) ) {
                alt95=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }
            switch (alt95) {
                case 1 :
                    // EsperEPL2Ast.g:339:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr2142); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:339:13: ( valueExpr )*
                        loop93:
                        do {
                            int alt93=2;
                            int LA93_0 = input.LA(1);

                            if ( ((LA93_0>=IN_SET && LA93_0<=REGEXP)||LA93_0==NOT_EXPR||(LA93_0>=SUM && LA93_0<=AVG)||(LA93_0>=COALESCE && LA93_0<=COUNT)||(LA93_0>=CASE && LA93_0<=CASE2)||(LA93_0>=PREVIOUS && LA93_0<=EXISTS)||(LA93_0>=INSTANCEOF && LA93_0<=CURRENT_TIMESTAMP)||(LA93_0>=EVAL_AND_EXPR && LA93_0<=EVAL_NOTEQUALS_EXPR)||LA93_0==EVENT_PROP_EXPR||(LA93_0>=CONCAT && LA93_0<=LIB_FUNCTION)||LA93_0==ARRAY_EXPR||(LA93_0>=NOT_IN_SET && LA93_0<=NOT_REGEXP)||(LA93_0>=IN_RANGE && LA93_0<=NOT_IN_SUBSELECT_EXPR)||LA93_0==SUBSTITUTION||(LA93_0>=INT_TYPE && LA93_0<=NULL_TYPE)||LA93_0==STAR||(LA93_0>=BAND && LA93_0<=BXOR)||(LA93_0>=LT && LA93_0<=GE)||(LA93_0>=PLUS && LA93_0<=MOD)) ) {
                                alt93=1;
                            }


                            switch (alt93) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:339:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2145);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop93;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:340:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr2158); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:340:14: ( valueExpr )*
                        loop94:
                        do {
                            int alt94=2;
                            int LA94_0 = input.LA(1);

                            if ( ((LA94_0>=IN_SET && LA94_0<=REGEXP)||LA94_0==NOT_EXPR||(LA94_0>=SUM && LA94_0<=AVG)||(LA94_0>=COALESCE && LA94_0<=COUNT)||(LA94_0>=CASE && LA94_0<=CASE2)||(LA94_0>=PREVIOUS && LA94_0<=EXISTS)||(LA94_0>=INSTANCEOF && LA94_0<=CURRENT_TIMESTAMP)||(LA94_0>=EVAL_AND_EXPR && LA94_0<=EVAL_NOTEQUALS_EXPR)||LA94_0==EVENT_PROP_EXPR||(LA94_0>=CONCAT && LA94_0<=LIB_FUNCTION)||LA94_0==ARRAY_EXPR||(LA94_0>=NOT_IN_SET && LA94_0<=NOT_REGEXP)||(LA94_0>=IN_RANGE && LA94_0<=NOT_IN_SUBSELECT_EXPR)||LA94_0==SUBSTITUTION||(LA94_0>=INT_TYPE && LA94_0<=NULL_TYPE)||LA94_0==STAR||(LA94_0>=BAND && LA94_0<=BXOR)||(LA94_0>=LT && LA94_0<=GE)||(LA94_0>=PLUS && LA94_0<=MOD)) ) {
                                alt94=1;
                            }


                            switch (alt94) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:340:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2161);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop94;
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
    // EsperEPL2Ast.g:343:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:344:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt98=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt98=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt98=2;
                }
                break;
            case IN_RANGE:
                {
                alt98=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt98=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 98, 0, input);

                throw nvae;
            }

            switch (alt98) {
                case 1 :
                    // EsperEPL2Ast.g:344:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr2181); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2183);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2191);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:344:51: ( valueExpr )*
                    loop96:
                    do {
                        int alt96=2;
                        int LA96_0 = input.LA(1);

                        if ( ((LA96_0>=IN_SET && LA96_0<=REGEXP)||LA96_0==NOT_EXPR||(LA96_0>=SUM && LA96_0<=AVG)||(LA96_0>=COALESCE && LA96_0<=COUNT)||(LA96_0>=CASE && LA96_0<=CASE2)||(LA96_0>=PREVIOUS && LA96_0<=EXISTS)||(LA96_0>=INSTANCEOF && LA96_0<=CURRENT_TIMESTAMP)||(LA96_0>=EVAL_AND_EXPR && LA96_0<=EVAL_NOTEQUALS_EXPR)||LA96_0==EVENT_PROP_EXPR||(LA96_0>=CONCAT && LA96_0<=LIB_FUNCTION)||LA96_0==ARRAY_EXPR||(LA96_0>=NOT_IN_SET && LA96_0<=NOT_REGEXP)||(LA96_0>=IN_RANGE && LA96_0<=NOT_IN_SUBSELECT_EXPR)||LA96_0==SUBSTITUTION||(LA96_0>=INT_TYPE && LA96_0<=NULL_TYPE)||LA96_0==STAR||(LA96_0>=BAND && LA96_0<=BXOR)||(LA96_0>=LT && LA96_0<=GE)||(LA96_0>=PLUS && LA96_0<=MOD)) ) {
                            alt96=1;
                        }


                        switch (alt96) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:344:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr2194);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop96;
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
                    // EsperEPL2Ast.g:345:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr2213); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2215);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2223);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:345:55: ( valueExpr )*
                    loop97:
                    do {
                        int alt97=2;
                        int LA97_0 = input.LA(1);

                        if ( ((LA97_0>=IN_SET && LA97_0<=REGEXP)||LA97_0==NOT_EXPR||(LA97_0>=SUM && LA97_0<=AVG)||(LA97_0>=COALESCE && LA97_0<=COUNT)||(LA97_0>=CASE && LA97_0<=CASE2)||(LA97_0>=PREVIOUS && LA97_0<=EXISTS)||(LA97_0>=INSTANCEOF && LA97_0<=CURRENT_TIMESTAMP)||(LA97_0>=EVAL_AND_EXPR && LA97_0<=EVAL_NOTEQUALS_EXPR)||LA97_0==EVENT_PROP_EXPR||(LA97_0>=CONCAT && LA97_0<=LIB_FUNCTION)||LA97_0==ARRAY_EXPR||(LA97_0>=NOT_IN_SET && LA97_0<=NOT_REGEXP)||(LA97_0>=IN_RANGE && LA97_0<=NOT_IN_SUBSELECT_EXPR)||LA97_0==SUBSTITUTION||(LA97_0>=INT_TYPE && LA97_0<=NULL_TYPE)||LA97_0==STAR||(LA97_0>=BAND && LA97_0<=BXOR)||(LA97_0>=LT && LA97_0<=GE)||(LA97_0>=PLUS && LA97_0<=MOD)) ) {
                            alt97=1;
                        }


                        switch (alt97) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:345:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr2226);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop97;
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
                    // EsperEPL2Ast.g:346:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr2245); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2247);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2255);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr2257);
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
                    // EsperEPL2Ast.g:347:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr2274); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2276);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2284);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr2286);
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
    // EsperEPL2Ast.g:350:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:351:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==BETWEEN) ) {
                alt100=1;
            }
            else if ( (LA100_0==NOT_BETWEEN) ) {
                alt100=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                throw nvae;
            }
            switch (alt100) {
                case 1 :
                    // EsperEPL2Ast.g:351:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr2311); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2313);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2315);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2317);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:352:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr2328); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2330);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2332);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:352:40: ( valueExpr )*
                    loop99:
                    do {
                        int alt99=2;
                        int LA99_0 = input.LA(1);

                        if ( ((LA99_0>=IN_SET && LA99_0<=REGEXP)||LA99_0==NOT_EXPR||(LA99_0>=SUM && LA99_0<=AVG)||(LA99_0>=COALESCE && LA99_0<=COUNT)||(LA99_0>=CASE && LA99_0<=CASE2)||(LA99_0>=PREVIOUS && LA99_0<=EXISTS)||(LA99_0>=INSTANCEOF && LA99_0<=CURRENT_TIMESTAMP)||(LA99_0>=EVAL_AND_EXPR && LA99_0<=EVAL_NOTEQUALS_EXPR)||LA99_0==EVENT_PROP_EXPR||(LA99_0>=CONCAT && LA99_0<=LIB_FUNCTION)||LA99_0==ARRAY_EXPR||(LA99_0>=NOT_IN_SET && LA99_0<=NOT_REGEXP)||(LA99_0>=IN_RANGE && LA99_0<=NOT_IN_SUBSELECT_EXPR)||LA99_0==SUBSTITUTION||(LA99_0>=INT_TYPE && LA99_0<=NULL_TYPE)||LA99_0==STAR||(LA99_0>=BAND && LA99_0<=BXOR)||(LA99_0>=LT && LA99_0<=GE)||(LA99_0>=PLUS && LA99_0<=MOD)) ) {
                            alt99=1;
                        }


                        switch (alt99) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:352:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr2335);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop99;
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
    // EsperEPL2Ast.g:355:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:356:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==LIKE) ) {
                alt103=1;
            }
            else if ( (LA103_0==NOT_LIKE) ) {
                alt103=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;
            }
            switch (alt103) {
                case 1 :
                    // EsperEPL2Ast.g:356:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr2355); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr2357);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr2359);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:356:33: ( valueExpr )?
                    int alt101=2;
                    int LA101_0 = input.LA(1);

                    if ( ((LA101_0>=IN_SET && LA101_0<=REGEXP)||LA101_0==NOT_EXPR||(LA101_0>=SUM && LA101_0<=AVG)||(LA101_0>=COALESCE && LA101_0<=COUNT)||(LA101_0>=CASE && LA101_0<=CASE2)||(LA101_0>=PREVIOUS && LA101_0<=EXISTS)||(LA101_0>=INSTANCEOF && LA101_0<=CURRENT_TIMESTAMP)||(LA101_0>=EVAL_AND_EXPR && LA101_0<=EVAL_NOTEQUALS_EXPR)||LA101_0==EVENT_PROP_EXPR||(LA101_0>=CONCAT && LA101_0<=LIB_FUNCTION)||LA101_0==ARRAY_EXPR||(LA101_0>=NOT_IN_SET && LA101_0<=NOT_REGEXP)||(LA101_0>=IN_RANGE && LA101_0<=NOT_IN_SUBSELECT_EXPR)||LA101_0==SUBSTITUTION||(LA101_0>=INT_TYPE && LA101_0<=NULL_TYPE)||LA101_0==STAR||(LA101_0>=BAND && LA101_0<=BXOR)||(LA101_0>=LT && LA101_0<=GE)||(LA101_0>=PLUS && LA101_0<=MOD)) ) {
                        alt101=1;
                    }
                    switch (alt101) {
                        case 1 :
                            // EsperEPL2Ast.g:356:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr2362);
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
                    // EsperEPL2Ast.g:357:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr2375); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr2377);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr2379);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:357:37: ( valueExpr )?
                    int alt102=2;
                    int LA102_0 = input.LA(1);

                    if ( ((LA102_0>=IN_SET && LA102_0<=REGEXP)||LA102_0==NOT_EXPR||(LA102_0>=SUM && LA102_0<=AVG)||(LA102_0>=COALESCE && LA102_0<=COUNT)||(LA102_0>=CASE && LA102_0<=CASE2)||(LA102_0>=PREVIOUS && LA102_0<=EXISTS)||(LA102_0>=INSTANCEOF && LA102_0<=CURRENT_TIMESTAMP)||(LA102_0>=EVAL_AND_EXPR && LA102_0<=EVAL_NOTEQUALS_EXPR)||LA102_0==EVENT_PROP_EXPR||(LA102_0>=CONCAT && LA102_0<=LIB_FUNCTION)||LA102_0==ARRAY_EXPR||(LA102_0>=NOT_IN_SET && LA102_0<=NOT_REGEXP)||(LA102_0>=IN_RANGE && LA102_0<=NOT_IN_SUBSELECT_EXPR)||LA102_0==SUBSTITUTION||(LA102_0>=INT_TYPE && LA102_0<=NULL_TYPE)||LA102_0==STAR||(LA102_0>=BAND && LA102_0<=BXOR)||(LA102_0>=LT && LA102_0<=GE)||(LA102_0>=PLUS && LA102_0<=MOD)) ) {
                        alt102=1;
                    }
                    switch (alt102) {
                        case 1 :
                            // EsperEPL2Ast.g:357:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr2382);
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
    // EsperEPL2Ast.g:360:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:361:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==REGEXP) ) {
                alt104=1;
            }
            else if ( (LA104_0==NOT_REGEXP) ) {
                alt104=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 104, 0, input);

                throw nvae;
            }
            switch (alt104) {
                case 1 :
                    // EsperEPL2Ast.g:361:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr2401); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2403);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2405);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:362:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr2416); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2418);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2420);
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
    // EsperEPL2Ast.g:365:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr eventPropertyExpr ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:366:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr eventPropertyExpr ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt114=13;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt114=1;
                }
                break;
            case AVG:
                {
                alt114=2;
                }
                break;
            case COUNT:
                {
                alt114=3;
                }
                break;
            case MEDIAN:
                {
                alt114=4;
                }
                break;
            case STDDEV:
                {
                alt114=5;
                }
                break;
            case AVEDEV:
                {
                alt114=6;
                }
                break;
            case COALESCE:
                {
                alt114=7;
                }
                break;
            case PREVIOUS:
                {
                alt114=8;
                }
                break;
            case PRIOR:
                {
                alt114=9;
                }
                break;
            case INSTANCEOF:
                {
                alt114=10;
                }
                break;
            case CAST:
                {
                alt114=11;
                }
                break;
            case EXISTS:
                {
                alt114=12;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt114=13;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 114, 0, input);

                throw nvae;
            }

            switch (alt114) {
                case 1 :
                    // EsperEPL2Ast.g:366:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc2439); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:366:13: ( DISTINCT )?
                    int alt105=2;
                    int LA105_0 = input.LA(1);

                    if ( (LA105_0==DISTINCT) ) {
                        alt105=1;
                    }
                    switch (alt105) {
                        case 1 :
                            // EsperEPL2Ast.g:366:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2442); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2446);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:367:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc2457); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:367:12: ( DISTINCT )?
                    int alt106=2;
                    int LA106_0 = input.LA(1);

                    if ( (LA106_0==DISTINCT) ) {
                        alt106=1;
                    }
                    switch (alt106) {
                        case 1 :
                            // EsperEPL2Ast.g:367:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2460); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2464);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:368:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc2475); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:368:14: ( ( DISTINCT )? valueExpr )?
                        int alt108=2;
                        int LA108_0 = input.LA(1);

                        if ( ((LA108_0>=IN_SET && LA108_0<=REGEXP)||LA108_0==NOT_EXPR||(LA108_0>=SUM && LA108_0<=AVG)||(LA108_0>=COALESCE && LA108_0<=COUNT)||(LA108_0>=CASE && LA108_0<=CASE2)||LA108_0==DISTINCT||(LA108_0>=PREVIOUS && LA108_0<=EXISTS)||(LA108_0>=INSTANCEOF && LA108_0<=CURRENT_TIMESTAMP)||(LA108_0>=EVAL_AND_EXPR && LA108_0<=EVAL_NOTEQUALS_EXPR)||LA108_0==EVENT_PROP_EXPR||(LA108_0>=CONCAT && LA108_0<=LIB_FUNCTION)||LA108_0==ARRAY_EXPR||(LA108_0>=NOT_IN_SET && LA108_0<=NOT_REGEXP)||(LA108_0>=IN_RANGE && LA108_0<=NOT_IN_SUBSELECT_EXPR)||LA108_0==SUBSTITUTION||(LA108_0>=INT_TYPE && LA108_0<=NULL_TYPE)||LA108_0==STAR||(LA108_0>=BAND && LA108_0<=BXOR)||(LA108_0>=LT && LA108_0<=GE)||(LA108_0>=PLUS && LA108_0<=MOD)) ) {
                            alt108=1;
                        }
                        switch (alt108) {
                            case 1 :
                                // EsperEPL2Ast.g:368:15: ( DISTINCT )? valueExpr
                                {
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
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2479); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc2483);
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
                    // EsperEPL2Ast.g:369:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc2497); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:369:15: ( DISTINCT )?
                    int alt109=2;
                    int LA109_0 = input.LA(1);

                    if ( (LA109_0==DISTINCT) ) {
                        alt109=1;
                    }
                    switch (alt109) {
                        case 1 :
                            // EsperEPL2Ast.g:369:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2500); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2504);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:370:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc2515); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:370:15: ( DISTINCT )?
                    int alt110=2;
                    int LA110_0 = input.LA(1);

                    if ( (LA110_0==DISTINCT) ) {
                        alt110=1;
                    }
                    switch (alt110) {
                        case 1 :
                            // EsperEPL2Ast.g:370:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2518); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2522);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:371:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc2533); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:371:15: ( DISTINCT )?
                    int alt111=2;
                    int LA111_0 = input.LA(1);

                    if ( (LA111_0==DISTINCT) ) {
                        alt111=1;
                    }
                    switch (alt111) {
                        case 1 :
                            // EsperEPL2Ast.g:371:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2536); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2540);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:372:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc2552); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2554);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2556);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:372:38: ( valueExpr )*
                    loop112:
                    do {
                        int alt112=2;
                        int LA112_0 = input.LA(1);

                        if ( ((LA112_0>=IN_SET && LA112_0<=REGEXP)||LA112_0==NOT_EXPR||(LA112_0>=SUM && LA112_0<=AVG)||(LA112_0>=COALESCE && LA112_0<=COUNT)||(LA112_0>=CASE && LA112_0<=CASE2)||(LA112_0>=PREVIOUS && LA112_0<=EXISTS)||(LA112_0>=INSTANCEOF && LA112_0<=CURRENT_TIMESTAMP)||(LA112_0>=EVAL_AND_EXPR && LA112_0<=EVAL_NOTEQUALS_EXPR)||LA112_0==EVENT_PROP_EXPR||(LA112_0>=CONCAT && LA112_0<=LIB_FUNCTION)||LA112_0==ARRAY_EXPR||(LA112_0>=NOT_IN_SET && LA112_0<=NOT_REGEXP)||(LA112_0>=IN_RANGE && LA112_0<=NOT_IN_SUBSELECT_EXPR)||LA112_0==SUBSTITUTION||(LA112_0>=INT_TYPE && LA112_0<=NULL_TYPE)||LA112_0==STAR||(LA112_0>=BAND && LA112_0<=BXOR)||(LA112_0>=LT && LA112_0<=GE)||(LA112_0>=PLUS && LA112_0<=MOD)) ) {
                            alt112=1;
                        }


                        switch (alt112) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:372:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc2559);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop112;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:373:5: ^(f= PREVIOUS valueExpr eventPropertyExpr )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc2574); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2576);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc2578);
                    eventPropertyExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:374:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc2590); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc2594); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc2596);
                    eventPropertyExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:375:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc2608); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2610);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc2612); 
                    // EsperEPL2Ast.g:375:42: ( CLASS_IDENT )*
                    loop113:
                    do {
                        int alt113=2;
                        int LA113_0 = input.LA(1);

                        if ( (LA113_0==CLASS_IDENT) ) {
                            alt113=1;
                        }


                        switch (alt113) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:375:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc2615); 

                    	    }
                    	    break;

                    	default :
                    	    break loop113;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:376:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc2629); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2631);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc2633); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:377:5: ^(f= EXISTS eventPropertyExpr )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc2645); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc2647);
                    eventPropertyExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:378:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc2658); 



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
    // EsperEPL2Ast.g:381:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:382:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:382:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr2678); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:382:19: ( valueExpr )*
                loop115:
                do {
                    int alt115=2;
                    int LA115_0 = input.LA(1);

                    if ( ((LA115_0>=IN_SET && LA115_0<=REGEXP)||LA115_0==NOT_EXPR||(LA115_0>=SUM && LA115_0<=AVG)||(LA115_0>=COALESCE && LA115_0<=COUNT)||(LA115_0>=CASE && LA115_0<=CASE2)||(LA115_0>=PREVIOUS && LA115_0<=EXISTS)||(LA115_0>=INSTANCEOF && LA115_0<=CURRENT_TIMESTAMP)||(LA115_0>=EVAL_AND_EXPR && LA115_0<=EVAL_NOTEQUALS_EXPR)||LA115_0==EVENT_PROP_EXPR||(LA115_0>=CONCAT && LA115_0<=LIB_FUNCTION)||LA115_0==ARRAY_EXPR||(LA115_0>=NOT_IN_SET && LA115_0<=NOT_REGEXP)||(LA115_0>=IN_RANGE && LA115_0<=NOT_IN_SUBSELECT_EXPR)||LA115_0==SUBSTITUTION||(LA115_0>=INT_TYPE && LA115_0<=NULL_TYPE)||LA115_0==STAR||(LA115_0>=BAND && LA115_0<=BXOR)||(LA115_0>=LT && LA115_0<=GE)||(LA115_0>=PLUS && LA115_0<=MOD)) ) {
                        alt115=1;
                    }


                    switch (alt115) {
                	case 1 :
                	    // EsperEPL2Ast.g:382:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr2681);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop115;
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
    // EsperEPL2Ast.g:385:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:386:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt117=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt117=1;
                }
                break;
            case MINUS:
                {
                alt117=2;
                }
                break;
            case DIV:
                {
                alt117=3;
                }
                break;
            case STAR:
                {
                alt117=4;
                }
                break;
            case MOD:
                {
                alt117=5;
                }
                break;
            case BAND:
                {
                alt117=6;
                }
                break;
            case BOR:
                {
                alt117=7;
                }
                break;
            case BXOR:
                {
                alt117=8;
                }
                break;
            case CONCAT:
                {
                alt117=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 117, 0, input);

                throw nvae;
            }

            switch (alt117) {
                case 1 :
                    // EsperEPL2Ast.g:386:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr2702); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2704);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2706);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:387:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr2718); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2720);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2722);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:388:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr2734); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2736);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2738);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:389:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr2749); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2751);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2753);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:390:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr2765); 

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
                case 6 :
                    // EsperEPL2Ast.g:391:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr2780); 

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
                case 7 :
                    // EsperEPL2Ast.g:392:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr2795); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2797);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2799);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:393:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr2810); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2812);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2814);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:394:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr2826); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2828);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2830);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:394:36: ( valueExpr )*
                    loop116:
                    do {
                        int alt116=2;
                        int LA116_0 = input.LA(1);

                        if ( ((LA116_0>=IN_SET && LA116_0<=REGEXP)||LA116_0==NOT_EXPR||(LA116_0>=SUM && LA116_0<=AVG)||(LA116_0>=COALESCE && LA116_0<=COUNT)||(LA116_0>=CASE && LA116_0<=CASE2)||(LA116_0>=PREVIOUS && LA116_0<=EXISTS)||(LA116_0>=INSTANCEOF && LA116_0<=CURRENT_TIMESTAMP)||(LA116_0>=EVAL_AND_EXPR && LA116_0<=EVAL_NOTEQUALS_EXPR)||LA116_0==EVENT_PROP_EXPR||(LA116_0>=CONCAT && LA116_0<=LIB_FUNCTION)||LA116_0==ARRAY_EXPR||(LA116_0>=NOT_IN_SET && LA116_0<=NOT_REGEXP)||(LA116_0>=IN_RANGE && LA116_0<=NOT_IN_SUBSELECT_EXPR)||LA116_0==SUBSTITUTION||(LA116_0>=INT_TYPE && LA116_0<=NULL_TYPE)||LA116_0==STAR||(LA116_0>=BAND && LA116_0<=BXOR)||(LA116_0>=LT && LA116_0<=GE)||(LA116_0>=PLUS && LA116_0<=MOD)) ) {
                            alt116=1;
                        }


                        switch (alt116) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:394:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2833);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop116;
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
    // EsperEPL2Ast.g:397:1: libFunc : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunc() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:398:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:398:5: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunc2854); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:398:22: ( CLASS_IDENT )?
            int alt118=2;
            int LA118_0 = input.LA(1);

            if ( (LA118_0==CLASS_IDENT) ) {
                alt118=1;
            }
            switch (alt118) {
                case 1 :
                    // EsperEPL2Ast.g:398:23: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunc2857); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunc2861); 
            // EsperEPL2Ast.g:398:43: ( DISTINCT )?
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==DISTINCT) ) {
                alt119=1;
            }
            switch (alt119) {
                case 1 :
                    // EsperEPL2Ast.g:398:44: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunc2864); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:398:55: ( valueExpr )*
            loop120:
            do {
                int alt120=2;
                int LA120_0 = input.LA(1);

                if ( ((LA120_0>=IN_SET && LA120_0<=REGEXP)||LA120_0==NOT_EXPR||(LA120_0>=SUM && LA120_0<=AVG)||(LA120_0>=COALESCE && LA120_0<=COUNT)||(LA120_0>=CASE && LA120_0<=CASE2)||(LA120_0>=PREVIOUS && LA120_0<=EXISTS)||(LA120_0>=INSTANCEOF && LA120_0<=CURRENT_TIMESTAMP)||(LA120_0>=EVAL_AND_EXPR && LA120_0<=EVAL_NOTEQUALS_EXPR)||LA120_0==EVENT_PROP_EXPR||(LA120_0>=CONCAT && LA120_0<=LIB_FUNCTION)||LA120_0==ARRAY_EXPR||(LA120_0>=NOT_IN_SET && LA120_0<=NOT_REGEXP)||(LA120_0>=IN_RANGE && LA120_0<=NOT_IN_SUBSELECT_EXPR)||LA120_0==SUBSTITUTION||(LA120_0>=INT_TYPE && LA120_0<=NULL_TYPE)||LA120_0==STAR||(LA120_0>=BAND && LA120_0<=BXOR)||(LA120_0>=LT && LA120_0<=GE)||(LA120_0>=PLUS && LA120_0<=MOD)) ) {
                    alt120=1;
                }


                switch (alt120) {
            	case 1 :
            	    // EsperEPL2Ast.g:398:56: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunc2869);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop120;
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
    // EsperEPL2Ast.g:404:1: startPatternExpressionRule : exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:405:2: ( exprChoice )
            // EsperEPL2Ast.g:405:4: exprChoice
            {
            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule2889);
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
    // EsperEPL2Ast.g:408:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:409:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt124=6;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt124=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt124=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt124=3;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt124=4;
                }
                break;
            case GUARD_EXPR:
                {
                alt124=5;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt124=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 124, 0, input);

                throw nvae;
            }

            switch (alt124) {
                case 1 :
                    // EsperEPL2Ast.g:409:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice2903);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:410:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice2908);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:411:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice2918); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice2920);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:412:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice2934); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice2936);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:413:5: ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice2950); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice2952);
                    exprChoice();

                    state._fsp--;

                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice2954); 
                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice2956); 
                    // EsperEPL2Ast.g:413:44: ( valueExprWithTime )*
                    loop121:
                    do {
                        int alt121=2;
                        int LA121_0 = input.LA(1);

                        if ( ((LA121_0>=IN_SET && LA121_0<=REGEXP)||LA121_0==NOT_EXPR||(LA121_0>=SUM && LA121_0<=AVG)||(LA121_0>=COALESCE && LA121_0<=COUNT)||(LA121_0>=CASE && LA121_0<=CASE2)||LA121_0==LAST||(LA121_0>=PREVIOUS && LA121_0<=EXISTS)||(LA121_0>=LW && LA121_0<=CURRENT_TIMESTAMP)||(LA121_0>=NUMERIC_PARAM_RANGE && LA121_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA121_0>=EVAL_AND_EXPR && LA121_0<=EVAL_NOTEQUALS_EXPR)||LA121_0==EVENT_PROP_EXPR||(LA121_0>=CONCAT && LA121_0<=LIB_FUNCTION)||(LA121_0>=TIME_PERIOD && LA121_0<=ARRAY_EXPR)||(LA121_0>=NOT_IN_SET && LA121_0<=NOT_REGEXP)||(LA121_0>=IN_RANGE && LA121_0<=NOT_IN_SUBSELECT_EXPR)||(LA121_0>=LAST_OPERATOR && LA121_0<=SUBSTITUTION)||(LA121_0>=NUMBERSETSTAR && LA121_0<=NULL_TYPE)||LA121_0==STAR||(LA121_0>=BAND && LA121_0<=BXOR)||(LA121_0>=LT && LA121_0<=GE)||(LA121_0>=PLUS && LA121_0<=MOD)) ) {
                            alt121=1;
                        }


                        switch (alt121) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:413:44: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice2958);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop121;
                        }
                    } while (true);

                     leaveNode(g); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:414:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice2972); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:414:26: ( matchUntilRange )?
                    int alt122=2;
                    int LA122_0 = input.LA(1);

                    if ( ((LA122_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA122_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt122=1;
                    }
                    switch (alt122) {
                        case 1 :
                            // EsperEPL2Ast.g:414:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice2974);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice2977);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:414:54: ( exprChoice )?
                    int alt123=2;
                    int LA123_0 = input.LA(1);

                    if ( ((LA123_0>=OR_EXPR && LA123_0<=AND_EXPR)||LA123_0==EVERY_EXPR||LA123_0==FOLLOWED_BY_EXPR||(LA123_0>=PATTERN_FILTER_EXPR && LA123_0<=PATTERN_NOT_EXPR)||(LA123_0>=GUARD_EXPR && LA123_0<=OBSERVER_EXPR)||LA123_0==MATCH_UNTIL_EXPR) ) {
                        alt123=1;
                    }
                    switch (alt123) {
                        case 1 :
                            // EsperEPL2Ast.g:414:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice2979);
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
    // EsperEPL2Ast.g:417:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:418:2: ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt128=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt128=1;
                }
                break;
            case OR_EXPR:
                {
                alt128=2;
                }
                break;
            case AND_EXPR:
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
                    // EsperEPL2Ast.g:418:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp3000); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3002);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3004);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:418:48: ( exprChoice )*
                    loop125:
                    do {
                        int alt125=2;
                        int LA125_0 = input.LA(1);

                        if ( ((LA125_0>=OR_EXPR && LA125_0<=AND_EXPR)||LA125_0==EVERY_EXPR||LA125_0==FOLLOWED_BY_EXPR||(LA125_0>=PATTERN_FILTER_EXPR && LA125_0<=PATTERN_NOT_EXPR)||(LA125_0>=GUARD_EXPR && LA125_0<=OBSERVER_EXPR)||LA125_0==MATCH_UNTIL_EXPR) ) {
                            alt125=1;
                        }


                        switch (alt125) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:418:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3007);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop125;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:419:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp3023); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3025);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3027);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:419:40: ( exprChoice )*
                    loop126:
                    do {
                        int alt126=2;
                        int LA126_0 = input.LA(1);

                        if ( ((LA126_0>=OR_EXPR && LA126_0<=AND_EXPR)||LA126_0==EVERY_EXPR||LA126_0==FOLLOWED_BY_EXPR||(LA126_0>=PATTERN_FILTER_EXPR && LA126_0<=PATTERN_NOT_EXPR)||(LA126_0>=GUARD_EXPR && LA126_0<=OBSERVER_EXPR)||LA126_0==MATCH_UNTIL_EXPR) ) {
                            alt126=1;
                        }


                        switch (alt126) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:419:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3030);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop126;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:420:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp3046); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3048);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3050);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:420:41: ( exprChoice )*
                    loop127:
                    do {
                        int alt127=2;
                        int LA127_0 = input.LA(1);

                        if ( ((LA127_0>=OR_EXPR && LA127_0<=AND_EXPR)||LA127_0==EVERY_EXPR||LA127_0==FOLLOWED_BY_EXPR||(LA127_0>=PATTERN_FILTER_EXPR && LA127_0<=PATTERN_NOT_EXPR)||(LA127_0>=GUARD_EXPR && LA127_0<=OBSERVER_EXPR)||LA127_0==MATCH_UNTIL_EXPR) ) {
                            alt127=1;
                        }


                        switch (alt127) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:420:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3053);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop127;
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
    // EsperEPL2Ast.g:423:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:424:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt130=2;
            int LA130_0 = input.LA(1);

            if ( (LA130_0==PATTERN_FILTER_EXPR) ) {
                alt130=1;
            }
            else if ( (LA130_0==OBSERVER_EXPR) ) {
                alt130=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 130, 0, input);

                throw nvae;
            }
            switch (alt130) {
                case 1 :
                    // EsperEPL2Ast.g:424:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr3072);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:425:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr3084); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3086); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3088); 
                    // EsperEPL2Ast.g:425:39: ( valueExprWithTime )*
                    loop129:
                    do {
                        int alt129=2;
                        int LA129_0 = input.LA(1);

                        if ( ((LA129_0>=IN_SET && LA129_0<=REGEXP)||LA129_0==NOT_EXPR||(LA129_0>=SUM && LA129_0<=AVG)||(LA129_0>=COALESCE && LA129_0<=COUNT)||(LA129_0>=CASE && LA129_0<=CASE2)||LA129_0==LAST||(LA129_0>=PREVIOUS && LA129_0<=EXISTS)||(LA129_0>=LW && LA129_0<=CURRENT_TIMESTAMP)||(LA129_0>=NUMERIC_PARAM_RANGE && LA129_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA129_0>=EVAL_AND_EXPR && LA129_0<=EVAL_NOTEQUALS_EXPR)||LA129_0==EVENT_PROP_EXPR||(LA129_0>=CONCAT && LA129_0<=LIB_FUNCTION)||(LA129_0>=TIME_PERIOD && LA129_0<=ARRAY_EXPR)||(LA129_0>=NOT_IN_SET && LA129_0<=NOT_REGEXP)||(LA129_0>=IN_RANGE && LA129_0<=NOT_IN_SUBSELECT_EXPR)||(LA129_0>=LAST_OPERATOR && LA129_0<=SUBSTITUTION)||(LA129_0>=NUMBERSETSTAR && LA129_0<=NULL_TYPE)||LA129_0==STAR||(LA129_0>=BAND && LA129_0<=BXOR)||(LA129_0>=LT && LA129_0<=GE)||(LA129_0>=PLUS && LA129_0<=MOD)) ) {
                            alt129=1;
                        }


                        switch (alt129) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:425:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr3090);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop129;
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
    // EsperEPL2Ast.g:428:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:429:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:429:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr3110); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:429:29: ( IDENT )?
            int alt131=2;
            int LA131_0 = input.LA(1);

            if ( (LA131_0==IDENT) ) {
                alt131=1;
            }
            switch (alt131) {
                case 1 :
                    // EsperEPL2Ast.g:429:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr3112); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr3115); 
            // EsperEPL2Ast.g:429:48: ( valueExpr )*
            loop132:
            do {
                int alt132=2;
                int LA132_0 = input.LA(1);

                if ( ((LA132_0>=IN_SET && LA132_0<=REGEXP)||LA132_0==NOT_EXPR||(LA132_0>=SUM && LA132_0<=AVG)||(LA132_0>=COALESCE && LA132_0<=COUNT)||(LA132_0>=CASE && LA132_0<=CASE2)||(LA132_0>=PREVIOUS && LA132_0<=EXISTS)||(LA132_0>=INSTANCEOF && LA132_0<=CURRENT_TIMESTAMP)||(LA132_0>=EVAL_AND_EXPR && LA132_0<=EVAL_NOTEQUALS_EXPR)||LA132_0==EVENT_PROP_EXPR||(LA132_0>=CONCAT && LA132_0<=LIB_FUNCTION)||LA132_0==ARRAY_EXPR||(LA132_0>=NOT_IN_SET && LA132_0<=NOT_REGEXP)||(LA132_0>=IN_RANGE && LA132_0<=NOT_IN_SUBSELECT_EXPR)||LA132_0==SUBSTITUTION||(LA132_0>=INT_TYPE && LA132_0<=NULL_TYPE)||LA132_0==STAR||(LA132_0>=BAND && LA132_0<=BXOR)||(LA132_0>=LT && LA132_0<=GE)||(LA132_0>=PLUS && LA132_0<=MOD)) ) {
                    alt132=1;
                }


                switch (alt132) {
            	case 1 :
            	    // EsperEPL2Ast.g:429:49: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr3118);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop132;
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
    // EsperEPL2Ast.g:432:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:433:2: ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) )
            int alt133=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt133=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt133=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt133=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt133=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 133, 0, input);

                throw nvae;
            }

            switch (alt133) {
                case 1 :
                    // EsperEPL2Ast.g:433:4: ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange3136); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3138);
                    matchUntilRangeParam();

                    state._fsp--;

                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3140);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:434:5: ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange3148); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3150);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:435:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange3158); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3160);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:436:4: ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange3167); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3169);
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
    // EsperEPL2Ast.g:439:1: matchUntilRangeParam : ( NUM_DOUBLE | NUM_INT );
    public final void matchUntilRangeParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:440:2: ( NUM_DOUBLE | NUM_INT )
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
    // EsperEPL2Ast.g:444:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:445:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:445:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam3198); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam3200);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:445:35: ( valueExpr )*
            loop134:
            do {
                int alt134=2;
                int LA134_0 = input.LA(1);

                if ( ((LA134_0>=IN_SET && LA134_0<=REGEXP)||LA134_0==NOT_EXPR||(LA134_0>=SUM && LA134_0<=AVG)||(LA134_0>=COALESCE && LA134_0<=COUNT)||(LA134_0>=CASE && LA134_0<=CASE2)||(LA134_0>=PREVIOUS && LA134_0<=EXISTS)||(LA134_0>=INSTANCEOF && LA134_0<=CURRENT_TIMESTAMP)||(LA134_0>=EVAL_AND_EXPR && LA134_0<=EVAL_NOTEQUALS_EXPR)||LA134_0==EVENT_PROP_EXPR||(LA134_0>=CONCAT && LA134_0<=LIB_FUNCTION)||LA134_0==ARRAY_EXPR||(LA134_0>=NOT_IN_SET && LA134_0<=NOT_REGEXP)||(LA134_0>=IN_RANGE && LA134_0<=NOT_IN_SUBSELECT_EXPR)||LA134_0==SUBSTITUTION||(LA134_0>=INT_TYPE && LA134_0<=NULL_TYPE)||LA134_0==STAR||(LA134_0>=BAND && LA134_0<=BXOR)||(LA134_0>=LT && LA134_0<=GE)||(LA134_0>=PLUS && LA134_0<=MOD)) ) {
                    alt134=1;
                }


                switch (alt134) {
            	case 1 :
            	    // EsperEPL2Ast.g:445:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam3203);
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
    // EsperEPL2Ast.g:448:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:449:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt147=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt147=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt147=2;
                }
                break;
            case LT:
                {
                alt147=3;
                }
                break;
            case LE:
                {
                alt147=4;
                }
                break;
            case GT:
                {
                alt147=5;
                }
                break;
            case GE:
                {
                alt147=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt147=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt147=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt147=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt147=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt147=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt147=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 147, 0, input);

                throw nvae;
            }

            switch (alt147) {
                case 1 :
                    // EsperEPL2Ast.g:449:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator3219); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3221);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:450:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator3228); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3230);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:451:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator3237); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3239);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:452:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator3246); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3248);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:453:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator3255); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3257);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:454:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator3264); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3266);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:455:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator3273); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:455:41: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:455:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3282);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:455:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3285);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:455:76: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:455:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3289);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:455:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3292);
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
                    // EsperEPL2Ast.g:456:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator3306); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:456:45: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:456:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3315);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:456:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3318);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:456:80: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:456:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3322);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:456:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3325);
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
                    // EsperEPL2Ast.g:457:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator3339); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:457:38: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:457:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3348);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:457:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3351);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:457:73: ( constant[false] | filterIdentifier )*
                    loop140:
                    do {
                        int alt140=3;
                        int LA140_0 = input.LA(1);

                        if ( ((LA140_0>=INT_TYPE && LA140_0<=NULL_TYPE)) ) {
                            alt140=1;
                        }
                        else if ( (LA140_0==EVENT_FILTER_IDENT) ) {
                            alt140=2;
                        }


                        switch (alt140) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:457:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator3355);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:457:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3358);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop140;
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
                    // EsperEPL2Ast.g:458:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator3373); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:458:42: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:458:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3382);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:458:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3385);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:458:77: ( constant[false] | filterIdentifier )*
                    loop142:
                    do {
                        int alt142=3;
                        int LA142_0 = input.LA(1);

                        if ( ((LA142_0>=INT_TYPE && LA142_0<=NULL_TYPE)) ) {
                            alt142=1;
                        }
                        else if ( (LA142_0==EVENT_FILTER_IDENT) ) {
                            alt142=2;
                        }


                        switch (alt142) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:458:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator3389);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:458:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3392);
                    	    filterIdentifier();

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

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:459:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator3407); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:459:27: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:459:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3410);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:459:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3413);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:459:62: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:459:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3417);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:459:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3420);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:460:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator3428); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:460:31: ( constant[false] | filterIdentifier )
                    int alt145=2;
                    int LA145_0 = input.LA(1);

                    if ( ((LA145_0>=INT_TYPE && LA145_0<=NULL_TYPE)) ) {
                        alt145=1;
                    }
                    else if ( (LA145_0==EVENT_FILTER_IDENT) ) {
                        alt145=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 145, 0, input);

                        throw nvae;
                    }
                    switch (alt145) {
                        case 1 :
                            // EsperEPL2Ast.g:460:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3431);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:460:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3434);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:460:66: ( constant[false] | filterIdentifier )
                    int alt146=2;
                    int LA146_0 = input.LA(1);

                    if ( ((LA146_0>=INT_TYPE && LA146_0<=NULL_TYPE)) ) {
                        alt146=1;
                    }
                    else if ( (LA146_0==EVENT_FILTER_IDENT) ) {
                        alt146=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 146, 0, input);

                        throw nvae;
                    }
                    switch (alt146) {
                        case 1 :
                            // EsperEPL2Ast.g:460:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3438);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:460:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3441);
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
    // EsperEPL2Ast.g:463:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:464:2: ( constant[false] | filterIdentifier )
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
                    // EsperEPL2Ast.g:464:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom3455);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:465:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom3461);
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
    // EsperEPL2Ast.g:467:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:468:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr ) )
            // EsperEPL2Ast.g:468:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier3472); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier3474); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier3476);
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
    // EsperEPL2Ast.g:471:1: eventPropertyExpr : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:472:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:472:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr3493); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3495);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:472:44: ( eventPropertyAtomic )*
            loop149:
            do {
                int alt149=2;
                int LA149_0 = input.LA(1);

                if ( ((LA149_0>=EVENT_PROP_SIMPLE && LA149_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt149=1;
                }


                switch (alt149) {
            	case 1 :
            	    // EsperEPL2Ast.g:472:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3498);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop149;
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
    // EsperEPL2Ast.g:475:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:476:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt150=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt150=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt150=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt150=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt150=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt150=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt150=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 150, 0, input);

                throw nvae;
            }

            switch (alt150) {
                case 1 :
                    // EsperEPL2Ast.g:476:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic3517); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3519); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:477:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic3526); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3528); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic3530); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:478:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic3537); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3539); 
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
                    // EsperEPL2Ast.g:479:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic3554); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3556); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:480:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic3563); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3565); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic3567); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:481:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic3574); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3576); 
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
    // EsperEPL2Ast.g:484:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:485:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:485:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod3603); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod3605);
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
    // EsperEPL2Ast.g:488:1: timePeriodDef : ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:489:2: ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt161=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt161=1;
                }
                break;
            case HOUR_PART:
                {
                alt161=2;
                }
                break;
            case MINUTE_PART:
                {
                alt161=3;
                }
                break;
            case SECOND_PART:
                {
                alt161=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt161=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 161, 0, input);

                throw nvae;
            }

            switch (alt161) {
                case 1 :
                    // EsperEPL2Ast.g:489:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef3621);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:489:13: ( hourPart )?
                    int alt151=2;
                    int LA151_0 = input.LA(1);

                    if ( (LA151_0==HOUR_PART) ) {
                        alt151=1;
                    }
                    switch (alt151) {
                        case 1 :
                            // EsperEPL2Ast.g:489:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef3624);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:489:25: ( minutePart )?
                    int alt152=2;
                    int LA152_0 = input.LA(1);

                    if ( (LA152_0==MINUTE_PART) ) {
                        alt152=1;
                    }
                    switch (alt152) {
                        case 1 :
                            // EsperEPL2Ast.g:489:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef3629);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:489:39: ( secondPart )?
                    int alt153=2;
                    int LA153_0 = input.LA(1);

                    if ( (LA153_0==SECOND_PART) ) {
                        alt153=1;
                    }
                    switch (alt153) {
                        case 1 :
                            // EsperEPL2Ast.g:489:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef3634);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:489:53: ( millisecondPart )?
                    int alt154=2;
                    int LA154_0 = input.LA(1);

                    if ( (LA154_0==MILLISECOND_PART) ) {
                        alt154=1;
                    }
                    switch (alt154) {
                        case 1 :
                            // EsperEPL2Ast.g:489:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3639);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:490:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef3646);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:490:13: ( minutePart )?
                    int alt155=2;
                    int LA155_0 = input.LA(1);

                    if ( (LA155_0==MINUTE_PART) ) {
                        alt155=1;
                    }
                    switch (alt155) {
                        case 1 :
                            // EsperEPL2Ast.g:490:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef3649);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:490:27: ( secondPart )?
                    int alt156=2;
                    int LA156_0 = input.LA(1);

                    if ( (LA156_0==SECOND_PART) ) {
                        alt156=1;
                    }
                    switch (alt156) {
                        case 1 :
                            // EsperEPL2Ast.g:490:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef3654);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:490:41: ( millisecondPart )?
                    int alt157=2;
                    int LA157_0 = input.LA(1);

                    if ( (LA157_0==MILLISECOND_PART) ) {
                        alt157=1;
                    }
                    switch (alt157) {
                        case 1 :
                            // EsperEPL2Ast.g:490:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3659);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:491:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef3666);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:491:15: ( secondPart )?
                    int alt158=2;
                    int LA158_0 = input.LA(1);

                    if ( (LA158_0==SECOND_PART) ) {
                        alt158=1;
                    }
                    switch (alt158) {
                        case 1 :
                            // EsperEPL2Ast.g:491:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef3669);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:491:29: ( millisecondPart )?
                    int alt159=2;
                    int LA159_0 = input.LA(1);

                    if ( (LA159_0==MILLISECOND_PART) ) {
                        alt159=1;
                    }
                    switch (alt159) {
                        case 1 :
                            // EsperEPL2Ast.g:491:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3674);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:492:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef3681);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:492:15: ( millisecondPart )?
                    int alt160=2;
                    int LA160_0 = input.LA(1);

                    if ( (LA160_0==MILLISECOND_PART) ) {
                        alt160=1;
                    }
                    switch (alt160) {
                        case 1 :
                            // EsperEPL2Ast.g:492:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3684);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:493:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3691);
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
    // EsperEPL2Ast.g:496:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:497:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:497:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart3705); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart3707);
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
    // EsperEPL2Ast.g:500:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:501:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:501:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart3722); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart3724);
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
    // EsperEPL2Ast.g:504:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:505:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:505:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart3739); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart3741);
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
    // EsperEPL2Ast.g:508:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:509:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:509:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart3756); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart3758);
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
    // EsperEPL2Ast.g:512:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:513:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:513:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart3773); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart3775);
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
    // EsperEPL2Ast.g:516:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:517:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:517:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution3790); 
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
    // EsperEPL2Ast.g:520:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:521:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt162=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt162=1;
                }
                break;
            case LONG_TYPE:
                {
                alt162=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt162=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt162=4;
                }
                break;
            case STRING_TYPE:
                {
                alt162=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt162=6;
                }
                break;
            case NULL_TYPE:
                {
                alt162=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 162, 0, input);

                throw nvae;
            }

            switch (alt162) {
                case 1 :
                    // EsperEPL2Ast.g:521:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant3806); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:522:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant3815); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:523:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant3824); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:524:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant3833); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:525:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant3849); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:526:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant3865); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:527:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant3878); 
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
    // EsperEPL2Ast.g:530:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:531:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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
    public static final BitSet FOLLOW_eventFilterExpr_in_onExpr142 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x2C00000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onExpr146 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x2C00000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_onExpr149 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x2C00000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr156 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr160 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr164 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr184 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr186 = new BitSet(new long[]{0x0000000000000008L,0x0080000000000000L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr189 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr206 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr209 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040000000003L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr213 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr215 = new BitSet(new long[]{0x0000000000000008L,0x0180000000000000L,0x0000000000000300L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr218 = new BitSet(new long[]{0x0000000000000008L,0x0100000000000000L,0x0000000000000300L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr223 = new BitSet(new long[]{0x0000000000000008L,0x0100000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr228 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr233 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr252 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr255 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_onSetAssignment270 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom284 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom286 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom289 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr307 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr309 = new BitSet(new long[]{0x3000000000000000L,0x0012000000000000L,0x0100000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr312 = new BitSet(new long[]{0x3000000000000000L,0x0012000000000000L,0x0100000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr316 = new BitSet(new long[]{0x3000000000000000L,0x0012000000000000L,0x0100000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr319 = new BitSet(new long[]{0x3000000000000000L,0x0012000000000000L,0x0100000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr333 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr336 = new BitSet(new long[]{0x0008000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr365 = new BitSet(new long[]{0x0008000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr376 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert394 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert396 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList413 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList415 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000040000000001L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList418 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000040000000001L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList437 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList439 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList442 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement457 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement459 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement461 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement485 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement505 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement508 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement530 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement533 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr569 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr571 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr573 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr576 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr594 = new BitSet(new long[]{0x0000000000000000L,0x8000000000000000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr600 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr605 = new BitSet(new long[]{0x0000000000000002L,0x0180000040000000L,0x00000000005C0300L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr610 = new BitSet(new long[]{0x0000000000000002L,0x0100000040000000L,0x00000000005C0300L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr617 = new BitSet(new long[]{0x0000000000000002L,0x0100000040000000L,0x00000000005C0200L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr624 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L,0x00000000005C0200L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr631 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr638 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr662 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr664 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr673 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_insertIntoExprCol_in_insertIntoExpr676 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol695 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol697 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol700 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause718 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause720 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040000000003L});
    public static final BitSet FOLLOW_selectionList_in_selectClause733 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause747 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause750 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000000000000F4L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause753 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000000000000F4L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList770 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000040000000003L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList773 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000040000000003L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement799 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement801 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement804 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement818 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement820 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement823 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent856 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent858 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent860 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent863 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent865 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent879 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent881 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent883 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent886 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent888 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent902 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent904 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent906 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent909 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent911 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent925 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent927 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent929 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent932 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent934 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression954 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression957 = new BitSet(new long[]{0x3800000000000008L,0x0010000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression961 = new BitSet(new long[]{0x3800000000000008L,0x0010000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression965 = new BitSet(new long[]{0x3800000000000008L,0x0010000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression969 = new BitSet(new long[]{0x3800000000000008L,0x0010000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression973 = new BitSet(new long[]{0x3800000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression978 = new BitSet(new long[]{0x3800000000000008L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression983 = new BitSet(new long[]{0x3000000000000008L});
    public static final BitSet FOLLOW_set_in_streamExpression987 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1011 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1013 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1016 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1019 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1040 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression1042 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1059 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression1061 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000018000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1063 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000018000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1071 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1092 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression1094 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression1096 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression1099 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1113 = new BitSet(new long[]{0x0000000000000002L,0x0010000000000000L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1116 = new BitSet(new long[]{0x0000000000000002L,0x0010000000000000L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr1133 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1135 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1137 = new BitSet(new long[]{0x000400001BE623C8L,0x3C00000F000001EEL,0x003BF07836000800L,0x000007BCE0807F80L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr1140 = new BitSet(new long[]{0x000400001BE623C8L,0x3C00000F000001EEL,0x003BF07836000800L,0x000007BCE0807F80L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause1161 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause1163 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause1181 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1183 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1186 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause1204 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1206 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1209 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1229 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement1231 = new BitSet(new long[]{0x00C0000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement1233 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause1256 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause1258 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1276 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1278 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020F00L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr1290 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr1292 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1309 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1311 = new BitSet(new long[]{0x000400001BE623C0L,0x3C00000F000001EEL,0x003BF07836000800L,0x000007BCE0807F80L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr1322 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1337 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1339 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr1350 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr1365 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1367 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr1378 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x2C00000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr1380 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause1399 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause1402 = new BitSet(new long[]{0x0000000000000008L,0x0000000080000000L,0x0000000000000000L,0x0000000000060F00L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause1404 = new BitSet(new long[]{0x0000000000000008L,0x0000000080000000L,0x0000000000000000L,0x0000000000060F00L});
    public static final BitSet FOLLOW_number_in_rowLimitClause1408 = new BitSet(new long[]{0x0000000000000008L,0x0000000080000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause1410 = new BitSet(new long[]{0x0000000000000008L,0x0000000080000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause1414 = new BitSet(new long[]{0x0000000000000008L,0x0000000080000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause1417 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet1435 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1437 = new BitSet(new long[]{0x000400001BE623C0L,0x3C00000F000001EEL,0x003BF07836000800L,0x000007BCE0807F80L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1439 = new BitSet(new long[]{0x000400001BE623C0L,0x3C00000F000001EEL,0x003BF07836000800L,0x000007BCE0807F80L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1441 = new BitSet(new long[]{0x000400001BE623C0L,0x3C00000F000001EEL,0x003BF07836000800L,0x000007BCE0807F80L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1443 = new BitSet(new long[]{0x000400001BE623C0L,0x3C00000F000001EEL,0x003BF07836000800L,0x000007BCE0807F80L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1445 = new BitSet(new long[]{0x000400001BE623C8L,0x3C00000F000001EEL,0x003BF07836000800L,0x000007BCE0807F80L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1447 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr1464 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1466 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1468 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr1480 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1482 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1484 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr1496 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1498 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1500 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr1511 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1513 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1515 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice1532 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1534 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1536 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1539 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice1553 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1555 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1557 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1560 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice1574 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1576 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1578 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice1590 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1592 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1594 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice1606 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1608 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice1619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr1632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr1638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr1644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr1651 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr1659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr1664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunc_in_valueExpr1672 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr1677 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr1682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr1688 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr1693 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr1698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr1703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr1708 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr1714 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr1721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime1734 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime1743 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime1750 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime1758 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime1760 = new BitSet(new long[]{0x00C0000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime1762 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime1775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime1781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime1786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime1791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime1801 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime1803 = new BitSet(new long[]{0x0000000000000008L,0x0000000500000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime1814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime1821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList1834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList1841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList1847 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator1863 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator1866 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000800L,0x0000000000007F00L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator1869 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000800L,0x0000000000007F00L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator1871 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000800L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_rangeOperator1875 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator1878 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator1880 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator1901 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator1904 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator1907 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator1909 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator1928 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator1931 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator1934 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator1936 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator1955 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator1958 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator1961 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator1963 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr1984 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr1986 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2005 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr2007 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2026 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2028 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2030 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2042 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2044 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2046 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2065 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr2067 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_subQueryExpr2083 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr2085 = new BitSet(new long[]{0x0000000000000002L,0x0080000000000000L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr2088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2105 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr2107 = new BitSet(new long[]{0x3000000000000008L,0x0010000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr2110 = new BitSet(new long[]{0x3000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr2115 = new BitSet(new long[]{0x3000000000000008L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr2119 = new BitSet(new long[]{0x2000000000000008L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr2122 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr2142 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2145 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr2158 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2161 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr2181 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2183 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001200000L});
    public static final BitSet FOLLOW_set_in_inExpr2185 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2191 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE2C07F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2194 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE2C07F00L});
    public static final BitSet FOLLOW_set_in_inExpr2198 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr2213 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2215 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001200000L});
    public static final BitSet FOLLOW_set_in_inExpr2217 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2223 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE2C07F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2226 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE2C07F00L});
    public static final BitSet FOLLOW_set_in_inExpr2230 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr2245 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2247 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001200000L});
    public static final BitSet FOLLOW_set_in_inExpr2249 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2255 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2257 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000002400000L});
    public static final BitSet FOLLOW_set_in_inExpr2259 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr2274 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2276 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001200000L});
    public static final BitSet FOLLOW_set_in_inExpr2278 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2284 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2286 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000002400000L});
    public static final BitSet FOLLOW_set_in_inExpr2288 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr2311 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2313 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2315 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2317 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr2328 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2330 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2332 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2335 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr2355 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2357 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2359 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2362 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr2375 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2377 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2379 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2382 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr2401 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2403 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2405 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr2416 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2418 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2420 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc2439 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2442 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2446 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc2457 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2460 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2464 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc2475 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2479 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2483 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc2497 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2500 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2504 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc2515 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2518 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2522 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc2533 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2536 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2540 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc2552 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2554 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2556 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2559 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc2574 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2576 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc2578 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc2590 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc2594 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc2596 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc2608 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2610 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc2612 = new BitSet(new long[]{0x0000000000000008L,0x0002000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc2615 = new BitSet(new long[]{0x0000000000000008L,0x0002000000000000L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc2629 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2631 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc2633 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc2645 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc2647 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc2658 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr2678 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr2681 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr2702 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2704 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2706 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr2718 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2720 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2722 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr2734 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2736 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2738 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr2749 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2751 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2753 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr2765 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2767 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2769 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr2780 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2782 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2784 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr2795 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2797 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2799 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr2810 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2812 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2814 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr2826 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2828 = new BitSet(new long[]{0x000000001BE623C0L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2830 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2833 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunc2854 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunc2857 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_libFunc2861 = new BitSet(new long[]{0x000020001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunc2864 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_libFunc2869 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule2889 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice2903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice2908 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice2918 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2920 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice2934 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2936 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice2950 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2952 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice2954 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice2956 = new BitSet(new long[]{0x000400001BE623C8L,0x3C00000F000001EEL,0x003BF07836000800L,0x000007BCE0807F80L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice2958 = new BitSet(new long[]{0x000400001BE623C8L,0x3C00000F000001EEL,0x003BF07836000800L,0x000007BCE0807F80L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice2972 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice2974 = new BitSet(new long[]{0x0000000000005800L,0x000C00D000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2977 = new BitSet(new long[]{0x0000000000005808L,0x000C00D000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2979 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp3000 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3002 = new BitSet(new long[]{0x0000000000005800L,0x000C00D000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3004 = new BitSet(new long[]{0x0000000000005808L,0x000C00D000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3007 = new BitSet(new long[]{0x0000000000005808L,0x000C00D000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp3023 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3025 = new BitSet(new long[]{0x0000000000005800L,0x000C00D000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3027 = new BitSet(new long[]{0x0000000000005808L,0x000C00D000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3030 = new BitSet(new long[]{0x0000000000005808L,0x000C00D000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp3046 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3048 = new BitSet(new long[]{0x0000000000005800L,0x000C00D000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3050 = new BitSet(new long[]{0x0000000000005808L,0x000C00D000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3053 = new BitSet(new long[]{0x0000000000005808L,0x000C00D000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr3072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr3084 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3086 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3088 = new BitSet(new long[]{0x000400001BE623C8L,0x3C00000F000001EEL,0x003BF07836000800L,0x000007BCE0807F80L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr3090 = new BitSet(new long[]{0x000400001BE623C8L,0x3C00000F000001EEL,0x003BF07836000800L,0x000007BCE0807F80L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr3110 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr3112 = new BitSet(new long[]{0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr3115 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr3118 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange3136 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3138 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000200000008000L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3140 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange3148 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3150 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange3158 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3160 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange3167 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3169 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_matchUntilRangeParam0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam3198 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam3200 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam3203 = new BitSet(new long[]{0x000000001BE623C8L,0x3C000000000001CEL,0x0023F07826000800L,0x000007BCE0807F00L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator3219 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3221 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator3228 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3230 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator3237 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3239 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator3246 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3248 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator3255 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3257 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator3264 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3266 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator3273 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3275 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3282 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3285 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3289 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000002400000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3292 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000002400000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3295 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator3306 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3308 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3315 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3318 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3322 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000002400000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3325 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000002400000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3328 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator3339 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3341 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3348 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000002407F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3351 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000002407F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3355 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000002407F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3358 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000002407F00L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3362 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator3373 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3375 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3382 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000002407F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3385 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000002407F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3389 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000002407F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3392 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000002407F00L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3396 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator3407 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3410 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3413 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3417 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3420 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator3428 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3431 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3434 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3438 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3441 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom3455 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom3461 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier3472 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier3474 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier3476 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr3493 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3495 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000000003F000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3498 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000000003F000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic3517 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3519 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic3526 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3528 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic3530 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic3537 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3539 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000018000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic3541 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic3554 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3556 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic3563 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3565 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic3567 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic3574 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3576 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000018000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic3578 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod3603 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod3605 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef3621 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000780000000L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef3624 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000700000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef3629 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000600000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3634 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef3646 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000700000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef3649 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000600000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3654 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef3666 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000600000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3669 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3681 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart3705 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart3707 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart3722 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart3724 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart3739 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart3741 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart3756 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart3758 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart3773 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart3775 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution3790 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant3806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant3815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant3824 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant3833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant3849 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant3865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant3878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}