package net.esper.core;

import net.esper.client.EPAdministrator;
import net.esper.client.EPStatement;
import net.esper.client.EPException;
import net.esper.client.EPStatementException;
import net.esper.event.EventType;
import net.esper.eql.parse.*;
import net.esper.eql.generated.EQLStatementParser;
import net.esper.eql.generated.EQLBaseWalker;
import net.esper.eql.expression.*;
import net.esper.collection.Pair;
import net.esper.util.DebugFacility;

import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

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
        EQLPatternTreeWalker walker = new EQLPatternTreeWalker(services.getEventAdapterService());

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
        Map<String, EventType> eventTypes = walker.getTaggedEventTypes();
        Map<String, Class> types = getUnderlyingTypes(eventTypes);
        EventType eventType = services.getEventAdapterService().createAnonymousMapType(types);

        EPPatternStmtStartMethod startMethod = new EPPatternStmtStartMethod(services, walker.getRootNode());

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

        // Compile list of selection elements
        List<Pair<ExprNode, String>> rawSelectionList = walker.getSelectListExpressions();
        List<SelectExprElement> selectClause = new LinkedList<SelectExprElement>();
        for (Pair<ExprNode, String> raw : rawSelectionList)
        {
            selectClause.add(new SelectExprElement(raw.getFirst(), raw.getSecond()));
        }

        // Create start method
        List<StreamSpec> fromClause = walker.getStreamSpecs();
        List<OuterJoinDesc> outerJoinClauses = walker.getOuterJoinDescList();
        ExprNode whereClause = walker.getFilterRootNode();
        List<ExprNode> groupByNodes = walker.getGroupByExpressions();
        ExprNode havingClause = walker.getHavingExprRootNode();
        OutputLimitSpec outputClause = walker.getOutputLimitSpec();
        InsertIntoDesc insertIntoDesc = walker.getInsertIntoDesc();
        List<Pair<ExprNode, Boolean>> orderByClause = walker.getOrderByList();

        EPEQLStmtStartMethod startMethod = new EPEQLStmtStartMethod(insertIntoDesc, selectClause, fromClause,
                outerJoinClauses, whereClause, groupByNodes, havingClause, outputClause, orderByClause, eqlStatement, services);

        return new EPEQLStatementImpl(eqlStatement, services.getDispatchService(), startMethod);
    }

    /**
     * Return a map of property name and types for a given map of property name and event type,
     * by extracting the underlying type for the event types.
     * @param types is the various event types returned.
     * @return map of property name and type
     */
    private static Map<String, Class> getUnderlyingTypes(Map<String, EventType> types)
    {
        Map<String, Class> classes = new HashMap<String, Class>();

        for (Map.Entry<String, EventType> type : types.entrySet())
        {
            classes.put(type.getKey(), type.getValue().getUnderlyingType());
        }

        return classes;
    }


    private static Log log = LogFactory.getLog(EPAdministratorImpl.class);
}
