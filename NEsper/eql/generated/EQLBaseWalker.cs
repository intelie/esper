// $ANTLR 2.7.7 (20060930): "eql_tree.g" -> "EQLBaseWalker.cs"$

using System.Collections;

using org.apache.commons.logging;

namespace net.esper.eql.generated
{
    // Generate header specific to the tree-parser CSharp file
    using System;

    using TreeParser = antlr.TreeParser;
    using Token = antlr.Token;
    using IToken = antlr.IToken;
    using AST = antlr.collections.AST;
    using RecognitionException = antlr.RecognitionException;
    using ANTLRException = antlr.ANTLRException;
    using NoViableAltException = antlr.NoViableAltException;
    using MismatchedTokenException = antlr.MismatchedTokenException;
    using SemanticException = antlr.SemanticException;
    using BitSet = antlr.collections.impl.BitSet;
    using ASTPair = antlr.ASTPair;
    using ASTFactory = antlr.ASTFactory;
    using ASTArray = antlr.collections.impl.ASTArray;


    public class EQLBaseWalker : antlr.TreeParser
    {
        public const int EOF = 1;
        public const int NULL_TREE_LOOKAHEAD = 3;
        public const int IN_SET = 4;
        public const int BETWEEN = 5;
        public const int LIKE = 6;
        public const int REGEXP = 7;
        public const int ESCAPE = 8;
        public const int OR_EXPR = 9;
        public const int AND_EXPR = 10;
        public const int NOT_EXPR = 11;
        public const int EVERY_EXPR = 12;
        public const int WHERE = 13;
        public const int AS = 14;
        public const int SUM = 15;
        public const int AVG = 16;
        public const int MAX = 17;
        public const int MIN = 18;
        public const int COALESCE = 19;
        public const int MEDIAN = 20;
        public const int STDDEV = 21;
        public const int AVEDEV = 22;
        public const int COUNT = 23;
        public const int SELECT = 24;
        public const int CASE = 25;
        public const int CASE2 = 26;
        public const int ELSE = 27;
        public const int WHEN = 28;
        public const int THEN = 29;
        public const int END = 30;
        public const int FROM = 31;
        public const int OUTER = 32;
        public const int JOIN = 33;
        public const int LEFT = 34;
        public const int RIGHT = 35;
        public const int FULL = 36;
        public const int ON = 37;
        public const int IS_ = 38;
        public const int BY = 39;
        public const int GROUP = 40;
        public const int HAVING = 41;
        public const int DISTINCT = 42;
        public const int ALL = 43;
        public const int OUTPUT = 44;
        public const int EVENTS = 45;
        public const int SECONDS = 46;
        public const int MINUTES = 47;
        public const int FIRST = 48;
        public const int LAST = 49;
        public const int INSERT = 50;
        public const int INTO = 51;
        public const int ORDER = 52;
        public const int ASC = 53;
        public const int DESC = 54;
        public const int RSTREAM = 55;
        public const int ISTREAM = 56;
        public const int PATTERN = 57;
        public const int SQL = 58;
        public const int NUMERIC_PARAM_RANGE = 59;
        public const int NUMERIC_PARAM_LIST = 60;
        public const int NUMERIC_PARAM_FREQUENCY = 61;
        public const int FOLLOWED_BY_EXPR = 62;
        public const int ARRAY_PARAM_LIST = 63;
        public const int EVENT_FILTER_EXPR = 64;
        public const int EVENT_FILTER_NAME_TAG = 65;
        public const int EVENT_FILTER_IDENT = 66;
        public const int EVENT_FILTER_PARAM = 67;
        public const int CLASS_IDENT = 68;
        public const int GUARD_EXPR = 69;
        public const int OBSERVER_EXPR = 70;
        public const int VIEW_EXPR = 71;
        public const int PATTERN_INCL_EXPR = 72;
        public const int DATABASE_JOIN_EXPR = 73;
        public const int WHERE_EXPR = 74;
        public const int HAVING_EXPR = 75;
        public const int EVAL_BITWISE_EXPR = 76;
        public const int EVAL_AND_EXPR = 77;
        public const int EVAL_OR_EXPR = 78;
        public const int EVAL_EQUALS_EXPR = 79;
        public const int EVAL_NOTEQUALS_EXPR = 80;
        public const int EVAL_IDENT = 81;
        public const int SELECTION_EXPR = 82;
        public const int SELECTION_ELEMENT_EXPR = 83;
        public const int STREAM_EXPR = 84;
        public const int OUTERJOIN_EXPR = 85;
        public const int LEFT_OUTERJOIN_EXPR = 86;
        public const int RIGHT_OUTERJOIN_EXPR = 87;
        public const int FULL_OUTERJOIN_EXPR = 88;
        public const int GROUP_BY_EXPR = 89;
        public const int ORDER_BY_EXPR = 90;
        public const int ORDER_ELEMENT_EXPR = 91;
        public const int EVENT_PROP_EXPR = 92;
        public const int EVENT_PROP_SIMPLE = 93;
        public const int EVENT_PROP_MAPPED = 94;
        public const int EVENT_PROP_INDEXED = 95;
        public const int EVENT_LIMIT_EXPR = 96;
        public const int SEC_LIMIT_EXPR = 97;
        public const int MIN_LIMIT_EXPR = 98;
        public const int INSERTINTO_EXPR = 99;
        public const int INSERTINTO_EXPRCOL = 100;
        public const int CONCAT = 101;
        public const int LIB_FUNCTION = 102;
        public const int UNARY_MINUS = 103;
        public const int TIME_PERIOD = 104;
        public const int DAY_PART = 105;
        public const int HOUR_PART = 106;
        public const int MINUTE_PART = 107;
        public const int SECOND_PART = 108;
        public const int MILLISECOND_PART = 109;
        public const int NOT_IN_SET = 110;
        public const int NOT_BETWEEN = 111;
        public const int NOT_LIKE = 112;
        public const int NOT_REGEXP = 113;
        public const int DBSELECT_EXPR = 114;
        public const int DBFROM_CLAUSE = 115;
        public const int DBWHERE_CLAUSE = 116;
        public const int INT_TYPE = 117;
        public const int LONG_TYPE = 118;
        public const int FLOAT_TYPE = 119;
        public const int DOUBLE_TYPE = 120;
        public const int STRING_TYPE = 121;
        public const int BOOL_TYPE = 122;
        public const int NULL_TYPE = 123;
        public const int NUM_INT = 124;
        public const int NUM_LONG = 125;
        public const int NUM_FLOAT = 126;
        public const int NUM_DOUBLE = 127;
        public const int MINUS = 128;
        public const int PLUS = 129;
        public const int LITERAL_true = 130;
        public const int LITERAL_false = 131;
        public const int LITERAL_null = 132;
        public const int STRING_LITERAL = 133;
        public const int QUOTED_STRING_LITERAL = 134;
        public const int IDENT = 135;
        public const int LPAREN = 136;
        public const int COMMA = 137;
        public const int RPAREN = 138;
        public const int EQUALS = 139;
        public const int STAR = 140;
        public const int DOT = 141;
        public const int LBRACK = 142;
        public const int RBRACK = 143;
        public const int COLON = 144;
        public const int BAND = 145;
        public const int BOR = 146;
        public const int BXOR = 147;
        public const int SQL_NE = 148;
        public const int NOT_EQUAL = 149;
        public const int LT_ = 150;
        public const int GT = 151;
        public const int LE = 152;
        public const int GE = 153;
        public const int LOR = 154;
        public const int DIV = 155;
        public const int MOD = 156;
        public const int FOLLOWED_BY = 157;
        public const int LCURLY = 158;
        public const int RCURLY = 159;
        public const int LITERAL_days = 160;
        public const int LITERAL_day = 161;
        public const int LITERAL_hours = 162;
        public const int LITERAL_hour = 163;
        public const int LITERAL_minute = 164;
        public const int LITERAL_second = 165;
        public const int LITERAL_sec = 166;
        public const int LITERAL_milliseconds = 167;
        public const int LITERAL_millisecond = 168;
        public const int LITERAL_msec = 169;
        public const int QUESTION = 170;
        public const int EQUAL = 171;
        public const int LNOT = 172;
        public const int BNOT = 173;
        public const int DIV_ASSIGN = 174;
        public const int PLUS_ASSIGN = 175;
        public const int INC = 176;
        public const int MINUS_ASSIGN = 177;
        public const int DEC = 178;
        public const int STAR_ASSIGN = 179;
        public const int MOD_ASSIGN = 180;
        public const int SR = 181;
        public const int SR_ASSIGN = 182;
        public const int BSR = 183;
        public const int BSR_ASSIGN = 184;
        public const int SL = 185;
        public const int SL_ASSIGN = 186;
        public const int BXOR_ASSIGN = 187;
        public const int BOR_ASSIGN = 188;
        public const int BAND_ASSIGN = 189;
        public const int LAND = 190;
        public const int SEMI = 191;
        public const int WS = 192;
        public const int SL_COMMENT = 193;
        public const int ML_COMMENT = 194;
        public const int ESC = 195;
        public const int HEX_DIGIT = 196;
        public const int EXPONENT = 197;
        public const int FLOAT_SUFFIX = 198;
        public const int BOGUS = 199;
        public const int NUMERIC_PARAM_FREQUENCE = 200;


        private static Log log = LogFactory.GetLog(typeof(EQLBaseWalker));

        // For pattern processing within EQL and for create pattern
        protected virtual void setIsPatternWalk(Boolean isPatternWalk) { }
        protected virtual void endPattern() { }

        protected virtual void leaveNode(AST node) { }
        protected virtual void end() { }
        public EQLBaseWalker()
        {
            tokenNames = tokenNames_;
        }

        public void startEQLExpressionRule(AST _t) //throws RecognitionException
        {

            AST startPatternExpressionRule_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST startPatternExpressionRule_AST = null;

            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case INSERTINTO_EXPR:
                        {
                            insertIntoExpr(_t);
                            _t = retTree_;
                            astFactory.addASTChild(ref currentAST, returnAST);
                            break;
                        }
                    case SELECTION_EXPR:
                    case STAR:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            selectClause(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            fromClause(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case WHERE_EXPR:
                        {
                            whereClause(_t);
                            _t = retTree_;
                            astFactory.addASTChild(ref currentAST, returnAST);
                            break;
                        }
                    case 3:
                    case HAVING_EXPR:
                    case GROUP_BY_EXPR:
                    case ORDER_BY_EXPR:
                    case EVENT_LIMIT_EXPR:
                    case SEC_LIMIT_EXPR:
                    case MIN_LIMIT_EXPR:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case GROUP_BY_EXPR:
                        {
                            groupByClause(_t);
                            _t = retTree_;
                            astFactory.addASTChild(ref currentAST, returnAST);
                            break;
                        }
                    case 3:
                    case HAVING_EXPR:
                    case ORDER_BY_EXPR:
                    case EVENT_LIMIT_EXPR:
                    case SEC_LIMIT_EXPR:
                    case MIN_LIMIT_EXPR:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case HAVING_EXPR:
                        {
                            havingClause(_t);
                            _t = retTree_;
                            astFactory.addASTChild(ref currentAST, returnAST);
                            break;
                        }
                    case 3:
                    case ORDER_BY_EXPR:
                    case EVENT_LIMIT_EXPR:
                    case SEC_LIMIT_EXPR:
                    case MIN_LIMIT_EXPR:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case EVENT_LIMIT_EXPR:
                    case SEC_LIMIT_EXPR:
                    case MIN_LIMIT_EXPR:
                        {
                            outputLimitExpr(_t);
                            _t = retTree_;
                            astFactory.addASTChild(ref currentAST, returnAST);
                            break;
                        }
                    case 3:
                    case ORDER_BY_EXPR:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case ORDER_BY_EXPR:
                        {
                            orderByClause(_t);
                            _t = retTree_;
                            astFactory.addASTChild(ref currentAST, returnAST);
                            break;
                        }
                    case 3:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            end();
            startPatternExpressionRule_AST = currentAST.root;
            returnAST = startPatternExpressionRule_AST;
            retTree_ = _t;
        }

        public void insertIntoExpr(AST _t) //throws RecognitionException
        {

            AST insertIntoExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST insertIntoExpr_AST = null;
            AST i = null;
            AST i_AST = null;

            AST __t9 = _t;
            i = (ASTNULL == _t) ? null : (AST)_t;
            AST i_AST_in = null;
            i_AST = astFactory.create(i);
            astFactory.addASTChild(ref currentAST, i_AST);
            ASTPair __currentAST9 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, INSERTINTO_EXPR);
            _t = _t.getFirstChild();
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case ISTREAM:
                        {
                            AST tmp1_AST = null;
                            AST tmp1_AST_in = null;
                            tmp1_AST = astFactory.create(_t);
                            tmp1_AST_in = _t;
                            astFactory.addASTChild(ref currentAST, tmp1_AST);
                            match(_t, ISTREAM);
                            _t = _t.getNextSibling();
                            break;
                        }
                    case RSTREAM:
                        {
                            AST tmp2_AST = null;
                            AST tmp2_AST_in = null;
                            tmp2_AST = astFactory.create(_t);
                            tmp2_AST_in = _t;
                            astFactory.addASTChild(ref currentAST, tmp2_AST);
                            match(_t, RSTREAM);
                            _t = _t.getNextSibling();
                            break;
                        }
                    case IDENT:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            AST tmp3_AST = null;
            AST tmp3_AST_in = null;
            tmp3_AST = astFactory.create(_t);
            tmp3_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp3_AST);
            match(_t, IDENT);
            _t = _t.getNextSibling();
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case INSERTINTO_EXPRCOL:
                        {
                            insertIntoExprCol(_t);
                            _t = retTree_;
                            astFactory.addASTChild(ref currentAST, returnAST);
                            break;
                        }
                    case 3:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            leaveNode(i_AST);
            currentAST = __currentAST9;
            _t = __t9;
            _t = _t.getNextSibling();
            insertIntoExpr_AST = currentAST.root;
            returnAST = insertIntoExpr_AST;
            retTree_ = _t;
        }

