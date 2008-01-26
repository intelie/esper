/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.core;

import net.esper.client.*;
import net.esper.client.soda.EPStatementObjectModel;
import net.esper.eql.generated.EsperEPL2GrammarParser;
import net.esper.eql.parse.*;
import net.esper.eql.spec.*;
import net.esper.antlr.ASTUtil;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Implementation for the admin interface.
 */
public class EPAdministratorImpl implements EPAdministrator
{
    private static ParseRuleSelector patternParseRule;
    private static ParseRuleSelector eqlParseRule;
    private static WalkRuleSelector patternWalkRule;
    private static WalkRuleSelector eqlWalkRule;

    private EPServicesContext services;
    private ConfigurationOperations configurationOperations;

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
            public void invokeWalkRule(EQLTreeWalker walker) throws RecognitionException
            {
                walker.startPatternExpressionRule();
            }
        };

        eqlParseRule = new ParseRuleSelector()
        {
            public Tree invokeParseRule(EsperEPL2GrammarParser parser) throws RecognitionException
            {
                EsperEPL2GrammarParser.startEPLExpressionRule_return r = parser.startEPLExpressionRule();
                return (Tree) r.getTree();
            }
        };
        eqlWalkRule = new WalkRuleSelector()
        {
            public void invokeWalkRule(EQLTreeWalker walker) throws RecognitionException
            {
                walker.startEPLExpressionRule();
            }
        };
    }

    /**
     * Constructor - takes the services context as argument.
     * @param services - references to services
     * @param configurationOperations - runtime configuration operations
     */
    public EPAdministratorImpl(EPServicesContext services, ConfigurationOperations configurationOperations)
    {
        this.services = services;
        this.configurationOperations = configurationOperations;
    }

    public EPStatement createPattern(String onExpression) throws EPException
    {
        return createPatternStmt(onExpression, null);
    }

    public EPStatement createEQL(String eqlStatement) throws EPException
    {
        return createEQLStmt(eqlStatement, null);
    }

    public EPStatement createPattern(String expression, String statementName) throws EPException
    {
        return createPatternStmt(expression, statementName);
    }

    public EPStatement createEQL(String eqlStatement, String statementName) throws EPException
    {
        return createEQLStmt(eqlStatement, statementName);
    }

    private EPStatement createPatternStmt(String expression, String statementName) throws EPException
    {
        StatementSpecRaw rawPattern = compilePattern(expression);
        return services.getStatementLifecycleSvc().createAndStart(rawPattern, expression, true, statementName);

        /**
         * For round-trip testing of all statements, of a statement to SODA and creation from SODA, use below lines:
        String pattern = "select * from pattern[" + expression + "]";
        EPStatementObjectModel model = compileEQL(pattern);
        return create(model, statementName);
         */
    }

    private EPStatement createEQLStmt(String eqlStatement, String statementName) throws EPException
    {
        StatementSpecRaw statementSpec = compileEQL(eqlStatement, statementName, services);
        EPStatement statement = services.getStatementLifecycleSvc().createAndStart(statementSpec, eqlStatement, false, statementName);

        log.debug(".createEQLStmt Statement created and started");
        return statement;

        /**
         * For round-trip testing of all statements, of a statement to SODA and creation from SODA, use below lines:
        EPStatementObjectModel model = compile(eqlStatement);
        return create(model, statementName);
         */
    }

    public EPStatement create(EPStatementObjectModel sodaStatement) throws EPException
    {
        return create(sodaStatement, null);
    }

    public EPStatement create(EPStatementObjectModel sodaStatement, String statementName) throws EPException
    {
        // Specifies the statement
        StatementSpecRaw statementSpec = StatementSpecMapper.map(sodaStatement, services.getEngineImportService(), services.getVariableService());
        String eqlStatement = sodaStatement.toEQL(); 

        EPStatement statement = services.getStatementLifecycleSvc().createAndStart(statementSpec, eqlStatement, false, statementName);

        log.debug(".createEQLStmt Statement created and started");
        return statement;
    }

    public EPPreparedStatement prepareEQL(String eqlExpression) throws EPException
    {
        // compile to specification
        StatementSpecRaw statementSpec = compileEQL(eqlExpression, null, services);

        // map to object model thus finding all substitution parameters and their indexes
        StatementSpecUnMapResult unmapped = StatementSpecMapper.unmap(statementSpec);

        // the prepared statement is the object model plus a list of substitution parameters
        // map to specification will refuse any substitution parameters that are unfilled
        return new EPPreparedStatementImpl(unmapped.getObjectModel(), unmapped.getIndexedParams());
    }

    public EPPreparedStatement preparePattern(String patternExpression) throws EPException
    {
        StatementSpecRaw rawPattern = compilePattern(patternExpression);

        // map to object model thus finding all substitution parameters and their indexes
        StatementSpecUnMapResult unmapped = StatementSpecMapper.unmap(rawPattern);

        // the prepared statement is the object model plus a list of substitution parameters
        // map to specification will refuse any substitution parameters that are unfilled
        return new EPPreparedStatementImpl(unmapped.getObjectModel(), unmapped.getIndexedParams());
    }

    public EPStatement create(EPPreparedStatement prepared, String statementName) throws EPException
    {
        EPPreparedStatementImpl impl = (EPPreparedStatementImpl) prepared;

        StatementSpecRaw statementSpec = StatementSpecMapper.map(impl.getModel(), services.getEngineImportService(), services.getVariableService());
        String eqlStatement = impl.getModel().toEQL();

        return services.getStatementLifecycleSvc().createAndStart(statementSpec, eqlStatement, false, statementName);
    }

    public EPStatement create(EPPreparedStatement prepared) throws EPException
    {
        return create(prepared, null);
    }

    public EPStatementObjectModel compileEQL(String eqlStatement) throws EPException
    {
        StatementSpecRaw statementSpec = compileEQL(eqlStatement, null, services);
        StatementSpecUnMapResult unmapped = StatementSpecMapper.unmap(statementSpec);
        if (unmapped.getIndexedParams().size() != 0)
        {
            throw new EPException("Invalid use of substitution parameters marked by '?' in statement, use the prepare method to prepare statements with substitution parameters");
        }
        return unmapped.getObjectModel();
    }

    public EPStatement getStatement(String name)
    {
        return services.getStatementLifecycleSvc().getStatementByName(name);
    }

    public String[] getStatementNames()
    {
        return services.getStatementLifecycleSvc().getStatementNames();
    }

    public void startAllStatements() throws EPException
    {
        services.getStatementLifecycleSvc().startAllStatements();
    }

    public void stopAllStatements() throws EPException
    {
        services.getStatementLifecycleSvc().stopAllStatements();
    }

    public void destroyAllStatements() throws EPException
    {
        services.getStatementLifecycleSvc().destroyAllStatements();
    }

    public ConfigurationOperations getConfiguration()
    {
        return configurationOperations;
    }

    /**
     * Destroys an engine instance.
     */
    public void destroy()
    {
        services = null;
        configurationOperations = null;        
    }

    /**
     * Compile the EQL.
     * @param eqlStatement expression to compile
     * @param statementName is the name of the statement
     * @param services is the context
     * @return statement specification
     */
    protected static StatementSpecRaw compileEQL(String eqlStatement, String statementName, EPServicesContext services)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".createEQLStmt statementName=" + statementName + " eqlStatement=" + eqlStatement);
        }

        Tree ast = ParseHelper.parse(eqlStatement, eqlParseRule);
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);

        EQLTreeWalker walker = new EQLTreeWalker(nodes, services.getEngineImportService(), services.getVariableService(), services.getSchedulingService().getTime());

        try
        {
            ParseHelper.walk(ast, walker, eqlWalkRule, eqlStatement);
        }
        catch (ASTWalkException ex)
        {
            log.error(".createEQL Error validating expression", ex);
            throw new EPStatementException(ex.getMessage(), eqlStatement);
        }
        catch (EPStatementSyntaxException ex)
        {
            throw ex;
        }
        catch (RuntimeException ex)
        {
            log.error(".createEQL Error validating expression", ex);
            throw new EPStatementException(ex.getMessage(), eqlStatement);
        }

        if (log.isDebugEnabled())
        {
            ASTUtil.dumpAST(ast);
        }

        // Specifies the statement
        return walker.getStatementSpec();
    }

    private StatementSpecRaw compilePattern(String expression)
    {
        // Parse and walk
        Tree ast = ParseHelper.parse(expression, patternParseRule);
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
        EQLTreeWalker walker = new EQLTreeWalker(nodes, services.getEngineImportService(), services.getVariableService(), services.getSchedulingService().getTime());

        try
        {
            ParseHelper.walk(ast, walker, patternWalkRule, expression);
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
            log.debug(".createPattern Error validating expression", ex);
            throw new EPStatementException(ex.getMessage(), expression);
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
        StatementSpecRaw statementSpec = new StatementSpecRaw();
        statementSpec.getStreamSpecs().add(patternStreamSpec);
        statementSpec.getSelectClauseSpec().getSelectExprList().clear();
        statementSpec.getSelectClauseSpec().getSelectExprList().add(new SelectClauseElementWildcard());

        return statementSpec;
    }

    private static Log log = LogFactory.getLog(EPAdministratorImpl.class);
}
