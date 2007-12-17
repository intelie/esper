/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import antlr.SemanticException;
import antlr.collections.AST;
import net.esper.collection.UniformPair;
import net.esper.eql.agg.AggregationSupport;
import net.esper.eql.core.EngineImportException;
import net.esper.eql.core.EngineImportService;
import net.esper.eql.core.EngineImportUndefinedException;
import net.esper.eql.expression.*;
import net.esper.eql.generated.EQLBaseWalker;
import net.esper.eql.spec.*;
import net.esper.eql.variable.VariableService;
import net.esper.pattern.*;
import net.esper.type.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Called during the walks of a EQL expression AST tree as specified in the grammar file.
 * Constructs filter and view specifications etc.
 */
public class EQLTreeWalker extends EQLBaseWalker
{
    // private holding areas for accumulated info
    private Map<AST, ExprNode> astExprNodeMap = new HashMap<AST, ExprNode>();
    private final Stack<Map<AST, ExprNode>> astExprNodeMapStack;

    private final Map<AST, EvalNode> astPatternNodeMap = new HashMap<AST, EvalNode>();

    private FilterSpecRaw filterSpec;
    private final List<ViewSpec> viewSpecs = new LinkedList<ViewSpec>();

    // Pattern indicator dictates behavior for some AST nodes
    private boolean isProcessingPattern;

    // AST Walk result
    private List<ExprSubstitutionNode> substitutionParamNodes = new ArrayList<ExprSubstitutionNode>(); 
    private StatementSpecRaw statementSpec;
    private final Stack<StatementSpecRaw> statementSpecStack;

    private final EngineImportService engineImportService;
    private final VariableService variableService;

    /**
     * Ctor.
     * @param engineImportService is required to resolve lib-calls into static methods or configured aggregation functions
     * @param variableService for variable access
     */
    public EQLTreeWalker(EngineImportService engineImportService, VariableService variableService)
    {
        this.engineImportService = engineImportService;
        this.variableService = variableService;
        
        statementSpec = new StatementSpecRaw();
        statementSpecStack = new Stack<StatementSpecRaw>();
        astExprNodeMapStack = new Stack<Map<AST, ExprNode>>();
    }

    /**
     * Pushes a statement into the stack, creating a new empty statement to fill in.
     * The leave node method for lookup statements pops from the stack.
     * @throws SemanticException is a standard parser exception
     */
    protected void pushStmtContext() throws SemanticException {
        if (log.isDebugEnabled())
        {
            log.debug(".pushStmtContext");
        }
        statementSpecStack.push(statementSpec);
        astExprNodeMapStack.push(astExprNodeMap);

        statementSpec = new StatementSpecRaw();
        astExprNodeMap = new HashMap<AST, ExprNode>();
    }

    /**
     * Returns statement specification.
     * @return statement spec.
     */
    public StatementSpecRaw getStatementSpec()
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
     * @throws ASTWalkException if the node tree walk operation failed
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
            case WILDCARD_SELECT:
            	leaveWildcardSelect();
            	break;
            case SELECTION_ELEMENT_EXPR:
                leaveSelectionElement(node);
                break;
            case SELECTION_STREAM:
                leaveSelectionStream(node);
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
                leaveEqualsExpr(node);
                break;
            case WHERE_EXPR:
                leaveWhereClause();
                break;
            case NUM_INT:
            case INT_TYPE:
            case LONG_TYPE:
            case BOOL_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case NULL_TYPE:
                leaveConstant(node);
                break;
            case SUBSTITUTION:
                leaveSubstitution(node);
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
                leaveInSet(node);
                break;
            case IN_RANGE:
            case NOT_IN_RANGE:
                leaveInRange(node);
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
            case PREVIOUS:
                leavePrevious(node);
                break;
            case PRIOR:
                leavePrior(node);
                break;
            case ARRAY_EXPR:
                leaveArray(node);
                break;
            case SUBSELECT_EXPR:
                leaveSubselectRow(node);
                break;
            case EXISTS_SUBSELECT_EXPR:
                leaveSubselectExists(node);
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                leaveSubselectIn(node);
                break;
            case IN_SUBSELECT_QUERY_EXPR:
                leaveSubselectQueryIn(node);
                break;
            case INSTANCEOF:
                leaveInstanceOf(node);
                break;
            case EXISTS:
                leaveExists(node);
                break;
            case CAST:
                leaveCast(node);
                break;
            case CURRENT_TIMESTAMP:
                leaveTimestamp(node);
                break;
            case CREATE_WINDOW_EXPR:
                leaveCreateWindow(node);
                break;
            case CREATE_WINDOW_SELECT_EXPR:
                leaveCreateWindowSelect(node);
                break;
            case CREATE_VARIABLE_EXPR:
                leaveCreateVariable(node);
                break;
            case ON_EXPR:
                leaveOnExpr(node);
                break;
            default:
                throw new ASTWalkException("Unhandled node type encountered, type '" + node.getType() +
                        "' with text '" + node.getText() + '\'');
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

