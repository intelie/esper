package net.esper.eql.parse;

import net.esper.filter.FilterSpec;
import net.esper.view.ViewSpec;
import net.esper.eql.generated.EQLBaseWalker;
import net.esper.eql.expression.*;
import net.esper.type.*;
import net.esper.collection.Pair;
import net.esper.event.EventAdapterService;
import antlr.ASTFactory;
import antlr.collections.AST;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Called during the walks of a EQL expression AST tree as specified in the grammar file.
 * Constructs filter and view specifications etc.
 */
public class EQLTreeWalker extends EQLBaseWalker
{
    private Map<AST, ExprNode> astNodeMap;
    private EventAdapterService eventAdapterService;

    private List<ViewSpec> viewSpecs = new LinkedList<ViewSpec>();
    private OutputLimitSpec outputLimitSpec;
    private FilterSpec filterSpec;
    private List<StreamSpec> streamSpecs = new LinkedList<StreamSpec>();
    private List<Pair<ExprNode, String>> selectListExpressions = new LinkedList<Pair<ExprNode, String>>();
    private List<ExprNode> groupByExpressions = new LinkedList<ExprNode>();
    private ExprNode filterExprRootNode;
    private ExprNode havingExprRootNode;
    private List<OuterJoinDesc> outerJoinDescList = new LinkedList<OuterJoinDesc>();
    private InsertIntoDesc insertIntoDesc;
    private List<Pair<ExprNode, Boolean>> orderByList = new LinkedList<Pair<ExprNode, Boolean>>();

    /**
     * Ctor.
     * @param eventAdapterService for resolving event names
     */
    public EQLTreeWalker(EventAdapterService eventAdapterService)
    {
        this.eventAdapterService = eventAdapterService;
        astNodeMap = new HashMap<AST, ExprNode>();
    }

    /**
     * Returns the FROM-clause stream definitions.
     * @return list of stream specifications
     */
    public List<StreamSpec> getStreamSpecs()
    {
        return streamSpecs;
    }

    /**
     * Returns SELECT-clause list of expressions.
     * @return list of expressions and optional name
     */
    public List<Pair<ExprNode, String>> getSelectListExpressions()
    {
        return selectListExpressions;
    }

    /**
     * Returns the WHERE-clause root node of filter expression.
     * @return filter expression root node
     */
    public ExprNode getFilterRootNode()
    {
        return filterExprRootNode;
    }

    /**
     * Returns the LEFT/RIGHT/FULL OUTER JOIN-type and property name descriptor, if applicable. Returns null if regular join.
     * @return outer join type, stream names and property names
     */
    public List<OuterJoinDesc> getOuterJoinDescList()
    {
        return outerJoinDescList;
    }

    /**
     * Returns list of group-by expressions.
     * @return group-by expression nodes as specified in group-by clause
     */
    public List<ExprNode> getGroupByExpressions()
    {
        return groupByExpressions;
    }

    /**
     * Returns expression root node representing the having-clause, if present, or null if no having clause was supplied.
     * @return having-clause expression top node
     */
    public ExprNode getHavingExprRootNode()
    {
        return havingExprRootNode;
    }

    /**
     * Returns the output limit definition, if any.
     * @return output limit spec
     */
    public OutputLimitSpec getOutputLimitSpec()
    {
        return outputLimitSpec;
    }

    /**
     * Return a descriptor with the insert-into event name and optional list of columns.
     * @return insert into specification
     */
    public InsertIntoDesc getInsertIntoDesc()
    {
        return insertIntoDesc;
    }

    /**
     * Returns the list of order-by expression as specified in the ORDER BY clause.
     * @return Returns the orderByList.
     */
    public List<Pair<ExprNode, Boolean>> getOrderByList() {
        return orderByList;
    }

    protected void leaveNode(AST node) throws ASTWalkException
    {
        if (log.isDebugEnabled())
        {
            log.debug(".leaveNode " + node);
        }

        switch (node.getType())
        {
            case SELECTION_EXPR:
                return;
            case SELECTION_ELEMENT_EXPR:
                leaveSelectionElement(node);
                break;
            case EVENT_FILTER_EXPR:
                leaveFilter(node);
                break;
            case VIEW_EXPR:
                leaveView(node);
                break;
            case STREAM_EXPR:
                leaveStream(node);
                break;
            case EVENT_PROP_EXPR:
                leaveEventPropertyExpr(node);
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
            case MAX:
            case MIN:
                leaveMinMax(node);
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
            default:
                throw new ASTWalkException("Unhandled node type encountered, type '" + node.getType() +
                        "' with text '" + node.getText() + "'");
        }

        // For each AST child node of this AST node that generated an ExprNode add the child node to the expression node.
        // This is for automatic expression tree building.
        ExprNode thisEvalNode = astNodeMap.get(node);

        // Loop over all child nodes for this node.
        AST childNode = node.getFirstChild();
        do {
            ExprNode childEvalNode = astNodeMap.get(childNode);
            // If there was an expression node generated for the child node, and there is a current expression node,
            // add it to the current expression node (thisEvalNode)
            if ((childEvalNode != null) && (thisEvalNode != null))
            {
                thisEvalNode.addChildNode(childEvalNode);
                astNodeMap.remove(childNode);
            }
            // Next child node
            if (childNode != null)
            {
                childNode = childNode.getNextSibling();
            }
        }
        while (childNode != null);
    }

