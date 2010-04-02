// $ANTLR 3.1.1 EsperEPL2Grammar.g 2010-04-01 18:20:55

  package com.espertech.esper.epl.generated;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class EsperEPL2GrammarLexer extends Lexer {
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

    // $ANTLR start "EVERY_DISTINCT_EXPR"
    public final void mEVERY_DISTINCT_EXPR() throws RecognitionException {
        try {
            int _type = EVERY_DISTINCT_EXPR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:43:21: ( 'every-distinct' )
            // EsperEPL2Grammar.g:43:23: 'every-distinct'
            {
            match("every-distinct"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EVERY_DISTINCT_EXPR"

    // $ANTLR start "WHERE"
    public final void mWHERE() throws RecognitionException {
        try {
            int _type = WHERE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:44:7: ( 'where' )
            // EsperEPL2Grammar.g:44:9: 'where'
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
            // EsperEPL2Grammar.g:45:4: ( 'as' )
            // EsperEPL2Grammar.g:45:6: 'as'
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
            // EsperEPL2Grammar.g:46:5: ( 'sum' )
            // EsperEPL2Grammar.g:46:7: 'sum'
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
            // EsperEPL2Grammar.g:47:5: ( 'avg' )
            // EsperEPL2Grammar.g:47:7: 'avg'
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
            // EsperEPL2Grammar.g:48:5: ( 'max' )
            // EsperEPL2Grammar.g:48:7: 'max'
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
            // EsperEPL2Grammar.g:49:5: ( 'min' )
            // EsperEPL2Grammar.g:49:7: 'min'
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
            // EsperEPL2Grammar.g:50:10: ( 'coalesce' )
            // EsperEPL2Grammar.g:50:12: 'coalesce'
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
            // EsperEPL2Grammar.g:51:8: ( 'median' )
            // EsperEPL2Grammar.g:51:10: 'median'
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
            // EsperEPL2Grammar.g:52:8: ( 'stddev' )
            // EsperEPL2Grammar.g:52:10: 'stddev'
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
            // EsperEPL2Grammar.g:53:8: ( 'avedev' )
            // EsperEPL2Grammar.g:53:10: 'avedev'
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
            // EsperEPL2Grammar.g:54:7: ( 'count' )
            // EsperEPL2Grammar.g:54:9: 'count'
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
            // EsperEPL2Grammar.g:55:8: ( 'select' )
            // EsperEPL2Grammar.g:55:10: 'select'
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
            // EsperEPL2Grammar.g:56:6: ( 'case' )
            // EsperEPL2Grammar.g:56:8: 'case'
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
            // EsperEPL2Grammar.g:57:6: ( 'else' )
            // EsperEPL2Grammar.g:57:8: 'else'
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
            // EsperEPL2Grammar.g:58:6: ( 'when' )
            // EsperEPL2Grammar.g:58:8: 'when'
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
            // EsperEPL2Grammar.g:59:6: ( 'then' )
            // EsperEPL2Grammar.g:59:8: 'then'
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
            // EsperEPL2Grammar.g:60:5: ( 'end' )
            // EsperEPL2Grammar.g:60:7: 'end'
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
            // EsperEPL2Grammar.g:61:6: ( 'from' )
            // EsperEPL2Grammar.g:61:8: 'from'
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
            // EsperEPL2Grammar.g:62:7: ( 'outer' )
            // EsperEPL2Grammar.g:62:9: 'outer'
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
            // EsperEPL2Grammar.g:63:7: ( 'inner' )
            // EsperEPL2Grammar.g:63:9: 'inner'
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
            // EsperEPL2Grammar.g:64:6: ( 'join' )
            // EsperEPL2Grammar.g:64:8: 'join'
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
            // EsperEPL2Grammar.g:65:6: ( 'left' )
            // EsperEPL2Grammar.g:65:8: 'left'
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
            // EsperEPL2Grammar.g:66:7: ( 'right' )
            // EsperEPL2Grammar.g:66:9: 'right'
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
            // EsperEPL2Grammar.g:67:6: ( 'full' )
            // EsperEPL2Grammar.g:67:8: 'full'
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
            // EsperEPL2Grammar.g:68:4: ( 'on' )
            // EsperEPL2Grammar.g:68:6: 'on'
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
            // EsperEPL2Grammar.g:69:4: ( 'is' )
            // EsperEPL2Grammar.g:69:6: 'is'
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
            // EsperEPL2Grammar.g:70:4: ( 'by' )
            // EsperEPL2Grammar.g:70:6: 'by'
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
            // EsperEPL2Grammar.g:71:7: ( 'group' )
            // EsperEPL2Grammar.g:71:9: 'group'
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
            // EsperEPL2Grammar.g:72:8: ( 'having' )
            // EsperEPL2Grammar.g:72:10: 'having'
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
            // EsperEPL2Grammar.g:73:10: ( 'distinct' )
            // EsperEPL2Grammar.g:73:12: 'distinct'
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
            // EsperEPL2Grammar.g:74:5: ( 'all' )
            // EsperEPL2Grammar.g:74:7: 'all'
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

    // $ANTLR start "ANY"
    public final void mANY() throws RecognitionException {
        try {
            int _type = ANY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:75:5: ( 'any' )
            // EsperEPL2Grammar.g:75:7: 'any'
            {
            match("any"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ANY"

    // $ANTLR start "SOME"
    public final void mSOME() throws RecognitionException {
        try {
            int _type = SOME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:76:6: ( 'some' )
            // EsperEPL2Grammar.g:76:8: 'some'
            {
            match("some"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SOME"

    // $ANTLR start "OUTPUT"
    public final void mOUTPUT() throws RecognitionException {
        try {
            int _type = OUTPUT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:77:8: ( 'output' )
            // EsperEPL2Grammar.g:77:10: 'output'
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
            // EsperEPL2Grammar.g:78:8: ( 'events' )
            // EsperEPL2Grammar.g:78:10: 'events'
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
            // EsperEPL2Grammar.g:79:7: ( 'first' )
            // EsperEPL2Grammar.g:79:9: 'first'
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
            // EsperEPL2Grammar.g:80:6: ( 'last' )
            // EsperEPL2Grammar.g:80:8: 'last'
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
            // EsperEPL2Grammar.g:81:8: ( 'insert' )
            // EsperEPL2Grammar.g:81:10: 'insert'
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
            // EsperEPL2Grammar.g:82:6: ( 'into' )
            // EsperEPL2Grammar.g:82:8: 'into'
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
            // EsperEPL2Grammar.g:83:7: ( 'order' )
            // EsperEPL2Grammar.g:83:9: 'order'
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
            // EsperEPL2Grammar.g:84:5: ( 'asc' )
            // EsperEPL2Grammar.g:84:7: 'asc'
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
            // EsperEPL2Grammar.g:85:6: ( 'desc' )
            // EsperEPL2Grammar.g:85:8: 'desc'
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
            // EsperEPL2Grammar.g:86:9: ( 'rstream' )
            // EsperEPL2Grammar.g:86:11: 'rstream'
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
            // EsperEPL2Grammar.g:87:9: ( 'istream' )
            // EsperEPL2Grammar.g:87:11: 'istream'
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
            // EsperEPL2Grammar.g:88:10: ( 'irstream' )
            // EsperEPL2Grammar.g:88:12: 'irstream'
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

    // $ANTLR start "SCHEMA"
    public final void mSCHEMA() throws RecognitionException {
        try {
            int _type = SCHEMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:89:8: ( 'schema' )
            // EsperEPL2Grammar.g:89:10: 'schema'
            {
            match("schema"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SCHEMA"

    // $ANTLR start "UNIDIRECTIONAL"
    public final void mUNIDIRECTIONAL() throws RecognitionException {
        try {
            int _type = UNIDIRECTIONAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:90:16: ( 'unidirectional' )
            // EsperEPL2Grammar.g:90:18: 'unidirectional'
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
            // EsperEPL2Grammar.g:91:13: ( 'retain-union' )
            // EsperEPL2Grammar.g:91:15: 'retain-union'
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
            // EsperEPL2Grammar.g:92:20: ( 'retain-intersection' )
            // EsperEPL2Grammar.g:92:22: 'retain-intersection'
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
            // EsperEPL2Grammar.g:93:9: ( 'pattern' )
            // EsperEPL2Grammar.g:93:11: 'pattern'
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
            // EsperEPL2Grammar.g:94:5: ( 'sql' )
            // EsperEPL2Grammar.g:94:7: 'sql'
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
            // EsperEPL2Grammar.g:95:13: ( 'metadatasql' )
            // EsperEPL2Grammar.g:95:15: 'metadatasql'
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
            // EsperEPL2Grammar.g:96:10: ( 'prev' )
            // EsperEPL2Grammar.g:96:12: 'prev'
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
            // EsperEPL2Grammar.g:97:7: ( 'prior' )
            // EsperEPL2Grammar.g:97:9: 'prior'
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
            // EsperEPL2Grammar.g:98:8: ( 'exists' )
            // EsperEPL2Grammar.g:98:10: 'exists'
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
            // EsperEPL2Grammar.g:99:9: ( 'weekday' )
            // EsperEPL2Grammar.g:99:11: 'weekday'
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
            // EsperEPL2Grammar.g:100:4: ( 'lastweekday' )
            // EsperEPL2Grammar.g:100:6: 'lastweekday'
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
            // EsperEPL2Grammar.g:101:12: ( 'instanceof' )
            // EsperEPL2Grammar.g:101:14: 'instanceof'
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
            // EsperEPL2Grammar.g:102:6: ( 'cast' )
            // EsperEPL2Grammar.g:102:8: 'cast'
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
            // EsperEPL2Grammar.g:103:19: ( 'current_timestamp' )
            // EsperEPL2Grammar.g:103:21: 'current_timestamp'
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
            // EsperEPL2Grammar.g:104:8: ( 'delete' )
            // EsperEPL2Grammar.g:104:10: 'delete'
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
            // EsperEPL2Grammar.g:105:10: ( 'snapshot' )
            // EsperEPL2Grammar.g:105:12: 'snapshot'
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
            // EsperEPL2Grammar.g:106:5: ( 'set' )
            // EsperEPL2Grammar.g:106:7: 'set'
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
            // EsperEPL2Grammar.g:107:10: ( 'variable' )
            // EsperEPL2Grammar.g:107:12: 'variable'
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
            // EsperEPL2Grammar.g:108:7: ( 'until' )
            // EsperEPL2Grammar.g:108:9: 'until'
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
            // EsperEPL2Grammar.g:109:4: ( 'at' )
            // EsperEPL2Grammar.g:109:6: 'at'
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

    // $ANTLR start "INDEX"
    public final void mINDEX() throws RecognitionException {
        try {
            int _type = INDEX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:110:7: ( 'index' )
            // EsperEPL2Grammar.g:110:9: 'index'
            {
            match("index"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INDEX"

    // $ANTLR start "TIMEPERIOD_DAY"
    public final void mTIMEPERIOD_DAY() throws RecognitionException {
        try {
            int _type = TIMEPERIOD_DAY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:111:16: ( 'day' )
            // EsperEPL2Grammar.g:111:18: 'day'
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
            // EsperEPL2Grammar.g:112:17: ( 'days' )
            // EsperEPL2Grammar.g:112:19: 'days'
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
            // EsperEPL2Grammar.g:113:17: ( 'hour' )
            // EsperEPL2Grammar.g:113:19: 'hour'
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
            // EsperEPL2Grammar.g:114:18: ( 'hours' )
            // EsperEPL2Grammar.g:114:20: 'hours'
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
            // EsperEPL2Grammar.g:115:19: ( 'minute' )
            // EsperEPL2Grammar.g:115:21: 'minute'
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
            // EsperEPL2Grammar.g:116:20: ( 'minutes' )
            // EsperEPL2Grammar.g:116:22: 'minutes'
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
            // EsperEPL2Grammar.g:117:16: ( 'sec' )
            // EsperEPL2Grammar.g:117:18: 'sec'
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
            // EsperEPL2Grammar.g:118:19: ( 'second' )
            // EsperEPL2Grammar.g:118:21: 'second'
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
            // EsperEPL2Grammar.g:119:20: ( 'seconds' )
            // EsperEPL2Grammar.g:119:22: 'seconds'
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
            // EsperEPL2Grammar.g:120:21: ( 'msec' )
            // EsperEPL2Grammar.g:120:23: 'msec'
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
            // EsperEPL2Grammar.g:121:24: ( 'millisecond' )
            // EsperEPL2Grammar.g:121:26: 'millisecond'
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
            // EsperEPL2Grammar.g:122:25: ( 'milliseconds' )
            // EsperEPL2Grammar.g:122:27: 'milliseconds'
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
            // EsperEPL2Grammar.g:123:14: ( 'true' )
            // EsperEPL2Grammar.g:123:16: 'true'
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
            // EsperEPL2Grammar.g:124:15: ( 'false' )
            // EsperEPL2Grammar.g:124:17: 'false'
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
            // EsperEPL2Grammar.g:125:12: ( 'null' )
            // EsperEPL2Grammar.g:125:14: 'null'
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
            // EsperEPL2Grammar.g:126:16: ( 'limit' )
            // EsperEPL2Grammar.g:126:18: 'limit'
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
            // EsperEPL2Grammar.g:127:8: ( 'offset' )
            // EsperEPL2Grammar.g:127:10: 'offset'
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

    // $ANTLR start "UPDATE"
    public final void mUPDATE() throws RecognitionException {
        try {
            int _type = UPDATE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:128:8: ( 'update' )
            // EsperEPL2Grammar.g:128:10: 'update'
            {
            match("update"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UPDATE"

    // $ANTLR start "MATCH_RECOGNIZE"
    public final void mMATCH_RECOGNIZE() throws RecognitionException {
        try {
            int _type = MATCH_RECOGNIZE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:129:17: ( 'match_recognize' )
            // EsperEPL2Grammar.g:129:19: 'match_recognize'
            {
            match("match_recognize"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MATCH_RECOGNIZE"

    // $ANTLR start "MEASURES"
    public final void mMEASURES() throws RecognitionException {
        try {
            int _type = MEASURES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:130:10: ( 'measures' )
            // EsperEPL2Grammar.g:130:12: 'measures'
            {
            match("measures"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MEASURES"

    // $ANTLR start "DEFINE"
    public final void mDEFINE() throws RecognitionException {
        try {
            int _type = DEFINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:131:8: ( 'define' )
            // EsperEPL2Grammar.g:131:10: 'define'
            {
            match("define"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DEFINE"

    // $ANTLR start "PARTITION"
    public final void mPARTITION() throws RecognitionException {
        try {
            int _type = PARTITION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:132:11: ( 'partition' )
            // EsperEPL2Grammar.g:132:13: 'partition'
            {
            match("partition"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PARTITION"

    // $ANTLR start "MATCHES"
    public final void mMATCHES() throws RecognitionException {
        try {
            int _type = MATCHES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:133:9: ( 'matches' )
            // EsperEPL2Grammar.g:133:11: 'matches'
            {
            match("matches"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MATCHES"

    // $ANTLR start "AFTER"
    public final void mAFTER() throws RecognitionException {
        try {
            int _type = AFTER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:134:7: ( 'after' )
            // EsperEPL2Grammar.g:134:9: 'after'
            {
            match("after"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AFTER"

    // $ANTLR start "FOLLOWED_BY"
    public final void mFOLLOWED_BY() throws RecognitionException {
        try {
            int _type = FOLLOWED_BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1709:14: ( '->' )
            // EsperEPL2Grammar.g:1709:16: '->'
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
            // EsperEPL2Grammar.g:1710:10: ( '=' )
            // EsperEPL2Grammar.g:1710:12: '='
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
            // EsperEPL2Grammar.g:1711:10: ( '<>' )
            // EsperEPL2Grammar.g:1711:12: '<>'
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
            // EsperEPL2Grammar.g:1712:11: ( '?' )
            // EsperEPL2Grammar.g:1712:13: '?'
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
            // EsperEPL2Grammar.g:1713:10: ( '(' )
            // EsperEPL2Grammar.g:1713:12: '('
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
            // EsperEPL2Grammar.g:1714:10: ( ')' )
            // EsperEPL2Grammar.g:1714:12: ')'
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
            // EsperEPL2Grammar.g:1715:10: ( '[' )
            // EsperEPL2Grammar.g:1715:12: '['
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
            // EsperEPL2Grammar.g:1716:10: ( ']' )
            // EsperEPL2Grammar.g:1716:12: ']'
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
            // EsperEPL2Grammar.g:1717:10: ( '{' )
            // EsperEPL2Grammar.g:1717:12: '{'
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
            // EsperEPL2Grammar.g:1718:10: ( '}' )
            // EsperEPL2Grammar.g:1718:12: '}'
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
            // EsperEPL2Grammar.g:1719:9: ( ':' )
            // EsperEPL2Grammar.g:1719:11: ':'
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
            // EsperEPL2Grammar.g:1720:9: ( ',' )
            // EsperEPL2Grammar.g:1720:11: ','
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
            // EsperEPL2Grammar.g:1721:9: ( '==' )
            // EsperEPL2Grammar.g:1721:11: '=='
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
            // EsperEPL2Grammar.g:1722:8: ( '!' )
            // EsperEPL2Grammar.g:1722:10: '!'
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
            // EsperEPL2Grammar.g:1723:8: ( '~' )
            // EsperEPL2Grammar.g:1723:10: '~'
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
            // EsperEPL2Grammar.g:1724:12: ( '!=' )
            // EsperEPL2Grammar.g:1724:14: '!='
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
            // EsperEPL2Grammar.g:1725:7: ( '/' )
            // EsperEPL2Grammar.g:1725:9: '/'
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
            // EsperEPL2Grammar.g:1726:13: ( '/=' )
            // EsperEPL2Grammar.g:1726:15: '/='
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
            // EsperEPL2Grammar.g:1727:8: ( '+' )
            // EsperEPL2Grammar.g:1727:10: '+'
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
            // EsperEPL2Grammar.g:1728:13: ( '+=' )
            // EsperEPL2Grammar.g:1728:15: '+='
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
            // EsperEPL2Grammar.g:1729:7: ( '++' )
            // EsperEPL2Grammar.g:1729:9: '++'
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
            // EsperEPL2Grammar.g:1730:9: ( '-' )
            // EsperEPL2Grammar.g:1730:11: '-'
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
            // EsperEPL2Grammar.g:1731:15: ( '-=' )
            // EsperEPL2Grammar.g:1731:17: '-='
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
            // EsperEPL2Grammar.g:1732:7: ( '--' )
            // EsperEPL2Grammar.g:1732:9: '--'
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
            // EsperEPL2Grammar.g:1733:8: ( '*' )
            // EsperEPL2Grammar.g:1733:10: '*'
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
            // EsperEPL2Grammar.g:1734:14: ( '*=' )
            // EsperEPL2Grammar.g:1734:16: '*='
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
            // EsperEPL2Grammar.g:1735:7: ( '%' )
            // EsperEPL2Grammar.g:1735:9: '%'
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
            // EsperEPL2Grammar.g:1736:13: ( '%=' )
            // EsperEPL2Grammar.g:1736:15: '%='
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
            // EsperEPL2Grammar.g:1737:6: ( '>>' )
            // EsperEPL2Grammar.g:1737:8: '>>'
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
            // EsperEPL2Grammar.g:1738:12: ( '>>=' )
            // EsperEPL2Grammar.g:1738:14: '>>='
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
            // EsperEPL2Grammar.g:1739:7: ( '>>>' )
            // EsperEPL2Grammar.g:1739:9: '>>>'
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
            // EsperEPL2Grammar.g:1740:13: ( '>>>=' )
            // EsperEPL2Grammar.g:1740:15: '>>>='
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
            // EsperEPL2Grammar.g:1741:6: ( '>=' )
            // EsperEPL2Grammar.g:1741:8: '>='
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
            // EsperEPL2Grammar.g:1742:6: ( '>' )
            // EsperEPL2Grammar.g:1742:8: '>'
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
            // EsperEPL2Grammar.g:1743:6: ( '<<' )
            // EsperEPL2Grammar.g:1743:8: '<<'
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
            // EsperEPL2Grammar.g:1744:12: ( '<<=' )
            // EsperEPL2Grammar.g:1744:14: '<<='
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
            // EsperEPL2Grammar.g:1745:6: ( '<=' )
            // EsperEPL2Grammar.g:1745:8: '<='
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
            // EsperEPL2Grammar.g:1746:6: ( '<' )
            // EsperEPL2Grammar.g:1746:8: '<'
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
            // EsperEPL2Grammar.g:1747:8: ( '^' )
            // EsperEPL2Grammar.g:1747:10: '^'
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
            // EsperEPL2Grammar.g:1748:14: ( '^=' )
            // EsperEPL2Grammar.g:1748:16: '^='
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
            // EsperEPL2Grammar.g:1749:6: ( '|' )
            // EsperEPL2Grammar.g:1749:8: '|'
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
            // EsperEPL2Grammar.g:1750:13: ( '|=' )
            // EsperEPL2Grammar.g:1750:15: '|='
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
            // EsperEPL2Grammar.g:1751:6: ( '||' )
            // EsperEPL2Grammar.g:1751:8: '||'
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
            // EsperEPL2Grammar.g:1752:8: ( '&' )
            // EsperEPL2Grammar.g:1752:10: '&'
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
            // EsperEPL2Grammar.g:1753:14: ( '&=' )
            // EsperEPL2Grammar.g:1753:16: '&='
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
            // EsperEPL2Grammar.g:1754:8: ( '&&' )
            // EsperEPL2Grammar.g:1754:10: '&&'
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
            // EsperEPL2Grammar.g:1755:8: ( ';' )
            // EsperEPL2Grammar.g:1755:10: ';'
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
            // EsperEPL2Grammar.g:1756:7: ( '.' )
            // EsperEPL2Grammar.g:1756:9: '.'
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
            // EsperEPL2Grammar.g:1757:10: ( '\\u18FF' )
            // EsperEPL2Grammar.g:1757:12: '\\u18FF'
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
            // EsperEPL2Grammar.g:1758:12: ( '\\u18FE' )
            // EsperEPL2Grammar.g:1758:14: '\\u18FE'
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
            // EsperEPL2Grammar.g:1759:11: ( '\\u18FD' )
            // EsperEPL2Grammar.g:1759:13: '\\u18FD'
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
            // EsperEPL2Grammar.g:1760:12: ( '\\\\' )
            // EsperEPL2Grammar.g:1760:14: '\\\\'
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
            // EsperEPL2Grammar.g:1761:10: ( '@' )
            // EsperEPL2Grammar.g:1761:12: '@'
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
            // EsperEPL2Grammar.g:1764:4: ( ( ' ' | '\\t' | '\\f' | ( '\\r' | '\\n' ) )+ )
            // EsperEPL2Grammar.g:1764:6: ( ' ' | '\\t' | '\\f' | ( '\\r' | '\\n' ) )+
            {
            // EsperEPL2Grammar.g:1764:6: ( ' ' | '\\t' | '\\f' | ( '\\r' | '\\n' ) )+
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
            // EsperEPL2Grammar.g:1778:2: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\n' | '\\r' ( '\\n' )? )? )
            // EsperEPL2Grammar.g:1778:4: '//' (~ ( '\\n' | '\\r' ) )* ( '\\n' | '\\r' ( '\\n' )? )?
            {
            match("//"); if (state.failed) return ;

            // EsperEPL2Grammar.g:1779:3: (~ ( '\\n' | '\\r' ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='\u0000' && LA2_0<='\t')||(LA2_0>='\u000B' && LA2_0<='\f')||(LA2_0>='\u000E' && LA2_0<='\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // EsperEPL2Grammar.g:1779:4: ~ ( '\\n' | '\\r' )
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

            // EsperEPL2Grammar.g:1779:19: ( '\\n' | '\\r' ( '\\n' )? )?
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
                    // EsperEPL2Grammar.g:1779:20: '\\n'
                    {
                    match('\n'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // EsperEPL2Grammar.g:1779:25: '\\r' ( '\\n' )?
                    {
                    match('\r'); if (state.failed) return ;
                    // EsperEPL2Grammar.g:1779:29: ( '\\n' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='\n') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // EsperEPL2Grammar.g:1779:30: '\\n'
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
            // EsperEPL2Grammar.g:1785:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // EsperEPL2Grammar.g:1785:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); if (state.failed) return ;

            // EsperEPL2Grammar.g:1785:14: ( options {greedy=false; } : . )*
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
            	    // EsperEPL2Grammar.g:1785:42: .
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

    // $ANTLR start "TICKED_STRING_LITERAL"
    public final void mTICKED_STRING_LITERAL() throws RecognitionException {
        try {
            int _type = TICKED_STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1789:5: ( '`' ( EscapeSequence | ~ ( '\\`' | '\\\\' ) )* '`' )
            // EsperEPL2Grammar.g:1789:9: '`' ( EscapeSequence | ~ ( '\\`' | '\\\\' ) )* '`'
            {
            match('`'); if (state.failed) return ;
            // EsperEPL2Grammar.g:1789:13: ( EscapeSequence | ~ ( '\\`' | '\\\\' ) )*
            loop6:
            do {
                int alt6=3;
                int LA6_0 = input.LA(1);

                if ( (LA6_0=='\\') ) {
                    alt6=1;
                }
                else if ( ((LA6_0>='\u0000' && LA6_0<='[')||(LA6_0>=']' && LA6_0<='_')||(LA6_0>='a' && LA6_0<='\uFFFF')) ) {
                    alt6=2;
                }


                switch (alt6) {
            	case 1 :
            	    // EsperEPL2Grammar.g:1789:15: EscapeSequence
            	    {
            	    mEscapeSequence(); if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    // EsperEPL2Grammar.g:1789:32: ~ ( '\\`' | '\\\\' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='_')||(input.LA(1)>='a' && input.LA(1)<='\uFFFF') ) {
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

            match('`'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TICKED_STRING_LITERAL"

    // $ANTLR start "QUOTED_STRING_LITERAL"
    public final void mQUOTED_STRING_LITERAL() throws RecognitionException {
        try {
            int _type = QUOTED_STRING_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1793:5: ( '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )* '\\'' )
            // EsperEPL2Grammar.g:1793:9: '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )* '\\''
            {
            match('\''); if (state.failed) return ;
            // EsperEPL2Grammar.g:1793:14: ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )*
            loop7:
            do {
                int alt7=3;
                int LA7_0 = input.LA(1);

                if ( (LA7_0=='\\') ) {
                    alt7=1;
                }
                else if ( ((LA7_0>='\u0000' && LA7_0<='&')||(LA7_0>='(' && LA7_0<='[')||(LA7_0>=']' && LA7_0<='\uFFFF')) ) {
                    alt7=2;
                }


                switch (alt7) {
            	case 1 :
            	    // EsperEPL2Grammar.g:1793:16: EscapeSequence
            	    {
            	    mEscapeSequence(); if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    // EsperEPL2Grammar.g:1793:33: ~ ( '\\'' | '\\\\' )
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
            	    break loop7;
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
            // EsperEPL2Grammar.g:1797:5: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' )
            // EsperEPL2Grammar.g:1797:8: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); if (state.failed) return ;
            // EsperEPL2Grammar.g:1797:12: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
            loop8:
            do {
                int alt8=3;
                int LA8_0 = input.LA(1);

                if ( (LA8_0=='\\') ) {
                    alt8=1;
                }
                else if ( ((LA8_0>='\u0000' && LA8_0<='!')||(LA8_0>='#' && LA8_0<='[')||(LA8_0>=']' && LA8_0<='\uFFFF')) ) {
                    alt8=2;
                }


                switch (alt8) {
            	case 1 :
            	    // EsperEPL2Grammar.g:1797:14: EscapeSequence
            	    {
            	    mEscapeSequence(); if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    // EsperEPL2Grammar.g:1797:31: ~ ( '\\\\' | '\"' )
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
            	    break loop8;
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
            // EsperEPL2Grammar.g:1801:16: ( '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | UnicodeEscape | OctalEscape | . ) )
            // EsperEPL2Grammar.g:1801:18: '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | UnicodeEscape | OctalEscape | . )
            {
            match('\\'); if (state.failed) return ;
            // EsperEPL2Grammar.g:1802:3: ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | UnicodeEscape | OctalEscape | . )
            int alt9=11;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='n') ) {
                alt9=1;
            }
            else if ( (LA9_0=='r') ) {
                alt9=2;
            }
            else if ( (LA9_0=='t') ) {
                alt9=3;
            }
            else if ( (LA9_0=='b') ) {
                alt9=4;
            }
            else if ( (LA9_0=='f') ) {
                alt9=5;
            }
            else if ( (LA9_0=='\"') ) {
                alt9=6;
            }
            else if ( (LA9_0=='\'') ) {
                alt9=7;
            }
            else if ( (LA9_0=='\\') ) {
                switch ( input.LA(2) ) {
                case 'u':
                    {
                    alt9=9;
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
                    alt9=10;
                    }
                    break;
                default:
                    alt9=8;}

            }
            else if ( ((LA9_0>='\u0000' && LA9_0<='!')||(LA9_0>='#' && LA9_0<='&')||(LA9_0>='(' && LA9_0<='[')||(LA9_0>=']' && LA9_0<='a')||(LA9_0>='c' && LA9_0<='e')||(LA9_0>='g' && LA9_0<='m')||(LA9_0>='o' && LA9_0<='q')||LA9_0=='s'||(LA9_0>='u' && LA9_0<='\uFFFF')) ) {
                alt9=11;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // EsperEPL2Grammar.g:1802:5: 'n'
                    {
                    match('n'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // EsperEPL2Grammar.g:1803:5: 'r'
                    {
                    match('r'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // EsperEPL2Grammar.g:1804:5: 't'
                    {
                    match('t'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // EsperEPL2Grammar.g:1805:5: 'b'
                    {
                    match('b'); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // EsperEPL2Grammar.g:1806:5: 'f'
                    {
                    match('f'); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    // EsperEPL2Grammar.g:1807:5: '\"'
                    {
                    match('\"'); if (state.failed) return ;

                    }
                    break;
                case 7 :
                    // EsperEPL2Grammar.g:1808:5: '\\''
                    {
                    match('\''); if (state.failed) return ;

                    }
                    break;
                case 8 :
                    // EsperEPL2Grammar.g:1809:5: '\\\\'
                    {
                    match('\\'); if (state.failed) return ;

                    }
                    break;
                case 9 :
                    // EsperEPL2Grammar.g:1810:5: UnicodeEscape
                    {
                    mUnicodeEscape(); if (state.failed) return ;

                    }
                    break;
                case 10 :
                    // EsperEPL2Grammar.g:1811:5: OctalEscape
                    {
                    mOctalEscape(); if (state.failed) return ;

                    }
                    break;
                case 11 :
                    // EsperEPL2Grammar.g:1812:5: .
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
            // EsperEPL2Grammar.g:1818:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt10=3;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='\\') ) {
                int LA10_1 = input.LA(2);

                if ( ((LA10_1>='0' && LA10_1<='3')) ) {
                    int LA10_2 = input.LA(3);

                    if ( ((LA10_2>='0' && LA10_2<='7')) ) {
                        int LA10_4 = input.LA(4);

                        if ( ((LA10_4>='0' && LA10_4<='7')) ) {
                            alt10=1;
                        }
                        else {
                            alt10=2;}
                    }
                    else {
                        alt10=3;}
                }
                else if ( ((LA10_1>='4' && LA10_1<='7')) ) {
                    int LA10_3 = input.LA(3);

                    if ( ((LA10_3>='0' && LA10_3<='7')) ) {
                        alt10=2;
                    }
                    else {
                        alt10=3;}
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // EsperEPL2Grammar.g:1818:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); if (state.failed) return ;
                    // EsperEPL2Grammar.g:1818:14: ( '0' .. '3' )
                    // EsperEPL2Grammar.g:1818:15: '0' .. '3'
                    {
                    matchRange('0','3'); if (state.failed) return ;

                    }

                    // EsperEPL2Grammar.g:1818:25: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1818:26: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }

                    // EsperEPL2Grammar.g:1818:36: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1818:37: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Grammar.g:1819:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); if (state.failed) return ;
                    // EsperEPL2Grammar.g:1819:14: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1819:15: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }

                    // EsperEPL2Grammar.g:1819:25: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1819:26: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Grammar.g:1820:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); if (state.failed) return ;
                    // EsperEPL2Grammar.g:1820:14: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1820:15: '0' .. '7'
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
            // EsperEPL2Grammar.g:1824:10: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // EsperEPL2Grammar.g:1824:12: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
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
            // EsperEPL2Grammar.g:1828:5: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit )
            // EsperEPL2Grammar.g:1828:9: '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit
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
            // EsperEPL2Grammar.g:1835:2: ( ( 'a' .. 'z' | '_' | '$' ) ( 'a' .. 'z' | '_' | '0' .. '9' | '$' )* )
            // EsperEPL2Grammar.g:1835:4: ( 'a' .. 'z' | '_' | '$' ) ( 'a' .. 'z' | '_' | '0' .. '9' | '$' )*
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

            // EsperEPL2Grammar.g:1835:23: ( 'a' .. 'z' | '_' | '0' .. '9' | '$' )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0=='$'||(LA11_0>='0' && LA11_0<='9')||LA11_0=='_'||(LA11_0>='a' && LA11_0<='z')) ) {
                    alt11=1;
                }


                switch (alt11) {
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
            	    break loop11;
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
            // EsperEPL2Grammar.g:1842:5: ( '.' ( ( '0' .. '9' )+ ( EXPONENT )? (f1= FLOAT_SUFFIX )? )? | ( '0' ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )? | ( '1' .. '9' ) ( '0' .. '9' )* ) ( ( 'l' ) | {...}? ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX ) )? )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0=='.') ) {
                alt28=1;
            }
            else if ( ((LA28_0>='0' && LA28_0<='9')) ) {
                alt28=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // EsperEPL2Grammar.g:1842:9: '.' ( ( '0' .. '9' )+ ( EXPONENT )? (f1= FLOAT_SUFFIX )? )?
                    {
                    match('.'); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                      _type = DOT;
                    }
                    // EsperEPL2Grammar.g:1843:13: ( ( '0' .. '9' )+ ( EXPONENT )? (f1= FLOAT_SUFFIX )? )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( ((LA15_0>='0' && LA15_0<='9')) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // EsperEPL2Grammar.g:1843:15: ( '0' .. '9' )+ ( EXPONENT )? (f1= FLOAT_SUFFIX )?
                            {
                            // EsperEPL2Grammar.g:1843:15: ( '0' .. '9' )+
                            int cnt12=0;
                            loop12:
                            do {
                                int alt12=2;
                                int LA12_0 = input.LA(1);

                                if ( ((LA12_0>='0' && LA12_0<='9')) ) {
                                    alt12=1;
                                }


                                switch (alt12) {
                            	case 1 :
                            	    // EsperEPL2Grammar.g:1843:16: '0' .. '9'
                            	    {
                            	    matchRange('0','9'); if (state.failed) return ;

                            	    }
                            	    break;

                            	default :
                            	    if ( cnt12 >= 1 ) break loop12;
                            	    if (state.backtracking>0) {state.failed=true; return ;}
                                        EarlyExitException eee =
                                            new EarlyExitException(12, input);
                                        throw eee;
                                }
                                cnt12++;
                            } while (true);

                            // EsperEPL2Grammar.g:1843:27: ( EXPONENT )?
                            int alt13=2;
                            int LA13_0 = input.LA(1);

                            if ( (LA13_0=='e') ) {
                                alt13=1;
                            }
                            switch (alt13) {
                                case 1 :
                                    // EsperEPL2Grammar.g:1843:28: EXPONENT
                                    {
                                    mEXPONENT(); if (state.failed) return ;

                                    }
                                    break;

                            }

                            // EsperEPL2Grammar.g:1843:39: (f1= FLOAT_SUFFIX )?
                            int alt14=2;
                            int LA14_0 = input.LA(1);

                            if ( (LA14_0=='d'||LA14_0=='f') ) {
                                alt14=1;
                            }
                            switch (alt14) {
                                case 1 :
                                    // EsperEPL2Grammar.g:1843:40: f1= FLOAT_SUFFIX
                                    {
                                    int f1Start1933 = getCharIndex();
                                    mFLOAT_SUFFIX(); if (state.failed) return ;
                                    f1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, f1Start1933, getCharIndex()-1);
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
                    // EsperEPL2Grammar.g:1854:4: ( '0' ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )? | ( '1' .. '9' ) ( '0' .. '9' )* ) ( ( 'l' ) | {...}? ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX ) )?
                    {
                    // EsperEPL2Grammar.g:1854:4: ( '0' ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )? | ( '1' .. '9' ) ( '0' .. '9' )* )
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0=='0') ) {
                        alt21=1;
                    }
                    else if ( ((LA21_0>='1' && LA21_0<='9')) ) {
                        alt21=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 21, 0, input);

                        throw nvae;
                    }
                    switch (alt21) {
                        case 1 :
                            // EsperEPL2Grammar.g:1854:6: '0' ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )?
                            {
                            match('0'); if (state.failed) return ;
                            if ( state.backtracking==0 ) {
                              isDecimal = true;
                            }
                            // EsperEPL2Grammar.g:1855:4: ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )?
                            int alt19=4;
                            int LA19_0 = input.LA(1);

                            if ( (LA19_0=='x') ) {
                                alt19=1;
                            }
                            else if ( ((LA19_0>='0' && LA19_0<='7')) ) {
                                int LA19_2 = input.LA(2);

                                if ( (synpred1_EsperEPL2Grammar()) ) {
                                    alt19=2;
                                }
                                else if ( (true) ) {
                                    alt19=3;
                                }
                            }
                            else if ( ((LA19_0>='8' && LA19_0<='9')) && (synpred1_EsperEPL2Grammar())) {
                                alt19=2;
                            }
                            switch (alt19) {
                                case 1 :
                                    // EsperEPL2Grammar.g:1855:6: ( 'x' ) ( HexDigit )+
                                    {
                                    // EsperEPL2Grammar.g:1855:6: ( 'x' )
                                    // EsperEPL2Grammar.g:1855:7: 'x'
                                    {
                                    match('x'); if (state.failed) return ;

                                    }

                                    // EsperEPL2Grammar.g:1856:5: ( HexDigit )+
                                    int cnt16=0;
                                    loop16:
                                    do {
                                        int alt16=2;
                                        switch ( input.LA(1) ) {
                                        case 'e':
                                            {
                                            int LA16_2 = input.LA(2);

                                            if ( ((LA16_2>='0' && LA16_2<='9')) ) {
                                                int LA16_5 = input.LA(3);

                                                if ( (!(((isDecimal)))) ) {
                                                    alt16=1;
                                                }


                                            }

                                            else {
                                                alt16=1;
                                            }

                                            }
                                            break;
                                        case 'd':
                                        case 'f':
                                            {
                                            int LA16_3 = input.LA(2);

                                            if ( (!(((isDecimal)))) ) {
                                                alt16=1;
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
                                            alt16=1;
                                            }
                                            break;

                                        }

                                        switch (alt16) {
                                    	case 1 :
                                    	    // EsperEPL2Grammar.g:1862:6: HexDigit
                                    	    {
                                    	    mHexDigit(); if (state.failed) return ;

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
                                case 2 :
                                    // EsperEPL2Grammar.g:1866:5: ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+
                                    {
                                    // EsperEPL2Grammar.g:1866:50: ( '0' .. '9' )+
                                    int cnt17=0;
                                    loop17:
                                    do {
                                        int alt17=2;
                                        int LA17_0 = input.LA(1);

                                        if ( ((LA17_0>='0' && LA17_0<='9')) ) {
                                            alt17=1;
                                        }


                                        switch (alt17) {
                                    	case 1 :
                                    	    // EsperEPL2Grammar.g:1866:51: '0' .. '9'
                                    	    {
                                    	    matchRange('0','9'); if (state.failed) return ;

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
                                case 3 :
                                    // EsperEPL2Grammar.g:1868:6: ( '0' .. '7' )+
                                    {
                                    // EsperEPL2Grammar.g:1868:6: ( '0' .. '7' )+
                                    int cnt18=0;
                                    loop18:
                                    do {
                                        int alt18=2;
                                        int LA18_0 = input.LA(1);

                                        if ( ((LA18_0>='0' && LA18_0<='7')) ) {
                                            alt18=1;
                                        }


                                        switch (alt18) {
                                    	case 1 :
                                    	    // EsperEPL2Grammar.g:1868:7: '0' .. '7'
                                    	    {
                                    	    matchRange('0','7'); if (state.failed) return ;

                                    	    }
                                    	    break;

                                    	default :
                                    	    if ( cnt18 >= 1 ) break loop18;
                                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                                EarlyExitException eee =
                                                    new EarlyExitException(18, input);
                                                throw eee;
                                        }
                                        cnt18++;
                                    } while (true);


                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // EsperEPL2Grammar.g:1870:5: ( '1' .. '9' ) ( '0' .. '9' )*
                            {
                            // EsperEPL2Grammar.g:1870:5: ( '1' .. '9' )
                            // EsperEPL2Grammar.g:1870:6: '1' .. '9'
                            {
                            matchRange('1','9'); if (state.failed) return ;

                            }

                            // EsperEPL2Grammar.g:1870:16: ( '0' .. '9' )*
                            loop20:
                            do {
                                int alt20=2;
                                int LA20_0 = input.LA(1);

                                if ( ((LA20_0>='0' && LA20_0<='9')) ) {
                                    alt20=1;
                                }


                                switch (alt20) {
                            	case 1 :
                            	    // EsperEPL2Grammar.g:1870:17: '0' .. '9'
                            	    {
                            	    matchRange('0','9'); if (state.failed) return ;

                            	    }
                            	    break;

                            	default :
                            	    break loop20;
                                }
                            } while (true);

                            if ( state.backtracking==0 ) {
                              isDecimal=true;
                            }

                            }
                            break;

                    }

                    // EsperEPL2Grammar.g:1872:3: ( ( 'l' ) | {...}? ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX ) )?
                    int alt27=3;
                    int LA27_0 = input.LA(1);

                    if ( (LA27_0=='l') ) {
                        alt27=1;
                    }
                    else if ( (LA27_0=='.'||(LA27_0>='d' && LA27_0<='f')) ) {
                        alt27=2;
                    }
                    switch (alt27) {
                        case 1 :
                            // EsperEPL2Grammar.g:1872:5: ( 'l' )
                            {
                            // EsperEPL2Grammar.g:1872:5: ( 'l' )
                            // EsperEPL2Grammar.g:1872:6: 'l'
                            {
                            match('l'); if (state.failed) return ;

                            }

                            if ( state.backtracking==0 ) {
                               _type = NUM_LONG; 
                            }

                            }
                            break;
                        case 2 :
                            // EsperEPL2Grammar.g:1875:5: {...}? ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX )
                            {
                            if ( !((isDecimal)) ) {
                                if (state.backtracking>0) {state.failed=true; return ;}
                                throw new FailedPredicateException(input, "NUM_INT", "isDecimal");
                            }
                            // EsperEPL2Grammar.g:1876:13: ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX )
                            int alt26=3;
                            switch ( input.LA(1) ) {
                            case '.':
                                {
                                alt26=1;
                                }
                                break;
                            case 'e':
                                {
                                alt26=2;
                                }
                                break;
                            case 'd':
                            case 'f':
                                {
                                alt26=3;
                                }
                                break;
                            default:
                                if (state.backtracking>0) {state.failed=true; return ;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 26, 0, input);

                                throw nvae;
                            }

                            switch (alt26) {
                                case 1 :
                                    // EsperEPL2Grammar.g:1876:17: '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )?
                                    {
                                    match('.'); if (state.failed) return ;
                                    // EsperEPL2Grammar.g:1876:21: ( '0' .. '9' )*
                                    loop22:
                                    do {
                                        int alt22=2;
                                        int LA22_0 = input.LA(1);

                                        if ( ((LA22_0>='0' && LA22_0<='9')) ) {
                                            alt22=1;
                                        }


                                        switch (alt22) {
                                    	case 1 :
                                    	    // EsperEPL2Grammar.g:1876:22: '0' .. '9'
                                    	    {
                                    	    matchRange('0','9'); if (state.failed) return ;

                                    	    }
                                    	    break;

                                    	default :
                                    	    break loop22;
                                        }
                                    } while (true);

                                    // EsperEPL2Grammar.g:1876:33: ( EXPONENT )?
                                    int alt23=2;
                                    int LA23_0 = input.LA(1);

                                    if ( (LA23_0=='e') ) {
                                        alt23=1;
                                    }
                                    switch (alt23) {
                                        case 1 :
                                            // EsperEPL2Grammar.g:1876:34: EXPONENT
                                            {
                                            mEXPONENT(); if (state.failed) return ;

                                            }
                                            break;

                                    }

                                    // EsperEPL2Grammar.g:1876:45: (f2= FLOAT_SUFFIX )?
                                    int alt24=2;
                                    int LA24_0 = input.LA(1);

                                    if ( (LA24_0=='d'||LA24_0=='f') ) {
                                        alt24=1;
                                    }
                                    switch (alt24) {
                                        case 1 :
                                            // EsperEPL2Grammar.g:1876:46: f2= FLOAT_SUFFIX
                                            {
                                            int f2Start2197 = getCharIndex();
                                            mFLOAT_SUFFIX(); if (state.failed) return ;
                                            f2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, f2Start2197, getCharIndex()-1);
                                            if ( state.backtracking==0 ) {
                                              t=f2;
                                            }

                                            }
                                            break;

                                    }


                                    }
                                    break;
                                case 2 :
                                    // EsperEPL2Grammar.g:1877:17: EXPONENT (f3= FLOAT_SUFFIX )?
                                    {
                                    mEXPONENT(); if (state.failed) return ;
                                    // EsperEPL2Grammar.g:1877:26: (f3= FLOAT_SUFFIX )?
                                    int alt25=2;
                                    int LA25_0 = input.LA(1);

                                    if ( (LA25_0=='d'||LA25_0=='f') ) {
                                        alt25=1;
                                    }
                                    switch (alt25) {
                                        case 1 :
                                            // EsperEPL2Grammar.g:1877:27: f3= FLOAT_SUFFIX
                                            {
                                            int f3Start2224 = getCharIndex();
                                            mFLOAT_SUFFIX(); if (state.failed) return ;
                                            f3 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, f3Start2224, getCharIndex()-1);
                                            if ( state.backtracking==0 ) {
                                              t=f3;
                                            }

                                            }
                                            break;

                                    }


                                    }
                                    break;
                                case 3 :
                                    // EsperEPL2Grammar.g:1878:17: f4= FLOAT_SUFFIX
                                    {
                                    int f4Start2248 = getCharIndex();
                                    mFLOAT_SUFFIX(); if (state.failed) return ;
                                    f4 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, f4Start2248, getCharIndex()-1);
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
            // EsperEPL2Grammar.g:1895:2: ( ( 'e' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // EsperEPL2Grammar.g:1895:4: ( 'e' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            // EsperEPL2Grammar.g:1895:4: ( 'e' )
            // EsperEPL2Grammar.g:1895:5: 'e'
            {
            match('e'); if (state.failed) return ;

            }

            // EsperEPL2Grammar.g:1895:10: ( '+' | '-' )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0=='+'||LA29_0=='-') ) {
                alt29=1;
            }
            switch (alt29) {
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

            // EsperEPL2Grammar.g:1895:21: ( '0' .. '9' )+
            int cnt30=0;
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( ((LA30_0>='0' && LA30_0<='9')) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // EsperEPL2Grammar.g:1895:22: '0' .. '9'
            	    {
            	    matchRange('0','9'); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt30 >= 1 ) break loop30;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(30, input);
                        throw eee;
                }
                cnt30++;
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
            // EsperEPL2Grammar.g:1901:2: ( 'f' | 'd' )
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
        // EsperEPL2Grammar.g:1:8: ( CREATE | WINDOW | IN_SET | BETWEEN | LIKE | REGEXP | ESCAPE | OR_EXPR | AND_EXPR | NOT_EXPR | EVERY_EXPR | EVERY_DISTINCT_EXPR | WHERE | AS | SUM | AVG | MAX | MIN | COALESCE | MEDIAN | STDDEV | AVEDEV | COUNT | SELECT | CASE | ELSE | WHEN | THEN | END | FROM | OUTER | INNER | JOIN | LEFT | RIGHT | FULL | ON | IS | BY | GROUP | HAVING | DISTINCT | ALL | ANY | SOME | OUTPUT | EVENTS | FIRST | LAST | INSERT | INTO | ORDER | ASC | DESC | RSTREAM | ISTREAM | IRSTREAM | SCHEMA | UNIDIRECTIONAL | RETAINUNION | RETAININTERSECTION | PATTERN | SQL | METADATASQL | PREVIOUS | PRIOR | EXISTS | WEEKDAY | LW | INSTANCEOF | CAST | CURRENT_TIMESTAMP | DELETE | SNAPSHOT | SET | VARIABLE | UNTIL | AT | INDEX | TIMEPERIOD_DAY | TIMEPERIOD_DAYS | TIMEPERIOD_HOUR | TIMEPERIOD_HOURS | TIMEPERIOD_MINUTE | TIMEPERIOD_MINUTES | TIMEPERIOD_SEC | TIMEPERIOD_SECOND | TIMEPERIOD_SECONDS | TIMEPERIOD_MILLISEC | TIMEPERIOD_MILLISECOND | TIMEPERIOD_MILLISECONDS | BOOLEAN_TRUE | BOOLEAN_FALSE | VALUE_NULL | ROW_LIMIT_EXPR | OFFSET | UPDATE | MATCH_RECOGNIZE | MEASURES | DEFINE | PARTITION | MATCHES | AFTER | FOLLOWED_BY | EQUALS | SQL_NE | QUESTION | LPAREN | RPAREN | LBRACK | RBRACK | LCURLY | RCURLY | COLON | COMMA | EQUAL | LNOT | BNOT | NOT_EQUAL | DIV | DIV_ASSIGN | PLUS | PLUS_ASSIGN | INC | MINUS | MINUS_ASSIGN | DEC | STAR | STAR_ASSIGN | MOD | MOD_ASSIGN | SR | SR_ASSIGN | BSR | BSR_ASSIGN | GE | GT | SL | SL_ASSIGN | LE | LT | BXOR | BXOR_ASSIGN | BOR | BOR_ASSIGN | LOR | BAND | BAND_ASSIGN | LAND | SEMI | DOT | NUM_LONG | NUM_DOUBLE | NUM_FLOAT | ESCAPECHAR | EMAILAT | WS | SL_COMMENT | ML_COMMENT | TICKED_STRING_LITERAL | QUOTED_STRING_LITERAL | STRING_LITERAL | IDENT | NUM_INT )
        int alt31=164;
        alt31 = dfa31.predict(input);
        switch (alt31) {
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
                // EsperEPL2Grammar.g:1:95: EVERY_DISTINCT_EXPR
                {
                mEVERY_DISTINCT_EXPR(); if (state.failed) return ;

                }
                break;
            case 13 :
                // EsperEPL2Grammar.g:1:115: WHERE
                {
                mWHERE(); if (state.failed) return ;

                }
                break;
            case 14 :
                // EsperEPL2Grammar.g:1:121: AS
                {
                mAS(); if (state.failed) return ;

                }
                break;
            case 15 :
                // EsperEPL2Grammar.g:1:124: SUM
                {
                mSUM(); if (state.failed) return ;

                }
                break;
            case 16 :
                // EsperEPL2Grammar.g:1:128: AVG
                {
                mAVG(); if (state.failed) return ;

                }
                break;
            case 17 :
                // EsperEPL2Grammar.g:1:132: MAX
                {
                mMAX(); if (state.failed) return ;

                }
                break;
            case 18 :
                // EsperEPL2Grammar.g:1:136: MIN
                {
                mMIN(); if (state.failed) return ;

                }
                break;
            case 19 :
                // EsperEPL2Grammar.g:1:140: COALESCE
                {
                mCOALESCE(); if (state.failed) return ;

                }
                break;
            case 20 :
                // EsperEPL2Grammar.g:1:149: MEDIAN
                {
                mMEDIAN(); if (state.failed) return ;

                }
                break;
            case 21 :
                // EsperEPL2Grammar.g:1:156: STDDEV
                {
                mSTDDEV(); if (state.failed) return ;

                }
                break;
            case 22 :
                // EsperEPL2Grammar.g:1:163: AVEDEV
                {
                mAVEDEV(); if (state.failed) return ;

                }
                break;
            case 23 :
                // EsperEPL2Grammar.g:1:170: COUNT
                {
                mCOUNT(); if (state.failed) return ;

                }
                break;
            case 24 :
                // EsperEPL2Grammar.g:1:176: SELECT
                {
                mSELECT(); if (state.failed) return ;

                }
                break;
            case 25 :
                // EsperEPL2Grammar.g:1:183: CASE
                {
                mCASE(); if (state.failed) return ;

                }
                break;
            case 26 :
                // EsperEPL2Grammar.g:1:188: ELSE
                {
                mELSE(); if (state.failed) return ;

                }
                break;
            case 27 :
                // EsperEPL2Grammar.g:1:193: WHEN
                {
                mWHEN(); if (state.failed) return ;

                }
                break;
            case 28 :
                // EsperEPL2Grammar.g:1:198: THEN
                {
                mTHEN(); if (state.failed) return ;

                }
                break;
            case 29 :
                // EsperEPL2Grammar.g:1:203: END
                {
                mEND(); if (state.failed) return ;

                }
                break;
            case 30 :
                // EsperEPL2Grammar.g:1:207: FROM
                {
                mFROM(); if (state.failed) return ;

                }
                break;
            case 31 :
                // EsperEPL2Grammar.g:1:212: OUTER
                {
                mOUTER(); if (state.failed) return ;

                }
                break;
            case 32 :
                // EsperEPL2Grammar.g:1:218: INNER
                {
                mINNER(); if (state.failed) return ;

                }
                break;
            case 33 :
                // EsperEPL2Grammar.g:1:224: JOIN
                {
                mJOIN(); if (state.failed) return ;

                }
                break;
            case 34 :
                // EsperEPL2Grammar.g:1:229: LEFT
                {
                mLEFT(); if (state.failed) return ;

                }
                break;
            case 35 :
                // EsperEPL2Grammar.g:1:234: RIGHT
                {
                mRIGHT(); if (state.failed) return ;

                }
                break;
            case 36 :
                // EsperEPL2Grammar.g:1:240: FULL
                {
                mFULL(); if (state.failed) return ;

                }
                break;
            case 37 :
                // EsperEPL2Grammar.g:1:245: ON
                {
                mON(); if (state.failed) return ;

                }
                break;
            case 38 :
                // EsperEPL2Grammar.g:1:248: IS
                {
                mIS(); if (state.failed) return ;

                }
                break;
            case 39 :
                // EsperEPL2Grammar.g:1:251: BY
                {
                mBY(); if (state.failed) return ;

                }
                break;
            case 40 :
                // EsperEPL2Grammar.g:1:254: GROUP
                {
                mGROUP(); if (state.failed) return ;

                }
                break;
            case 41 :
                // EsperEPL2Grammar.g:1:260: HAVING
                {
                mHAVING(); if (state.failed) return ;

                }
                break;
            case 42 :
                // EsperEPL2Grammar.g:1:267: DISTINCT
                {
                mDISTINCT(); if (state.failed) return ;

                }
                break;
            case 43 :
                // EsperEPL2Grammar.g:1:276: ALL
                {
                mALL(); if (state.failed) return ;

                }
                break;
            case 44 :
                // EsperEPL2Grammar.g:1:280: ANY
                {
                mANY(); if (state.failed) return ;

                }
                break;
            case 45 :
                // EsperEPL2Grammar.g:1:284: SOME
                {
                mSOME(); if (state.failed) return ;

                }
                break;
            case 46 :
                // EsperEPL2Grammar.g:1:289: OUTPUT
                {
                mOUTPUT(); if (state.failed) return ;

                }
                break;
            case 47 :
                // EsperEPL2Grammar.g:1:296: EVENTS
                {
                mEVENTS(); if (state.failed) return ;

                }
                break;
            case 48 :
                // EsperEPL2Grammar.g:1:303: FIRST
                {
                mFIRST(); if (state.failed) return ;

                }
                break;
            case 49 :
                // EsperEPL2Grammar.g:1:309: LAST
                {
                mLAST(); if (state.failed) return ;

                }
                break;
            case 50 :
                // EsperEPL2Grammar.g:1:314: INSERT
                {
                mINSERT(); if (state.failed) return ;

                }
                break;
            case 51 :
                // EsperEPL2Grammar.g:1:321: INTO
                {
                mINTO(); if (state.failed) return ;

                }
                break;
            case 52 :
                // EsperEPL2Grammar.g:1:326: ORDER
                {
                mORDER(); if (state.failed) return ;

                }
                break;
            case 53 :
                // EsperEPL2Grammar.g:1:332: ASC
                {
                mASC(); if (state.failed) return ;

                }
                break;
            case 54 :
                // EsperEPL2Grammar.g:1:336: DESC
                {
                mDESC(); if (state.failed) return ;

                }
                break;
            case 55 :
                // EsperEPL2Grammar.g:1:341: RSTREAM
                {
                mRSTREAM(); if (state.failed) return ;

                }
                break;
            case 56 :
                // EsperEPL2Grammar.g:1:349: ISTREAM
                {
                mISTREAM(); if (state.failed) return ;

                }
                break;
            case 57 :
                // EsperEPL2Grammar.g:1:357: IRSTREAM
                {
                mIRSTREAM(); if (state.failed) return ;

                }
                break;
            case 58 :
                // EsperEPL2Grammar.g:1:366: SCHEMA
                {
                mSCHEMA(); if (state.failed) return ;

                }
                break;
            case 59 :
                // EsperEPL2Grammar.g:1:373: UNIDIRECTIONAL
                {
                mUNIDIRECTIONAL(); if (state.failed) return ;

                }
                break;
            case 60 :
                // EsperEPL2Grammar.g:1:388: RETAINUNION
                {
                mRETAINUNION(); if (state.failed) return ;

                }
                break;
            case 61 :
                // EsperEPL2Grammar.g:1:400: RETAININTERSECTION
                {
                mRETAININTERSECTION(); if (state.failed) return ;

                }
                break;
            case 62 :
                // EsperEPL2Grammar.g:1:419: PATTERN
                {
                mPATTERN(); if (state.failed) return ;

                }
                break;
            case 63 :
                // EsperEPL2Grammar.g:1:427: SQL
                {
                mSQL(); if (state.failed) return ;

                }
                break;
            case 64 :
                // EsperEPL2Grammar.g:1:431: METADATASQL
                {
                mMETADATASQL(); if (state.failed) return ;

                }
                break;
            case 65 :
                // EsperEPL2Grammar.g:1:443: PREVIOUS
                {
                mPREVIOUS(); if (state.failed) return ;

                }
                break;
            case 66 :
                // EsperEPL2Grammar.g:1:452: PRIOR
                {
                mPRIOR(); if (state.failed) return ;

                }
                break;
            case 67 :
                // EsperEPL2Grammar.g:1:458: EXISTS
                {
                mEXISTS(); if (state.failed) return ;

                }
                break;
            case 68 :
                // EsperEPL2Grammar.g:1:465: WEEKDAY
                {
                mWEEKDAY(); if (state.failed) return ;

                }
                break;
            case 69 :
                // EsperEPL2Grammar.g:1:473: LW
                {
                mLW(); if (state.failed) return ;

                }
                break;
            case 70 :
                // EsperEPL2Grammar.g:1:476: INSTANCEOF
                {
                mINSTANCEOF(); if (state.failed) return ;

                }
                break;
            case 71 :
                // EsperEPL2Grammar.g:1:487: CAST
                {
                mCAST(); if (state.failed) return ;

                }
                break;
            case 72 :
                // EsperEPL2Grammar.g:1:492: CURRENT_TIMESTAMP
                {
                mCURRENT_TIMESTAMP(); if (state.failed) return ;

                }
                break;
            case 73 :
                // EsperEPL2Grammar.g:1:510: DELETE
                {
                mDELETE(); if (state.failed) return ;

                }
                break;
            case 74 :
                // EsperEPL2Grammar.g:1:517: SNAPSHOT
                {
                mSNAPSHOT(); if (state.failed) return ;

                }
                break;
            case 75 :
                // EsperEPL2Grammar.g:1:526: SET
                {
                mSET(); if (state.failed) return ;

                }
                break;
            case 76 :
                // EsperEPL2Grammar.g:1:530: VARIABLE
                {
                mVARIABLE(); if (state.failed) return ;

                }
                break;
            case 77 :
                // EsperEPL2Grammar.g:1:539: UNTIL
                {
                mUNTIL(); if (state.failed) return ;

                }
                break;
            case 78 :
                // EsperEPL2Grammar.g:1:545: AT
                {
                mAT(); if (state.failed) return ;

                }
                break;
            case 79 :
                // EsperEPL2Grammar.g:1:548: INDEX
                {
                mINDEX(); if (state.failed) return ;

                }
                break;
            case 80 :
                // EsperEPL2Grammar.g:1:554: TIMEPERIOD_DAY
                {
                mTIMEPERIOD_DAY(); if (state.failed) return ;

                }
                break;
            case 81 :
                // EsperEPL2Grammar.g:1:569: TIMEPERIOD_DAYS
                {
                mTIMEPERIOD_DAYS(); if (state.failed) return ;

                }
                break;
            case 82 :
                // EsperEPL2Grammar.g:1:585: TIMEPERIOD_HOUR
                {
                mTIMEPERIOD_HOUR(); if (state.failed) return ;

                }
                break;
            case 83 :
                // EsperEPL2Grammar.g:1:601: TIMEPERIOD_HOURS
                {
                mTIMEPERIOD_HOURS(); if (state.failed) return ;

                }
                break;
            case 84 :
                // EsperEPL2Grammar.g:1:618: TIMEPERIOD_MINUTE
                {
                mTIMEPERIOD_MINUTE(); if (state.failed) return ;

                }
                break;
            case 85 :
                // EsperEPL2Grammar.g:1:636: TIMEPERIOD_MINUTES
                {
                mTIMEPERIOD_MINUTES(); if (state.failed) return ;

                }
                break;
            case 86 :
                // EsperEPL2Grammar.g:1:655: TIMEPERIOD_SEC
                {
                mTIMEPERIOD_SEC(); if (state.failed) return ;

                }
                break;
            case 87 :
                // EsperEPL2Grammar.g:1:670: TIMEPERIOD_SECOND
                {
                mTIMEPERIOD_SECOND(); if (state.failed) return ;

                }
                break;
            case 88 :
                // EsperEPL2Grammar.g:1:688: TIMEPERIOD_SECONDS
                {
                mTIMEPERIOD_SECONDS(); if (state.failed) return ;

                }
                break;
            case 89 :
                // EsperEPL2Grammar.g:1:707: TIMEPERIOD_MILLISEC
                {
                mTIMEPERIOD_MILLISEC(); if (state.failed) return ;

                }
                break;
            case 90 :
                // EsperEPL2Grammar.g:1:727: TIMEPERIOD_MILLISECOND
                {
                mTIMEPERIOD_MILLISECOND(); if (state.failed) return ;

                }
                break;
            case 91 :
                // EsperEPL2Grammar.g:1:750: TIMEPERIOD_MILLISECONDS
                {
                mTIMEPERIOD_MILLISECONDS(); if (state.failed) return ;

                }
                break;
            case 92 :
                // EsperEPL2Grammar.g:1:774: BOOLEAN_TRUE
                {
                mBOOLEAN_TRUE(); if (state.failed) return ;

                }
                break;
            case 93 :
                // EsperEPL2Grammar.g:1:787: BOOLEAN_FALSE
                {
                mBOOLEAN_FALSE(); if (state.failed) return ;

                }
                break;
            case 94 :
                // EsperEPL2Grammar.g:1:801: VALUE_NULL
                {
                mVALUE_NULL(); if (state.failed) return ;

                }
                break;
            case 95 :
                // EsperEPL2Grammar.g:1:812: ROW_LIMIT_EXPR
                {
                mROW_LIMIT_EXPR(); if (state.failed) return ;

                }
                break;
            case 96 :
                // EsperEPL2Grammar.g:1:827: OFFSET
                {
                mOFFSET(); if (state.failed) return ;

                }
                break;
            case 97 :
                // EsperEPL2Grammar.g:1:834: UPDATE
                {
                mUPDATE(); if (state.failed) return ;

                }
                break;
            case 98 :
                // EsperEPL2Grammar.g:1:841: MATCH_RECOGNIZE
                {
                mMATCH_RECOGNIZE(); if (state.failed) return ;

                }
                break;
            case 99 :
                // EsperEPL2Grammar.g:1:857: MEASURES
                {
                mMEASURES(); if (state.failed) return ;

                }
                break;
            case 100 :
                // EsperEPL2Grammar.g:1:866: DEFINE
                {
                mDEFINE(); if (state.failed) return ;

                }
                break;
            case 101 :
                // EsperEPL2Grammar.g:1:873: PARTITION
                {
                mPARTITION(); if (state.failed) return ;

                }
                break;
            case 102 :
                // EsperEPL2Grammar.g:1:883: MATCHES
                {
                mMATCHES(); if (state.failed) return ;

                }
                break;
            case 103 :
                // EsperEPL2Grammar.g:1:891: AFTER
                {
                mAFTER(); if (state.failed) return ;

                }
                break;
            case 104 :
                // EsperEPL2Grammar.g:1:897: FOLLOWED_BY
                {
                mFOLLOWED_BY(); if (state.failed) return ;

                }
                break;
            case 105 :
                // EsperEPL2Grammar.g:1:909: EQUALS
                {
                mEQUALS(); if (state.failed) return ;

                }
                break;
            case 106 :
                // EsperEPL2Grammar.g:1:916: SQL_NE
                {
                mSQL_NE(); if (state.failed) return ;

                }
                break;
            case 107 :
                // EsperEPL2Grammar.g:1:923: QUESTION
                {
                mQUESTION(); if (state.failed) return ;

                }
                break;
            case 108 :
                // EsperEPL2Grammar.g:1:932: LPAREN
                {
                mLPAREN(); if (state.failed) return ;

                }
                break;
            case 109 :
                // EsperEPL2Grammar.g:1:939: RPAREN
                {
                mRPAREN(); if (state.failed) return ;

                }
                break;
            case 110 :
                // EsperEPL2Grammar.g:1:946: LBRACK
                {
                mLBRACK(); if (state.failed) return ;

                }
                break;
            case 111 :
                // EsperEPL2Grammar.g:1:953: RBRACK
                {
                mRBRACK(); if (state.failed) return ;

                }
                break;
            case 112 :
                // EsperEPL2Grammar.g:1:960: LCURLY
                {
                mLCURLY(); if (state.failed) return ;

                }
                break;
            case 113 :
                // EsperEPL2Grammar.g:1:967: RCURLY
                {
                mRCURLY(); if (state.failed) return ;

                }
                break;
            case 114 :
                // EsperEPL2Grammar.g:1:974: COLON
                {
                mCOLON(); if (state.failed) return ;

                }
                break;
            case 115 :
                // EsperEPL2Grammar.g:1:980: COMMA
                {
                mCOMMA(); if (state.failed) return ;

                }
                break;
            case 116 :
                // EsperEPL2Grammar.g:1:986: EQUAL
                {
                mEQUAL(); if (state.failed) return ;

                }
                break;
            case 117 :
                // EsperEPL2Grammar.g:1:992: LNOT
                {
                mLNOT(); if (state.failed) return ;

                }
                break;
            case 118 :
                // EsperEPL2Grammar.g:1:997: BNOT
                {
                mBNOT(); if (state.failed) return ;

                }
                break;
            case 119 :
                // EsperEPL2Grammar.g:1:1002: NOT_EQUAL
                {
                mNOT_EQUAL(); if (state.failed) return ;

                }
                break;
            case 120 :
                // EsperEPL2Grammar.g:1:1012: DIV
                {
                mDIV(); if (state.failed) return ;

                }
                break;
            case 121 :
                // EsperEPL2Grammar.g:1:1016: DIV_ASSIGN
                {
                mDIV_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 122 :
                // EsperEPL2Grammar.g:1:1027: PLUS
                {
                mPLUS(); if (state.failed) return ;

                }
                break;
            case 123 :
                // EsperEPL2Grammar.g:1:1032: PLUS_ASSIGN
                {
                mPLUS_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 124 :
                // EsperEPL2Grammar.g:1:1044: INC
                {
                mINC(); if (state.failed) return ;

                }
                break;
            case 125 :
                // EsperEPL2Grammar.g:1:1048: MINUS
                {
                mMINUS(); if (state.failed) return ;

                }
                break;
            case 126 :
                // EsperEPL2Grammar.g:1:1054: MINUS_ASSIGN
                {
                mMINUS_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 127 :
                // EsperEPL2Grammar.g:1:1067: DEC
                {
                mDEC(); if (state.failed) return ;

                }
                break;
            case 128 :
                // EsperEPL2Grammar.g:1:1071: STAR
                {
                mSTAR(); if (state.failed) return ;

                }
                break;
            case 129 :
                // EsperEPL2Grammar.g:1:1076: STAR_ASSIGN
                {
                mSTAR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 130 :
                // EsperEPL2Grammar.g:1:1088: MOD
                {
                mMOD(); if (state.failed) return ;

                }
                break;
            case 131 :
                // EsperEPL2Grammar.g:1:1092: MOD_ASSIGN
                {
                mMOD_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 132 :
                // EsperEPL2Grammar.g:1:1103: SR
                {
                mSR(); if (state.failed) return ;

                }
                break;
            case 133 :
                // EsperEPL2Grammar.g:1:1106: SR_ASSIGN
                {
                mSR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 134 :
                // EsperEPL2Grammar.g:1:1116: BSR
                {
                mBSR(); if (state.failed) return ;

                }
                break;
            case 135 :
                // EsperEPL2Grammar.g:1:1120: BSR_ASSIGN
                {
                mBSR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 136 :
                // EsperEPL2Grammar.g:1:1131: GE
                {
                mGE(); if (state.failed) return ;

                }
                break;
            case 137 :
                // EsperEPL2Grammar.g:1:1134: GT
                {
                mGT(); if (state.failed) return ;

                }
                break;
            case 138 :
                // EsperEPL2Grammar.g:1:1137: SL
                {
                mSL(); if (state.failed) return ;

                }
                break;
            case 139 :
                // EsperEPL2Grammar.g:1:1140: SL_ASSIGN
                {
                mSL_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 140 :
                // EsperEPL2Grammar.g:1:1150: LE
                {
                mLE(); if (state.failed) return ;

                }
                break;
            case 141 :
                // EsperEPL2Grammar.g:1:1153: LT
                {
                mLT(); if (state.failed) return ;

                }
                break;
            case 142 :
                // EsperEPL2Grammar.g:1:1156: BXOR
                {
                mBXOR(); if (state.failed) return ;

                }
                break;
            case 143 :
                // EsperEPL2Grammar.g:1:1161: BXOR_ASSIGN
                {
                mBXOR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 144 :
                // EsperEPL2Grammar.g:1:1173: BOR
                {
                mBOR(); if (state.failed) return ;

                }
                break;
            case 145 :
                // EsperEPL2Grammar.g:1:1177: BOR_ASSIGN
                {
                mBOR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 146 :
                // EsperEPL2Grammar.g:1:1188: LOR
                {
                mLOR(); if (state.failed) return ;

                }
                break;
            case 147 :
                // EsperEPL2Grammar.g:1:1192: BAND
                {
                mBAND(); if (state.failed) return ;

                }
                break;
            case 148 :
                // EsperEPL2Grammar.g:1:1197: BAND_ASSIGN
                {
                mBAND_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 149 :
                // EsperEPL2Grammar.g:1:1209: LAND
                {
                mLAND(); if (state.failed) return ;

                }
                break;
            case 150 :
                // EsperEPL2Grammar.g:1:1214: SEMI
                {
                mSEMI(); if (state.failed) return ;

                }
                break;
            case 151 :
                // EsperEPL2Grammar.g:1:1219: DOT
                {
                mDOT(); if (state.failed) return ;

                }
                break;
            case 152 :
                // EsperEPL2Grammar.g:1:1223: NUM_LONG
                {
                mNUM_LONG(); if (state.failed) return ;

                }
                break;
            case 153 :
                // EsperEPL2Grammar.g:1:1232: NUM_DOUBLE
                {
                mNUM_DOUBLE(); if (state.failed) return ;

                }
                break;
            case 154 :
                // EsperEPL2Grammar.g:1:1243: NUM_FLOAT
                {
                mNUM_FLOAT(); if (state.failed) return ;

                }
                break;
            case 155 :
                // EsperEPL2Grammar.g:1:1253: ESCAPECHAR
                {
                mESCAPECHAR(); if (state.failed) return ;

                }
                break;
            case 156 :
                // EsperEPL2Grammar.g:1:1264: EMAILAT
                {
                mEMAILAT(); if (state.failed) return ;

                }
                break;
            case 157 :
                // EsperEPL2Grammar.g:1:1272: WS
                {
                mWS(); if (state.failed) return ;

                }
                break;
            case 158 :
                // EsperEPL2Grammar.g:1:1275: SL_COMMENT
                {
                mSL_COMMENT(); if (state.failed) return ;

                }
                break;
            case 159 :
                // EsperEPL2Grammar.g:1:1286: ML_COMMENT
                {
                mML_COMMENT(); if (state.failed) return ;

                }
                break;
            case 160 :
                // EsperEPL2Grammar.g:1:1297: TICKED_STRING_LITERAL
                {
                mTICKED_STRING_LITERAL(); if (state.failed) return ;

                }
                break;
            case 161 :
                // EsperEPL2Grammar.g:1:1319: QUOTED_STRING_LITERAL
                {
                mQUOTED_STRING_LITERAL(); if (state.failed) return ;

                }
                break;
            case 162 :
                // EsperEPL2Grammar.g:1:1341: STRING_LITERAL
                {
                mSTRING_LITERAL(); if (state.failed) return ;

                }
                break;
            case 163 :
                // EsperEPL2Grammar.g:1:1356: IDENT
                {
                mIDENT(); if (state.failed) return ;

                }
                break;
            case 164 :
                // EsperEPL2Grammar.g:1:1362: NUM_INT
                {
                mNUM_INT(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred1_EsperEPL2Grammar
    public final void synpred1_EsperEPL2Grammar_fragment() throws RecognitionException {   
        // EsperEPL2Grammar.g:1866:5: ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )
        // EsperEPL2Grammar.g:1866:6: ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX )
        {
        // EsperEPL2Grammar.g:1866:6: ( '0' .. '9' )+
        int cnt32=0;
        loop32:
        do {
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( ((LA32_0>='0' && LA32_0<='9')) ) {
                alt32=1;
            }


            switch (alt32) {
        	case 1 :
        	    // EsperEPL2Grammar.g:1866:7: '0' .. '9'
        	    {
        	    matchRange('0','9'); if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    if ( cnt32 >= 1 ) break loop32;
        	    if (state.backtracking>0) {state.failed=true; return ;}
                    EarlyExitException eee =
                        new EarlyExitException(32, input);
                    throw eee;
            }
            cnt32++;
        } while (true);

        // EsperEPL2Grammar.g:1866:18: ( '.' | EXPONENT | FLOAT_SUFFIX )
        int alt33=3;
        switch ( input.LA(1) ) {
        case '.':
            {
            alt33=1;
            }
            break;
        case 'e':
            {
            alt33=2;
            }
            break;
        case 'd':
        case 'f':
            {
            alt33=3;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 33, 0, input);

            throw nvae;
        }

        switch (alt33) {
            case 1 :
                // EsperEPL2Grammar.g:1866:19: '.'
                {
                match('.'); if (state.failed) return ;

                }
                break;
            case 2 :
                // EsperEPL2Grammar.g:1866:23: EXPONENT
                {
                mEXPONENT(); if (state.failed) return ;

                }
                break;
            case 3 :
                // EsperEPL2Grammar.g:1866:32: FLOAT_SUFFIX
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


    protected DFA31 dfa31 = new DFA31(this);
    static final String DFA31_eotS =
        "\1\uffff\25\67\1\174\1\176\1\u0082\11\uffff\1\u0084\1\uffff\1\u0088"+
        "\1\u008b\1\u008d\1\u008f\1\u0092\1\u0094\1\u0097\1\u009a\1\uffff"+
        "\1\u009b\13\uffff\7\67\1\u00a8\1\u00aa\2\67\1\u00ad\13\67\1\u00bc"+
        "\1\67\1\u00be\2\67\1\u00c3\2\67\1\u00c7\40\67\7\uffff\1\u00f4\17"+
        "\uffff\1\u00f7\13\uffff\14\67\1\uffff\1\67\1\uffff\2\67\1\uffff"+
        "\13\67\1\u0116\2\67\1\uffff\1\67\1\uffff\1\67\1\u011c\1\u011d\1"+
        "\u011e\1\uffff\1\u011f\1\67\1\u0121\1\uffff\1\67\1\u0123\1\67\1"+
        "\u0125\2\67\1\u0128\1\u012a\2\67\1\u012d\1\67\1\u012f\1\67\1\u0132"+
        "\23\67\1\u0147\10\67\3\uffff\1\u0151\1\uffff\3\67\1\u0155\1\u0156"+
        "\3\67\1\u015a\4\67\1\u015f\4\67\1\u0164\1\67\1\u0166\1\u0168\7\67"+
        "\1\u0170\1\uffff\5\67\4\uffff\1\67\1\uffff\1\67\1\uffff\1\u0178"+
        "\1\uffff\2\67\1\uffff\1\67\1\uffff\1\u017c\1\67\1\uffff\1\67\1\uffff"+
        "\2\67\1\uffff\4\67\1\u0185\1\u0186\1\u0187\1\u0188\1\u0189\2\67"+
        "\1\u018c\2\67\1\u0190\1\67\1\u0192\2\67\1\u0195\1\uffff\5\67\1\u019b"+
        "\2\67\2\uffff\2\67\1\u01a0\2\uffff\2\67\1\u01a3\1\uffff\1\67\1\u01a5"+
        "\2\67\1\uffff\1\u01a8\3\67\1\uffff\1\u01ac\1\uffff\1\67\1\uffff"+
        "\2\67\1\u01b0\2\67\1\u01b4\1\67\1\uffff\1\67\1\u01b7\1\u01b8\3\67"+
        "\1\u01bc\1\uffff\3\67\1\uffff\10\67\5\uffff\1\u01c9\1\u01ca\1\uffff"+
        "\1\u01cb\1\67\1\u01cd\1\uffff\1\67\1\uffff\2\67\1\uffff\1\67\1\u01d2"+
        "\3\67\1\uffff\1\u01d6\1\67\1\u01d8\1\67\1\uffff\1\67\1\u01db\1\uffff"+
        "\1\67\1\uffff\1\u01dd\1\67\1\uffff\3\67\1\uffff\1\67\1\u01e3\1\67"+
        "\1\uffff\1\67\1\u01e6\2\uffff\1\u01e7\1\u01e8\2\uffff\1\u01e9\1"+
        "\u01ea\1\u01eb\1\uffff\1\u01ec\1\u01ed\1\u01ef\1\u01f0\3\67\1\u01f5"+
        "\1\67\1\u01f7\2\67\3\uffff\1\u01fa\1\uffff\1\67\1\u01fc\1\u01fd"+
        "\1\67\1\uffff\1\u01ff\2\67\1\uffff\1\67\1\uffff\2\67\1\uffff\1\u0205"+
        "\1\uffff\1\67\1\u0207\1\67\1\u0209\1\67\2\uffff\1\u020d\10\uffff"+
        "\1\u020e\2\uffff\2\67\1\u0211\1\u0212\1\uffff\1\67\1\uffff\2\67"+
        "\1\uffff\1\67\2\uffff\1\67\1\uffff\1\u0218\2\67\1\u021b\1\67\1\uffff"+
        "\1\67\1\uffff\1\u021e\1\uffff\1\67\4\uffff\1\u0220\1\67\2\uffff"+
        "\2\67\1\u0224\1\u0225\1\67\1\uffff\1\67\1\u0228\1\uffff\2\67\1\uffff"+
        "\1\67\1\uffff\3\67\2\uffff\1\67\1\u0230\1\uffff\1\67\1\u0232\5\67"+
        "\1\uffff\1\67\1\uffff\1\u0239\1\67\1\u023c\1\u023d\2\67\1\uffff"+
        "\1\67\1\u0241\2\uffff\3\67\1\uffff\3\67\1\u0248\1\67\1\u024a\1\uffff"+
        "\1\67\1\uffff\1\u024c\1\uffff";
    static final String DFA31_eofS =
        "\u024d\uffff";
    static final String DFA31_minS =
        "\1\11\1\141\1\145\1\156\1\145\1\141\1\145\1\154\2\146\1\157\1\143"+
        "\1\141\1\150\1\141\1\157\1\162\2\141\1\156\2\141\1\55\1\75\1\74"+
        "\11\uffff\1\75\1\uffff\1\52\1\53\5\75\1\46\1\uffff\1\60\13\uffff"+
        "\1\145\1\141\1\163\1\162\1\156\2\145\2\44\1\163\1\164\1\44\1\153"+
        "\1\146\1\163\2\147\1\164\1\143\1\145\1\163\1\144\1\151\1\44\1\164"+
        "\1\44\1\146\1\144\1\44\1\145\1\154\1\44\2\164\1\154\1\155\1\144"+
        "\1\143\1\155\1\150\1\154\1\141\1\164\1\154\1\141\2\145\1\165\1\157"+
        "\1\154\1\162\1\154\1\151\1\157\1\166\1\165\1\163\1\146\1\171\1\151"+
        "\1\144\1\162\1\145\1\162\7\uffff\1\75\17\uffff\1\75\13\uffff\1\141"+
        "\1\154\1\156\1\145\1\162\1\144\1\156\1\153\2\145\1\157\1\145\1\uffff"+
        "\1\162\1\uffff\1\164\1\167\1\uffff\1\145\1\151\2\164\1\145\1\141"+
        "\1\150\1\162\1\141\1\156\1\145\1\44\1\163\1\145\1\uffff\1\145\1"+
        "\uffff\1\163\3\44\1\uffff\1\44\1\144\1\44\1\uffff\1\145\1\44\1\154"+
        "\1\44\1\144\1\145\2\44\2\145\1\44\1\160\1\44\1\143\1\44\1\154\1"+
        "\151\1\141\1\163\1\143\1\156\1\145\1\155\1\154\2\163\1\156\1\165"+
        "\1\151\1\162\1\164\1\143\1\145\1\151\1\44\1\144\1\151\1\141\2\164"+
        "\1\166\1\157\1\151\3\uffff\1\75\1\uffff\1\164\1\145\1\164\2\44\1"+
        "\145\1\157\1\145\1\44\1\144\2\162\1\141\1\44\1\170\1\145\1\162\1"+
        "\145\1\44\1\164\2\44\1\170\1\151\1\164\1\145\1\160\1\171\1\164\1"+
        "\44\1\uffff\1\164\2\162\1\165\1\145\4\uffff\1\145\1\uffff\1\162"+
        "\1\uffff\1\44\1\uffff\1\145\1\143\1\uffff\1\156\1\uffff\1\44\1\155"+
        "\1\uffff\1\163\1\uffff\1\150\1\164\1\uffff\1\151\1\141\1\144\1\165"+
        "\5\44\1\164\1\145\1\44\1\160\1\156\1\44\1\151\1\44\1\164\1\156\1"+
        "\44\1\uffff\1\151\1\154\1\164\1\145\1\151\1\44\1\162\1\141\2\uffff"+
        "\1\145\1\163\1\44\2\uffff\1\156\1\167\1\44\1\uffff\1\141\1\44\1"+
        "\164\1\156\1\uffff\1\44\1\141\2\145\1\uffff\1\44\1\uffff\1\145\1"+
        "\uffff\1\160\1\156\1\44\1\141\1\145\1\44\1\163\1\uffff\1\163\2\44"+
        "\2\164\1\166\1\44\1\uffff\1\166\1\164\1\144\1\uffff\1\141\1\150"+
        "\1\137\1\145\1\163\1\156\1\141\1\162\5\uffff\2\44\1\uffff\1\44\1"+
        "\147\1\44\1\uffff\1\156\1\uffff\2\145\1\uffff\1\162\1\44\1\145\1"+
        "\162\1\164\1\uffff\1\44\1\142\1\44\1\143\1\uffff\1\164\1\44\1\uffff"+
        "\1\171\1\uffff\1\44\1\143\1\uffff\1\155\1\141\1\156\1\uffff\1\145"+
        "\1\44\1\55\1\uffff\1\155\1\44\2\uffff\2\44\2\uffff\3\44\1\uffff"+
        "\4\44\1\157\1\162\1\163\1\44\1\145\1\44\1\164\1\145\3\uffff\1\44"+
        "\1\uffff\1\143\2\44\1\145\1\uffff\1\44\1\156\1\151\1\uffff\1\154"+
        "\1\uffff\1\145\1\137\1\uffff\1\44\1\uffff\1\145\1\44\1\155\1\44"+
        "\1\153\1\uffff\1\151\1\44\10\uffff\1\44\2\uffff\1\164\1\145\2\44"+
        "\1\uffff\1\143\1\uffff\1\141\1\163\1\uffff\1\164\2\uffff\1\143\1"+
        "\uffff\1\44\1\157\1\145\1\44\1\164\1\uffff\1\157\1\uffff\1\44\1"+
        "\uffff\1\144\4\uffff\1\44\1\143\2\uffff\1\157\1\163\2\44\1\164\1"+
        "\uffff\1\156\1\44\1\uffff\1\151\1\146\1\uffff\1\141\1\uffff\1\157"+
        "\1\156\1\161\2\uffff\1\151\1\44\1\uffff\1\155\1\44\1\171\1\147\1"+
        "\144\1\154\1\157\1\uffff\1\145\1\uffff\1\44\1\156\2\44\1\156\1\163"+
        "\1\uffff\1\151\1\44\2\uffff\1\141\1\164\1\172\1\uffff\1\154\1\141"+
        "\1\145\1\44\1\155\1\44\1\uffff\1\160\1\uffff\1\44\1\uffff";
    static final String DFA31_maxS =
        "\1\u18ff\1\165\1\151\1\163\1\171\1\151\1\163\1\170\1\165\1\166"+
        "\2\165\1\163\1\162\1\165\1\157\1\162\1\157\1\151\1\160\1\162\1\141"+
        "\1\76\1\75\1\76\11\uffff\1\75\1\uffff\4\75\1\76\1\75\1\174\1\75"+
        "\1\uffff\1\71\13\uffff\1\145\1\165\1\163\1\162\1\156\2\145\2\172"+
        "\1\163\1\164\1\172\1\155\1\146\1\163\1\164\1\147\1\164\1\143\1\145"+
        "\1\163\1\144\1\151\1\172\1\164\1\172\1\146\1\171\1\172\1\147\1\154"+
        "\1\172\2\164\1\154\1\155\1\144\1\164\1\155\1\150\1\154\1\141\1\170"+
        "\1\156\1\164\2\145\1\165\1\157\1\154\1\162\1\154\1\151\1\157\1\166"+
        "\1\165\2\163\1\171\1\164\1\144\1\164\1\151\1\162\7\uffff\1\75\17"+
        "\uffff\1\76\13\uffff\1\141\1\154\1\156\1\164\1\162\1\144\1\162\1"+
        "\153\1\145\1\164\1\157\1\145\1\uffff\1\162\1\uffff\1\164\1\167\1"+
        "\uffff\1\145\1\151\2\164\1\145\1\141\1\150\1\162\1\141\1\162\1\145"+
        "\1\172\1\163\1\145\1\uffff\1\160\1\uffff\1\163\3\172\1\uffff\1\172"+
        "\1\144\1\172\1\uffff\1\145\1\172\1\154\1\172\1\144\1\145\2\172\2"+
        "\145\1\172\1\160\1\172\1\143\1\172\1\154\1\151\1\141\1\163\1\143"+
        "\1\156\1\145\1\155\1\154\2\163\1\156\1\165\1\151\1\162\1\164\1\143"+
        "\1\145\1\151\1\172\1\144\1\151\1\141\2\164\1\166\1\157\1\151\3\uffff"+
        "\1\75\1\uffff\1\164\1\145\1\164\2\172\1\145\1\157\1\145\1\172\1"+
        "\144\2\162\1\141\1\172\1\170\1\145\1\162\1\145\1\172\1\164\2\172"+
        "\1\170\1\151\1\164\1\145\1\160\1\171\1\164\1\172\1\uffff\1\164\2"+
        "\162\1\165\1\145\4\uffff\1\145\1\uffff\1\162\1\uffff\1\172\1\uffff"+
        "\1\145\1\143\1\uffff\1\156\1\uffff\1\172\1\155\1\uffff\1\163\1\uffff"+
        "\1\150\1\164\1\uffff\1\151\1\141\1\144\1\165\5\172\1\164\1\145\1"+
        "\172\1\160\1\156\1\172\1\151\1\172\1\164\1\156\1\172\1\uffff\1\151"+
        "\1\154\1\164\1\145\1\151\1\172\1\162\1\141\2\uffff\1\145\1\163\1"+
        "\172\2\uffff\1\156\1\167\1\172\1\uffff\1\141\1\172\1\164\1\156\1"+
        "\uffff\1\172\1\141\2\145\1\uffff\1\172\1\uffff\1\145\1\uffff\1\160"+
        "\1\156\1\172\1\141\1\145\1\172\1\163\1\uffff\1\163\2\172\2\164\1"+
        "\166\1\172\1\uffff\1\166\1\164\1\144\1\uffff\1\141\1\150\2\145\1"+
        "\163\1\156\1\141\1\162\5\uffff\2\172\1\uffff\1\172\1\147\1\172\1"+
        "\uffff\1\156\1\uffff\2\145\1\uffff\1\162\1\172\1\145\1\162\1\164"+
        "\1\uffff\1\172\1\142\1\172\1\143\1\uffff\1\164\1\172\1\uffff\1\171"+
        "\1\uffff\1\172\1\143\1\uffff\1\155\1\141\1\156\1\uffff\1\145\1\172"+
        "\1\55\1\uffff\1\155\1\172\2\uffff\2\172\2\uffff\3\172\1\uffff\4"+
        "\172\1\157\1\162\1\163\1\172\1\145\1\172\1\164\1\145\3\uffff\1\172"+
        "\1\uffff\1\143\2\172\1\145\1\uffff\1\172\1\156\1\151\1\uffff\1\154"+
        "\1\uffff\1\145\1\137\1\uffff\1\172\1\uffff\1\145\1\172\1\155\1\172"+
        "\1\153\1\uffff\1\165\1\172\10\uffff\1\172\2\uffff\1\164\1\145\2"+
        "\172\1\uffff\1\143\1\uffff\1\141\1\163\1\uffff\1\164\2\uffff\1\143"+
        "\1\uffff\1\172\1\157\1\145\1\172\1\164\1\uffff\1\157\1\uffff\1\172"+
        "\1\uffff\1\144\4\uffff\1\172\1\143\2\uffff\1\157\1\163\2\172\1\164"+
        "\1\uffff\1\156\1\172\1\uffff\1\151\1\146\1\uffff\1\141\1\uffff\1"+
        "\157\1\156\1\161\2\uffff\1\151\1\172\1\uffff\1\155\1\172\1\171\1"+
        "\147\1\144\1\154\1\157\1\uffff\1\145\1\uffff\1\172\1\156\2\172\1"+
        "\156\1\163\1\uffff\1\151\1\172\2\uffff\1\141\1\164\1\172\1\uffff"+
        "\1\154\1\141\1\145\1\172\1\155\1\172\1\uffff\1\160\1\uffff\1\172"+
        "\1\uffff";
    static final String DFA31_acceptS =
        "\31\uffff\1\153\1\154\1\155\1\156\1\157\1\160\1\161\1\162\1\163"+
        "\1\uffff\1\166\10\uffff\1\u0096\1\uffff\1\u0098\1\u0099\1\u009a"+
        "\1\u009b\1\u009c\1\u009d\1\u00a0\1\u00a1\1\u00a2\1\u00a3\1\u00a4"+
        "\100\uffff\1\150\1\176\1\177\1\175\1\164\1\151\1\152\1\uffff\1\u008c"+
        "\1\u008d\1\167\1\165\1\171\1\u009e\1\u009f\1\170\1\173\1\174\1\172"+
        "\1\u0081\1\u0080\1\u0083\1\u0082\1\uffff\1\u0088\1\u0089\1\u008f"+
        "\1\u008e\1\u0091\1\u0092\1\u0090\1\u0094\1\u0095\1\u0093\1\u0097"+
        "\14\uffff\1\3\1\uffff\1\46\2\uffff\1\47\16\uffff\1\10\1\uffff\1"+
        "\45\4\uffff\1\16\3\uffff\1\116\53\uffff\1\u008b\1\u008a\1\u0085"+
        "\1\uffff\1\u0084\36\uffff\1\35\5\uffff\1\11\1\54\1\65\1\20\1\uffff"+
        "\1\53\1\uffff\1\12\1\uffff\1\17\2\uffff\1\113\1\uffff\1\126\2\uffff"+
        "\1\77\1\uffff\1\21\2\uffff\1\22\24\uffff\1\120\10\uffff\1\u0087"+
        "\1\u0086\3\uffff\1\31\1\107\3\uffff\1\33\4\uffff\1\63\4\uffff\1"+
        "\5\1\uffff\1\42\1\uffff\1\61\7\uffff\1\32\7\uffff\1\136\3\uffff"+
        "\1\55\10\uffff\1\131\1\34\1\134\1\36\1\44\2\uffff\1\41\3\uffff\1"+
        "\122\1\uffff\1\66\2\uffff\1\121\5\uffff\1\101\4\uffff\1\27\2\uffff"+
        "\1\15\1\uffff\1\40\2\uffff\1\117\3\uffff\1\137\3\uffff\1\43\2\uffff"+
        "\1\14\1\13\2\uffff\1\64\1\37\3\uffff\1\147\14\uffff\1\60\1\135\1"+
        "\50\1\uffff\1\123\4\uffff\1\115\3\uffff\1\102\1\uffff\1\1\2\uffff"+
        "\1\2\1\uffff\1\62\5\uffff\1\6\2\uffff\1\7\1\57\1\103\1\56\1\140"+
        "\1\26\1\25\1\30\1\uffff\1\127\1\72\4\uffff\1\124\1\uffff\1\24\2"+
        "\uffff\1\51\1\uffff\1\111\1\144\1\uffff\1\141\5\uffff\1\104\1\uffff"+
        "\1\70\1\uffff\1\4\1\uffff\1\74\1\75\1\67\1\130\2\uffff\1\146\1\125"+
        "\5\uffff\1\76\2\uffff\1\23\2\uffff\1\71\1\uffff\1\112\3\uffff\1"+
        "\143\1\52\2\uffff\1\114\7\uffff\1\145\1\uffff\1\106\6\uffff\1\105"+
        "\2\uffff\1\132\1\100\3\uffff\1\133\6\uffff\1\73\1\uffff\1\142\1"+
        "\uffff\1\110";
    static final String DFA31_specialS =
        "\u024d\uffff}>";
    static final String[] DFA31_transitionS = {
            "\2\63\1\uffff\2\63\22\uffff\1\63\1\42\1\66\1\uffff\1\67\1\47"+
            "\1\53\1\65\1\32\1\33\1\46\1\45\1\41\1\26\1\55\1\44\12\70\1\40"+
            "\1\54\1\30\1\27\1\50\1\31\1\62\32\uffff\1\34\1\61\1\35\1\51"+
            "\1\67\1\64\1\11\1\4\1\1\1\22\1\7\1\16\1\20\1\21\1\3\1\17\1\67"+
            "\1\5\1\14\1\12\1\10\1\24\1\67\1\6\1\13\1\15\1\23\1\25\1\2\3"+
            "\67\1\36\1\52\1\37\1\43\u187e\uffff\1\60\1\57\1\56",
            "\1\73\15\uffff\1\72\2\uffff\1\71\2\uffff\1\74",
            "\1\77\2\uffff\1\76\1\75",
            "\1\100\3\uffff\1\102\1\101",
            "\1\103\23\uffff\1\104",
            "\1\107\3\uffff\1\106\3\uffff\1\105",
            "\1\110\3\uffff\1\111\11\uffff\1\112",
            "\1\115\1\uffff\1\116\4\uffff\1\113\2\uffff\1\114\1\uffff\1"+
            "\117",
            "\1\123\7\uffff\1\122\3\uffff\1\120\2\uffff\1\121",
            "\1\131\5\uffff\1\127\1\uffff\1\124\4\uffff\1\125\1\130\1\uffff"+
            "\1\126",
            "\1\132\5\uffff\1\133",
            "\1\140\1\uffff\1\136\10\uffff\1\142\1\137\1\uffff\1\141\2"+
            "\uffff\1\135\1\134",
            "\1\143\3\uffff\1\145\3\uffff\1\144\11\uffff\1\146",
            "\1\147\11\uffff\1\150",
            "\1\154\7\uffff\1\153\10\uffff\1\151\2\uffff\1\152",
            "\1\155",
            "\1\156",
            "\1\157\15\uffff\1\160",
            "\1\163\3\uffff\1\162\3\uffff\1\161",
            "\1\164\1\uffff\1\165",
            "\1\166\20\uffff\1\167",
            "\1\170",
            "\1\173\17\uffff\1\172\1\171",
            "\1\175",
            "\1\u0080\1\u0081\1\177",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u0083",
            "",
            "\1\u0087\4\uffff\1\u0086\15\uffff\1\u0085",
            "\1\u008a\21\uffff\1\u0089",
            "\1\u008c",
            "\1\u008e",
            "\1\u0091\1\u0090",
            "\1\u0093",
            "\1\u0095\76\uffff\1\u0096",
            "\1\u0099\26\uffff\1\u0098",
            "",
            "\12\70",
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
            "\1\u009c",
            "\1\u009d\23\uffff\1\u009e",
            "\1\u009f",
            "\1\u00a0",
            "\1\u00a1",
            "\1\u00a2",
            "\1\u00a3",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\3\67\1\u00a7\11"+
            "\67\1\u00a4\4\67\1\u00a5\1\u00a6\6\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\23\67\1\u00a9\6"+
            "\67",
            "\1\u00ab",
            "\1\u00ac",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u00ae\1\uffff\1\u00af",
            "\1\u00b0",
            "\1\u00b1",
            "\1\u00b2\14\uffff\1\u00b3",
            "\1\u00b4",
            "\1\u00b5",
            "\1\u00b6",
            "\1\u00b7",
            "\1\u00b8",
            "\1\u00b9",
            "\1\u00ba",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\3\67\1\u00bb\26"+
            "\67",
            "\1\u00bd",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u00bf",
            "\1\u00c0\24\uffff\1\u00c1",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\2\67\1\u00c2\27"+
            "\67",
            "\1\u00c5\1\uffff\1\u00c4",
            "\1\u00c6",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u00c8",
            "\1\u00c9",
            "\1\u00ca",
            "\1\u00cb",
            "\1\u00cc",
            "\1\u00cf\10\uffff\1\u00cd\7\uffff\1\u00ce",
            "\1\u00d0",
            "\1\u00d1",
            "\1\u00d2",
            "\1\u00d3",
            "\1\u00d5\3\uffff\1\u00d4",
            "\1\u00d7\1\uffff\1\u00d6",
            "\1\u00da\2\uffff\1\u00d8\17\uffff\1\u00d9",
            "\1\u00db",
            "\1\u00dc",
            "\1\u00dd",
            "\1\u00de",
            "\1\u00df",
            "\1\u00e0",
            "\1\u00e1",
            "\1\u00e2",
            "\1\u00e3",
            "\1\u00e4",
            "\1\u00e5",
            "\1\u00e6",
            "\1\u00e9\5\uffff\1\u00e8\6\uffff\1\u00e7",
            "\1\u00ea",
            "\1\u00eb\12\uffff\1\u00ec",
            "\1\u00ed",
            "\1\u00ef\1\uffff\1\u00ee",
            "\1\u00f0\3\uffff\1\u00f1",
            "\1\u00f2",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u00f3",
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
            "\1\u00f5\1\u00f6",
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
            "\1\u00f8",
            "\1\u00f9",
            "\1\u00fa",
            "\1\u00fb\16\uffff\1\u00fc",
            "\1\u00fd",
            "\1\u00fe",
            "\1\u0100\3\uffff\1\u00ff",
            "\1\u0101",
            "\1\u0102",
            "\1\u0103\16\uffff\1\u0104",
            "\1\u0105",
            "\1\u0106",
            "",
            "\1\u0107",
            "",
            "\1\u0108",
            "\1\u0109",
            "",
            "\1\u010a",
            "\1\u010b",
            "\1\u010c",
            "\1\u010d",
            "\1\u010e",
            "\1\u010f",
            "\1\u0110",
            "\1\u0111",
            "\1\u0112",
            "\1\u0114\3\uffff\1\u0113",
            "\1\u0115",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0117",
            "\1\u0118",
            "",
            "\1\u0119\12\uffff\1\u011a",
            "",
            "\1\u011b",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0120",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u0122",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0124",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0126",
            "\1\u0127",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\16\67\1\u0129\13"+
            "\67",
            "\1\u012b",
            "\1\u012c",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u012e",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0130",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\24\67\1\u0131\5"+
            "\67",
            "\1\u0133",
            "\1\u0134",
            "\1\u0135",
            "\1\u0136",
            "\1\u0137",
            "\1\u0138",
            "\1\u0139",
            "\1\u013a",
            "\1\u013b",
            "\1\u013c",
            "\1\u013d",
            "\1\u013e",
            "\1\u013f",
            "\1\u0140",
            "\1\u0141",
            "\1\u0142",
            "\1\u0143",
            "\1\u0144",
            "\1\u0145",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\22\67\1\u0146\7"+
            "\67",
            "\1\u0148",
            "\1\u0149",
            "\1\u014a",
            "\1\u014b",
            "\1\u014c",
            "\1\u014d",
            "\1\u014e",
            "\1\u014f",
            "",
            "",
            "",
            "\1\u0150",
            "",
            "\1\u0152",
            "\1\u0153",
            "\1\u0154",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0157",
            "\1\u0158",
            "\1\u0159",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u015b",
            "\1\u015c",
            "\1\u015d",
            "\1\u015e",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0160",
            "\1\u0161",
            "\1\u0162",
            "\1\u0163",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0165",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\26\67\1\u0167\3"+
            "\67",
            "\1\u0169",
            "\1\u016a",
            "\1\u016b",
            "\1\u016c",
            "\1\u016d",
            "\1\u016e",
            "\1\u016f",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u0171",
            "\1\u0172",
            "\1\u0173",
            "\1\u0174",
            "\1\u0175",
            "",
            "",
            "",
            "",
            "\1\u0176",
            "",
            "\1\u0177",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u0179",
            "\1\u017a",
            "",
            "\1\u017b",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u017d",
            "",
            "\1\u017e",
            "",
            "\1\u017f",
            "\1\u0180",
            "",
            "\1\u0181",
            "\1\u0182",
            "\1\u0183",
            "\1\u0184",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u018a",
            "\1\u018b",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u018d",
            "\1\u018e",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\22\67\1\u018f\7"+
            "\67",
            "\1\u0191",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0193",
            "\1\u0194",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u0196",
            "\1\u0197",
            "\1\u0198",
            "\1\u0199",
            "\1\u019a",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u019c",
            "\1\u019d",
            "",
            "",
            "\1\u019e",
            "\1\u019f",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "",
            "\1\u01a1",
            "\1\u01a2",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u01a4",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01a6",
            "\1\u01a7",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01a9",
            "\1\u01aa",
            "\1\u01ab",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u01ad",
            "",
            "\1\u01ae",
            "\1\u01af",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01b1",
            "\1\u01b2",
            "\1\67\10\uffff\1\u01b3\2\uffff\12\67\45\uffff\1\67\1\uffff"+
            "\32\67",
            "\1\u01b5",
            "",
            "\1\u01b6",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01b9",
            "\1\u01ba",
            "\1\u01bb",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u01bd",
            "\1\u01be",
            "\1\u01bf",
            "",
            "\1\u01c0",
            "\1\u01c1",
            "\1\u01c2\5\uffff\1\u01c3",
            "\1\u01c4",
            "\1\u01c5",
            "\1\u01c6",
            "\1\u01c7",
            "\1\u01c8",
            "",
            "",
            "",
            "",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01cc",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u01ce",
            "",
            "\1\u01cf",
            "\1\u01d0",
            "",
            "\1\u01d1",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01d3",
            "\1\u01d4",
            "\1\u01d5",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01d7",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01d9",
            "",
            "\1\u01da",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u01dc",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01de",
            "",
            "\1\u01df",
            "\1\u01e0",
            "\1\u01e1",
            "",
            "\1\u01e2",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01e4",
            "",
            "\1\u01e5",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\22\67\1\u01ee\7"+
            "\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01f1",
            "\1\u01f2",
            "\1\u01f3",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\22\67\1\u01f4\7"+
            "\67",
            "\1\u01f6",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01f8",
            "\1\u01f9",
            "",
            "",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u01fb",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u01fe",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0200",
            "\1\u0201",
            "",
            "\1\u0202",
            "",
            "\1\u0203",
            "\1\u0204",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u0206",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0208",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u020a",
            "",
            "\1\u020c\13\uffff\1\u020b",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "",
            "\1\u020f",
            "\1\u0210",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u0213",
            "",
            "\1\u0214",
            "\1\u0215",
            "",
            "\1\u0216",
            "",
            "",
            "\1\u0217",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0219",
            "\1\u021a",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u021c",
            "",
            "\1\u021d",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u021f",
            "",
            "",
            "",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0221",
            "",
            "",
            "\1\u0222",
            "\1\u0223",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0226",
            "",
            "\1\u0227",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u0229",
            "\1\u022a",
            "",
            "\1\u022b",
            "",
            "\1\u022c",
            "\1\u022d",
            "\1\u022e",
            "",
            "",
            "\1\u022f",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u0231",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0233",
            "\1\u0234",
            "\1\u0235",
            "\1\u0236",
            "\1\u0237",
            "",
            "\1\u0238",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u023a",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\22\67\1\u023b\7"+
            "\67",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u023e",
            "\1\u023f",
            "",
            "\1\u0240",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "",
            "\1\u0242",
            "\1\u0243",
            "\1\u0244",
            "",
            "\1\u0245",
            "\1\u0246",
            "\1\u0247",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "\1\u0249",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            "",
            "\1\u024b",
            "",
            "\1\67\13\uffff\12\67\45\uffff\1\67\1\uffff\32\67",
            ""
    };

    static final short[] DFA31_eot = DFA.unpackEncodedString(DFA31_eotS);
    static final short[] DFA31_eof = DFA.unpackEncodedString(DFA31_eofS);
    static final char[] DFA31_min = DFA.unpackEncodedStringToUnsignedChars(DFA31_minS);
    static final char[] DFA31_max = DFA.unpackEncodedStringToUnsignedChars(DFA31_maxS);
    static final short[] DFA31_accept = DFA.unpackEncodedString(DFA31_acceptS);
    static final short[] DFA31_special = DFA.unpackEncodedString(DFA31_specialS);
    static final short[][] DFA31_transition;

    static {
        int numStates = DFA31_transitionS.length;
        DFA31_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA31_transition[i] = DFA.unpackEncodedString(DFA31_transitionS[i]);
        }
    }

    class DFA31 extends DFA {

        public DFA31(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 31;
            this.eot = DFA31_eot;
            this.eof = DFA31_eof;
            this.min = DFA31_min;
            this.max = DFA31_max;
            this.accept = DFA31_accept;
            this.special = DFA31_special;
            this.transition = DFA31_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( CREATE | WINDOW | IN_SET | BETWEEN | LIKE | REGEXP | ESCAPE | OR_EXPR | AND_EXPR | NOT_EXPR | EVERY_EXPR | EVERY_DISTINCT_EXPR | WHERE | AS | SUM | AVG | MAX | MIN | COALESCE | MEDIAN | STDDEV | AVEDEV | COUNT | SELECT | CASE | ELSE | WHEN | THEN | END | FROM | OUTER | INNER | JOIN | LEFT | RIGHT | FULL | ON | IS | BY | GROUP | HAVING | DISTINCT | ALL | ANY | SOME | OUTPUT | EVENTS | FIRST | LAST | INSERT | INTO | ORDER | ASC | DESC | RSTREAM | ISTREAM | IRSTREAM | SCHEMA | UNIDIRECTIONAL | RETAINUNION | RETAININTERSECTION | PATTERN | SQL | METADATASQL | PREVIOUS | PRIOR | EXISTS | WEEKDAY | LW | INSTANCEOF | CAST | CURRENT_TIMESTAMP | DELETE | SNAPSHOT | SET | VARIABLE | UNTIL | AT | INDEX | TIMEPERIOD_DAY | TIMEPERIOD_DAYS | TIMEPERIOD_HOUR | TIMEPERIOD_HOURS | TIMEPERIOD_MINUTE | TIMEPERIOD_MINUTES | TIMEPERIOD_SEC | TIMEPERIOD_SECOND | TIMEPERIOD_SECONDS | TIMEPERIOD_MILLISEC | TIMEPERIOD_MILLISECOND | TIMEPERIOD_MILLISECONDS | BOOLEAN_TRUE | BOOLEAN_FALSE | VALUE_NULL | ROW_LIMIT_EXPR | OFFSET | UPDATE | MATCH_RECOGNIZE | MEASURES | DEFINE | PARTITION | MATCHES | AFTER | FOLLOWED_BY | EQUALS | SQL_NE | QUESTION | LPAREN | RPAREN | LBRACK | RBRACK | LCURLY | RCURLY | COLON | COMMA | EQUAL | LNOT | BNOT | NOT_EQUAL | DIV | DIV_ASSIGN | PLUS | PLUS_ASSIGN | INC | MINUS | MINUS_ASSIGN | DEC | STAR | STAR_ASSIGN | MOD | MOD_ASSIGN | SR | SR_ASSIGN | BSR | BSR_ASSIGN | GE | GT | SL | SL_ASSIGN | LE | LT | BXOR | BXOR_ASSIGN | BOR | BOR_ASSIGN | LOR | BAND | BAND_ASSIGN | LAND | SEMI | DOT | NUM_LONG | NUM_DOUBLE | NUM_FLOAT | ESCAPECHAR | EMAILAT | WS | SL_COMMENT | ML_COMMENT | TICKED_STRING_LITERAL | QUOTED_STRING_LITERAL | STRING_LITERAL | IDENT | NUM_INT );";
        }
    }
 

}