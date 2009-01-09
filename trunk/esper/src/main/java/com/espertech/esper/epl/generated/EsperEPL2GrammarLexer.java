// $ANTLR 3.1.1 EsperEPL2Grammar.g 2009-01-08 21:24:30

  package com.espertech.esper.epl.generated;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class EsperEPL2GrammarLexer extends Lexer {
    public static final int CRONTAB_LIMIT_EXPR=149;
    public static final int FLOAT_SUFFIX=274;
    public static final int STAR=216;
    public static final int NUMERIC_PARAM_LIST=97;
    public static final int ISTREAM=57;
    public static final int MOD=235;
    public static final int OUTERJOIN_EXPR=132;
    public static final int BSR=256;
    public static final int LIB_FUNCTION=155;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=90;
    public static final int FULL_OUTERJOIN_EXPR=136;
    public static final int RPAREN=215;
    public static final int LNOT=245;
    public static final int INC=249;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=220;
    public static final int BSR_ASSIGN=257;
    public static final int STREAM_EXPR=131;
    public static final int CAST_EXPR=183;
    public static final int TIMEPERIOD_SECONDS=87;
    public static final int NOT_EQUAL=226;
    public static final int METADATASQL=64;
    public static final int EVENT_FILTER_PROPERTY_EXPR=105;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=100;
    public static final int FOLLOWED_BY=239;
    public static final int HOUR_PART=160;
    public static final int RBRACK=218;
    public static final int MATCH_UNTIL_RANGE_CLOSED=196;
    public static final int GE=230;
    public static final int METHOD_JOIN_EXPR=192;
    public static final int ASC=54;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=104;
    public static final int MINUS_ASSIGN=250;
    public static final int ELSE=29;
    public static final int EVENT_FILTER_NOT_IN=111;
    public static final int NUM_DOUBLE=208;
    public static final int INSERTINTO_STREAM_NAME=172;
    public static final int UNARY_MINUS=156;
    public static final int TIMEPERIOD_MILLISEC=88;
    public static final int LCURLY=236;
    public static final int RETAINUNION=60;
    public static final int DBWHERE_CLAUSE=170;
    public static final int MEDIAN=22;
    public static final int EVENTS=48;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=109;
    public static final int GROUP=43;
    public static final int EMAILAT=265;
    public static final int WS=266;
    public static final int ESCAPECHAR=241;
    public static final int SL_COMMENT=267;
    public static final int NULL_TYPE=207;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=194;
    public static final int GT=228;
    public static final int BNOT=246;
    public static final int WHERE_EXPR=120;
    public static final int END=32;
    public static final int INNERJOIN_EXPR=133;
    public static final int LAND=263;
    public static final int NOT_REGEXP=167;
    public static final int MATCH_UNTIL_EXPR=193;
    public static final int EVENT_PROP_EXPR=140;
    public static final int LBRACK=217;
    public static final int VIEW_EXPR=117;
    public static final int LONG_TYPE=202;
    public static final int TIMEPERIOD_SEC=85;
    public static final int ON_SELECT_EXPR=188;
    public static final int MINUTE_PART=161;
    public static final int PATTERN_NOT_EXPR=103;
    public static final int SUM=17;
    public static final int SQL_NE=225;
    public static final int HexDigit=272;
    public static final int LPAREN=214;
    public static final int IN_SUBSELECT_EXPR=177;
    public static final int AT=78;
    public static final int AS=16;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=91;
    public static final int THEN=31;
    public static final int NOT_IN_RANGE=174;
    public static final int OFFSET=95;
    public static final int LEFT=37;
    public static final int AVG=18;
    public static final int SECOND_PART=162;
    public static final int PREVIOUS=65;
    public static final int IDENT=210;
    public static final int DATABASE_JOIN_EXPR=119;
    public static final int PLUS=232;
    public static final int BXOR=224;
    public static final int CASE2=28;
    public static final int TIMEPERIOD_DAY=79;
    public static final int EXISTS=67;
    public static final int EVENT_PROP_INDEXED=143;
    public static final int TIMEPERIOD_MILLISECOND=89;
    public static final int EVAL_NOTEQUALS_EXPR=126;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=195;
    public static final int CREATE_VARIABLE_EXPR=191;
    public static final int CREATE_WINDOW_COL_TYPE=199;
    public static final int LIKE=8;
    public static final int OUTER=34;
    public static final int BY=42;
    public static final int ARRAY_PARAM_LIST=101;
    public static final int RIGHT_OUTERJOIN_EXPR=135;
    public static final int NUMBERSETSTAR=200;
    public static final int PATTERN_FILTER_EXPR=102;
    public static final int LAST_OPERATOR=180;
    public static final int EVAL_AND_EXPR=123;
    public static final int LEFT_OUTERJOIN_EXPR=134;
    public static final int EPL_EXPR=209;
    public static final int GROUP_BY_EXPR=137;
    public static final int SET=75;
    public static final int RIGHT=38;
    public static final int HAVING=44;
    public static final int INSTANCEOF=70;
    public static final int MIN=20;
    public static final int EVENT_PROP_SIMPLE=141;
    public static final int MINUS=233;
    public static final int SEMI=264;
    public static final int STAR_ASSIGN=252;
    public static final int COLON=219;
    public static final int BAND_ASSIGN=262;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=150;
    public static final int VALUE_NULL=93;
    public static final int NOT_IN_SET=164;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=144;
    public static final int SL=258;
    public static final int WHEN=30;
    public static final int NOT_IN_SUBSELECT_EXPR=178;
    public static final int GUARD_EXPR=115;
    public static final int SR=254;
    public static final int RCURLY=237;
    public static final int PLUS_ASSIGN=248;
    public static final int DAY_PART=159;
    public static final int EXISTS_SUBSELECT_EXPR=176;
    public static final int EVENT_FILTER_IN=110;
    public static final int DIV=234;
    public static final int OBJECT_PARAM_ORDERED_EXPR=99;
    public static final int OctalEscape=271;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=163;
    public static final int PRIOR=66;
    public static final int FIRST=49;
    public static final int ROW_LIMIT_EXPR=94;
    public static final int SELECTION_EXPR=128;
    public static final int LOR=231;
    public static final int CAST=71;
    public static final int LW=69;
    public static final int WILDCARD_SELECT=171;
    public static final int EXPONENT=273;
    public static final int LT=227;
    public static final int PATTERN_INCL_EXPR=118;
    public static final int ORDER_BY_EXPR=138;
    public static final int BOOL_TYPE=206;
    public static final int MOD_ASSIGN=253;
    public static final int CASE=27;
    public static final int IN_SUBSELECT_QUERY_EXPR=179;
    public static final int EQUALS=212;
    public static final int COUNT=25;
    public static final int RETAININTERSECTION=61;
    public static final int DIV_ASSIGN=247;
    public static final int SL_ASSIGN=259;
    public static final int PATTERN=62;
    public static final int SQL=63;
    public static final int WEEKDAY=68;
    public static final int FULL=39;
    public static final int INSERT=51;
    public static final int ESCAPE=10;
    public static final int ARRAY_EXPR=158;
    public static final int LAST=50;
    public static final int BOOLEAN_FALSE=92;
    public static final int SELECT=26;
    public static final int INTO=52;
    public static final int FLOAT_TYPE=203;
    public static final int TIMEPERIOD_SECOND=86;
    public static final int COALESCE=21;
    public static final int EVENT_FILTER_BETWEEN=112;
    public static final int SUBSELECT_EXPR=175;
    public static final int NUMERIC_PARAM_RANGE=96;
    public static final int CONCAT=154;
    public static final int CLASS_IDENT=114;
    public static final int ON_EXPR=186;
    public static final int CREATE_WINDOW_EXPR=184;
    public static final int ON_DELETE_EXPR=187;
    public static final int ON=40;
    public static final int NUM_LONG=242;
    public static final int TIME_PERIOD=157;
    public static final int DOUBLE_TYPE=204;
    public static final int DELETE=73;
    public static final int INT_TYPE=201;
    public static final int EVAL_BITWISE_EXPR=122;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=139;
    public static final int TIMEPERIOD_HOURS=82;
    public static final int VARIABLE=76;
    public static final int SUBSTITUTION=182;
    public static final int UNTIL=77;
    public static final int STRING_TYPE=205;
    public static final int ON_SET_EXPR=190;
    public static final int NUM_INT=238;
    public static final int STDDEV=23;
    public static final int ON_EXPR_FROM=189;
    public static final int NUM_FLOAT=243;
    public static final int FROM=33;
    public static final int DISTINCT=45;
    public static final int OUTPUT=47;
    public static final int EscapeSequence=269;
    public static final int WEEKDAY_OPERATOR=181;
    public static final int WHERE=15;
    public static final int CREATE_WINDOW_COL_TYPE_LIST=198;
    public static final int DEC=251;
    public static final int INNER=35;
    public static final int NUMERIC_PARAM_FREQUENCY=98;
    public static final int BXOR_ASSIGN=260;
    public static final int ORDER=53;
    public static final int SNAPSHOT=74;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=146;
    public static final int EVENT_FILTER_PARAM=107;
    public static final int IRSTREAM=58;
    public static final int MAX=19;
    public static final int TIMEPERIOD_DAYS=80;
    public static final int EVENT_FILTER_RANGE=108;
    public static final int ML_COMMENT=268;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=145;
    public static final int BOR_ASSIGN=261;
    public static final int COMMA=211;
    public static final int WHEN_LIMIT_EXPR=151;
    public static final int IS=41;
    public static final int TIMEPERIOD_LIMIT_EXPR=148;
    public static final int ALL=46;
    public static final int TIMEPERIOD_HOUR=81;
    public static final int BOR=223;
    public static final int EQUAL=244;
    public static final int EVENT_FILTER_NOT_BETWEEN=113;
    public static final int IN_RANGE=173;
    public static final int DOT=213;
    public static final int CURRENT_TIMESTAMP=72;
    public static final int INSERTINTO_EXPR=152;
    public static final int HAVING_EXPR=121;
    public static final int UNIDIRECTIONAL=59;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=197;
    public static final int EVAL_EQUALS_EXPR=125;
    public static final int TIMEPERIOD_MINUTES=84;
    public static final int RSTREAM=56;
    public static final int NOT_LIKE=166;
    public static final int EVENT_LIMIT_EXPR=147;
    public static final int NOT_BETWEEN=165;
    public static final int TIMEPERIOD_MINUTE=83;
    public static final int EVAL_OR_EXPR=124;
    public static final int BAND=222;
    public static final int QUOTED_STRING_LITERAL=221;
    public static final int JOIN=36;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=240;
    public static final int OBSERVER_EXPR=116;
    public static final int EVENT_FILTER_IDENT=106;
    public static final int EVENT_PROP_MAPPED=142;
    public static final int UnicodeEscape=270;
    public static final int AVEDEV=24;
    public static final int DBSELECT_EXPR=168;
    public static final int SELECTION_ELEMENT_EXPR=129;
    public static final int CREATE_WINDOW_SELECT_EXPR=185;
    public static final int INSERTINTO_EXPRCOL=153;
    public static final int WINDOW=5;
    public static final int DESC=55;
    public static final int SELECTION_STREAM=130;
    public static final int SR_ASSIGN=255;
    public static final int DBFROM_CLAUSE=169;
    public static final int LE=229;
    public static final int EVAL_IDENT=127;

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
      
      public String getErrorMessage(RecognitionException e, String[] tokenNames) {
        if(e instanceof EarlyExitException)
            {
                throw new RuntimeException(e);
            }
        return super.getErrorMessage(e, tokenNames);
      }


    // delegates
    // delegators

    public EsperEPL2GrammarLexer() {;} 
    public EsperEPL2GrammarLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public EsperEPL2GrammarLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "EsperEPL2Grammar.g"; }

    // $ANTLR start "CREATE"
    public final void mCREATE() throws RecognitionException {
        try {
            int _type = CREATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:32:8: ( 'create' )
            // EsperEPL2Grammar.g:32:10: 'create'
            {
            match("create"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CREATE"

    // $ANTLR start "WINDOW"
    public final void mWINDOW() throws RecognitionException {
        try {
            int _type = WINDOW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:33:8: ( 'window' )
            // EsperEPL2Grammar.g:33:10: 'window'
            {
            match("window"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WINDOW"

    // $ANTLR start "IN_SET"
    public final void mIN_SET() throws RecognitionException {
        try {
            int _type = IN_SET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:34:8: ( 'in' )
            // EsperEPL2Grammar.g:34:10: 'in'
            {
            match("in"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IN_SET"

    // $ANTLR start "BETWEEN"
    public final void mBETWEEN() throws RecognitionException {
        try {
            int _type = BETWEEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:35:9: ( 'between' )
            // EsperEPL2Grammar.g:35:11: 'between'
            {
            match("between"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BETWEEN"

    // $ANTLR start "LIKE"
    public final void mLIKE() throws RecognitionException {
        try {
            int _type = LIKE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:36:6: ( 'like' )
            // EsperEPL2Grammar.g:36:8: 'like'
            {
            match("like"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LIKE"

    // $ANTLR start "REGEXP"
    public final void mREGEXP() throws RecognitionException {
        try {
            int _type = REGEXP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:37:8: ( 'regexp' )
            // EsperEPL2Grammar.g:37:10: 'regexp'
            {
            match("regexp"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "REGEXP"

    // $ANTLR start "ESCAPE"
    public final void mESCAPE() throws RecognitionException {
        try {
            int _type = ESCAPE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:38:8: ( 'escape' )
            // EsperEPL2Grammar.g:38:10: 'escape'
            {
            match("escape"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESCAPE"

    // $ANTLR start "OR_EXPR"
    public final void mOR_EXPR() throws RecognitionException {
        try {
            int _type = OR_EXPR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:39:9: ( 'or' )
            // EsperEPL2Grammar.g:39:11: 'or'
            {
            match("or"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR_EXPR"

    // $ANTLR start "AND_EXPR"
    public final void mAND_EXPR() throws RecognitionException {
        try {
            int _type = AND_EXPR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:40:10: ( 'and' )
            // EsperEPL2Grammar.g:40:12: 'and'
            {
            match("and"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND_EXPR"

    // $ANTLR start "NOT_EXPR"
    public final void mNOT_EXPR() throws RecognitionException {
        try {
            int _type = NOT_EXPR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:41:10: ( 'not' )
            // EsperEPL2Grammar.g:41:12: 'not'
            {
            match("not"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT_EXPR"

    // $ANTLR start "EVERY_EXPR"
    public final void mEVERY_EXPR() throws RecognitionException {
        try {
            int _type = EVERY_EXPR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:42:12: ( 'every' )
            // EsperEPL2Grammar.g:42:14: 'every'
            {
            match("every"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EVERY_EXPR"

    // $ANTLR start "WHERE"
    public final void mWHERE() throws RecognitionException {
        try {
            int _type = WHERE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:43:7: ( 'where' )
            // EsperEPL2Grammar.g:43:9: 'where'
            {
            match("where"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHERE"

    // $ANTLR start "AS"
    public final void mAS() throws RecognitionException {
        try {
            int _type = AS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:44:4: ( 'as' )
            // EsperEPL2Grammar.g:44:6: 'as'
            {
            match("as"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AS"

    // $ANTLR start "SUM"
    public final void mSUM() throws RecognitionException {
        try {
            int _type = SUM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:45:5: ( 'sum' )
            // EsperEPL2Grammar.g:45:7: 'sum'
            {
            match("sum"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SUM"

    // $ANTLR start "AVG"
    public final void mAVG() throws RecognitionException {
        try {
            int _type = AVG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:46:5: ( 'avg' )
            // EsperEPL2Grammar.g:46:7: 'avg'
            {
            match("avg"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AVG"

    // $ANTLR start "MAX"
    public final void mMAX() throws RecognitionException {
        try {
            int _type = MAX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:47:5: ( 'max' )
            // EsperEPL2Grammar.g:47:7: 'max'
            {
            match("max"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MAX"

    // $ANTLR start "MIN"
    public final void mMIN() throws RecognitionException {
        try {
            int _type = MIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:48:5: ( 'min' )
            // EsperEPL2Grammar.g:48:7: 'min'
            {
            match("min"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MIN"

    // $ANTLR start "COALESCE"
    public final void mCOALESCE() throws RecognitionException {
        try {
            int _type = COALESCE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:49:10: ( 'coalesce' )
            // EsperEPL2Grammar.g:49:12: 'coalesce'
            {
            match("coalesce"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COALESCE"

    // $ANTLR start "MEDIAN"
    public final void mMEDIAN() throws RecognitionException {
        try {
            int _type = MEDIAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:50:8: ( 'median' )
            // EsperEPL2Grammar.g:50:10: 'median'
            {
            match("median"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MEDIAN"

    // $ANTLR start "STDDEV"
    public final void mSTDDEV() throws RecognitionException {
        try {
            int _type = STDDEV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:51:8: ( 'stddev' )
            // EsperEPL2Grammar.g:51:10: 'stddev'
            {
            match("stddev"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STDDEV"

    // $ANTLR start "AVEDEV"
    public final void mAVEDEV() throws RecognitionException {
        try {
            int _type = AVEDEV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:52:8: ( 'avedev' )
            // EsperEPL2Grammar.g:52:10: 'avedev'
            {
            match("avedev"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AVEDEV"

    // $ANTLR start "COUNT"
    public final void mCOUNT() throws RecognitionException {
        try {
            int _type = COUNT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:53:7: ( 'count' )
            // EsperEPL2Grammar.g:53:9: 'count'
            {
            match("count"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COUNT"

    // $ANTLR start "SELECT"
    public final void mSELECT() throws RecognitionException {
        try {
            int _type = SELECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:54:8: ( 'select' )
            // EsperEPL2Grammar.g:54:10: 'select'
            {
            match("select"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SELECT"

    // $ANTLR start "CASE"
    public final void mCASE() throws RecognitionException {
        try {
            int _type = CASE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:55:6: ( 'case' )
            // EsperEPL2Grammar.g:55:8: 'case'
            {
            match("case"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CASE"

    // $ANTLR start "ELSE"
    public final void mELSE() throws RecognitionException {
        try {
            int _type = ELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:56:6: ( 'else' )
            // EsperEPL2Grammar.g:56:8: 'else'
            {
            match("else"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ELSE"

    // $ANTLR start "WHEN"
    public final void mWHEN() throws RecognitionException {
        try {
            int _type = WHEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:57:6: ( 'when' )
            // EsperEPL2Grammar.g:57:8: 'when'
            {
            match("when"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHEN"

    // $ANTLR start "THEN"
    public final void mTHEN() throws RecognitionException {
        try {
            int _type = THEN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:58:6: ( 'then' )
            // EsperEPL2Grammar.g:58:8: 'then'
            {
            match("then"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "THEN"

    // $ANTLR start "END"
    public final void mEND() throws RecognitionException {
        try {
            int _type = END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:59:5: ( 'end' )
            // EsperEPL2Grammar.g:59:7: 'end'
            {
            match("end"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END"

    // $ANTLR start "FROM"
    public final void mFROM() throws RecognitionException {
        try {
            int _type = FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:60:6: ( 'from' )
            // EsperEPL2Grammar.g:60:8: 'from'
            {
            match("from"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FROM"

    // $ANTLR start "OUTER"
    public final void mOUTER() throws RecognitionException {
        try {
            int _type = OUTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:61:7: ( 'outer' )
            // EsperEPL2Grammar.g:61:9: 'outer'
            {
            match("outer"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OUTER"

    // $ANTLR start "INNER"
    public final void mINNER() throws RecognitionException {
        try {
            int _type = INNER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:62:7: ( 'inner' )
            // EsperEPL2Grammar.g:62:9: 'inner'
            {
            match("inner"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INNER"

    // $ANTLR start "JOIN"
    public final void mJOIN() throws RecognitionException {
        try {
            int _type = JOIN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:63:6: ( 'join' )
            // EsperEPL2Grammar.g:63:8: 'join'
            {
            match("join"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "JOIN"

    // $ANTLR start "LEFT"
    public final void mLEFT() throws RecognitionException {
        try {
            int _type = LEFT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:64:6: ( 'left' )
            // EsperEPL2Grammar.g:64:8: 'left'
            {
            match("left"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFT"

    // $ANTLR start "RIGHT"
    public final void mRIGHT() throws RecognitionException {
        try {
            int _type = RIGHT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:65:7: ( 'right' )
            // EsperEPL2Grammar.g:65:9: 'right'
            {
            match("right"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RIGHT"

    // $ANTLR start "FULL"
    public final void mFULL() throws RecognitionException {
        try {
            int _type = FULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:66:6: ( 'full' )
            // EsperEPL2Grammar.g:66:8: 'full'
            {
            match("full"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FULL"

    // $ANTLR start "ON"
    public final void mON() throws RecognitionException {
        try {
            int _type = ON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:67:4: ( 'on' )
            // EsperEPL2Grammar.g:67:6: 'on'
            {
            match("on"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ON"

    // $ANTLR start "IS"
    public final void mIS() throws RecognitionException {
        try {
            int _type = IS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:68:4: ( 'is' )
            // EsperEPL2Grammar.g:68:6: 'is'
            {
            match("is"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IS"

    // $ANTLR start "BY"
    public final void mBY() throws RecognitionException {
        try {
            int _type = BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:69:4: ( 'by' )
            // EsperEPL2Grammar.g:69:6: 'by'
            {
            match("by"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BY"

    // $ANTLR start "GROUP"
    public final void mGROUP() throws RecognitionException {
        try {
            int _type = GROUP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:70:7: ( 'group' )
            // EsperEPL2Grammar.g:70:9: 'group'
            {
            match("group"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GROUP"

    // $ANTLR start "HAVING"
    public final void mHAVING() throws RecognitionException {
        try {
            int _type = HAVING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:71:8: ( 'having' )
            // EsperEPL2Grammar.g:71:10: 'having'
            {
            match("having"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "HAVING"

    // $ANTLR start "DISTINCT"
    public final void mDISTINCT() throws RecognitionException {
        try {
            int _type = DISTINCT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:72:10: ( 'distinct' )
            // EsperEPL2Grammar.g:72:12: 'distinct'
            {
            match("distinct"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DISTINCT"

    // $ANTLR start "ALL"
    public final void mALL() throws RecognitionException {
        try {
            int _type = ALL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:73:5: ( 'all' )
            // EsperEPL2Grammar.g:73:7: 'all'
            {
            match("all"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ALL"

    // $ANTLR start "OUTPUT"
    public final void mOUTPUT() throws RecognitionException {
        try {
            int _type = OUTPUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:74:8: ( 'output' )
            // EsperEPL2Grammar.g:74:10: 'output'
            {
            match("output"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OUTPUT"

    // $ANTLR start "EVENTS"
    public final void mEVENTS() throws RecognitionException {
        try {
            int _type = EVENTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:75:8: ( 'events' )
            // EsperEPL2Grammar.g:75:10: 'events'
            {
            match("events"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EVENTS"

    // $ANTLR start "FIRST"
    public final void mFIRST() throws RecognitionException {
        try {
            int _type = FIRST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:76:7: ( 'first' )
            // EsperEPL2Grammar.g:76:9: 'first'
            {
            match("first"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FIRST"

    // $ANTLR start "LAST"
    public final void mLAST() throws RecognitionException {
        try {
            int _type = LAST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:77:6: ( 'last' )
            // EsperEPL2Grammar.g:77:8: 'last'
            {
            match("last"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LAST"

    // $ANTLR start "INSERT"
    public final void mINSERT() throws RecognitionException {
        try {
            int _type = INSERT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:78:8: ( 'insert' )
            // EsperEPL2Grammar.g:78:10: 'insert'
            {
            match("insert"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INSERT"

    // $ANTLR start "INTO"
    public final void mINTO() throws RecognitionException {
        try {
            int _type = INTO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:79:6: ( 'into' )
            // EsperEPL2Grammar.g:79:8: 'into'
            {
            match("into"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTO"

    // $ANTLR start "ORDER"
    public final void mORDER() throws RecognitionException {
        try {
            int _type = ORDER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:80:7: ( 'order' )
            // EsperEPL2Grammar.g:80:9: 'order'
            {
            match("order"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ORDER"

    // $ANTLR start "ASC"
    public final void mASC() throws RecognitionException {
        try {
            int _type = ASC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:81:5: ( 'asc' )
            // EsperEPL2Grammar.g:81:7: 'asc'
            {
            match("asc"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASC"

    // $ANTLR start "DESC"
    public final void mDESC() throws RecognitionException {
        try {
            int _type = DESC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:82:6: ( 'desc' )
            // EsperEPL2Grammar.g:82:8: 'desc'
            {
            match("desc"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DESC"

    // $ANTLR start "RSTREAM"
    public final void mRSTREAM() throws RecognitionException {
        try {
            int _type = RSTREAM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:83:9: ( 'rstream' )
            // EsperEPL2Grammar.g:83:11: 'rstream'
            {
            match("rstream"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RSTREAM"

    // $ANTLR start "ISTREAM"
    public final void mISTREAM() throws RecognitionException {
        try {
            int _type = ISTREAM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:84:9: ( 'istream' )
            // EsperEPL2Grammar.g:84:11: 'istream'
            {
            match("istream"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ISTREAM"

    // $ANTLR start "IRSTREAM"
    public final void mIRSTREAM() throws RecognitionException {
        try {
            int _type = IRSTREAM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:85:10: ( 'irstream' )
            // EsperEPL2Grammar.g:85:12: 'irstream'
            {
            match("irstream"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IRSTREAM"

    // $ANTLR start "UNIDIRECTIONAL"
    public final void mUNIDIRECTIONAL() throws RecognitionException {
        try {
            int _type = UNIDIRECTIONAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:86:16: ( 'unidirectional' )
            // EsperEPL2Grammar.g:86:18: 'unidirectional'
            {
            match("unidirectional"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNIDIRECTIONAL"

    // $ANTLR start "RETAINUNION"
    public final void mRETAINUNION() throws RecognitionException {
        try {
            int _type = RETAINUNION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:87:13: ( 'retain-union' )
            // EsperEPL2Grammar.g:87:15: 'retain-union'
            {
            match("retain-union"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RETAINUNION"

    // $ANTLR start "RETAININTERSECTION"
    public final void mRETAININTERSECTION() throws RecognitionException {
        try {
            int _type = RETAININTERSECTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:88:20: ( 'retain-intersection' )
            // EsperEPL2Grammar.g:88:22: 'retain-intersection'
            {
            match("retain-intersection"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RETAININTERSECTION"

    // $ANTLR start "PATTERN"
    public final void mPATTERN() throws RecognitionException {
        try {
            int _type = PATTERN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:89:9: ( 'pattern' )
            // EsperEPL2Grammar.g:89:11: 'pattern'
            {
            match("pattern"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PATTERN"

    // $ANTLR start "SQL"
    public final void mSQL() throws RecognitionException {
        try {
            int _type = SQL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:90:5: ( 'sql' )
            // EsperEPL2Grammar.g:90:7: 'sql'
            {
            match("sql"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SQL"

    // $ANTLR start "METADATASQL"
    public final void mMETADATASQL() throws RecognitionException {
        try {
            int _type = METADATASQL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:91:13: ( 'metadatasql' )
            // EsperEPL2Grammar.g:91:15: 'metadatasql'
            {
            match("metadatasql"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "METADATASQL"

    // $ANTLR start "PREVIOUS"
    public final void mPREVIOUS() throws RecognitionException {
        try {
            int _type = PREVIOUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:92:10: ( 'prev' )
            // EsperEPL2Grammar.g:92:12: 'prev'
            {
            match("prev"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PREVIOUS"

    // $ANTLR start "PRIOR"
    public final void mPRIOR() throws RecognitionException {
        try {
            int _type = PRIOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:93:7: ( 'prior' )
            // EsperEPL2Grammar.g:93:9: 'prior'
            {
            match("prior"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PRIOR"

    // $ANTLR start "EXISTS"
    public final void mEXISTS() throws RecognitionException {
        try {
            int _type = EXISTS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:94:8: ( 'exists' )
            // EsperEPL2Grammar.g:94:10: 'exists'
            {
            match("exists"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXISTS"

    // $ANTLR start "WEEKDAY"
    public final void mWEEKDAY() throws RecognitionException {
        try {
            int _type = WEEKDAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:95:9: ( 'weekday' )
            // EsperEPL2Grammar.g:95:11: 'weekday'
            {
            match("weekday"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WEEKDAY"

    // $ANTLR start "LW"
    public final void mLW() throws RecognitionException {
        try {
            int _type = LW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:96:4: ( 'lastweekday' )
            // EsperEPL2Grammar.g:96:6: 'lastweekday'
            {
            match("lastweekday"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LW"

    // $ANTLR start "INSTANCEOF"
    public final void mINSTANCEOF() throws RecognitionException {
        try {
            int _type = INSTANCEOF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:97:12: ( 'instanceof' )
            // EsperEPL2Grammar.g:97:14: 'instanceof'
            {
            match("instanceof"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INSTANCEOF"

    // $ANTLR start "CAST"
    public final void mCAST() throws RecognitionException {
        try {
            int _type = CAST;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:98:6: ( 'cast' )
            // EsperEPL2Grammar.g:98:8: 'cast'
            {
            match("cast"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CAST"

    // $ANTLR start "CURRENT_TIMESTAMP"
    public final void mCURRENT_TIMESTAMP() throws RecognitionException {
        try {
            int _type = CURRENT_TIMESTAMP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:99:19: ( 'current_timestamp' )
            // EsperEPL2Grammar.g:99:21: 'current_timestamp'
            {
            match("current_timestamp"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CURRENT_TIMESTAMP"

    // $ANTLR start "DELETE"
    public final void mDELETE() throws RecognitionException {
        try {
            int _type = DELETE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:100:8: ( 'delete' )
            // EsperEPL2Grammar.g:100:10: 'delete'
            {
            match("delete"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DELETE"

    // $ANTLR start "SNAPSHOT"
    public final void mSNAPSHOT() throws RecognitionException {
        try {
            int _type = SNAPSHOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:101:10: ( 'snapshot' )
            // EsperEPL2Grammar.g:101:12: 'snapshot'
            {
            match("snapshot"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SNAPSHOT"

    // $ANTLR start "SET"
    public final void mSET() throws RecognitionException {
        try {
            int _type = SET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:102:5: ( 'set' )
            // EsperEPL2Grammar.g:102:7: 'set'
            {
            match("set"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SET"

    // $ANTLR start "VARIABLE"
    public final void mVARIABLE() throws RecognitionException {
        try {
            int _type = VARIABLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:103:10: ( 'variable' )
            // EsperEPL2Grammar.g:103:12: 'variable'
            {
            match("variable"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VARIABLE"

    // $ANTLR start "UNTIL"
    public final void mUNTIL() throws RecognitionException {
        try {
            int _type = UNTIL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:104:7: ( 'until' )
            // EsperEPL2Grammar.g:104:9: 'until'
            {
            match("until"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNTIL"

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:105:4: ( 'at' )
            // EsperEPL2Grammar.g:105:6: 'at'
            {
            match("at"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "TIMEPERIOD_DAY"
    public final void mTIMEPERIOD_DAY() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_DAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:106:16: ( 'day' )
            // EsperEPL2Grammar.g:106:18: 'day'
            {
            match("day"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMEPERIOD_DAY"

    // $ANTLR start "TIMEPERIOD_DAYS"
    public final void mTIMEPERIOD_DAYS() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_DAYS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:107:17: ( 'days' )
            // EsperEPL2Grammar.g:107:19: 'days'
            {
            match("days"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMEPERIOD_DAYS"

    // $ANTLR start "TIMEPERIOD_HOUR"
    public final void mTIMEPERIOD_HOUR() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_HOUR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:108:17: ( 'hour' )
            // EsperEPL2Grammar.g:108:19: 'hour'
            {
            match("hour"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMEPERIOD_HOUR"

    // $ANTLR start "TIMEPERIOD_HOURS"
    public final void mTIMEPERIOD_HOURS() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_HOURS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:109:18: ( 'hours' )
            // EsperEPL2Grammar.g:109:20: 'hours'
            {
            match("hours"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMEPERIOD_HOURS"

    // $ANTLR start "TIMEPERIOD_MINUTE"
    public final void mTIMEPERIOD_MINUTE() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_MINUTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:110:19: ( 'minute' )
            // EsperEPL2Grammar.g:110:21: 'minute'
            {
            match("minute"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMEPERIOD_MINUTE"

    // $ANTLR start "TIMEPERIOD_MINUTES"
    public final void mTIMEPERIOD_MINUTES() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_MINUTES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:111:20: ( 'minutes' )
            // EsperEPL2Grammar.g:111:22: 'minutes'
            {
            match("minutes"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMEPERIOD_MINUTES"

    // $ANTLR start "TIMEPERIOD_SEC"
    public final void mTIMEPERIOD_SEC() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_SEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:112:16: ( 'sec' )
            // EsperEPL2Grammar.g:112:18: 'sec'
            {
            match("sec"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMEPERIOD_SEC"

    // $ANTLR start "TIMEPERIOD_SECOND"
    public final void mTIMEPERIOD_SECOND() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_SECOND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:113:19: ( 'second' )
            // EsperEPL2Grammar.g:113:21: 'second'
            {
            match("second"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMEPERIOD_SECOND"

    // $ANTLR start "TIMEPERIOD_SECONDS"
    public final void mTIMEPERIOD_SECONDS() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_SECONDS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:114:20: ( 'seconds' )
            // EsperEPL2Grammar.g:114:22: 'seconds'
            {
            match("seconds"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMEPERIOD_SECONDS"

    // $ANTLR start "TIMEPERIOD_MILLISEC"
    public final void mTIMEPERIOD_MILLISEC() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_MILLISEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:115:21: ( 'msec' )
            // EsperEPL2Grammar.g:115:23: 'msec'
            {
            match("msec"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMEPERIOD_MILLISEC"

    // $ANTLR start "TIMEPERIOD_MILLISECOND"
    public final void mTIMEPERIOD_MILLISECOND() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_MILLISECOND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:116:24: ( 'millisecond' )
            // EsperEPL2Grammar.g:116:26: 'millisecond'
            {
            match("millisecond"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMEPERIOD_MILLISECOND"

    // $ANTLR start "TIMEPERIOD_MILLISECONDS"
    public final void mTIMEPERIOD_MILLISECONDS() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_MILLISECONDS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:117:25: ( 'milliseconds' )
            // EsperEPL2Grammar.g:117:27: 'milliseconds'
            {
            match("milliseconds"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TIMEPERIOD_MILLISECONDS"

    // $ANTLR start "BOOLEAN_TRUE"
    public final void mBOOLEAN_TRUE() throws RecognitionException {
        try {
            int _type = BOOLEAN_TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:118:14: ( 'true' )
            // EsperEPL2Grammar.g:118:16: 'true'
            {
            match("true"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BOOLEAN_TRUE"

    // $ANTLR start "BOOLEAN_FALSE"
    public final void mBOOLEAN_FALSE() throws RecognitionException {
        try {
            int _type = BOOLEAN_FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:119:15: ( 'false' )
            // EsperEPL2Grammar.g:119:17: 'false'
            {
            match("false"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BOOLEAN_FALSE"

    // $ANTLR start "VALUE_NULL"
    public final void mVALUE_NULL() throws RecognitionException {
        try {
            int _type = VALUE_NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:120:12: ( 'null' )
            // EsperEPL2Grammar.g:120:14: 'null'
            {
            match("null"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VALUE_NULL"

    // $ANTLR start "ROW_LIMIT_EXPR"
    public final void mROW_LIMIT_EXPR() throws RecognitionException {
        try {
            int _type = ROW_LIMIT_EXPR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:121:16: ( 'limit' )
            // EsperEPL2Grammar.g:121:18: 'limit'
            {
            match("limit"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ROW_LIMIT_EXPR"

    // $ANTLR start "OFFSET"
    public final void mOFFSET() throws RecognitionException {
        try {
            int _type = OFFSET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:122:8: ( 'offset' )
            // EsperEPL2Grammar.g:122:10: 'offset'
            {
            match("offset"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OFFSET"

    // $ANTLR start "FOLLOWED_BY"
    public final void mFOLLOWED_BY() throws RecognitionException {
        try {
            int _type = FOLLOWED_BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1346:14: ( '->' )
            // EsperEPL2Grammar.g:1346:16: '->'
            {
            match("->"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FOLLOWED_BY"

    // $ANTLR start "EQUALS"
    public final void mEQUALS() throws RecognitionException {
        try {
            int _type = EQUALS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1347:10: ( '=' )
            // EsperEPL2Grammar.g:1347:12: '='
            {
            match('='); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUALS"

    // $ANTLR start "SQL_NE"
    public final void mSQL_NE() throws RecognitionException {
        try {
            int _type = SQL_NE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1348:10: ( '<>' )
            // EsperEPL2Grammar.g:1348:12: '<>'
            {
            match("<>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SQL_NE"

    // $ANTLR start "QUESTION"
    public final void mQUESTION() throws RecognitionException {
        try {
            int _type = QUESTION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1349:11: ( '?' )
            // EsperEPL2Grammar.g:1349:13: '?'
            {
            match('?'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QUESTION"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1350:10: ( '(' )
            // EsperEPL2Grammar.g:1350:12: '('
            {
            match('('); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1351:10: ( ')' )
            // EsperEPL2Grammar.g:1351:12: ')'
            {
            match(')'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "LBRACK"
    public final void mLBRACK() throws RecognitionException {
        try {
            int _type = LBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1352:10: ( '[' )
            // EsperEPL2Grammar.g:1352:12: '['
            {
            match('['); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRACK"

    // $ANTLR start "RBRACK"
    public final void mRBRACK() throws RecognitionException {
        try {
            int _type = RBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1353:10: ( ']' )
            // EsperEPL2Grammar.g:1353:12: ']'
            {
            match(']'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRACK"

    // $ANTLR start "LCURLY"
    public final void mLCURLY() throws RecognitionException {
        try {
            int _type = LCURLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1354:10: ( '{' )
            // EsperEPL2Grammar.g:1354:12: '{'
            {
            match('{'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LCURLY"

    // $ANTLR start "RCURLY"
    public final void mRCURLY() throws RecognitionException {
        try {
            int _type = RCURLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1355:10: ( '}' )
            // EsperEPL2Grammar.g:1355:12: '}'
            {
            match('}'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RCURLY"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1356:9: ( ':' )
            // EsperEPL2Grammar.g:1356:11: ':'
            {
            match(':'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1357:9: ( ',' )
            // EsperEPL2Grammar.g:1357:11: ','
            {
            match(','); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "EQUAL"
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1358:9: ( '==' )
            // EsperEPL2Grammar.g:1358:11: '=='
            {
            match("=="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUAL"

    // $ANTLR start "LNOT"
    public final void mLNOT() throws RecognitionException {
        try {
            int _type = LNOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1359:8: ( '!' )
            // EsperEPL2Grammar.g:1359:10: '!'
            {
            match('!'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LNOT"

    // $ANTLR start "BNOT"
    public final void mBNOT() throws RecognitionException {
        try {
            int _type = BNOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1360:8: ( '~' )
            // EsperEPL2Grammar.g:1360:10: '~'
            {
            match('~'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BNOT"

    // $ANTLR start "NOT_EQUAL"
    public final void mNOT_EQUAL() throws RecognitionException {
        try {
            int _type = NOT_EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1361:12: ( '!=' )
            // EsperEPL2Grammar.g:1361:14: '!='
            {
            match("!="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT_EQUAL"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1362:7: ( '/' )
            // EsperEPL2Grammar.g:1362:9: '/'
            {
            match('/'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "DIV_ASSIGN"
    public final void mDIV_ASSIGN() throws RecognitionException {
        try {
            int _type = DIV_ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1363:13: ( '/=' )
            // EsperEPL2Grammar.g:1363:15: '/='
            {
            match("/="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIV_ASSIGN"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1364:8: ( '+' )
            // EsperEPL2Grammar.g:1364:10: '+'
            {
            match('+'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "PLUS_ASSIGN"
    public final void mPLUS_ASSIGN() throws RecognitionException {
        try {
            int _type = PLUS_ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1365:13: ( '+=' )
            // EsperEPL2Grammar.g:1365:15: '+='
            {
            match("+="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS_ASSIGN"

    // $ANTLR start "INC"
    public final void mINC() throws RecognitionException {
        try {
            int _type = INC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1366:7: ( '++' )
            // EsperEPL2Grammar.g:1366:9: '++'
            {
            match("++"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INC"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1367:9: ( '-' )
            // EsperEPL2Grammar.g:1367:11: '-'
            {
            match('-'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "MINUS_ASSIGN"
    public final void mMINUS_ASSIGN() throws RecognitionException {
        try {
            int _type = MINUS_ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1368:15: ( '-=' )
            // EsperEPL2Grammar.g:1368:17: '-='
            {
            match("-="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS_ASSIGN"

    // $ANTLR start "DEC"
    public final void mDEC() throws RecognitionException {
        try {
            int _type = DEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1369:7: ( '--' )
            // EsperEPL2Grammar.g:1369:9: '--'
            {
            match("--"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DEC"

    // $ANTLR start "STAR"
    public final void mSTAR() throws RecognitionException {
        try {
            int _type = STAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1370:8: ( '*' )
            // EsperEPL2Grammar.g:1370:10: '*'
            {
            match('*'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STAR"

    // $ANTLR start "STAR_ASSIGN"
    public final void mSTAR_ASSIGN() throws RecognitionException {
        try {
            int _type = STAR_ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1371:14: ( '*=' )
            // EsperEPL2Grammar.g:1371:16: '*='
            {
            match("*="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STAR_ASSIGN"

    // $ANTLR start "MOD"
    public final void mMOD() throws RecognitionException {
        try {
            int _type = MOD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1372:7: ( '%' )
            // EsperEPL2Grammar.g:1372:9: '%'
            {
            match('%'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MOD"

    // $ANTLR start "MOD_ASSIGN"
    public final void mMOD_ASSIGN() throws RecognitionException {
        try {
            int _type = MOD_ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1373:13: ( '%=' )
            // EsperEPL2Grammar.g:1373:15: '%='
            {
            match("%="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MOD_ASSIGN"

    // $ANTLR start "SR"
    public final void mSR() throws RecognitionException {
        try {
            int _type = SR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1374:6: ( '>>' )
            // EsperEPL2Grammar.g:1374:8: '>>'
            {
            match(">>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SR"

    // $ANTLR start "SR_ASSIGN"
    public final void mSR_ASSIGN() throws RecognitionException {
        try {
            int _type = SR_ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1375:12: ( '>>=' )
            // EsperEPL2Grammar.g:1375:14: '>>='
            {
            match(">>="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SR_ASSIGN"

    // $ANTLR start "BSR"
    public final void mBSR() throws RecognitionException {
        try {
            int _type = BSR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1376:7: ( '>>>' )
            // EsperEPL2Grammar.g:1376:9: '>>>'
            {
            match(">>>"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BSR"

    // $ANTLR start "BSR_ASSIGN"
    public final void mBSR_ASSIGN() throws RecognitionException {
        try {
            int _type = BSR_ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1377:13: ( '>>>=' )
            // EsperEPL2Grammar.g:1377:15: '>>>='
            {
            match(">>>="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BSR_ASSIGN"

    // $ANTLR start "GE"
    public final void mGE() throws RecognitionException {
        try {
            int _type = GE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1378:6: ( '>=' )
            // EsperEPL2Grammar.g:1378:8: '>='
            {
            match(">="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GE"

    // $ANTLR start "GT"
    public final void mGT() throws RecognitionException {
        try {
            int _type = GT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1379:6: ( '>' )
            // EsperEPL2Grammar.g:1379:8: '>'
            {
            match('>'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GT"

    // $ANTLR start "SL"
    public final void mSL() throws RecognitionException {
        try {
            int _type = SL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1380:6: ( '<<' )
            // EsperEPL2Grammar.g:1380:8: '<<'
            {
            match("<<"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SL"

    // $ANTLR start "SL_ASSIGN"
    public final void mSL_ASSIGN() throws RecognitionException {
        try {
            int _type = SL_ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1381:12: ( '<<=' )
            // EsperEPL2Grammar.g:1381:14: '<<='
            {
            match("<<="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SL_ASSIGN"

    // $ANTLR start "LE"
    public final void mLE() throws RecognitionException {
        try {
            int _type = LE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1382:6: ( '<=' )
            // EsperEPL2Grammar.g:1382:8: '<='
            {
            match("<="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LE"

    // $ANTLR start "LT"
    public final void mLT() throws RecognitionException {
        try {
            int _type = LT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1383:6: ( '<' )
            // EsperEPL2Grammar.g:1383:8: '<'
            {
            match('<'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LT"

    // $ANTLR start "BXOR"
    public final void mBXOR() throws RecognitionException {
        try {
            int _type = BXOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1384:8: ( '^' )
            // EsperEPL2Grammar.g:1384:10: '^'
            {
            match('^'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BXOR"

    // $ANTLR start "BXOR_ASSIGN"
    public final void mBXOR_ASSIGN() throws RecognitionException {
        try {
            int _type = BXOR_ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1385:14: ( '^=' )
            // EsperEPL2Grammar.g:1385:16: '^='
            {
            match("^="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BXOR_ASSIGN"

    // $ANTLR start "BOR"
    public final void mBOR() throws RecognitionException {
        try {
            int _type = BOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1386:6: ( '|' )
            // EsperEPL2Grammar.g:1386:8: '|'
            {
            match('|'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BOR"

    // $ANTLR start "BOR_ASSIGN"
    public final void mBOR_ASSIGN() throws RecognitionException {
        try {
            int _type = BOR_ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1387:13: ( '|=' )
            // EsperEPL2Grammar.g:1387:15: '|='
            {
            match("|="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BOR_ASSIGN"

    // $ANTLR start "LOR"
    public final void mLOR() throws RecognitionException {
        try {
            int _type = LOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1388:6: ( '||' )
            // EsperEPL2Grammar.g:1388:8: '||'
            {
            match("||"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LOR"

    // $ANTLR start "BAND"
    public final void mBAND() throws RecognitionException {
        try {
            int _type = BAND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1389:8: ( '&' )
            // EsperEPL2Grammar.g:1389:10: '&'
            {
            match('&'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BAND"

    // $ANTLR start "BAND_ASSIGN"
    public final void mBAND_ASSIGN() throws RecognitionException {
        try {
            int _type = BAND_ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1390:14: ( '&=' )
            // EsperEPL2Grammar.g:1390:16: '&='
            {
            match("&="); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BAND_ASSIGN"

    // $ANTLR start "LAND"
    public final void mLAND() throws RecognitionException {
        try {
            int _type = LAND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1391:8: ( '&&' )
            // EsperEPL2Grammar.g:1391:10: '&&'
            {
            match("&&"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LAND"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1392:8: ( ';' )
            // EsperEPL2Grammar.g:1392:10: ';'
            {
            match(';'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMI"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1393:7: ( '.' )
            // EsperEPL2Grammar.g:1393:9: '.'
            {
            match('.'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "NUM_LONG"
    public final void mNUM_LONG() throws RecognitionException {
        try {
            int _type = NUM_LONG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1394:10: ( '\\u18FF' )
            // EsperEPL2Grammar.g:1394:12: '\\u18FF'
            {
            match('\u18FF'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUM_LONG"

    // $ANTLR start "NUM_DOUBLE"
    public final void mNUM_DOUBLE() throws RecognitionException {
        try {
            int _type = NUM_DOUBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1395:12: ( '\\u18FE' )
            // EsperEPL2Grammar.g:1395:14: '\\u18FE'
            {
            match('\u18FE'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUM_DOUBLE"

    // $ANTLR start "NUM_FLOAT"
    public final void mNUM_FLOAT() throws RecognitionException {
        try {
            int _type = NUM_FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1396:11: ( '\\u18FD' )
            // EsperEPL2Grammar.g:1396:13: '\\u18FD'
            {
            match('\u18FD'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUM_FLOAT"

    // $ANTLR start "ESCAPECHAR"
    public final void mESCAPECHAR() throws RecognitionException {
        try {
            int _type = ESCAPECHAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1397:12: ( '\\\\' )
            // EsperEPL2Grammar.g:1397:14: '\\\\'
            {
            match('\\'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ESCAPECHAR"

    // $ANTLR start "EMAILAT"
    public final void mEMAILAT() throws RecognitionException {
        try {
            int _type = EMAILAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1398:10: ( '@' )
            // EsperEPL2Grammar.g:1398:12: '@'
            {
            match('@'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EMAILAT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1401:4: ( ( ' ' | '\\t' | '\\f' | ( '\\r' | '\\n' ) )+ )
            // EsperEPL2Grammar.g:1401:6: ( ' ' | '\\t' | '\\f' | ( '\\r' | '\\n' ) )+
            {
            // EsperEPL2Grammar.g:1401:6: ( ' ' | '\\t' | '\\f' | ( '\\r' | '\\n' ) )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0>='\t' && LA1_0<='\n')||(LA1_0>='\f' && LA1_0<='\r')||LA1_0==' ') ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // EsperEPL2Grammar.g:
            	    {
            	    if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);

            if ( state.backtracking==0 ) {
               _channel=HIDDEN; 
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "SL_COMMENT"
    public final void mSL_COMMENT() throws RecognitionException {
        try {
            int _type = SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1415:2: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\n' | '\\r' ( '\\n' )? )? )
            // EsperEPL2Grammar.g:1415:4: '//' (~ ( '\\n' | '\\r' ) )* ( '\\n' | '\\r' ( '\\n' )? )?
            {
            match("//"); if (state.failed) return ;

            // EsperEPL2Grammar.g:1416:3: (~ ( '\\n' | '\\r' ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='\u0000' && LA2_0<='\t')||(LA2_0>='\u000B' && LA2_0<='\f')||(LA2_0>='\u000E' && LA2_0<='\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // EsperEPL2Grammar.g:1416:4: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // EsperEPL2Grammar.g:1416:19: ( '\\n' | '\\r' ( '\\n' )? )?
            int alt4=3;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\n') ) {
                alt4=1;
            }
            else if ( (LA4_0=='\r') ) {
                alt4=2;
            }
            switch (alt4) {
                case 1 :
                    // EsperEPL2Grammar.g:1416:20: '\\n'
                    {
                    match('\n'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // EsperEPL2Grammar.g:1416:25: '\\r' ( '\\n' )?
                    {
                    match('\r'); if (state.failed) return ;
                    // EsperEPL2Grammar.g:1416:29: ( '\\n' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='\n') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // EsperEPL2Grammar.g:1416:30: '\\n'
                            {
                            match('\n'); if (state.failed) return ;

                            }
                            break;

                    }


                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              _channel=HIDDEN;
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SL_COMMENT"

    // $ANTLR start "ML_COMMENT"
    public final void mML_COMMENT() throws RecognitionException {
        try {
            int _type = ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1422:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // EsperEPL2Grammar.g:1422:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); if (state.failed) return ;

            // EsperEPL2Grammar.g:1422:14: ( options {greedy=false; } : . )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0=='*') ) {
                    int LA5_1 = input.LA(2);

                    if ( (LA5_1=='/') ) {
                        alt5=2;
                    }
                    else if ( ((LA5_1>='\u0000' && LA5_1<='.')||(LA5_1>='0' && LA5_1<='\uFFFF')) ) {
                        alt5=1;
                    }


                }
                else if ( ((LA5_0>='\u0000' && LA5_0<=')')||(LA5_0>='+' && LA5_0<='\uFFFF')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // EsperEPL2Grammar.g:1422:42: .
            	    {
            	    matchAny(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            match("*/"); if (state.failed) return ;

            if ( state.backtracking==0 ) {
              _channel=HIDDEN;
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ML_COMMENT"

    // $ANTLR start "QUOTED_STRING_LITERAL"
    public final void mQUOTED_STRING_LITERAL() throws RecognitionException {
        try {
            int _type = QUOTED_STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1426:5: ( '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )* '\\'' )
            // EsperEPL2Grammar.g:1426:9: '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )* '\\''
            {
            match('\''); if (state.failed) return ;
            // EsperEPL2Grammar.g:1426:14: ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )*
            loop6:
            do {
                int alt6=3;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='\\') ) {
                    alt6=1;
                }
                else if ( ((LA6_0>='\u0000' && LA6_0<='&')||(LA6_0>='(' && LA6_0<='[')||(LA6_0>=']' && LA6_0<='\uFFFF')) ) {
                    alt6=2;
                }


                switch (alt6) {
            	case 1 :
            	    // EsperEPL2Grammar.g:1426:16: EscapeSequence
            	    {
            	    mEscapeSequence(); if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    // EsperEPL2Grammar.g:1426:33: ~ ( '\\'' | '\\\\' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            match('\''); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QUOTED_STRING_LITERAL"

    // $ANTLR start "STRING_LITERAL"
    public final void mSTRING_LITERAL() throws RecognitionException {
        try {
            int _type = STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1430:5: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' )
            // EsperEPL2Grammar.g:1430:8: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); if (state.failed) return ;
            // EsperEPL2Grammar.g:1430:12: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
            loop7:
            do {
                int alt7=3;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='\\') ) {
                    alt7=1;
                }
                else if ( ((LA7_0>='\u0000' && LA7_0<='!')||(LA7_0>='#' && LA7_0<='[')||(LA7_0>=']' && LA7_0<='\uFFFF')) ) {
                    alt7=2;
                }


                switch (alt7) {
            	case 1 :
            	    // EsperEPL2Grammar.g:1430:14: EscapeSequence
            	    {
            	    mEscapeSequence(); if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    // EsperEPL2Grammar.g:1430:31: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            match('\"'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING_LITERAL"

    // $ANTLR start "EscapeSequence"
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // EsperEPL2Grammar.g:1434:16: ( '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | UnicodeEscape | OctalEscape | . ) )
            // EsperEPL2Grammar.g:1434:18: '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | UnicodeEscape | OctalEscape | . )
            {
            match('\\'); if (state.failed) return ;
            // EsperEPL2Grammar.g:1435:3: ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | UnicodeEscape | OctalEscape | . )
            int alt8=11;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='n') ) {
                alt8=1;
            }
            else if ( (LA8_0=='r') ) {
                alt8=2;
            }
            else if ( (LA8_0=='t') ) {
                alt8=3;
            }
            else if ( (LA8_0=='b') ) {
                alt8=4;
            }
            else if ( (LA8_0=='f') ) {
                alt8=5;
            }
            else if ( (LA8_0=='\"') ) {
                alt8=6;
            }
            else if ( (LA8_0=='\'') ) {
                alt8=7;
            }
            else if ( (LA8_0=='\\') ) {
                switch ( input.LA(2) ) {
                case 'u':
                    {
                    alt8=9;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt8=10;
                    }
                    break;
                default:
                    alt8=8;}

            }
            else if ( ((LA8_0>='\u0000' && LA8_0<='!')||(LA8_0>='#' && LA8_0<='&')||(LA8_0>='(' && LA8_0<='[')||(LA8_0>=']' && LA8_0<='a')||(LA8_0>='c' && LA8_0<='e')||(LA8_0>='g' && LA8_0<='m')||(LA8_0>='o' && LA8_0<='q')||LA8_0=='s'||(LA8_0>='u' && LA8_0<='\uFFFF')) ) {
                alt8=11;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // EsperEPL2Grammar.g:1435:5: 'n'
                    {
                    match('n'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // EsperEPL2Grammar.g:1436:5: 'r'
                    {
                    match('r'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // EsperEPL2Grammar.g:1437:5: 't'
                    {
                    match('t'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // EsperEPL2Grammar.g:1438:5: 'b'
                    {
                    match('b'); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // EsperEPL2Grammar.g:1439:5: 'f'
                    {
                    match('f'); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    // EsperEPL2Grammar.g:1440:5: '\"'
                    {
                    match('\"'); if (state.failed) return ;

                    }
                    break;
                case 7 :
                    // EsperEPL2Grammar.g:1441:5: '\\''
                    {
                    match('\''); if (state.failed) return ;

                    }
                    break;
                case 8 :
                    // EsperEPL2Grammar.g:1442:5: '\\\\'
                    {
                    match('\\'); if (state.failed) return ;

                    }
                    break;
                case 9 :
                    // EsperEPL2Grammar.g:1443:5: UnicodeEscape
                    {
                    mUnicodeEscape(); if (state.failed) return ;

                    }
                    break;
                case 10 :
                    // EsperEPL2Grammar.g:1444:5: OctalEscape
                    {
                    mOctalEscape(); if (state.failed) return ;

                    }
                    break;
                case 11 :
                    // EsperEPL2Grammar.g:1445:5: .
                    {
                    matchAny(); if (state.failed) return ;

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end "EscapeSequence"

    // $ANTLR start "OctalEscape"
    public final void mOctalEscape() throws RecognitionException {
        try {
            // EsperEPL2Grammar.g:1451:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt9=3;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='\\') ) {
                int LA9_1 = input.LA(2);

                if ( ((LA9_1>='0' && LA9_1<='3')) ) {
                    int LA9_2 = input.LA(3);

                    if ( ((LA9_2>='0' && LA9_2<='7')) ) {
                        int LA9_4 = input.LA(4);

                        if ( ((LA9_4>='0' && LA9_4<='7')) ) {
                            alt9=1;
                        }
                        else {
                            alt9=2;}
                    }
                    else {
                        alt9=3;}
                }
                else if ( ((LA9_1>='4' && LA9_1<='7')) ) {
                    int LA9_3 = input.LA(3);

                    if ( ((LA9_3>='0' && LA9_3<='7')) ) {
                        alt9=2;
                    }
                    else {
                        alt9=3;}
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // EsperEPL2Grammar.g:1451:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); if (state.failed) return ;
                    // EsperEPL2Grammar.g:1451:14: ( '0' .. '3' )
                    // EsperEPL2Grammar.g:1451:15: '0' .. '3'
                    {
                    matchRange('0','3'); if (state.failed) return ;

                    }

                    // EsperEPL2Grammar.g:1451:25: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1451:26: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }

                    // EsperEPL2Grammar.g:1451:36: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1451:37: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Grammar.g:1452:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); if (state.failed) return ;
                    // EsperEPL2Grammar.g:1452:14: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1452:15: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }

                    // EsperEPL2Grammar.g:1452:25: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1452:26: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Grammar.g:1453:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); if (state.failed) return ;
                    // EsperEPL2Grammar.g:1453:14: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1453:15: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }


                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "OctalEscape"

    // $ANTLR start "HexDigit"
    public final void mHexDigit() throws RecognitionException {
        try {
            // EsperEPL2Grammar.g:1457:10: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // EsperEPL2Grammar.g:1457:12: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "HexDigit"

    // $ANTLR start "UnicodeEscape"
    public final void mUnicodeEscape() throws RecognitionException {
        try {
            // EsperEPL2Grammar.g:1461:5: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit )
            // EsperEPL2Grammar.g:1461:9: '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit
            {
            match('\\'); if (state.failed) return ;
            match('u'); if (state.failed) return ;
            mHexDigit(); if (state.failed) return ;
            mHexDigit(); if (state.failed) return ;
            mHexDigit(); if (state.failed) return ;
            mHexDigit(); if (state.failed) return ;

            }

        }
        finally {
        }
    }
    // $ANTLR end "UnicodeEscape"

    // $ANTLR start "IDENT"
    public final void mIDENT() throws RecognitionException {
        try {
            int _type = IDENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1468:2: ( ( 'a' .. 'z' | '_' | '$' ) ( 'a' .. 'z' | '_' | '0' .. '9' | '$' )* )
            // EsperEPL2Grammar.g:1468:4: ( 'a' .. 'z' | '_' | '$' ) ( 'a' .. 'z' | '_' | '0' .. '9' | '$' )*
            {
            if ( input.LA(1)=='$'||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // EsperEPL2Grammar.g:1468:23: ( 'a' .. 'z' | '_' | '0' .. '9' | '$' )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0=='$'||(LA10_0>='0' && LA10_0<='9')||LA10_0=='_'||(LA10_0>='a' && LA10_0<='z')) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // EsperEPL2Grammar.g:
            	    {
            	    if ( input.LA(1)=='$'||(input.LA(1)>='0' && input.LA(1)<='9')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IDENT"

    // $ANTLR start "NUM_INT"
    public final void mNUM_INT() throws RecognitionException {
        try {
            int _type = NUM_INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token f1=null;
            Token f2=null;
            Token f3=null;
            Token f4=null;

            boolean isDecimal=false; Token t=null;
            // EsperEPL2Grammar.g:1475:5: ( '.' ( ( '0' .. '9' )+ ( EXPONENT )? (f1= FLOAT_SUFFIX )? )? | ( '0' ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )? | ( '1' .. '9' ) ( '0' .. '9' )* ) ( ( 'l' ) | {...}? ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX ) )? )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0=='.') ) {
                alt27=1;
            }
            else if ( ((LA27_0>='0' && LA27_0<='9')) ) {
                alt27=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // EsperEPL2Grammar.g:1475:9: '.' ( ( '0' .. '9' )+ ( EXPONENT )? (f1= FLOAT_SUFFIX )? )?
                    {
                    match('.'); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                      _type = DOT;
                    }
                    // EsperEPL2Grammar.g:1476:13: ( ( '0' .. '9' )+ ( EXPONENT )? (f1= FLOAT_SUFFIX )? )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( ((LA14_0>='0' && LA14_0<='9')) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // EsperEPL2Grammar.g:1476:15: ( '0' .. '9' )+ ( EXPONENT )? (f1= FLOAT_SUFFIX )?
                            {
                            // EsperEPL2Grammar.g:1476:15: ( '0' .. '9' )+
                            int cnt11=0;
                            loop11:
                            do {
                                int alt11=2;
                                int LA11_0 = input.LA(1);

                                if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                                    alt11=1;
                                }


                                switch (alt11) {
                            	case 1 :
                            	    // EsperEPL2Grammar.g:1476:16: '0' .. '9'
                            	    {
                            	    matchRange('0','9'); if (state.failed) return ;

                            	    }
                            	    break;

                            	default :
                            	    if ( cnt11 >= 1 ) break loop11;
                            	    if (state.backtracking>0) {state.failed=true; return ;}
                                        EarlyExitException eee =
                                            new EarlyExitException(11, input);
                                        throw eee;
                                }
                                cnt11++;
                            } while (true);

                            // EsperEPL2Grammar.g:1476:27: ( EXPONENT )?
                            int alt12=2;
                            int LA12_0 = input.LA(1);

                            if ( (LA12_0=='e') ) {
                                alt12=1;
                            }
                            switch (alt12) {
                                case 1 :
                                    // EsperEPL2Grammar.g:1476:28: EXPONENT
                                    {
                                    mEXPONENT(); if (state.failed) return ;

                                    }
                                    break;

                            }

                            // EsperEPL2Grammar.g:1476:39: (f1= FLOAT_SUFFIX )?
                            int alt13=2;
                            int LA13_0 = input.LA(1);

                            if ( (LA13_0=='d'||LA13_0=='f') ) {
                                alt13=1;
                            }
                            switch (alt13) {
                                case 1 :
                                    // EsperEPL2Grammar.g:1476:40: f1= FLOAT_SUFFIX
                                    {
                                    int f1Start1799 = getCharIndex();
                                    mFLOAT_SUFFIX(); if (state.failed) return ;
                                    f1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, f1Start1799, getCharIndex()-1);
                                    if ( state.backtracking==0 ) {
                                      t=f1;
                                    }

                                    }
                                    break;

                            }

                            if ( state.backtracking==0 ) {

                              				if (t != null && t.getText().toUpperCase().indexOf('F')>=0) {
                                              	_type = NUM_FLOAT;
                              				}
                              				else {
                                              	_type = NUM_DOUBLE; // assume double
                              				}
                              				
                            }

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Grammar.g:1487:4: ( '0' ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )? | ( '1' .. '9' ) ( '0' .. '9' )* ) ( ( 'l' ) | {...}? ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX ) )?
                    {
                    // EsperEPL2Grammar.g:1487:4: ( '0' ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )? | ( '1' .. '9' ) ( '0' .. '9' )* )
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0=='0') ) {
                        alt20=1;
                    }
                    else if ( ((LA20_0>='1' && LA20_0<='9')) ) {
                        alt20=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 20, 0, input);

                        throw nvae;
                    }
                    switch (alt20) {
                        case 1 :
                            // EsperEPL2Grammar.g:1487:6: '0' ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )?
                            {
                            match('0'); if (state.failed) return ;
                            if ( state.backtracking==0 ) {
                              isDecimal = true;
                            }
                            // EsperEPL2Grammar.g:1488:4: ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )?
                            int alt18=4;
                            int LA18_0 = input.LA(1);

                            if ( (LA18_0=='x') ) {
                                alt18=1;
                            }
                            else if ( ((LA18_0>='0' && LA18_0<='7')) ) {
                                int LA18_2 = input.LA(2);

                                if ( (synpred1_EsperEPL2Grammar()) ) {
                                    alt18=2;
                                }
                                else if ( (true) ) {
                                    alt18=3;
                                }
                            }
                            else if ( ((LA18_0>='8' && LA18_0<='9')) && (synpred1_EsperEPL2Grammar())) {
                                alt18=2;
                            }
                            switch (alt18) {
                                case 1 :
                                    // EsperEPL2Grammar.g:1488:6: ( 'x' ) ( HexDigit )+
                                    {
                                    // EsperEPL2Grammar.g:1488:6: ( 'x' )
                                    // EsperEPL2Grammar.g:1488:7: 'x'
                                    {
                                    match('x'); if (state.failed) return ;

                                    }

                                    // EsperEPL2Grammar.g:1489:5: ( HexDigit )+
                                    int cnt15=0;
                                    loop15:
                                    do {
                                        int alt15=2;
                                        switch ( input.LA(1) ) {
                                        case 'e':
                                            {
                                            int LA15_2 = input.LA(2);

                                            if ( ((LA15_2>='0' && LA15_2<='9')) ) {
                                                int LA15_5 = input.LA(3);

                                                if ( (!(((isDecimal)))) ) {
                                                    alt15=1;
                                                }


                                            }

                                            else {
                                                alt15=1;
                                            }

                                            }
                                            break;
                                        case 'd':
                                        case 'f':
                                            {
                                            int LA15_3 = input.LA(2);

                                            if ( (!(((isDecimal)))) ) {
                                                alt15=1;
                                            }


                                            }
                                            break;
                                        case '0':
                                        case '1':
                                        case '2':
                                        case '3':
                                        case '4':
                                        case '5':
                                        case '6':
                                        case '7':
                                        case '8':
                                        case '9':
                                        case 'A':
                                        case 'B':
                                        case 'C':
                                        case 'D':
                                        case 'E':
                                        case 'F':
                                        case 'a':
                                        case 'b':
                                        case 'c':
                                            {
                                            alt15=1;
                                            }
                                            break;

                                        }

                                        switch (alt15) {
                                    	case 1 :
                                    	    // EsperEPL2Grammar.g:1495:6: HexDigit
                                    	    {
                                    	    mHexDigit(); if (state.failed) return ;

                                    	    }
                                    	    break;

                                    	default :
                                    	    if ( cnt15 >= 1 ) break loop15;
                                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                                EarlyExitException eee =
                                                    new EarlyExitException(15, input);
                                                throw eee;
                                        }
                                        cnt15++;
                                    } while (true);


                                    }
                                    break;
                                case 2 :
                                    // EsperEPL2Grammar.g:1499:5: ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+
                                    {
                                    // EsperEPL2Grammar.g:1499:50: ( '0' .. '9' )+
                                    int cnt16=0;
                                    loop16:
                                    do {
                                        int alt16=2;
                                        int LA16_0 = input.LA(1);

                                        if ( ((LA16_0>='0' && LA16_0<='9')) ) {
                                            alt16=1;
                                        }


                                        switch (alt16) {
                                    	case 1 :
                                    	    // EsperEPL2Grammar.g:1499:51: '0' .. '9'
                                    	    {
                                    	    matchRange('0','9'); if (state.failed) return ;

                                    	    }
                                    	    break;

                                    	default :
                                    	    if ( cnt16 >= 1 ) break loop16;
                                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                                EarlyExitException eee =
                                                    new EarlyExitException(16, input);
                                                throw eee;
                                        }
                                        cnt16++;
                                    } while (true);


                                    }
                                    break;
                                case 3 :
                                    // EsperEPL2Grammar.g:1501:6: ( '0' .. '7' )+
                                    {
                                    // EsperEPL2Grammar.g:1501:6: ( '0' .. '7' )+
                                    int cnt17=0;
                                    loop17:
                                    do {
                                        int alt17=2;
                                        int LA17_0 = input.LA(1);

                                        if ( ((LA17_0>='0' && LA17_0<='7')) ) {
                                            alt17=1;
                                        }


                                        switch (alt17) {
                                    	case 1 :
                                    	    // EsperEPL2Grammar.g:1501:7: '0' .. '7'
                                    	    {
                                    	    matchRange('0','7'); if (state.failed) return ;

                                    	    }
                                    	    break;

                                    	default :
                                    	    if ( cnt17 >= 1 ) break loop17;
                                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                                EarlyExitException eee =
                                                    new EarlyExitException(17, input);
                                                throw eee;
                                        }
                                        cnt17++;
                                    } while (true);


                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // EsperEPL2Grammar.g:1503:5: ( '1' .. '9' ) ( '0' .. '9' )*
                            {
                            // EsperEPL2Grammar.g:1503:5: ( '1' .. '9' )
                            // EsperEPL2Grammar.g:1503:6: '1' .. '9'
                            {
                            matchRange('1','9'); if (state.failed) return ;

                            }

                            // EsperEPL2Grammar.g:1503:16: ( '0' .. '9' )*
                            loop19:
                            do {
                                int alt19=2;
                                int LA19_0 = input.LA(1);

                                if ( ((LA19_0>='0' && LA19_0<='9')) ) {
                                    alt19=1;
                                }


                                switch (alt19) {
                            	case 1 :
                            	    // EsperEPL2Grammar.g:1503:17: '0' .. '9'
                            	    {
                            	    matchRange('0','9'); if (state.failed) return ;

                            	    }
                            	    break;

                            	default :
                            	    break loop19;
                                }
                            } while (true);

                            if ( state.backtracking==0 ) {
                              isDecimal=true;
                            }

                            }
                            break;

                    }

                    // EsperEPL2Grammar.g:1505:3: ( ( 'l' ) | {...}? ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX ) )?
                    int alt26=3;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0=='l') ) {
                        alt26=1;
                    }
                    else if ( (LA26_0=='.'||(LA26_0>='d' && LA26_0<='f')) ) {
                        alt26=2;
                    }
                    switch (alt26) {
                        case 1 :
                            // EsperEPL2Grammar.g:1505:5: ( 'l' )
                            {
                            // EsperEPL2Grammar.g:1505:5: ( 'l' )
                            // EsperEPL2Grammar.g:1505:6: 'l'
                            {
                            match('l'); if (state.failed) return ;

                            }

                            if ( state.backtracking==0 ) {
                               _type = NUM_LONG; 
                            }

                            }
                            break;
                        case 2 :
                            // EsperEPL2Grammar.g:1508:5: {...}? ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX )
                            {
                            if ( !((isDecimal)) ) {
                                if (state.backtracking>0) {state.failed=true; return ;}
                                throw new FailedPredicateException(input, "NUM_INT", "isDecimal");
                            }
                            // EsperEPL2Grammar.g:1509:13: ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX )
                            int alt25=3;
                            switch ( input.LA(1) ) {
                            case '.':
                                {
                                alt25=1;
                                }
                                break;
                            case 'e':
                                {
                                alt25=2;
                                }
                                break;
                            case 'd':
                            case 'f':
                                {
                                alt25=3;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return ;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 25, 0, input);

                                throw nvae;
                            }

                            switch (alt25) {
                                case 1 :
                                    // EsperEPL2Grammar.g:1509:17: '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )?
                                    {
                                    match('.'); if (state.failed) return ;
                                    // EsperEPL2Grammar.g:1509:21: ( '0' .. '9' )*
                                    loop21:
                                    do {
                                        int alt21=2;
                                        int LA21_0 = input.LA(1);

                                        if ( ((LA21_0>='0' && LA21_0<='9')) ) {
                                            alt21=1;
                                        }


                                        switch (alt21) {
                                    	case 1 :
                                    	    // EsperEPL2Grammar.g:1509:22: '0' .. '9'
                                    	    {
                                    	    matchRange('0','9'); if (state.failed) return ;

                                    	    }
                                    	    break;

                                    	default :
                                    	    break loop21;
                                        }
                                    } while (true);

                                    // EsperEPL2Grammar.g:1509:33: ( EXPONENT )?
                                    int alt22=2;
                                    int LA22_0 = input.LA(1);

                                    if ( (LA22_0=='e') ) {
                                        alt22=1;
                                    }
                                    switch (alt22) {
                                        case 1 :
                                            // EsperEPL2Grammar.g:1509:34: EXPONENT
                                            {
                                            mEXPONENT(); if (state.failed) return ;

                                            }
                                            break;

                                    }

                                    // EsperEPL2Grammar.g:1509:45: (f2= FLOAT_SUFFIX )?
                                    int alt23=2;
                                    int LA23_0 = input.LA(1);

                                    if ( (LA23_0=='d'||LA23_0=='f') ) {
                                        alt23=1;
                                    }
                                    switch (alt23) {
                                        case 1 :
                                            // EsperEPL2Grammar.g:1509:46: f2= FLOAT_SUFFIX
                                            {
                                            int f2Start2063 = getCharIndex();
                                            mFLOAT_SUFFIX(); if (state.failed) return ;
                                            f2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, f2Start2063, getCharIndex()-1);
                                            if ( state.backtracking==0 ) {
                                              t=f2;
                                            }

                                            }
                                            break;

                                    }


                                    }
                                    break;
                                case 2 :
                                    // EsperEPL2Grammar.g:1510:17: EXPONENT (f3= FLOAT_SUFFIX )?
                                    {
                                    mEXPONENT(); if (state.failed) return ;
                                    // EsperEPL2Grammar.g:1510:26: (f3= FLOAT_SUFFIX )?
                                    int alt24=2;
                                    int LA24_0 = input.LA(1);

                                    if ( (LA24_0=='d'||LA24_0=='f') ) {
                                        alt24=1;
                                    }
                                    switch (alt24) {
                                        case 1 :
                                            // EsperEPL2Grammar.g:1510:27: f3= FLOAT_SUFFIX
                                            {
                                            int f3Start2090 = getCharIndex();
                                            mFLOAT_SUFFIX(); if (state.failed) return ;
                                            f3 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, f3Start2090, getCharIndex()-1);
                                            if ( state.backtracking==0 ) {
                                              t=f3;
                                            }

                                            }
                                            break;

                                    }


                                    }
                                    break;
                                case 3 :
                                    // EsperEPL2Grammar.g:1511:17: f4= FLOAT_SUFFIX
                                    {
                                    int f4Start2114 = getCharIndex();
                                    mFLOAT_SUFFIX(); if (state.failed) return ;
                                    f4 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, f4Start2114, getCharIndex()-1);
                                    if ( state.backtracking==0 ) {
                                      t=f4;
                                    }

                                    }
                                    break;

                            }

                            if ( state.backtracking==0 ) {

                              			if (t != null && t.getText().toUpperCase() .indexOf('F') >= 0) {
                                              _type = NUM_FLOAT;
                              			}
                                          else {
                              	           	_type = NUM_DOUBLE; // assume double
                              			}
                              			
                            }

                            }
                            break;

                    }


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NUM_INT"

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // EsperEPL2Grammar.g:1528:2: ( ( 'e' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // EsperEPL2Grammar.g:1528:4: ( 'e' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            // EsperEPL2Grammar.g:1528:4: ( 'e' )
            // EsperEPL2Grammar.g:1528:5: 'e'
            {
            match('e'); if (state.failed) return ;

            }

            // EsperEPL2Grammar.g:1528:10: ( '+' | '-' )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0=='+'||LA28_0=='-') ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // EsperEPL2Grammar.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();
                    state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // EsperEPL2Grammar.g:1528:21: ( '0' .. '9' )+
            int cnt29=0;
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( ((LA29_0>='0' && LA29_0<='9')) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // EsperEPL2Grammar.g:1528:22: '0' .. '9'
            	    {
            	    matchRange('0','9'); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt29 >= 1 ) break loop29;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(29, input);
                        throw eee;
                }
                cnt29++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "EXPONENT"

    // $ANTLR start "FLOAT_SUFFIX"
    public final void mFLOAT_SUFFIX() throws RecognitionException {
        try {
            // EsperEPL2Grammar.g:1534:2: ( 'f' | 'd' )
            // EsperEPL2Grammar.g:
            {
            if ( input.LA(1)=='d'||input.LA(1)=='f' ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "FLOAT_SUFFIX"

    public void mTokens() throws RecognitionException {
        // EsperEPL2Grammar.g:1:8: ( CREATE | WINDOW | IN_SET | BETWEEN | LIKE | REGEXP | ESCAPE | OR_EXPR | AND_EXPR | NOT_EXPR | EVERY_EXPR | WHERE | AS | SUM | AVG | MAX | MIN | COALESCE | MEDIAN | STDDEV | AVEDEV | COUNT | SELECT | CASE | ELSE | WHEN | THEN | END | FROM | OUTER | INNER | JOIN | LEFT | RIGHT | FULL | ON | IS | BY | GROUP | HAVING | DISTINCT | ALL | OUTPUT | EVENTS | FIRST | LAST | INSERT | INTO | ORDER | ASC | DESC | RSTREAM | ISTREAM | IRSTREAM | UNIDIRECTIONAL | RETAINUNION | RETAININTERSECTION | PATTERN | SQL | METADATASQL | PREVIOUS | PRIOR | EXISTS | WEEKDAY | LW | INSTANCEOF | CAST | CURRENT_TIMESTAMP | DELETE | SNAPSHOT | SET | VARIABLE | UNTIL | AT | TIMEPERIOD_DAY | TIMEPERIOD_DAYS | TIMEPERIOD_HOUR | TIMEPERIOD_HOURS | TIMEPERIOD_MINUTE | TIMEPERIOD_MINUTES | TIMEPERIOD_SEC | TIMEPERIOD_SECOND | TIMEPERIOD_SECONDS | TIMEPERIOD_MILLISEC | TIMEPERIOD_MILLISECOND | TIMEPERIOD_MILLISECONDS | BOOLEAN_TRUE | BOOLEAN_FALSE | VALUE_NULL | ROW_LIMIT_EXPR | OFFSET | FOLLOWED_BY | EQUALS | SQL_NE | QUESTION | LPAREN | RPAREN | LBRACK | RBRACK | LCURLY | RCURLY | COLON | COMMA | EQUAL | LNOT | BNOT | NOT_EQUAL | DIV | DIV_ASSIGN | PLUS | PLUS_ASSIGN | INC | MINUS | MINUS_ASSIGN | DEC | STAR | STAR_ASSIGN | MOD | MOD_ASSIGN | SR | SR_ASSIGN | BSR | BSR_ASSIGN | GE | GT | SL | SL_ASSIGN | LE | LT | BXOR | BXOR_ASSIGN | BOR | BOR_ASSIGN | LOR | BAND | BAND_ASSIGN | LAND | SEMI | DOT | NUM_LONG | NUM_DOUBLE | NUM_FLOAT | ESCAPECHAR | EMAILAT | WS | SL_COMMENT | ML_COMMENT | QUOTED_STRING_LITERAL | STRING_LITERAL | IDENT | NUM_INT )
        int alt30=151;
        alt30 = dfa30.predict(input);
        switch (alt30) {
            case 1 :
                // EsperEPL2Grammar.g:1:10: CREATE
                {
                mCREATE(); if (state.failed) return ;

                }
                break;
            case 2 :
                // EsperEPL2Grammar.g:1:17: WINDOW
                {
                mWINDOW(); if (state.failed) return ;

                }
                break;
            case 3 :
                // EsperEPL2Grammar.g:1:24: IN_SET
                {
                mIN_SET(); if (state.failed) return ;

                }
                break;
            case 4 :
                // EsperEPL2Grammar.g:1:31: BETWEEN
                {
                mBETWEEN(); if (state.failed) return ;

                }
                break;
            case 5 :
                // EsperEPL2Grammar.g:1:39: LIKE
                {
                mLIKE(); if (state.failed) return ;

                }
                break;
            case 6 :
                // EsperEPL2Grammar.g:1:44: REGEXP
                {
                mREGEXP(); if (state.failed) return ;

                }
                break;
            case 7 :
                // EsperEPL2Grammar.g:1:51: ESCAPE
                {
                mESCAPE(); if (state.failed) return ;

                }
                break;
            case 8 :
                // EsperEPL2Grammar.g:1:58: OR_EXPR
                {
                mOR_EXPR(); if (state.failed) return ;

                }
                break;
            case 9 :
                // EsperEPL2Grammar.g:1:66: AND_EXPR
                {
                mAND_EXPR(); if (state.failed) return ;

                }
                break;
            case 10 :
                // EsperEPL2Grammar.g:1:75: NOT_EXPR
                {
                mNOT_EXPR(); if (state.failed) return ;

                }
                break;
            case 11 :
                // EsperEPL2Grammar.g:1:84: EVERY_EXPR
                {
                mEVERY_EXPR(); if (state.failed) return ;

                }
                break;
            case 12 :
                // EsperEPL2Grammar.g:1:95: WHERE
                {
                mWHERE(); if (state.failed) return ;

                }
                break;
            case 13 :
                // EsperEPL2Grammar.g:1:101: AS
                {
                mAS(); if (state.failed) return ;

                }
                break;
            case 14 :
                // EsperEPL2Grammar.g:1:104: SUM
                {
                mSUM(); if (state.failed) return ;

                }
                break;
            case 15 :
                // EsperEPL2Grammar.g:1:108: AVG
                {
                mAVG(); if (state.failed) return ;

                }
                break;
            case 16 :
                // EsperEPL2Grammar.g:1:112: MAX
                {
                mMAX(); if (state.failed) return ;

                }
                break;
            case 17 :
                // EsperEPL2Grammar.g:1:116: MIN
                {
                mMIN(); if (state.failed) return ;

                }
                break;
            case 18 :
                // EsperEPL2Grammar.g:1:120: COALESCE
                {
                mCOALESCE(); if (state.failed) return ;

                }
                break;
            case 19 :
                // EsperEPL2Grammar.g:1:129: MEDIAN
                {
                mMEDIAN(); if (state.failed) return ;

                }
                break;
            case 20 :
                // EsperEPL2Grammar.g:1:136: STDDEV
                {
                mSTDDEV(); if (state.failed) return ;

                }
                break;
            case 21 :
                // EsperEPL2Grammar.g:1:143: AVEDEV
                {
                mAVEDEV(); if (state.failed) return ;

                }
                break;
            case 22 :
                // EsperEPL2Grammar.g:1:150: COUNT
                {
                mCOUNT(); if (state.failed) return ;

                }
                break;
            case 23 :
                // EsperEPL2Grammar.g:1:156: SELECT
                {
                mSELECT(); if (state.failed) return ;

                }
                break;
            case 24 :
                // EsperEPL2Grammar.g:1:163: CASE
                {
                mCASE(); if (state.failed) return ;

                }
                break;
            case 25 :
                // EsperEPL2Grammar.g:1:168: ELSE
                {
                mELSE(); if (state.failed) return ;

                }
                break;
            case 26 :
                // EsperEPL2Grammar.g:1:173: WHEN
                {
                mWHEN(); if (state.failed) return ;

                }
                break;
            case 27 :
                // EsperEPL2Grammar.g:1:178: THEN
                {
                mTHEN(); if (state.failed) return ;

                }
                break;
            case 28 :
                // EsperEPL2Grammar.g:1:183: END
                {
                mEND(); if (state.failed) return ;

                }
                break;
            case 29 :
                // EsperEPL2Grammar.g:1:187: FROM
                {
                mFROM(); if (state.failed) return ;

                }
                break;
            case 30 :
                // EsperEPL2Grammar.g:1:192: OUTER
                {
                mOUTER(); if (state.failed) return ;

                }
                break;
            case 31 :
                // EsperEPL2Grammar.g:1:198: INNER
                {
                mINNER(); if (state.failed) return ;

                }
                break;
            case 32 :
                // EsperEPL2Grammar.g:1:204: JOIN
                {
                mJOIN(); if (state.failed) return ;

                }
                break;
            case 33 :
                // EsperEPL2Grammar.g:1:209: LEFT
                {
                mLEFT(); if (state.failed) return ;

                }
                break;
            case 34 :
                // EsperEPL2Grammar.g:1:214: RIGHT
                {
                mRIGHT(); if (state.failed) return ;

                }
                break;
            case 35 :
                // EsperEPL2Grammar.g:1:220: FULL
                {
                mFULL(); if (state.failed) return ;

                }
                break;
            case 36 :
                // EsperEPL2Grammar.g:1:225: ON
                {
                mON(); if (state.failed) return ;

                }
                break;
            case 37 :
                // EsperEPL2Grammar.g:1:228: IS
                {
                mIS(); if (state.failed) return ;

                }
                break;
            case 38 :
                // EsperEPL2Grammar.g:1:231: BY
                {
                mBY(); if (state.failed) return ;

                }
                break;
            case 39 :
                // EsperEPL2Grammar.g:1:234: GROUP
                {
                mGROUP(); if (state.failed) return ;

                }
                break;
            case 40 :
                // EsperEPL2Grammar.g:1:240: HAVING
                {
                mHAVING(); if (state.failed) return ;

                }
                break;
            case 41 :
                // EsperEPL2Grammar.g:1:247: DISTINCT
                {
                mDISTINCT(); if (state.failed) return ;

                }
                break;
            case 42 :
                // EsperEPL2Grammar.g:1:256: ALL
                {
                mALL(); if (state.failed) return ;

                }
                break;
            case 43 :
                // EsperEPL2Grammar.g:1:260: OUTPUT
                {
                mOUTPUT(); if (state.failed) return ;

                }
                break;
            case 44 :
                // EsperEPL2Grammar.g:1:267: EVENTS
                {
                mEVENTS(); if (state.failed) return ;

                }
                break;
            case 45 :
                // EsperEPL2Grammar.g:1:274: FIRST
                {
                mFIRST(); if (state.failed) return ;

                }
                break;
            case 46 :
                // EsperEPL2Grammar.g:1:280: LAST
                {
                mLAST(); if (state.failed) return ;

                }
                break;
            case 47 :
                // EsperEPL2Grammar.g:1:285: INSERT
                {
                mINSERT(); if (state.failed) return ;

                }
                break;
            case 48 :
                // EsperEPL2Grammar.g:1:292: INTO
                {
                mINTO(); if (state.failed) return ;

                }
                break;
            case 49 :
                // EsperEPL2Grammar.g:1:297: ORDER
                {
                mORDER(); if (state.failed) return ;

                }
                break;
            case 50 :
                // EsperEPL2Grammar.g:1:303: ASC
                {
                mASC(); if (state.failed) return ;

                }
                break;
            case 51 :
                // EsperEPL2Grammar.g:1:307: DESC
                {
                mDESC(); if (state.failed) return ;

                }
                break;
            case 52 :
                // EsperEPL2Grammar.g:1:312: RSTREAM
                {
                mRSTREAM(); if (state.failed) return ;

                }
                break;
            case 53 :
                // EsperEPL2Grammar.g:1:320: ISTREAM
                {
                mISTREAM(); if (state.failed) return ;

                }
                break;
            case 54 :
                // EsperEPL2Grammar.g:1:328: IRSTREAM
                {
                mIRSTREAM(); if (state.failed) return ;

                }
                break;
            case 55 :
                // EsperEPL2Grammar.g:1:337: UNIDIRECTIONAL
                {
                mUNIDIRECTIONAL(); if (state.failed) return ;

                }
                break;
            case 56 :
                // EsperEPL2Grammar.g:1:352: RETAINUNION
                {
                mRETAINUNION(); if (state.failed) return ;

                }
                break;
            case 57 :
                // EsperEPL2Grammar.g:1:364: RETAININTERSECTION
                {
                mRETAININTERSECTION(); if (state.failed) return ;

                }
                break;
            case 58 :
                // EsperEPL2Grammar.g:1:383: PATTERN
                {
                mPATTERN(); if (state.failed) return ;

                }
                break;
            case 59 :
                // EsperEPL2Grammar.g:1:391: SQL
                {
                mSQL(); if (state.failed) return ;

                }
                break;
            case 60 :
                // EsperEPL2Grammar.g:1:395: METADATASQL
                {
                mMETADATASQL(); if (state.failed) return ;

                }
                break;
            case 61 :
                // EsperEPL2Grammar.g:1:407: PREVIOUS
                {
                mPREVIOUS(); if (state.failed) return ;

                }
                break;
            case 62 :
                // EsperEPL2Grammar.g:1:416: PRIOR
                {
                mPRIOR(); if (state.failed) return ;

                }
                break;
            case 63 :
                // EsperEPL2Grammar.g:1:422: EXISTS
                {
                mEXISTS(); if (state.failed) return ;

                }
                break;
            case 64 :
                // EsperEPL2Grammar.g:1:429: WEEKDAY
                {
                mWEEKDAY(); if (state.failed) return ;

                }
                break;
            case 65 :
                // EsperEPL2Grammar.g:1:437: LW
                {
                mLW(); if (state.failed) return ;

                }
                break;
            case 66 :
                // EsperEPL2Grammar.g:1:440: INSTANCEOF
                {
                mINSTANCEOF(); if (state.failed) return ;

                }
                break;
            case 67 :
                // EsperEPL2Grammar.g:1:451: CAST
                {
                mCAST(); if (state.failed) return ;

                }
                break;
            case 68 :
                // EsperEPL2Grammar.g:1:456: CURRENT_TIMESTAMP
                {
                mCURRENT_TIMESTAMP(); if (state.failed) return ;

                }
                break;
            case 69 :
                // EsperEPL2Grammar.g:1:474: DELETE
                {
                mDELETE(); if (state.failed) return ;

                }
                break;
            case 70 :
                // EsperEPL2Grammar.g:1:481: SNAPSHOT
                {
                mSNAPSHOT(); if (state.failed) return ;

                }
                break;
            case 71 :
                // EsperEPL2Grammar.g:1:490: SET
                {
                mSET(); if (state.failed) return ;

                }
                break;
            case 72 :
                // EsperEPL2Grammar.g:1:494: VARIABLE
                {
                mVARIABLE(); if (state.failed) return ;

                }
                break;
            case 73 :
                // EsperEPL2Grammar.g:1:503: UNTIL
                {
                mUNTIL(); if (state.failed) return ;

                }
                break;
            case 74 :
                // EsperEPL2Grammar.g:1:509: AT
                {
                mAT(); if (state.failed) return ;

                }
                break;
            case 75 :
                // EsperEPL2Grammar.g:1:512: TIMEPERIOD_DAY
                {
                mTIMEPERIOD_DAY(); if (state.failed) return ;

                }
                break;
            case 76 :
                // EsperEPL2Grammar.g:1:527: TIMEPERIOD_DAYS
                {
                mTIMEPERIOD_DAYS(); if (state.failed) return ;

                }
                break;
            case 77 :
                // EsperEPL2Grammar.g:1:543: TIMEPERIOD_HOUR
                {
                mTIMEPERIOD_HOUR(); if (state.failed) return ;

                }
                break;
            case 78 :
                // EsperEPL2Grammar.g:1:559: TIMEPERIOD_HOURS
                {
                mTIMEPERIOD_HOURS(); if (state.failed) return ;

                }
                break;
            case 79 :
                // EsperEPL2Grammar.g:1:576: TIMEPERIOD_MINUTE
                {
                mTIMEPERIOD_MINUTE(); if (state.failed) return ;

                }
                break;
            case 80 :
                // EsperEPL2Grammar.g:1:594: TIMEPERIOD_MINUTES
                {
                mTIMEPERIOD_MINUTES(); if (state.failed) return ;

                }
                break;
            case 81 :
                // EsperEPL2Grammar.g:1:613: TIMEPERIOD_SEC
                {
                mTIMEPERIOD_SEC(); if (state.failed) return ;

                }
                break;
            case 82 :
                // EsperEPL2Grammar.g:1:628: TIMEPERIOD_SECOND
                {
                mTIMEPERIOD_SECOND(); if (state.failed) return ;

                }
                break;
            case 83 :
                // EsperEPL2Grammar.g:1:646: TIMEPERIOD_SECONDS
                {
                mTIMEPERIOD_SECONDS(); if (state.failed) return ;

                }
                break;
            case 84 :
                // EsperEPL2Grammar.g:1:665: TIMEPERIOD_MILLISEC
                {
                mTIMEPERIOD_MILLISEC(); if (state.failed) return ;

                }
                break;
            case 85 :
                // EsperEPL2Grammar.g:1:685: TIMEPERIOD_MILLISECOND
                {
                mTIMEPERIOD_MILLISECOND(); if (state.failed) return ;

                }
                break;
            case 86 :
                // EsperEPL2Grammar.g:1:708: TIMEPERIOD_MILLISECONDS
                {
                mTIMEPERIOD_MILLISECONDS(); if (state.failed) return ;

                }
                break;
            case 87 :
                // EsperEPL2Grammar.g:1:732: BOOLEAN_TRUE
                {
                mBOOLEAN_TRUE(); if (state.failed) return ;

                }
                break;
            case 88 :
                // EsperEPL2Grammar.g:1:745: BOOLEAN_FALSE
                {
                mBOOLEAN_FALSE(); if (state.failed) return ;

                }
                break;
            case 89 :
                // EsperEPL2Grammar.g:1:759: VALUE_NULL
                {
                mVALUE_NULL(); if (state.failed) return ;

                }
                break;
            case 90 :
                // EsperEPL2Grammar.g:1:770: ROW_LIMIT_EXPR
                {
                mROW_LIMIT_EXPR(); if (state.failed) return ;

                }
                break;
            case 91 :
                // EsperEPL2Grammar.g:1:785: OFFSET
                {
                mOFFSET(); if (state.failed) return ;

                }
                break;
            case 92 :
                // EsperEPL2Grammar.g:1:792: FOLLOWED_BY
                {
                mFOLLOWED_BY(); if (state.failed) return ;

                }
                break;
            case 93 :
                // EsperEPL2Grammar.g:1:804: EQUALS
                {
                mEQUALS(); if (state.failed) return ;

                }
                break;
            case 94 :
                // EsperEPL2Grammar.g:1:811: SQL_NE
                {
                mSQL_NE(); if (state.failed) return ;

                }
                break;
            case 95 :
                // EsperEPL2Grammar.g:1:818: QUESTION
                {
                mQUESTION(); if (state.failed) return ;

                }
                break;
            case 96 :
                // EsperEPL2Grammar.g:1:827: LPAREN
                {
                mLPAREN(); if (state.failed) return ;

                }
                break;
            case 97 :
                // EsperEPL2Grammar.g:1:834: RPAREN
                {
                mRPAREN(); if (state.failed) return ;

                }
                break;
            case 98 :
                // EsperEPL2Grammar.g:1:841: LBRACK
                {
                mLBRACK(); if (state.failed) return ;

                }
                break;
            case 99 :
                // EsperEPL2Grammar.g:1:848: RBRACK
                {
                mRBRACK(); if (state.failed) return ;

                }
                break;
            case 100 :
                // EsperEPL2Grammar.g:1:855: LCURLY
                {
                mLCURLY(); if (state.failed) return ;

                }
                break;
            case 101 :
                // EsperEPL2Grammar.g:1:862: RCURLY
                {
                mRCURLY(); if (state.failed) return ;

                }
                break;
            case 102 :
                // EsperEPL2Grammar.g:1:869: COLON
                {
                mCOLON(); if (state.failed) return ;

                }
                break;
            case 103 :
                // EsperEPL2Grammar.g:1:875: COMMA
                {
                mCOMMA(); if (state.failed) return ;

                }
                break;
            case 104 :
                // EsperEPL2Grammar.g:1:881: EQUAL
                {
                mEQUAL(); if (state.failed) return ;

                }
                break;
            case 105 :
                // EsperEPL2Grammar.g:1:887: LNOT
                {
                mLNOT(); if (state.failed) return ;

                }
                break;
            case 106 :
                // EsperEPL2Grammar.g:1:892: BNOT
                {
                mBNOT(); if (state.failed) return ;

                }
                break;
            case 107 :
                // EsperEPL2Grammar.g:1:897: NOT_EQUAL
                {
                mNOT_EQUAL(); if (state.failed) return ;

                }
                break;
            case 108 :
                // EsperEPL2Grammar.g:1:907: DIV
                {
                mDIV(); if (state.failed) return ;

                }
                break;
            case 109 :
                // EsperEPL2Grammar.g:1:911: DIV_ASSIGN
                {
                mDIV_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 110 :
                // EsperEPL2Grammar.g:1:922: PLUS
                {
                mPLUS(); if (state.failed) return ;

                }
                break;
            case 111 :
                // EsperEPL2Grammar.g:1:927: PLUS_ASSIGN
                {
                mPLUS_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 112 :
                // EsperEPL2Grammar.g:1:939: INC
                {
                mINC(); if (state.failed) return ;

                }
                break;
            case 113 :
                // EsperEPL2Grammar.g:1:943: MINUS
                {
                mMINUS(); if (state.failed) return ;

                }
                break;
            case 114 :
                // EsperEPL2Grammar.g:1:949: MINUS_ASSIGN
                {
                mMINUS_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 115 :
                // EsperEPL2Grammar.g:1:962: DEC
                {
                mDEC(); if (state.failed) return ;

                }
                break;
            case 116 :
                // EsperEPL2Grammar.g:1:966: STAR
                {
                mSTAR(); if (state.failed) return ;

                }
                break;
            case 117 :
                // EsperEPL2Grammar.g:1:971: STAR_ASSIGN
                {
                mSTAR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 118 :
                // EsperEPL2Grammar.g:1:983: MOD
                {
                mMOD(); if (state.failed) return ;

                }
                break;
            case 119 :
                // EsperEPL2Grammar.g:1:987: MOD_ASSIGN
                {
                mMOD_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 120 :
                // EsperEPL2Grammar.g:1:998: SR
                {
                mSR(); if (state.failed) return ;

                }
                break;
            case 121 :
                // EsperEPL2Grammar.g:1:1001: SR_ASSIGN
                {
                mSR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 122 :
                // EsperEPL2Grammar.g:1:1011: BSR
                {
                mBSR(); if (state.failed) return ;

                }
                break;
            case 123 :
                // EsperEPL2Grammar.g:1:1015: BSR_ASSIGN
                {
                mBSR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 124 :
                // EsperEPL2Grammar.g:1:1026: GE
                {
                mGE(); if (state.failed) return ;

                }
                break;
            case 125 :
                // EsperEPL2Grammar.g:1:1029: GT
                {
                mGT(); if (state.failed) return ;

                }
                break;
            case 126 :
                // EsperEPL2Grammar.g:1:1032: SL
                {
                mSL(); if (state.failed) return ;

                }
                break;
            case 127 :
                // EsperEPL2Grammar.g:1:1035: SL_ASSIGN
                {
                mSL_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 128 :
                // EsperEPL2Grammar.g:1:1045: LE
                {
                mLE(); if (state.failed) return ;

                }
                break;
            case 129 :
                // EsperEPL2Grammar.g:1:1048: LT
                {
                mLT(); if (state.failed) return ;

                }
                break;
            case 130 :
                // EsperEPL2Grammar.g:1:1051: BXOR
                {
                mBXOR(); if (state.failed) return ;

                }
                break;
            case 131 :
                // EsperEPL2Grammar.g:1:1056: BXOR_ASSIGN
                {
                mBXOR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 132 :
                // EsperEPL2Grammar.g:1:1068: BOR
                {
                mBOR(); if (state.failed) return ;

                }
                break;
            case 133 :
                // EsperEPL2Grammar.g:1:1072: BOR_ASSIGN
                {
                mBOR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 134 :
                // EsperEPL2Grammar.g:1:1083: LOR
                {
                mLOR(); if (state.failed) return ;

                }
                break;
            case 135 :
                // EsperEPL2Grammar.g:1:1087: BAND
                {
                mBAND(); if (state.failed) return ;

                }
                break;
            case 136 :
                // EsperEPL2Grammar.g:1:1092: BAND_ASSIGN
                {
                mBAND_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 137 :
                // EsperEPL2Grammar.g:1:1104: LAND
                {
                mLAND(); if (state.failed) return ;

                }
                break;
            case 138 :
                // EsperEPL2Grammar.g:1:1109: SEMI
                {
                mSEMI(); if (state.failed) return ;

                }
                break;
            case 139 :
                // EsperEPL2Grammar.g:1:1114: DOT
                {
                mDOT(); if (state.failed) return ;

                }
                break;
            case 140 :
                // EsperEPL2Grammar.g:1:1118: NUM_LONG
                {
                mNUM_LONG(); if (state.failed) return ;

                }
                break;
            case 141 :
                // EsperEPL2Grammar.g:1:1127: NUM_DOUBLE
                {
                mNUM_DOUBLE(); if (state.failed) return ;

                }
                break;
            case 142 :
                // EsperEPL2Grammar.g:1:1138: NUM_FLOAT
                {
                mNUM_FLOAT(); if (state.failed) return ;

                }
                break;
            case 143 :
                // EsperEPL2Grammar.g:1:1148: ESCAPECHAR
                {
                mESCAPECHAR(); if (state.failed) return ;

                }
                break;
            case 144 :
                // EsperEPL2Grammar.g:1:1159: EMAILAT
                {
                mEMAILAT(); if (state.failed) return ;

                }
                break;
            case 145 :
                // EsperEPL2Grammar.g:1:1167: WS
                {
                mWS(); if (state.failed) return ;

                }
                break;
            case 146 :
                // EsperEPL2Grammar.g:1:1170: SL_COMMENT
                {
                mSL_COMMENT(); if (state.failed) return ;

                }
                break;
            case 147 :
                // EsperEPL2Grammar.g:1:1181: ML_COMMENT
                {
                mML_COMMENT(); if (state.failed) return ;

                }
                break;
            case 148 :
                // EsperEPL2Grammar.g:1:1192: QUOTED_STRING_LITERAL
                {
                mQUOTED_STRING_LITERAL(); if (state.failed) return ;

                }
                break;
            case 149 :
                // EsperEPL2Grammar.g:1:1214: STRING_LITERAL
                {
                mSTRING_LITERAL(); if (state.failed) return ;

                }
                break;
            case 150 :
                // EsperEPL2Grammar.g:1:1229: IDENT
                {
                mIDENT(); if (state.failed) return ;

                }
                break;
            case 151 :
                // EsperEPL2Grammar.g:1:1235: NUM_INT
                {
                mNUM_INT(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred1_EsperEPL2Grammar
    public final void synpred1_EsperEPL2Grammar_fragment() throws RecognitionException {   
        // EsperEPL2Grammar.g:1499:5: ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )
        // EsperEPL2Grammar.g:1499:6: ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX )
        {
        // EsperEPL2Grammar.g:1499:6: ( '0' .. '9' )+
        int cnt31=0;
        loop31:
        do {
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( ((LA31_0>='0' && LA31_0<='9')) ) {
                alt31=1;
            }


            switch (alt31) {
        	case 1 :
        	    // EsperEPL2Grammar.g:1499:7: '0' .. '9'
        	    {
        	    matchRange('0','9'); if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    if ( cnt31 >= 1 ) break loop31;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(31, input);
                    throw eee;
            }
            cnt31++;
        } while (true);

        // EsperEPL2Grammar.g:1499:18: ( '.' | EXPONENT | FLOAT_SUFFIX )
        int alt32=3;
        switch ( input.LA(1) ) {
        case '.':
            {
            alt32=1;
            }
            break;
        case 'e':
            {
            alt32=2;
            }
            break;
        case 'd':
        case 'f':
            {
            alt32=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 32, 0, input);

            throw nvae;
        }

        switch (alt32) {
            case 1 :
                // EsperEPL2Grammar.g:1499:19: '.'
                {
                match('.'); if (state.failed) return ;

                }
                break;
            case 2 :
                // EsperEPL2Grammar.g:1499:23: EXPONENT
                {
                mEXPONENT(); if (state.failed) return ;

                }
                break;
            case 3 :
                // EsperEPL2Grammar.g:1499:32: FLOAT_SUFFIX
                {
                mFLOAT_SUFFIX(); if (state.failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred1_EsperEPL2Grammar

    public final boolean synpred1_EsperEPL2Grammar() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_EsperEPL2Grammar_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA30 dfa30 = new DFA30(this);
    static final String DFA30_eotS =
        "\1\uffff\25\66\1\167\1\171\1\175\11\uffff\1\177\1\uffff\1\u0083"+
        "\1\u0086\1\u0088\1\u008a\1\u008d\1\u008f\1\u0092\1\u0095\1\uffff"+
        "\1\u0096\12\uffff\7\66\1\u00a2\1\u00a4\2\66\1\u00a7\13\66\1\u00b6"+
        "\1\66\1\u00b8\2\66\1\u00bc\2\66\1\u00c0\34\66\7\uffff\1\u00e5\17"+
        "\uffff\1\u00e8\13\uffff\13\66\1\uffff\1\66\1\uffff\2\66\1\uffff"+
        "\13\66\1\u0106\2\66\1\uffff\1\66\1\uffff\1\66\1\u010c\1\u010d\1"+
        "\uffff\1\u010e\1\66\1\u0110\1\uffff\1\u0111\1\66\1\u0113\2\66\1"+
        "\u0116\1\u0118\1\u0119\1\66\1\u011b\1\u011d\21\66\1\u0130\6\66\3"+
        "\uffff\1\u0138\1\uffff\3\66\1\u013c\1\u013d\3\66\1\u0141\4\66\1"+
        "\u0146\3\66\1\u014a\1\66\1\u014c\1\u014e\7\66\1\u0156\1\uffff\5"+
        "\66\3\uffff\1\66\2\uffff\1\u015d\1\uffff\2\66\1\uffff\1\66\2\uffff"+
        "\1\66\1\uffff\1\66\1\uffff\3\66\1\u0166\1\u0167\1\u0168\1\u0169"+
        "\1\u016a\2\66\1\u016d\2\66\1\u0171\1\66\1\u0173\1\66\1\u0175\1\uffff"+
        "\3\66\1\u0179\2\66\2\uffff\2\66\1\u017e\2\uffff\2\66\1\u0181\1\uffff"+
        "\1\66\1\u0183\2\66\1\uffff\3\66\1\uffff\1\u0189\1\uffff\1\66\1\uffff"+
        "\2\66\1\u018d\2\66\1\u0190\1\66\1\uffff\1\66\1\u0193\1\u0194\3\66"+
        "\1\uffff\10\66\5\uffff\1\u01a0\1\u01a1\1\uffff\1\u01a2\1\66\1\u01a4"+
        "\1\uffff\1\66\1\uffff\1\66\1\uffff\1\66\1\u01a8\1\66\1\uffff\1\u01aa"+
        "\1\66\1\u01ac\1\66\1\uffff\1\66\1\u01af\1\uffff\1\66\1\uffff\1\u01b1"+
        "\4\66\1\uffff\1\66\1\u01b7\1\66\1\uffff\1\66\1\u01ba\1\uffff\1\u01bb"+
        "\1\u01bc\2\uffff\1\u01bd\1\u01be\1\u01bf\1\u01c0\1\u01c1\1\u01c3"+
        "\1\66\1\u01c6\1\66\1\u01c8\1\66\3\uffff\1\u01ca\1\uffff\1\66\1\u01cc"+
        "\1\66\1\uffff\1\66\1\uffff\1\66\1\uffff\2\66\1\uffff\1\u01d2\1\uffff"+
        "\1\66\1\u01d4\1\66\1\u01d6\1\66\2\uffff\1\u01da\10\uffff\1\u01db"+
        "\1\uffff\1\66\1\u01dd\1\uffff\1\66\1\uffff\1\66\1\uffff\1\66\1\uffff"+
        "\1\66\1\u01e2\1\66\1\u01e4\1\66\1\uffff\1\66\1\uffff\1\u01e7\1\uffff"+
        "\1\66\4\uffff\1\u01e9\1\uffff\2\66\1\u01ec\1\66\1\uffff\1\u01ee"+
        "\1\uffff\2\66\1\uffff\1\66\1\uffff\2\66\1\uffff\1\66\1\uffff\1\66"+
        "\1\u01f6\5\66\1\uffff\1\u01fc\1\u01fe\1\u01ff\2\66\1\uffff\1\u0202"+
        "\2\uffff\2\66\1\uffff\2\66\1\u0207\1\66\1\uffff\1\66\1\u020a\1\uffff";
    static final String DFA30_eofS =
        "\u020b\uffff";
    static final String DFA30_minS =
        "\1\11\1\141\1\145\1\156\1\145\1\141\1\145\1\154\1\146\1\154\1\157"+
        "\1\145\1\141\1\150\1\141\1\157\1\162\2\141\1\156\2\141\1\55\1\75"+
        "\1\74\11\uffff\1\75\1\uffff\1\52\1\53\5\75\1\46\1\uffff\1\60\12"+
        "\uffff\1\145\1\141\1\163\1\162\1\156\2\145\2\44\1\163\1\164\1\44"+
        "\1\153\1\146\1\163\2\147\1\164\1\143\1\145\1\163\1\144\1\151\1\44"+
        "\1\164\1\44\1\146\1\144\1\44\1\145\1\154\1\44\1\164\1\154\1\155"+
        "\1\144\1\143\1\154\1\141\1\170\1\154\1\144\2\145\1\165\1\157\1\154"+
        "\1\162\1\154\1\151\1\157\1\166\1\165\1\163\1\154\1\171\1\151\1\164"+
        "\1\145\1\162\7\uffff\1\75\17\uffff\1\75\13\uffff\1\141\1\154\1\156"+
        "\1\145\1\162\1\144\1\156\1\153\2\145\1\157\1\uffff\1\162\1\uffff"+
        "\1\164\1\167\1\uffff\1\145\1\151\2\164\1\145\1\141\1\150\1\162\1"+
        "\141\1\156\1\145\1\44\1\163\1\145\1\uffff\1\145\1\uffff\1\163\2"+
        "\44\1\uffff\1\44\1\144\1\44\1\uffff\1\44\1\154\1\44\1\144\1\145"+
        "\3\44\1\160\2\44\1\154\1\151\1\141\1\143\1\156\1\145\1\155\1\154"+
        "\2\163\1\156\1\165\1\151\1\162\1\164\1\143\1\145\1\44\1\144\1\151"+
        "\1\164\1\166\1\157\1\151\3\uffff\1\75\1\uffff\1\164\1\145\1\164"+
        "\2\44\1\145\1\157\1\145\1\44\1\144\2\162\1\141\1\44\1\145\1\162"+
        "\1\145\1\44\1\164\2\44\1\170\1\151\1\164\1\145\1\160\1\171\1\164"+
        "\1\44\1\uffff\1\164\2\162\1\165\1\145\3\uffff\1\145\2\uffff\1\44"+
        "\1\uffff\1\145\1\143\1\uffff\1\156\2\uffff\1\163\1\uffff\1\164\1"+
        "\uffff\1\151\1\141\1\144\5\44\1\164\1\145\1\44\1\160\1\156\1\44"+
        "\1\151\1\44\1\164\1\44\1\uffff\1\151\1\154\1\145\1\44\1\162\1\141"+
        "\2\uffff\1\145\1\163\1\44\2\uffff\1\156\1\167\1\44\1\uffff\1\141"+
        "\1\44\1\164\1\156\1\uffff\1\141\2\145\1\uffff\1\44\1\uffff\1\145"+
        "\1\uffff\1\160\1\156\1\44\1\141\1\145\1\44\1\163\1\uffff\1\163\2"+
        "\44\2\164\1\166\1\uffff\1\166\1\164\1\144\1\150\1\145\1\163\1\156"+
        "\1\141\5\uffff\2\44\1\uffff\1\44\1\147\1\44\1\uffff\1\156\1\uffff"+
        "\1\145\1\uffff\1\162\1\44\1\162\1\uffff\1\44\1\142\1\44\1\143\1"+
        "\uffff\1\164\1\44\1\uffff\1\171\1\uffff\1\44\1\143\1\155\1\141\1"+
        "\156\1\uffff\1\145\1\44\1\55\1\uffff\1\155\1\44\1\uffff\2\44\2\uffff"+
        "\6\44\1\157\1\44\1\145\1\44\1\164\3\uffff\1\44\1\uffff\1\143\1\44"+
        "\1\145\1\uffff\1\156\1\uffff\1\154\1\uffff\1\145\1\137\1\uffff\1"+
        "\44\1\uffff\1\145\1\44\1\155\1\44\1\153\1\uffff\1\151\1\44\10\uffff"+
        "\1\44\1\uffff\1\164\1\44\1\uffff\1\143\1\uffff\1\141\1\uffff\1\164"+
        "\1\uffff\1\143\1\44\1\145\1\44\1\164\1\uffff\1\157\1\uffff\1\44"+
        "\1\uffff\1\144\4\uffff\1\44\1\uffff\1\157\1\163\1\44\1\164\1\uffff"+
        "\1\44\1\uffff\1\151\1\146\1\uffff\1\141\1\uffff\1\156\1\161\1\uffff"+
        "\1\151\1\uffff\1\155\1\44\1\171\1\144\1\154\1\157\1\145\1\uffff"+
        "\3\44\1\156\1\163\1\uffff\1\44\2\uffff\1\141\1\164\1\uffff\1\154"+
        "\1\141\1\44\1\155\1\uffff\1\160\1\44\1\uffff";
    static final String DFA30_maxS =
        "\1\u18ff\1\165\1\151\1\163\1\171\1\151\1\163\1\170\1\165\1\166"+
        "\2\165\1\163\1\162\1\165\1\157\1\162\1\157\1\151\1\156\1\162\1\141"+
        "\1\76\1\75\1\76\11\uffff\1\75\1\uffff\4\75\1\76\1\75\1\174\1\75"+
        "\1\uffff\1\71\12\uffff\1\145\1\165\1\163\1\162\1\156\2\145\2\172"+
        "\1\163\1\164\1\172\1\155\1\146\1\163\1\164\1\147\1\164\1\143\1\145"+
        "\1\163\1\144\1\151\1\172\1\164\1\172\1\146\1\144\1\172\1\147\1\154"+
        "\1\172\1\164\1\154\1\155\1\144\1\164\1\154\1\141\1\170\1\156\1\164"+
        "\2\145\1\165\1\157\1\154\1\162\1\154\1\151\1\157\1\166\1\165\2\163"+
        "\1\171\2\164\1\151\1\162\7\uffff\1\75\17\uffff\1\76\13\uffff\1\141"+
        "\1\154\1\156\1\164\1\162\1\144\1\162\1\153\1\145\1\164\1\157\1\uffff"+
        "\1\162\1\uffff\1\164\1\167\1\uffff\1\145\1\151\2\164\1\145\1\141"+
        "\1\150\1\162\1\141\1\162\1\145\1\172\1\163\1\145\1\uffff\1\160\1"+
        "\uffff\1\163\2\172\1\uffff\1\172\1\144\1\172\1\uffff\1\172\1\154"+
        "\1\172\1\144\1\145\3\172\1\160\2\172\1\154\1\151\1\141\1\143\1\156"+
        "\1\145\1\155\1\154\2\163\1\156\1\165\1\151\1\162\1\164\1\143\1\145"+
        "\1\172\1\144\1\151\1\164\1\166\1\157\1\151\3\uffff\1\75\1\uffff"+
        "\1\164\1\145\1\164\2\172\1\145\1\157\1\145\1\172\1\144\2\162\1\141"+
        "\1\172\1\145\1\162\1\145\1\172\1\164\2\172\1\170\1\151\1\164\1\145"+
        "\1\160\1\171\1\164\1\172\1\uffff\1\164\2\162\1\165\1\145\3\uffff"+
        "\1\145\2\uffff\1\172\1\uffff\1\145\1\143\1\uffff\1\156\2\uffff\1"+
        "\163\1\uffff\1\164\1\uffff\1\151\1\141\1\144\5\172\1\164\1\145\1"+
        "\172\1\160\1\156\1\172\1\151\1\172\1\164\1\172\1\uffff\1\151\1\154"+
        "\1\145\1\172\1\162\1\141\2\uffff\1\145\1\163\1\172\2\uffff\1\156"+
        "\1\167\1\172\1\uffff\1\141\1\172\1\164\1\156\1\uffff\1\141\2\145"+
        "\1\uffff\1\172\1\uffff\1\145\1\uffff\1\160\1\156\1\172\1\141\1\145"+
        "\1\172\1\163\1\uffff\1\163\2\172\2\164\1\166\1\uffff\1\166\1\164"+
        "\1\144\1\150\1\145\1\163\1\156\1\141\5\uffff\2\172\1\uffff\1\172"+
        "\1\147\1\172\1\uffff\1\156\1\uffff\1\145\1\uffff\1\162\1\172\1\162"+
        "\1\uffff\1\172\1\142\1\172\1\143\1\uffff\1\164\1\172\1\uffff\1\171"+
        "\1\uffff\1\172\1\143\1\155\1\141\1\156\1\uffff\1\145\1\172\1\55"+
        "\1\uffff\1\155\1\172\1\uffff\2\172\2\uffff\6\172\1\157\1\172\1\145"+
        "\1\172\1\164\3\uffff\1\172\1\uffff\1\143\1\172\1\145\1\uffff\1\156"+
        "\1\uffff\1\154\1\uffff\1\145\1\137\1\uffff\1\172\1\uffff\1\145\1"+
        "\172\1\155\1\172\1\153\1\uffff\1\165\1\172\10\uffff\1\172\1\uffff"+
        "\1\164\1\172\1\uffff\1\143\1\uffff\1\141\1\uffff\1\164\1\uffff\1"+
        "\143\1\172\1\145\1\172\1\164\1\uffff\1\157\1\uffff\1\172\1\uffff"+
        "\1\144\4\uffff\1\172\1\uffff\1\157\1\163\1\172\1\164\1\uffff\1\172"+
        "\1\uffff\1\151\1\146\1\uffff\1\141\1\uffff\1\156\1\161\1\uffff\1"+
        "\151\1\uffff\1\155\1\172\1\171\1\144\1\154\1\157\1\145\1\uffff\3"+
        "\172\1\156\1\163\1\uffff\1\172\2\uffff\1\141\1\164\1\uffff\1\154"+
        "\1\141\1\172\1\155\1\uffff\1\160\1\172\1\uffff";
    static final String DFA30_acceptS =
        "\31\uffff\1\137\1\140\1\141\1\142\1\143\1\144\1\145\1\146\1\147"+
        "\1\uffff\1\152\10\uffff\1\u008a\1\uffff\1\u008c\1\u008d\1\u008e"+
        "\1\u008f\1\u0090\1\u0091\1\u0094\1\u0095\1\u0096\1\u0097\74\uffff"+
        "\1\134\1\162\1\163\1\161\1\150\1\135\1\136\1\uffff\1\u0080\1\u0081"+
        "\1\153\1\151\1\155\1\u0092\1\u0093\1\154\1\157\1\160\1\156\1\165"+
        "\1\164\1\167\1\166\1\uffff\1\174\1\175\1\u0083\1\u0082\1\u0085\1"+
        "\u0086\1\u0084\1\u0088\1\u0089\1\u0087\1\u008b\13\uffff\1\3\1\uffff"+
        "\1\45\2\uffff\1\46\16\uffff\1\10\1\uffff\1\44\3\uffff\1\15\3\uffff"+
        "\1\112\43\uffff\1\177\1\176\1\171\1\uffff\1\170\35\uffff\1\34\5"+
        "\uffff\1\11\1\62\1\17\1\uffff\1\52\1\12\1\uffff\1\16\2\uffff\1\107"+
        "\1\uffff\1\121\1\73\1\uffff\1\20\1\uffff\1\21\22\uffff\1\113\6\uffff"+
        "\1\173\1\172\3\uffff\1\30\1\103\3\uffff\1\32\4\uffff\1\60\3\uffff"+
        "\1\5\1\uffff\1\41\1\uffff\1\56\7\uffff\1\31\6\uffff\1\131\10\uffff"+
        "\1\124\1\33\1\127\1\35\1\43\2\uffff\1\40\3\uffff\1\115\1\uffff\1"+
        "\63\1\uffff\1\114\3\uffff\1\75\4\uffff\1\26\2\uffff\1\14\1\uffff"+
        "\1\37\5\uffff\1\132\3\uffff\1\42\2\uffff\1\13\2\uffff\1\61\1\36"+
        "\13\uffff\1\55\1\130\1\47\1\uffff\1\116\3\uffff\1\111\1\uffff\1"+
        "\76\1\uffff\1\1\2\uffff\1\2\1\uffff\1\57\5\uffff\1\6\2\uffff\1\7"+
        "\1\54\1\77\1\53\1\133\1\25\1\24\1\27\1\uffff\1\122\2\uffff\1\117"+
        "\1\uffff\1\23\1\uffff\1\50\1\uffff\1\105\5\uffff\1\100\1\uffff\1"+
        "\65\1\uffff\1\4\1\uffff\1\70\1\71\1\64\1\123\1\uffff\1\120\4\uffff"+
        "\1\72\1\uffff\1\22\2\uffff\1\66\1\uffff\1\106\2\uffff\1\51\1\uffff"+
        "\1\110\7\uffff\1\102\5\uffff\1\101\1\uffff\1\125\1\74\2\uffff\1"+
        "\126\4\uffff\1\67\2\uffff\1\104";
    static final String DFA30_specialS =
        "\u020b\uffff}>";
    static final String[] DFA30_transitionS = {
            "\2\63\1\uffff\2\63\22\uffff\1\63\1\42\1\65\1\uffff\1\66\1\47"+
            "\1\53\1\64\1\32\1\33\1\46\1\45\1\41\1\26\1\55\1\44\12\67\1\40"+
            "\1\54\1\30\1\27\1\50\1\31\1\62\32\uffff\1\34\1\61\1\35\1\51"+
            "\1\66\1\uffff\1\11\1\4\1\1\1\22\1\7\1\16\1\20\1\21\1\3\1\17"+
            "\1\66\1\5\1\14\1\12\1\10\1\24\1\66\1\6\1\13\1\15\1\23\1\25\1"+
            "\2\3\66\1\36\1\52\1\37\1\43\u187e\uffff\1\60\1\57\1\56",
            "\1\72\15\uffff\1\71\2\uffff\1\70\2\uffff\1\73",
            "\1\76\2\uffff\1\75\1\74",
            "\1\77\3\uffff\1\101\1\100",
            "\1\102\23\uffff\1\103",
            "\1\106\3\uffff\1\105\3\uffff\1\104",
            "\1\107\3\uffff\1\110\11\uffff\1\111",
            "\1\114\1\uffff\1\115\4\uffff\1\112\2\uffff\1\113\1\uffff\1"+
            "\116",
            "\1\122\7\uffff\1\121\3\uffff\1\117\2\uffff\1\120",
            "\1\126\1\uffff\1\123\4\uffff\1\124\1\127\1\uffff\1\125",
            "\1\130\5\uffff\1\131",
            "\1\134\10\uffff\1\136\2\uffff\1\135\2\uffff\1\133\1\132",
            "\1\137\3\uffff\1\141\3\uffff\1\140\11\uffff\1\142",
            "\1\143\11\uffff\1\144",
            "\1\150\7\uffff\1\147\10\uffff\1\145\2\uffff\1\146",
            "\1\151",
            "\1\152",
            "\1\153\15\uffff\1\154",
            "\1\157\3\uffff\1\156\3\uffff\1\155",
            "\1\160",
            "\1\161\20\uffff\1\162",
            "\1\163",
            "\1\166\17\uffff\1\165\1\164",
            "\1\170",
            "\1\173\1\174\1\172",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\176",
            "",
            "\1\u0082\4\uffff\1\u0081\15\uffff\1\u0080",
            "\1\u0085\21\uffff\1\u0084",
            "\1\u0087",
            "\1\u0089",
            "\1\u008c\1\u008b",
            "\1\u008e",
            "\1\u0090\76\uffff\1\u0091",
            "\1\u0094\26\uffff\1\u0093",
            "",
            "\12\67",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u0097",
            "\1\u0098\23\uffff\1\u0099",
            "\1\u009a",
            "\1\u009b",
            "\1\u009c",
            "\1\u009d",
            "\1\u009e",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\15\66\1\u009f\4"+
            "\66\1\u00a0\1\u00a1\6\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\23\66\1\u00a3\6"+
            "\66",
            "\1\u00a5",
            "\1\u00a6",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u00a8\1\uffff\1\u00a9",
            "\1\u00aa",
            "\1\u00ab",
            "\1\u00ac\14\uffff\1\u00ad",
            "\1\u00ae",
            "\1\u00af",
            "\1\u00b0",
            "\1\u00b1",
            "\1\u00b2",
            "\1\u00b3",
            "\1\u00b4",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\3\66\1\u00b5\26"+
            "\66",
            "\1\u00b7",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u00b9",
            "\1\u00ba",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\2\66\1\u00bb\27"+
            "\66",
            "\1\u00be\1\uffff\1\u00bd",
            "\1\u00bf",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u00c1",
            "\1\u00c2",
            "\1\u00c3",
            "\1\u00c4",
            "\1\u00c7\10\uffff\1\u00c5\7\uffff\1\u00c6",
            "\1\u00c8",
            "\1\u00c9",
            "\1\u00ca",
            "\1\u00cc\1\uffff\1\u00cb",
            "\1\u00cd\17\uffff\1\u00ce",
            "\1\u00cf",
            "\1\u00d0",
            "\1\u00d1",
            "\1\u00d2",
            "\1\u00d3",
            "\1\u00d4",
            "\1\u00d5",
            "\1\u00d6",
            "\1\u00d7",
            "\1\u00d8",
            "\1\u00d9",
            "\1\u00da",
            "\1\u00dc\6\uffff\1\u00db",
            "\1\u00dd",
            "\1\u00de\12\uffff\1\u00df",
            "\1\u00e0",
            "\1\u00e1\3\uffff\1\u00e2",
            "\1\u00e3",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u00e4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u00e6\1\u00e7",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u00e9",
            "\1\u00ea",
            "\1\u00eb",
            "\1\u00ec\16\uffff\1\u00ed",
            "\1\u00ee",
            "\1\u00ef",
            "\1\u00f1\3\uffff\1\u00f0",
            "\1\u00f2",
            "\1\u00f3",
            "\1\u00f4\16\uffff\1\u00f5",
            "\1\u00f6",
            "",
            "\1\u00f7",
            "",
            "\1\u00f8",
            "\1\u00f9",
            "",
            "\1\u00fa",
            "\1\u00fb",
            "\1\u00fc",
            "\1\u00fd",
            "\1\u00fe",
            "\1\u00ff",
            "\1\u0100",
            "\1\u0101",
            "\1\u0102",
            "\1\u0104\3\uffff\1\u0103",
            "\1\u0105",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u0107",
            "\1\u0108",
            "",
            "\1\u0109\12\uffff\1\u010a",
            "",
            "\1\u010b",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u010f",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u0112",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u0114",
            "\1\u0115",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\16\66\1\u0117\13"+
            "\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u011a",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\24\66\1\u011c\5"+
            "\66",
            "\1\u011e",
            "\1\u011f",
            "\1\u0120",
            "\1\u0121",
            "\1\u0122",
            "\1\u0123",
            "\1\u0124",
            "\1\u0125",
            "\1\u0126",
            "\1\u0127",
            "\1\u0128",
            "\1\u0129",
            "\1\u012a",
            "\1\u012b",
            "\1\u012c",
            "\1\u012d",
            "\1\u012e",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\22\66\1\u012f\7"+
            "\66",
            "\1\u0131",
            "\1\u0132",
            "\1\u0133",
            "\1\u0134",
            "\1\u0135",
            "\1\u0136",
            "",
            "",
            "",
            "\1\u0137",
            "",
            "\1\u0139",
            "\1\u013a",
            "\1\u013b",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u013e",
            "\1\u013f",
            "\1\u0140",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u0142",
            "\1\u0143",
            "\1\u0144",
            "\1\u0145",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u0147",
            "\1\u0148",
            "\1\u0149",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u014b",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\26\66\1\u014d\3"+
            "\66",
            "\1\u014f",
            "\1\u0150",
            "\1\u0151",
            "\1\u0152",
            "\1\u0153",
            "\1\u0154",
            "\1\u0155",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u0157",
            "\1\u0158",
            "\1\u0159",
            "\1\u015a",
            "\1\u015b",
            "",
            "",
            "",
            "\1\u015c",
            "",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u015e",
            "\1\u015f",
            "",
            "\1\u0160",
            "",
            "",
            "\1\u0161",
            "",
            "\1\u0162",
            "",
            "\1\u0163",
            "\1\u0164",
            "\1\u0165",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u016b",
            "\1\u016c",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u016e",
            "\1\u016f",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\22\66\1\u0170\7"+
            "\66",
            "\1\u0172",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u0174",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u0176",
            "\1\u0177",
            "\1\u0178",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u017a",
            "\1\u017b",
            "",
            "",
            "\1\u017c",
            "\1\u017d",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\u017f",
            "\1\u0180",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u0182",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u0184",
            "\1\u0185",
            "",
            "\1\u0186",
            "\1\u0187",
            "\1\u0188",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u018a",
            "",
            "\1\u018b",
            "\1\u018c",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u018e",
            "\1\u018f",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u0191",
            "",
            "\1\u0192",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u0195",
            "\1\u0196",
            "\1\u0197",
            "",
            "\1\u0198",
            "\1\u0199",
            "\1\u019a",
            "\1\u019b",
            "\1\u019c",
            "\1\u019d",
            "\1\u019e",
            "\1\u019f",
            "",
            "",
            "",
            "",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01a3",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01a5",
            "",
            "\1\u01a6",
            "",
            "\1\u01a7",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01a9",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01ab",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01ad",
            "",
            "\1\u01ae",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01b0",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01b2",
            "\1\u01b3",
            "\1\u01b4",
            "\1\u01b5",
            "",
            "\1\u01b6",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01b8",
            "",
            "\1\u01b9",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\22\66\1\u01c2\7"+
            "\66",
            "\1\u01c4",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\22\66\1\u01c5\7"+
            "\66",
            "\1\u01c7",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01c9",
            "",
            "",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01cb",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01cd",
            "",
            "\1\u01ce",
            "",
            "\1\u01cf",
            "",
            "\1\u01d0",
            "\1\u01d1",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01d3",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01d5",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01d7",
            "",
            "\1\u01d9\13\uffff\1\u01d8",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01dc",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01de",
            "",
            "\1\u01df",
            "",
            "\1\u01e0",
            "",
            "\1\u01e1",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01e3",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01e5",
            "",
            "\1\u01e6",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01e8",
            "",
            "",
            "",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01ea",
            "\1\u01eb",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01ed",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "\1\u01ef",
            "\1\u01f0",
            "",
            "\1\u01f1",
            "",
            "\1\u01f2",
            "\1\u01f3",
            "",
            "\1\u01f4",
            "",
            "\1\u01f5",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u01f7",
            "\1\u01f8",
            "\1\u01f9",
            "\1\u01fa",
            "\1\u01fb",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\22\66\1\u01fd\7"+
            "\66",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u0200",
            "\1\u0201",
            "",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "",
            "",
            "\1\u0203",
            "\1\u0204",
            "",
            "\1\u0205",
            "\1\u0206",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            "\1\u0208",
            "",
            "\1\u0209",
            "\1\66\13\uffff\12\66\45\uffff\1\66\1\uffff\32\66",
            ""
    };

    static final short[] DFA30_eot = DFA.unpackEncodedString(DFA30_eotS);
    static final short[] DFA30_eof = DFA.unpackEncodedString(DFA30_eofS);
    static final char[] DFA30_min = DFA.unpackEncodedStringToUnsignedChars(DFA30_minS);
    static final char[] DFA30_max = DFA.unpackEncodedStringToUnsignedChars(DFA30_maxS);
    static final short[] DFA30_accept = DFA.unpackEncodedString(DFA30_acceptS);
    static final short[] DFA30_special = DFA.unpackEncodedString(DFA30_specialS);
    static final short[][] DFA30_transition;

    static {
        int numStates = DFA30_transitionS.length;
        DFA30_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA30_transition[i] = DFA.unpackEncodedString(DFA30_transitionS[i]);
        }
    }

    class DFA30 extends DFA {

        public DFA30(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 30;
            this.eot = DFA30_eot;
            this.eof = DFA30_eof;
            this.min = DFA30_min;
            this.max = DFA30_max;
            this.accept = DFA30_accept;
            this.special = DFA30_special;
            this.transition = DFA30_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( CREATE | WINDOW | IN_SET | BETWEEN | LIKE | REGEXP | ESCAPE | OR_EXPR | AND_EXPR | NOT_EXPR | EVERY_EXPR | WHERE | AS | SUM | AVG | MAX | MIN | COALESCE | MEDIAN | STDDEV | AVEDEV | COUNT | SELECT | CASE | ELSE | WHEN | THEN | END | FROM | OUTER | INNER | JOIN | LEFT | RIGHT | FULL | ON | IS | BY | GROUP | HAVING | DISTINCT | ALL | OUTPUT | EVENTS | FIRST | LAST | INSERT | INTO | ORDER | ASC | DESC | RSTREAM | ISTREAM | IRSTREAM | UNIDIRECTIONAL | RETAINUNION | RETAININTERSECTION | PATTERN | SQL | METADATASQL | PREVIOUS | PRIOR | EXISTS | WEEKDAY | LW | INSTANCEOF | CAST | CURRENT_TIMESTAMP | DELETE | SNAPSHOT | SET | VARIABLE | UNTIL | AT | TIMEPERIOD_DAY | TIMEPERIOD_DAYS | TIMEPERIOD_HOUR | TIMEPERIOD_HOURS | TIMEPERIOD_MINUTE | TIMEPERIOD_MINUTES | TIMEPERIOD_SEC | TIMEPERIOD_SECOND | TIMEPERIOD_SECONDS | TIMEPERIOD_MILLISEC | TIMEPERIOD_MILLISECOND | TIMEPERIOD_MILLISECONDS | BOOLEAN_TRUE | BOOLEAN_FALSE | VALUE_NULL | ROW_LIMIT_EXPR | OFFSET | FOLLOWED_BY | EQUALS | SQL_NE | QUESTION | LPAREN | RPAREN | LBRACK | RBRACK | LCURLY | RCURLY | COLON | COMMA | EQUAL | LNOT | BNOT | NOT_EQUAL | DIV | DIV_ASSIGN | PLUS | PLUS_ASSIGN | INC | MINUS | MINUS_ASSIGN | DEC | STAR | STAR_ASSIGN | MOD | MOD_ASSIGN | SR | SR_ASSIGN | BSR | BSR_ASSIGN | GE | GT | SL | SL_ASSIGN | LE | LT | BXOR | BXOR_ASSIGN | BOR | BOR_ASSIGN | LOR | BAND | BAND_ASSIGN | LAND | SEMI | DOT | NUM_LONG | NUM_DOUBLE | NUM_FLOAT | ESCAPECHAR | EMAILAT | WS | SL_COMMENT | ML_COMMENT | QUOTED_STRING_LITERAL | STRING_LITERAL | IDENT | NUM_INT );";
        }
    }
 

}