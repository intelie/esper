package net.esper.eql.parse;

import net.esper.filter.FilterSpec;
import net.esper.view.ViewSpec;
import net.esper.eql.generated.EQLBaseWalker;
import net.esper.eql.expression.*;
import net.esper.eql.spec.*;
import net.esper.type.*;
import net.esper.collection.Pair;
import net.esper.event.EventAdapterService;
import net.esper.event.EventType;
import net.esper.pattern.*;
import net.esper.pattern.observer.ObserverEnum;
import net.esper.pattern.observer.ObserverFactory;
import net.esper.pattern.guard.GuardEnum;
import net.esper.pattern.guard.GuardFactory;
import net.esper.util.ConstructorHelper;
import antlr.collections.AST;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.beanutils.ConstructorUtils;

import java.util.*;

/**
 * Called during the walks of a EQL expression AST tree as specified in the grammar file.
 * Constructs filter and view specifications etc.
 */
public class EQLTreeWalker extends EQLBaseWalker
{
    // services required
    private final EventAdapterService eventAdapterService;

    // private holding areas for accumulated info
    private final Map<AST, ExprNode> astExprNodeMap = new HashMap<AST, ExprNode>();
    private final Map<AST, EvalNode> astPatternNodeMap = new HashMap<AST, EvalNode>();
    private final Map<String, EventType> taggedEventTypes = new HashMap<String, EventType>(); // Stores types for filters with tags
    private FilterSpec filterSpec;
    private final List<ViewSpec> viewSpecs = new LinkedList<ViewSpec>();

    // Pattern indicator dictates behavior for some AST nodes
    private boolean isProcessingPattern;

    // AST Walk result
    private final StatementSpec statementSpec;

    /**
     * Ctor.
     * @param eventAdapterService for resolving event names
     */
    public EQLTreeWalker(EventAdapterService eventAdapterService)
    {
        this.eventAdapterService = eventAdapterService;
        statementSpec = new StatementSpec();
    }

    /**
     * Returns statement specification.
     * @return statement spec.
     */
    public StatementSpec getStatementSpec()
    {
        return statementSpec;
    }