    private void leaveCreateWindow(AST node)
    {
        log.debug(".leaveCreateWindow");

        String windowName = node.getFirstChild().getText();

        String eventName = null;
        AST child = node.getFirstChild().getNextSibling();
        while (child != null)
        {
            if (child.getType() == CLASS_IDENT) // the event type
            {
                eventName = child.getText();
            }
            child = child.getNextSibling();
        }
        if (eventName == null)
        {
            throw new ASTWalkException("Event type AST not found");
        }

        CreateWindowDesc desc = new CreateWindowDesc(windowName, viewSpecs);
        statementSpec.setCreateWindowDesc(desc);

        FilterSpecRaw rawFilterSpec = new FilterSpecRaw(eventName, new LinkedList<ExprNode>());
        FilterStreamSpecRaw streamSpec = new FilterStreamSpecRaw(rawFilterSpec, new LinkedList<ViewSpec>(), null);
        statementSpec.getStreamSpecs().add(streamSpec);
    }

    private void leaveCreateVariable(AST node)
    {
        log.debug(".leaveCreateVariable");

        AST child = node.getFirstChild();
        String variableType = child.getText();
        child = child.getNextSibling();
        String variableName = child.getText();

        ExprNode assignment = null;
        child = child.getNextSibling();
        if (child != null)
        {
            assignment = astExprNodeMap.get(child);
            astExprNodeMap.remove(child);
        }

        CreateVariableDesc desc = new CreateVariableDesc(variableType, variableName, assignment);
        statementSpec.setCreateVariableDesc(desc);
    }

    private void leaveCreateWindowSelect(AST node)
    {
        log.debug(".leaveCreateWindowSelect");
    }

