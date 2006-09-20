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

import java.util.Map;

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

        // Build event type of aggregate event representing the pattern
        // TODO checking if more then 1, comments in code
        PatternStreamSpec patternStreamSpec = (PatternStreamSpec) walker.getStatementSpec().getStreamSpecs().get(0);

        EvalRootNode rootNode = new EvalRootNode();
        rootNode.addChildNode(patternStreamSpec.getEvalNode());
        EPPatternStmtStartMethod startMethod = new EPPatternStmtStartMethod(services, rootNode);

        // generate event type
        Map<String, EventType> eventTypes = patternStreamSpec.getTaggedEventTypes();
        EventType eventType = services.getEventAdapterService().createAnonymousMapTypeUnd(eventTypes);

        EPPatternStatementImpl patternStatement = new EPPatternStatementImpl(expression,
                eventType, services.getDispatchService(), services.getEventAdapterService(), startMethod);
        
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


        // Create start method
        StatementSpec statementSpec = walker.getStatementSpec();
        EPEQLStmtStartMethod startMethod = new EPEQLStmtStartMethod(statementSpec, eqlStatement, services);

        return new EPEQLStatementImpl(eqlStatement, services.getDispatchService(), startMethod);
    }

    private static Log log = LogFactory.getLog(EPAdministratorImpl.class);
}