        public void selectClause(AST _t) //throws RecognitionException
        {

            AST selectClause_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST selectClause_AST = null;
            AST s = null;
            AST s_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case SELECTION_EXPR:
                    {
                        AST __t17 = _t;
                        s = (ASTNULL == _t) ? null : (AST)_t;
                        AST s_AST_in = null;
                        s_AST = astFactory.create(s);
                        astFactory.addASTChild(ref currentAST, s_AST);
                        ASTPair __currentAST17 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, SELECTION_EXPR);
                        _t = _t.getFirstChild();
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case RSTREAM:
                                    {
                                        AST tmp4_AST = null;
                                        AST tmp4_AST_in = null;
                                        tmp4_AST = astFactory.create(_t);
                                        tmp4_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp4_AST);
                                        match(_t, RSTREAM);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case ISTREAM:
                                    {
                                        AST tmp5_AST = null;
                                        AST tmp5_AST_in = null;
                                        tmp5_AST = astFactory.create(_t);
                                        tmp5_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp5_AST);
                                        match(_t, ISTREAM);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case SELECTION_ELEMENT_EXPR:
                                case STAR:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case STAR:
                                    {
                                        AST tmp6_AST = null;
                                        AST tmp6_AST_in = null;
                                        tmp6_AST = astFactory.create(_t);
                                        tmp6_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp6_AST);
                                        match(_t, STAR);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case SELECTION_ELEMENT_EXPR:
                                    {
                                        selectionList(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        leaveNode(s_AST);
                        currentAST = __currentAST17;
                        _t = __t17;
                        _t = _t.getNextSibling();
                        selectClause_AST = currentAST.root;
                        break;
                    }
                case STAR:
                    {
                        AST tmp7_AST = null;
                        AST tmp7_AST_in = null;
                        tmp7_AST = astFactory.create(_t);
                        tmp7_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp7_AST);
                        match(_t, STAR);
                        _t = _t.getNextSibling();
                        selectClause_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = selectClause_AST;
            retTree_ = _t;
        }

        public void fromClause(AST _t) //throws RecognitionException
        {

            AST fromClause_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST fromClause_AST = null;

            streamExpression(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            {    // ( ... )*
                for (; ; )
                {
                    if (_t == null)
                        _t = ASTNULL;
                    if ((_t.Type == STREAM_EXPR))
                    {
                        streamExpression(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if (((_t.Type >= LEFT_OUTERJOIN_EXPR && _t.Type <= FULL_OUTERJOIN_EXPR)))
                                {
                                    outerJoin(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop23_breakloop;
                                }

                            }
                        _loop23_breakloop: ;
                        }    // ( ... )*
                    }
                    else
                    {
                        goto _loop24_breakloop;
                    }

                }
            _loop24_breakloop: ;
            }    // ( ... )*
            fromClause_AST = currentAST.root;
            returnAST = fromClause_AST;
            retTree_ = _t;
        }

        public void whereClause(AST _t) //throws RecognitionException
        {

            AST whereClause_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST whereClause_AST = null;
            AST n = null;
            AST n_AST = null;

            AST __t54 = _t;
            n = (ASTNULL == _t) ? null : (AST)_t;
            AST n_AST_in = null;
            n_AST = astFactory.create(n);
            astFactory.addASTChild(ref currentAST, n_AST);
            ASTPair __currentAST54 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, WHERE_EXPR);
            _t = _t.getFirstChild();
            valueExpr(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            leaveNode(n_AST);
            currentAST = __currentAST54;
            _t = __t54;
            _t = _t.getNextSibling();
            whereClause_AST = currentAST.root;
            returnAST = whereClause_AST;
            retTree_ = _t;
        }

        public void groupByClause(AST _t) //throws RecognitionException
        {

            AST groupByClause_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST groupByClause_AST = null;
            AST g = null;
            AST g_AST = null;

            AST __t56 = _t;
            g = (ASTNULL == _t) ? null : (AST)_t;
            AST g_AST_in = null;
            g_AST = astFactory.create(g);
            astFactory.addASTChild(ref currentAST, g_AST);
            ASTPair __currentAST56 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, GROUP_BY_EXPR);
            _t = _t.getFirstChild();
            valueExpr(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            {    // ( ... )*
                for (; ; )
                {
                    if (_t == null)
                        _t = ASTNULL;
                    if ((tokenSet_0_.member(_t.Type)))
                    {
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                    }
                    else
                    {
                        goto _loop58_breakloop;
                    }

                }
            _loop58_breakloop: ;
            }    // ( ... )*
            currentAST = __currentAST56;
            _t = __t56;
            _t = _t.getNextSibling();
            leaveNode(g_AST);
            groupByClause_AST = currentAST.root;
            returnAST = groupByClause_AST;
            retTree_ = _t;
        }

        public void havingClause(AST _t) //throws RecognitionException
        {

            AST havingClause_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST havingClause_AST = null;
            AST n = null;
            AST n_AST = null;

            AST __t67 = _t;
            n = (ASTNULL == _t) ? null : (AST)_t;
            AST n_AST_in = null;
            n_AST = astFactory.create(n);
            astFactory.addASTChild(ref currentAST, n_AST);
            ASTPair __currentAST67 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, HAVING_EXPR);
            _t = _t.getFirstChild();
            valueExpr(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            leaveNode(n_AST);
            currentAST = __currentAST67;
            _t = __t67;
            _t = _t.getNextSibling();
            havingClause_AST = currentAST.root;
            returnAST = havingClause_AST;
            retTree_ = _t;
        }

        public void outputLimitExpr(AST _t) //throws RecognitionException
        {

            AST outputLimitExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST outputLimitExpr_AST = null;
            AST e = null;
            AST e_AST = null;
            AST sec = null;
            AST sec_AST = null;
            AST min = null;
            AST min_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case EVENT_LIMIT_EXPR:
                    {
                        AST __t69 = _t;
                        e = (ASTNULL == _t) ? null : (AST)_t;
                        AST e_AST_in = null;
                        e_AST = astFactory.create(e);
                        astFactory.addASTChild(ref currentAST, e_AST);
                        ASTPair __currentAST69 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, EVENT_LIMIT_EXPR);
                        _t = _t.getFirstChild();
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case ALL:
                                    {
                                        AST tmp8_AST = null;
                                        AST tmp8_AST_in = null;
                                        tmp8_AST = astFactory.create(_t);
                                        tmp8_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp8_AST);
                                        match(_t, ALL);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case FIRST:
                                    {
                                        AST tmp9_AST = null;
                                        AST tmp9_AST_in = null;
                                        tmp9_AST = astFactory.create(_t);
                                        tmp9_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp9_AST);
                                        match(_t, FIRST);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case LAST:
                                    {
                                        AST tmp10_AST = null;
                                        AST tmp10_AST_in = null;
                                        tmp10_AST = astFactory.create(_t);
                                        tmp10_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp10_AST);
                                        match(_t, LAST);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        number(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(e_AST);
                        currentAST = __currentAST69;
                        _t = __t69;
                        _t = _t.getNextSibling();
                        outputLimitExpr_AST = currentAST.root;
                        break;
                    }
                case SEC_LIMIT_EXPR:
                    {
                        AST __t71 = _t;
                        sec = (ASTNULL == _t) ? null : (AST)_t;
                        AST sec_AST_in = null;
                        sec_AST = astFactory.create(sec);
                        astFactory.addASTChild(ref currentAST, sec_AST);
                        ASTPair __currentAST71 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, SEC_LIMIT_EXPR);
                        _t = _t.getFirstChild();
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case ALL:
                                    {
                                        AST tmp11_AST = null;
                                        AST tmp11_AST_in = null;
                                        tmp11_AST = astFactory.create(_t);
                                        tmp11_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp11_AST);
                                        match(_t, ALL);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case FIRST:
                                    {
                                        AST tmp12_AST = null;
                                        AST tmp12_AST_in = null;
                                        tmp12_AST = astFactory.create(_t);
                                        tmp12_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp12_AST);
                                        match(_t, FIRST);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case LAST:
                                    {
                                        AST tmp13_AST = null;
                                        AST tmp13_AST_in = null;
                                        tmp13_AST = astFactory.create(_t);
                                        tmp13_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp13_AST);
                                        match(_t, LAST);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        number(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(sec_AST);
                        currentAST = __currentAST71;
                        _t = __t71;
                        _t = _t.getNextSibling();
                        outputLimitExpr_AST = currentAST.root;
                        break;
                    }
                case MIN_LIMIT_EXPR:
                    {
                        AST __t73 = _t;
                        min = (ASTNULL == _t) ? null : (AST)_t;
                        AST min_AST_in = null;
                        min_AST = astFactory.create(min);
                        astFactory.addASTChild(ref currentAST, min_AST);
                        ASTPair __currentAST73 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, MIN_LIMIT_EXPR);
                        _t = _t.getFirstChild();
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case ALL:
                                    {
                                        AST tmp14_AST = null;
                                        AST tmp14_AST_in = null;
                                        tmp14_AST = astFactory.create(_t);
                                        tmp14_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp14_AST);
                                        match(_t, ALL);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case FIRST:
                                    {
                                        AST tmp15_AST = null;
                                        AST tmp15_AST_in = null;
                                        tmp15_AST = astFactory.create(_t);
                                        tmp15_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp15_AST);
                                        match(_t, FIRST);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case LAST:
                                    {
                                        AST tmp16_AST = null;
                                        AST tmp16_AST_in = null;
                                        tmp16_AST = astFactory.create(_t);
                                        tmp16_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp16_AST);
                                        match(_t, LAST);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        number(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(min_AST);
                        currentAST = __currentAST73;
                        _t = __t73;
                        _t = _t.getNextSibling();
                        outputLimitExpr_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = outputLimitExpr_AST;
            retTree_ = _t;
        }

        public void orderByClause(AST _t) //throws RecognitionException
        {

            AST orderByClause_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST orderByClause_AST = null;
            AST s = null;
            AST s_AST = null;

            AST __t60 = _t;
            s = (ASTNULL == _t) ? null : (AST)_t;
            AST s_AST_in = null;
            s_AST = astFactory.create(s);
            astFactory.addASTChild(ref currentAST, s_AST);
            ASTPair __currentAST60 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, ORDER_BY_EXPR);
            _t = _t.getFirstChild();
            orderByElement(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            {    // ( ... )*
                for (; ; )
                {
                    if (_t == null)
                        _t = ASTNULL;
                    if ((_t.Type == ORDER_ELEMENT_EXPR))
                    {
                        orderByElement(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                    }
                    else
                    {
                        goto _loop62_breakloop;
                    }

                }
            _loop62_breakloop: ;
            }    // ( ... )*
            currentAST = __currentAST60;
            _t = __t60;
            _t = _t.getNextSibling();
            orderByClause_AST = currentAST.root;
            returnAST = orderByClause_AST;
            retTree_ = _t;
        }

        public void insertIntoExprCol(AST _t) //throws RecognitionException
        {

            AST insertIntoExprCol_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST insertIntoExprCol_AST = null;
            AST i = null;
            AST i_AST = null;

            AST __t13 = _t;
            i = (ASTNULL == _t) ? null : (AST)_t;
            AST i_AST_in = null;
            i_AST = astFactory.create(i);
            astFactory.addASTChild(ref currentAST, i_AST);
            ASTPair __currentAST13 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, INSERTINTO_EXPRCOL);
            _t = _t.getFirstChild();
            AST tmp17_AST = null;
            AST tmp17_AST_in = null;
            tmp17_AST = astFactory.create(_t);
            tmp17_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp17_AST);
            match(_t, IDENT);
            _t = _t.getNextSibling();
            {    // ( ... )*
                for (; ; )
                {
                    if (_t == null)
                        _t = ASTNULL;
                    if ((_t.Type == IDENT))
                    {
                        AST tmp18_AST = null;
                        AST tmp18_AST_in = null;
                        tmp18_AST = astFactory.create(_t);
                        tmp18_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp18_AST);
                        match(_t, IDENT);
                        _t = _t.getNextSibling();
                    }
                    else
                    {
                        goto _loop15_breakloop;
                    }

                }
            _loop15_breakloop: ;
            }    // ( ... )*
            currentAST = __currentAST13;
            _t = __t13;
            _t = _t.getNextSibling();
            insertIntoExprCol_AST = currentAST.root;
            returnAST = insertIntoExprCol_AST;
            retTree_ = _t;
        }

        public void selectionList(AST _t) //throws RecognitionException
        {

            AST selectionList_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST selectionList_AST = null;

            selectionListElement(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            {    // ( ... )*
                for (; ; )
                {
                    if (_t == null)
                        _t = ASTNULL;
                    if ((_t.Type == SELECTION_ELEMENT_EXPR))
                    {
                        selectionListElement(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                    }
                    else
                    {
                        goto _loop27_breakloop;
                    }

                }
            _loop27_breakloop: ;
            }    // ( ... )*
            selectionList_AST = currentAST.root;
            returnAST = selectionList_AST;
            retTree_ = _t;
        }

        public void streamExpression(AST _t) //throws RecognitionException
        {

            AST streamExpression_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST streamExpression_AST = null;
            AST v = null;
            AST v_AST = null;

            AST __t37 = _t;
            v = (ASTNULL == _t) ? null : (AST)_t;
            AST v_AST_in = null;
            v_AST = astFactory.create(v);
            astFactory.addASTChild(ref currentAST, v_AST);
            ASTPair __currentAST37 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, STREAM_EXPR);
            _t = _t.getFirstChild();
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case EVENT_FILTER_EXPR:
                        {
                            eventFilterExpr(_t);
                            _t = retTree_;
                            astFactory.addASTChild(ref currentAST, returnAST);
                            break;
                        }
                    case PATTERN_INCL_EXPR:
                        {
                            patternInclusionExpression(_t);
                            _t = retTree_;
                            astFactory.addASTChild(ref currentAST, returnAST);
                            break;
                        }
                    case DATABASE_JOIN_EXPR:
                        {
                            databaseJoinExpression(_t);
                            _t = retTree_;
                            astFactory.addASTChild(ref currentAST, returnAST);
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case VIEW_EXPR:
                        {
                            viewListExpr(_t);
                            _t = retTree_;
                            astFactory.addASTChild(ref currentAST, returnAST);
                            break;
                        }
                    case 3:
                    case IDENT:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case IDENT:
                        {
                            AST tmp19_AST = null;
                            AST tmp19_AST_in = null;
                            tmp19_AST = astFactory.create(_t);
                            tmp19_AST_in = _t;
                            astFactory.addASTChild(ref currentAST, tmp19_AST);
                            match(_t, IDENT);
                            _t = _t.getNextSibling();
                            break;
                        }
                    case 3:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            leaveNode(v_AST);
            currentAST = __currentAST37;
            _t = __t37;
            _t = _t.getNextSibling();
            streamExpression_AST = currentAST.root;
            returnAST = streamExpression_AST;
            retTree_ = _t;
        }

        public void outerJoin(AST _t) //throws RecognitionException
        {

            AST outerJoin_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST outerJoin_AST = null;

            outerJoinIdent(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            outerJoin_AST = currentAST.root;
            returnAST = outerJoin_AST;
            retTree_ = _t;
        }

        public void selectionListElement(AST _t) //throws RecognitionException
        {

            AST selectionListElement_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST selectionListElement_AST = null;
            AST s = null;
            AST s_AST = null;

            AST __t29 = _t;
            s = (ASTNULL == _t) ? null : (AST)_t;
            AST s_AST_in = null;
            s_AST = astFactory.create(s);
            astFactory.addASTChild(ref currentAST, s_AST);
            ASTPair __currentAST29 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, SELECTION_ELEMENT_EXPR);
            _t = _t.getFirstChild();
            valueExpr(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case IDENT:
                        {
                            AST tmp20_AST = null;
                            AST tmp20_AST_in = null;
                            tmp20_AST = astFactory.create(_t);
                            tmp20_AST_in = _t;
                            astFactory.addASTChild(ref currentAST, tmp20_AST);
                            match(_t, IDENT);
                            _t = _t.getNextSibling();
                            break;
                        }
                    case 3:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            leaveNode(s_AST);
            currentAST = __currentAST29;
            _t = __t29;
            _t = _t.getNextSibling();
            selectionListElement_AST = currentAST.root;
            returnAST = selectionListElement_AST;
            retTree_ = _t;
        }

        public void valueExpr(AST _t) //throws RecognitionException
        {

            AST valueExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST valueExpr_AST = null;
            AST c_AST = null;
            AST c = null;
            AST a_AST = null;
            AST a = null;
            AST f_AST = null;
            AST f = null;
            AST l_AST = null;
            AST l = null;
            AST cs_AST = null;
            AST cs = null;
            AST in__AST = null;
            AST in_ = null;
            AST b_AST = null;
            AST b = null;
            AST li_AST = null;
            AST li = null;
            AST r_AST = null;
            AST r = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case INT_TYPE:
                case LONG_TYPE:
                case FLOAT_TYPE:
                case DOUBLE_TYPE:
                case STRING_TYPE:
                case BOOL_TYPE:
                case NULL_TYPE:
                    {
                        c = _t == ASTNULL ? null : _t;
                        constant(_t);
                        _t = retTree_;
                        c_AST = (AST)returnAST;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(c_AST);
                        valueExpr_AST = currentAST.root;
                        break;
                    }
                case CONCAT:
                case MINUS:
                case PLUS:
                case STAR:
                case BAND:
                case BOR:
                case BXOR:
                case DIV:
                case MOD:
                    {
                        a = _t == ASTNULL ? null : _t;
                        arithmeticExpr(_t);
                        _t = retTree_;
                        a_AST = (AST)returnAST;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(a_AST);
                        valueExpr_AST = currentAST.root;
                        break;
                    }
                case EVENT_PROP_EXPR:
                    {
                        eventPropertyExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr_AST = currentAST.root;
                        break;
                    }
                case NOT_EXPR:
                case EVAL_AND_EXPR:
                case EVAL_OR_EXPR:
                case EVAL_EQUALS_EXPR:
                case EVAL_NOTEQUALS_EXPR:
                case LT_:
                case GT:
                case LE:
                case GE:
                    {
                        evalExprChoice(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr_AST = currentAST.root;
                        break;
                    }
                case SUM:
                case AVG:
                case COALESCE:
                case MEDIAN:
                case STDDEV:
                case AVEDEV:
                case COUNT:
                    {
                        f = _t == ASTNULL ? null : _t;
                        builtinFunc(_t);
                        _t = retTree_;
                        f_AST = (AST)returnAST;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(f_AST);
                        valueExpr_AST = currentAST.root;
                        break;
                    }
                case LIB_FUNCTION:
                    {
                        l = _t == ASTNULL ? null : _t;
                        libFunc(_t);
                        _t = retTree_;
                        l_AST = (AST)returnAST;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(l_AST);
                        valueExpr_AST = currentAST.root;
                        break;
                    }
                case CASE:
                case CASE2:
                    {
                        cs = _t == ASTNULL ? null : _t;
                        caseExpr(_t);
                        _t = retTree_;
                        cs_AST = (AST)returnAST;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(cs_AST);
                        valueExpr_AST = currentAST.root;
                        break;
                    }
                case IN_SET:
                case NOT_IN_SET:
                    {
                        in_ = _t == ASTNULL ? null : _t;
                        inExpr(_t);
                        _t = retTree_;
                        in__AST = (AST)returnAST;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(in__AST);
                        valueExpr_AST = currentAST.root;
                        break;
                    }
                case BETWEEN:
                case NOT_BETWEEN:
                    {
                        b = _t == ASTNULL ? null : _t;
                        betweenExpr(_t);
                        _t = retTree_;
                        b_AST = (AST)returnAST;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(b_AST);
                        valueExpr_AST = currentAST.root;
                        break;
                    }
                case LIKE:
                case NOT_LIKE:
                    {
                        li = _t == ASTNULL ? null : _t;
                        likeExpr(_t);
                        _t = retTree_;
                        li_AST = (AST)returnAST;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(li_AST);
                        valueExpr_AST = currentAST.root;
                        break;
                    }
                case REGEXP:
                case NOT_REGEXP:
                    {
                        r = _t == ASTNULL ? null : _t;
                        regExpExpr(_t);
                        _t = retTree_;
                        r_AST = (AST)returnAST;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(r_AST);
                        valueExpr_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = valueExpr_AST;
            retTree_ = _t;
        }

        public void outerJoinIdent(AST _t) //throws RecognitionException
        {

            AST outerJoinIdent_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST outerJoinIdent_AST = null;
            AST tl = null;
            AST tl_AST = null;
            AST tr = null;
            AST tr_AST = null;
            AST tf = null;
            AST tf_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case LEFT_OUTERJOIN_EXPR:
                    {
                        AST __t33 = _t;
                        tl = (ASTNULL == _t) ? null : (AST)_t;
                        AST tl_AST_in = null;
                        tl_AST = astFactory.create(tl);
                        astFactory.addASTChild(ref currentAST, tl_AST);
                        ASTPair __currentAST33 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, LEFT_OUTERJOIN_EXPR);
                        _t = _t.getFirstChild();
                        eventPropertyExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        eventPropertyExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(tl_AST);
                        currentAST = __currentAST33;
                        _t = __t33;
                        _t = _t.getNextSibling();
                        outerJoinIdent_AST = currentAST.root;
                        break;
                    }
                case RIGHT_OUTERJOIN_EXPR:
                    {
                        AST __t34 = _t;
                        tr = (ASTNULL == _t) ? null : (AST)_t;
                        AST tr_AST_in = null;
                        tr_AST = astFactory.create(tr);
                        astFactory.addASTChild(ref currentAST, tr_AST);
                        ASTPair __currentAST34 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, RIGHT_OUTERJOIN_EXPR);
                        _t = _t.getFirstChild();
                        eventPropertyExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        eventPropertyExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(tr_AST);
                        currentAST = __currentAST34;
                        _t = __t34;
                        _t = _t.getNextSibling();
                        outerJoinIdent_AST = currentAST.root;
                        break;
                    }
                case FULL_OUTERJOIN_EXPR:
                    {
                        AST __t35 = _t;
                        tf = (ASTNULL == _t) ? null : (AST)_t;
                        AST tf_AST_in = null;
                        tf_AST = astFactory.create(tf);
                        astFactory.addASTChild(ref currentAST, tf_AST);
                        ASTPair __currentAST35 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, FULL_OUTERJOIN_EXPR);
                        _t = _t.getFirstChild();
                        eventPropertyExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        eventPropertyExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(tf_AST);
                        currentAST = __currentAST35;
                        _t = __t35;
                        _t = _t.getNextSibling();
                        outerJoinIdent_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = outerJoinIdent_AST;
            retTree_ = _t;
        }

        public void eventPropertyExpr(AST _t) //throws RecognitionException
        {

            AST eventPropertyExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST eventPropertyExpr_AST = null;
            AST p = null;
            AST p_AST = null;

            AST __t197 = _t;
            p = (ASTNULL == _t) ? null : (AST)_t;
            AST p_AST_in = null;
            p_AST = astFactory.create(p);
            astFactory.addASTChild(ref currentAST, p_AST);
            ASTPair __currentAST197 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, EVENT_PROP_EXPR);
            _t = _t.getFirstChild();
            eventPropertyAtomic(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            {    // ( ... )*
                for (; ; )
                {
                    if (_t == null)
                        _t = ASTNULL;
                    if (((_t.Type >= EVENT_PROP_SIMPLE && _t.Type <= EVENT_PROP_INDEXED)))
                    {
                        eventPropertyAtomic(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                    }
                    else
                    {
                        goto _loop199_breakloop;
                    }

                }
            _loop199_breakloop: ;
            }    // ( ... )*
            currentAST = __currentAST197;
            _t = __t197;
            _t = _t.getNextSibling();
            leaveNode(p_AST);
            eventPropertyExpr_AST = currentAST.root;
            returnAST = eventPropertyExpr_AST;
            retTree_ = _t;
        }

        public void eventFilterExpr(AST _t) //throws RecognitionException
        {

            AST eventFilterExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST eventFilterExpr_AST = null;
            AST f = null;
            AST f_AST = null;

            AST __t175 = _t;
            f = (ASTNULL == _t) ? null : (AST)_t;
            AST f_AST_in = null;
            f_AST = astFactory.create(f);
            astFactory.addASTChild(ref currentAST, f_AST);
            ASTPair __currentAST175 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, EVENT_FILTER_EXPR);
            _t = _t.getFirstChild();
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case EVENT_FILTER_NAME_TAG:
                        {
                            AST tmp21_AST = null;
                            AST tmp21_AST_in = null;
                            tmp21_AST = astFactory.create(_t);
                            tmp21_AST_in = _t;
                            astFactory.addASTChild(ref currentAST, tmp21_AST);
                            match(_t, EVENT_FILTER_NAME_TAG);
                            _t = _t.getNextSibling();
                            break;
                        }
                    case CLASS_IDENT:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            AST tmp22_AST = null;
            AST tmp22_AST_in = null;
            tmp22_AST = astFactory.create(_t);
            tmp22_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp22_AST);
            match(_t, CLASS_IDENT);
            _t = _t.getNextSibling();
            {    // ( ... )*
                for (; ; )
                {
                    if (_t == null)
                        _t = ASTNULL;
                    if ((_t.Type == EVENT_FILTER_PARAM))
                    {
                        filterParam(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                    }
                    else
                    {
                        goto _loop178_breakloop;
                    }

                }
            _loop178_breakloop: ;
            }    // ( ... )*
            leaveNode(f_AST);
            currentAST = __currentAST175;
            _t = __t175;
            _t = _t.getNextSibling();
            eventFilterExpr_AST = currentAST.root;
            returnAST = eventFilterExpr_AST;
            retTree_ = _t;
        }

        public void patternInclusionExpression(AST _t) //throws RecognitionException
        {

            AST patternInclusionExpression_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST patternInclusionExpression_AST = null;
            AST p = null;
            AST p_AST = null;

            AST __t42 = _t;
            p = (ASTNULL == _t) ? null : (AST)_t;
            AST p_AST_in = null;
            p_AST = astFactory.create(p);
            astFactory.addASTChild(ref currentAST, p_AST);
            ASTPair __currentAST42 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, PATTERN_INCL_EXPR);
            _t = _t.getFirstChild();
            setIsPatternWalk(true);
            exprChoice(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            setIsPatternWalk(false); leaveNode(p_AST);
            currentAST = __currentAST42;
            _t = __t42;
            _t = _t.getNextSibling();
            patternInclusionExpression_AST = currentAST.root;
            returnAST = patternInclusionExpression_AST;
            retTree_ = _t;
        }

        public void databaseJoinExpression(AST _t) //throws RecognitionException
        {

            AST databaseJoinExpression_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST databaseJoinExpression_AST = null;
            AST d = null;
            AST d_AST = null;

            AST __t44 = _t;
            d = (ASTNULL == _t) ? null : (AST)_t;
            AST d_AST_in = null;
            d_AST = astFactory.create(d);
            astFactory.addASTChild(ref currentAST, d_AST);
            ASTPair __currentAST44 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, DATABASE_JOIN_EXPR);
            _t = _t.getFirstChild();
            AST tmp23_AST = null;
            AST tmp23_AST_in = null;
            tmp23_AST = astFactory.create(_t);
            tmp23_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp23_AST);
            match(_t, IDENT);
            _t = _t.getNextSibling();
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case STRING_LITERAL:
                        {
                            AST tmp24_AST = null;
                            AST tmp24_AST_in = null;
                            tmp24_AST = astFactory.create(_t);
                            tmp24_AST_in = _t;
                            astFactory.addASTChild(ref currentAST, tmp24_AST);
                            match(_t, STRING_LITERAL);
                            _t = _t.getNextSibling();
                            break;
                        }
                    case QUOTED_STRING_LITERAL:
                        {
                            AST tmp25_AST = null;
                            AST tmp25_AST_in = null;
                            tmp25_AST = astFactory.create(_t);
                            tmp25_AST_in = _t;
                            astFactory.addASTChild(ref currentAST, tmp25_AST);
                            match(_t, QUOTED_STRING_LITERAL);
                            _t = _t.getNextSibling();
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            currentAST = __currentAST44;
            _t = __t44;
            _t = _t.getNextSibling();
            databaseJoinExpression_AST = currentAST.root;
            returnAST = databaseJoinExpression_AST;
            retTree_ = _t;
        }

        public void viewListExpr(AST _t) //throws RecognitionException
        {

            AST viewListExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST viewListExpr_AST = null;

            viewExpr(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            {    // ( ... )*
                for (; ; )
                {
                    if (_t == null)
                        _t = ASTNULL;
                    if ((_t.Type == VIEW_EXPR))
                    {
                        viewExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                    }
                    else
                    {
                        goto _loop48_breakloop;
                    }

                }
            _loop48_breakloop: ;
            }    // ( ... )*
            viewListExpr_AST = currentAST.root;
            returnAST = viewListExpr_AST;
            retTree_ = _t;
        }

        public void exprChoice(AST _t) //throws RecognitionException
        {

            AST exprChoice_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST exprChoice_AST = null;
            AST a = null;
            AST a_AST = null;
            AST n = null;
            AST n_AST = null;
            AST g = null;
            AST g_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case EVENT_FILTER_EXPR:
                case OBSERVER_EXPR:
                    {
                        atomicExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        exprChoice_AST = currentAST.root;
                        break;
                    }
                case OR_EXPR:
                case AND_EXPR:
                case FOLLOWED_BY_EXPR:
                    {
                        patternOp(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        exprChoice_AST = currentAST.root;
                        break;
                    }
                case EVERY_EXPR:
                    {
                        AST __t155 = _t;
                        a = (ASTNULL == _t) ? null : (AST)_t;
                        AST a_AST_in = null;
                        a_AST = astFactory.create(a);
                        astFactory.addASTChild(ref currentAST, a_AST);
                        ASTPair __currentAST155 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, EVERY_EXPR);
                        _t = _t.getFirstChild();
                        exprChoice(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(a_AST);
                        currentAST = __currentAST155;
                        _t = __t155;
                        _t = _t.getNextSibling();
                        exprChoice_AST = currentAST.root;
                        break;
                    }
                case NOT_EXPR:
                    {
                        AST __t156 = _t;
                        n = (ASTNULL == _t) ? null : (AST)_t;
                        AST n_AST_in = null;
                        n_AST = astFactory.create(n);
                        astFactory.addASTChild(ref currentAST, n_AST);
                        ASTPair __currentAST156 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, NOT_EXPR);
                        _t = _t.getFirstChild();
                        exprChoice(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(n_AST);
                        currentAST = __currentAST156;
                        _t = __t156;
                        _t = _t.getNextSibling();
                        exprChoice_AST = currentAST.root;
                        break;
                    }
                case GUARD_EXPR:
                    {
                        AST __t157 = _t;
                        g = (ASTNULL == _t) ? null : (AST)_t;
                        AST g_AST_in = null;
                        g_AST = astFactory.create(g);
                        astFactory.addASTChild(ref currentAST, g_AST);
                        ASTPair __currentAST157 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, GUARD_EXPR);
                        _t = _t.getFirstChild();
                        exprChoice(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        AST tmp26_AST = null;
                        AST tmp26_AST_in = null;
                        tmp26_AST = astFactory.create(_t);
                        tmp26_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp26_AST);
                        match(_t, IDENT);
                        _t = _t.getNextSibling();
                        AST tmp27_AST = null;
                        AST tmp27_AST_in = null;
                        tmp27_AST = astFactory.create(_t);
                        tmp27_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp27_AST);
                        match(_t, IDENT);
                        _t = _t.getNextSibling();
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (null == _t)
                                    _t = ASTNULL;
                                switch (_t.Type)
                                {
                                    case INT_TYPE:
                                    case LONG_TYPE:
                                    case FLOAT_TYPE:
                                    case DOUBLE_TYPE:
                                    case STRING_TYPE:
                                    case BOOL_TYPE:
                                    case NULL_TYPE:
                                        {
                                            constant(_t);
                                            _t = retTree_;
                                            astFactory.addASTChild(ref currentAST, returnAST);
                                            break;
                                        }
                                    case TIME_PERIOD:
                                        {
                                            time_period(_t);
                                            _t = retTree_;
                                            astFactory.addASTChild(ref currentAST, returnAST);
                                            break;
                                        }
                                    default:
                                        {
                                            goto _loop159_breakloop;
                                        }
                                }
                            }
                        _loop159_breakloop: ;
                        }    // ( ... )*
                        leaveNode(g_AST);
                        currentAST = __currentAST157;
                        _t = __t157;
                        _t = _t.getNextSibling();
                        exprChoice_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = exprChoice_AST;
            retTree_ = _t;
        }

        public void viewExpr(AST _t) //throws RecognitionException
        {

            AST viewExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST viewExpr_AST = null;
            AST n = null;
            AST n_AST = null;

            AST __t50 = _t;
            n = (ASTNULL == _t) ? null : (AST)_t;
            AST n_AST_in = null;
            n_AST = astFactory.create(n);
            astFactory.addASTChild(ref currentAST, n_AST);
            ASTPair __currentAST50 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, VIEW_EXPR);
            _t = _t.getFirstChild();
            AST tmp28_AST = null;
            AST tmp28_AST_in = null;
            tmp28_AST = astFactory.create(_t);
            tmp28_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp28_AST);
            match(_t, IDENT);
            _t = _t.getNextSibling();
            AST tmp29_AST = null;
            AST tmp29_AST_in = null;
            tmp29_AST = astFactory.create(_t);
            tmp29_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp29_AST);
            match(_t, IDENT);
            _t = _t.getNextSibling();
            {    // ( ... )*
                for (; ; )
                {
                    if (_t == null)
                        _t = ASTNULL;
                    if ((tokenSet_1_.member(_t.Type)))
                    {
                        parameter(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                    }
                    else
                    {
                        goto _loop52_breakloop;
                    }

                }
            _loop52_breakloop: ;
            }    // ( ... )*
            leaveNode(n_AST);
            currentAST = __currentAST50;
            _t = __t50;
            _t = _t.getNextSibling();
            viewExpr_AST = currentAST.root;
            returnAST = viewExpr_AST;
            retTree_ = _t;
        }

