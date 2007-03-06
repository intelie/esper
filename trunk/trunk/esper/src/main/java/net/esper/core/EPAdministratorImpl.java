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

import java.util.ArrayList;
import java.util.List;

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
    private final StatementLifecycleSvc statementLifecycleSvc;

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
     * @param statementLifecycleSvc - service for statement management
     */
    public EPAdministratorImpl(EPServicesContext services,
                               StatementLifecycleSvc statementLifecycleSvc)
    {
        this.services = services;
        this.statementLifecycleSvc = statementLifecycleSvc;
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

        return statementLifecycleSvc.createAndStart(compiledSpec, expression, true, statementName);
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
            throw new EPStatementException("Unexpected error compiling statement:" + ex.getMessage(), eqlStatement);
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

    public EPStatement createEQLStmt(String eqlStatement, String statementName) throws EPException
    {
        AST ast = ParseHelper.parse(eqlStatement, eqlParseRule);
        EQLTreeWalker walker = new EQLTreeWalker();

        try
        {
            ParseHelper.walk(ast, walker, eqlWalkRule, eqlStatement);
        }
        catch (ASTWalkException ex)
        {
            log.debug(".createEQL Error validating expression", ex);
            throw new EPStatementException(ex.getMessage(), eqlStatement);
        }
        catch (RuntimeException ex)
        {
            log.debug(".createEQL Error validating expression", ex);
            throw new EPStatementException(ex.getMessage(), eqlStatement);
        }

        if (log.isDebugEnabled())
        {
            DebugFacility.dumpAST(walker.getAST());
        }

        // Specifies the statement
        StatementSpecRaw statementSpec = walker.getStatementSpec();
        StatementSpecCompiled compiledSpec = compile(statementSpec, eqlStatement);

        return statementLifecycleSvc.createAndStart(compiledSpec, eqlStatement, false, statementName);
    }

    public EPStatement getStatement(String name)
    {
        return statementLifecycleSvc.getStatement(name);
    }

    public String[] getStatementNames()
    {
        return statementLifecycleSvc.getStatementNames();
    }

    public void startAllStatements() throws EPException
    {
        statementLifecycleSvc.startAllStatements();
    }

    public void stopAllStatements() throws EPException
    {
        statementLifecycleSvc.stopAllStatements();
    }

    public void destroyAllStatements() throws EPException
    {
        statementLifecycleSvc.destroyAllStatements();
    }

    private static Log log = LogFactory.getLog(EPAdministratorImpl.class);
}
