// $ANTLR 3.1.1 EsperEPL2Grammar.g 2010-05-04 11:18:13

  package com.espertech.esper.epl.generated;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
public class EsperEPL2GrammarLexer extends Lexer {
    public static final int CRONTAB_LIMIT_EXPR=170;
    public static final int FLOAT_SUFFIX=325;
    public static final int STAR=268;
    public static final int NUMERIC_PARAM_LIST=110;
    public static final int ISTREAM=60;
    public static final int MOD=286;
    public static final int OUTERJOIN_EXPR=152;
    public static final int CREATE_COL_TYPE_LIST=225;
    public static final int BSR=307;
    public static final int LIB_FUNCTION=176;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=95;
    public static final int FULL_OUTERJOIN_EXPR=156;
    public static final int MATCHREC_PATTERN_CONCAT=250;
    public static final int RPAREN=265;
    public static final int LNOT=296;
    public static final int INC=300;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=273;
    public static final int BSR_ASSIGN=308;
    public static final int CAST_EXPR=205;
    public static final int MATCHES=106;
    public static final int STREAM_EXPR=151;
    public static final int TIMEPERIOD_SECONDS=92;
    public static final int NOT_EQUAL=278;
    public static final int METADATASQL=68;
    public static final int EVENT_FILTER_PROPERTY_EXPR=119;
    public static final int LAST_AGGREG=232;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=113;
    public static final int FOLLOWED_BY=290;
    public static final int HOUR_PART=181;
    public static final int RBRACK=267;
    public static final int MATCHREC_PATTERN_NESTED=252;
    public static final int MATCH_UNTIL_RANGE_CLOSED=223;
    public static final int GE=282;
    public static final int METHOD_JOIN_EXPR=219;
    public static final int ASC=57;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=118;
    public static final int PATTERN_EVERY_DISTINCT_EXPR=117;
    public static final int ELSE=30;
    public static final int MINUS_ASSIGN=301;
    public static final int EVENT_FILTER_NOT_IN=129;
    public static final int INSERTINTO_STREAM_NAME=193;
    public static final int NUM_DOUBLE=246;
    public static final int UNARY_MINUS=177;
    public static final int TIMEPERIOD_MILLISEC=93;
    public static final int LCURLY=287;
    public static final int RETAINUNION=64;
    public static final int DBWHERE_CLAUSE=191;
    public static final int MEDIAN=23;
    public static final int EVENTS=51;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=127;
    public static final int GROUP=44;
    public static final int EMAILAT=316;
    public static final int WS=317;
    public static final int SUBSELECT_GROUP_EXPR=197;
    public static final int ON_SELECT_INSERT_EXPR=214;
    public static final int ESCAPECHAR=291;
    public static final int EXPRCOL=174;
    public static final int SL_COMMENT=318;
    public static final int NULL_TYPE=245;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=221;
    public static final int GT=280;
    public static final int BNOT=297;
    public static final int WHERE_EXPR=138;
    public static final int END=33;
    public static final int INNERJOIN_EXPR=153;
    public static final int LAND=314;
    public static final int NOT_REGEXP=188;
    public static final int MATCH_UNTIL_EXPR=220;
    public static final int EVENT_PROP_EXPR=160;
    public static final int LBRACK=266;
    public static final int VIEW_EXPR=135;
    public static final int ANNOTATION=228;
    public static final int LONG_TYPE=240;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=120;
    public static final int MATCHREC_PATTERN=248;
    public static final int TIMEPERIOD_SEC=90;
    public static final int ON_SELECT_EXPR=212;
    public static final int TICKED_STRING_LITERAL=292;
    public static final int MINUTE_PART=182;
    public static final int PATTERN_NOT_EXPR=116;
    public static final int SUM=18;
    public static final int SQL_NE=277;
    public static final int HexDigit=323;
    public static final int UPDATE_EXPR=233;
    public static final int LPAREN=264;
    public static final int IN_SUBSELECT_EXPR=199;
    public static final int AT=82;
    public static final int AS=17;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=96;
    public static final int THEN=32;
    public static final int MATCHREC_INTERVAL=254;
    public static final int NOT_IN_RANGE=195;
    public static final int OFFSET=100;
    public static final int AVG=19;
    public static final int LEFT=38;
    public static final int PREVIOUS=69;
    public static final int SECOND_PART=183;
    public static final int MATCH_RECOGNIZE=102;
    public static final int IDENT=261;
    public static final int DATABASE_JOIN_EXPR=137;
    public static final int BXOR=276;
    public static final int PLUS=270;
    public static final int CASE2=29;
    public static final int TIMEPERIOD_DAY=84;
    public static final int CREATE_SCHEMA_EXPR=235;
    public static final int EXISTS=71;
    public static final int EVENT_PROP_INDEXED=163;
    public static final int CREATE_INDEX_EXPR=206;
    public static final int TIMEPERIOD_MILLISECOND=94;
    public static final int EVAL_NOTEQUALS_EXPR=144;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=222;
    public static final int CREATE_VARIABLE_EXPR=218;
    public static final int LIKE=8;
    public static final int OUTER=35;
    public static final int MATCHREC_DEFINE=255;
    public static final int BY=43;
    public static final int ARRAY_PARAM_LIST=114;
    public static final int RIGHT_OUTERJOIN_EXPR=155;
    public static final int NUMBERSETSTAR=227;
    public static final int LAST_OPERATOR=202;
    public static final int PATTERN_FILTER_EXPR=115;
    public static final int EVAL_AND_EXPR=141;
    public static final int LEFT_OUTERJOIN_EXPR=154;
    public static final int EPL_EXPR=247;
    public static final int GROUP_BY_EXPR=157;
    public static final int SET=79;
    public static final int RIGHT=39;
    public static final int HAVING=45;
    public static final int INSTANCEOF=74;
    public static final int MIN=21;
    public static final int EVENT_PROP_SIMPLE=161;
    public static final int MINUS=284;
    public static final int SEMI=315;
    public static final int STAR_ASSIGN=303;
    public static final int VARIANT_LIST=238;
    public static final int FIRST_AGGREG=231;
    public static final int COLON=272;
    public static final int EVAL_EQUALS_GROUP_EXPR=145;
    public static final int BAND_ASSIGN=313;
    public static final int SCHEMA=62;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=171;
    public static final int VALUE_NULL=98;
    public static final int NOT_IN_SET=185;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=164;
    public static final int SL=309;
    public static final int NOT_IN_SUBSELECT_EXPR=200;
    public static final int WHEN=31;
    public static final int GUARD_EXPR=133;
    public static final int SR=305;
    public static final int RCURLY=288;
    public static final int PLUS_ASSIGN=299;
    public static final int EXISTS_SUBSELECT_EXPR=198;
    public static final int DAY_PART=180;
    public static final int EVENT_FILTER_IN=128;
    public static final int DIV=285;
    public static final int OBJECT_PARAM_ORDERED_EXPR=112;
    public static final int OctalEscape=322;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=184;
    public static final int PRIOR=70;
    public static final int FIRST=52;
    public static final int ROW_LIMIT_EXPR=99;
    public static final int SELECTION_EXPR=148;
    public static final int LOR=283;
    public static final int CAST=75;
    public static final int LW=73;
    public static final int WILDCARD_SELECT=192;
    public static final int EXPONENT=324;
    public static final int LT=279;
    public static final int PATTERN_INCL_EXPR=136;
    public static final int ORDER_BY_EXPR=158;
    public static final int BOOL_TYPE=244;
    public static final int MOD_ASSIGN=304;
    public static final int ANNOTATION_ARRAY=229;
    public static final int CASE=28;
    public static final int IN_SUBSELECT_QUERY_EXPR=201;
    public static final int EQUALS=262;
    public static final int COUNT=26;
    public static final int RETAININTERSECTION=65;
    public static final int DIV_ASSIGN=298;
    public static final int SL_ASSIGN=310;
    public static final int PATTERN=66;
    public static final int SQL=67;
    public static final int FULL=40;
    public static final int WEEKDAY=72;
    public static final int MATCHREC_AFTER_SKIP=253;
    public static final int ESCAPE=10;
    public static final int INSERT=54;
    public static final int ON_UPDATE_EXPR=213;
    public static final int ARRAY_EXPR=179;
    public static final int CREATE_COL_TYPE=226;
    public static final int LAST=53;
    public static final int BOOLEAN_FALSE=97;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=146;
    public static final int SELECT=27;
    public static final int INTO=55;
    public static final int EVENT_FILTER_BETWEEN=130;
    public static final int COALESCE=22;
    public static final int TIMEPERIOD_SECOND=91;
    public static final int FLOAT_TYPE=241;
    public static final int SUBSELECT_EXPR=196;
    public static final int ANNOTATION_VALUE=230;
    public static final int CONCAT=175;
    public static final int NUMERIC_PARAM_RANGE=109;
    public static final int CLASS_IDENT=132;
    public static final int MATCHREC_PATTERN_ALTER=251;
    public static final int ON_EXPR=209;
    public static final int CREATE_WINDOW_EXPR=207;
    public static final int PROPERTY_SELECTION_STREAM=122;
    public static final int ON_DELETE_EXPR=211;
    public static final int ON=41;
    public static final int NUM_LONG=293;
    public static final int TIME_PERIOD=178;
    public static final int DOUBLE_TYPE=242;
    public static final int DELETE=77;
    public static final int INT_TYPE=239;
    public static final int MATCHREC_PARTITION=259;
    public static final int EVAL_BITWISE_EXPR=140;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=159;
    public static final int TIMEPERIOD_HOURS=87;
    public static final int VARIABLE=80;
    public static final int SUBSTITUTION=204;
    public static final int UNTIL=81;
    public static final int STRING_TYPE=243;
    public static final int ON_SET_EXPR=217;
    public static final int MATCHREC_DEFINE_ITEM=256;
    public static final int NUM_INT=289;
    public static final int STDDEV=24;
    public static final int CREATE_SCHEMA_EXPR_INH=237;
    public static final int ON_EXPR_FROM=216;
    public static final int NUM_FLOAT=294;
    public static final int FROM=34;
    public static final int DISTINCT=46;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=121;
    public static final int OUTPUT=50;
    public static final int EscapeSequence=320;
    public static final int WEEKDAY_OPERATOR=203;
    public static final int WHERE=16;
    public static final int DEC=302;
    public static final int INNER=36;
    public static final int NUMERIC_PARAM_FREQUENCY=111;
    public static final int BXOR_ASSIGN=311;
    public static final int AFTER_LIMIT_EXPR=169;
    public static final int ORDER=56;
    public static final int SNAPSHOT=78;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=166;
    public static final int EVENT_FILTER_PARAM=125;
    public static final int IRSTREAM=61;
    public static final int UPDATE=101;
    public static final int MAX=20;
    public static final int FOR=108;
    public static final int ON_STREAM=210;
    public static final int DEFINE=104;
    public static final int TIMEPERIOD_DAYS=85;
    public static final int EVENT_FILTER_RANGE=126;
    public static final int INDEX=83;
    public static final int ML_COMMENT=319;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=165;
    public static final int BOR_ASSIGN=312;
    public static final int COMMA=260;
    public static final int WHEN_LIMIT_EXPR=172;
    public static final int PARTITION=105;
    public static final int IS=42;
    public static final int TIMEPERIOD_LIMIT_EXPR=168;
    public static final int SOME=49;
    public static final int ALL=47;
    public static final int TIMEPERIOD_HOUR=86;
    public static final int MATCHREC_MEASURE_ITEM=258;
    public static final int BOR=269;
    public static final int EQUAL=295;
    public static final int EVENT_FILTER_NOT_BETWEEN=131;
    public static final int IN_RANGE=194;
    public static final int DOT=263;
    public static final int CURRENT_TIMESTAMP=76;
    public static final int MATCHREC_MEASURES=257;
    public static final int EVERY_DISTINCT_EXPR=15;
    public static final int PROPERTY_WILDCARD_SELECT=123;
    public static final int INSERTINTO_EXPR=173;
    public static final int HAVING_EXPR=139;
    public static final int UNIDIRECTIONAL=63;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=224;
    public static final int EVAL_EQUALS_EXPR=143;
    public static final int TIMEPERIOD_MINUTES=89;
    public static final int RSTREAM=59;
    public static final int NOT_LIKE=187;
    public static final int EVENT_LIMIT_EXPR=167;
    public static final int NOT_BETWEEN=186;
    public static final int TIMEPERIOD_MINUTE=88;
    public static final int EVAL_OR_EXPR=142;
    public static final int ON_SELECT_INSERT_OUTPUT=215;
    public static final int AFTER=107;
    public static final int MEASURES=103;
    public static final int MATCHREC_PATTERN_ATOM=249;
    public static final int BAND=275;
    public static final int QUOTED_STRING_LITERAL=274;
    public static final int JOIN=37;
    public static final int ANY=48;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=271;
    public static final int OBSERVER_EXPR=134;
    public static final int EVENT_FILTER_IDENT=124;
    public static final int CREATE_SCHEMA_EXPR_QUAL=236;
    public static final int EVENT_PROP_MAPPED=162;
    public static final int UnicodeEscape=321;
    public static final int AVEDEV=25;
    public static final int DBSELECT_EXPR=189;
    public static final int SELECTION_ELEMENT_EXPR=149;
    public static final int CREATE_WINDOW_SELECT_EXPR=208;
    public static final int WINDOW=5;
    public static final int ON_SET_EXPR_ITEM=234;
    public static final int DESC=58;
    public static final int SELECTION_STREAM=150;
    public static final int SR_ASSIGN=306;
    public static final int DBFROM_CLAUSE=190;
    public static final int LE=281;
    public static final int EVAL_IDENT=147;

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