    private void leaveOnExpr(AST node)
    {
        log.debug(".leaveOnExpr");

        // determine on-delete or on-select
        AST typeChildNode = node.getFirstChild();
        boolean isOnDelete = false;
        while(typeChildNode != null)
        {
            if (typeChildNode.getType() == ON_DELETE_EXPR)
            {
                isOnDelete = true;
                break;
            }
            if (typeChildNode.getType() == ON_SELECT_EXPR)
            {
                break;
            }
            if (typeChildNode.getType() == ON_SET_EXPR)
            {
                break;
            }
            typeChildNode = typeChildNode.getNextSibling();
        }
        if (typeChildNode == null)
        {
            throw new IllegalStateException("Could not determine on-expr type");
        }

        // get optional filter stream as-name
        AST childNode = node.getFirstChild().getNextSibling();
        String streamAsName = null;
        if (childNode.getType() == IDENT)
        {
            streamAsName = childNode.getText();
        }

        // get stream to use (pattern or filter)
        StreamSpecRaw streamSpec;
        if (node.getFirstChild().getType() == EVENT_FILTER_EXPR)
        {
            streamSpec = new FilterStreamSpecRaw(filterSpec, new ArrayList<ViewSpec>(), streamAsName);
        }
        else if (node.getFirstChild().getType() == PATTERN_INCL_EXPR)
        {
            if ((astPatternNodeMap.size() > 1) || ((astPatternNodeMap.isEmpty())))
            {
                throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child elements for root");
            }
            // Get expression node sub-tree from the AST nodes placed so far
            EvalNode evalNode = astPatternNodeMap.values().iterator().next();
            streamSpec = new PatternStreamSpecRaw(evalNode, viewSpecs, streamAsName);
            astPatternNodeMap.clear();
        }
        else
        {
            throw new IllegalStateException("Invalid AST type node, cannot map to stream specification");
        }

        if (typeChildNode.getType() != ON_SET_EXPR)
        {
            // The ON_EXPR_FROM contains the window name
            UniformPair<String> windowName = getWindowName(typeChildNode);
            statementSpec.setOnTriggerDesc(new OnTriggerWindowDesc(windowName.getFirst(), windowName.getSecond(), isOnDelete));
        }
        else
        {
            OnTriggerSetDesc setDesc = getOnTriggerSet(typeChildNode);
            statementSpec.setOnTriggerDesc(setDesc);
        }
        statementSpec.getStreamSpecs().add(streamSpec);
    }

    private OnTriggerSetDesc getOnTriggerSet(AST typeChildNode)
    {
        OnTriggerSetDesc desc = new OnTriggerSetDesc();

        AST child = typeChildNode.getFirstChild();
        do
        {
            // get variable name
            if (child.getType() != IDENT)
            {
                throw new IllegalStateException("Expected identifier but received type '" + child.getType() + "'");
            }
            String variableName = child.getText();

            // get expression
            child = child.getNextSibling();
            ExprNode childEvalNode = astExprNodeMap.get(child);
            astExprNodeMap.remove(child);

            desc.addAssignment(new OnTriggerSetAssignment(variableName, childEvalNode));
            child = child.getNextSibling();
        }
        while (child != null);

        return desc;
    }

    private UniformPair<String> getWindowName(AST typeChildNode)
    {
        String windowName = null;
        String windowStreamName = null;

        AST child = typeChildNode.getFirstChild();
        while(child != null)
        {
            if (child.getType() == ON_EXPR_FROM)
            {
                windowName = child.getFirstChild().getText();
                if (child.getFirstChild().getNextSibling() != null)
                {
                    windowStreamName = child.getFirstChild().getNextSibling().getText();
                }
                break;
            }
            child = child.getNextSibling();
        }
        if (windowName == null)
        {
            throw new IllegalStateException("Could not determine on-expr from-clause named window name");
        }
        return new UniformPair<String>(windowName, windowStreamName);
    }


    private void leavePrevious(AST node)
    {
        log.debug(".leavePrevious");

        ExprPreviousNode previousNode = new ExprPreviousNode();
        astExprNodeMap.put(node, previousNode);
    }

    private void leavePrior(AST node)
    {
        log.debug(".leavePrior");

        ExprPriorNode priorNode = new ExprPriorNode();
        astExprNodeMap.put(node, priorNode);
    }

    private void leaveInstanceOf(AST node)
    {
        log.debug(".leaveInstanceOf");

        AST classIdent = node.getFirstChild().getNextSibling();

        // get class identifiers
        List<String> classes = new ArrayList<String>();
        while(classIdent != null)
        {
            classes.add(classIdent.getText());
            classIdent = classIdent.getNextSibling();
        }

        String idents[] = classes.toArray(new String[0]);
        ExprInstanceofNode instanceofNode = new ExprInstanceofNode(idents);
        astExprNodeMap.put(node, instanceofNode);
    }

    private void leaveExists(AST node)
    {
        log.debug(".leaveExists");

        ExprPropertyExistsNode instanceofNode = new ExprPropertyExistsNode();
        astExprNodeMap.put(node, instanceofNode);
    }