        public void parameter(AST _t) //throws RecognitionException
        {

            AST parameter_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST parameter_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case NUMERIC_PARAM_RANGE:
                case NUMERIC_PARAM_FREQUENCY:
                case TIME_PERIOD:
                case INT_TYPE:
                case LONG_TYPE:
                case FLOAT_TYPE:
                case DOUBLE_TYPE:
                case STRING_TYPE:
                case BOOL_TYPE:
                case NULL_TYPE:
                case STAR:
                    {
                        singleParameter(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        parameter_AST = currentAST.root;
                        break;
                    }
                case NUMERIC_PARAM_LIST:
                    {
                        AST __t206 = _t;
                        AST tmp30_AST = null;
                        AST tmp30_AST_in = null;
                        tmp30_AST = astFactory.create(_t);
                        tmp30_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp30_AST);
                        ASTPair __currentAST206 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, NUMERIC_PARAM_LIST);
                        _t = _t.getFirstChild();
                        { // ( ... )+
                            int _cnt208 = 0;
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((_t.Type == NUMERIC_PARAM_RANGE || _t.Type == NUM_INT || _t.Type == NUMERIC_PARAM_FREQUENCE))
                                {
                                    numericParameterList(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    if (_cnt208 >= 1) { goto _loop208_breakloop; } else { throw new NoViableAltException(_t); ; }
                                }

                                _cnt208++;
                            }
                        _loop208_breakloop: ;
                        }    // ( ... )+
                        currentAST = __currentAST206;
                        _t = __t206;
                        _t = _t.getNextSibling();
                        parameter_AST = currentAST.root;
                        break;
                    }
                case ARRAY_PARAM_LIST:
                    {
                        AST __t209 = _t;
                        AST tmp31_AST = null;
                        AST tmp31_AST_in = null;
                        tmp31_AST = astFactory.create(_t);
                        tmp31_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp31_AST);
                        ASTPair __currentAST209 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, ARRAY_PARAM_LIST);
                        _t = _t.getFirstChild();
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if (((_t.Type >= INT_TYPE && _t.Type <= NULL_TYPE)))
                                {
                                    constant(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop211_breakloop;
                                }

                            }
                        _loop211_breakloop: ;
                        }    // ( ... )*
                        currentAST = __currentAST209;
                        _t = __t209;
                        _t = _t.getNextSibling();
                        parameter_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = parameter_AST;
            retTree_ = _t;
        }

        public void orderByElement(AST _t) //throws RecognitionException
        {

            AST orderByElement_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST orderByElement_AST = null;
            AST e = null;
            AST e_AST = null;

            AST __t64 = _t;
            e = (ASTNULL == _t) ? null : (AST)_t;
            AST e_AST_in = null;
            e_AST = astFactory.create(e);
            astFactory.addASTChild(ref currentAST, e_AST);
            ASTPair __currentAST64 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, ORDER_ELEMENT_EXPR);
            _t = _t.getFirstChild();
            valueExpr(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case ASC:
                        {
                            AST tmp32_AST = null;
                            AST tmp32_AST_in = null;
                            tmp32_AST = astFactory.create(_t);
                            tmp32_AST_in = _t;
                            astFactory.addASTChild(ref currentAST, tmp32_AST);
                            match(_t, ASC);
                            _t = _t.getNextSibling();
                            break;
                        }
                    case DESC:
                        {
                            AST tmp33_AST = null;
                            AST tmp33_AST_in = null;
                            tmp33_AST = astFactory.create(_t);
                            tmp33_AST_in = _t;
                            astFactory.addASTChild(ref currentAST, tmp33_AST);
                            match(_t, DESC);
                            _t = _t.getNextSibling();
                            break;
                        }
                    case 3:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            leaveNode(e_AST);
            currentAST = __currentAST64;
            _t = __t64;
            _t = _t.getNextSibling();
            orderByElement_AST = currentAST.root;
            returnAST = orderByElement_AST;
            retTree_ = _t;
        }

        public void number(AST _t) //throws RecognitionException
        {

            AST number_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST number_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case INT_TYPE:
                    {
                        AST tmp34_AST = null;
                        AST tmp34_AST_in = null;
                        tmp34_AST = astFactory.create(_t);
                        tmp34_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp34_AST);
                        match(_t, INT_TYPE);
                        _t = _t.getNextSibling();
                        number_AST = currentAST.root;
                        break;
                    }
                case LONG_TYPE:
                    {
                        AST tmp35_AST = null;
                        AST tmp35_AST_in = null;
                        tmp35_AST = astFactory.create(_t);
                        tmp35_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp35_AST);
                        match(_t, LONG_TYPE);
                        _t = _t.getNextSibling();
                        number_AST = currentAST.root;
                        break;
                    }
                case FLOAT_TYPE:
                    {
                        AST tmp36_AST = null;
                        AST tmp36_AST_in = null;
                        tmp36_AST = astFactory.create(_t);
                        tmp36_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp36_AST);
                        match(_t, FLOAT_TYPE);
                        _t = _t.getNextSibling();
                        number_AST = currentAST.root;
                        break;
                    }
                case DOUBLE_TYPE:
                    {
                        AST tmp37_AST = null;
                        AST tmp37_AST_in = null;
                        tmp37_AST = astFactory.create(_t);
                        tmp37_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp37_AST);
                        match(_t, DOUBLE_TYPE);
                        _t = _t.getNextSibling();
                        number_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = number_AST;
            retTree_ = _t;
        }

        public void relationalExpr(AST _t) //throws RecognitionException
        {

            AST relationalExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST relationalExpr_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case LT_:
                    {
                        AST __t76 = _t;
                        AST tmp38_AST = null;
                        AST tmp38_AST_in = null;
                        tmp38_AST = astFactory.create(_t);
                        tmp38_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp38_AST);
                        ASTPair __currentAST76 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, LT_);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST76;
                        _t = __t76;
                        _t = _t.getNextSibling();
                        relationalExpr_AST = currentAST.root;
                        break;
                    }
                case GT:
                    {
                        AST __t77 = _t;
                        AST tmp39_AST = null;
                        AST tmp39_AST_in = null;
                        tmp39_AST = astFactory.create(_t);
                        tmp39_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp39_AST);
                        ASTPair __currentAST77 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, GT);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST77;
                        _t = __t77;
                        _t = _t.getNextSibling();
                        relationalExpr_AST = currentAST.root;
                        break;
                    }
                case LE:
                    {
                        AST __t78 = _t;
                        AST tmp40_AST = null;
                        AST tmp40_AST_in = null;
                        tmp40_AST = astFactory.create(_t);
                        tmp40_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp40_AST);
                        ASTPair __currentAST78 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, LE);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST78;
                        _t = __t78;
                        _t = _t.getNextSibling();
                        relationalExpr_AST = currentAST.root;
                        break;
                    }
                case GE:
                    {
                        AST __t79 = _t;
                        AST tmp41_AST = null;
                        AST tmp41_AST_in = null;
                        tmp41_AST = astFactory.create(_t);
                        tmp41_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp41_AST);
                        ASTPair __currentAST79 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, GE);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST79;
                        _t = __t79;
                        _t = _t.getNextSibling();
                        relationalExpr_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = relationalExpr_AST;
            retTree_ = _t;
        }

        public void evalExprChoice(AST _t) //throws RecognitionException
        {

            AST evalExprChoice_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST evalExprChoice_AST = null;
            AST jo = null;
            AST jo_AST = null;
            AST ja = null;
            AST ja_AST = null;
            AST je = null;
            AST je_AST = null;
            AST jne = null;
            AST jne_AST = null;
            AST n = null;
            AST n_AST = null;
            AST r_AST = null;
            AST r = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case EVAL_OR_EXPR:
                    {
                        AST __t81 = _t;
                        jo = (ASTNULL == _t) ? null : (AST)_t;
                        AST jo_AST_in = null;
                        jo_AST = astFactory.create(jo);
                        astFactory.addASTChild(ref currentAST, jo_AST);
                        ASTPair __currentAST81 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, EVAL_OR_EXPR);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_0_.member(_t.Type)))
                                {
                                    valueExpr(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop83_breakloop;
                                }

                            }
                        _loop83_breakloop: ;
                        }    // ( ... )*
                        leaveNode(jo_AST);
                        currentAST = __currentAST81;
                        _t = __t81;
                        _t = _t.getNextSibling();
                        evalExprChoice_AST = currentAST.root;
                        break;
                    }
                case EVAL_AND_EXPR:
                    {
                        AST __t84 = _t;
                        ja = (ASTNULL == _t) ? null : (AST)_t;
                        AST ja_AST_in = null;
                        ja_AST = astFactory.create(ja);
                        astFactory.addASTChild(ref currentAST, ja_AST);
                        ASTPair __currentAST84 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, EVAL_AND_EXPR);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_0_.member(_t.Type)))
                                {
                                    valueExpr(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop86_breakloop;
                                }

                            }
                        _loop86_breakloop: ;
                        }    // ( ... )*
                        leaveNode(ja_AST);
                        currentAST = __currentAST84;
                        _t = __t84;
                        _t = _t.getNextSibling();
                        evalExprChoice_AST = currentAST.root;
                        break;
                    }
                case EVAL_EQUALS_EXPR:
                    {
                        AST __t87 = _t;
                        je = (ASTNULL == _t) ? null : (AST)_t;
                        AST je_AST_in = null;
                        je_AST = astFactory.create(je);
                        astFactory.addASTChild(ref currentAST, je_AST);
                        ASTPair __currentAST87 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, EVAL_EQUALS_EXPR);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(je_AST);
                        currentAST = __currentAST87;
                        _t = __t87;
                        _t = _t.getNextSibling();
                        evalExprChoice_AST = currentAST.root;
                        break;
                    }
                case EVAL_NOTEQUALS_EXPR:
                    {
                        AST __t88 = _t;
                        jne = (ASTNULL == _t) ? null : (AST)_t;
                        AST jne_AST_in = null;
                        jne_AST = astFactory.create(jne);
                        astFactory.addASTChild(ref currentAST, jne_AST);
                        ASTPair __currentAST88 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, EVAL_NOTEQUALS_EXPR);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(jne_AST);
                        currentAST = __currentAST88;
                        _t = __t88;
                        _t = _t.getNextSibling();
                        evalExprChoice_AST = currentAST.root;
                        break;
                    }
                case NOT_EXPR:
                    {
                        AST __t89 = _t;
                        n = (ASTNULL == _t) ? null : (AST)_t;
                        AST n_AST_in = null;
                        n_AST = astFactory.create(n);
                        astFactory.addASTChild(ref currentAST, n_AST);
                        ASTPair __currentAST89 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, NOT_EXPR);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(n_AST);
                        currentAST = __currentAST89;
                        _t = __t89;
                        _t = _t.getNextSibling();
                        evalExprChoice_AST = currentAST.root;
                        break;
                    }
                case LT_:
                case GT:
                case LE:
                case GE:
                    {
                        r = _t == ASTNULL ? null : _t;
                        relationalExpr(_t);
                        _t = retTree_;
                        r_AST = (AST)returnAST;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        leaveNode(r_AST);
                        evalExprChoice_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = evalExprChoice_AST;
            retTree_ = _t;
        }

        public void constant(AST _t) //throws RecognitionException
        {

            AST constant_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST constant_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case INT_TYPE:
                    {
                        AST tmp42_AST = null;
                        AST tmp42_AST_in = null;
                        tmp42_AST = astFactory.create(_t);
                        tmp42_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp42_AST);
                        match(_t, INT_TYPE);
                        _t = _t.getNextSibling();
                        constant_AST = currentAST.root;
                        break;
                    }
                case LONG_TYPE:
                    {
                        AST tmp43_AST = null;
                        AST tmp43_AST_in = null;
                        tmp43_AST = astFactory.create(_t);
                        tmp43_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp43_AST);
                        match(_t, LONG_TYPE);
                        _t = _t.getNextSibling();
                        constant_AST = currentAST.root;
                        break;
                    }
                case FLOAT_TYPE:
                    {
                        AST tmp44_AST = null;
                        AST tmp44_AST_in = null;
                        tmp44_AST = astFactory.create(_t);
                        tmp44_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp44_AST);
                        match(_t, FLOAT_TYPE);
                        _t = _t.getNextSibling();
                        constant_AST = currentAST.root;
                        break;
                    }
                case DOUBLE_TYPE:
                    {
                        AST tmp45_AST = null;
                        AST tmp45_AST_in = null;
                        tmp45_AST = astFactory.create(_t);
                        tmp45_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp45_AST);
                        match(_t, DOUBLE_TYPE);
                        _t = _t.getNextSibling();
                        constant_AST = currentAST.root;
                        break;
                    }
                case STRING_TYPE:
                    {
                        AST tmp46_AST = null;
                        AST tmp46_AST_in = null;
                        tmp46_AST = astFactory.create(_t);
                        tmp46_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp46_AST);
                        match(_t, STRING_TYPE);
                        _t = _t.getNextSibling();
                        constant_AST = currentAST.root;
                        break;
                    }
                case BOOL_TYPE:
                    {
                        AST tmp47_AST = null;
                        AST tmp47_AST_in = null;
                        tmp47_AST = astFactory.create(_t);
                        tmp47_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp47_AST);
                        match(_t, BOOL_TYPE);
                        _t = _t.getNextSibling();
                        constant_AST = currentAST.root;
                        break;
                    }
                case NULL_TYPE:
                    {
                        AST tmp48_AST = null;
                        AST tmp48_AST_in = null;
                        tmp48_AST = astFactory.create(_t);
                        tmp48_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp48_AST);
                        match(_t, NULL_TYPE);
                        _t = _t.getNextSibling();
                        constant_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = constant_AST;
            retTree_ = _t;
        }

        public void arithmeticExpr(AST _t) //throws RecognitionException
        {

            AST arithmeticExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST arithmeticExpr_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case PLUS:
                    {
                        AST __t136 = _t;
                        AST tmp49_AST = null;
                        AST tmp49_AST_in = null;
                        tmp49_AST = astFactory.create(_t);
                        tmp49_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp49_AST);
                        ASTPair __currentAST136 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, PLUS);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST136;
                        _t = __t136;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                case MINUS:
                    {
                        AST __t137 = _t;
                        AST tmp50_AST = null;
                        AST tmp50_AST_in = null;
                        tmp50_AST = astFactory.create(_t);
                        tmp50_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp50_AST);
                        ASTPair __currentAST137 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, MINUS);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST137;
                        _t = __t137;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                case DIV:
                    {
                        AST __t138 = _t;
                        AST tmp51_AST = null;
                        AST tmp51_AST_in = null;
                        tmp51_AST = astFactory.create(_t);
                        tmp51_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp51_AST);
                        ASTPair __currentAST138 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, DIV);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST138;
                        _t = __t138;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                case STAR:
                    {
                        AST __t139 = _t;
                        AST tmp52_AST = null;
                        AST tmp52_AST_in = null;
                        tmp52_AST = astFactory.create(_t);
                        tmp52_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp52_AST);
                        ASTPair __currentAST139 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, STAR);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST139;
                        _t = __t139;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                case MOD:
                    {
                        AST __t140 = _t;
                        AST tmp53_AST = null;
                        AST tmp53_AST_in = null;
                        tmp53_AST = astFactory.create(_t);
                        tmp53_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp53_AST);
                        ASTPair __currentAST140 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, MOD);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST140;
                        _t = __t140;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                case BAND:
                    {
                        AST __t141 = _t;
                        AST tmp54_AST = null;
                        AST tmp54_AST_in = null;
                        tmp54_AST = astFactory.create(_t);
                        tmp54_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp54_AST);
                        ASTPair __currentAST141 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, BAND);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST141;
                        _t = __t141;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                case BOR:
                    {
                        AST __t142 = _t;
                        AST tmp55_AST = null;
                        AST tmp55_AST_in = null;
                        tmp55_AST = astFactory.create(_t);
                        tmp55_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp55_AST);
                        ASTPair __currentAST142 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, BOR);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST142;
                        _t = __t142;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                case BXOR:
                    {
                        AST __t143 = _t;
                        AST tmp56_AST = null;
                        AST tmp56_AST_in = null;
                        tmp56_AST = astFactory.create(_t);
                        tmp56_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp56_AST);
                        ASTPair __currentAST143 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, BXOR);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST143;
                        _t = __t143;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                case CONCAT:
                    {
                        AST __t144 = _t;
                        AST tmp57_AST = null;
                        AST tmp57_AST_in = null;
                        tmp57_AST = astFactory.create(_t);
                        tmp57_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp57_AST);
                        ASTPair __currentAST144 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, CONCAT);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_0_.member(_t.Type)))
                                {
                                    valueExpr(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop146_breakloop;
                                }

                            }
                        _loop146_breakloop: ;
                        }    // ( ... )*
                        currentAST = __currentAST144;
                        _t = __t144;
                        _t = _t.getNextSibling();
                        arithmeticExpr_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = arithmeticExpr_AST;
            retTree_ = _t;
        }

        public void builtinFunc(AST _t) //throws RecognitionException
        {

            AST builtinFunc_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST builtinFunc_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case SUM:
                    {
                        AST __t119 = _t;
                        AST tmp58_AST = null;
                        AST tmp58_AST_in = null;
                        tmp58_AST = astFactory.create(_t);
                        tmp58_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp58_AST);
                        ASTPair __currentAST119 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, SUM);
                        _t = _t.getFirstChild();
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case DISTINCT:
                                    {
                                        AST tmp59_AST = null;
                                        AST tmp59_AST_in = null;
                                        tmp59_AST = astFactory.create(_t);
                                        tmp59_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp59_AST);
                                        match(_t, DISTINCT);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
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
                                case EVAL_AND_EXPR:
                                case EVAL_OR_EXPR:
                                case EVAL_EQUALS_EXPR:
                                case EVAL_NOTEQUALS_EXPR:
                                case EVENT_PROP_EXPR:
                                case CONCAT:
                                case LIB_FUNCTION:
                                case NOT_IN_SET:
                                case NOT_BETWEEN:
                                case NOT_LIKE:
                                case NOT_REGEXP:
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                case STRING_TYPE:
                                case BOOL_TYPE:
                                case NULL_TYPE:
                                case MINUS:
                                case PLUS:
                                case STAR:
                                case BAND:
                                case BOR:
                                case BXOR:
                                case LT_:
                                case GT:
                                case LE:
                                case GE:
                                case DIV:
                                case MOD:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST119;
                        _t = __t119;
                        _t = _t.getNextSibling();
                        builtinFunc_AST = currentAST.root;
                        break;
                    }
                case AVG:
                    {
                        AST __t121 = _t;
                        AST tmp60_AST = null;
                        AST tmp60_AST_in = null;
                        tmp60_AST = astFactory.create(_t);
                        tmp60_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp60_AST);
                        ASTPair __currentAST121 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, AVG);
                        _t = _t.getFirstChild();
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case DISTINCT:
                                    {
                                        AST tmp61_AST = null;
                                        AST tmp61_AST_in = null;
                                        tmp61_AST = astFactory.create(_t);
                                        tmp61_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp61_AST);
                                        match(_t, DISTINCT);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
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
                                case EVAL_AND_EXPR:
                                case EVAL_OR_EXPR:
                                case EVAL_EQUALS_EXPR:
                                case EVAL_NOTEQUALS_EXPR:
                                case EVENT_PROP_EXPR:
                                case CONCAT:
                                case LIB_FUNCTION:
                                case NOT_IN_SET:
                                case NOT_BETWEEN:
                                case NOT_LIKE:
                                case NOT_REGEXP:
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                case STRING_TYPE:
                                case BOOL_TYPE:
                                case NULL_TYPE:
                                case MINUS:
                                case PLUS:
                                case STAR:
                                case BAND:
                                case BOR:
                                case BXOR:
                                case LT_:
                                case GT:
                                case LE:
                                case GE:
                                case DIV:
                                case MOD:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST121;
                        _t = __t121;
                        _t = _t.getNextSibling();
                        builtinFunc_AST = currentAST.root;
                        break;
                    }
                case COUNT:
                    {
                        AST __t123 = _t;
                        AST tmp62_AST = null;
                        AST tmp62_AST_in = null;
                        tmp62_AST = astFactory.create(_t);
                        tmp62_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp62_AST);
                        ASTPair __currentAST123 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, COUNT);
                        _t = _t.getFirstChild();
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
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
                                case DISTINCT:
                                case EVAL_AND_EXPR:
                                case EVAL_OR_EXPR:
                                case EVAL_EQUALS_EXPR:
                                case EVAL_NOTEQUALS_EXPR:
                                case EVENT_PROP_EXPR:
                                case CONCAT:
                                case LIB_FUNCTION:
                                case NOT_IN_SET:
                                case NOT_BETWEEN:
                                case NOT_LIKE:
                                case NOT_REGEXP:
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                case STRING_TYPE:
                                case BOOL_TYPE:
                                case NULL_TYPE:
                                case MINUS:
                                case PLUS:
                                case STAR:
                                case BAND:
                                case BOR:
                                case BXOR:
                                case LT_:
                                case GT:
                                case LE:
                                case GE:
                                case DIV:
                                case MOD:
                                    {
                                        {
                                            if (null == _t)
                                                _t = ASTNULL;
                                            switch (_t.Type)
                                            {
                                                case DISTINCT:
                                                    {
                                                        AST tmp63_AST = null;
                                                        AST tmp63_AST_in = null;
                                                        tmp63_AST = astFactory.create(_t);
                                                        tmp63_AST_in = _t;
                                                        astFactory.addASTChild(ref currentAST, tmp63_AST);
                                                        match(_t, DISTINCT);
                                                        _t = _t.getNextSibling();
                                                        break;
                                                    }
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
                                                case EVAL_AND_EXPR:
                                                case EVAL_OR_EXPR:
                                                case EVAL_EQUALS_EXPR:
                                                case EVAL_NOTEQUALS_EXPR:
                                                case EVENT_PROP_EXPR:
                                                case CONCAT:
                                                case LIB_FUNCTION:
                                                case NOT_IN_SET:
                                                case NOT_BETWEEN:
                                                case NOT_LIKE:
                                                case NOT_REGEXP:
                                                case INT_TYPE:
                                                case LONG_TYPE:
                                                case FLOAT_TYPE:
                                                case DOUBLE_TYPE:
                                                case STRING_TYPE:
                                                case BOOL_TYPE:
                                                case NULL_TYPE:
                                                case MINUS:
                                                case PLUS:
                                                case STAR:
                                                case BAND:
                                                case BOR:
                                                case BXOR:
                                                case LT_:
                                                case GT:
                                                case LE:
                                                case GE:
                                                case DIV:
                                                case MOD:
                                                    {
                                                        break;
                                                    }
                                                default:
                                                    {
                                                        throw new NoViableAltException(_t);
                                                    }
                                            }
                                        }
                                        valueExpr(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        currentAST = __currentAST123;
                        _t = __t123;
                        _t = _t.getNextSibling();
                        builtinFunc_AST = currentAST.root;
                        break;
                    }
                case MEDIAN:
                    {
                        AST __t126 = _t;
                        AST tmp64_AST = null;
                        AST tmp64_AST_in = null;
                        tmp64_AST = astFactory.create(_t);
                        tmp64_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp64_AST);
                        ASTPair __currentAST126 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, MEDIAN);
                        _t = _t.getFirstChild();
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case DISTINCT:
                                    {
                                        AST tmp65_AST = null;
                                        AST tmp65_AST_in = null;
                                        tmp65_AST = astFactory.create(_t);
                                        tmp65_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp65_AST);
                                        match(_t, DISTINCT);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
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
                                case EVAL_AND_EXPR:
                                case EVAL_OR_EXPR:
                                case EVAL_EQUALS_EXPR:
                                case EVAL_NOTEQUALS_EXPR:
                                case EVENT_PROP_EXPR:
                                case CONCAT:
                                case LIB_FUNCTION:
                                case NOT_IN_SET:
                                case NOT_BETWEEN:
                                case NOT_LIKE:
                                case NOT_REGEXP:
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                case STRING_TYPE:
                                case BOOL_TYPE:
                                case NULL_TYPE:
                                case MINUS:
                                case PLUS:
                                case STAR:
                                case BAND:
                                case BOR:
                                case BXOR:
                                case LT_:
                                case GT:
                                case LE:
                                case GE:
                                case DIV:
                                case MOD:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST126;
                        _t = __t126;
                        _t = _t.getNextSibling();
                        builtinFunc_AST = currentAST.root;
                        break;
                    }
                case STDDEV:
                    {
                        AST __t128 = _t;
                        AST tmp66_AST = null;
                        AST tmp66_AST_in = null;
                        tmp66_AST = astFactory.create(_t);
                        tmp66_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp66_AST);
                        ASTPair __currentAST128 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, STDDEV);
                        _t = _t.getFirstChild();
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case DISTINCT:
                                    {
                                        AST tmp67_AST = null;
                                        AST tmp67_AST_in = null;
                                        tmp67_AST = astFactory.create(_t);
                                        tmp67_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp67_AST);
                                        match(_t, DISTINCT);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
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
                                case EVAL_AND_EXPR:
                                case EVAL_OR_EXPR:
                                case EVAL_EQUALS_EXPR:
                                case EVAL_NOTEQUALS_EXPR:
                                case EVENT_PROP_EXPR:
                                case CONCAT:
                                case LIB_FUNCTION:
                                case NOT_IN_SET:
                                case NOT_BETWEEN:
                                case NOT_LIKE:
                                case NOT_REGEXP:
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                case STRING_TYPE:
                                case BOOL_TYPE:
                                case NULL_TYPE:
                                case MINUS:
                                case PLUS:
                                case STAR:
                                case BAND:
                                case BOR:
                                case BXOR:
                                case LT_:
                                case GT:
                                case LE:
                                case GE:
                                case DIV:
                                case MOD:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST128;
                        _t = __t128;
                        _t = _t.getNextSibling();
                        builtinFunc_AST = currentAST.root;
                        break;
                    }
                case AVEDEV:
                    {
                        AST __t130 = _t;
                        AST tmp68_AST = null;
                        AST tmp68_AST_in = null;
                        tmp68_AST = astFactory.create(_t);
                        tmp68_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp68_AST);
                        ASTPair __currentAST130 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, AVEDEV);
                        _t = _t.getFirstChild();
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case DISTINCT:
                                    {
                                        AST tmp69_AST = null;
                                        AST tmp69_AST_in = null;
                                        tmp69_AST = astFactory.create(_t);
                                        tmp69_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp69_AST);
                                        match(_t, DISTINCT);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
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
                                case EVAL_AND_EXPR:
                                case EVAL_OR_EXPR:
                                case EVAL_EQUALS_EXPR:
                                case EVAL_NOTEQUALS_EXPR:
                                case EVENT_PROP_EXPR:
                                case CONCAT:
                                case LIB_FUNCTION:
                                case NOT_IN_SET:
                                case NOT_BETWEEN:
                                case NOT_LIKE:
                                case NOT_REGEXP:
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                case STRING_TYPE:
                                case BOOL_TYPE:
                                case NULL_TYPE:
                                case MINUS:
                                case PLUS:
                                case STAR:
                                case BAND:
                                case BOR:
                                case BXOR:
                                case LT_:
                                case GT:
                                case LE:
                                case GE:
                                case DIV:
                                case MOD:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST130;
                        _t = __t130;
                        _t = _t.getNextSibling();
                        builtinFunc_AST = currentAST.root;
                        break;
                    }
                case COALESCE:
                    {
                        AST __t132 = _t;
                        AST tmp70_AST = null;
                        AST tmp70_AST_in = null;
                        tmp70_AST = astFactory.create(_t);
                        tmp70_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp70_AST);
                        ASTPair __currentAST132 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, COALESCE);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_0_.member(_t.Type)))
                                {
                                    valueExpr(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop134_breakloop;
                                }

                            }
                        _loop134_breakloop: ;
                        }    // ( ... )*
                        currentAST = __currentAST132;
                        _t = __t132;
                        _t = _t.getNextSibling();
                        builtinFunc_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = builtinFunc_AST;
            retTree_ = _t;
        }

        public void libFunc(AST _t) //throws RecognitionException
        {

            AST libFunc_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST libFunc_AST = null;

            AST __t148 = _t;
            AST tmp71_AST = null;
            AST tmp71_AST_in = null;
            tmp71_AST = astFactory.create(_t);
            tmp71_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp71_AST);
            ASTPair __currentAST148 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, LIB_FUNCTION);
            _t = _t.getFirstChild();
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case CLASS_IDENT:
                        {
                            AST tmp72_AST = null;
                            AST tmp72_AST_in = null;
                            tmp72_AST = astFactory.create(_t);
                            tmp72_AST_in = _t;
                            astFactory.addASTChild(ref currentAST, tmp72_AST);
                            match(_t, CLASS_IDENT);
                            _t = _t.getNextSibling();
                            break;
                        }
                    case IDENT:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            AST tmp73_AST = null;
            AST tmp73_AST_in = null;
            tmp73_AST = astFactory.create(_t);
            tmp73_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp73_AST);
            match(_t, IDENT);
            _t = _t.getNextSibling();
            {
                if (null == _t)
                    _t = ASTNULL;
                switch (_t.Type)
                {
                    case DISTINCT:
                        {
                            AST tmp74_AST = null;
                            AST tmp74_AST_in = null;
                            tmp74_AST = astFactory.create(_t);
                            tmp74_AST_in = _t;
                            astFactory.addASTChild(ref currentAST, tmp74_AST);
                            match(_t, DISTINCT);
                            _t = _t.getNextSibling();
                            break;
                        }
                    case 3:
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
                    case EVAL_AND_EXPR:
                    case EVAL_OR_EXPR:
                    case EVAL_EQUALS_EXPR:
                    case EVAL_NOTEQUALS_EXPR:
                    case EVENT_PROP_EXPR:
                    case CONCAT:
                    case LIB_FUNCTION:
                    case NOT_IN_SET:
                    case NOT_BETWEEN:
                    case NOT_LIKE:
                    case NOT_REGEXP:
                    case INT_TYPE:
                    case LONG_TYPE:
                    case FLOAT_TYPE:
                    case DOUBLE_TYPE:
                    case STRING_TYPE:
                    case BOOL_TYPE:
                    case NULL_TYPE:
                    case MINUS:
                    case PLUS:
                    case STAR:
                    case BAND:
                    case BOR:
                    case BXOR:
                    case LT_:
                    case GT:
                    case LE:
                    case GE:
                    case DIV:
                    case MOD:
                        {
                            break;
                        }
                    default:
                        {
                            throw new NoViableAltException(_t);
                        }
                }
            }
            {    // ( ... )*
                for (; ; )
                {
                    if (_t == null)
                        _t = ASTNULL;
                    if ((tokenSet_0_.member(_t.Type)))
                    {
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                    }
                    else
                    {
                        goto _loop152_breakloop;
                    }

                }
            _loop152_breakloop: ;
            }    // ( ... )*
            currentAST = __currentAST148;
            _t = __t148;
            _t = _t.getNextSibling();
            libFunc_AST = currentAST.root;
            returnAST = libFunc_AST;
            retTree_ = _t;
        }

        public void caseExpr(AST _t) //throws RecognitionException
        {

            AST caseExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST caseExpr_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case CASE:
                    {
                        AST __t92 = _t;
                        AST tmp75_AST = null;
                        AST tmp75_AST_in = null;
                        tmp75_AST = astFactory.create(_t);
                        tmp75_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp75_AST);
                        ASTPair __currentAST92 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, CASE);
                        _t = _t.getFirstChild();
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_0_.member(_t.Type)))
                                {
                                    valueExpr(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop94_breakloop;
                                }

                            }
                        _loop94_breakloop: ;
                        }    // ( ... )*
                        currentAST = __currentAST92;
                        _t = __t92;
                        _t = _t.getNextSibling();
                        caseExpr_AST = currentAST.root;
                        break;
                    }
                case CASE2:
                    {
                        AST __t95 = _t;
                        AST tmp76_AST = null;
                        AST tmp76_AST_in = null;
                        tmp76_AST = astFactory.create(_t);
                        tmp76_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp76_AST);
                        ASTPair __currentAST95 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, CASE2);
                        _t = _t.getFirstChild();
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_0_.member(_t.Type)))
                                {
                                    valueExpr(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop97_breakloop;
                                }

                            }
                        _loop97_breakloop: ;
                        }    // ( ... )*
                        currentAST = __currentAST95;
                        _t = __t95;
                        _t = _t.getNextSibling();
                        caseExpr_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = caseExpr_AST;
            retTree_ = _t;
        }

        public void inExpr(AST _t) //throws RecognitionException
        {

            AST inExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST inExpr_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case IN_SET:
                    {
                        AST __t99 = _t;
                        AST tmp77_AST = null;
                        AST tmp77_AST_in = null;
                        tmp77_AST = astFactory.create(_t);
                        tmp77_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp77_AST);
                        ASTPair __currentAST99 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, IN_SET);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_0_.member(_t.Type)))
                                {
                                    valueExpr(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop101_breakloop;
                                }

                            }
                        _loop101_breakloop: ;
                        }    // ( ... )*
                        currentAST = __currentAST99;
                        _t = __t99;
                        _t = _t.getNextSibling();
                        inExpr_AST = currentAST.root;
                        break;
                    }
                case NOT_IN_SET:
                    {
                        AST __t102 = _t;
                        AST tmp78_AST = null;
                        AST tmp78_AST_in = null;
                        tmp78_AST = astFactory.create(_t);
                        tmp78_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp78_AST);
                        ASTPair __currentAST102 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, NOT_IN_SET);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_0_.member(_t.Type)))
                                {
                                    valueExpr(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop104_breakloop;
                                }

                            }
                        _loop104_breakloop: ;
                        }    // ( ... )*
                        currentAST = __currentAST102;
                        _t = __t102;
                        _t = _t.getNextSibling();
                        inExpr_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = inExpr_AST;
            retTree_ = _t;
        }

        public void betweenExpr(AST _t) //throws RecognitionException
        {

            AST betweenExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST betweenExpr_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case BETWEEN:
                    {
                        AST __t106 = _t;
                        AST tmp79_AST = null;
                        AST tmp79_AST_in = null;
                        tmp79_AST = astFactory.create(_t);
                        tmp79_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp79_AST);
                        ASTPair __currentAST106 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, BETWEEN);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST106;
                        _t = __t106;
                        _t = _t.getNextSibling();
                        betweenExpr_AST = currentAST.root;
                        break;
                    }
                case NOT_BETWEEN:
                    {
                        AST __t107 = _t;
                        AST tmp80_AST = null;
                        AST tmp80_AST_in = null;
                        tmp80_AST = astFactory.create(_t);
                        tmp80_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp80_AST);
                        ASTPair __currentAST107 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, NOT_BETWEEN);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_0_.member(_t.Type)))
                                {
                                    valueExpr(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop109_breakloop;
                                }

                            }
                        _loop109_breakloop: ;
                        }    // ( ... )*
                        currentAST = __currentAST107;
                        _t = __t107;
                        _t = _t.getNextSibling();
                        betweenExpr_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = betweenExpr_AST;
            retTree_ = _t;
        }

        public void likeExpr(AST _t) //throws RecognitionException
        {

            AST likeExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST likeExpr_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case LIKE:
                    {
                        AST __t111 = _t;
                        AST tmp81_AST = null;
                        AST tmp81_AST_in = null;
                        tmp81_AST = astFactory.create(_t);
                        tmp81_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp81_AST);
                        ASTPair __currentAST111 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, LIKE);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
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
                                case EVAL_AND_EXPR:
                                case EVAL_OR_EXPR:
                                case EVAL_EQUALS_EXPR:
                                case EVAL_NOTEQUALS_EXPR:
                                case EVENT_PROP_EXPR:
                                case CONCAT:
                                case LIB_FUNCTION:
                                case NOT_IN_SET:
                                case NOT_BETWEEN:
                                case NOT_LIKE:
                                case NOT_REGEXP:
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                case STRING_TYPE:
                                case BOOL_TYPE:
                                case NULL_TYPE:
                                case MINUS:
                                case PLUS:
                                case STAR:
                                case BAND:
                                case BOR:
                                case BXOR:
                                case LT_:
                                case GT:
                                case LE:
                                case GE:
                                case DIV:
                                case MOD:
                                    {
                                        valueExpr(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        currentAST = __currentAST111;
                        _t = __t111;
                        _t = _t.getNextSibling();
                        likeExpr_AST = currentAST.root;
                        break;
                    }
                case NOT_LIKE:
                    {
                        AST __t113 = _t;
                        AST tmp82_AST = null;
                        AST tmp82_AST_in = null;
                        tmp82_AST = astFactory.create(_t);
                        tmp82_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp82_AST);
                        ASTPair __currentAST113 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, NOT_LIKE);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
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
                                case EVAL_AND_EXPR:
                                case EVAL_OR_EXPR:
                                case EVAL_EQUALS_EXPR:
                                case EVAL_NOTEQUALS_EXPR:
                                case EVENT_PROP_EXPR:
                                case CONCAT:
                                case LIB_FUNCTION:
                                case NOT_IN_SET:
                                case NOT_BETWEEN:
                                case NOT_LIKE:
                                case NOT_REGEXP:
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                case STRING_TYPE:
                                case BOOL_TYPE:
                                case NULL_TYPE:
                                case MINUS:
                                case PLUS:
                                case STAR:
                                case BAND:
                                case BOR:
                                case BXOR:
                                case LT_:
                                case GT:
                                case LE:
                                case GE:
                                case DIV:
                                case MOD:
                                    {
                                        valueExpr(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        currentAST = __currentAST113;
                        _t = __t113;
                        _t = _t.getNextSibling();
                        likeExpr_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = likeExpr_AST;
            retTree_ = _t;
        }

        public void regExpExpr(AST _t) //throws RecognitionException
        {

            AST regExpExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST regExpExpr_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case REGEXP:
                    {
                        AST __t116 = _t;
                        AST tmp83_AST = null;
                        AST tmp83_AST_in = null;
                        tmp83_AST = astFactory.create(_t);
                        tmp83_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp83_AST);
                        ASTPair __currentAST116 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, REGEXP);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST116;
                        _t = __t116;
                        _t = _t.getNextSibling();
                        regExpExpr_AST = currentAST.root;
                        break;
                    }
                case NOT_REGEXP:
                    {
                        AST __t117 = _t;
                        AST tmp84_AST = null;
                        AST tmp84_AST_in = null;
                        tmp84_AST = astFactory.create(_t);
                        tmp84_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp84_AST);
                        ASTPair __currentAST117 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, NOT_REGEXP);
                        _t = _t.getFirstChild();
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        valueExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST117;
                        _t = __t117;
                        _t = _t.getNextSibling();
                        regExpExpr_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = regExpExpr_AST;
            retTree_ = _t;
        }

        public void startPatternExpressionRule(AST _t) //throws RecognitionException
        {

            AST startPatternExpressionRule_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST startPatternExpressionRule_AST = null;

            setIsPatternWalk(true);
            exprChoice(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            endPattern(); end();
            startPatternExpressionRule_AST = currentAST.root;
            returnAST = startPatternExpressionRule_AST;
            retTree_ = _t;
        }

        public void atomicExpr(AST _t) //throws RecognitionException
        {

            AST atomicExpr_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST atomicExpr_AST = null;
            AST ac = null;
            AST ac_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case EVENT_FILTER_EXPR:
                    {
                        eventFilterExpr(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        atomicExpr_AST = currentAST.root;
                        break;
                    }
                case OBSERVER_EXPR:
                    {
                        AST __t171 = _t;
                        ac = (ASTNULL == _t) ? null : (AST)_t;
                        AST ac_AST_in = null;
                        ac_AST = astFactory.create(ac);
                        astFactory.addASTChild(ref currentAST, ac_AST);
                        ASTPair __currentAST171 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, OBSERVER_EXPR);
                        _t = _t.getFirstChild();
                        AST tmp85_AST = null;
                        AST tmp85_AST_in = null;
                        tmp85_AST = astFactory.create(_t);
                        tmp85_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp85_AST);
                        match(_t, IDENT);
                        _t = _t.getNextSibling();
                        AST tmp86_AST = null;
                        AST tmp86_AST_in = null;
                        tmp86_AST = astFactory.create(_t);
                        tmp86_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp86_AST);
                        match(_t, IDENT);
                        _t = _t.getNextSibling();
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_1_.member(_t.Type)))
                                {
                                    parameter(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop173_breakloop;
                                }

                            }
                        _loop173_breakloop: ;
                        }    // ( ... )*
                        leaveNode(ac_AST);
                        currentAST = __currentAST171;
                        _t = __t171;
                        _t = _t.getNextSibling();
                        atomicExpr_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = atomicExpr_AST;
            retTree_ = _t;
        }

        public void patternOp(AST _t) //throws RecognitionException
        {

            AST patternOp_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST patternOp_AST = null;
            AST f = null;
            AST f_AST = null;
            AST o = null;
            AST o_AST = null;
            AST a = null;
            AST a_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case FOLLOWED_BY_EXPR:
                    {
                        AST __t161 = _t;
                        f = (ASTNULL == _t) ? null : (AST)_t;
                        AST f_AST_in = null;
                        f_AST = astFactory.create(f);
                        astFactory.addASTChild(ref currentAST, f_AST);
                        ASTPair __currentAST161 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, FOLLOWED_BY_EXPR);
                        _t = _t.getFirstChild();
                        exprChoice(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        exprChoice(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_2_.member(_t.Type)))
                                {
                                    exprChoice(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop163_breakloop;
                                }

                            }
                        _loop163_breakloop: ;
                        }    // ( ... )*
                        leaveNode(f_AST);
                        currentAST = __currentAST161;
                        _t = __t161;
                        _t = _t.getNextSibling();
                        patternOp_AST = currentAST.root;
                        break;
                    }
                case OR_EXPR:
                    {
                        AST __t164 = _t;
                        o = (ASTNULL == _t) ? null : (AST)_t;
                        AST o_AST_in = null;
                        o_AST = astFactory.create(o);
                        astFactory.addASTChild(ref currentAST, o_AST);
                        ASTPair __currentAST164 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, OR_EXPR);
                        _t = _t.getFirstChild();
                        exprChoice(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        exprChoice(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_2_.member(_t.Type)))
                                {
                                    exprChoice(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop166_breakloop;
                                }

                            }
                        _loop166_breakloop: ;
                        }    // ( ... )*
                        leaveNode(o_AST);
                        currentAST = __currentAST164;
                        _t = __t164;
                        _t = _t.getNextSibling();
                        patternOp_AST = currentAST.root;
                        break;
                    }
                case AND_EXPR:
                    {
                        AST __t167 = _t;
                        a = (ASTNULL == _t) ? null : (AST)_t;
                        AST a_AST_in = null;
                        a_AST = astFactory.create(a);
                        astFactory.addASTChild(ref currentAST, a_AST);
                        ASTPair __currentAST167 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, AND_EXPR);
                        _t = _t.getFirstChild();
                        exprChoice(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        exprChoice(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {    // ( ... )*
                            for (; ; )
                            {
                                if (_t == null)
                                    _t = ASTNULL;
                                if ((tokenSet_2_.member(_t.Type)))
                                {
                                    exprChoice(_t);
                                    _t = retTree_;
                                    astFactory.addASTChild(ref currentAST, returnAST);
                                }
                                else
                                {
                                    goto _loop169_breakloop;
                                }

                            }
                        _loop169_breakloop: ;
                        }    // ( ... )*
                        leaveNode(a_AST);
                        currentAST = __currentAST167;
                        _t = __t167;
                        _t = _t.getNextSibling();
                        patternOp_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = patternOp_AST;
            retTree_ = _t;
        }

        public void time_period(AST _t) //throws RecognitionException
        {

            AST time_period_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST time_period_AST = null;

            AST __t219 = _t;
            AST tmp87_AST = null;
            AST tmp87_AST_in = null;
            tmp87_AST = astFactory.create(_t);
            tmp87_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp87_AST);
            ASTPair __currentAST219 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, TIME_PERIOD);
            _t = _t.getFirstChild();
            timePeriodDef(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            currentAST = __currentAST219;
            _t = __t219;
            _t = _t.getNextSibling();
            time_period_AST = currentAST.root;
            returnAST = time_period_AST;
            retTree_ = _t;
        }

        public void filterParam(AST _t) //throws RecognitionException
        {

            AST filterParam_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST filterParam_AST = null;

            AST __t180 = _t;
            AST tmp88_AST = null;
            AST tmp88_AST_in = null;
            tmp88_AST = astFactory.create(_t);
            tmp88_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp88_AST);
            ASTPair __currentAST180 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, EVENT_FILTER_PARAM);
            _t = _t.getFirstChild();
            eventPropertyExpr(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            filterParamComparator(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            currentAST = __currentAST180;
            _t = __t180;
            _t = _t.getNextSibling();
            filterParam_AST = currentAST.root;
            returnAST = filterParam_AST;
            retTree_ = _t;
        }

        public void filterParamComparator(AST _t) //throws RecognitionException
        {

            AST filterParamComparator_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST filterParamComparator_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case EQUALS:
                    {
                        AST __t182 = _t;
                        AST tmp89_AST = null;
                        AST tmp89_AST_in = null;
                        tmp89_AST = astFactory.create(_t);
                        tmp89_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp89_AST);
                        ASTPair __currentAST182 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, EQUALS);
                        _t = _t.getFirstChild();
                        filterAtom(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST182;
                        _t = __t182;
                        _t = _t.getNextSibling();
                        filterParamComparator_AST = currentAST.root;
                        break;
                    }
                case NOT_EQUAL:
                    {
                        AST __t183 = _t;
                        AST tmp90_AST = null;
                        AST tmp90_AST_in = null;
                        tmp90_AST = astFactory.create(_t);
                        tmp90_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp90_AST);
                        ASTPair __currentAST183 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, NOT_EQUAL);
                        _t = _t.getFirstChild();
                        filterAtom(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST183;
                        _t = __t183;
                        _t = _t.getNextSibling();
                        filterParamComparator_AST = currentAST.root;
                        break;
                    }
                case LT_:
                    {
                        AST __t184 = _t;
                        AST tmp91_AST = null;
                        AST tmp91_AST_in = null;
                        tmp91_AST = astFactory.create(_t);
                        tmp91_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp91_AST);
                        ASTPair __currentAST184 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, LT_);
                        _t = _t.getFirstChild();
                        filterAtom(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST184;
                        _t = __t184;
                        _t = _t.getNextSibling();
                        filterParamComparator_AST = currentAST.root;
                        break;
                    }
                case LE:
                    {
                        AST __t185 = _t;
                        AST tmp92_AST = null;
                        AST tmp92_AST_in = null;
                        tmp92_AST = astFactory.create(_t);
                        tmp92_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp92_AST);
                        ASTPair __currentAST185 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, LE);
                        _t = _t.getFirstChild();
                        filterAtom(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST185;
                        _t = __t185;
                        _t = _t.getNextSibling();
                        filterParamComparator_AST = currentAST.root;
                        break;
                    }
                case GT:
                    {
                        AST __t186 = _t;
                        AST tmp93_AST = null;
                        AST tmp93_AST_in = null;
                        tmp93_AST = astFactory.create(_t);
                        tmp93_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp93_AST);
                        ASTPair __currentAST186 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, GT);
                        _t = _t.getFirstChild();
                        filterAtom(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST186;
                        _t = __t186;
                        _t = _t.getNextSibling();
                        filterParamComparator_AST = currentAST.root;
                        break;
                    }
                case GE:
                    {
                        AST __t187 = _t;
                        AST tmp94_AST = null;
                        AST tmp94_AST_in = null;
                        tmp94_AST = astFactory.create(_t);
                        tmp94_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp94_AST);
                        ASTPair __currentAST187 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, GE);
                        _t = _t.getFirstChild();
                        filterAtom(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        currentAST = __currentAST187;
                        _t = __t187;
                        _t = _t.getNextSibling();
                        filterParamComparator_AST = currentAST.root;
                        break;
                    }
                case IN_SET:
                    {
                        AST __t188 = _t;
                        AST tmp95_AST = null;
                        AST tmp95_AST_in = null;
                        tmp95_AST = astFactory.create(_t);
                        tmp95_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp95_AST);
                        ASTPair __currentAST188 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, IN_SET);
                        _t = _t.getFirstChild();
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case LPAREN:
                                    {
                                        AST tmp96_AST = null;
                                        AST tmp96_AST_in = null;
                                        tmp96_AST = astFactory.create(_t);
                                        tmp96_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp96_AST);
                                        match(_t, LPAREN);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case LBRACK:
                                    {
                                        AST tmp97_AST = null;
                                        AST tmp97_AST_in = null;
                                        tmp97_AST = astFactory.create(_t);
                                        tmp97_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp97_AST);
                                        match(_t, LBRACK);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                case STRING_TYPE:
                                case BOOL_TYPE:
                                case NULL_TYPE:
                                    {
                                        constant(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case EVENT_FILTER_IDENT:
                                    {
                                        filterIdentifier(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case INT_TYPE:
                                case LONG_TYPE:
                                case FLOAT_TYPE:
                                case DOUBLE_TYPE:
                                case STRING_TYPE:
                                case BOOL_TYPE:
                                case NULL_TYPE:
                                    {
                                        constant(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case EVENT_FILTER_IDENT:
                                    {
                                        filterIdentifier(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case RPAREN:
                                    {
                                        AST tmp98_AST = null;
                                        AST tmp98_AST_in = null;
                                        tmp98_AST = astFactory.create(_t);
                                        tmp98_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp98_AST);
                                        match(_t, RPAREN);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case RBRACK:
                                    {
                                        AST tmp99_AST = null;
                                        AST tmp99_AST_in = null;
                                        tmp99_AST = astFactory.create(_t);
                                        tmp99_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp99_AST);
                                        match(_t, RBRACK);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        currentAST = __currentAST188;
                        _t = __t188;
                        _t = _t.getNextSibling();
                        filterParamComparator_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = filterParamComparator_AST;
            retTree_ = _t;
        }

        public void filterAtom(AST _t) //throws RecognitionException
        {

            AST filterAtom_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST filterAtom_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case INT_TYPE:
                case LONG_TYPE:
                case FLOAT_TYPE:
                case DOUBLE_TYPE:
                case STRING_TYPE:
                case BOOL_TYPE:
                case NULL_TYPE:
                    {
                        constant(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        filterAtom_AST = currentAST.root;
                        break;
                    }
                case EVENT_FILTER_IDENT:
                    {
                        filterIdentifier(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        filterAtom_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = filterAtom_AST;
            retTree_ = _t;
        }

        public void filterIdentifier(AST _t) //throws RecognitionException
        {

            AST filterIdentifier_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST filterIdentifier_AST = null;

            AST __t195 = _t;
            AST tmp100_AST = null;
            AST tmp100_AST_in = null;
            tmp100_AST = astFactory.create(_t);
            tmp100_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp100_AST);
            ASTPair __currentAST195 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, EVENT_FILTER_IDENT);
            _t = _t.getFirstChild();
            AST tmp101_AST = null;
            AST tmp101_AST_in = null;
            tmp101_AST = astFactory.create(_t);
            tmp101_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp101_AST);
            match(_t, IDENT);
            _t = _t.getNextSibling();
            eventPropertyExpr(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            currentAST = __currentAST195;
            _t = __t195;
            _t = _t.getNextSibling();
            filterIdentifier_AST = currentAST.root;
            returnAST = filterIdentifier_AST;
            retTree_ = _t;
        }

        public void eventPropertyAtomic(AST _t) //throws RecognitionException
        {

            AST eventPropertyAtomic_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST eventPropertyAtomic_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case EVENT_PROP_SIMPLE:
                    {
                        AST __t201 = _t;
                        AST tmp102_AST = null;
                        AST tmp102_AST_in = null;
                        tmp102_AST = astFactory.create(_t);
                        tmp102_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp102_AST);
                        ASTPair __currentAST201 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, EVENT_PROP_SIMPLE);
                        _t = _t.getFirstChild();
                        AST tmp103_AST = null;
                        AST tmp103_AST_in = null;
                        tmp103_AST = astFactory.create(_t);
                        tmp103_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp103_AST);
                        match(_t, IDENT);
                        _t = _t.getNextSibling();
                        currentAST = __currentAST201;
                        _t = __t201;
                        _t = _t.getNextSibling();
                        eventPropertyAtomic_AST = currentAST.root;
                        break;
                    }
                case EVENT_PROP_INDEXED:
                    {
                        AST __t202 = _t;
                        AST tmp104_AST = null;
                        AST tmp104_AST_in = null;
                        tmp104_AST = astFactory.create(_t);
                        tmp104_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp104_AST);
                        ASTPair __currentAST202 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, EVENT_PROP_INDEXED);
                        _t = _t.getFirstChild();
                        AST tmp105_AST = null;
                        AST tmp105_AST_in = null;
                        tmp105_AST = astFactory.create(_t);
                        tmp105_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp105_AST);
                        match(_t, IDENT);
                        _t = _t.getNextSibling();
                        AST tmp106_AST = null;
                        AST tmp106_AST_in = null;
                        tmp106_AST = astFactory.create(_t);
                        tmp106_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp106_AST);
                        match(_t, NUM_INT);
                        _t = _t.getNextSibling();
                        currentAST = __currentAST202;
                        _t = __t202;
                        _t = _t.getNextSibling();
                        eventPropertyAtomic_AST = currentAST.root;
                        break;
                    }
                case EVENT_PROP_MAPPED:
                    {
                        AST __t203 = _t;
                        AST tmp107_AST = null;
                        AST tmp107_AST_in = null;
                        tmp107_AST = astFactory.create(_t);
                        tmp107_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp107_AST);
                        ASTPair __currentAST203 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, EVENT_PROP_MAPPED);
                        _t = _t.getFirstChild();
                        AST tmp108_AST = null;
                        AST tmp108_AST_in = null;
                        tmp108_AST = astFactory.create(_t);
                        tmp108_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp108_AST);
                        match(_t, IDENT);
                        _t = _t.getNextSibling();
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case STRING_LITERAL:
                                    {
                                        AST tmp109_AST = null;
                                        AST tmp109_AST_in = null;
                                        tmp109_AST = astFactory.create(_t);
                                        tmp109_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp109_AST);
                                        match(_t, STRING_LITERAL);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                case QUOTED_STRING_LITERAL:
                                    {
                                        AST tmp110_AST = null;
                                        AST tmp110_AST_in = null;
                                        tmp110_AST = astFactory.create(_t);
                                        tmp110_AST_in = _t;
                                        astFactory.addASTChild(ref currentAST, tmp110_AST);
                                        match(_t, QUOTED_STRING_LITERAL);
                                        _t = _t.getNextSibling();
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        currentAST = __currentAST203;
                        _t = __t203;
                        _t = _t.getNextSibling();
                        eventPropertyAtomic_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = eventPropertyAtomic_AST;
            retTree_ = _t;
        }

        public void singleParameter(AST _t) //throws RecognitionException
        {

            AST singleParameter_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST singleParameter_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case STAR:
                    {
                        AST tmp111_AST = null;
                        AST tmp111_AST_in = null;
                        tmp111_AST = astFactory.create(_t);
                        tmp111_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp111_AST);
                        match(_t, STAR);
                        _t = _t.getNextSibling();
                        singleParameter_AST = currentAST.root;
                        break;
                    }
                case INT_TYPE:
                case LONG_TYPE:
                case FLOAT_TYPE:
                case DOUBLE_TYPE:
                case STRING_TYPE:
                case BOOL_TYPE:
                case NULL_TYPE:
                    {
                        constant(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        singleParameter_AST = currentAST.root;
                        break;
                    }
                case NUMERIC_PARAM_RANGE:
                    {
                        AST __t213 = _t;
                        AST tmp112_AST = null;
                        AST tmp112_AST_in = null;
                        tmp112_AST = astFactory.create(_t);
                        tmp112_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp112_AST);
                        ASTPair __currentAST213 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, NUMERIC_PARAM_RANGE);
                        _t = _t.getFirstChild();
                        AST tmp113_AST = null;
                        AST tmp113_AST_in = null;
                        tmp113_AST = astFactory.create(_t);
                        tmp113_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp113_AST);
                        match(_t, NUM_INT);
                        _t = _t.getNextSibling();
                        AST tmp114_AST = null;
                        AST tmp114_AST_in = null;
                        tmp114_AST = astFactory.create(_t);
                        tmp114_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp114_AST);
                        match(_t, NUM_INT);
                        _t = _t.getNextSibling();
                        currentAST = __currentAST213;
                        _t = __t213;
                        _t = _t.getNextSibling();
                        singleParameter_AST = currentAST.root;
                        break;
                    }
                case NUMERIC_PARAM_FREQUENCY:
                    {
                        AST __t214 = _t;
                        AST tmp115_AST = null;
                        AST tmp115_AST_in = null;
                        tmp115_AST = astFactory.create(_t);
                        tmp115_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp115_AST);
                        ASTPair __currentAST214 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, NUMERIC_PARAM_FREQUENCY);
                        _t = _t.getFirstChild();
                        AST tmp116_AST = null;
                        AST tmp116_AST_in = null;
                        tmp116_AST = astFactory.create(_t);
                        tmp116_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp116_AST);
                        match(_t, NUM_INT);
                        _t = _t.getNextSibling();
                        currentAST = __currentAST214;
                        _t = __t214;
                        _t = _t.getNextSibling();
                        singleParameter_AST = currentAST.root;
                        break;
                    }
                case TIME_PERIOD:
                    {
                        time_period(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        singleParameter_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = singleParameter_AST;
            retTree_ = _t;
        }

        public void numericParameterList(AST _t) //throws RecognitionException
        {

            AST numericParameterList_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST numericParameterList_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case NUM_INT:
                    {
                        AST tmp117_AST = null;
                        AST tmp117_AST_in = null;
                        tmp117_AST = astFactory.create(_t);
                        tmp117_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp117_AST);
                        match(_t, NUM_INT);
                        _t = _t.getNextSibling();
                        numericParameterList_AST = currentAST.root;
                        break;
                    }
                case NUMERIC_PARAM_RANGE:
                    {
                        AST __t216 = _t;
                        AST tmp118_AST = null;
                        AST tmp118_AST_in = null;
                        tmp118_AST = astFactory.create(_t);
                        tmp118_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp118_AST);
                        ASTPair __currentAST216 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, NUMERIC_PARAM_RANGE);
                        _t = _t.getFirstChild();
                        AST tmp119_AST = null;
                        AST tmp119_AST_in = null;
                        tmp119_AST = astFactory.create(_t);
                        tmp119_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp119_AST);
                        match(_t, NUM_INT);
                        _t = _t.getNextSibling();
                        AST tmp120_AST = null;
                        AST tmp120_AST_in = null;
                        tmp120_AST = astFactory.create(_t);
                        tmp120_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp120_AST);
                        match(_t, NUM_INT);
                        _t = _t.getNextSibling();
                        currentAST = __currentAST216;
                        _t = __t216;
                        _t = _t.getNextSibling();
                        numericParameterList_AST = currentAST.root;
                        break;
                    }
                case NUMERIC_PARAM_FREQUENCE:
                    {
                        AST __t217 = _t;
                        AST tmp121_AST = null;
                        AST tmp121_AST_in = null;
                        tmp121_AST = astFactory.create(_t);
                        tmp121_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp121_AST);
                        ASTPair __currentAST217 = currentAST.copy();
                        currentAST.root = currentAST.child;
                        currentAST.child = null;
                        match(_t, NUMERIC_PARAM_FREQUENCE);
                        _t = _t.getFirstChild();
                        AST tmp122_AST = null;
                        AST tmp122_AST_in = null;
                        tmp122_AST = astFactory.create(_t);
                        tmp122_AST_in = _t;
                        astFactory.addASTChild(ref currentAST, tmp122_AST);
                        match(_t, NUM_INT);
                        _t = _t.getNextSibling();
                        currentAST = __currentAST217;
                        _t = __t217;
                        _t = _t.getNextSibling();
                        numericParameterList_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = numericParameterList_AST;
            retTree_ = _t;
        }

        public void timePeriodDef(AST _t) //throws RecognitionException
        {

            AST timePeriodDef_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST timePeriodDef_AST = null;

            if (null == _t)
                _t = ASTNULL;
            switch (_t.Type)
            {
                case DAY_PART:
                    {
                        dayPart(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case HOUR_PART:
                                    {
                                        hourPart(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                case MINUTE_PART:
                                case SECOND_PART:
                                case MILLISECOND_PART:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case MINUTE_PART:
                                    {
                                        minutePart(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                case SECOND_PART:
                                case MILLISECOND_PART:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case SECOND_PART:
                                    {
                                        secondPart(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                case MILLISECOND_PART:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case MILLISECOND_PART:
                                    {
                                        millisecondPart(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        timePeriodDef_AST = currentAST.root;
                        break;
                    }
                case HOUR_PART:
                    {
                        hourPart(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case MINUTE_PART:
                                    {
                                        minutePart(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                case SECOND_PART:
                                case MILLISECOND_PART:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case SECOND_PART:
                                    {
                                        secondPart(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                case MILLISECOND_PART:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case MILLISECOND_PART:
                                    {
                                        millisecondPart(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        timePeriodDef_AST = currentAST.root;
                        break;
                    }
                case MINUTE_PART:
                    {
                        minutePart(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case SECOND_PART:
                                    {
                                        secondPart(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                case MILLISECOND_PART:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case MILLISECOND_PART:
                                    {
                                        millisecondPart(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        timePeriodDef_AST = currentAST.root;
                        break;
                    }
                case SECOND_PART:
                    {
                        secondPart(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        {
                            if (null == _t)
                                _t = ASTNULL;
                            switch (_t.Type)
                            {
                                case MILLISECOND_PART:
                                    {
                                        millisecondPart(_t);
                                        _t = retTree_;
                                        astFactory.addASTChild(ref currentAST, returnAST);
                                        break;
                                    }
                                case 3:
                                    {
                                        break;
                                    }
                                default:
                                    {
                                        throw new NoViableAltException(_t);
                                    }
                            }
                        }
                        timePeriodDef_AST = currentAST.root;
                        break;
                    }
                case MILLISECOND_PART:
                    {
                        millisecondPart(_t);
                        _t = retTree_;
                        astFactory.addASTChild(ref currentAST, returnAST);
                        timePeriodDef_AST = currentAST.root;
                        break;
                    }
                default:
                    {
                        throw new NoViableAltException(_t);
                    }
            }
            returnAST = timePeriodDef_AST;
            retTree_ = _t;
        }

        public void dayPart(AST _t) //throws RecognitionException
        {

            AST dayPart_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST dayPart_AST = null;

            AST __t232 = _t;
            AST tmp123_AST = null;
            AST tmp123_AST_in = null;
            tmp123_AST = astFactory.create(_t);
            tmp123_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp123_AST);
            ASTPair __currentAST232 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, DAY_PART);
            _t = _t.getFirstChild();
            number(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            currentAST = __currentAST232;
            _t = __t232;
            _t = _t.getNextSibling();
            dayPart_AST = currentAST.root;
            returnAST = dayPart_AST;
            retTree_ = _t;
        }

        public void hourPart(AST _t) //throws RecognitionException
        {

            AST hourPart_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST hourPart_AST = null;

            AST __t234 = _t;
            AST tmp124_AST = null;
            AST tmp124_AST_in = null;
            tmp124_AST = astFactory.create(_t);
            tmp124_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp124_AST);
            ASTPair __currentAST234 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, HOUR_PART);
            _t = _t.getFirstChild();
            number(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            currentAST = __currentAST234;
            _t = __t234;
            _t = _t.getNextSibling();
            hourPart_AST = currentAST.root;
            returnAST = hourPart_AST;
            retTree_ = _t;
        }

        public void minutePart(AST _t) //throws RecognitionException
        {

            AST minutePart_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST minutePart_AST = null;

            AST __t236 = _t;
            AST tmp125_AST = null;
            AST tmp125_AST_in = null;
            tmp125_AST = astFactory.create(_t);
            tmp125_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp125_AST);
            ASTPair __currentAST236 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, MINUTE_PART);
            _t = _t.getFirstChild();
            number(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            currentAST = __currentAST236;
            _t = __t236;
            _t = _t.getNextSibling();
            minutePart_AST = currentAST.root;
            returnAST = minutePart_AST;
            retTree_ = _t;
        }

        public void secondPart(AST _t) //throws RecognitionException
        {

            AST secondPart_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST secondPart_AST = null;

            AST __t238 = _t;
            AST tmp126_AST = null;
            AST tmp126_AST_in = null;
            tmp126_AST = astFactory.create(_t);
            tmp126_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp126_AST);
            ASTPair __currentAST238 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, SECOND_PART);
            _t = _t.getFirstChild();
            number(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            currentAST = __currentAST238;
            _t = __t238;
            _t = _t.getNextSibling();
            secondPart_AST = currentAST.root;
            returnAST = secondPart_AST;
            retTree_ = _t;
        }

        public void millisecondPart(AST _t) //throws RecognitionException
        {

            AST millisecondPart_AST_in = (AST)_t;
            returnAST = null;
            ASTPair currentAST = new ASTPair();
            AST millisecondPart_AST = null;

            AST __t240 = _t;
            AST tmp127_AST = null;
            AST tmp127_AST_in = null;
            tmp127_AST = astFactory.create(_t);
            tmp127_AST_in = _t;
            astFactory.addASTChild(ref currentAST, tmp127_AST);
            ASTPair __currentAST240 = currentAST.copy();
            currentAST.root = currentAST.child;
            currentAST.child = null;
            match(_t, MILLISECOND_PART);
            _t = _t.getFirstChild();
            number(_t);
            _t = retTree_;
            astFactory.addASTChild(ref currentAST, returnAST);
            currentAST = __currentAST240;
            _t = __t240;
            _t = _t.getNextSibling();
            millisecondPart_AST = currentAST.root;
            returnAST = millisecondPart_AST;
            retTree_ = _t;
        }

        static public void initializeASTFactory(ASTFactory factory)
        {
            factory.setMaxNodeType(200);
        }

        public static readonly string[] tokenNames_ = new string[] {
		    @"""<0>""",
		    @"""EOF""",
		    @"""<2>""",
		    @"""NULL_TREE_LOOKAHEAD""",
		    @"""in""",
		    @"""between""",
		    @"""like""",
		    @"""regexp""",
		    @"""escape""",
		    @"""or""",
		    @"""and""",
		    @"""not""",
		    @"""every""",
		    @"""where""",
		    @"""as""",
		    @"""sum""",
		    @"""avg""",
		    @"""max""",
		    @"""min""",
		    @"""coalesce""",
		    @"""median""",
		    @"""stddev""",
		    @"""avedev""",
		    @"""count""",
		    @"""select""",
		    @"""case""",
		    @"""CASE2""",
		    @"""else""",
		    @"""when""",
		    @"""then""",
		    @"""end""",
		    @"""from""",
		    @"""outer""",
		    @"""join""",
		    @"""left""",
		    @"""right""",
		    @"""full""",
		    @"""on""",
		    @"""is""",
		    @"""by""",
		    @"""group""",
		    @"""having""",
		    @"""distinct""",
		    @"""all""",
		    @"""output""",
		    @"""events""",
		    @"""seconds""",
		    @"""minutes""",
		    @"""first""",
		    @"""last""",
		    @"""insert""",
		    @"""into""",
		    @"""order""",
		    @"""asc""",
		    @"""desc""",
		    @"""rstream""",
		    @"""istream""",
		    @"""pattern""",
		    @"""sql""",
		    @"""NUMERIC_PARAM_RANGE""",
		    @"""NUMERIC_PARAM_LIST""",
		    @"""NUMERIC_PARAM_FREQUENCY""",
		    @"""FOLLOWED_BY_EXPR""",
		    @"""ARRAY_PARAM_LIST""",
		    @"""EVENT_FILTER_EXPR""",
		    @"""EVENT_FILTER_NAME_TAG""",
		    @"""EVENT_FILTER_IDENT""",
		    @"""EVENT_FILTER_PARAM""",
		    @"""CLASS_IDENT""",
		    @"""GUARD_EXPR""",
		    @"""OBSERVER_EXPR""",
		    @"""VIEW_EXPR""",
		    @"""PATTERN_INCL_EXPR""",
		    @"""DATABASE_JOIN_EXPR""",
		    @"""WHERE_EXPR""",
		    @"""HAVING_EXPR""",
		    @"""EVAL_BITWISE_EXPR""",
		    @"""EVAL_AND_EXPR""",
		    @"""EVAL_OR_EXPR""",
		    @"""EVAL_EQUALS_EXPR""",
		    @"""EVAL_NOTEQUALS_EXPR""",
		    @"""EVAL_IDENT""",
		    @"""SELECTION_EXPR""",
		    @"""SELECTION_ELEMENT_EXPR""",
		    @"""STREAM_EXPR""",
		    @"""OUTERJOIN_EXPR""",
		    @"""LEFT_OUTERJOIN_EXPR""",
		    @"""RIGHT_OUTERJOIN_EXPR""",
		    @"""FULL_OUTERJOIN_EXPR""",
		    @"""GROUP_BY_EXPR""",
		    @"""ORDER_BY_EXPR""",
		    @"""ORDER_ELEMENT_EXPR""",
		    @"""EVENT_PROP_EXPR""",
		    @"""EVENT_PROP_SIMPLE""",
		    @"""EVENT_PROP_MAPPED""",
		    @"""EVENT_PROP_INDEXED""",
		    @"""EVENT_LIMIT_EXPR""",
		    @"""SEC_LIMIT_EXPR""",
		    @"""MIN_LIMIT_EXPR""",
		    @"""INSERTINTO_EXPR""",
		    @"""INSERTINTO_EXPRCOL""",
		    @"""CONCAT""",
		    @"""LIB_FUNCTION""",
		    @"""UNARY_MINUS""",
		    @"""TIME_PERIOD""",
		    @"""DAY_PART""",
		    @"""HOUR_PART""",
		    @"""MINUTE_PART""",
		    @"""SECOND_PART""",
		    @"""MILLISECOND_PART""",
		    @"""NOT_IN_SET""",
		    @"""NOT_BETWEEN""",
		    @"""NOT_LIKE""",
		    @"""NOT_REGEXP""",
		    @"""DBSELECT_EXPR""",
		    @"""DBFROM_CLAUSE""",
		    @"""DBWHERE_CLAUSE""",
		    @"""INT_TYPE""",
		    @"""LONG_TYPE""",
		    @"""FLOAT_TYPE""",
		    @"""DOUBLE_TYPE""",
		    @"""STRING_TYPE""",
		    @"""BOOL_TYPE""",
		    @"""NULL_TYPE""",
		    @"""NUM_INT""",
		    @"""NUM_LONG""",
		    @"""NUM_FLOAT""",
		    @"""NUM_DOUBLE""",
		    @"""MINUS""",
		    @"""PLUS""",
		    @"""true""",
		    @"""false""",
		    @"""null""",
		    @"""STRING_LITERAL""",
		    @"""QUOTED_STRING_LITERAL""",
		    @"""IDENT""",
		    @"""LPAREN""",
		    @"""COMMA""",
		    @"""RPAREN""",
		    @"""EQUALS""",
		    @"""STAR""",
		    @"""DOT""",
		    @"""LBRACK""",
		    @"""RBRACK""",
		    @"""COLON""",
		    @"""BAND""",
		    @"""BOR""",
		    @"""BXOR""",
		    @"""SQL_NE""",
		    @"""NOT_EQUAL""",
		    @"""LT_""",
		    @"""GT""",
		    @"""LE""",
		    @"""GE""",
		    @"""LOR""",
		    @"""DIV""",
		    @"""MOD""",
		    @"""FOLLOWED_BY""",
		    @"""LCURLY""",
		    @"""RCURLY""",
		    @"""days""",
		    @"""day""",
		    @"""hours""",
		    @"""hour""",
		    @"""minute""",
		    @"""second""",
		    @"""sec""",
		    @"""milliseconds""",
		    @"""millisecond""",
		    @"""msec""",
		    @"""QUESTION""",
		    @"""EQUAL""",
		    @"""LNOT""",
		    @"""BNOT""",
		    @"""DIV_ASSIGN""",
		    @"""PLUS_ASSIGN""",
		    @"""INC""",
		    @"""MINUS_ASSIGN""",
		    @"""DEC""",
		    @"""STAR_ASSIGN""",
		    @"""MOD_ASSIGN""",
		    @"""SR""",
		    @"""SR_ASSIGN""",
		    @"""BSR""",
		    @"""BSR_ASSIGN""",
		    @"""SL""",
		    @"""SL_ASSIGN""",
		    @"""BXOR_ASSIGN""",
		    @"""BOR_ASSIGN""",
		    @"""BAND_ASSIGN""",
		    @"""LAND""",
		    @"""SEMI""",
		    @"""WS""",
		    @"""SL_COMMENT""",
		    @"""ML_COMMENT""",
		    @"""ESC""",
		    @"""HEX_DIGIT""",
		    @"""EXPONENT""",
		    @"""FLOAT_SUFFIX""",
		    @"""BOGUS""",
		    @"""NUMERIC_PARAM_FREQUENCE"""
	    };

        private static long[] mk_tokenSet_0_()
        {
            long[] data = { 117016816L, 1144970249100189696L, 466489347L, 0L, 0L, 0L };
            return data;
        }
        public static readonly BitSet tokenSet_0_ = new BitSet(mk_tokenSet_0_());
        private static long[] mk_tokenSet_1_()
        {
            long[] data = { -5188146770730811392L, 1143915404863733760L, 4096L, 0L, 0L, 0L };
            return data;
        }
        public static readonly BitSet tokenSet_1_ = new BitSet(mk_tokenSet_1_());
        private static long[] mk_tokenSet_2_()
        {
            long[] data = { 4611686018427395584L, 97L, 0L, 0L };
            return data;
        }
        public static readonly BitSet tokenSet_2_ = new BitSet(mk_tokenSet_2_());
    }
}