    // $ANTLR start "FOR"
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:135:5: ( 'for' )
            // EsperEPL2Grammar.g:135:7: 'for'
            {
            match("for"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FOR"

    // $ANTLR start "FOLLOWED_BY"
    public final void mFOLLOWED_BY() throws RecognitionException {
        try {
            int _type = FOLLOWED_BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EsperEPL2Grammar.g:1717:14: ( '->' )
            // EsperEPL2Grammar.g:1717:16: '->'
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
            // EsperEPL2Grammar.g:1718:10: ( '=' )
            // EsperEPL2Grammar.g:1718:12: '='
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
            // EsperEPL2Grammar.g:1719:10: ( '<>' )
            // EsperEPL2Grammar.g:1719:12: '<>'
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
            // EsperEPL2Grammar.g:1720:11: ( '?' )
            // EsperEPL2Grammar.g:1720:13: '?'
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
            // EsperEPL2Grammar.g:1721:10: ( '(' )
            // EsperEPL2Grammar.g:1721:12: '('
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
            // EsperEPL2Grammar.g:1722:10: ( ')' )
            // EsperEPL2Grammar.g:1722:12: ')'
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
            // EsperEPL2Grammar.g:1723:10: ( '[' )
            // EsperEPL2Grammar.g:1723:12: '['
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
            // EsperEPL2Grammar.g:1724:10: ( ']' )
            // EsperEPL2Grammar.g:1724:12: ']'
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
            // EsperEPL2Grammar.g:1725:10: ( '{' )
            // EsperEPL2Grammar.g:1725:12: '{'
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
            // EsperEPL2Grammar.g:1726:10: ( '}' )
            // EsperEPL2Grammar.g:1726:12: '}'
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
            // EsperEPL2Grammar.g:1727:9: ( ':' )
            // EsperEPL2Grammar.g:1727:11: ':'
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
            // EsperEPL2Grammar.g:1728:9: ( ',' )
            // EsperEPL2Grammar.g:1728:11: ','
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
            // EsperEPL2Grammar.g:1729:9: ( '==' )
            // EsperEPL2Grammar.g:1729:11: '=='
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
            // EsperEPL2Grammar.g:1730:8: ( '!' )
            // EsperEPL2Grammar.g:1730:10: '!'
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
            // EsperEPL2Grammar.g:1731:8: ( '~' )
            // EsperEPL2Grammar.g:1731:10: '~'
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
            // EsperEPL2Grammar.g:1732:12: ( '!=' )
            // EsperEPL2Grammar.g:1732:14: '!='
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
            // EsperEPL2Grammar.g:1733:7: ( '/' )
            // EsperEPL2Grammar.g:1733:9: '/'
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
            // EsperEPL2Grammar.g:1734:13: ( '/=' )
            // EsperEPL2Grammar.g:1734:15: '/='
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
            // EsperEPL2Grammar.g:1735:8: ( '+' )
            // EsperEPL2Grammar.g:1735:10: '+'
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
            // EsperEPL2Grammar.g:1736:13: ( '+=' )
            // EsperEPL2Grammar.g:1736:15: '+='
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
            // EsperEPL2Grammar.g:1737:7: ( '++' )
            // EsperEPL2Grammar.g:1737:9: '++'
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
            // EsperEPL2Grammar.g:1738:9: ( '-' )
            // EsperEPL2Grammar.g:1738:11: '-'
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
            // EsperEPL2Grammar.g:1739:15: ( '-=' )
            // EsperEPL2Grammar.g:1739:17: '-='
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
            // EsperEPL2Grammar.g:1740:7: ( '--' )
            // EsperEPL2Grammar.g:1740:9: '--'
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
            // EsperEPL2Grammar.g:1741:8: ( '*' )
            // EsperEPL2Grammar.g:1741:10: '*'
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
            // EsperEPL2Grammar.g:1742:14: ( '*=' )
            // EsperEPL2Grammar.g:1742:16: '*='
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
            // EsperEPL2Grammar.g:1743:7: ( '%' )
            // EsperEPL2Grammar.g:1743:9: '%'
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
            // EsperEPL2Grammar.g:1744:13: ( '%=' )
            // EsperEPL2Grammar.g:1744:15: '%='
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
            // EsperEPL2Grammar.g:1745:6: ( '>>' )
            // EsperEPL2Grammar.g:1745:8: '>>'
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
            // EsperEPL2Grammar.g:1746:12: ( '>>=' )
            // EsperEPL2Grammar.g:1746:14: '>>='
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
            // EsperEPL2Grammar.g:1747:7: ( '>>>' )
            // EsperEPL2Grammar.g:1747:9: '>>>'
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
            // EsperEPL2Grammar.g:1748:13: ( '>>>=' )
            // EsperEPL2Grammar.g:1748:15: '>>>='
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
            // EsperEPL2Grammar.g:1749:6: ( '>=' )
            // EsperEPL2Grammar.g:1749:8: '>='
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
            // EsperEPL2Grammar.g:1750:6: ( '>' )
            // EsperEPL2Grammar.g:1750:8: '>'
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
            // EsperEPL2Grammar.g:1751:6: ( '<<' )
            // EsperEPL2Grammar.g:1751:8: '<<'
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
            // EsperEPL2Grammar.g:1752:12: ( '<<=' )
            // EsperEPL2Grammar.g:1752:14: '<<='
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
            // EsperEPL2Grammar.g:1753:6: ( '<=' )
            // EsperEPL2Grammar.g:1753:8: '<='
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
            // EsperEPL2Grammar.g:1754:6: ( '<' )
            // EsperEPL2Grammar.g:1754:8: '<'
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
            // EsperEPL2Grammar.g:1755:8: ( '^' )
            // EsperEPL2Grammar.g:1755:10: '^'
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
            // EsperEPL2Grammar.g:1756:14: ( '^=' )
            // EsperEPL2Grammar.g:1756:16: '^='
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
            // EsperEPL2Grammar.g:1757:6: ( '|' )
            // EsperEPL2Grammar.g:1757:8: '|'
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
            // EsperEPL2Grammar.g:1758:13: ( '|=' )
            // EsperEPL2Grammar.g:1758:15: '|='
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
            // EsperEPL2Grammar.g:1759:6: ( '||' )
            // EsperEPL2Grammar.g:1759:8: '||'
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
            // EsperEPL2Grammar.g:1760:8: ( '&' )
            // EsperEPL2Grammar.g:1760:10: '&'
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
            // EsperEPL2Grammar.g:1761:14: ( '&=' )
            // EsperEPL2Grammar.g:1761:16: '&='
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
            // EsperEPL2Grammar.g:1762:8: ( '&&' )
            // EsperEPL2Grammar.g:1762:10: '&&'
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
            // EsperEPL2Grammar.g:1763:8: ( ';' )
            // EsperEPL2Grammar.g:1763:10: ';'
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
            // EsperEPL2Grammar.g:1764:7: ( '.' )
            // EsperEPL2Grammar.g:1764:9: '.'
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
            // EsperEPL2Grammar.g:1765:10: ( '\\u18FF' )
            // EsperEPL2Grammar.g:1765:12: '\\u18FF'
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
            // EsperEPL2Grammar.g:1766:12: ( '\\u18FE' )
            // EsperEPL2Grammar.g:1766:14: '\\u18FE'
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
            // EsperEPL2Grammar.g:1767:11: ( '\\u18FD' )
            // EsperEPL2Grammar.g:1767:13: '\\u18FD'
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
            // EsperEPL2Grammar.g:1768:12: ( '\\\\' )
            // EsperEPL2Grammar.g:1768:14: '\\\\'
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
            // EsperEPL2Grammar.g:1769:10: ( '@' )
            // EsperEPL2Grammar.g:1769:12: '@'
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
            // EsperEPL2Grammar.g:1772:4: ( ( ' ' | '\\t' | '\\f' | ( '\\r' | '\\n' ) )+ )
            // EsperEPL2Grammar.g:1772:6: ( ' ' | '\\t' | '\\f' | ( '\\r' | '\\n' ) )+
            {
            // EsperEPL2Grammar.g:1772:6: ( ' ' | '\\t' | '\\f' | ( '\\r' | '\\n' ) )+
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
            // EsperEPL2Grammar.g:1786:2: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\n' | '\\r' ( '\\n' )? )? )
            // EsperEPL2Grammar.g:1786:4: '//' (~ ( '\\n' | '\\r' ) )* ( '\\n' | '\\r' ( '\\n' )? )?
            {
            match("//"); if (state.failed) return ;

            // EsperEPL2Grammar.g:1787:3: (~ ( '\\n' | '\\r' ) )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='\u0000' && LA2_0<='\t')||(LA2_0>='\u000B' && LA2_0<='\f')||(LA2_0>='\u000E' && LA2_0<='\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // EsperEPL2Grammar.g:1787:4: ~ ( '\\n' | '\\r' )
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

            // EsperEPL2Grammar.g:1787:19: ( '\\n' | '\\r' ( '\\n' )? )?
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
                    // EsperEPL2Grammar.g:1787:20: '\\n'
                    {
                    match('\n'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // EsperEPL2Grammar.g:1787:25: '\\r' ( '\\n' )?
                    {
                    match('\r'); if (state.failed) return ;
                    // EsperEPL2Grammar.g:1787:29: ( '\\n' )?
                    int alt3=2;
                    int LA3_0 = input.LA(1);

                    if ( (LA3_0=='\n') ) {
                        alt3=1;
                    }
                    switch (alt3) {
                        case 1 :
                            // EsperEPL2Grammar.g:1787:30: '\\n'
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
            // EsperEPL2Grammar.g:1793:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // EsperEPL2Grammar.g:1793:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); if (state.failed) return ;

            // EsperEPL2Grammar.g:1793:14: ( options {greedy=false; } : . )*
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
            	    // EsperEPL2Grammar.g:1793:42: .
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
            // EsperEPL2Grammar.g:1797:5: ( '`' ( EscapeSequence | ~ ( '\\`' | '\\\\' ) )* '`' )
            // EsperEPL2Grammar.g:1797:9: '`' ( EscapeSequence | ~ ( '\\`' | '\\\\' ) )* '`'
            {
            match('`'); if (state.failed) return ;
            // EsperEPL2Grammar.g:1797:13: ( EscapeSequence | ~ ( '\\`' | '\\\\' ) )*
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
            	    // EsperEPL2Grammar.g:1797:15: EscapeSequence
            	    {
            	    mEscapeSequence(); if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    // EsperEPL2Grammar.g:1797:32: ~ ( '\\`' | '\\\\' )
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
            // EsperEPL2Grammar.g:1801:5: ( '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )* '\\'' )
            // EsperEPL2Grammar.g:1801:9: '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )* '\\''
            {
            match('\''); if (state.failed) return ;
            // EsperEPL2Grammar.g:1801:14: ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )*
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
            	    // EsperEPL2Grammar.g:1801:16: EscapeSequence
            	    {
            	    mEscapeSequence(); if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    // EsperEPL2Grammar.g:1801:33: ~ ( '\\'' | '\\\\' )
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
            // EsperEPL2Grammar.g:1805:5: ( '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' )
            // EsperEPL2Grammar.g:1805:8: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); if (state.failed) return ;
            // EsperEPL2Grammar.g:1805:12: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
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
            	    // EsperEPL2Grammar.g:1805:14: EscapeSequence
            	    {
            	    mEscapeSequence(); if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    // EsperEPL2Grammar.g:1805:31: ~ ( '\\\\' | '\"' )
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
            // EsperEPL2Grammar.g:1809:16: ( '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | UnicodeEscape | OctalEscape | . ) )
            // EsperEPL2Grammar.g:1809:18: '\\\\' ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | UnicodeEscape | OctalEscape | . )
            {
            match('\\'); if (state.failed) return ;
            // EsperEPL2Grammar.g:1810:3: ( 'n' | 'r' | 't' | 'b' | 'f' | '\"' | '\\'' | '\\\\' | UnicodeEscape | OctalEscape | . )
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
                    // EsperEPL2Grammar.g:1810:5: 'n'
                    {
                    match('n'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // EsperEPL2Grammar.g:1811:5: 'r'
                    {
                    match('r'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // EsperEPL2Grammar.g:1812:5: 't'
                    {
                    match('t'); if (state.failed) return ;

                    }
                    break;
                case 4 :
                    // EsperEPL2Grammar.g:1813:5: 'b'
                    {
                    match('b'); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // EsperEPL2Grammar.g:1814:5: 'f'
                    {
                    match('f'); if (state.failed) return ;

                    }
                    break;
                case 6 :
                    // EsperEPL2Grammar.g:1815:5: '\"'
                    {
                    match('\"'); if (state.failed) return ;

                    }
                    break;
                case 7 :
                    // EsperEPL2Grammar.g:1816:5: '\\''
                    {
                    match('\''); if (state.failed) return ;

                    }
                    break;
                case 8 :
                    // EsperEPL2Grammar.g:1817:5: '\\\\'
                    {
                    match('\\'); if (state.failed) return ;

                    }
                    break;
                case 9 :
                    // EsperEPL2Grammar.g:1818:5: UnicodeEscape
                    {
                    mUnicodeEscape(); if (state.failed) return ;

                    }
                    break;
                case 10 :
                    // EsperEPL2Grammar.g:1819:5: OctalEscape
                    {
                    mOctalEscape(); if (state.failed) return ;

                    }
                    break;
                case 11 :
                    // EsperEPL2Grammar.g:1820:5: .
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
            // EsperEPL2Grammar.g:1826:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt10=3;
            int LA10_0 = input.LA(1);

            if ( (LA10_0=='\\') ) {
                int LA10_1 = input.LA(2);

                if ( ((LA10_1>='0' && LA10_1<='3')) ) {
                    int LA10_2 = input.LA(3);

                    if ( ((LA10_2>='0' && LA10_2<='7')) ) {
                        int LA10_5 = input.LA(4);

                        if ( ((LA10_5>='0' && LA10_5<='7')) ) {
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
                    // EsperEPL2Grammar.g:1826:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); if (state.failed) return ;
                    // EsperEPL2Grammar.g:1826:14: ( '0' .. '3' )
                    // EsperEPL2Grammar.g:1826:15: '0' .. '3'
                    {
                    matchRange('0','3'); if (state.failed) return ;

                    }

                    // EsperEPL2Grammar.g:1826:25: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1826:26: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }

                    // EsperEPL2Grammar.g:1826:36: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1826:37: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Grammar.g:1827:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); if (state.failed) return ;
                    // EsperEPL2Grammar.g:1827:14: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1827:15: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }

                    // EsperEPL2Grammar.g:1827:25: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1827:26: '0' .. '7'
                    {
                    matchRange('0','7'); if (state.failed) return ;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Grammar.g:1828:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); if (state.failed) return ;
                    // EsperEPL2Grammar.g:1828:14: ( '0' .. '7' )
                    // EsperEPL2Grammar.g:1828:15: '0' .. '7'
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
            // EsperEPL2Grammar.g:1832:10: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // EsperEPL2Grammar.g:1832:12: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )
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
            // EsperEPL2Grammar.g:1836:5: ( '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit )
            // EsperEPL2Grammar.g:1836:9: '\\\\' 'u' HexDigit HexDigit HexDigit HexDigit
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
            // EsperEPL2Grammar.g:1843:2: ( ( 'a' .. 'z' | '_' | '$' ) ( 'a' .. 'z' | '_' | '0' .. '9' | '$' )* )
            // EsperEPL2Grammar.g:1843:4: ( 'a' .. 'z' | '_' | '$' ) ( 'a' .. 'z' | '_' | '0' .. '9' | '$' )*
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

            // EsperEPL2Grammar.g:1843:23: ( 'a' .. 'z' | '_' | '0' .. '9' | '$' )*
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
            // EsperEPL2Grammar.g:1850:5: ( '.' ( ( '0' .. '9' )+ ( EXPONENT )? (f1= FLOAT_SUFFIX )? )? | ( '0' ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )? | ( '1' .. '9' ) ( '0' .. '9' )* ) ( ( 'l' ) | {...}? ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX ) )? )
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
                    // EsperEPL2Grammar.g:1850:9: '.' ( ( '0' .. '9' )+ ( EXPONENT )? (f1= FLOAT_SUFFIX )? )?
                    {
                    match('.'); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                      _type = DOT;
                    }
                    // EsperEPL2Grammar.g:1851:13: ( ( '0' .. '9' )+ ( EXPONENT )? (f1= FLOAT_SUFFIX )? )?
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( ((LA15_0>='0' && LA15_0<='9')) ) {
                        alt15=1;
                    }
                    switch (alt15) {
                        case 1 :
                            // EsperEPL2Grammar.g:1851:15: ( '0' .. '9' )+ ( EXPONENT )? (f1= FLOAT_SUFFIX )?
                            {
                            // EsperEPL2Grammar.g:1851:15: ( '0' .. '9' )+
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
                            	    // EsperEPL2Grammar.g:1851:16: '0' .. '9'
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

                            // EsperEPL2Grammar.g:1851:27: ( EXPONENT )?
                            int alt13=2;
                            int LA13_0 = input.LA(1);

                            if ( (LA13_0=='e') ) {
                                alt13=1;
                            }
                            switch (alt13) {
                                case 1 :
                                    // EsperEPL2Grammar.g:1851:28: EXPONENT
                                    {
                                    mEXPONENT(); if (state.failed) return ;

                                    }
                                    break;

                            }

                            // EsperEPL2Grammar.g:1851:39: (f1= FLOAT_SUFFIX )?
                            int alt14=2;
                            int LA14_0 = input.LA(1);

                            if ( (LA14_0=='d'||LA14_0=='f') ) {
                                alt14=1;
                            }
                            switch (alt14) {
                                case 1 :
                                    // EsperEPL2Grammar.g:1851:40: f1= FLOAT_SUFFIX
                                    {
                                    int f1Start1941 = getCharIndex();
                                    mFLOAT_SUFFIX(); if (state.failed) return ;
                                    f1 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, f1Start1941, getCharIndex()-1);
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
                    // EsperEPL2Grammar.g:1862:4: ( '0' ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )? | ( '1' .. '9' ) ( '0' .. '9' )* ) ( ( 'l' ) | {...}? ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX ) )?
                    {
                    // EsperEPL2Grammar.g:1862:4: ( '0' ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )? | ( '1' .. '9' ) ( '0' .. '9' )* )
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
                            // EsperEPL2Grammar.g:1862:6: '0' ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )?
                            {
                            match('0'); if (state.failed) return ;
                            if ( state.backtracking==0 ) {
                              isDecimal = true;
                            }
                            // EsperEPL2Grammar.g:1863:4: ( ( 'x' ) ( HexDigit )+ | ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+ | ( '0' .. '7' )+ )?
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
                                    // EsperEPL2Grammar.g:1863:6: ( 'x' ) ( HexDigit )+
                                    {
                                    // EsperEPL2Grammar.g:1863:6: ( 'x' )
                                    // EsperEPL2Grammar.g:1863:7: 'x'
                                    {
                                    match('x'); if (state.failed) return ;

                                    }

                                    // EsperEPL2Grammar.g:1864:5: ( HexDigit )+
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
                                    	    // EsperEPL2Grammar.g:1870:6: HexDigit
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
                                    // EsperEPL2Grammar.g:1874:5: ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )=> ( '0' .. '9' )+
                                    {
                                    // EsperEPL2Grammar.g:1874:50: ( '0' .. '9' )+
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
                                    	    // EsperEPL2Grammar.g:1874:51: '0' .. '9'
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
                                    // EsperEPL2Grammar.g:1876:6: ( '0' .. '7' )+
                                    {
                                    // EsperEPL2Grammar.g:1876:6: ( '0' .. '7' )+
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
                                    	    // EsperEPL2Grammar.g:1876:7: '0' .. '7'
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
                            // EsperEPL2Grammar.g:1878:5: ( '1' .. '9' ) ( '0' .. '9' )*
                            {
                            // EsperEPL2Grammar.g:1878:5: ( '1' .. '9' )
                            // EsperEPL2Grammar.g:1878:6: '1' .. '9'
                            {
                            matchRange('1','9'); if (state.failed) return ;

                            }

                            // EsperEPL2Grammar.g:1878:16: ( '0' .. '9' )*
                            loop20:
                            do {
                                int alt20=2;
                                int LA20_0 = input.LA(1);

                                if ( ((LA20_0>='0' && LA20_0<='9')) ) {
                                    alt20=1;
                                }


                                switch (alt20) {
                            	case 1 :
                            	    // EsperEPL2Grammar.g:1878:17: '0' .. '9'
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

                    // EsperEPL2Grammar.g:1880:3: ( ( 'l' ) | {...}? ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX ) )?
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
                            // EsperEPL2Grammar.g:1880:5: ( 'l' )
                            {
                            // EsperEPL2Grammar.g:1880:5: ( 'l' )
                            // EsperEPL2Grammar.g:1880:6: 'l'
                            {
                            match('l'); if (state.failed) return ;

                            }

                            if ( state.backtracking==0 ) {
                               _type = NUM_LONG; 
                            }

                            }
                            break;
                        case 2 :
                            // EsperEPL2Grammar.g:1883:5: {...}? ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX )
                            {
                            if ( !((isDecimal)) ) {
                                if (state.backtracking>0) {state.failed=true; return ;}
                                throw new FailedPredicateException(input, "NUM_INT", "isDecimal");
                            }
                            // EsperEPL2Grammar.g:1884:13: ( '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )? | EXPONENT (f3= FLOAT_SUFFIX )? | f4= FLOAT_SUFFIX )
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
                                    // EsperEPL2Grammar.g:1884:17: '.' ( '0' .. '9' )* ( EXPONENT )? (f2= FLOAT_SUFFIX )?
                                    {
                                    match('.'); if (state.failed) return ;
                                    // EsperEPL2Grammar.g:1884:21: ( '0' .. '9' )*
                                    loop22:
                                    do {
                                        int alt22=2;
                                        int LA22_0 = input.LA(1);

                                        if ( ((LA22_0>='0' && LA22_0<='9')) ) {
                                            alt22=1;
                                        }


                                        switch (alt22) {
                                    	case 1 :
                                    	    // EsperEPL2Grammar.g:1884:22: '0' .. '9'
                                    	    {
                                    	    matchRange('0','9'); if (state.failed) return ;

                                    	    }
                                    	    break;

                                    	default :
                                    	    break loop22;
                                        }
                                    } while (true);

                                    // EsperEPL2Grammar.g:1884:33: ( EXPONENT )?
                                    int alt23=2;
                                    int LA23_0 = input.LA(1);

                                    if ( (LA23_0=='e') ) {
                                        alt23=1;
                                    }
                                    switch (alt23) {
                                        case 1 :
                                            // EsperEPL2Grammar.g:1884:34: EXPONENT
                                            {
                                            mEXPONENT(); if (state.failed) return ;

                                            }
                                            break;

                                    }

                                    // EsperEPL2Grammar.g:1884:45: (f2= FLOAT_SUFFIX )?
                                    int alt24=2;
                                    int LA24_0 = input.LA(1);

                                    if ( (LA24_0=='d'||LA24_0=='f') ) {
                                        alt24=1;
                                    }
                                    switch (alt24) {
                                        case 1 :
                                            // EsperEPL2Grammar.g:1884:46: f2= FLOAT_SUFFIX
                                            {
                                            int f2Start2205 = getCharIndex();
                                            mFLOAT_SUFFIX(); if (state.failed) return ;
                                            f2 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, f2Start2205, getCharIndex()-1);
                                            if ( state.backtracking==0 ) {
                                              t=f2;
                                            }

                                            }
                                            break;

                                    }


                                    }
                                    break;
                                case 2 :
                                    // EsperEPL2Grammar.g:1885:17: EXPONENT (f3= FLOAT_SUFFIX )?
                                    {
                                    mEXPONENT(); if (state.failed) return ;
                                    // EsperEPL2Grammar.g:1885:26: (f3= FLOAT_SUFFIX )?
                                    int alt25=2;
                                    int LA25_0 = input.LA(1);

                                    if ( (LA25_0=='d'||LA25_0=='f') ) {
                                        alt25=1;
                                    }
                                    switch (alt25) {
                                        case 1 :
                                            // EsperEPL2Grammar.g:1885:27: f3= FLOAT_SUFFIX
                                            {
                                            int f3Start2232 = getCharIndex();
                                            mFLOAT_SUFFIX(); if (state.failed) return ;
                                            f3 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, f3Start2232, getCharIndex()-1);
                                            if ( state.backtracking==0 ) {
                                              t=f3;
                                            }

                                            }
                                            break;

                                    }


                                    }
                                    break;
                                case 3 :
                                    // EsperEPL2Grammar.g:1886:17: f4= FLOAT_SUFFIX
                                    {
                                    int f4Start2256 = getCharIndex();
                                    mFLOAT_SUFFIX(); if (state.failed) return ;
                                    f4 = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, f4Start2256, getCharIndex()-1);
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
            // EsperEPL2Grammar.g:1903:2: ( ( 'e' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // EsperEPL2Grammar.g:1903:4: ( 'e' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            // EsperEPL2Grammar.g:1903:4: ( 'e' )
            // EsperEPL2Grammar.g:1903:5: 'e'
            {
            match('e'); if (state.failed) return ;

            }

            // EsperEPL2Grammar.g:1903:10: ( '+' | '-' )?
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

            // EsperEPL2Grammar.g:1903:21: ( '0' .. '9' )+
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
            	    // EsperEPL2Grammar.g:1903:22: '0' .. '9'
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
            // EsperEPL2Grammar.g:1909:2: ( 'f' | 'd' )
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
        // EsperEPL2Grammar.g:1:8: ( CREATE | WINDOW | IN_SET | BETWEEN | LIKE | REGEXP | ESCAPE | OR_EXPR | AND_EXPR | NOT_EXPR | EVERY_EXPR | EVERY_DISTINCT_EXPR | WHERE | AS | SUM | AVG | MAX | MIN | COALESCE | MEDIAN | STDDEV | AVEDEV | COUNT | SELECT | CASE | ELSE | WHEN | THEN | END | FROM | OUTER | INNER | JOIN | LEFT | RIGHT | FULL | ON | IS | BY | GROUP | HAVING | DISTINCT | ALL | ANY | SOME | OUTPUT | EVENTS | FIRST | LAST | INSERT | INTO | ORDER | ASC | DESC | RSTREAM | ISTREAM | IRSTREAM | SCHEMA | UNIDIRECTIONAL | RETAINUNION | RETAININTERSECTION | PATTERN | SQL | METADATASQL | PREVIOUS | PRIOR | EXISTS | WEEKDAY | LW | INSTANCEOF | CAST | CURRENT_TIMESTAMP | DELETE | SNAPSHOT | SET | VARIABLE | UNTIL | AT | INDEX | TIMEPERIOD_DAY | TIMEPERIOD_DAYS | TIMEPERIOD_HOUR | TIMEPERIOD_HOURS | TIMEPERIOD_MINUTE | TIMEPERIOD_MINUTES | TIMEPERIOD_SEC | TIMEPERIOD_SECOND | TIMEPERIOD_SECONDS | TIMEPERIOD_MILLISEC | TIMEPERIOD_MILLISECOND | TIMEPERIOD_MILLISECONDS | BOOLEAN_TRUE | BOOLEAN_FALSE | VALUE_NULL | ROW_LIMIT_EXPR | OFFSET | UPDATE | MATCH_RECOGNIZE | MEASURES | DEFINE | PARTITION | MATCHES | AFTER | FOR | FOLLOWED_BY | EQUALS | SQL_NE | QUESTION | LPAREN | RPAREN | LBRACK | RBRACK | LCURLY | RCURLY | COLON | COMMA | EQUAL | LNOT | BNOT | NOT_EQUAL | DIV | DIV_ASSIGN | PLUS | PLUS_ASSIGN | INC | MINUS | MINUS_ASSIGN | DEC | STAR | STAR_ASSIGN | MOD | MOD_ASSIGN | SR | SR_ASSIGN | BSR | BSR_ASSIGN | GE | GT | SL | SL_ASSIGN | LE | LT | BXOR | BXOR_ASSIGN | BOR | BOR_ASSIGN | LOR | BAND | BAND_ASSIGN | LAND | SEMI | DOT | NUM_LONG | NUM_DOUBLE | NUM_FLOAT | ESCAPECHAR | EMAILAT | WS | SL_COMMENT | ML_COMMENT | TICKED_STRING_LITERAL | QUOTED_STRING_LITERAL | STRING_LITERAL | IDENT | NUM_INT )
        int alt31=165;
        switch ( input.LA(1) ) {
        case 'c':
            {
            switch ( input.LA(2) ) {
            case 'r':
                {
                int LA31_57 = input.LA(3);

                if ( (LA31_57=='e') ) {
                    int LA31_157 = input.LA(4);

                    if ( (LA31_157=='a') ) {
                        int LA31_250 = input.LA(5);

                        if ( (LA31_250=='t') ) {
                            int LA31_341 = input.LA(6);

                            if ( (LA31_341=='e') ) {
                                int LA31_417 = input.LA(7);

                                if ( (LA31_417=='$'||(LA31_417>='0' && LA31_417<='9')||LA31_417=='_'||(LA31_417>='a' && LA31_417<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=1;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'o':
                {
                switch ( input.LA(3) ) {
                case 'a':
                    {
                    int LA31_158 = input.LA(4);

                    if ( (LA31_158=='l') ) {
                        int LA31_251 = input.LA(5);

                        if ( (LA31_251=='e') ) {
                            int LA31_342 = input.LA(6);

                            if ( (LA31_342=='s') ) {
                                int LA31_418 = input.LA(7);

                                if ( (LA31_418=='c') ) {
                                    int LA31_476 = input.LA(8);

                                    if ( (LA31_476=='e') ) {
                                        int LA31_518 = input.LA(9);

                                        if ( (LA31_518=='$'||(LA31_518>='0' && LA31_518<='9')||LA31_518=='_'||(LA31_518>='a' && LA31_518<='z')) ) {
                                            alt31=164;
                                        }
                                        else {
                                            alt31=19;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 'u':
                    {
                    int LA31_159 = input.LA(4);

                    if ( (LA31_159=='n') ) {
                        int LA31_252 = input.LA(5);

                        if ( (LA31_252=='t') ) {
                            int LA31_343 = input.LA(6);

                            if ( (LA31_343=='$'||(LA31_343>='0' && LA31_343<='9')||LA31_343=='_'||(LA31_343>='a' && LA31_343<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=23;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            case 'a':
                {
                int LA31_59 = input.LA(3);

                if ( (LA31_59=='s') ) {
                    switch ( input.LA(4) ) {
                    case 'e':
                        {
                        int LA31_253 = input.LA(5);

                        if ( (LA31_253=='$'||(LA31_253>='0' && LA31_253<='9')||LA31_253=='_'||(LA31_253>='a' && LA31_253<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=25;}
                        }
                        break;
                    case 't':
                        {
                        int LA31_254 = input.LA(5);

                        if ( (LA31_254=='$'||(LA31_254>='0' && LA31_254<='9')||LA31_254=='_'||(LA31_254>='a' && LA31_254<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=71;}
                        }
                        break;
                    default:
                        alt31=164;}

                }
                else {
                    alt31=164;}
                }
                break;
            case 'u':
                {
                int LA31_60 = input.LA(3);

                if ( (LA31_60=='r') ) {
                    int LA31_161 = input.LA(4);

                    if ( (LA31_161=='r') ) {
                        int LA31_255 = input.LA(5);

                        if ( (LA31_255=='e') ) {
                            int LA31_346 = input.LA(6);

                            if ( (LA31_346=='n') ) {
                                int LA31_420 = input.LA(7);

                                if ( (LA31_420=='t') ) {
                                    int LA31_477 = input.LA(8);

                                    if ( (LA31_477=='_') ) {
                                        int LA31_519 = input.LA(9);

                                        if ( (LA31_519=='t') ) {
                                            int LA31_543 = input.LA(10);

                                            if ( (LA31_543=='i') ) {
                                                int LA31_556 = input.LA(11);

                                                if ( (LA31_556=='m') ) {
                                                    int LA31_564 = input.LA(12);

                                                    if ( (LA31_564=='e') ) {
                                                        int LA31_571 = input.LA(13);

                                                        if ( (LA31_571=='s') ) {
                                                            int LA31_578 = input.LA(14);

                                                            if ( (LA31_578=='t') ) {
                                                                int LA31_582 = input.LA(15);

                                                                if ( (LA31_582=='a') ) {
                                                                    int LA31_585 = input.LA(16);

                                                                    if ( (LA31_585=='m') ) {
                                                                        int LA31_588 = input.LA(17);

                                                                        if ( (LA31_588=='p') ) {
                                                                            int LA31_590 = input.LA(18);

                                                                            if ( (LA31_590=='$'||(LA31_590>='0' && LA31_590<='9')||LA31_590=='_'||(LA31_590>='a' && LA31_590<='z')) ) {
                                                                                alt31=164;
                                                                            }
                                                                            else {
                                                                                alt31=72;}
                                                                        }
                                                                        else {
                                                                            alt31=164;}
                                                                    }
                                                                    else {
                                                                        alt31=164;}
                                                                }
                                                                else {
                                                                    alt31=164;}
                                                            }
                                                            else {
                                                                alt31=164;}
                                                        }
                                                        else {
                                                            alt31=164;}
                                                    }
                                                    else {
                                                        alt31=164;}
                                                }
                                                else {
                                                    alt31=164;}
                                            }
                                            else {
                                                alt31=164;}
                                        }
                                        else {
                                            alt31=164;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'w':
            {
            switch ( input.LA(2) ) {
            case 'i':
                {
                int LA31_61 = input.LA(3);

                if ( (LA31_61=='n') ) {
                    int LA31_162 = input.LA(4);

                    if ( (LA31_162=='d') ) {
                        int LA31_256 = input.LA(5);

                        if ( (LA31_256=='o') ) {
                            int LA31_347 = input.LA(6);

                            if ( (LA31_347=='w') ) {
                                int LA31_421 = input.LA(7);

                                if ( (LA31_421=='$'||(LA31_421>='0' && LA31_421<='9')||LA31_421=='_'||(LA31_421>='a' && LA31_421<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=2;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'h':
                {
                int LA31_62 = input.LA(3);

                if ( (LA31_62=='e') ) {
                    switch ( input.LA(4) ) {
                    case 'r':
                        {
                        int LA31_257 = input.LA(5);

                        if ( (LA31_257=='e') ) {
                            int LA31_348 = input.LA(6);

                            if ( (LA31_348=='$'||(LA31_348>='0' && LA31_348<='9')||LA31_348=='_'||(LA31_348>='a' && LA31_348<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=13;}
                        }
                        else {
                            alt31=164;}
                        }
                        break;
                    case 'n':
                        {
                        int LA31_258 = input.LA(5);

                        if ( (LA31_258=='$'||(LA31_258>='0' && LA31_258<='9')||LA31_258=='_'||(LA31_258>='a' && LA31_258<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=27;}
                        }
                        break;
                    default:
                        alt31=164;}

                }
                else {
                    alt31=164;}
                }
                break;
            case 'e':
                {
                int LA31_63 = input.LA(3);

                if ( (LA31_63=='e') ) {
                    int LA31_164 = input.LA(4);

                    if ( (LA31_164=='k') ) {
                        int LA31_259 = input.LA(5);

                        if ( (LA31_259=='d') ) {
                            int LA31_350 = input.LA(6);

                            if ( (LA31_350=='a') ) {
                                int LA31_423 = input.LA(7);

                                if ( (LA31_423=='y') ) {
                                    int LA31_479 = input.LA(8);

                                    if ( (LA31_479=='$'||(LA31_479>='0' && LA31_479<='9')||LA31_479=='_'||(LA31_479>='a' && LA31_479<='z')) ) {
                                        alt31=164;
                                    }
                                    else {
                                        alt31=68;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'i':
            {
            switch ( input.LA(2) ) {
            case 'n':
                {
                switch ( input.LA(3) ) {
                case 'n':
                    {
                    int LA31_165 = input.LA(4);

                    if ( (LA31_165=='e') ) {
                        int LA31_260 = input.LA(5);

                        if ( (LA31_260=='r') ) {
                            int LA31_351 = input.LA(6);

                            if ( (LA31_351=='$'||(LA31_351>='0' && LA31_351<='9')||LA31_351=='_'||(LA31_351>='a' && LA31_351<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=32;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 's':
                    {
                    switch ( input.LA(4) ) {
                    case 'e':
                        {
                        int LA31_261 = input.LA(5);

                        if ( (LA31_261=='r') ) {
                            int LA31_352 = input.LA(6);

                            if ( (LA31_352=='t') ) {
                                int LA31_425 = input.LA(7);

                                if ( (LA31_425=='$'||(LA31_425>='0' && LA31_425<='9')||LA31_425=='_'||(LA31_425>='a' && LA31_425<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=50;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                        }
                        break;
                    case 't':
                        {
                        int LA31_262 = input.LA(5);

                        if ( (LA31_262=='a') ) {
                            int LA31_353 = input.LA(6);

                            if ( (LA31_353=='n') ) {
                                int LA31_426 = input.LA(7);

                                if ( (LA31_426=='c') ) {
                                    int LA31_481 = input.LA(8);

                                    if ( (LA31_481=='e') ) {
                                        int LA31_521 = input.LA(9);

                                        if ( (LA31_521=='o') ) {
                                            int LA31_544 = input.LA(10);

                                            if ( (LA31_544=='f') ) {
                                                int LA31_557 = input.LA(11);

                                                if ( (LA31_557=='$'||(LA31_557>='0' && LA31_557<='9')||LA31_557=='_'||(LA31_557>='a' && LA31_557<='z')) ) {
                                                    alt31=164;
                                                }
                                                else {
                                                    alt31=70;}
                                            }
                                            else {
                                                alt31=164;}
                                        }
                                        else {
                                            alt31=164;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                        }
                        break;
                    default:
                        alt31=164;}

                    }
                    break;
                case 't':
                    {
                    int LA31_167 = input.LA(4);

                    if ( (LA31_167=='o') ) {
                        int LA31_263 = input.LA(5);

                        if ( (LA31_263=='$'||(LA31_263>='0' && LA31_263<='9')||LA31_263=='_'||(LA31_263>='a' && LA31_263<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=51;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 'd':
                    {
                    int LA31_168 = input.LA(4);

                    if ( (LA31_168=='e') ) {
                        int LA31_264 = input.LA(5);

                        if ( (LA31_264=='x') ) {
                            int LA31_355 = input.LA(6);

                            if ( (LA31_355=='$'||(LA31_355>='0' && LA31_355<='9')||LA31_355=='_'||(LA31_355>='a' && LA31_355<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=79;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case '$':
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
                case '_':
                case 'a':
                case 'b':
                case 'c':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt31=164;
                    }
                    break;
                default:
                    alt31=3;}

                }
                break;
            case 's':
                {
                switch ( input.LA(3) ) {
                case 't':
                    {
                    int LA31_170 = input.LA(4);

                    if ( (LA31_170=='r') ) {
                        int LA31_265 = input.LA(5);

                        if ( (LA31_265=='e') ) {
                            int LA31_356 = input.LA(6);

                            if ( (LA31_356=='a') ) {
                                int LA31_428 = input.LA(7);

                                if ( (LA31_428=='m') ) {
                                    int LA31_482 = input.LA(8);

                                    if ( (LA31_482=='$'||(LA31_482>='0' && LA31_482<='9')||LA31_482=='_'||(LA31_482>='a' && LA31_482<='z')) ) {
                                        alt31=164;
                                    }
                                    else {
                                        alt31=56;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case '$':
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
                case '_':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt31=164;
                    }
                    break;
                default:
                    alt31=38;}

                }
                break;
            case 'r':
                {
                int LA31_66 = input.LA(3);

                if ( (LA31_66=='s') ) {
                    int LA31_172 = input.LA(4);

                    if ( (LA31_172=='t') ) {
                        int LA31_266 = input.LA(5);

                        if ( (LA31_266=='r') ) {
                            int LA31_357 = input.LA(6);

                            if ( (LA31_357=='e') ) {
                                int LA31_429 = input.LA(7);

                                if ( (LA31_429=='a') ) {
                                    int LA31_483 = input.LA(8);

                                    if ( (LA31_483=='m') ) {
                                        int LA31_523 = input.LA(9);

                                        if ( (LA31_523=='$'||(LA31_523>='0' && LA31_523<='9')||LA31_523=='_'||(LA31_523>='a' && LA31_523<='z')) ) {
                                            alt31=164;
                                        }
                                        else {
                                            alt31=57;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'b':
            {
            switch ( input.LA(2) ) {
            case 'e':
                {
                int LA31_67 = input.LA(3);

                if ( (LA31_67=='t') ) {
                    int LA31_173 = input.LA(4);

                    if ( (LA31_173=='w') ) {
                        int LA31_267 = input.LA(5);

                        if ( (LA31_267=='e') ) {
                            int LA31_358 = input.LA(6);

                            if ( (LA31_358=='e') ) {
                                int LA31_430 = input.LA(7);

                                if ( (LA31_430=='n') ) {
                                    int LA31_484 = input.LA(8);

                                    if ( (LA31_484=='$'||(LA31_484>='0' && LA31_484<='9')||LA31_484=='_'||(LA31_484>='a' && LA31_484<='z')) ) {
                                        alt31=164;
                                    }
                                    else {
                                        alt31=4;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'y':
                {
                int LA31_68 = input.LA(3);

                if ( (LA31_68=='$'||(LA31_68>='0' && LA31_68<='9')||LA31_68=='_'||(LA31_68>='a' && LA31_68<='z')) ) {
                    alt31=164;
                }
                else {
                    alt31=39;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'l':
            {
            switch ( input.LA(2) ) {
            case 'i':
                {
                switch ( input.LA(3) ) {
                case 'k':
                    {
                    int LA31_175 = input.LA(4);

                    if ( (LA31_175=='e') ) {
                        int LA31_268 = input.LA(5);

                        if ( (LA31_268=='$'||(LA31_268>='0' && LA31_268<='9')||LA31_268=='_'||(LA31_268>='a' && LA31_268<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=5;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 'm':
                    {
                    int LA31_176 = input.LA(4);

                    if ( (LA31_176=='i') ) {
                        int LA31_269 = input.LA(5);

                        if ( (LA31_269=='t') ) {
                            int LA31_360 = input.LA(6);

                            if ( (LA31_360=='$'||(LA31_360>='0' && LA31_360<='9')||LA31_360=='_'||(LA31_360>='a' && LA31_360<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=95;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            case 'e':
                {
                int LA31_70 = input.LA(3);

                if ( (LA31_70=='f') ) {
                    int LA31_177 = input.LA(4);

                    if ( (LA31_177=='t') ) {
                        int LA31_270 = input.LA(5);

                        if ( (LA31_270=='$'||(LA31_270>='0' && LA31_270<='9')||LA31_270=='_'||(LA31_270>='a' && LA31_270<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=34;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'a':
                {
                int LA31_71 = input.LA(3);

                if ( (LA31_71=='s') ) {
                    int LA31_178 = input.LA(4);

                    if ( (LA31_178=='t') ) {
                        switch ( input.LA(5) ) {
                        case 'w':
                            {
                            int LA31_362 = input.LA(6);

                            if ( (LA31_362=='e') ) {
                                int LA31_432 = input.LA(7);

                                if ( (LA31_432=='e') ) {
                                    int LA31_485 = input.LA(8);

                                    if ( (LA31_485=='k') ) {
                                        int LA31_525 = input.LA(9);

                                        if ( (LA31_525=='d') ) {
                                            int LA31_546 = input.LA(10);

                                            if ( (LA31_546=='a') ) {
                                                int LA31_558 = input.LA(11);

                                                if ( (LA31_558=='y') ) {
                                                    int LA31_566 = input.LA(12);

                                                    if ( (LA31_566=='$'||(LA31_566>='0' && LA31_566<='9')||LA31_566=='_'||(LA31_566>='a' && LA31_566<='z')) ) {
                                                        alt31=164;
                                                    }
                                                    else {
                                                        alt31=69;}
                                                }
                                                else {
                                                    alt31=164;}
                                            }
                                            else {
                                                alt31=164;}
                                        }
                                        else {
                                            alt31=164;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                            }
                            break;
                        case '$':
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
                        case '_':
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                        case 'g':
                        case 'h':
                        case 'i':
                        case 'j':
                        case 'k':
                        case 'l':
                        case 'm':
                        case 'n':
                        case 'o':
                        case 'p':
                        case 'q':
                        case 'r':
                        case 's':
                        case 't':
                        case 'u':
                        case 'v':
                        case 'x':
                        case 'y':
                        case 'z':
                            {
                            alt31=164;
                            }
                            break;
                        default:
                            alt31=49;}

                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'r':
            {
            switch ( input.LA(2) ) {
            case 'e':
                {
                switch ( input.LA(3) ) {
                case 'g':
                    {
                    int LA31_179 = input.LA(4);

                    if ( (LA31_179=='e') ) {
                        int LA31_272 = input.LA(5);

                        if ( (LA31_272=='x') ) {
                            int LA31_364 = input.LA(6);

                            if ( (LA31_364=='p') ) {
                                int LA31_433 = input.LA(7);

                                if ( (LA31_433=='$'||(LA31_433>='0' && LA31_433<='9')||LA31_433=='_'||(LA31_433>='a' && LA31_433<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=6;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 't':
                    {
                    int LA31_180 = input.LA(4);

                    if ( (LA31_180=='a') ) {
                        int LA31_273 = input.LA(5);

                        if ( (LA31_273=='i') ) {
                            int LA31_365 = input.LA(6);

                            if ( (LA31_365=='n') ) {
                                int LA31_434 = input.LA(7);

                                if ( (LA31_434=='-') ) {
                                    int LA31_487 = input.LA(8);

                                    if ( (LA31_487=='u') ) {
                                        alt31=60;
                                    }
                                    else if ( (LA31_487=='i') ) {
                                        alt31=61;
                                    }
                                    else {
                                        if (state.backtracking>0) {state.failed=true; return ;}
                                        NoViableAltException nvae =
                                            new NoViableAltException("", 31, 487, input);

                                        throw nvae;
                                    }
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            case 'i':
                {
                int LA31_73 = input.LA(3);

                if ( (LA31_73=='g') ) {
                    int LA31_181 = input.LA(4);

                    if ( (LA31_181=='h') ) {
                        int LA31_274 = input.LA(5);

                        if ( (LA31_274=='t') ) {
                            int LA31_366 = input.LA(6);

                            if ( (LA31_366=='$'||(LA31_366>='0' && LA31_366<='9')||LA31_366=='_'||(LA31_366>='a' && LA31_366<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=35;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 's':
                {
                int LA31_74 = input.LA(3);

                if ( (LA31_74=='t') ) {
                    int LA31_182 = input.LA(4);

                    if ( (LA31_182=='r') ) {
                        int LA31_275 = input.LA(5);

                        if ( (LA31_275=='e') ) {
                            int LA31_367 = input.LA(6);

                            if ( (LA31_367=='a') ) {
                                int LA31_436 = input.LA(7);

                                if ( (LA31_436=='m') ) {
                                    int LA31_488 = input.LA(8);

                                    if ( (LA31_488=='$'||(LA31_488>='0' && LA31_488<='9')||LA31_488=='_'||(LA31_488>='a' && LA31_488<='z')) ) {
                                        alt31=164;
                                    }
                                    else {
                                        alt31=55;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'e':
            {
            switch ( input.LA(2) ) {
            case 's':
                {
                int LA31_75 = input.LA(3);

                if ( (LA31_75=='c') ) {
                    int LA31_183 = input.LA(4);

                    if ( (LA31_183=='a') ) {
                        int LA31_276 = input.LA(5);

                        if ( (LA31_276=='p') ) {
                            int LA31_368 = input.LA(6);

                            if ( (LA31_368=='e') ) {
                                int LA31_437 = input.LA(7);

                                if ( (LA31_437=='$'||(LA31_437>='0' && LA31_437<='9')||LA31_437=='_'||(LA31_437>='a' && LA31_437<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=7;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'v':
                {
                int LA31_76 = input.LA(3);

                if ( (LA31_76=='e') ) {
                    switch ( input.LA(4) ) {
                    case 'r':
                        {
                        int LA31_277 = input.LA(5);

                        if ( (LA31_277=='y') ) {
                            switch ( input.LA(6) ) {
                            case '-':
                                {
                                alt31=12;
                                }
                                break;
                            case '$':
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
                            case '_':
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                            case 'g':
                            case 'h':
                            case 'i':
                            case 'j':
                            case 'k':
                            case 'l':
                            case 'm':
                            case 'n':
                            case 'o':
                            case 'p':
                            case 'q':
                            case 'r':
                            case 's':
                            case 't':
                            case 'u':
                            case 'v':
                            case 'w':
                            case 'x':
                            case 'y':
                            case 'z':
                                {
                                alt31=164;
                                }
                                break;
                            default:
                                alt31=11;}

                        }
                        else {
                            alt31=164;}
                        }
                        break;
                    case 'n':
                        {
                        int LA31_278 = input.LA(5);

                        if ( (LA31_278=='t') ) {
                            int LA31_370 = input.LA(6);

                            if ( (LA31_370=='s') ) {
                                int LA31_440 = input.LA(7);

                                if ( (LA31_440=='$'||(LA31_440>='0' && LA31_440<='9')||LA31_440=='_'||(LA31_440>='a' && LA31_440<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=47;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                        }
                        break;
                    default:
                        alt31=164;}

                }
                else {
                    alt31=164;}
                }
                break;
            case 'l':
                {
                int LA31_77 = input.LA(3);

                if ( (LA31_77=='s') ) {
                    int LA31_185 = input.LA(4);

                    if ( (LA31_185=='e') ) {
                        int LA31_279 = input.LA(5);

                        if ( (LA31_279=='$'||(LA31_279>='0' && LA31_279<='9')||LA31_279=='_'||(LA31_279>='a' && LA31_279<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=26;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'n':
                {
                int LA31_78 = input.LA(3);

                if ( (LA31_78=='d') ) {
                    int LA31_186 = input.LA(4);

                    if ( (LA31_186=='$'||(LA31_186>='0' && LA31_186<='9')||LA31_186=='_'||(LA31_186>='a' && LA31_186<='z')) ) {
                        alt31=164;
                    }
                    else {
                        alt31=29;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'x':
                {
                int LA31_79 = input.LA(3);

                if ( (LA31_79=='i') ) {
                    int LA31_187 = input.LA(4);

                    if ( (LA31_187=='s') ) {
                        int LA31_281 = input.LA(5);

                        if ( (LA31_281=='t') ) {
                            int LA31_372 = input.LA(6);

                            if ( (LA31_372=='s') ) {
                                int LA31_441 = input.LA(7);

                                if ( (LA31_441=='$'||(LA31_441>='0' && LA31_441<='9')||LA31_441=='_'||(LA31_441>='a' && LA31_441<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=67;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'o':
            {
            switch ( input.LA(2) ) {
            case 'r':
                {
                switch ( input.LA(3) ) {
                case 'd':
                    {
                    int LA31_188 = input.LA(4);

                    if ( (LA31_188=='e') ) {
                        int LA31_282 = input.LA(5);

                        if ( (LA31_282=='r') ) {
                            int LA31_373 = input.LA(6);

                            if ( (LA31_373=='$'||(LA31_373>='0' && LA31_373<='9')||LA31_373=='_'||(LA31_373>='a' && LA31_373<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=52;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case '$':
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
                case '_':
                case 'a':
                case 'b':
                case 'c':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt31=164;
                    }
                    break;
                default:
                    alt31=8;}

                }
                break;
            case 'u':
                {
                int LA31_81 = input.LA(3);

                if ( (LA31_81=='t') ) {
                    switch ( input.LA(4) ) {
                    case 'e':
                        {
                        int LA31_283 = input.LA(5);

                        if ( (LA31_283=='r') ) {
                            int LA31_374 = input.LA(6);

                            if ( (LA31_374=='$'||(LA31_374>='0' && LA31_374<='9')||LA31_374=='_'||(LA31_374>='a' && LA31_374<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=31;}
                        }
                        else {
                            alt31=164;}
                        }
                        break;
                    case 'p':
                        {
                        int LA31_284 = input.LA(5);

                        if ( (LA31_284=='u') ) {
                            int LA31_375 = input.LA(6);

                            if ( (LA31_375=='t') ) {
                                int LA31_444 = input.LA(7);

                                if ( (LA31_444=='$'||(LA31_444>='0' && LA31_444<='9')||LA31_444=='_'||(LA31_444>='a' && LA31_444<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=46;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                        }
                        break;
                    default:
                        alt31=164;}

                }
                else {
                    alt31=164;}
                }
                break;
            case 'n':
                {
                int LA31_82 = input.LA(3);

                if ( (LA31_82=='$'||(LA31_82>='0' && LA31_82<='9')||LA31_82=='_'||(LA31_82>='a' && LA31_82<='z')) ) {
                    alt31=164;
                }
                else {
                    alt31=37;}
                }
                break;
            case 'f':
                {
                int LA31_83 = input.LA(3);

                if ( (LA31_83=='f') ) {
                    int LA31_192 = input.LA(4);

                    if ( (LA31_192=='s') ) {
                        int LA31_285 = input.LA(5);

                        if ( (LA31_285=='e') ) {
                            int LA31_376 = input.LA(6);

                            if ( (LA31_376=='t') ) {
                                int LA31_445 = input.LA(7);

                                if ( (LA31_445=='$'||(LA31_445>='0' && LA31_445<='9')||LA31_445=='_'||(LA31_445>='a' && LA31_445<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=96;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'a':
            {
            switch ( input.LA(2) ) {
            case 'n':
                {
                switch ( input.LA(3) ) {
                case 'd':
                    {
                    int LA31_193 = input.LA(4);

                    if ( (LA31_193=='$'||(LA31_193>='0' && LA31_193<='9')||LA31_193=='_'||(LA31_193>='a' && LA31_193<='z')) ) {
                        alt31=164;
                    }
                    else {
                        alt31=9;}
                    }
                    break;
                case 'y':
                    {
                    int LA31_194 = input.LA(4);

                    if ( (LA31_194=='$'||(LA31_194>='0' && LA31_194<='9')||LA31_194=='_'||(LA31_194>='a' && LA31_194<='z')) ) {
                        alt31=164;
                    }
                    else {
                        alt31=44;}
                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            case 's':
                {
                switch ( input.LA(3) ) {
                case 'c':
                    {
                    int LA31_195 = input.LA(4);

                    if ( (LA31_195=='$'||(LA31_195>='0' && LA31_195<='9')||LA31_195=='_'||(LA31_195>='a' && LA31_195<='z')) ) {
                        alt31=164;
                    }
                    else {
                        alt31=53;}
                    }
                    break;
                case '$':
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
                case '_':
                case 'a':
                case 'b':
                case 'd':
                case 'e':
                case 'f':
                case 'g':
                case 'h':
                case 'i':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'o':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'u':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    {
                    alt31=164;
                    }
                    break;
                default:
                    alt31=14;}

                }
                break;
            case 'v':
                {
                switch ( input.LA(3) ) {
                case 'g':
                    {
                    int LA31_197 = input.LA(4);

                    if ( (LA31_197=='$'||(LA31_197>='0' && LA31_197<='9')||LA31_197=='_'||(LA31_197>='a' && LA31_197<='z')) ) {
                        alt31=164;
                    }
                    else {
                        alt31=16;}
                    }
                    break;
                case 'e':
                    {
                    int LA31_198 = input.LA(4);

                    if ( (LA31_198=='d') ) {
                        int LA31_290 = input.LA(5);

                        if ( (LA31_290=='e') ) {
                            int LA31_377 = input.LA(6);

                            if ( (LA31_377=='v') ) {
                                int LA31_446 = input.LA(7);

                                if ( (LA31_446=='$'||(LA31_446>='0' && LA31_446<='9')||LA31_446=='_'||(LA31_446>='a' && LA31_446<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=22;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            case 'l':
                {
                int LA31_87 = input.LA(3);

                if ( (LA31_87=='l') ) {
                    int LA31_199 = input.LA(4);

                    if ( (LA31_199=='$'||(LA31_199>='0' && LA31_199<='9')||LA31_199=='_'||(LA31_199>='a' && LA31_199<='z')) ) {
                        alt31=164;
                    }
                    else {
                        alt31=43;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 't':
                {
                int LA31_88 = input.LA(3);

                if ( (LA31_88=='$'||(LA31_88>='0' && LA31_88<='9')||LA31_88=='_'||(LA31_88>='a' && LA31_88<='z')) ) {
                    alt31=164;
                }
                else {
                    alt31=78;}
                }
                break;
            case 'f':
                {
                int LA31_89 = input.LA(3);

                if ( (LA31_89=='t') ) {
                    int LA31_201 = input.LA(4);

                    if ( (LA31_201=='e') ) {
                        int LA31_292 = input.LA(5);

                        if ( (LA31_292=='r') ) {
                            int LA31_378 = input.LA(6);

                            if ( (LA31_378=='$'||(LA31_378>='0' && LA31_378<='9')||LA31_378=='_'||(LA31_378>='a' && LA31_378<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=103;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'n':
            {
            switch ( input.LA(2) ) {
            case 'o':
                {
                int LA31_90 = input.LA(3);

                if ( (LA31_90=='t') ) {
                    int LA31_202 = input.LA(4);

                    if ( (LA31_202=='$'||(LA31_202>='0' && LA31_202<='9')||LA31_202=='_'||(LA31_202>='a' && LA31_202<='z')) ) {
                        alt31=164;
                    }
                    else {
                        alt31=10;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'u':
                {
                int LA31_91 = input.LA(3);

                if ( (LA31_91=='l') ) {
                    int LA31_203 = input.LA(4);

                    if ( (LA31_203=='l') ) {
                        int LA31_294 = input.LA(5);

                        if ( (LA31_294=='$'||(LA31_294>='0' && LA31_294<='9')||LA31_294=='_'||(LA31_294>='a' && LA31_294<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=94;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 's':
            {
            switch ( input.LA(2) ) {
            case 'u':
                {
                int LA31_92 = input.LA(3);

                if ( (LA31_92=='m') ) {
                    int LA31_204 = input.LA(4);

                    if ( (LA31_204=='$'||(LA31_204>='0' && LA31_204<='9')||LA31_204=='_'||(LA31_204>='a' && LA31_204<='z')) ) {
                        alt31=164;
                    }
                    else {
                        alt31=15;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 't':
                {
                int LA31_93 = input.LA(3);

                if ( (LA31_93=='d') ) {
                    int LA31_205 = input.LA(4);

                    if ( (LA31_205=='d') ) {
                        int LA31_296 = input.LA(5);

                        if ( (LA31_296=='e') ) {
                            int LA31_380 = input.LA(6);

                            if ( (LA31_380=='v') ) {
                                int LA31_448 = input.LA(7);

                                if ( (LA31_448=='$'||(LA31_448>='0' && LA31_448<='9')||LA31_448=='_'||(LA31_448>='a' && LA31_448<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=21;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'e':
                {
                switch ( input.LA(3) ) {
                case 'l':
                    {
                    int LA31_206 = input.LA(4);

                    if ( (LA31_206=='e') ) {
                        int LA31_297 = input.LA(5);

                        if ( (LA31_297=='c') ) {
                            int LA31_381 = input.LA(6);

                            if ( (LA31_381=='t') ) {
                                int LA31_449 = input.LA(7);

                                if ( (LA31_449=='$'||(LA31_449>='0' && LA31_449<='9')||LA31_449=='_'||(LA31_449>='a' && LA31_449<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=24;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 't':
                    {
                    int LA31_207 = input.LA(4);

                    if ( (LA31_207=='$'||(LA31_207>='0' && LA31_207<='9')||LA31_207=='_'||(LA31_207>='a' && LA31_207<='z')) ) {
                        alt31=164;
                    }
                    else {
                        alt31=75;}
                    }
                    break;
                case 'c':
                    {
                    switch ( input.LA(4) ) {
                    case 'o':
                        {
                        int LA31_299 = input.LA(5);

                        if ( (LA31_299=='n') ) {
                            int LA31_382 = input.LA(6);

                            if ( (LA31_382=='d') ) {
                                switch ( input.LA(7) ) {
                                case 's':
                                    {
                                    int LA31_497 = input.LA(8);

                                    if ( (LA31_497=='$'||(LA31_497>='0' && LA31_497<='9')||LA31_497=='_'||(LA31_497>='a' && LA31_497<='z')) ) {
                                        alt31=164;
                                    }
                                    else {
                                        alt31=88;}
                                    }
                                    break;
                                case '$':
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
                                case '_':
                                case 'a':
                                case 'b':
                                case 'c':
                                case 'd':
                                case 'e':
                                case 'f':
                                case 'g':
                                case 'h':
                                case 'i':
                                case 'j':
                                case 'k':
                                case 'l':
                                case 'm':
                                case 'n':
                                case 'o':
                                case 'p':
                                case 'q':
                                case 'r':
                                case 't':
                                case 'u':
                                case 'v':
                                case 'w':
                                case 'x':
                                case 'y':
                                case 'z':
                                    {
                                    alt31=164;
                                    }
                                    break;
                                default:
                                    alt31=87;}

                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                        }
                        break;
                    case '$':
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
                    case '_':
                    case 'a':
                    case 'b':
                    case 'c':
                    case 'd':
                    case 'e':
                    case 'f':
                    case 'g':
                    case 'h':
                    case 'i':
                    case 'j':
                    case 'k':
                    case 'l':
                    case 'm':
                    case 'n':
                    case 'p':
                    case 'q':
                    case 'r':
                    case 's':
                    case 't':
                    case 'u':
                    case 'v':
                    case 'w':
                    case 'x':
                    case 'y':
                    case 'z':
                        {
                        alt31=164;
                        }
                        break;
                    default:
                        alt31=86;}

                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            case 'o':
                {
                int LA31_95 = input.LA(3);

                if ( (LA31_95=='m') ) {
                    int LA31_209 = input.LA(4);

                    if ( (LA31_209=='e') ) {
                        int LA31_301 = input.LA(5);

                        if ( (LA31_301=='$'||(LA31_301>='0' && LA31_301<='9')||LA31_301=='_'||(LA31_301>='a' && LA31_301<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=45;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'c':
                {
                int LA31_96 = input.LA(3);

                if ( (LA31_96=='h') ) {
                    int LA31_210 = input.LA(4);

                    if ( (LA31_210=='e') ) {
                        int LA31_302 = input.LA(5);

                        if ( (LA31_302=='m') ) {
                            int LA31_384 = input.LA(6);

                            if ( (LA31_384=='a') ) {
                                int LA31_451 = input.LA(7);

                                if ( (LA31_451=='$'||(LA31_451>='0' && LA31_451<='9')||LA31_451=='_'||(LA31_451>='a' && LA31_451<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=58;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'q':
                {
                int LA31_97 = input.LA(3);

                if ( (LA31_97=='l') ) {
                    int LA31_211 = input.LA(4);

                    if ( (LA31_211=='$'||(LA31_211>='0' && LA31_211<='9')||LA31_211=='_'||(LA31_211>='a' && LA31_211<='z')) ) {
                        alt31=164;
                    }
                    else {
                        alt31=63;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'n':
                {
                int LA31_98 = input.LA(3);

                if ( (LA31_98=='a') ) {
                    int LA31_212 = input.LA(4);

                    if ( (LA31_212=='p') ) {
                        int LA31_304 = input.LA(5);

                        if ( (LA31_304=='s') ) {
                            int LA31_385 = input.LA(6);

                            if ( (LA31_385=='h') ) {
                                int LA31_452 = input.LA(7);

                                if ( (LA31_452=='o') ) {
                                    int LA31_500 = input.LA(8);

                                    if ( (LA31_500=='t') ) {
                                        int LA31_530 = input.LA(9);

                                        if ( (LA31_530=='$'||(LA31_530>='0' && LA31_530<='9')||LA31_530=='_'||(LA31_530>='a' && LA31_530<='z')) ) {
                                            alt31=164;
                                        }
                                        else {
                                            alt31=74;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'm':
            {
            switch ( input.LA(2) ) {
            case 'a':
                {
                switch ( input.LA(3) ) {
                case 'x':
                    {
                    int LA31_213 = input.LA(4);

                    if ( (LA31_213=='$'||(LA31_213>='0' && LA31_213<='9')||LA31_213=='_'||(LA31_213>='a' && LA31_213<='z')) ) {
                        alt31=164;
                    }
                    else {
                        alt31=17;}
                    }
                    break;
                case 't':
                    {
                    int LA31_214 = input.LA(4);

                    if ( (LA31_214=='c') ) {
                        int LA31_306 = input.LA(5);

                        if ( (LA31_306=='h') ) {
                            switch ( input.LA(6) ) {
                            case '_':
                                {
                                int LA31_453 = input.LA(7);

                                if ( (LA31_453=='r') ) {
                                    int LA31_501 = input.LA(8);

                                    if ( (LA31_501=='e') ) {
                                        int LA31_531 = input.LA(9);

                                        if ( (LA31_531=='c') ) {
                                            int LA31_548 = input.LA(10);

                                            if ( (LA31_548=='o') ) {
                                                int LA31_559 = input.LA(11);

                                                if ( (LA31_559=='g') ) {
                                                    int LA31_567 = input.LA(12);

                                                    if ( (LA31_567=='n') ) {
                                                        int LA31_573 = input.LA(13);

                                                        if ( (LA31_573=='i') ) {
                                                            int LA31_579 = input.LA(14);

                                                            if ( (LA31_579=='z') ) {
                                                                int LA31_583 = input.LA(15);

                                                                if ( (LA31_583=='e') ) {
                                                                    int LA31_586 = input.LA(16);

                                                                    if ( (LA31_586=='$'||(LA31_586>='0' && LA31_586<='9')||LA31_586=='_'||(LA31_586>='a' && LA31_586<='z')) ) {
                                                                        alt31=164;
                                                                    }
                                                                    else {
                                                                        alt31=98;}
                                                                }
                                                                else {
                                                                    alt31=164;}
                                                            }
                                                            else {
                                                                alt31=164;}
                                                        }
                                                        else {
                                                            alt31=164;}
                                                    }
                                                    else {
                                                        alt31=164;}
                                                }
                                                else {
                                                    alt31=164;}
                                            }
                                            else {
                                                alt31=164;}
                                        }
                                        else {
                                            alt31=164;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                                }
                                break;
                            case 'e':
                                {
                                int LA31_454 = input.LA(7);

                                if ( (LA31_454=='s') ) {
                                    int LA31_502 = input.LA(8);

                                    if ( (LA31_502=='$'||(LA31_502>='0' && LA31_502<='9')||LA31_502=='_'||(LA31_502>='a' && LA31_502<='z')) ) {
                                        alt31=164;
                                    }
                                    else {
                                        alt31=102;}
                                }
                                else {
                                    alt31=164;}
                                }
                                break;
                            default:
                                alt31=164;}

                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            case 'i':
                {
                switch ( input.LA(3) ) {
                case 'n':
                    {
                    switch ( input.LA(4) ) {
                    case 'u':
                        {
                        int LA31_307 = input.LA(5);

                        if ( (LA31_307=='t') ) {
                            int LA31_387 = input.LA(6);

                            if ( (LA31_387=='e') ) {
                                switch ( input.LA(7) ) {
                                case 's':
                                    {
                                    int LA31_503 = input.LA(8);

                                    if ( (LA31_503=='$'||(LA31_503>='0' && LA31_503<='9')||LA31_503=='_'||(LA31_503>='a' && LA31_503<='z')) ) {
                                        alt31=164;
                                    }
                                    else {
                                        alt31=85;}
                                    }
                                    break;
                                case '$':
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
                                case '_':
                                case 'a':
                                case 'b':
                                case 'c':
                                case 'd':
                                case 'e':
                                case 'f':
                                case 'g':
                                case 'h':
                                case 'i':
                                case 'j':
                                case 'k':
                                case 'l':
                                case 'm':
                                case 'n':
                                case 'o':
                                case 'p':
                                case 'q':
                                case 'r':
                                case 't':
                                case 'u':
                                case 'v':
                                case 'w':
                                case 'x':
                                case 'y':
                                case 'z':
                                    {
                                    alt31=164;
                                    }
                                    break;
                                default:
                                    alt31=84;}

                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                        }
                        break;
                    case '$':
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
                    case '_':
                    case 'a':
                    case 'b':
                    case 'c':
                    case 'd':
                    case 'e':
                    case 'f':
                    case 'g':
                    case 'h':
                    case 'i':
                    case 'j':
                    case 'k':
                    case 'l':
                    case 'm':
                    case 'n':
                    case 'o':
                    case 'p':
                    case 'q':
                    case 'r':
                    case 's':
                    case 't':
                    case 'v':
                    case 'w':
                    case 'x':
                    case 'y':
                    case 'z':
                        {
                        alt31=164;
                        }
                        break;
                    default:
                        alt31=18;}

                    }
                    break;
                case 'l':
                    {
                    int LA31_216 = input.LA(4);

                    if ( (LA31_216=='l') ) {
                        int LA31_309 = input.LA(5);

                        if ( (LA31_309=='i') ) {
                            int LA31_388 = input.LA(6);

                            if ( (LA31_388=='s') ) {
                                int LA31_456 = input.LA(7);

                                if ( (LA31_456=='e') ) {
                                    int LA31_505 = input.LA(8);

                                    if ( (LA31_505=='c') ) {
                                        int LA31_534 = input.LA(9);

                                        if ( (LA31_534=='o') ) {
                                            int LA31_549 = input.LA(10);

                                            if ( (LA31_549=='n') ) {
                                                int LA31_560 = input.LA(11);

                                                if ( (LA31_560=='d') ) {
                                                    switch ( input.LA(12) ) {
                                                    case 's':
                                                        {
                                                        int LA31_574 = input.LA(13);

                                                        if ( (LA31_574=='$'||(LA31_574>='0' && LA31_574<='9')||LA31_574=='_'||(LA31_574>='a' && LA31_574<='z')) ) {
                                                            alt31=164;
                                                        }
                                                        else {
                                                            alt31=91;}
                                                        }
                                                        break;
                                                    case '$':
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
                                                    case '_':
                                                    case 'a':
                                                    case 'b':
                                                    case 'c':
                                                    case 'd':
                                                    case 'e':
                                                    case 'f':
                                                    case 'g':
                                                    case 'h':
                                                    case 'i':
                                                    case 'j':
                                                    case 'k':
                                                    case 'l':
                                                    case 'm':
                                                    case 'n':
                                                    case 'o':
                                                    case 'p':
                                                    case 'q':
                                                    case 'r':
                                                    case 't':
                                                    case 'u':
                                                    case 'v':
                                                    case 'w':
                                                    case 'x':
                                                    case 'y':
                                                    case 'z':
                                                        {
                                                        alt31=164;
                                                        }
                                                        break;
                                                    default:
                                                        alt31=90;}

                                                }
                                                else {
                                                    alt31=164;}
                                            }
                                            else {
                                                alt31=164;}
                                        }
                                        else {
                                            alt31=164;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            case 'e':
                {
                switch ( input.LA(3) ) {
                case 'd':
                    {
                    int LA31_217 = input.LA(4);

                    if ( (LA31_217=='i') ) {
                        int LA31_310 = input.LA(5);

                        if ( (LA31_310=='a') ) {
                            int LA31_389 = input.LA(6);

                            if ( (LA31_389=='n') ) {
                                int LA31_457 = input.LA(7);

                                if ( (LA31_457=='$'||(LA31_457>='0' && LA31_457<='9')||LA31_457=='_'||(LA31_457>='a' && LA31_457<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=20;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 't':
                    {
                    int LA31_218 = input.LA(4);

                    if ( (LA31_218=='a') ) {
                        int LA31_311 = input.LA(5);

                        if ( (LA31_311=='d') ) {
                            int LA31_390 = input.LA(6);

                            if ( (LA31_390=='a') ) {
                                int LA31_458 = input.LA(7);

                                if ( (LA31_458=='t') ) {
                                    int LA31_507 = input.LA(8);

                                    if ( (LA31_507=='a') ) {
                                        int LA31_535 = input.LA(9);

                                        if ( (LA31_535=='s') ) {
                                            int LA31_550 = input.LA(10);

                                            if ( (LA31_550=='q') ) {
                                                int LA31_561 = input.LA(11);

                                                if ( (LA31_561=='l') ) {
                                                    int LA31_569 = input.LA(12);

                                                    if ( (LA31_569=='$'||(LA31_569>='0' && LA31_569<='9')||LA31_569=='_'||(LA31_569>='a' && LA31_569<='z')) ) {
                                                        alt31=164;
                                                    }
                                                    else {
                                                        alt31=64;}
                                                }
                                                else {
                                                    alt31=164;}
                                            }
                                            else {
                                                alt31=164;}
                                        }
                                        else {
                                            alt31=164;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 'a':
                    {
                    int LA31_219 = input.LA(4);

                    if ( (LA31_219=='s') ) {
                        int LA31_312 = input.LA(5);

                        if ( (LA31_312=='u') ) {
                            int LA31_391 = input.LA(6);

                            if ( (LA31_391=='r') ) {
                                int LA31_459 = input.LA(7);

                                if ( (LA31_459=='e') ) {
                                    int LA31_508 = input.LA(8);

                                    if ( (LA31_508=='s') ) {
                                        int LA31_536 = input.LA(9);

                                        if ( (LA31_536=='$'||(LA31_536>='0' && LA31_536<='9')||LA31_536=='_'||(LA31_536>='a' && LA31_536<='z')) ) {
                                            alt31=164;
                                        }
                                        else {
                                            alt31=99;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            case 's':
                {
                int LA31_102 = input.LA(3);

                if ( (LA31_102=='e') ) {
                    int LA31_220 = input.LA(4);

                    if ( (LA31_220=='c') ) {
                        int LA31_313 = input.LA(5);

                        if ( (LA31_313=='$'||(LA31_313>='0' && LA31_313<='9')||LA31_313=='_'||(LA31_313>='a' && LA31_313<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=89;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 't':
            {
            switch ( input.LA(2) ) {
            case 'h':
                {
                int LA31_103 = input.LA(3);

                if ( (LA31_103=='e') ) {
                    int LA31_221 = input.LA(4);

                    if ( (LA31_221=='n') ) {
                        int LA31_314 = input.LA(5);

                        if ( (LA31_314=='$'||(LA31_314>='0' && LA31_314<='9')||LA31_314=='_'||(LA31_314>='a' && LA31_314<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=28;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'r':
                {
                int LA31_104 = input.LA(3);

                if ( (LA31_104=='u') ) {
                    int LA31_222 = input.LA(4);

                    if ( (LA31_222=='e') ) {
                        int LA31_315 = input.LA(5);

                        if ( (LA31_315=='$'||(LA31_315>='0' && LA31_315<='9')||LA31_315=='_'||(LA31_315>='a' && LA31_315<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=92;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'f':
            {
            switch ( input.LA(2) ) {
            case 'r':
                {
                int LA31_105 = input.LA(3);

                if ( (LA31_105=='o') ) {
                    int LA31_223 = input.LA(4);

                    if ( (LA31_223=='m') ) {
                        int LA31_316 = input.LA(5);

                        if ( (LA31_316=='$'||(LA31_316>='0' && LA31_316<='9')||LA31_316=='_'||(LA31_316>='a' && LA31_316<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=30;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'u':
                {
                int LA31_106 = input.LA(3);

                if ( (LA31_106=='l') ) {
                    int LA31_224 = input.LA(4);

                    if ( (LA31_224=='l') ) {
                        int LA31_317 = input.LA(5);

                        if ( (LA31_317=='$'||(LA31_317>='0' && LA31_317<='9')||LA31_317=='_'||(LA31_317>='a' && LA31_317<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=36;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'i':
                {
                int LA31_107 = input.LA(3);

                if ( (LA31_107=='r') ) {
                    int LA31_225 = input.LA(4);

                    if ( (LA31_225=='s') ) {
                        int LA31_318 = input.LA(5);

                        if ( (LA31_318=='t') ) {
                            int LA31_397 = input.LA(6);

                            if ( (LA31_397=='$'||(LA31_397>='0' && LA31_397<='9')||LA31_397=='_'||(LA31_397>='a' && LA31_397<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=48;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'a':
                {
                int LA31_108 = input.LA(3);

                if ( (LA31_108=='l') ) {
                    int LA31_226 = input.LA(4);

                    if ( (LA31_226=='s') ) {
                        int LA31_319 = input.LA(5);

                        if ( (LA31_319=='e') ) {
                            int LA31_398 = input.LA(6);

                            if ( (LA31_398=='$'||(LA31_398>='0' && LA31_398<='9')||LA31_398=='_'||(LA31_398>='a' && LA31_398<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=93;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'o':
                {
                int LA31_109 = input.LA(3);

                if ( (LA31_109=='r') ) {
                    int LA31_227 = input.LA(4);

                    if ( (LA31_227=='$'||(LA31_227>='0' && LA31_227<='9')||LA31_227=='_'||(LA31_227>='a' && LA31_227<='z')) ) {
                        alt31=164;
                    }
                    else {
                        alt31=104;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'j':
            {
            int LA31_15 = input.LA(2);

            if ( (LA31_15=='o') ) {
                int LA31_110 = input.LA(3);

                if ( (LA31_110=='i') ) {
                    int LA31_228 = input.LA(4);

                    if ( (LA31_228=='n') ) {
                        int LA31_321 = input.LA(5);

                        if ( (LA31_321=='$'||(LA31_321>='0' && LA31_321<='9')||LA31_321=='_'||(LA31_321>='a' && LA31_321<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=33;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
            }
            else {
                alt31=164;}
            }
            break;
        case 'g':
            {
            int LA31_16 = input.LA(2);

            if ( (LA31_16=='r') ) {
                int LA31_111 = input.LA(3);

                if ( (LA31_111=='o') ) {
                    int LA31_229 = input.LA(4);

                    if ( (LA31_229=='u') ) {
                        int LA31_322 = input.LA(5);

                        if ( (LA31_322=='p') ) {
                            int LA31_400 = input.LA(6);

                            if ( (LA31_400=='$'||(LA31_400>='0' && LA31_400<='9')||LA31_400=='_'||(LA31_400>='a' && LA31_400<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=40;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
            }
            else {
                alt31=164;}
            }
            break;
        case 'h':
            {
            switch ( input.LA(2) ) {
            case 'a':
                {
                int LA31_112 = input.LA(3);

                if ( (LA31_112=='v') ) {
                    int LA31_230 = input.LA(4);

                    if ( (LA31_230=='i') ) {
                        int LA31_323 = input.LA(5);

                        if ( (LA31_323=='n') ) {
                            int LA31_401 = input.LA(6);

                            if ( (LA31_401=='g') ) {
                                int LA31_463 = input.LA(7);

                                if ( (LA31_463=='$'||(LA31_463>='0' && LA31_463<='9')||LA31_463=='_'||(LA31_463>='a' && LA31_463<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=41;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'o':
                {
                int LA31_113 = input.LA(3);

                if ( (LA31_113=='u') ) {
                    int LA31_231 = input.LA(4);

                    if ( (LA31_231=='r') ) {
                        switch ( input.LA(5) ) {
                        case 's':
                            {
                            int LA31_402 = input.LA(6);

                            if ( (LA31_402=='$'||(LA31_402>='0' && LA31_402<='9')||LA31_402=='_'||(LA31_402>='a' && LA31_402<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=83;}
                            }
                            break;
                        case '$':
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
                        case '_':
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                        case 'g':
                        case 'h':
                        case 'i':
                        case 'j':
                        case 'k':
                        case 'l':
                        case 'm':
                        case 'n':
                        case 'o':
                        case 'p':
                        case 'q':
                        case 'r':
                        case 't':
                        case 'u':
                        case 'v':
                        case 'w':
                        case 'x':
                        case 'y':
                        case 'z':
                            {
                            alt31=164;
                            }
                            break;
                        default:
                            alt31=82;}

                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'd':
            {
            switch ( input.LA(2) ) {
            case 'i':
                {
                int LA31_114 = input.LA(3);

                if ( (LA31_114=='s') ) {
                    int LA31_232 = input.LA(4);

                    if ( (LA31_232=='t') ) {
                        int LA31_325 = input.LA(5);

                        if ( (LA31_325=='i') ) {
                            int LA31_404 = input.LA(6);

                            if ( (LA31_404=='n') ) {
                                int LA31_465 = input.LA(7);

                                if ( (LA31_465=='c') ) {
                                    int LA31_510 = input.LA(8);

                                    if ( (LA31_510=='t') ) {
                                        int LA31_537 = input.LA(9);

                                        if ( (LA31_537=='$'||(LA31_537>='0' && LA31_537<='9')||LA31_537=='_'||(LA31_537>='a' && LA31_537<='z')) ) {
                                            alt31=164;
                                        }
                                        else {
                                            alt31=42;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            case 'e':
                {
                switch ( input.LA(3) ) {
                case 's':
                    {
                    int LA31_233 = input.LA(4);

                    if ( (LA31_233=='c') ) {
                        int LA31_326 = input.LA(5);

                        if ( (LA31_326=='$'||(LA31_326>='0' && LA31_326<='9')||LA31_326=='_'||(LA31_326>='a' && LA31_326<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=54;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 'l':
                    {
                    int LA31_234 = input.LA(4);

                    if ( (LA31_234=='e') ) {
                        int LA31_327 = input.LA(5);

                        if ( (LA31_327=='t') ) {
                            int LA31_406 = input.LA(6);

                            if ( (LA31_406=='e') ) {
                                int LA31_466 = input.LA(7);

                                if ( (LA31_466=='$'||(LA31_466>='0' && LA31_466<='9')||LA31_466=='_'||(LA31_466>='a' && LA31_466<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=73;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 'f':
                    {
                    int LA31_235 = input.LA(4);

                    if ( (LA31_235=='i') ) {
                        int LA31_328 = input.LA(5);

                        if ( (LA31_328=='n') ) {
                            int LA31_407 = input.LA(6);

                            if ( (LA31_407=='e') ) {
                                int LA31_467 = input.LA(7);

                                if ( (LA31_467=='$'||(LA31_467>='0' && LA31_467<='9')||LA31_467=='_'||(LA31_467>='a' && LA31_467<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=100;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            case 'a':
                {
                int LA31_116 = input.LA(3);

                if ( (LA31_116=='y') ) {
                    switch ( input.LA(4) ) {
                    case 's':
                        {
                        int LA31_329 = input.LA(5);

                        if ( (LA31_329=='$'||(LA31_329>='0' && LA31_329<='9')||LA31_329=='_'||(LA31_329>='a' && LA31_329<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=81;}
                        }
                        break;
                    case '$':
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
                    case '_':
                    case 'a':
                    case 'b':
                    case 'c':
                    case 'd':
                    case 'e':
                    case 'f':
                    case 'g':
                    case 'h':
                    case 'i':
                    case 'j':
                    case 'k':
                    case 'l':
                    case 'm':
                    case 'n':
                    case 'o':
                    case 'p':
                    case 'q':
                    case 'r':
                    case 't':
                    case 'u':
                    case 'v':
                    case 'w':
                    case 'x':
                    case 'y':
                    case 'z':
                        {
                        alt31=164;
                        }
                        break;
                    default:
                        alt31=80;}

                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'u':
            {
            switch ( input.LA(2) ) {
            case 'n':
                {
                switch ( input.LA(3) ) {
                case 'i':
                    {
                    int LA31_237 = input.LA(4);

                    if ( (LA31_237=='d') ) {
                        int LA31_331 = input.LA(5);

                        if ( (LA31_331=='i') ) {
                            int LA31_409 = input.LA(6);

                            if ( (LA31_409=='r') ) {
                                int LA31_468 = input.LA(7);

                                if ( (LA31_468=='e') ) {
                                    int LA31_513 = input.LA(8);

                                    if ( (LA31_513=='c') ) {
                                        int LA31_538 = input.LA(9);

                                        if ( (LA31_538=='t') ) {
                                            int LA31_553 = input.LA(10);

                                            if ( (LA31_553=='i') ) {
                                                int LA31_562 = input.LA(11);

                                                if ( (LA31_562=='o') ) {
                                                    int LA31_570 = input.LA(12);

                                                    if ( (LA31_570=='n') ) {
                                                        int LA31_577 = input.LA(13);

                                                        if ( (LA31_577=='a') ) {
                                                            int LA31_581 = input.LA(14);

                                                            if ( (LA31_581=='l') ) {
                                                                int LA31_584 = input.LA(15);

                                                                if ( (LA31_584=='$'||(LA31_584>='0' && LA31_584<='9')||LA31_584=='_'||(LA31_584>='a' && LA31_584<='z')) ) {
                                                                    alt31=164;
                                                                }
                                                                else {
                                                                    alt31=59;}
                                                            }
                                                            else {
                                                                alt31=164;}
                                                        }
                                                        else {
                                                            alt31=164;}
                                                    }
                                                    else {
                                                        alt31=164;}
                                                }
                                                else {
                                                    alt31=164;}
                                            }
                                            else {
                                                alt31=164;}
                                        }
                                        else {
                                            alt31=164;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 't':
                    {
                    int LA31_238 = input.LA(4);

                    if ( (LA31_238=='i') ) {
                        int LA31_332 = input.LA(5);

                        if ( (LA31_332=='l') ) {
                            int LA31_410 = input.LA(6);

                            if ( (LA31_410=='$'||(LA31_410>='0' && LA31_410<='9')||LA31_410=='_'||(LA31_410>='a' && LA31_410<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=77;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            case 'p':
                {
                int LA31_118 = input.LA(3);

                if ( (LA31_118=='d') ) {
                    int LA31_239 = input.LA(4);

                    if ( (LA31_239=='a') ) {
                        int LA31_333 = input.LA(5);

                        if ( (LA31_333=='t') ) {
                            int LA31_411 = input.LA(6);

                            if ( (LA31_411=='e') ) {
                                int LA31_470 = input.LA(7);

                                if ( (LA31_470=='$'||(LA31_470>='0' && LA31_470<='9')||LA31_470=='_'||(LA31_470>='a' && LA31_470<='z')) ) {
                                    alt31=164;
                                }
                                else {
                                    alt31=97;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'p':
            {
            switch ( input.LA(2) ) {
            case 'a':
                {
                switch ( input.LA(3) ) {
                case 't':
                    {
                    int LA31_240 = input.LA(4);

                    if ( (LA31_240=='t') ) {
                        int LA31_334 = input.LA(5);

                        if ( (LA31_334=='e') ) {
                            int LA31_412 = input.LA(6);

                            if ( (LA31_412=='r') ) {
                                int LA31_471 = input.LA(7);

                                if ( (LA31_471=='n') ) {
                                    int LA31_515 = input.LA(8);

                                    if ( (LA31_515=='$'||(LA31_515>='0' && LA31_515<='9')||LA31_515=='_'||(LA31_515>='a' && LA31_515<='z')) ) {
                                        alt31=164;
                                    }
                                    else {
                                        alt31=62;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 'r':
                    {
                    int LA31_241 = input.LA(4);

                    if ( (LA31_241=='t') ) {
                        int LA31_335 = input.LA(5);

                        if ( (LA31_335=='i') ) {
                            int LA31_413 = input.LA(6);

                            if ( (LA31_413=='t') ) {
                                int LA31_472 = input.LA(7);

                                if ( (LA31_472=='i') ) {
                                    int LA31_516 = input.LA(8);

                                    if ( (LA31_516=='o') ) {
                                        int LA31_540 = input.LA(9);

                                        if ( (LA31_540=='n') ) {
                                            int LA31_554 = input.LA(10);

                                            if ( (LA31_554=='$'||(LA31_554>='0' && LA31_554<='9')||LA31_554=='_'||(LA31_554>='a' && LA31_554<='z')) ) {
                                                alt31=164;
                                            }
                                            else {
                                                alt31=101;}
                                        }
                                        else {
                                            alt31=164;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            case 'r':
                {
                switch ( input.LA(3) ) {
                case 'e':
                    {
                    int LA31_242 = input.LA(4);

                    if ( (LA31_242=='v') ) {
                        int LA31_336 = input.LA(5);

                        if ( (LA31_336=='$'||(LA31_336>='0' && LA31_336<='9')||LA31_336=='_'||(LA31_336>='a' && LA31_336<='z')) ) {
                            alt31=164;
                        }
                        else {
                            alt31=65;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                case 'i':
                    {
                    int LA31_243 = input.LA(4);

                    if ( (LA31_243=='o') ) {
                        int LA31_337 = input.LA(5);

                        if ( (LA31_337=='r') ) {
                            int LA31_415 = input.LA(6);

                            if ( (LA31_415=='$'||(LA31_415>='0' && LA31_415<='9')||LA31_415=='_'||(LA31_415>='a' && LA31_415<='z')) ) {
                                alt31=164;
                            }
                            else {
                                alt31=66;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                    }
                    break;
                default:
                    alt31=164;}

                }
                break;
            default:
                alt31=164;}

            }
            break;
        case 'v':
            {
            int LA31_21 = input.LA(2);

            if ( (LA31_21=='a') ) {
                int LA31_121 = input.LA(3);

                if ( (LA31_121=='r') ) {
                    int LA31_244 = input.LA(4);

                    if ( (LA31_244=='i') ) {
                        int LA31_338 = input.LA(5);

                        if ( (LA31_338=='a') ) {
                            int LA31_416 = input.LA(6);

                            if ( (LA31_416=='b') ) {
                                int LA31_474 = input.LA(7);

                                if ( (LA31_474=='l') ) {
                                    int LA31_517 = input.LA(8);

                                    if ( (LA31_517=='e') ) {
                                        int LA31_541 = input.LA(9);

                                        if ( (LA31_541=='$'||(LA31_541>='0' && LA31_541<='9')||LA31_541=='_'||(LA31_541>='a' && LA31_541<='z')) ) {
                                            alt31=164;
                                        }
                                        else {
                                            alt31=76;}
                                    }
                                    else {
                                        alt31=164;}
                                }
                                else {
                                    alt31=164;}
                            }
                            else {
                                alt31=164;}
                        }
                        else {
                            alt31=164;}
                    }
                    else {
                        alt31=164;}
                }
                else {
                    alt31=164;}
            }
            else {
                alt31=164;}
            }
            break;
        case '-':
            {
            switch ( input.LA(2) ) {
            case '>':
                {
                alt31=105;
                }
                break;
            case '=':
                {
                alt31=127;
                }
                break;
            case '-':
                {
                alt31=128;
                }
                break;
            default:
                alt31=126;}

            }
            break;
        case '=':
            {
            int LA31_23 = input.LA(2);

            if ( (LA31_23=='=') ) {
                alt31=117;
            }
            else {
                alt31=106;}
            }
            break;
        case '<':
            {
            switch ( input.LA(2) ) {
            case '>':
                {
                alt31=107;
                }
                break;
            case '<':
                {
                int LA31_129 = input.LA(3);

                if ( (LA31_129=='=') ) {
                    alt31=140;
                }
                else {
                    alt31=139;}
                }
                break;
            case '=':
                {
                alt31=141;
                }
                break;
            default:
                alt31=142;}

            }
            break;
        case '?':
            {
            alt31=108;
            }
            break;
        case '(':
            {
            alt31=109;
            }
            break;
        case ')':
            {
            alt31=110;
            }
            break;
        case '[':
            {
            alt31=111;
            }
            break;
        case ']':
            {
            alt31=112;
            }
            break;
        case '{':
            {
            alt31=113;
            }
            break;
        case '}':
            {
            alt31=114;
            }
            break;
        case ':':
            {
            alt31=115;
            }
            break;
        case ',':
            {
            alt31=116;
            }
            break;
        case '!':
            {
            int LA31_34 = input.LA(2);

            if ( (LA31_34=='=') ) {
                alt31=120;
            }
            else {
                alt31=118;}
            }
            break;
        case '~':
            {
            alt31=119;
            }
            break;
        case '/':
            {
            switch ( input.LA(2) ) {
            case '=':
                {
                alt31=122;
                }
                break;
            case '/':
                {
                alt31=159;
                }
                break;
            case '*':
                {
                alt31=160;
                }
                break;
            default:
                alt31=121;}

            }
            break;
        case '+':
            {
            switch ( input.LA(2) ) {
            case '=':
                {
                alt31=124;
                }
                break;
            case '+':
                {
                alt31=125;
                }
                break;
            default:
                alt31=123;}

            }
            break;
        case '*':
            {
            int LA31_38 = input.LA(2);

            if ( (LA31_38=='=') ) {
                alt31=130;
            }
            else {
                alt31=129;}
            }
            break;
        case '%':
            {
            int LA31_39 = input.LA(2);

            if ( (LA31_39=='=') ) {
                alt31=132;
            }
            else {
                alt31=131;}
            }
            break;
        case '>':
            {
            switch ( input.LA(2) ) {
            case '>':
                {
                switch ( input.LA(3) ) {
                case '=':
                    {
                    alt31=134;
                    }
                    break;
                case '>':
                    {
                    int LA31_248 = input.LA(4);

                    if ( (LA31_248=='=') ) {
                        alt31=136;
                    }
                    else {
                        alt31=135;}
                    }
                    break;
                default:
                    alt31=133;}

                }
                break;
            case '=':
                {
                alt31=137;
                }
                break;
            default:
                alt31=138;}

            }
            break;
        case '^':
            {
            int LA31_41 = input.LA(2);

            if ( (LA31_41=='=') ) {
                alt31=144;
            }
            else {
                alt31=143;}
            }
            break;
        case '|':
            {
            switch ( input.LA(2) ) {
            case '=':
                {
                alt31=146;
                }
                break;
            case '|':
                {
                alt31=147;
                }
                break;
            default:
                alt31=145;}

            }
            break;
        case '&':
            {
            switch ( input.LA(2) ) {
            case '=':
                {
                alt31=149;
                }
                break;
            case '&':
                {
                alt31=150;
                }
                break;
            default:
                alt31=148;}

            }
            break;
        case ';':
            {
            alt31=151;
            }
            break;
        case '.':
            {
            int LA31_45 = input.LA(2);

            if ( ((LA31_45>='0' && LA31_45<='9')) ) {
                alt31=165;
            }
            else {
                alt31=152;}
            }
            break;
        case '\u18FF':
            {
            alt31=153;
            }
            break;
        case '\u18FE':
            {
            alt31=154;
            }
            break;
        case '\u18FD':
            {
            alt31=155;
            }
            break;
        case '\\':
            {
            alt31=156;
            }
            break;
        case '@':
            {
            alt31=157;
            }
            break;
        case '\t':
        case '\n':
        case '\f':
        case '\r':
        case ' ':
            {
            alt31=158;
            }
            break;
        case '`':
            {
            alt31=161;
            }
            break;
        case '\'':
            {
            alt31=162;
            }
            break;
        case '\"':
            {
            alt31=163;
            }
            break;
        case '$':
        case '_':
        case 'k':
        case 'q':
        case 'x':
        case 'y':
        case 'z':
            {
            alt31=164;
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
            {
            alt31=165;
            }
            break;
        default:
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 31, 0, input);

            throw nvae;
        }

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
                // EsperEPL2Grammar.g:1:897: FOR
                {
                mFOR(); if (state.failed) return ;

                }
                break;
            case 105 :
                // EsperEPL2Grammar.g:1:901: FOLLOWED_BY
                {
                mFOLLOWED_BY(); if (state.failed) return ;

                }
                break;
            case 106 :
                // EsperEPL2Grammar.g:1:913: EQUALS
                {
                mEQUALS(); if (state.failed) return ;

                }
                break;
            case 107 :
                // EsperEPL2Grammar.g:1:920: SQL_NE
                {
                mSQL_NE(); if (state.failed) return ;

                }
                break;
            case 108 :
                // EsperEPL2Grammar.g:1:927: QUESTION
                {
                mQUESTION(); if (state.failed) return ;

                }
                break;
            case 109 :
                // EsperEPL2Grammar.g:1:936: LPAREN
                {
                mLPAREN(); if (state.failed) return ;

                }
                break;
            case 110 :
                // EsperEPL2Grammar.g:1:943: RPAREN
                {
                mRPAREN(); if (state.failed) return ;

                }
                break;
            case 111 :
                // EsperEPL2Grammar.g:1:950: LBRACK
                {
                mLBRACK(); if (state.failed) return ;

                }
                break;
            case 112 :
                // EsperEPL2Grammar.g:1:957: RBRACK
                {
                mRBRACK(); if (state.failed) return ;

                }
                break;
            case 113 :
                // EsperEPL2Grammar.g:1:964: LCURLY
                {
                mLCURLY(); if (state.failed) return ;

                }
                break;
            case 114 :
                // EsperEPL2Grammar.g:1:971: RCURLY
                {
                mRCURLY(); if (state.failed) return ;

                }
                break;
            case 115 :
                // EsperEPL2Grammar.g:1:978: COLON
                {
                mCOLON(); if (state.failed) return ;

                }
                break;
            case 116 :
                // EsperEPL2Grammar.g:1:984: COMMA
                {
                mCOMMA(); if (state.failed) return ;

                }
                break;
            case 117 :
                // EsperEPL2Grammar.g:1:990: EQUAL
                {
                mEQUAL(); if (state.failed) return ;

                }
                break;
            case 118 :
                // EsperEPL2Grammar.g:1:996: LNOT
                {
                mLNOT(); if (state.failed) return ;

                }
                break;
            case 119 :
                // EsperEPL2Grammar.g:1:1001: BNOT
                {
                mBNOT(); if (state.failed) return ;

                }
                break;
            case 120 :
                // EsperEPL2Grammar.g:1:1006: NOT_EQUAL
                {
                mNOT_EQUAL(); if (state.failed) return ;

                }
                break;
            case 121 :
                // EsperEPL2Grammar.g:1:1016: DIV
                {
                mDIV(); if (state.failed) return ;

                }
                break;
            case 122 :
                // EsperEPL2Grammar.g:1:1020: DIV_ASSIGN
                {
                mDIV_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 123 :
                // EsperEPL2Grammar.g:1:1031: PLUS
                {
                mPLUS(); if (state.failed) return ;

                }
                break;
            case 124 :
                // EsperEPL2Grammar.g:1:1036: PLUS_ASSIGN
                {
                mPLUS_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 125 :
                // EsperEPL2Grammar.g:1:1048: INC
                {
                mINC(); if (state.failed) return ;

                }
                break;
            case 126 :
                // EsperEPL2Grammar.g:1:1052: MINUS
                {
                mMINUS(); if (state.failed) return ;

                }
                break;
            case 127 :
                // EsperEPL2Grammar.g:1:1058: MINUS_ASSIGN
                {
                mMINUS_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 128 :
                // EsperEPL2Grammar.g:1:1071: DEC
                {
                mDEC(); if (state.failed) return ;

                }
                break;
            case 129 :
                // EsperEPL2Grammar.g:1:1075: STAR
                {
                mSTAR(); if (state.failed) return ;

                }
                break;
            case 130 :
                // EsperEPL2Grammar.g:1:1080: STAR_ASSIGN
                {
                mSTAR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 131 :
                // EsperEPL2Grammar.g:1:1092: MOD
                {
                mMOD(); if (state.failed) return ;

                }
                break;
            case 132 :
                // EsperEPL2Grammar.g:1:1096: MOD_ASSIGN
                {
                mMOD_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 133 :
                // EsperEPL2Grammar.g:1:1107: SR
                {
                mSR(); if (state.failed) return ;

                }
                break;
            case 134 :
                // EsperEPL2Grammar.g:1:1110: SR_ASSIGN
                {
                mSR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 135 :
                // EsperEPL2Grammar.g:1:1120: BSR
                {
                mBSR(); if (state.failed) return ;

                }
                break;
            case 136 :
                // EsperEPL2Grammar.g:1:1124: BSR_ASSIGN
                {
                mBSR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 137 :
                // EsperEPL2Grammar.g:1:1135: GE
                {
                mGE(); if (state.failed) return ;

                }
                break;
            case 138 :
                // EsperEPL2Grammar.g:1:1138: GT
                {
                mGT(); if (state.failed) return ;

                }
                break;
            case 139 :
                // EsperEPL2Grammar.g:1:1141: SL
                {
                mSL(); if (state.failed) return ;

                }
                break;
            case 140 :
                // EsperEPL2Grammar.g:1:1144: SL_ASSIGN
                {
                mSL_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 141 :
                // EsperEPL2Grammar.g:1:1154: LE
                {
                mLE(); if (state.failed) return ;

                }
                break;
            case 142 :
                // EsperEPL2Grammar.g:1:1157: LT
                {
                mLT(); if (state.failed) return ;

                }
                break;
            case 143 :
                // EsperEPL2Grammar.g:1:1160: BXOR
                {
                mBXOR(); if (state.failed) return ;

                }
                break;
            case 144 :
                // EsperEPL2Grammar.g:1:1165: BXOR_ASSIGN
                {
                mBXOR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 145 :
                // EsperEPL2Grammar.g:1:1177: BOR
                {
                mBOR(); if (state.failed) return ;

                }
                break;
            case 146 :
                // EsperEPL2Grammar.g:1:1181: BOR_ASSIGN
                {
                mBOR_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 147 :
                // EsperEPL2Grammar.g:1:1192: LOR
                {
                mLOR(); if (state.failed) return ;

                }
                break;
            case 148 :
                // EsperEPL2Grammar.g:1:1196: BAND
                {
                mBAND(); if (state.failed) return ;

                }
                break;
            case 149 :
                // EsperEPL2Grammar.g:1:1201: BAND_ASSIGN
                {
                mBAND_ASSIGN(); if (state.failed) return ;

                }
                break;
            case 150 :
                // EsperEPL2Grammar.g:1:1213: LAND
                {
                mLAND(); if (state.failed) return ;

                }
                break;
            case 151 :
                // EsperEPL2Grammar.g:1:1218: SEMI
                {
                mSEMI(); if (state.failed) return ;

                }
                break;
            case 152 :
                // EsperEPL2Grammar.g:1:1223: DOT
                {
                mDOT(); if (state.failed) return ;

                }
                break;
            case 153 :
                // EsperEPL2Grammar.g:1:1227: NUM_LONG
                {
                mNUM_LONG(); if (state.failed) return ;

                }
                break;
            case 154 :
                // EsperEPL2Grammar.g:1:1236: NUM_DOUBLE
                {
                mNUM_DOUBLE(); if (state.failed) return ;

                }
                break;
            case 155 :
                // EsperEPL2Grammar.g:1:1247: NUM_FLOAT
                {
                mNUM_FLOAT(); if (state.failed) return ;

                }
                break;
            case 156 :
                // EsperEPL2Grammar.g:1:1257: ESCAPECHAR
                {
                mESCAPECHAR(); if (state.failed) return ;

                }
                break;
            case 157 :
                // EsperEPL2Grammar.g:1:1268: EMAILAT
                {
                mEMAILAT(); if (state.failed) return ;

                }
                break;
            case 158 :
                // EsperEPL2Grammar.g:1:1276: WS
                {
                mWS(); if (state.failed) return ;

                }
                break;
            case 159 :
                // EsperEPL2Grammar.g:1:1279: SL_COMMENT
                {
                mSL_COMMENT(); if (state.failed) return ;

                }
                break;
            case 160 :
                // EsperEPL2Grammar.g:1:1290: ML_COMMENT
                {
                mML_COMMENT(); if (state.failed) return ;

                }
                break;
            case 161 :
                // EsperEPL2Grammar.g:1:1301: TICKED_STRING_LITERAL
                {
                mTICKED_STRING_LITERAL(); if (state.failed) return ;

                }
                break;
            case 162 :
                // EsperEPL2Grammar.g:1:1323: QUOTED_STRING_LITERAL
                {
                mQUOTED_STRING_LITERAL(); if (state.failed) return ;

                }
                break;
            case 163 :
                // EsperEPL2Grammar.g:1:1345: STRING_LITERAL
                {
                mSTRING_LITERAL(); if (state.failed) return ;

                }
                break;
            case 164 :
                // EsperEPL2Grammar.g:1:1360: IDENT
                {
                mIDENT(); if (state.failed) return ;

                }
                break;
            case 165 :
                // EsperEPL2Grammar.g:1:1366: NUM_INT
                {
                mNUM_INT(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred1_EsperEPL2Grammar
    public final void synpred1_EsperEPL2Grammar_fragment() throws RecognitionException {   
        // EsperEPL2Grammar.g:1874:5: ( ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX ) )
        // EsperEPL2Grammar.g:1874:6: ( '0' .. '9' )+ ( '.' | EXPONENT | FLOAT_SUFFIX )
        {
        // EsperEPL2Grammar.g:1874:6: ( '0' .. '9' )+
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
        	    // EsperEPL2Grammar.g:1874:7: '0' .. '9'
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

        // EsperEPL2Grammar.g:1874:18: ( '.' | EXPONENT | FLOAT_SUFFIX )
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
                // EsperEPL2Grammar.g:1874:19: '.'
                {
                match('.'); if (state.failed) return ;

                }
                break;
            case 2 :
                // EsperEPL2Grammar.g:1874:23: EXPONENT
                {
                mEXPONENT(); if (state.failed) return ;

                }
                break;
            case 3 :
                // EsperEPL2Grammar.g:1874:32: FLOAT_SUFFIX
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


 

}