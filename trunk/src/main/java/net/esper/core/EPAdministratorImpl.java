package net.esper.core;

import net.esper.client.EPAdministrator;
import net.esper.client.EPStatement;
import net.esper.client.EPException;
import net.esper.client.EPStatementException;
import net.esper.event.EventType;
import net.esper.eql.parse.*;
import net.esper.eql.generated.EQLStatementParser;
import net.esper.eql.generated.EQLBaseWalker;
import net.esper.eql.spec.StatementSpec;
import net.esper.eql.spec.PatternStreamSpec;
import net.esper.util.DebugFacility;
import net.esper.pattern.EvalRootNode;
import net.esper.persist.LogContextNode;
import net.esper.client.logstate.LogEntryType;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import antlr.TokenStreamException;
import antlr.RecognitionException;
import antlr.collections.AST;
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
    private List<EPStatement> statements;

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
        this.statements = new ArrayList<EPStatement>();
    }

    public EPStatement createPattern(String expression) throws EPException
    {
        // Parse and walk
        AST ast = ParseHelper.parse(expression, patternParseRule);
        EQLTreeWalker walker = new EQLTreeWalker(services.getEventAdapterService());

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
        PatternStreamSpec patternStreamSpec = (PatternStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0);

        // Create start method
        EvalRootNode rootNode = new EvalRootNode();
        rootNode.addChildNode(patternStreamSpec.getEvalNode());
        EPPatternStmtStartMethod startMethod = new EPPatternStmtStartMethod(services, rootNode);

        // Generate event type
        Map<String, EventType> eventTypes = patternStreamSpec.getTaggedEventTypes();
        EventType eventType = services.getEventAdapterService().createAnonymousMapTypeUnd(eventTypes);

        // Create statement state context
        LogContextNode<String> rootEngineContext = services.getLogContext().getRootNode();
        LogContextNode<String> statementLogContext = rootEngineContext.createChild(LogEntryType.STATEMENT, expression);
        statementLogContext.update();

        EPPatternStatementImpl patternStatement = new EPPatternStatementImpl(expression,
                eventType, services.getDispatchService(), services.getEventAdapterService(), statementLogContext,
                startMethod);
        
        return patternStatement;
    }

    public EPStatement createEQL(String eqlStatement) throws EPException
    {
        AST ast = ParseHelper.parse(eqlStatement, eqlParseRule);
        EQLTreeWalker walker = new EQLTreeWalker(services.getEventAdapterService());

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

        // Create statement state context
        LogContextNode<String> rootEngineContext = services.getLogContext().getRootNode();
        LogContextNode<String> statementLogContext = rootEngineContext.createChild(LogEntryType.STATEMENT, eqlStatement);
        statementLogContext.update();

        // Create start method
        StatementSpec statementSpec = walker.getStatementSpec();
        EPEQLStmtStartMethod startMethod = new EPEQLStmtStartMethod(statementSpec, eqlStatement, services, statementLogContext);

        EPStatement statement = new EPEQLStatementImpl(eqlStatement, services.getDispatchService(), startMethod, statementLogContext);
        statements.add(statement);
        return statement;
    }

    public List<EPStatement> getStatements()
    {
        return Collections.unmodifiableList(statements);
    }

    private static Log log = LogFactory.getLog(EPAdministratorImpl.class);
}