	protected void end() throws ASTWalkException
    {
        if (astNodeMap.size() > 1)
        {
            throw new ASTWalkException("Unexpected AST tree contains left over child elements," +
                    " not all expression nodes have been removed from AST-to-expression nodes map");
        }
    }

    private void leaveSelectionElement(AST node) throws ASTWalkException
    {
        log.debug(".leaveSelectionElement");
        if ((astNodeMap.size() > 1) || ((astNodeMap.size() == 0)))
        {
            throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child element for root");
        }

        // Get expression node sub-tree from the AST nodes placed so far
        ExprNode exprNode = astNodeMap.values().iterator().next();
        astNodeMap.clear();

        // Get list element name
        String optionalName = null;
        if (node.getFirstChild().getNextSibling() != null)
        {
            optionalName = node.getFirstChild().getNextSibling().getText();
        }

        // Add as selection element
        selectListExpressions.add(new Pair<ExprNode, String>(exprNode, optionalName));
    }

    private void leaveView(AST node) throws ASTWalkException
    {
        log.debug(".leaveView");
        ViewSpec spec = ASTViewSpecHelper.buildSpec(node);
        viewSpecs.add(spec);
    }

    private void leaveFilter(AST node)
    {
        log.debug(".leaveFilter");

        filterSpec = ASTFilterSpecHelper.buildSpec(node, null, eventAdapterService);

        // clear the sub-nodes for the filter since the event property expressions have been processed
        // by building the spec
        astNodeMap.clear();
    }

    private void leaveStream(AST node)
    {
        log.debug(".leaveStream");

        // The stream name node is an identifier
        AST streamNameNode = node.getFirstChild().getNextSibling();
        while ((streamNameNode != null) && (streamNameNode.getType() != IDENT))
        {
            streamNameNode = streamNameNode.getNextSibling();
        }

        // Stream name is optional
        String streamName = null;
        if (streamNameNode != null)
        {
            streamName = streamNameNode.getText();
        }

        StreamSpec streamSpec = new StreamSpec(filterSpec, viewSpecs, streamName);
        viewSpecs = new LinkedList<ViewSpec>();
        streamSpecs.add(streamSpec);
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

        astNodeMap.put(node, identNode);
    }

    private void leaveLibFunction(AST node) 
    {
    	log.debug(".leaveLibFunction");
    	
    	if(node.getNumberOfChildren() < 2)
    	{
    		throw new IllegalArgumentException("Illegal method invocation");
    	}
    	
    	AST itor = node.getFirstChild();
    	String className = itor.getText();

    	itor = itor.getNextSibling();
    	String methodName = itor.getText();
    	
    	astNodeMap.put(node, new ExprStaticMethodNode(className, methodName));
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
        astNodeMap.put(node, identNode);
    }

    private void leaveJoinAndExpr(AST node)
    {
        log.debug(".leaveJoinAndExpr");
        ExprAndNode identNode = new ExprAndNode();
        astNodeMap.put(node, identNode);
    }

    private void leaveJoinOrExpr(AST node)
    {
        log.debug(".leaveJoinOrExpr");
        ExprOrNode identNode = new ExprOrNode();
        astNodeMap.put(node, identNode);
    }

    private void leaveConstant(AST node)
    {
        log.debug(".leaveConstant");
        ExprConstantNode constantNode = new ExprConstantNode(ASTConstantHelper.parse(node));
        astNodeMap.put(node, constantNode);
    }

    private void leaveMath(AST node)
    {
        log.debug(".leaveMath");

        ArithTypeEnum arithTypeEnum;

        switch (node.getType())
        {
            case DIV :
                arithTypeEnum = ArithTypeEnum.DIVIDE;
                break;
            case STAR :
                arithTypeEnum = ArithTypeEnum.MULTIPLY;
                break;
            case PLUS :
                arithTypeEnum = ArithTypeEnum.ADD;
                break;
            case MINUS :
                arithTypeEnum = ArithTypeEnum.SUBTRACT;
                break;
            case MOD :
                arithTypeEnum = ArithTypeEnum.MODULO;
                break;
            default :
                throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized math node type");
        }

        ExprMathNode mathNode = new ExprMathNode(arithTypeEnum);
        astNodeMap.put(node, mathNode);
    }