    private void leaveCast(AST node)
    {
        log.debug(".leaveCast");

        String classIdent = node.getFirstChild().getNextSibling().getText();
        ExprCastNode castNode = new ExprCastNode(classIdent);
        astExprNodeMap.put(node, castNode);
    }

    private void leaveTimestamp(AST node)
    {
        log.debug(".leaveTimestamp");

        ExprTimestampNode timeNode = new ExprTimestampNode();
        astExprNodeMap.put(node, timeNode);
    }

    private void leaveArray(AST node)
    {
        log.debug(".leaveArray");

        ExprArrayNode arrayNode = new ExprArrayNode();
        astExprNodeMap.put(node, arrayNode);
    }

    private void leaveSubselectRow(AST node)
    {
        log.debug(".leaveSubselectRow");
       
        StatementSpecRaw currentSpec = popStacks();
        ExprSubselectRowNode subselectNode = new ExprSubselectRowNode(currentSpec);
        astExprNodeMap.put(node, subselectNode);
    }

    private void leaveSubselectExists(AST node)
    {
        log.debug(".leaveSubselectExists");

        StatementSpecRaw currentSpec = popStacks();
        ExprSubselectNode subselectNode = new ExprSubselectExistsNode(currentSpec);
        astExprNodeMap.put(node, subselectNode);
    }

    private void leaveSubselectIn(AST node)
    {
        log.debug(".leaveSubselectIn");

        AST nodeEvalExpr = node.getFirstChild();
        AST nodeSubquery = nodeEvalExpr.getNextSibling();

        boolean isNot = false;
        if (node.getType() == NOT_IN_SUBSELECT_EXPR)
        {
            isNot = true;
        }
        
        ExprSubselectInNode subqueryNode = (ExprSubselectInNode) astExprNodeMap.remove(nodeSubquery);
        subqueryNode.setNotIn(isNot);

        astExprNodeMap.put(node, subqueryNode);
    }

    private void leaveSubselectQueryIn(AST node)
    {
        log.debug(".leaveSubselectQueryIn");

        StatementSpecRaw currentSpec = popStacks();
        ExprSubselectNode subselectNode = new ExprSubselectInNode(currentSpec);
        astExprNodeMap.put(node, subselectNode);
    }

    private StatementSpecRaw popStacks()
    {
        log.debug(".popStacks");

        StatementSpecRaw currentSpec = statementSpec;
        statementSpec = statementSpecStack.pop();

        if (currentSpec.isHasVariables())
        {
            statementSpec.setHasVariables(true);
        }

        astExprNodeMap = astExprNodeMapStack.pop();

        return currentSpec;
    }

    /**
     * End processing of the AST tree for stand-alone pattern expressions.
     * @throws ASTWalkException is the walk failed
     */
    protected void endPattern() throws ASTWalkException
    {
        log.debug(".endPattern");

        if ((astPatternNodeMap.size() > 1) || ((astPatternNodeMap.isEmpty())))
        {
            throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child elements for root");
        }

        // Get expression node sub-tree from the AST nodes placed so far
        EvalNode evalNode = astPatternNodeMap.values().iterator().next();

        PatternStreamSpecRaw streamSpec = new PatternStreamSpecRaw(evalNode, new LinkedList<ViewSpec>(), null);
        statementSpec.getStreamSpecs().add(streamSpec);
        statementSpec.setExistsSubstitutionParameters(substitutionParamNodes.size() > 0);

        astPatternNodeMap.clear();
    }

