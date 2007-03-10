package net.esper.core;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import net.esper.client.EPAdministrator;
import net.esper.client.EPException;
import net.esper.client.EPStatement;
import net.esper.client.EPStatementException;
import net.esper.eql.generated.EQLBaseWalker;
import net.esper.eql.generated.EQLStatementParser;
import net.esper.eql.parse.*;
import net.esper.eql.spec.*;
import net.esper.eql.expression.ExprValidationException;
import net.esper.util.DebugFacility;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * Implementation for the admin interface.
 */
public class EPAdministratorImpl implements EPAdministrator
{
    private static ParseRuleSelector patternParseRule;
    private static ParseRuleSelector eqlParseRule;
    private static WalkRuleSelector patternWalkRule;
    private static WalkRuleSelector eqlWalkRule;

    private final EPServicesContext services;

    static
    {
        patternParseRule = new ParseRuleSelector()
        {
            public void invokeParseRule(EQLStatementParser parser) throws TokenStreamException, RecognitionException
            {
                parser.startPatternExpressionRule();
            }
        };
        patternWalkRule = new WalkRuleSelector()
        {
            public void invokeWalkRule(EQLBaseWalker walker, AST ast) throws RecognitionException
            {
                walker.startPatternExpressionRule(ast);
            }
        };

        eqlParseRule = new ParseRuleSelector()
        {
            public void invokeParseRule(EQLStatementParser parser) throws TokenStreamException, RecognitionException
            {
                parser.startEQLExpressionRule();
            }
        };
        eqlWalkRule = new WalkRuleSelector()
        {
            public void invokeWalkRule(EQLBaseWalker walker, AST ast) throws RecognitionException
            {
                walker.startEQLExpressionRule(ast);
            }
        };
    }

    /**
     * Constructor - takes the services context as argument.
     * @param services - references to services
     */
    public EPAdministratorImpl(EPServicesContext services)
    {
        this.services = services;
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
        if (statementName == null)
        {
            throw new IllegalArgumentException("Invalid parameter, statement name cannot be null");
        }
        return createPatternStmt(expression, statementName);
    }

    public EPStatement createEQL(String eqlStatement, String statementName) throws EPException
    {
        return createEQLStmt(eqlStatement, statementName);
    }

    private EPStatement createPatternStmt(String expression, String statementName) throws EPException
    {
        // Parse and walk
        AST ast = ParseHelper.parse(expression, patternParseRule);
        EQLTreeWalker walker = new EQLTreeWalker();

        try
        {
            ParseHelper.walk(ast, walker, patternWalkRule, expression);
        }
        catch (ASTWalkException ex)
        {
            log.debug(".createPattern Error validating expression", ex);
            throw new EPStatementException(ex.getMessage(), expression);
        }
        catch (RuntimeException ex)
        {
            log.debug(".createPattern Error validating expression", ex);
            throw new EPStatementException(ex.getMessage(), expression);
        }

        if (log.isDebugEnabled())
        {
            DebugFacility.dumpAST(walker.getAST());
        }

        if (walker.getStatementSpec().getStreamSpecs().size() > 1)
        {
            throw new IllegalStateException("Unexpected multiple stream specifications encountered");
        }

        // Get pattern specification
        PatternStreamSpecRaw patternStreamSpec = (PatternStreamSpecRaw) walker.getStatementSpec().getStreamSpecs().get(0);

        // Create statement spec
        StatementSpecRaw statementSpec = new StatementSpecRaw();
        statementSpec.getStreamSpecs().add(patternStreamSpec);

        StatementSpecCompiled compiledSpec = compile(statementSpec, expression);

        return services.getStatementLifecycleSvc().createAndStart(compiledSpec, expression, true, statementName);
    }

    public EPStatement createEQLStmt(String eqlStatement, String statementName) throws EPException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".createEQLStmt statementName=" + statementName + " eqlStatement=" + eqlStatement);
        }

        AST ast = ParseHelper.parse(eqlStatement, eqlParseRule);
        EQLTreeWalker walker = new EQLTreeWalker();

        try
        {
            ParseHelper.walk(ast, walker, eqlWalkRule, eqlStatement);
        }
        catch (ASTWalkException ex)
        {
            log.error(".createEQL Error validating expression", ex);
            throw new EPStatementException(ex.getMessage(), eqlStatement);
        }
        catch (RuntimeException ex)
        {
            log.error(".createEQL Error validating expression", ex);
            throw new EPStatementException(ex.getMessage(), eqlStatement);
        }

        if (log.isDebugEnabled())
        {
            DebugFacility.dumpAST(walker.getAST());
        }

        // Specifies the statement
        StatementSpecRaw statementSpec = walker.getStatementSpec();
        StatementSpecCompiled compiledSpec = compile(statementSpec, eqlStatement);

        EPStatement statement = services.getStatementLifecycleSvc().createAndStart(compiledSpec, eqlStatement, false, statementName);

        log.debug(".createEQLStmt Statement created and started");
        return statement;
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

    private StatementSpecCompiled compile(StatementSpecRaw spec, String eqlStatement) throws EPStatementException
    {
        List<StreamSpecCompiled> compiledStreams;

        try
        {
            compiledStreams = new ArrayList<StreamSpecCompiled>();
            for (StreamSpecRaw rawSpec : spec.getStreamSpecs())
            {
                StreamSpecCompiled compiled = rawSpec.compile(services.getEventAdapterService(), services.getAutoImportService());
                compiledStreams.add(compiled);
            }
        }
        catch (ExprValidationException ex)
        {
            throw new EPStatementException(ex.getMessage(), eqlStatement);
        }
        catch (RuntimeException ex)
        {
            String text = "Unexpected error compiling statement";
            log.error(".compile " + text, ex);
            throw new EPStatementException(text + ":" + ex.getClass().getName() + ":" + ex.getMessage(), eqlStatement);
        }

        return new StatementSpecCompiled(
                spec.getInsertIntoDesc(),
                spec.getSelectStreamSelectorEnum(),
                spec.getSelectClauseSpec(),
                compiledStreams,
                spec.getOuterJoinDescList(),
                spec.getFilterRootNode(),
                spec.getGroupByExpressions(),
                spec.getHavingExprRootNode(),
                spec.getOutputLimitSpec(),
                spec.getOrderByList());
    }    

    private static Log log = LogFactory.getLog(EPAdministratorImpl.class);
}