    // Min/Max nodes can be either an aggregate or a per-row function depending on the number or arguments
    private void leaveMinMax(AST node)
    {
        log.debug(".leaveMinMax");

        MinMaxTypeEnum minMaxTypeEnum;

        switch (node.getType())
        {
            case MIN :
                minMaxTypeEnum = MinMaxTypeEnum.MIN;
                break;
            case MAX :
                minMaxTypeEnum = MinMaxTypeEnum.MAX;
                break;
            default :
                throw new IllegalArgumentException("Node type " + node.getType() + " not a recognized min max node type");
        }

        boolean isDistinct = false;
        if (node.getFirstChild().getType() == DISTINCT)
        {
            isDistinct = true;
        }

        if ((node.getNumberOfChildren() > 2) && (isDistinct))
        {
            throw new ASTWalkException("The distinct keyword is not valid in per-row min and max " +
                    "functions with multiple sub-expressions");
        }

        ExprNode minMaxNode;
        if ((!isDistinct) && (node.getNumberOfChildren() > 1))
        {
            minMaxNode = new ExprMinMaxRowNode(minMaxTypeEnum);
        }
        else
        {
            minMaxNode = new ExprMinMaxAggrNode(true, minMaxTypeEnum);
        }
        astNodeMap.put(node, minMaxNode);
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

        astNodeMap.put(node, aggregateNode);
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
        astNodeMap.put(node, mathNode);
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
	    astNodeMap.put(node, bwNode);       
    }

    private void leaveWhereClause()
    {
        log.debug(".leaveWhereClause");

        if (astNodeMap.size() != 1)
        {
            throw new IllegalStateException("Where clause generated zero or more then one expression nodes");
        }

        // Just assign the single root ExprNode not consumed yet
        filterExprRootNode = astNodeMap.values().iterator().next();
        astNodeMap.clear();
    }

    private void leaveHavingClause()
    {
        log.debug(".leaveHavingClause");

        if (astNodeMap.size() != 1)
        {
            throw new IllegalStateException("Having clause generated zero or more then one expression nodes");
        }

        // Just assign the single root ExprNode not consumed yet
        havingExprRootNode = astNodeMap.values().iterator().next();
        astNodeMap.clear();
    }

    private void leaveOutputLimit(AST node) throws ASTWalkException
    {
        log.debug(".leaveOutputLimit");

        outputLimitSpec = ASTOutputLimitHelper.buildSpec(node);
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
        ExprIdentNode left = (ExprIdentNode) astNodeMap.get(node.getFirstChild());
        ExprIdentNode right = (ExprIdentNode) astNodeMap.get(node.getFirstChild().getNextSibling());

        // remove from AST-to-expression node map
        astNodeMap.remove(node.getFirstChild());
        astNodeMap.remove(node.getFirstChild().getNextSibling());

        OuterJoinDesc outerJoinDesc = new OuterJoinDesc(joinType, left, right);
        outerJoinDescList.add(outerJoinDesc);
    }

    private void leaveGroupBy(AST node)
    {
        log.debug(".leaveGroupBy");

        // there must be some expressions under the group by in our map
        if (astNodeMap.size() < 1)
        {
            throw new IllegalStateException("Group-by clause generated no expression nodes");
        }

        AST child = node.getFirstChild();

        // For each child to the group-by AST node there must be a generated ExprNode
        while (child != null)
        {
            // get top expression node for the child node
            ExprNode exprNode = astNodeMap.get(child);

            if (exprNode == null)
            {
                throw new IllegalStateException("Expression node as a result of group-by child node not found in collection");
            }

            groupByExpressions.add(exprNode);
            child = child.getNextSibling();
        }

        // Clear the map - all expression node should be gone
        astNodeMap.clear();
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
        insertIntoDesc = new InsertIntoDesc(isIStream, eventAliasName);

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
    }

    private void leaveOrderByElement(AST node) throws ASTWalkException
    {
        log.debug(".leaveOrderByElement");
        if ((astNodeMap.size() > 1) || ((astNodeMap.size() == 0)))
        {
            throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child element for root");
        }

        // Get expression node sub-tree from the AST nodes placed so far
        ExprNode exprNode = astNodeMap.values().iterator().next();
        astNodeMap.clear();

        // Get optional ascending or descending qualifier
        boolean descending = false;
        if (node.getFirstChild().getNextSibling() != null)
        {
            descending = node.getFirstChild().getNextSibling().getType() == DESC;
        }

        // Add as order-by element
        orderByList.add(new Pair<ExprNode, Boolean>(exprNode, descending));
    }

    private void leaveNot(AST node)
    {
        ExprNotNode notNode = new ExprNotNode();
        astNodeMap.put(node, notNode);
    }

    private void leaveConcat(AST node)
    {
        ExprConcatNode concatNode = new ExprConcatNode();
        astNodeMap.put(node, concatNode);
    }

    private static final Log log = LogFactory.getLog(EQLTreeWalker.class);
}