    /**
     * End processing of the AST tree, check that expression nodes found their homes.
     * @throws ASTWalkException is the walk failed
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

        statementSpec.setExistsSubstitutionParameters(substitutionParamNodes.size() > 0);
    }

    private void leaveSelectionElement(AST node) throws ASTWalkException
    {
        log.debug(".leaveSelectionElement");

        if ((astExprNodeMap.size() > 1) || ((astExprNodeMap.isEmpty())))
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
        statementSpec.getSelectClauseSpec().add(new SelectExprElementRawSpec(exprNode, optionalName));
    }

    private void leaveSelectionStream(AST node) throws ASTWalkException
    {
        log.debug(".leaveSelectionStream");

        String streamName = node.getFirstChild().getText();

        // Get alias element name
        String optionalName = null;
        if (node.getFirstChild().getNextSibling() != null)
        {
            optionalName = node.getFirstChild().getNextSibling().getText();
        }

        // Add as selection element
        statementSpec.getSelectClauseSpec().add(new SelectExprElementStreamRawSpec(streamName, optionalName));
    }

    private void leaveWildcardSelect()
    {
    	log.debug(".leaveWildcardSelect");
    	statementSpec.getSelectClauseSpec().setIsUsingWildcard(true);
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
        StreamSpecRaw streamSpec;
        // If the first subnode is a filter node, we have a filter stream specification
        if (node.getFirstChild().getType() == EVENT_FILTER_EXPR)
        {
            streamSpec = new FilterStreamSpecRaw(filterSpec, viewSpecs, streamName);
        }
        else if (node.getFirstChild().getType() == PATTERN_INCL_EXPR)
        {
            if ((astPatternNodeMap.size() > 1) || ((astPatternNodeMap.isEmpty())))
            {
                throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child elements for root");
            }

            // Get expression node sub-tree from the AST nodes placed so far
            EvalNode evalNode = astPatternNodeMap.values().iterator().next();

            streamSpec = new PatternStreamSpecRaw(evalNode, viewSpecs, streamName);
            astPatternNodeMap.clear();
        }
        else if (node.getFirstChild().getType() == DATABASE_JOIN_EXPR)
        {
            AST dbchildNode = node.getFirstChild().getFirstChild();
            String dbName = dbchildNode.getText();
            String sqlWithParams = StringValue.parseString(dbchildNode.getNextSibling().getText().trim());

            String sampleSQL = null;
            if (dbchildNode.getNextSibling().getNextSibling() != null)
            {
                sampleSQL = dbchildNode.getNextSibling().getNextSibling().getText();
                sampleSQL = StringValue.parseString(sampleSQL.trim());
            }

            streamSpec = new DBStatementStreamSpec(streamName, viewSpecs, dbName, sqlWithParams, sampleSQL);
        }
        else if (node.getFirstChild().getType() == METHOD_JOIN_EXPR)
        {
            AST childNode = node.getFirstChild().getFirstChild();
            String prefixIdent = childNode.getText();
            childNode = childNode.getNextSibling();

            String className = childNode.getText();
            childNode = childNode.getNextSibling();

            int indexDot = className.lastIndexOf('.');
            String classNamePart;
            String methodNamePart;
            if (indexDot == -1)
            {
                classNamePart = className;
                methodNamePart = null;
            }
            else
            {
                classNamePart = className.substring(0, indexDot);
                methodNamePart = className.substring(indexDot + 1);
            }
            List<ExprNode> exprNodes = getExprNodes(childNode);

            streamSpec = new MethodStreamSpec(streamName, viewSpecs, prefixIdent, classNamePart, methodNamePart, exprNodes);
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

        ExprNode exprNode;
        String propertyName;

        // The stream name may precede the event property name, but cannot be told apart from the property name:
        //      s0.p1 could be a nested property, or could be stream 's0' and property 'p1'

        // A single entry means this must be the property name.
        // And a non-simple property means that it cannot be a stream name.
        if ((node.getNumberOfChildren() == 1) || (node.getFirstChild().getType() != EVENT_PROP_SIMPLE))
        {
            propertyName = ASTFilterSpecHelper.getPropertyName(node.getFirstChild());
            exprNode = new ExprIdentNode(propertyName);
        }
        // --> this is more then one child node, and the first child node is a simple property
        // we may have a stream name in the first simple property, or a nested property
        // i.e. 's0.p0' could mean that the event has a nested property to 's0' of name 'p0', or 's0' is the stream name
        else
        {
            String streamOrNestedPropertyName = node.getFirstChild().getFirstChild().getText();
            propertyName = ASTFilterSpecHelper.getPropertyName(node.getFirstChild().getNextSibling());
            exprNode = new ExprIdentNode(propertyName, streamOrNestedPropertyName);
        }

        if (variableService.getReader(propertyName) != null)
        {
            exprNode = new ExprVariableNode(propertyName);
            statementSpec.setHasVariables(true);
        }

        astExprNodeMap.put(node, exprNode);
    }

    private void leaveLibFunction(AST node)
    {
    	log.debug(".leaveLibFunction");

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
            return;
        }

        try
        {
            AggregationSupport aggregation = engineImportService.resolveAggregation(childNodeText);

            boolean isDistinct = false;
            if ((node.getFirstChild().getNextSibling() != null) && (node.getFirstChild().getNextSibling().getType() == DISTINCT))
            {
                isDistinct = true;
            }

            astExprNodeMap.put(node, new ExprPlugInAggFunctionNode(isDistinct, aggregation, childNodeText));
            return;
        }
        catch (EngineImportUndefinedException e)
        {
            // Not an aggretaion function
        }
        catch (EngineImportException e)
        {
            throw new IllegalStateException("Error resolving aggregation: " + e.getMessage(), e);            
        }

        throw new IllegalStateException("Unknown method named '" + childNodeText + "' could not be resolved");
    }

    private void leaveEqualsExpr(AST node)
    {
        log.debug(".leaveEqualsExpr");

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

    private void leaveSubstitution(AST node)
    {
        log.debug(".leaveSubstitution");

        // Add the substitution parameter node, for later replacement
        int currentSize = this.substitutionParamNodes.size();
        ExprSubstitutionNode substitutionNode = new ExprSubstitutionNode(currentSize + 1);
        substitutionParamNodes.add(substitutionNode);

        astExprNodeMap.put(node, substitutionNode);
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
            throw new IllegalArgumentException("Node type " + childNode.getType() + ' ' + childNode.getText() + " not a recognized min max node");
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

        ExprAggregateNode aggregateNode;

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
        statementSpec.setFilterRootNode(astExprNodeMap.values().iterator().next());
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

        OutputLimitSpec spec = ASTOutputLimitHelper.buildSpec(node);
        statementSpec.setOutputLimitSpec(spec);

        if (spec.getVariableName() != null)
        {
            statementSpec.setHasVariables(true);
        }
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

        // get optional additional
        AST child = node.getFirstChild().getNextSibling().getNextSibling();
        ExprIdentNode[] addLeftArr = null;
        ExprIdentNode[] addRightArr = null;
        if (child != null)
        {
            ArrayList<ExprIdentNode> addLeft = new ArrayList<ExprIdentNode>();
            ArrayList<ExprIdentNode> addRight = new ArrayList<ExprIdentNode>();
            while (child != null)
            {
                addLeft.add((ExprIdentNode)astExprNodeMap.remove(child));
                addRight.add((ExprIdentNode)astExprNodeMap.remove(child.getNextSibling()));
                child = child.getNextSibling().getNextSibling();
            }
            addLeftArr = addLeft.toArray(new ExprIdentNode[0]);
            addRightArr = addRight.toArray(new ExprIdentNode[0]);
        }

        OuterJoinDesc outerJoinDesc = new OuterJoinDesc(joinType, left, right, addLeftArr, addRightArr);
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
        if ((astExprNodeMap.size() > 1) || ((astExprNodeMap.isEmpty())))
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
        statementSpec.getOrderByList().add(new OrderByItem(exprNode, descending));
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

        AST startNode = node.getFirstChild();
        String optionalPatternTagName = null;
        if (startNode.getType() == EVENT_FILTER_NAME_TAG)
        {
            optionalPatternTagName = startNode.getText();
            startNode = startNode.getNextSibling();
        }

        // Determine event type
        String eventName = startNode.getText();

        AST currentNode = startNode.getNextSibling();
        List<ExprNode> exprNodes = getExprNodes(currentNode);

        FilterSpecRaw rawFilterSpec = new FilterSpecRaw(eventName, exprNodes);
        if (isProcessingPattern)
        {
            EvalFilterNode filterNode = new EvalFilterNode(rawFilterSpec, optionalPatternTagName);
            astPatternNodeMap.put(node, filterNode);
        }
        else
        {
            // for event streams we keep the filter spec around for use when the stream definition is completed
            filterSpec = rawFilterSpec;

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

    private void leaveInSet(AST node)
    {
        log.debug(".leaveInSet");

        ExprInNode inNode = new ExprInNode(node.getType() == NOT_IN_SET);
        astExprNodeMap.put(node, inNode);
    }

    private void leaveInRange(AST node)
    {
        log.debug(".leaveInRange");

        // The second node must be braces
        AST bracesNode = node.getFirstChild().getNextSibling();
        if ((bracesNode.getType() != LBRACK) && ((bracesNode.getType() != LPAREN)))
        {
            throw new IllegalStateException("Invalid in-range syntax, no braces but type '" + bracesNode.getType() + "'");
        }
        boolean isLowInclude = bracesNode.getType() == LBRACK;

        // The fifth node must be braces
        bracesNode = bracesNode.getNextSibling().getNextSibling().getNextSibling();
        if ((bracesNode.getType() != RBRACK) && ((bracesNode.getType() != RPAREN)))
        {
            throw new IllegalStateException("Invalid in-range syntax, no braces but type '" + bracesNode.getType() + "'");
        }
        boolean isHighInclude = bracesNode.getType() == RBRACK;

        ExprBetweenNode betweenNode = new ExprBetweenNode(isLowInclude, isHighInclude, node.getType() == NOT_IN_RANGE);
        astExprNodeMap.put(node, betweenNode);
    }

    private void leaveBetween(AST node)
    {
        log.debug(".leaveBetween");

        ExprBetweenNode betweenNode = new ExprBetweenNode(true, true, node.getType() == NOT_BETWEEN);
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

        PatternGuardSpec guardSpec = new PatternGuardSpec(objectNamespace, objectName, objectParams);
        EvalGuardNode guardNode = new EvalGuardNode(guardSpec);
        astPatternNodeMap.put(node, guardNode);
    }

    private void leaveCaseNode(AST node, boolean inCase2)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".leaveCase2Node inCase2=" + inCase2);
        }

        if (astExprNodeMap.isEmpty())
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

        List<Object> objectParams = new LinkedList<Object>();
        AST child = node.getFirstChild().getNextSibling().getNextSibling();
        while (child != null)
        {
            Object object = ASTParameterHelper.makeParameter(child);
            objectParams.add(object);
            child = child.getNextSibling();
        }

        PatternObserverSpec observerSpec = new PatternObserverSpec(objectNamespace, objectName, objectParams);
        EvalObserverNode observerNode = new EvalObserverNode(observerSpec);
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

    private List<ExprNode> getExprNodes(AST currentNode)
    {
        List<ExprNode> exprNodes = new LinkedList<ExprNode>();
        while(currentNode != null)
        {
            ExprNode exprNode = astExprNodeMap.get(currentNode);
            if (exprNode == null)
            {
                throw new IllegalStateException("Expression node for AST node not found for type " + currentNode.getType());
            }
            exprNodes.add(exprNode);
            astExprNodeMap.remove(currentNode);
            currentNode = currentNode.getNextSibling();
        }
        return exprNodes;
    }

    private static final Log log = LogFactory.getLog(EQLTreeWalker.class);
}
