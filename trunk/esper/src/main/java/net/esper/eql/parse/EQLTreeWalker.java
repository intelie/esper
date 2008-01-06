/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import net.esper.collection.UniformPair;
import net.esper.eql.agg.AggregationSupport;
import net.esper.eql.core.EngineImportException;
import net.esper.eql.core.EngineImportService;
import net.esper.eql.core.EngineImportUndefinedException;
import net.esper.eql.expression.*;
import net.esper.eql.spec.*;
import net.esper.eql.variable.VariableService;
import net.esper.eql.generated.EsperEPL2Ast;
import net.esper.pattern.*;
import net.esper.type.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.antlr.runtime.tree.Tree;
import org.antlr.runtime.tree.TreeNodeStream;

import java.util.*;

/**
 * Called during the walks of a EQL expression AST tree as specified in the grammar file.
 * Constructs filter and view specifications etc.
 */
public class EQLTreeWalker extends EsperEPL2Ast
{
    // private holding areas for accumulated info
    private Map<Tree, ExprNode> astExprNodeMap = new HashMap<Tree, ExprNode>();
    private final Stack<Map<Tree, ExprNode>> astExprNodeMapStack;

    private final Map<Tree, EvalNode> astPatternNodeMap = new HashMap<Tree, EvalNode>();

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
    private final long engineTime;

    /**
     * Ctor.
     * @param engineImportService is required to resolve lib-calls into static methods or configured aggregation functions
     * @param variableService for variable access
     */
    public EQLTreeWalker(TreeNodeStream input, EngineImportService engineImportService, VariableService variableService, long engineTime)
    {
        super(input);
        this.engineImportService = engineImportService;
        this.variableService = variableService;
        this.engineTime = engineTime;
        
        statementSpec = new StatementSpecRaw();
        statementSpecStack = new Stack<StatementSpecRaw>();
        astExprNodeMapStack = new Stack<Map<Tree, ExprNode>>();
    }

    /**
     * Pushes a statement into the stack, creating a new empty statement to fill in.
     * The leave node method for lookup statements pops from the stack.
     */
    protected void pushStmtContext() {
        if (log.isDebugEnabled())
        {
            log.debug(".pushStmtContext");
        }
        statementSpecStack.push(statementSpec);
        astExprNodeMapStack.push(astExprNodeMap);

        statementSpec = new StatementSpecRaw();
        astExprNodeMap = new HashMap<Tree, ExprNode>();
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
    protected void leaveNode(Tree node) throws ASTWalkException
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
        for (int i = 0; i < node.getChildCount(); i++)
        {
            Tree childNode = node.getChild(i);

            ExprNode childEvalNode = astExprNodeMap.get(childNode);
            // If there was an expression node generated for the child node, and there is a current expression node,
            // add it to the current expression node (thisEvalNode)
            if ((childEvalNode != null) && (thisEvalNode != null))
            {
                thisEvalNode.addChildNode(childEvalNode);
                astExprNodeMap.remove(childNode);
            }
        }

        // For each AST child node of this AST node that generated an EvalNode add the EvalNode as a child
        EvalNode thisPatternNode = astPatternNodeMap.get(node);
        for (int i = 0; i < node.getChildCount(); i++)
        {
            Tree childNode = node.getChild(i);
            EvalNode childEvalNode = astPatternNodeMap.get(childNode);
            if (childEvalNode != null)
            {
                thisPatternNode.addChildNode(childEvalNode);
                astPatternNodeMap.remove(childNode);
            }
        }
    }

