/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.antlr.ASTUtil;
import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.client.EPStatementSyntaxException;
import com.espertech.esper.epl.generated.EsperEPL2GrammarParser;
import com.espertech.esper.epl.parse.*;
import com.espertech.esper.epl.spec.PatternStreamSpecRaw;
import com.espertech.esper.epl.spec.SelectClauseElementWildcard;
import com.espertech.esper.epl.spec.SelectClauseStreamSelectorEnum;
import com.espertech.esper.epl.spec.StatementSpecRaw;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EPAdministratorHelper
{
    private static ParseRuleSelector patternParseRule;
    private static ParseRuleSelector eplParseRule;
    private static WalkRuleSelector patternWalkRule;
    private static WalkRuleSelector eplWalkRule;

    static
    {
        patternParseRule = new ParseRuleSelector()
        {
            public Tree invokeParseRule(EsperEPL2GrammarParser parser) throws RecognitionException
            {
                EsperEPL2GrammarParser.startPatternExpressionRule_return r = parser.startPatternExpressionRule();
                return (Tree) r.getTree();
            }
        };
        patternWalkRule = new WalkRuleSelector()
        {
            public void invokeWalkRule(EPLTreeWalker walker) throws RecognitionException
            {
                walker.startPatternExpressionRule();
            }
        };

        eplParseRule = new ParseRuleSelector()
        {
            public Tree invokeParseRule(EsperEPL2GrammarParser parser) throws RecognitionException
            {
                EsperEPL2GrammarParser.startEPLExpressionRule_return r = parser.startEPLExpressionRule();
                return (Tree) r.getTree();
            }
        };
        eplWalkRule = new WalkRuleSelector()
        {
            public void invokeWalkRule(EPLTreeWalker walker) throws RecognitionException
            {
                walker.startEPLExpressionRule();
            }
        };
    }

    /**
     * Compile the EPL.
     * @param eplStatement expression to compile
     * @param statementName is the name of the statement
     * @param services is the context
     * @param defaultStreamSelector - the configuration for which insert or remove streams (or both) to produce
     * @param eplStatementForErrorMsg - use this text for the error message
     * @param addPleaseCheck - indicator to add a "please check" wording for stack paraphrases
     * @return statement specification
     */
    public static StatementSpecRaw compileEPL(String eplStatement, String eplStatementForErrorMsg, boolean addPleaseCheck, String statementName, EPServicesContext services, SelectClauseStreamSelectorEnum defaultStreamSelector)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".createEPLStmt statementName=" + statementName + " eplStatement=" + eplStatement);
        }

        ParseResult parseResult = ParseHelper.parse(eplStatement, eplStatementForErrorMsg, addPleaseCheck, eplParseRule);
        Tree ast = parseResult.getTree();
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);

        EPLTreeWalker walker = new EPLTreeWalker(nodes, services.getEngineImportService(), services.getVariableService(), services.getSchedulingService(), defaultStreamSelector, services.getEngineURI(), services.getConfigSnapshot());

        try
        {
            ParseHelper.walk(ast, walker, eplWalkRule, eplStatement, eplStatementForErrorMsg);
        }
        catch (ASTWalkException ex)
        {
            log.error(".createEPL Error validating expression", ex);
            throw new EPStatementException(ex.getMessage(), eplStatementForErrorMsg);
        }
        catch (EPStatementSyntaxException ex)
        {
            throw ex;
        }
        catch (RuntimeException ex)
        {
            String message = "Error in expression";
            log.debug(message, ex);
            throw new EPStatementException(getNullableErrortext(message, ex.getMessage()), eplStatementForErrorMsg);
        }

        if (log.isDebugEnabled())
        {
            ASTUtil.dumpAST(ast);
        }

        StatementSpecRaw raw = walker.getStatementSpec();
        raw.setExpressionNoAnnotations(parseResult.getExpressionWithoutAnnotations());
        return raw;
    }

    public static StatementSpecRaw compilePattern(String expression, String expressionForErrorMessage, boolean addPleaseCheck, EPServicesContext services, SelectClauseStreamSelectorEnum defaultStreamSelector)
    {
        // Parse and walk
        ParseResult parseResult = ParseHelper.parse(expression, expressionForErrorMessage, addPleaseCheck, patternParseRule);
        Tree ast = parseResult.getTree();
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
        EPLTreeWalker walker = new EPLTreeWalker(nodes, services.getEngineImportService(), services.getVariableService(), services.getSchedulingService(), defaultStreamSelector, services.getEngineURI(), services.getConfigSnapshot());

        try
        {
            ParseHelper.walk(ast, walker, patternWalkRule, expression, expressionForErrorMessage);
        }
        catch (ASTWalkException ex)
        {
            log.debug(".createPattern Error validating expression", ex);
            throw new EPStatementException(ex.getMessage(), expression);
        }
        catch (EPStatementSyntaxException ex)
        {
            throw ex;
        }
        catch (RuntimeException ex)
        {
            String message = "Error in expression";
            log.debug(message, ex);
            throw new EPStatementException(getNullableErrortext(message, ex.getMessage()), expression);
        }

        if (log.isDebugEnabled())
        {
            ASTUtil.dumpAST(ast);
        }

        if (walker.getStatementSpec().getStreamSpecs().size() > 1)
        {
            throw new IllegalStateException("Unexpected multiple stream specifications encountered");
        }

        // Get pattern specification
        PatternStreamSpecRaw patternStreamSpec = (PatternStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0);

        // Create statement spec, set pattern stream, set wildcard select
        StatementSpecRaw statementSpec = new StatementSpecRaw(SelectClauseStreamSelectorEnum.ISTREAM_ONLY);
        statementSpec.getStreamSpecs().add(patternStreamSpec);
        statementSpec.getSelectClauseSpec().getSelectExprList().clear();
        statementSpec.getSelectClauseSpec().getSelectExprList().add(new SelectClauseElementWildcard());
        statementSpec.setAnnotations(walker.getStatementSpec().getAnnotations());
        statementSpec.setExpressionNoAnnotations(parseResult.getExpressionWithoutAnnotations());

        return statementSpec;
    }

    private static String getNullableErrortext(String msg, String cause)
    {
        if (cause == null)
        {
            return msg;
        }
        else
        {
            return msg + ": " + cause;
        }
    }

    private static Log log = LogFactory.getLog(EPAdministratorHelper.class);
}