    /**
     * Set to indicate that we are walking a pattern.
     * @param isPatternWalk is true if walking a pattern
     */
    protected void setIsPatternWalk(boolean isPatternWalk)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".setIsPatternWalk " + isPatternWalk);
        }
        isProcessingPattern = isPatternWalk;
    }

    /**
     * Leave AST node and process it's type and child nodes.
     * @param node is the node to complete
     * @throws ASTWalkException
     */
    protected void leaveNode(AST node) throws ASTWalkException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".leaveNode " + node);
        }

        switch (node.getType())
        {
            case STREAM_EXPR:
                leaveStreamExpr(node);
                break;
            case EVENT_FILTER_EXPR:
                leaveFilter(node);
                break;
            case PATTERN_INCL_EXPR:
                return;
            case VIEW_EXPR:
                leaveView(node);
                break;
            case SELECTION_EXPR:
                leaveSelectClause(node);
                break;
            case SELECTION_ELEMENT_EXPR:
                leaveSelectionElement(node);
                break;
            case EVENT_PROP_EXPR:
                if (!isProcessingPattern)
                {
                    leaveEventPropertyExpr(node);
                }
                break;
            case EVAL_AND_EXPR:
                leaveJoinAndExpr(node);
                break;
            case EVAL_OR_EXPR:
                leaveJoinOrExpr(node);
                break;
            case EVAL_EQUALS_EXPR:
            case EVAL_NOTEQUALS_EXPR:
                leaveJoinEqualsExpr(node);
                break;
            case WHERE_EXPR:
                leaveWhereClause();
                break;
            case INT_TYPE:
            case LONG_TYPE:
            case BOOL_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case NULL_TYPE:
                leaveConstant(node);
                break;
            case STAR:
            case MINUS:
            case PLUS:
            case DIV:
            case MOD:
                leaveMath(node);
                break;
            case BAND:
            case BOR:
            case BXOR:
            	leaveBitWise(node);
            	break;
             case LT:
            case GT:
            case LE:
            case GE:
                leaveRelationalOp(node);
                break;
            case COALESCE:
                leaveCoalesce(node);
                break;
            case NOT_EXPR:
                leaveNot(node);
                break;
            case SUM:
            case AVG:
            case COUNT:
            case MEDIAN:
            case STDDEV:
            case AVEDEV:
                leaveAggregate(node);
                break;
            case LIB_FUNCTION:
            	leaveLibFunction(node);
            	break;
            case LEFT_OUTERJOIN_EXPR:
            case RIGHT_OUTERJOIN_EXPR:
            case FULL_OUTERJOIN_EXPR:
                leaveOuterJoin(node);
                break;
            case GROUP_BY_EXPR:
                leaveGroupBy(node);
                break;
            case HAVING_EXPR:
                leaveHavingClause();
                break;
            case ORDER_BY_EXPR:
            	break;
            case ORDER_ELEMENT_EXPR:
            	leaveOrderByElement(node);
            	break;
            case EVENT_LIMIT_EXPR:
            case SEC_LIMIT_EXPR:
            case MIN_LIMIT_EXPR:
            	leaveOutputLimit(node);
            	break;
            case INSERTINTO_EXPR:
            	leaveInsertInto(node);
            	break;
            case CONCAT:
            	leaveConcat(node);
            	break;
            case CASE:
                leaveCaseNode(node, false);
                break;
            case CASE2:
                leaveCaseNode(node, true);
                break;
            case EVERY_EXPR:
                leaveEvery(node);
                break;
            case FOLLOWED_BY_EXPR:
                leaveFollowedBy(node);
                break;
            case OR_EXPR:
                leaveOr(node);
                break;
            case AND_EXPR:
                leaveAnd(node);
                break;
            case GUARD_EXPR:
                leaveGuard(node);
                break;
            case OBSERVER_EXPR:
                leaveObserver(node);
                break;
            case IN_SET:
            case NOT_IN_SET:
                leaveIn(node);
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                leaveBetween(node);
                break;
            case LIKE:
            case NOT_LIKE:
                leaveLike(node);
                break;
            case REGEXP:
            case NOT_REGEXP:
                leaveRegexp(node);
                break;
            default:
                throw new ASTWalkException("Unhandled node type encountered, type '" + node.getType() +
                        "' with text '" + node.getText() + "'");
        }

        // For each AST child node of this AST node that generated an ExprNode add the child node to the expression node.
        // This is for automatic expression tree building.
        ExprNode thisEvalNode = astExprNodeMap.get(node);

        // Loop over all child nodes for this node.
        AST childNode = node.getFirstChild();
        do {
            ExprNode childEvalNode = astExprNodeMap.get(childNode);
            // If there was an expression node generated for the child node, and there is a current expression node,
            // add it to the current expression node (thisEvalNode)
            if ((childEvalNode != null) && (thisEvalNode != null))
            {
                thisEvalNode.addChildNode(childEvalNode);
                astExprNodeMap.remove(childNode);
            }
            // Next child node
            if (childNode != null)
            {
                childNode = childNode.getNextSibling();
            }
        }
        while (childNode != null);

        // For each AST child node of this AST node that generated an EvalNode add the EvalNode as a child
        EvalNode thisPatternNode = astPatternNodeMap.get(node);

        childNode = node.getFirstChild();
        do {
            EvalNode childEvalNode = astPatternNodeMap.get(childNode);
            if (childEvalNode != null)
            {
                thisPatternNode.addChildNode(childEvalNode);
                astPatternNodeMap.remove(childNode);
            }
            // Next child node
            if (childNode != null)
            {
                childNode = childNode.getNextSibling();
            }
        }
        while (childNode != null);
    }

    /**
     * End processing of the AST tree for stand-alone pattern expressions.
     * @throws ASTWalkException
     */
    protected void endPattern() throws ASTWalkException
    {
        log.debug(".endPattern");

        if ((astPatternNodeMap.size() > 1) || ((astPatternNodeMap.size() == 0)))
        {
            throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child elements for root");
        }

        // Get expression node sub-tree from the AST nodes placed so far
        EvalNode evalNode = astPatternNodeMap.values().iterator().next();

        PatternStreamSpec streamSpec = new PatternStreamSpec(evalNode, taggedEventTypes, new LinkedList<ViewSpec>(), null);
        statementSpec.getStreamSpecs().add(streamSpec);

        taggedEventTypes.clear();
        astPatternNodeMap.clear();
    }

    /**
     * End processing of the AST tree, check that expression nodes found their homes.
     * @throws ASTWalkException
     */
    protected void end() throws ASTWalkException
    {
        log.debug(".end");

        if (astExprNodeMap.size() > 1)
        {
            throw new ASTWalkException("Unexpected AST tree contains left over child elements," +
                    " not all expression nodes have been removed from AST-to-expression nodes map");
        }
        if (astPatternNodeMap.size() > 1)
        {
            throw new ASTWalkException("Unexpected AST tree contains left over child elements," +
                    " not all pattern nodes have been removed from AST-to-pattern nodes map");
        }
    }

    private void leaveSelectionElement(AST node) throws ASTWalkException
    {
        log.debug(".leaveSelectionElement");
        if ((astExprNodeMap.size() > 1) || ((astExprNodeMap.size() == 0)))
        {
            throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child element for root");
        }

        // Get expression node sub-tree from the AST nodes placed so far
        ExprNode exprNode = astExprNodeMap.values().iterator().next();
        astExprNodeMap.clear();

        // Get list element name
        String optionalName = null;
        if (node.getFirstChild().getNextSibling() != null)
        {
            optionalName = node.getFirstChild().getNextSibling().getText();
        }

        // Add as selection element
        statementSpec.getSelectListExpressions().add(new SelectExprElementUnnamedSpec(exprNode, optionalName));
    }

    private void leaveView(AST node) throws ASTWalkException
    {
        log.debug(".leaveView");
        ViewSpec spec = ASTViewSpecHelper.buildSpec(node);
        viewSpecs.add(spec);
    }

    private void leaveStreamExpr(AST node)
    {
        log.debug(".leaveStreamExpr");

        // Determine the optional stream name
        // Search for identifier node that carries the stream name in an "from Class.win:time().std:doit() as StreamName"
        AST streamNameNode = node.getFirstChild().getNextSibling();
        while ((streamNameNode != null) && (streamNameNode.getType() != IDENT))
        {
            streamNameNode = streamNameNode.getNextSibling();
        }
        String streamName = null;
        if (streamNameNode != null)
        {
            streamName = streamNameNode.getText();
        }

        // Convert to a stream specification instance
        StreamSpec streamSpec = null;
        // If the first subnode is a filter node, we have a filter stream specification
        if (node.getFirstChild().getType() == EVENT_FILTER_EXPR)
        {
            streamSpec = new FilterStreamSpec(filterSpec, viewSpecs, streamName);
        }
        else if (node.getFirstChild().getType() == PATTERN_INCL_EXPR)
        {
            if ((astPatternNodeMap.size() > 1) || ((astPatternNodeMap.size() == 0)))
            {
                throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child elements for root");
            }

            // Get expression node sub-tree from the AST nodes placed so far
            EvalNode evalNode = astPatternNodeMap.values().iterator().next();

            streamSpec = new PatternStreamSpec(evalNode, taggedEventTypes, viewSpecs, streamName);
            taggedEventTypes.clear();
            astPatternNodeMap.clear();
        }
        else if (node.getFirstChild().getType() == DATABASE_JOIN_EXPR)
        {
            AST dbchildNode = node.getFirstChild().getFirstChild();
            String dbName = dbchildNode.getText();
            String sqlWithParams = StringValue.parseString(dbchildNode.getNextSibling().getText().trim());
            streamSpec = new DBStatementStreamSpec(streamName, viewSpecs, dbName, sqlWithParams);
        }
        else
        {
            throw new ASTWalkException("Unexpected AST child node to stream expression, type=" + node.getFirstChild().getType());
        }
        viewSpecs.clear();
        statementSpec.getStreamSpecs().add(streamSpec);
    }

    private void leaveEventPropertyExpr(AST node)
    {
        log.debug(".leaveEventPropertyExpr");

        if (node.getNumberOfChildren() == 0)
        {
            throw new IllegalStateException("Empty event property expression encountered");
        }

        ExprIdentNode identNode;

        // The stream name may precede the event property name, but cannot be told apart from the property name:
        //      s0.p1 could be a nested property, or could be stream 's0' and property 'p1'

        // A single entry means this must be the property name.
        // And a non-simple property means that it cannot be a stream name.
        if ((node.getNumberOfChildren() == 1) || (node.getFirstChild().getType() != EVENT_PROP_SIMPLE))
        {
            String propertyName = ASTFilterSpecHelper.getPropertyName(node.getFirstChild());
            identNode = new ExprIdentNode(propertyName);
        }
        // --> this is more then one child node, and the first child node is a simple property
        // we may have a stream name in the first simple property, or a nested property
        // i.e. 's0.p0' could mean that the event has a nested property to 's0' of name 'p0', or 's0' is the stream name
        else
        {
            String streamOrNestedPropertyName = node.getFirstChild().getFirstChild().getText();
            String propertyName = ASTFilterSpecHelper.getPropertyName(node.getFirstChild().getNextSibling());
            identNode = new ExprIdentNode(propertyName, streamOrNestedPropertyName);
        }

        astExprNodeMap.put(node, identNode);
    }

    private void leaveLibFunction(AST node)
    {
    	log.debug(".leaveLibFunction");

    	if(node.getNumberOfChildren() < 1)
    	{
    		throw new IllegalArgumentException("Illegal number of child nodes for lib function");
    	}

        String childNodeText = node.getFirstChild().getText();
        if ((childNodeText.equals("max")) || (childNodeText.equals("min")))
        {
            handleMinMax(node);
            return;
        }

        if (node.getFirstChild().getType() == CLASS_IDENT)
        {
            String className = node.getFirstChild().getText();
            String methodName = node.getFirstChild().getNextSibling().getText();
            astExprNodeMap.put(node, new ExprStaticMethodNode(className, methodName));
        }
        else
        {
            throw new IllegalStateException("Unknown method named '" + node.getFirstChild().getText() + "' could not be resolved"); 
        }
    }

    private void leaveJoinEqualsExpr(AST node)
    {
        log.debug(".leaveJoinEqualsExpr");

        boolean isNot = false;
        if (node.getType() == EVAL_NOTEQUALS_EXPR)
        {
            isNot = true;
        }

        ExprEqualsNode identNode = new ExprEqualsNode(isNot);
        astExprNodeMap.put(node, identNode);
    }

    private void leaveJoinAndExpr(AST node)
    {
        log.debug(".leaveJoinAndExpr");
        ExprAndNode identNode = new ExprAndNode();
        astExprNodeMap.put(node, identNode);
    }

    private void leaveJoinOrExpr(AST node)
    {
        log.debug(".leaveJoinOrExpr");
        ExprOrNode identNode = new ExprOrNode();
        astExprNodeMap.put(node, identNode);
    }

    private void leaveConstant(AST node)
    {
        log.debug(".leaveConstant");
        ExprConstantNode constantNode = new ExprConstantNode(ASTConstantHelper.parse(node));
        astExprNodeMap.put(node, constantNode);
    }

    private void leaveMath(AST node)
    {
        log.debug(".leaveMath");

        MathArithTypeEnum mathArithTypeEnum;

        switch (node.getType())
        {
            case DIV :
                mathArithTypeEnum = MathArithTypeEnum.DIVIDE;
                break;
            case STAR :
                mathArithTypeEnum = MathArithTypeEnum.MULTIPLY;
                break;
            case PLUS :
                mathArithTypeEnum = MathArithTypeEnum.ADD;
                break;
            case MINUS :
                mathArithTypeEnum = MathArithTypeEnum.SUBTRACT;
                break;
            case MOD :
                mathArithTypeEnum = MathArithTypeEnum.MODULO;
                break;
            default :
                throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized math node type");
        }

        ExprMathNode mathNode = new ExprMathNode(mathArithTypeEnum);
        astExprNodeMap.put(node, mathNode);
    }

    // Min/Max nodes can be either an aggregate or a per-row function depending on the number or arguments
    private void handleMinMax(AST libNode)
    {
        log.debug(".handleMinMax");

        // Determine min or max
        AST childNode = libNode.getFirstChild();
        MinMaxTypeEnum minMaxTypeEnum;
        if (childNode.getText().equals("min"))
        {
            minMaxTypeEnum = MinMaxTypeEnum.MIN;
        }
        else if (childNode.getText().equals("max"))
        {
            minMaxTypeEnum = MinMaxTypeEnum.MAX;
        }
        else
        {
            throw new IllegalArgumentException("Node type " + childNode.getType() + " " + childNode.getText() + " not a recognized min max node");
        }

        // Determine distinct or not
        AST nextNode = childNode.getNextSibling();
        boolean isDistinct = false;
        if (nextNode.getType() == DISTINCT)
        {
            isDistinct = true;
        }

        // Error if more then 3 nodes with distinct since it's an aggregate function
        if ((libNode.getNumberOfChildren() > 3) && (isDistinct))
        {
            throw new ASTWalkException("The distinct keyword is not valid in per-row min and max " +
                    "functions with multiple sub-expressions");
        }

        ExprNode minMaxNode;
        if ((!isDistinct) && (libNode.getNumberOfChildren() > 2))
        {
            // use the row function
            minMaxNode = new ExprMinMaxRowNode(minMaxTypeEnum);
        }
        else
        {
            // use the aggregation function
            minMaxNode = new ExprMinMaxAggrNode(isDistinct, minMaxTypeEnum);
        }
        astExprNodeMap.put(libNode, minMaxNode);
    }

    private void leaveCoalesce(AST node)
    {
        log.debug(".leaveCoalesce");

        ExprNode coalesceNode = new ExprCoalesceNode();
        astExprNodeMap.put(node, coalesceNode);
    }

    private void leaveAggregate(AST node)
    {
        log.debug(".leaveAggregate");

        boolean isDistinct = false;
        if ((node.getFirstChild() != null) && (node.getFirstChild().getType() == DISTINCT))
        {
            isDistinct = true;
        }

        ExprAggregateNode aggregateNode = null;

        switch (node.getType())
        {
            case AVG:
                aggregateNode = new ExprAvgNode(isDistinct);
                break;
            case SUM:
                aggregateNode = new ExprSumNode(isDistinct);
                break;
            case COUNT:
                aggregateNode = new ExprCountNode(isDistinct);
                break;
            case MEDIAN:
                aggregateNode = new ExprMedianNode(isDistinct);
                break;
            case STDDEV:
                aggregateNode = new ExprStddevNode(isDistinct);
                break;
            case AVEDEV:
                aggregateNode = new ExprAvedevNode(isDistinct);
                break;
            default:
                throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized aggregate node type");
        }

        astExprNodeMap.put(node, aggregateNode);
    }

    private void leaveRelationalOp(AST node)
    {
        log.debug(".leaveRelationalOp");

        RelationalOpEnum relationalOpEnum;

        switch (node.getType())
        {
            case LT :
                relationalOpEnum = RelationalOpEnum.LT;
                break;
            case GT :
                relationalOpEnum = RelationalOpEnum.GT;
                break;
            case LE :
                relationalOpEnum = RelationalOpEnum.LE;
                break;
            case GE :
                relationalOpEnum = RelationalOpEnum.GE;
                break;
            default :
                throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized relational op node type");
        }

        ExprRelationalOpNode mathNode = new ExprRelationalOpNode(relationalOpEnum);
        astExprNodeMap.put(node, mathNode);
    }

    private void leaveBitWise(AST node)
    {
        log.debug(".leaveBitWise");

        BitWiseOpEnum bitWiseOpEnum;
        switch (node.getType())
        {
	        case BAND :
	        	bitWiseOpEnum = BitWiseOpEnum.BAND;
	            break;
	        case BOR :
	        	bitWiseOpEnum = BitWiseOpEnum.BOR;
	            break;
	        case BXOR :
	        	bitWiseOpEnum = BitWiseOpEnum.BXOR;
	            break;
	        default :
	            throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized bit wise node type");
        }

	    ExprBitWiseNode bwNode = new ExprBitWiseNode(bitWiseOpEnum);
	    astExprNodeMap.put(node, bwNode);
    }

    private void leaveWhereClause()
    {
        log.debug(".leaveWhereClause");

        if (astExprNodeMap.size() != 1)
        {
            throw new IllegalStateException("Where clause generated zero or more then one expression nodes");
        }

        // Just assign the single root ExprNode not consumed yet
        statementSpec.setFilterExprRootNode(astExprNodeMap.values().iterator().next());
        astExprNodeMap.clear();
    }

    private void leaveHavingClause()
    {
        log.debug(".leaveHavingClause");

        if (astExprNodeMap.size() != 1)
        {
            throw new IllegalStateException("Having clause generated zero or more then one expression nodes");
        }

        // Just assign the single root ExprNode not consumed yet
        statementSpec.setHavingExprRootNode(astExprNodeMap.values().iterator().next());
        astExprNodeMap.clear();
    }

    private void leaveOutputLimit(AST node) throws ASTWalkException
    {
        log.debug(".leaveOutputLimit");

        statementSpec.setOutputLimitSpec(ASTOutputLimitHelper.buildSpec(node));
    }

    private void leaveOuterJoin(AST node)
    {
        log.debug(".leaveOuterJoin");

        OuterJoinType joinType;
        switch (node.getType())
        {
            case LEFT_OUTERJOIN_EXPR:
                joinType = OuterJoinType.LEFT;
                break;
            case RIGHT_OUTERJOIN_EXPR:
                joinType = OuterJoinType.RIGHT;
                break;
            case FULL_OUTERJOIN_EXPR:
                joinType = OuterJoinType.FULL;
                break;
            default:
                throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized outer join node type");
        }

        // get subnodes representing the expression
        ExprIdentNode left = (ExprIdentNode) astExprNodeMap.get(node.getFirstChild());
        ExprIdentNode right = (ExprIdentNode) astExprNodeMap.get(node.getFirstChild().getNextSibling());

        // remove from AST-to-expression node map
        astExprNodeMap.remove(node.getFirstChild());
        astExprNodeMap.remove(node.getFirstChild().getNextSibling());

        OuterJoinDesc outerJoinDesc = new OuterJoinDesc(joinType, left, right);
        statementSpec.getOuterJoinDescList().add(outerJoinDesc);
    }

    private void leaveGroupBy(AST node)
    {
        log.debug(".leaveGroupBy");

        // there must be some expressions under the group by in our map
        if (astExprNodeMap.size() < 1)
        {
            throw new IllegalStateException("Group-by clause generated no expression nodes");
        }

        AST child = node.getFirstChild();

        // For each child to the group-by AST node there must be a generated ExprNode
        while (child != null)
        {
            // get top expression node for the child node
            ExprNode exprNode = astExprNodeMap.get(child);

            if (exprNode == null)
            {
                throw new IllegalStateException("Expression node as a result of group-by child node not found in collection");
            }

            statementSpec.getGroupByExpressions().add(exprNode);
            child = child.getNextSibling();
        }

        // Clear the map - all expression node should be gone
        astExprNodeMap.clear();
    }

    private void leaveInsertInto(AST node)
    {
        log.debug(".leaveInsertInto");

        AST child = node.getFirstChild();

        // istream or rstream
        boolean isIStream = true;
        if (child.getType() == RSTREAM)
        {
            isIStream = false;
            child = child.getNextSibling();
        }
        if (child.getType() == ISTREAM)
        {
            child = child.getNextSibling();
        }

        // alias
        String eventAliasName = child.getText();
        InsertIntoDesc insertIntoDesc = new InsertIntoDesc(isIStream, eventAliasName);

        // optional columns
        child = child.getNextSibling();
        if ((child != null) && (child.getType() == INSERTINTO_EXPRCOL))
        {
            // Each child to the insert-into AST node represents a column name
            child = child.getFirstChild();
            while (child != null)
            {
                insertIntoDesc.add(child.getText());
                child = child.getNextSibling();
            }
        }

        statementSpec.setInsertIntoDesc(insertIntoDesc);
    }

    private void leaveOrderByElement(AST node) throws ASTWalkException
    {
        log.debug(".leaveOrderByElement");
        if ((astExprNodeMap.size() > 1) || ((astExprNodeMap.size() == 0)))
        {
            throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child element for root");
        }

        // Get expression node sub-tree from the AST nodes placed so far
        ExprNode exprNode = astExprNodeMap.values().iterator().next();
        astExprNodeMap.clear();

        // Get optional ascending or descending qualifier
        boolean descending = false;
        if (node.getFirstChild().getNextSibling() != null)
        {
            descending = node.getFirstChild().getNextSibling().getType() == DESC;
        }

        // Add as order-by element
        statementSpec.getOrderByList().add(new Pair<ExprNode, Boolean>(exprNode, descending));
    }

    private void leaveConcat(AST node)
    {
        ExprConcatNode concatNode = new ExprConcatNode();
        astExprNodeMap.put(node, concatNode);
    }

    private void leaveEvery(AST node)
    {
        log.debug(".leaveEvery");
        EvalEveryNode everyNode = new EvalEveryNode();
        astPatternNodeMap.put(node, everyNode);
    }

    private void leaveFilter(AST node)
    {
        log.debug(".leaveFilter");

        if (isProcessingPattern)
        {
            FilterSpec spec = ASTFilterSpecHelper.buildSpec(node, taggedEventTypes, eventAdapterService);
            String optionalTag = ASTFilterSpecHelper.getEventNameTag(node);
            EvalFilterNode filterNode = new EvalFilterNode(spec, optionalTag);
            EventType eventType = spec.getEventType();

            if (optionalTag != null)
            {
                EventType existingType = taggedEventTypes.get(optionalTag);
                if ((existingType != null) && (existingType != eventType))
                {
                    throw new IllegalArgumentException("Tag '" + optionalTag + "' for event type " + eventType.getUnderlyingType().getName() +
                            " has already been used for events of type " + existingType.getUnderlyingType().getName());
                }
                taggedEventTypes.put(optionalTag, eventType);
            }

            astPatternNodeMap.put(node, filterNode);
        }
        else
        {
            filterSpec = ASTFilterSpecHelper.buildSpec(node, null, eventAdapterService);

            // clear the sub-nodes for the filter since the event property expressions have been processed
            // by building the spec
            astExprNodeMap.clear();
        }
    }

    private void leaveFollowedBy(AST node)
    {
        log.debug(".leaveFollowedBy");
        EvalFollowedByNode fbNode = new EvalFollowedByNode();
        astPatternNodeMap.put(node, fbNode);
    }

    private void leaveAnd(AST node)
    {
        log.debug(".leaveAnd");
        EvalAndNode andNode = new EvalAndNode();
        astPatternNodeMap.put(node, andNode);
    }

    private void leaveOr(AST node)
    {
        log.debug(".leaveOr");
        EvalOrNode orNode = new EvalOrNode();
        astPatternNodeMap.put(node, orNode);
    }

    private void leaveIn(AST node)
    {
        log.debug(".leaveIn");

        ExprInNode inNode = new ExprInNode(node.getType() == NOT_IN_SET);
        astExprNodeMap.put(node, inNode);
    }

    private void leaveBetween(AST node)
    {
        log.debug(".leaveBetween");

        ExprBetweenNode betweenNode = new ExprBetweenNode(node.getType() == NOT_BETWEEN);
        astExprNodeMap.put(node, betweenNode);
    }

    private void leaveLike(AST node)
    {
        log.debug(".leaveLike");

        boolean isNot = node.getType() == NOT_LIKE;
        ExprLikeNode likeNode = new ExprLikeNode(isNot);
        astExprNodeMap.put(node, likeNode);
    }

    private void leaveRegexp(AST node)
    {
        log.debug(".leaveRegexp");

        boolean isNot = node.getType() == NOT_REGEXP;
        ExprRegexpNode regExpNode = new ExprRegexpNode(isNot);
        astExprNodeMap.put(node, regExpNode);
    }

    private void leaveNot(AST node)
    {
        log.debug(".leaveNot");

        if (isProcessingPattern)
        {
            EvalNotNode notNode = new EvalNotNode();
            astPatternNodeMap.put(node, notNode);
        }
        else
        {
            ExprNotNode notNode = new ExprNotNode();
            astExprNodeMap.put(node, notNode);
        }
    }

    private void leaveGuard(AST node) throws ASTWalkException
    {
        log.debug(".leaveGuard");

        // Get the object information from AST
        AST startGuard = node.getFirstChild().getNextSibling();
        String objectNamespace = startGuard.getText();
        String objectName = startGuard.getNextSibling().getText();

        List<Object> objectParams = new LinkedList<Object>();

        AST child = startGuard.getNextSibling().getNextSibling();
        while (child != null)
        {
            Object object = ASTParameterHelper.makeParameter(child);
            objectParams.add(object);
            child = child.getNextSibling();
        }

        // From object name construct guard factory
        GuardEnum guardEnum = GuardEnum.forName(objectNamespace, objectName);
        if (guardEnum == null)
        {
            throw new ASTWalkException("Guard in namespace " + objectNamespace + " and name " + objectName +
                    " is not a known guard");
        }

        GuardFactory guardFactory = null;
        try
        {
            guardFactory = (GuardFactory) ConstructorUtils.invokeConstructor(guardEnum.getClazz(), objectParams.toArray());

            if (log.isDebugEnabled())
            {
                log.debug(".leaveGuard Successfully instantiated guard");
            }
        }
        catch (Exception e)
        {
            String message = "Error invoking constructor for guard '" + objectName;
            message += "', invalid parameter list for the object";
            log.fatal(".leaveGuard " + message, e);
            throw new ASTWalkException(message);
        }

        EvalGuardNode guardNode = new EvalGuardNode(guardFactory);
        astPatternNodeMap.put(node, guardNode);
    }

    private void leaveCaseNode(AST node, boolean inCase2)
    {
        log.debug(".leaveCase2Node inCase2=" + inCase2);

        if (astExprNodeMap.size() == 0)
        {
            throw new ASTWalkException("Unexpected AST tree contains zero child element for case node");
        }
        AST childNode = node.getFirstChild();
        if ((astExprNodeMap.size() == 1) && (childNode.getType() != WHEN))
        {
            throw new ASTWalkException("AST tree doesn not contain at least when node for case node");
        }

        ExprCaseNode caseNode = new ExprCaseNode(inCase2);
        astExprNodeMap.put(node, caseNode);
    }

    private void leaveObserver(AST node) throws ASTWalkException
    {
        log.debug(".leaveObserver");

        // Get the object information from AST
        String objectNamespace = node.getFirstChild().getText();
        String objectName = node.getFirstChild().getNextSibling().getText();

        int numNodes = node.getNumberOfChildren();
        Object[] observerParameters = new Object[numNodes - 2];

        AST child = node.getFirstChild().getNextSibling().getNextSibling();
        int index = 0;
        while (child != null)
        {
            Object object = ASTParameterHelper.makeParameter(child);
            observerParameters[index++] = object;
            child = child.getNextSibling();
        }

        // From object name construct observer factory
        ObserverEnum observerEnum = ObserverEnum.forName(objectNamespace, objectName);
        if (observerEnum == null)
        {
            throw new ASTWalkException("EventObserver in namespace " + objectNamespace + " and name " + objectName +
                    " is not a known observer");
        }

        ObserverFactory observerFactory = null;
        try
        {
            Object obsFactory = ConstructorHelper.invokeConstructor(observerEnum.getClazz(), observerParameters);
            observerFactory = (ObserverFactory) obsFactory;

            if (log.isDebugEnabled())
            {
                log.debug(".create Successfully instantiated observer");
            }
        }
        catch (Exception e)
        {
            String message = "Error invoking constructor for observer '" + objectNamespace + ":" + objectName;
            message += "', invalid parameter list for the object";
            log.fatal(".leaveObserver " + message, e);
            throw new ASTWalkException(message);
        }

        EvalObserverNode observerNode = new EvalObserverNode(observerFactory);
        astPatternNodeMap.put(node, observerNode);
    }

    private void leaveSelectClause(AST node)
    {
        log.debug(".leaveSelectClause");

        int nodeType = node.getFirstChild().getType();
        if (nodeType == RSTREAM)
        {
            statementSpec.setSelectStreamDirEnum(SelectClauseStreamSelectorEnum.RSTREAM_ONLY);
        }
        if (nodeType == ISTREAM)
        {
            statementSpec.setSelectStreamDirEnum(SelectClauseStreamSelectorEnum.ISTREAM_ONLY);
        }
    }

    private static final Log log = LogFactory.getLog(EQLTreeWalker.class);
}