    private void leaveCreateWindow(Tree node)
    {
        log.debug(".leaveCreateWindow");

        String windowName = node.getChild(0).getText();

        String eventName = null;
        for (int i = 0; i < node.getChildCount(); i++)
        {
        	Tree child = node.getChild(i);
            if (child.getType() == CLASS_IDENT) // the event type
            {
                eventName = child.getText();
            }
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

    private void leaveCreateVariable(Tree node)
    {
        log.debug(".leaveCreateVariable");

        Tree child = node.getChild(0);
        String variableType = child.getText();
        child = node.getChild(1);
        String variableName = child.getText();

        ExprNode assignment = null;
        if (node.getChildCount() > 2)
        {
            child = node.getChild(2);
            assignment = astExprNodeMap.get(child);
            astExprNodeMap.remove(child);
        }

        CreateVariableDesc desc = new CreateVariableDesc(variableType, variableName, assignment);
        statementSpec.setCreateVariableDesc(desc);
    }

    private void leaveCreateWindowSelect(Tree node)
    {
        log.debug(".leaveCreateWindowSelect");
    }

    private void leaveOnExpr(Tree node)
    {
        log.debug(".leaveOnExpr");

        // determine on-delete or on-select
        boolean isOnDelete = false;
        Tree typeChildNode = null;
        for (int i = 0; i < node.getChildCount(); i++)
        {
        	Tree childNode = node.getChild(i);

            if (childNode.getType() == ON_DELETE_EXPR)
            {
                typeChildNode = childNode;
                isOnDelete = true;
                break;
            }
            if (childNode.getType() == ON_SELECT_EXPR)
            {
                typeChildNode = childNode;
                break;
            }
            if (childNode.getType() == ON_SET_EXPR)
            {
                typeChildNode = childNode;
                break;
            }
        }
        if (typeChildNode == null)
        {
            throw new IllegalStateException("Could not determine on-expr type");
        }

        // get optional filter stream as-name
        Tree childNode = node.getChild(1);
        String streamAsName = null;
        if (childNode.getType() == IDENT)
        {
            streamAsName = childNode.getText();
        }

        // get stream to use (pattern or filter)
        StreamSpecRaw streamSpec;
        if (node.getChild(0).getType() == EVENT_FILTER_EXPR)
        {
            streamSpec = new FilterStreamSpecRaw(filterSpec, new ArrayList<ViewSpec>(), streamAsName);
        }
        else if (node.getChild(0).getType() == PATTERN_INCL_EXPR)
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

    private OnTriggerSetDesc getOnTriggerSet(Tree typeChildNode)
    {
        OnTriggerSetDesc desc = new OnTriggerSetDesc();

        int count = 0;
        Tree child = typeChildNode.getChild(count);
        do
        {
            // get variable name
            if (child.getType() != IDENT)
            {
                throw new IllegalStateException("Expected identifier but received type '" + child.getType() + "'");
            }
            String variableName = child.getText();

            // get expression
            child = typeChildNode.getChild(++count);
            ExprNode childEvalNode = astExprNodeMap.get(child);
            astExprNodeMap.remove(child);

            desc.addAssignment(new OnTriggerSetAssignment(variableName, childEvalNode));
            child = typeChildNode.getChild(++count);
        }
        while (count < typeChildNode.getChildCount());

        return desc;
    }

    private UniformPair<String> getWindowName(Tree typeChildNode)
    {
        String windowName = null;
        String windowStreamName = null;

        for (int i = 0; i < typeChildNode.getChildCount(); i++)
        {
        	Tree child = typeChildNode.getChild(i);
            if (child.getType() == ON_EXPR_FROM)
            {
                windowName = child.getChild(0).getText();
                if (child.getChildCount() > 1)
                {
                    windowStreamName = child.getChild(1).getText();
                }
                break;
            }
        }
        if (windowName == null)
        {
            throw new IllegalStateException("Could not determine on-expr from-clause named window name");
        }
        return new UniformPair<String>(windowName, windowStreamName);
    }


    private void leavePrevious(Tree node)
    {
        log.debug(".leavePrevious");

        ExprPreviousNode previousNode = new ExprPreviousNode();
        astExprNodeMap.put(node, previousNode);
    }

    private void leavePrior(Tree node)
    {
        log.debug(".leavePrior");

        ExprPriorNode priorNode = new ExprPriorNode();
        astExprNodeMap.put(node, priorNode);
    }

    private void leaveInstanceOf(Tree node)
    {
        log.debug(".leaveInstanceOf");

        // get class identifiers
        List<String> classes = new ArrayList<String>();
        for (int i = 1; i < node.getChildCount(); i++)
        {
            Tree classIdent = node.getChild(i);
            classes.add(classIdent.getText());
        }

        String idents[] = classes.toArray(new String[0]);
        ExprInstanceofNode instanceofNode = new ExprInstanceofNode(idents);
        astExprNodeMap.put(node, instanceofNode);
    }

    private void leaveExists(Tree node)
    {
        log.debug(".leaveExists");

        ExprPropertyExistsNode instanceofNode = new ExprPropertyExistsNode();
        astExprNodeMap.put(node, instanceofNode);
    }

    private void leaveCast(Tree node)
    {
        log.debug(".leaveCast");

        String classIdent = node.getChild(1).getText();
        ExprCastNode castNode = new ExprCastNode(classIdent);
        astExprNodeMap.put(node, castNode);
    }

    private void leaveTimestamp(Tree node)
    {
        log.debug(".leaveTimestamp");

        ExprTimestampNode timeNode = new ExprTimestampNode();
        astExprNodeMap.put(node, timeNode);
    }

    private void leaveArray(Tree node)
    {
        log.debug(".leaveArray");

        ExprArrayNode arrayNode = new ExprArrayNode();
        astExprNodeMap.put(node, arrayNode);
    }

    private void leaveSubselectRow(Tree node)
    {
        log.debug(".leaveSubselectRow");
       
        StatementSpecRaw currentSpec = popStacks();
        ExprSubselectRowNode subselectNode = new ExprSubselectRowNode(currentSpec);
        astExprNodeMap.put(node, subselectNode);
    }

    private void leaveSubselectExists(Tree node)
    {
        log.debug(".leaveSubselectExists");

        StatementSpecRaw currentSpec = popStacks();
        ExprSubselectNode subselectNode = new ExprSubselectExistsNode(currentSpec);
        astExprNodeMap.put(node, subselectNode);
    }

    private void leaveSubselectIn(Tree node)
    {
        log.debug(".leaveSubselectIn");

        Tree nodeSubquery = node.getChild(1);

        boolean isNot = false;
        if (node.getType() == NOT_IN_SUBSELECT_EXPR)
        {
            isNot = true;
        }
        
        ExprSubselectInNode subqueryNode = (ExprSubselectInNode) astExprNodeMap.remove(nodeSubquery);
        subqueryNode.setNotIn(isNot);

        astExprNodeMap.put(node, subqueryNode);
    }

    private void leaveSubselectQueryIn(Tree node)
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

    private void leaveSelectionElement(Tree node) throws ASTWalkException
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
        if (node.getChildCount() > 1)
        {
            optionalName = node.getChild(1).getText();
        }

        // Add as selection element
        statementSpec.getSelectClauseSpec().add(new SelectExprElementRawSpec(exprNode, optionalName));
    }

    private void leaveSelectionStream(Tree node) throws ASTWalkException
    {
        log.debug(".leaveSelectionStream");

        String streamName = node.getChild(0).getText();

        // Get alias element name
        String optionalName = null;
        if (node.getChildCount() > 1)
        {
            optionalName = node.getChild(1).getText();
        }

        // Add as selection element
        statementSpec.getSelectClauseSpec().add(new SelectExprElementStreamRawSpec(streamName, optionalName));
    }

    private void leaveWildcardSelect()
    {
    	log.debug(".leaveWildcardSelect");
    	statementSpec.getSelectClauseSpec().setIsUsingWildcard(true);
    }

    private void leaveView(Tree node) throws ASTWalkException
    {
        log.debug(".leaveView");
        String objectNamespace = node.getChild(0).getText();
        String objectName = node.getChild(1).getText();

        List<Object> objectParams = new LinkedList<Object>();

        for (int i = 2; i < node.getChildCount(); i++)
        {
        	Tree child = node.getChild(i);

            // if there is an expression for this parameter, add the expression to the parameter list
            if (this.astExprNodeMap.containsKey(child))
            {
                ExprNode expr = astExprNodeMap.get(child);
                if (expr instanceof ExprIdentNode)
                {
                    ExprIdentNode property = (ExprIdentNode) expr;
                    objectParams.add(property.getFullUnresolvedName());
                }
                else
                {
                    objectParams.add(expr);
                }
                astExprNodeMap.remove(child);
            }
            else
            {
                Object object = ASTParameterHelper.makeParameter(child, engineTime);
                objectParams.add(object);
            }
        }

        viewSpecs.add(new ViewSpec(objectNamespace, objectName, objectParams));
    }

    private void leaveStreamExpr(Tree node)
    {
        log.debug(".leaveStreamExpr");

        // Determine the optional stream name
        // Search for identifier node that carries the stream name in an "from Class.win:time().std:doit() as StreamName"
        Tree streamNameNode = null;
        for (int i = 1; i < node.getChildCount(); i++)
        {
            Tree child = node.getChild(i);
            if (child.getType() == IDENT)
            {
                streamNameNode = child;
                break;
            }
        }
        String streamName = null;
        if (streamNameNode != null)
        {
            streamName = streamNameNode.getText();
        }

        // Convert to a stream specification instance
        StreamSpecRaw streamSpec;
        // If the first subnode is a filter node, we have a filter stream specification
        if (node.getChild(0).getType() == EVENT_FILTER_EXPR)
        {
            streamSpec = new FilterStreamSpecRaw(filterSpec, viewSpecs, streamName);
        }
        else if (node.getChild(0).getType() == PATTERN_INCL_EXPR)
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
        else if (node.getChild(0).getType() == DATABASE_JOIN_EXPR)
        {
            Tree dbrootNode = node.getChild(0);
            String dbName = dbrootNode.getChild(0).getText();
            String sqlWithParams = StringValue.parseString(dbrootNode.getChild(1).getText().trim());

            String sampleSQL = null;
            if (dbrootNode.getChildCount() > 2)
            {
                sampleSQL = dbrootNode.getChild(2).getText();
                sampleSQL = StringValue.parseString(sampleSQL.trim());
            }

            streamSpec = new DBStatementStreamSpec(streamName, viewSpecs, dbName, sqlWithParams, sampleSQL);
        }
        else if (node.getChild(0).getType() == METHOD_JOIN_EXPR)
        {
            Tree methodRootNode = node.getChild(0);
            String prefixIdent = methodRootNode.getChild(0).getText();
            String className = methodRootNode.getChild(1).getText();

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
            List<ExprNode> exprNodes = getExprNodes(methodRootNode, 2);

            streamSpec = new MethodStreamSpec(streamName, viewSpecs, prefixIdent, classNamePart, methodNamePart, exprNodes);
        }
        else
        {
            throw new ASTWalkException("Unexpected AST child node to stream expression, type=" + node.getChild(0).getType());
        }
        viewSpecs.clear();
        statementSpec.getStreamSpecs().add(streamSpec);
    }

    private void leaveEventPropertyExpr(Tree node)
    {
        log.debug(".leaveEventPropertyExpr");

        if (node.getChildCount() == 0)
        {
            throw new IllegalStateException("Empty event property expression encountered");
        }

        ExprNode exprNode;
        String propertyName;

        // The stream name may precede the event property name, but cannot be told apart from the property name:
        //      s0.p1 could be a nested property, or could be stream 's0' and property 'p1'

        // A single entry means this must be the property name.
        // And a non-simple property means that it cannot be a stream name.
        if ((node.getChildCount() == 1) || (node.getChild(0).getType() != EVENT_PROP_SIMPLE))
        {
            propertyName = ASTFilterSpecHelper.getPropertyName(node, 0);
            exprNode = new ExprIdentNode(propertyName);
        }
        // --> this is more then one child node, and the first child node is a simple property
        // we may have a stream name in the first simple property, or a nested property
        // i.e. 's0.p0' could mean that the event has a nested property to 's0' of name 'p0', or 's0' is the stream name
        else
        {
            String streamOrNestedPropertyName = node.getChild(0).getChild(0).getText();
            propertyName = ASTFilterSpecHelper.getPropertyName(node, 1);
            exprNode = new ExprIdentNode(propertyName, streamOrNestedPropertyName);
        }

        if (variableService.getReader(propertyName) != null)
        {
            exprNode = new ExprVariableNode(propertyName);
            statementSpec.setHasVariables(true);
        }

        astExprNodeMap.put(node, exprNode);
    }

    private void leaveLibFunction(Tree node)
    {
    	log.debug(".leaveLibFunction");

        String childNodeText = node.getChild(0).getText();
        if ((childNodeText.equals("max")) || (childNodeText.equals("min")))
        {
            handleMinMax(node);
            return;
        }

        if (node.getChild(0).getType() == CLASS_IDENT)
        {
            String className = node.getChild(0).getText();
            String methodName = node.getChild(1).getText();
            astExprNodeMap.put(node, new ExprStaticMethodNode(className, methodName));
            return;
        }

        try
        {
            AggregationSupport aggregation = engineImportService.resolveAggregation(childNodeText);

            boolean isDistinct = false;
            if ((node.getChild(1) != null) && (node.getChild(1).getType() == DISTINCT))
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

    private void leaveEqualsExpr(Tree node)
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

    private void leaveJoinAndExpr(Tree node)
    {
        log.debug(".leaveJoinAndExpr");
        ExprAndNode identNode = new ExprAndNode();
        astExprNodeMap.put(node, identNode);
    }

    private void leaveJoinOrExpr(Tree node)
    {
        log.debug(".leaveJoinOrExpr");
        ExprOrNode identNode = new ExprOrNode();
        astExprNodeMap.put(node, identNode);
    }

    private void leaveConstant(Tree node)
    {
        log.debug(".leaveConstant");
        ExprConstantNode constantNode = new ExprConstantNode(ASTConstantHelper.parse(node));
        astExprNodeMap.put(node, constantNode);
    }

    private void leaveSubstitution(Tree node)
    {
        log.debug(".leaveSubstitution");

        // Add the substitution parameter node, for later replacement
        int currentSize = this.substitutionParamNodes.size();
        ExprSubstitutionNode substitutionNode = new ExprSubstitutionNode(currentSize + 1);
        substitutionParamNodes.add(substitutionNode);

        astExprNodeMap.put(node, substitutionNode);
    }

    private void leaveMath(Tree node)
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
    private void handleMinMax(Tree libNode)
    {
        log.debug(".handleMinMax");

        // Determine min or max
        Tree childNode = libNode.getChild(0);
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
        Tree nextNode = libNode.getChild(1);
        boolean isDistinct = false;
        if (nextNode.getType() == DISTINCT)
        {
            isDistinct = true;
        }

        // Error if more then 3 nodes with distinct since it's an aggregate function
        if ((libNode.getChildCount() > 3) && (isDistinct))
        {
            throw new ASTWalkException("The distinct keyword is not valid in per-row min and max " +
                    "functions with multiple sub-expressions");
        }

        ExprNode minMaxNode;
        if ((!isDistinct) && (libNode.getChildCount() > 2))
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

    private void leaveCoalesce(Tree node)
    {
        log.debug(".leaveCoalesce");

        ExprNode coalesceNode = new ExprCoalesceNode();
        astExprNodeMap.put(node, coalesceNode);
    }

    private void leaveAggregate(Tree node)
    {
        log.debug(".leaveAggregate");

        boolean isDistinct = false;
        if ((node.getChild(0) != null) && (node.getChild(0).getType() == DISTINCT))
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

    private void leaveRelationalOp(Tree node)
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

    private void leaveBitWise(Tree node)
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

    private void leaveOutputLimit(Tree node) throws ASTWalkException
    {
        log.debug(".leaveOutputLimit");

        OutputLimitSpec spec = ASTOutputLimitHelper.buildOutputLimitSpec(node);
        statementSpec.setOutputLimitSpec(spec);

        if (spec.getVariableName() != null)
        {
            statementSpec.setHasVariables(true);
        }
    }

    private void leaveOuterJoin(Tree node)
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
        ExprIdentNode left = (ExprIdentNode) astExprNodeMap.get(node.getChild(0));
        ExprIdentNode right = (ExprIdentNode) astExprNodeMap.get(node.getChild(1));

        // remove from AST-to-expression node map
        astExprNodeMap.remove(node.getChild(0));
        astExprNodeMap.remove(node.getChild(1));

        // get optional additional
        ExprIdentNode[] addLeftArr = null;
        ExprIdentNode[] addRightArr = null;
        if (node.getChildCount() > 2)
        {
            ArrayList<ExprIdentNode> addLeft = new ArrayList<ExprIdentNode>();
            ArrayList<ExprIdentNode> addRight = new ArrayList<ExprIdentNode>();
            for (int i = 2; i < node.getChildCount(); i+=2)
            {
                Tree child = node.getChild(i);
                addLeft.add((ExprIdentNode)astExprNodeMap.remove(child));
                addRight.add((ExprIdentNode)astExprNodeMap.remove(node.getChild(i + 1)));
            }
            addLeftArr = addLeft.toArray(new ExprIdentNode[0]);
            addRightArr = addRight.toArray(new ExprIdentNode[0]);
        }

        OuterJoinDesc outerJoinDesc = new OuterJoinDesc(joinType, left, right, addLeftArr, addRightArr);
        statementSpec.getOuterJoinDescList().add(outerJoinDesc);
    }

    private void leaveGroupBy(Tree node)
    {
        log.debug(".leaveGroupBy");

        // there must be some expressions under the group by in our map
        if (astExprNodeMap.size() < 1)
        {
            throw new IllegalStateException("Group-by clause generated no expression nodes");
        }

        // For each child to the group-by AST node there must be a generated ExprNode
        for (int i = 0; i < node.getChildCount(); i++)
        {
        	Tree child = node.getChild(i);
            // get top expression node for the child node
            ExprNode exprNode = astExprNodeMap.get(child);

            if (exprNode == null)
            {
                throw new IllegalStateException("Expression node as a result of group-by child node not found in collection");
            }

            statementSpec.getGroupByExpressions().add(exprNode);
        }

        // Clear the map - all expression node should be gone
        astExprNodeMap.clear();
    }

    private void leaveInsertInto(Tree node)
    {
        log.debug(".leaveInsertInto");

        int count = 0;
        Tree child = node.getChild(count);

        // istream or rstream
        boolean isIStream = true;
        if (child.getType() == RSTREAM)
        {
            isIStream = false;
            child = node.getChild(++count);
        }
        if (child.getType() == ISTREAM)
        {
            child = node.getChild(++count);
        }

        // alias
        String eventAliasName = child.getText();
        InsertIntoDesc insertIntoDesc = new InsertIntoDesc(isIStream, eventAliasName);

        // optional columns
        child = node.getChild(++count);
        if ((child != null) && (child.getType() == INSERTINTO_EXPRCOL))
        {
            // Each child to the insert-into AST node represents a column name
            for (int i = 0; i < child.getChildCount(); i++)
            {
                Tree childNode = child.getChild(i);
                insertIntoDesc.add(childNode.getText());
            }
        }

        statementSpec.setInsertIntoDesc(insertIntoDesc);
    }

    private void leaveOrderByElement(Tree node) throws ASTWalkException
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
        if (node.getChildCount() > 1)
        {
            descending = node.getChild(1).getType() == DESC;
        }

        // Add as order-by element
        statementSpec.getOrderByList().add(new OrderByItem(exprNode, descending));
    }

    private void leaveConcat(Tree node)
    {
        ExprConcatNode concatNode = new ExprConcatNode();
        astExprNodeMap.put(node, concatNode);
    }

    private void leaveEvery(Tree node)
    {
        log.debug(".leaveEvery");
        EvalEveryNode everyNode = new EvalEveryNode();
        astPatternNodeMap.put(node, everyNode);
    }

    private void leaveFilter(Tree node)
    {
        log.debug(".leaveFilter");

        int count = 0;
        Tree startNode = node.getChild(0);
        String optionalPatternTagName = null;
        if (startNode.getType() == IDENT)
        {
            optionalPatternTagName = startNode.getText();
            startNode = node.getChild(++count);
        }

        // Determine event type
        String eventName = startNode.getText();

        Tree currentNode = node.getChild(++count);
        List<ExprNode> exprNodes = getExprNodes(node, count);

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

    private void leaveFollowedBy(Tree node)
    {
        log.debug(".leaveFollowedBy");
        EvalFollowedByNode fbNode = new EvalFollowedByNode();
        astPatternNodeMap.put(node, fbNode);
    }

    private void leaveAnd(Tree node)
    {
        log.debug(".leaveAnd");
        EvalAndNode andNode = new EvalAndNode();
        astPatternNodeMap.put(node, andNode);
    }

    private void leaveOr(Tree node)
    {
        log.debug(".leaveOr");
        EvalOrNode orNode = new EvalOrNode();
        astPatternNodeMap.put(node, orNode);
    }

    private void leaveInSet(Tree node)
    {
        log.debug(".leaveInSet");

        ExprInNode inNode = new ExprInNode(node.getType() == NOT_IN_SET);
        astExprNodeMap.put(node, inNode);
    }

    private void leaveInRange(Tree node)
    {
        log.debug(".leaveInRange");

        // The second node must be braces
        Tree bracesNode = node.getChild(1);
        if ((bracesNode.getType() != LBRACK) && ((bracesNode.getType() != LPAREN)))
        {
            throw new IllegalStateException("Invalid in-range syntax, no braces but type '" + bracesNode.getType() + "'");
        }
        boolean isLowInclude = bracesNode.getType() == LBRACK;

        // The fifth node must be braces
        bracesNode = node.getChild(4);
        if ((bracesNode.getType() != RBRACK) && ((bracesNode.getType() != RPAREN)))
        {
            throw new IllegalStateException("Invalid in-range syntax, no braces but type '" + bracesNode.getType() + "'");
        }
        boolean isHighInclude = bracesNode.getType() == RBRACK;

        ExprBetweenNode betweenNode = new ExprBetweenNode(isLowInclude, isHighInclude, node.getType() == NOT_IN_RANGE);
        astExprNodeMap.put(node, betweenNode);
    }

    private void leaveBetween(Tree node)
    {
        log.debug(".leaveBetween");

        ExprBetweenNode betweenNode = new ExprBetweenNode(true, true, node.getType() == NOT_BETWEEN);
        astExprNodeMap.put(node, betweenNode);
    }

    private void leaveLike(Tree node)
    {
        log.debug(".leaveLike");

        boolean isNot = node.getType() == NOT_LIKE;
        ExprLikeNode likeNode = new ExprLikeNode(isNot);
        astExprNodeMap.put(node, likeNode);
    }

    private void leaveRegexp(Tree node)
    {
        log.debug(".leaveRegexp");

        boolean isNot = node.getType() == NOT_REGEXP;
        ExprRegexpNode regExpNode = new ExprRegexpNode(isNot);
        astExprNodeMap.put(node, regExpNode);
    }

    private void leaveNot(Tree node)
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

    private void leaveGuard(Tree node) throws ASTWalkException
    {
        log.debug(".leaveGuard");

        // Get the object information from AST
        Tree startGuard = node.getChild(1);
        String objectNamespace = startGuard.getText();
        String objectName = node.getChild(2).getText();

        List<Object> objectParams = new LinkedList<Object>();
        for (int i = 3; i < node.getChildCount(); i++)
        {
        	Tree childNode = node.getChild(i);
            Object object = ASTParameterHelper.makeParameter(childNode, engineTime);
            objectParams.add(object);
        }

        PatternGuardSpec guardSpec = new PatternGuardSpec(objectNamespace, objectName, objectParams);
        EvalGuardNode guardNode = new EvalGuardNode(guardSpec);
        astPatternNodeMap.put(node, guardNode);
    }

    private void leaveCaseNode(Tree node, boolean inCase2)
    {
        if (log.isDebugEnabled())
        {
            log.debug(".leaveCase2Node inCase2=" + inCase2);
        }

        if (astExprNodeMap.isEmpty())
        {
            throw new ASTWalkException("Unexpected AST tree contains zero child element for case node");
        }
        Tree childNode = node.getChild(0);
        if (astExprNodeMap.size() == 1)
        {
            throw new ASTWalkException("AST tree doesn not contain at least when node for case node");
        }

        ExprCaseNode caseNode = new ExprCaseNode(inCase2);
        astExprNodeMap.put(node, caseNode);
    }

    private void leaveObserver(Tree node) throws ASTWalkException
    {
        log.debug(".leaveObserver");

        // Get the object information from AST
        String objectNamespace = node.getChild(0).getText();
        String objectName = node.getChild(1).getText();

        List<Object> objectParams = new LinkedList<Object>();
        for (int i = 2; i < node.getChildCount(); i++)
        {
        	Tree child = node.getChild(i);
            Object object = ASTParameterHelper.makeParameter(child, engineTime);
            objectParams.add(object);
        }

        PatternObserverSpec observerSpec = new PatternObserverSpec(objectNamespace, objectName, objectParams);
        EvalObserverNode observerNode = new EvalObserverNode(observerSpec);
        astPatternNodeMap.put(node, observerNode);
    }

    private void leaveSelectClause(Tree node)
    {
        log.debug(".leaveSelectClause");

        int nodeType = node.getChild(0).getType();
        if (nodeType == RSTREAM)
        {
            statementSpec.setSelectStreamDirEnum(SelectClauseStreamSelectorEnum.RSTREAM_ONLY);
        }
        if (nodeType == ISTREAM)
        {
            statementSpec.setSelectStreamDirEnum(SelectClauseStreamSelectorEnum.ISTREAM_ONLY);
        }
    }

    private List<ExprNode> getExprNodes(Tree parentNode, int startIndex)
    {
        List<ExprNode> exprNodes = new LinkedList<ExprNode>();

        for (int i = startIndex; i < parentNode.getChildCount(); i++)
        {
        	Tree currentNode = parentNode.getChild(i);
            ExprNode exprNode = astExprNodeMap.get(currentNode);
            if (exprNode == null)
            {
                throw new IllegalStateException("Expression node for AST node not found for type " + currentNode.getType());
            }
            exprNodes.add(exprNode);
            astExprNodeMap.remove(currentNode);
        }
        return exprNodes;
    }

    private static final Log log = LogFactory.getLog(EQLTreeWalker.class);
}
