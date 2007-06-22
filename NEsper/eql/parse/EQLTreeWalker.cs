using System;
using System.Collections.Generic;

using antlr;
using antlr.collections;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.agg;
using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.eql.generated;
using net.esper.eql.spec;
using net.esper.pattern;
using net.esper.pattern.observer;
using net.esper.pattern.guard;
using net.esper.type;

using org.apache.commons.logging;

namespace net.esper.eql.parse
{
	/// <summary>
    /// Called during the walks of a EQL expression AST tree as specified in the grammar file.
	/// Constructs filter and view specifications etc.
	/// </summary>

    public class EQLTreeWalker : EQLBaseWalker
    {
        /// <summary> Returns statement specification.</summary>
        /// <returns> statement spec.
        /// </returns>
        virtual public StatementSpecRaw StatementSpec
        {
            get { return statementSpec; }
        }

	    // private holding areas for accumulated info
	    private EDictionary<AST, ExprNode> astExprNodeMap = new HashDictionary<AST, ExprNode>();
	    private readonly Stack<EDictionary<AST, ExprNode>> astExprNodeMapStack;

	    private readonly EDictionary<AST, EvalNode> astPatternNodeMap = new HashDictionary<AST, EvalNode>();

	    private FilterSpecRaw filterSpec;
	    private readonly List<ViewSpec> viewSpecs = new List<ViewSpec>();

	    // Pattern indicator dictates behavior for some AST nodes
	    private bool isProcessingPattern;

	    // AST Walk result
	    private StatementSpecRaw statementSpec;
	    private readonly Stack<StatementSpecRaw> statementSpecStack;

	    private readonly EngineImportService engineImportService;
	    private readonly PatternObjectResolutionService patternObjectResolutionService;

	    /// <summary>Ctor.</summary>
	    /// <param name="engineImportService">
	    /// is required to resolve lib-calls into static methods or configured aggregation functions
	    /// </param>
	    /// <param name="patternObjectResolutionService">
	    /// resolves plug-in pattern object names (guards and observers)
	    /// </param>
	    public EQLTreeWalker(EngineImportService engineImportService,
	                         PatternObjectResolutionService patternObjectResolutionService)
	    {
	        this.engineImportService = engineImportService;
	        this.patternObjectResolutionService = patternObjectResolutionService;
	        statementSpec = new StatementSpecRaw();
	        statementSpecStack = new Stack<StatementSpecRaw>();
	        astExprNodeMapStack = new Stack<EDictionary<AST, ExprNode>>();
	    }

	    /// <summary>
	    /// Pushes a statement into the stack, creating a new empty statement to fill in.
	    /// The leave node method for subquery statements pops from the stack.
	    /// </summary>
	    /// <throws>SemanticException is a standard parser exception</throws>
	    protected void PushStmtContext()
		{
	        if (log.IsDebugEnabled)
	        {
	            log.Debug(".pushStmtContext");
	        }
	        statementSpecStack.Push(statementSpec);
	        astExprNodeMapStack.Push(astExprNodeMap);

	        statementSpec = new StatementSpecRaw();
	        astExprNodeMap = new HashDictionary<AST, ExprNode>();
	    }

        /// <summary> Set to indicate that we are walking a pattern.</summary>
        /// <param name="isPatternWalk">is true if walking a pattern
        /// </param>
        protected override void setIsPatternWalk(bool isPatternWalk)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".setIsPatternWalk " + isPatternWalk);
            }
            isProcessingPattern = isPatternWalk;
        }

        /// <summary> Leave AST node and process it's type and child nodes.</summary>
        /// <param name="node">is the node to complete
        /// </param>
        /// <throws>ASTWalkException - if the node tree walk operation failed</throws>
        protected override void leaveNode(AST node)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".leaveNode " + node);
            }

            switch (node.Type)
            {

                case EqlEvalTokenTypes.STREAM_EXPR:
                    leaveStreamExpr(node);
                    break;

                case EqlEvalTokenTypes.EVENT_FILTER_EXPR:
                    leaveFilter(node);
                    break;

                case EqlEvalTokenTypes.PATTERN_INCL_EXPR:
                    return;

                case EqlEvalTokenTypes.VIEW_EXPR:
                    leaveView(node);
                    break;

                case EqlEvalTokenTypes.SELECTION_EXPR:
                    leaveSelectClause(node);
                    break;

				case EqlEvalTokenTypes.WILDCARD_SELECT:
					leaveWildcardSelect();
					break;

                case EqlEvalTokenTypes.SELECTION_ELEMENT_EXPR:
                    leaveSelectionElement(node);
                    break;

                case EqlEvalTokenTypes.EVENT_PROP_EXPR:
					leaveEventPropertyExpr(node);
                    break;

                case EqlEvalTokenTypes.EVAL_AND_EXPR:
                    leaveJoinAndExpr(node);
                    break;

                case EqlEvalTokenTypes.EVAL_OR_EXPR:
                    leaveJoinOrExpr(node);
                    break;

                case EqlEvalTokenTypes.EVAL_EQUALS_EXPR:
                case EqlEvalTokenTypes.EVAL_NOTEQUALS_EXPR:
					leaveEqualsExpr(node);
                    break;

                case EqlEvalTokenTypes.WHERE_EXPR:
                    leaveWhereClause();
                    break;

				case EqlEvalTokenTypes.NUM_INT:
                case EqlEvalTokenTypes.INT_TYPE:
                case EqlEvalTokenTypes.LONG_TYPE:
                case EqlEvalTokenTypes.BOOL_TYPE:
                case EqlEvalTokenTypes.FLOAT_TYPE:
                case EqlEvalTokenTypes.DOUBLE_TYPE:
                case EqlEvalTokenTypes.STRING_TYPE:
                case EqlEvalTokenTypes.NULL_TYPE:
                    leaveConstant(node);
                    break;

                case EqlEvalTokenTypes.STAR:
                case EqlEvalTokenTypes.MINUS:
                case EqlEvalTokenTypes.PLUS:
                case EqlEvalTokenTypes.DIV:
                case EqlEvalTokenTypes.MOD:
                    leaveMath(node);
                    break;

                case EqlEvalTokenTypes.BAND:
                case EqlEvalTokenTypes.BOR:
                case EqlEvalTokenTypes.BXOR:
                    leaveBitWise(node);
                    break;

                case EqlEvalTokenTypes.LT_:
                case EqlEvalTokenTypes.GT:
                case EqlEvalTokenTypes.LE:
                case EqlEvalTokenTypes.GE:
                    leaveRelationalOp(node);
                    break;

                case EqlEvalTokenTypes.COALESCE:
                    leaveCoalesce(node);
                    break;

                case EqlEvalTokenTypes.NOT_EXPR:
                    leaveNot(node);
                    break;

                case EqlEvalTokenTypes.SUM:
                case EqlEvalTokenTypes.AVG:
                case EqlEvalTokenTypes.COUNT:
                case EqlEvalTokenTypes.MEDIAN:
                case EqlEvalTokenTypes.STDDEV:
                case EqlEvalTokenTypes.AVEDEV:
                    leaveAggregate(node);
                    break;

                case EqlEvalTokenTypes.LIB_FUNCTION:
                    leaveLibFunction(node);
                    break;

                case EqlEvalTokenTypes.LEFT_OUTERJOIN_EXPR:
                case EqlEvalTokenTypes.RIGHT_OUTERJOIN_EXPR:
                case EqlEvalTokenTypes.FULL_OUTERJOIN_EXPR:
                    leaveOuterJoin(node);
                    break;

                case EqlEvalTokenTypes.GROUP_BY_EXPR:
                    leaveGroupBy(node);
                    break;

                case EqlEvalTokenTypes.HAVING_EXPR:
                    leaveHavingClause();
                    break;

                case EqlEvalTokenTypes.ORDER_BY_EXPR:
                    break;

                case EqlEvalTokenTypes.ORDER_ELEMENT_EXPR:
                    leaveOrderByElement(node);
                    break;

                case EqlEvalTokenTypes.EVENT_LIMIT_EXPR:
                case EqlEvalTokenTypes.SEC_LIMIT_EXPR:
                case EqlEvalTokenTypes.MIN_LIMIT_EXPR:
                    leaveOutputLimit(node);
                    break;

                case EqlEvalTokenTypes.INSERTINTO_EXPR:
                    leaveInsertInto(node);
                    break;

                case EqlEvalTokenTypes.CONCAT:
                    leaveConcat(node);
                    break;

                case EqlEvalTokenTypes.CASE:
                    leaveCaseNode(node, false);
                    break;

                case EqlEvalTokenTypes.CASE2:
                    leaveCaseNode(node, true);
                    break;

                case EqlEvalTokenTypes.EVERY_EXPR:
                    leaveEvery(node);
                    break;

                case EqlEvalTokenTypes.FOLLOWED_BY_EXPR:
                    leaveFollowedBy(node);
                    break;

                case EqlEvalTokenTypes.OR_EXPR:
                    leaveOr(node);
                    break;

                case EqlEvalTokenTypes.AND_EXPR:
                    leaveAnd(node);
                    break;

                case EqlEvalTokenTypes.GUARD_EXPR:
                    leaveGuard(node);
                    break;

                case EqlEvalTokenTypes.OBSERVER_EXPR:
                    leaveObserver(node);
                    break;

                case EqlEvalTokenTypes.IN_SET:
                case EqlEvalTokenTypes.NOT_IN_SET:
	                leaveInSet(node);
	                break;

	            case EqlEvalTokenTypes.IN_RANGE:
	            case EqlEvalTokenTypes.NOT_IN_RANGE:
	                leaveInRange(node);
	                break;


                case EqlEvalTokenTypes.BETWEEN:
                case EqlEvalTokenTypes.NOT_BETWEEN:
                    leaveBetween(node);
                    break;

                case EqlEvalTokenTypes.LIKE:
                case EqlEvalTokenTypes.NOT_LIKE:
                    leaveLike(node);
                    break;

                case EqlEvalTokenTypes.REGEXP:
                case EqlEvalTokenTypes.NOT_REGEXP:
                    leaveRegexp(node);
                    break;

	            case EqlEvalTokenTypes.PREVIOUS:
	                leavePrevious(node);
	                break;

				case EqlEvalTokenTypes.PRIOR:
	                leavePrior(node);
	                break;

				case EqlEvalTokenTypes.ARRAY_EXPR:
	                leaveArray(node);
	                break;

				case EqlEvalTokenTypes.SUBSELECT_EXPR:
	                leaveSubselectRow(node);
	                break;

				case EqlEvalTokenTypes.EXISTS_SUBSELECT_EXPR:
	                leaveSubselectExists(node);
	                break;

				case EqlEvalTokenTypes.IN_SUBSELECT_EXPR:
	            case EqlEvalTokenTypes.NOT_IN_SUBSELECT_EXPR:
	                leaveSubselectIn(node);
	                break;

				case EqlEvalTokenTypes.IN_SUBSELECT_QUERY_EXPR:
	                leaveSubselectQueryIn(node);
	                break;

                default:
                    throw new ASTWalkException("Unhandled node type encountered, type '" + node.Type + "' with text '" + node.getText() + "'");

            }

            // For each AST child node of this AST node that generated an ExprNode add the child node to the expression node.
            // This is for automatic expression tree building.
            ExprNode thisEvalNode = astExprNodeMap.Fetch( node, null ) ;

            // Loop over all child nodes for this node.
            AST childNode = node.getFirstChild();
            do
            {
                ExprNode childEvalNode = null;
                if (childNode != null)
                {
                    astExprNodeMap.TryGetValue(childNode, out childEvalNode);
                }
                // If there was an expression node generated for the child node, and there is a current expression node,
                // add it to the current expression node (thisEvalNode)
                if ((childEvalNode != null) && (thisEvalNode != null))
                {
                    thisEvalNode.AddChildNode(childEvalNode);
                    astExprNodeMap.Remove(childNode);
                }
                // Next child node
                if (childNode != null)
                {
                    childNode = childNode.getNextSibling();
                }
            }
            while (childNode != null);

            // For each AST child node of this AST node that generated an EvalNode add the EvalNode as a child
            EvalNode thisPatternNode = astPatternNodeMap.Fetch(node, null);

            childNode = node.getFirstChild();
            do
            {
                EvalNode childEvalNode = astPatternNodeMap.Fetch(childNode);
                if ( childEvalNode != null )
                {
                    thisPatternNode.AddChildNode(childEvalNode);
                    astPatternNodeMap.Remove(childNode);
                }
                // Next child node
                if (childNode != null)
                {
                    childNode = childNode.getNextSibling();
                }
            }
            while (childNode != null);
        }

	    private void leavePrevious(AST node)
	    {
	        log.Debug(".leavePrevious");

	        ExprPreviousNode previousNode = new ExprPreviousNode();
	        astExprNodeMap[node] = previousNode;
	    }

	    private void leavePrior(AST node)
	    {
	        log.Debug(".leavePrior");

	        ExprPriorNode priorNode = new ExprPriorNode();
	        astExprNodeMap[node] = priorNode;
	    }

	    private void leaveArray(AST node)
	    {
	        log.Debug(".leaveArray");

	        ExprArrayNode arrayNode = new ExprArrayNode();
	        astExprNodeMap[node] = arrayNode;
	    }

	    private void leaveSubselectRow(AST node)
	    {
	        log.Debug(".leaveSubselectRow");

	        StatementSpecRaw currentSpec = popStacks();
	        ExprSubselectRowNode subselectNode = new ExprSubselectRowNode(currentSpec);
	        astExprNodeMap[node] = subselectNode;
	    }

	    private void leaveSubselectExists(AST node)
	    {
	        log.Debug(".leaveSubselectExists");

	        StatementSpecRaw currentSpec = popStacks();
	        ExprSubselectNode subselectNode = new ExprSubselectExistsNode(currentSpec);
	        astExprNodeMap[node] = subselectNode;
	    }

	    private void leaveSubselectIn(AST node)
	    {
	        log.Debug(".leaveSubselectIn");

	        AST nodeEvalExpr = node.getFirstChild();
	        AST nodeSubquery = nodeEvalExpr.getNextSibling();

	        bool isNot = false;
	        if (node.Type == NOT_IN_SUBSELECT_EXPR)
	        {
	            isNot = true;
	        }

            ExprNode tempOut;
	        astExprNodeMap.Remove(nodeSubquery, out tempOut);

            ExprSubselectInNode subqueryNode = (ExprSubselectInNode) tempOut;
	        subqueryNode.SetNotIn(isNot);

	        astExprNodeMap[node] = subqueryNode;
	    }

	    private void leaveSubselectQueryIn(AST node)
	    {
	        log.Debug(".leaveSubselectQueryIn");

	        StatementSpecRaw currentSpec = popStacks();
	        ExprSubselectNode subselectNode = new ExprSubselectInNode(currentSpec);
	        astExprNodeMap[node] = subselectNode;
	    }

	    private StatementSpecRaw popStacks()
	    {
	        log.Debug(".popStacks");

	        StatementSpecRaw currentSpec = statementSpec;
	        statementSpec = statementSpecStack.Pop();
	        astExprNodeMap = astExprNodeMapStack.Pop();

	        return currentSpec;
	    }

        /// <summary> End processing of the AST tree for stand-alone pattern expressions.</summary>
        /// <throws>  ASTWalkException </throws>
        protected override void endPattern()
        {
            log.Debug(".endPattern");

            if ((astPatternNodeMap.Count > 1) || (astPatternNodeMap.Count == 0))
            {
                throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child elements for root");
            }

            // Get expression node sub-tree from the AST nodes placed so far
            EvalNode evalNode = astPatternNodeMap.FirstValue;

            PatternStreamSpecRaw streamSpec = new PatternStreamSpecRaw(evalNode, new List<ViewSpec>(), null);
            statementSpec.StreamSpecs.Add(streamSpec);

            astPatternNodeMap.Clear();
        }

        /// <summary> End processing of the AST tree, check that expression nodes found their homes.</summary>
        /// <throws>  ASTWalkException </throws>
        protected override void end()
        {
            log.Debug(".end");

            if (astExprNodeMap.Count > 1)
            {
                throw new ASTWalkException("Unexpected AST tree contains left over child elements," + " not all expression nodes have been removed from AST-to-expression nodes map");
            }
            if (astPatternNodeMap.Count > 1)
            {
                throw new ASTWalkException("Unexpected AST tree contains left over child elements," + " not all pattern nodes have been removed from AST-to-pattern nodes map");
            }
        }

        private void leaveSelectionElement(AST node)
        {
            log.Debug(".leaveSelectionElement");
            if ((astExprNodeMap.Count > 1) || ((astExprNodeMap.Count == 0)))
            {
                throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child element for root");
            }

            // Get expression node sub-tree from the AST nodes placed so far
            ExprNode exprNode = astExprNodeMap.FirstValue;
            astExprNodeMap.Clear();

            // Get list element name
            String optionalName = null;
            if (node.getFirstChild().getNextSibling() != null)
            {
                optionalName = node.getFirstChild().getNextSibling().getText();
            }

            // Add as selection element
	        statementSpec.SelectClauseSpec.Add(new SelectExprElementRawSpec(exprNode, optionalName));
	    }

	    private void leaveWildcardSelect()
	    {
	    	log.Debug(".leaveWildcardSelect");
	    	statementSpec.SelectClauseSpec.IsUsingWildcard = true;
	    }

        private void leaveView(AST node)
        {
            log.Debug(".leaveView");
            ViewSpec spec = ASTViewSpecHelper.BuildSpec(node);
            viewSpecs.Add(spec);
        }

        private void leaveStreamExpr(AST node)
        {
            log.Debug(".leaveStreamExpr");

            // Determine the optional stream name
            // Search for identifier node that carries the stream name in an "from Class.win:time().std:doit() as StreamName"
            AST streamNameNode = node.getFirstChild().getNextSibling();
            while ((streamNameNode != null) && (streamNameNode.Type != EqlEvalTokenTypes.IDENT))
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
            if (node.getFirstChild().Type == EqlEvalTokenTypes.EVENT_FILTER_EXPR)
            {
				streamSpec = new FilterStreamSpecRaw(filterSpec, viewSpecs, streamName);
            }
            else if (node.getFirstChild().Type == EqlEvalTokenTypes.PATTERN_INCL_EXPR)
            {
                if ((astPatternNodeMap.Count > 1) || ((astPatternNodeMap.Count == 0)))
                {
                    throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child elements for root");
                }

                // Get expression node sub-tree from the AST nodes placed so far
                EvalNode evalNode = astPatternNodeMap.FirstValue;

				streamSpec = new PatternStreamSpecRaw(evalNode, viewSpecs, streamName);
                astPatternNodeMap.Clear();
            }
            else if (node.getFirstChild().Type == EqlEvalTokenTypes.DATABASE_JOIN_EXPR)
            {
                AST dbchildNode = node.getFirstChild().getFirstChild();
                String dbName = dbchildNode.getText();
                String sqlWithParams = StringValue.ParseString(dbchildNode.getNextSibling().getText().Trim());
                streamSpec = new DBStatementStreamSpec(streamName, viewSpecs, dbName, sqlWithParams);
            }
            else
            {
                throw new ASTWalkException("Unexpected AST child node to stream expression, type=" + node.getFirstChild().Type);
            }
            viewSpecs.Clear();
            statementSpec.StreamSpecs.Add(streamSpec);
        }

        private void leaveEventPropertyExpr(AST node)
        {
            log.Debug(".leaveEventPropertyExpr");

            if (node.getNumberOfChildren() == 0)
            {
                throw new SystemException("Empty event property expression encountered");
            }

            ExprIdentNode identNode;

            // The stream name may precede the event property name, but cannot be told apart from the property name:
            //      s0.p1 could be a nested property, or could be stream 's0' and property 'p1'

            // A single entry means this must be the property name.
            // And a non-simple property means that it cannot be a stream name.
            if ((node.getNumberOfChildren() == 1) || (node.getFirstChild().Type != EqlEvalTokenTypes.EVENT_PROP_SIMPLE))
            {
                String propertyName = ASTFilterSpecHelper.GetPropertyName(node.getFirstChild());
                identNode = new ExprIdentNode(propertyName);
            }
            // --> this is more then one child node, and the first child node is a simple property
            // we may have a stream name in the first simple property, or a nested property
            // i.e. 's0.p0' could mean that the event has a nested property to 's0' of name 'p0', or 's0' is the stream name
            else
            {
                String streamOrNestedPropertyName = node.getFirstChild().getFirstChild().getText();
                String propertyName = ASTFilterSpecHelper.GetPropertyName(node.getFirstChild().getNextSibling());
                identNode = new ExprIdentNode(propertyName, streamOrNestedPropertyName);
            }

            astExprNodeMap[node] = identNode;
        }

        private void leaveLibFunction(AST node)
        {
            log.Debug(".leaveLibFunction");

            String childNodeText = node.getFirstChild().getText();
            if ((childNodeText.Equals("max")) || (childNodeText.Equals("min")))
            {
                handleMinMax(node);
                return;
            }

            if (node.getFirstChild().Type == EqlEvalTokenTypes.CLASS_IDENT)
            {
                String className = node.getFirstChild().getText();
                String methodName = node.getFirstChild().getNextSibling().getText();
                astExprNodeMap[node] = new ExprStaticMethodNode(className, methodName);
	            return;
	        }

	        try
	        {
	            AggregationSupport aggregation = engineImportService.ResolveAggregation(childNodeText);

	            bool isDistinct = false;
	            if ((node.getFirstChild().getNextSibling() != null) && (node.getFirstChild().getNextSibling().Type == DISTINCT))
	            {
	                isDistinct = true;
	            }

	            astExprNodeMap[node] = new ExprPlugInAggFunctionNode(isDistinct, aggregation, childNodeText);
	            return;
	        }
	        catch (EngineImportUndefinedException e)
	        {
	            // Not an aggretaion function
	        }
	        catch (EngineImportException e)
	        {
	            throw new IllegalStateException("Error resolving aggregation: " + e.Message, e);
	        }

	        throw new IllegalStateException("Unknown method named '" + childNodeText + "' could not be resolved");
	    }

	    private void leaveEqualsExpr(AST node)
	    {
	        log.Debug(".leaveEqualsExpr");

            bool isNot = false;
            if (node.Type == EqlEvalTokenTypes.EVAL_NOTEQUALS_EXPR)
            {
                isNot = true;
            }

            ExprEqualsNode identNode = new ExprEqualsNode(isNot);
            astExprNodeMap[node] = identNode;
        }

        private void leaveJoinAndExpr(AST node)
        {
            log.Debug(".leaveJoinAndExpr");
            ExprAndNode identNode = new ExprAndNode();
            astExprNodeMap[node] = identNode;
        }

        private void leaveJoinOrExpr(AST node)
        {
            log.Debug(".leaveJoinOrExpr");
            ExprOrNode identNode = new ExprOrNode();
            astExprNodeMap[node] = identNode;
        }

        private void leaveConstant(AST node)
        {
            log.Debug(".leaveConstant");
            ExprConstantNode constantNode = new ExprConstantNode(ASTConstantHelper.Parse(node));
            astExprNodeMap[node] = constantNode;
        }

        private void leaveMath(AST node)
        {
            log.Debug(".leaveMath");

            MathArithTypeEnum mathArithTypeEnum;

            switch (node.Type)
            {

                case EqlEvalTokenTypes.DIV:
                    mathArithTypeEnum = MathArithTypeEnum.DIVIDE;
                    break;

                case EqlEvalTokenTypes.STAR:
                    mathArithTypeEnum = MathArithTypeEnum.MULTIPLY;
                    break;

                case EqlEvalTokenTypes.PLUS:
                    mathArithTypeEnum = MathArithTypeEnum.ADD;
                    break;

                case EqlEvalTokenTypes.MINUS:
                    mathArithTypeEnum = MathArithTypeEnum.SUBTRACT;
                    break;

                case EqlEvalTokenTypes.MOD:
                    mathArithTypeEnum = MathArithTypeEnum.MODULO;
                    break;

                default:
                    throw new ArgumentException("Node type " + node.Type + " not a recognized math node type");

            }

            ExprMathNode mathNode = new ExprMathNode(mathArithTypeEnum);
            astExprNodeMap[node] = mathNode;
        }

        // Min/Max nodes can be either an aggregate or a per-row function depending on the number or arguments
        private void handleMinMax(AST libNode)
        {
            log.Debug(".handleMinMax");

            // Determine min or max
            AST childNode = libNode.getFirstChild();
            MinMaxTypeEnum minMaxTypeEnum;
            if (childNode.getText().Equals("min"))
            {
                minMaxTypeEnum = MinMaxTypeEnum.MIN;
            }
            else if (childNode.getText().Equals("max"))
            {
                minMaxTypeEnum = MinMaxTypeEnum.MAX;
            }
            else
            {
                throw new ArgumentException("Node type " + childNode.Type + " " + childNode.getText() + " not a recognized min max node");
            }

            // Determine distinct or not
            AST nextNode = childNode.getNextSibling();
            bool isDistinct = false;
            if (nextNode.Type == EqlEvalTokenTypes.DISTINCT)
            {
                isDistinct = true;
            }

            // Error if more then 3 nodes with distinct since it's an aggregate function
            if ((libNode.getNumberOfChildren() > 3) && (isDistinct))
            {
                throw new ASTWalkException("The distinct keyword is not valid in per-row min and max " + "functions with multiple sub-expressions");
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
            astExprNodeMap[libNode] = minMaxNode;
        }

        private void leaveCoalesce(AST node)
        {
            log.Debug(".leaveCoalesce");

            ExprNode coalesceNode = new ExprCoalesceNode();
            astExprNodeMap[node] = coalesceNode;
        }

        private void leaveAggregate(AST node)
        {
            log.Debug(".leaveAggregate");

            bool isDistinct = false;
            if ((node.getFirstChild() != null) && (node.getFirstChild().Type == EqlEvalTokenTypes.DISTINCT))
            {
                isDistinct = true;
            }

			ExprAggregateNode aggregateNode;

            switch (node.Type)
            {

                case EqlEvalTokenTypes.AVG:
                    aggregateNode = new ExprAvgNode(isDistinct);
                    break;

                case EqlEvalTokenTypes.SUM:
                    aggregateNode = new ExprSumNode(isDistinct);
                    break;

                case EqlEvalTokenTypes.COUNT:
                    aggregateNode = new ExprCountNode(isDistinct);
                    break;

                case EqlEvalTokenTypes.MEDIAN:
                    aggregateNode = new ExprMedianNode(isDistinct);
                    break;

                case EqlEvalTokenTypes.STDDEV:
                    aggregateNode = new ExprStddevNode(isDistinct);
                    break;

                case EqlEvalTokenTypes.AVEDEV:
                    aggregateNode = new ExprAvedevNode(isDistinct);
                    break;

                default:
                    throw new ArgumentException("Node type " + node.Type + " not a recognized aggregate node type");

            }

            astExprNodeMap[node] = aggregateNode;
        }

        private void leaveRelationalOp(AST node)
        {
            log.Debug(".leaveRelationalOp");

            RelationalOpEnum relationalOpEnum;

            switch (node.Type)
            {

                case EqlEvalTokenTypes.LT_:
                    relationalOpEnum = RelationalOpEnum.LT;
                    break;

                case EqlEvalTokenTypes.GT:
                    relationalOpEnum = RelationalOpEnum.GT;
                    break;

                case EqlEvalTokenTypes.LE:
                    relationalOpEnum = RelationalOpEnum.LE;
                    break;

                case EqlEvalTokenTypes.GE:
                    relationalOpEnum = RelationalOpEnum.GE;
                    break;

                default:
                    throw new ArgumentException("Node type " + node.Type + " not a recognized relational op node type");

            }

            ExprRelationalOpNode mathNode = new ExprRelationalOpNode(relationalOpEnum);
            astExprNodeMap[node] = mathNode;
        }

        private void leaveBitWise(AST node)
        {
            log.Debug(".leaveBitWise");

            BitWiseOpEnum bitWiseOpEnum;
            switch (node.Type)
            {

                case EqlEvalTokenTypes.BAND:
                    bitWiseOpEnum = BitWiseOpEnum.BAND;
                    break;

                case EqlEvalTokenTypes.BOR:
                    bitWiseOpEnum = BitWiseOpEnum.BOR;
                    break;

                case EqlEvalTokenTypes.BXOR:
                    bitWiseOpEnum = BitWiseOpEnum.BXOR;
                    break;

                default:
                    throw new ArgumentException("Node type " + node.Type + " not a recognized bit wise node type");

            }

            ExprBitWiseNode bwNode = new ExprBitWiseNode(bitWiseOpEnum);
            astExprNodeMap[node] = bwNode;
        }

        private void leaveWhereClause()
        {
            log.Debug(".leaveWhereClause");

            if (astExprNodeMap.Count != 1)
            {
                throw new SystemException("Where clause generated zero or more then one expression nodes");
            }

            // Just assign the single root ExprNode not consumed yet
            statementSpec.FilterExprRootNode = astExprNodeMap.FirstValue;
            astExprNodeMap.Clear();
        }

        private void leaveHavingClause()
        {
            log.Debug(".leaveHavingClause");

            if (astExprNodeMap.Count != 1)
            {
                throw new SystemException("Having clause generated zero or more then one expression nodes");
            }

            // Just assign the single root ExprNode not consumed yet
            statementSpec.HavingExprRootNode = astExprNodeMap.FirstValue;
            astExprNodeMap.Clear();
        }

        private void leaveOutputLimit(AST node)
        {
            log.Debug(".leaveOutputLimit");

            statementSpec.OutputLimitSpec = ASTOutputLimitHelper.buildSpec(node);
        }

        private void leaveOuterJoin(AST node)
        {
            log.Debug(".leaveOuterJoin");

            OuterJoinType joinType;
            switch (node.Type)
            {

                case EqlEvalTokenTypes.LEFT_OUTERJOIN_EXPR:
                    joinType = OuterJoinType.LEFT;
                    break;

                case EqlEvalTokenTypes.RIGHT_OUTERJOIN_EXPR:
                    joinType = OuterJoinType.RIGHT;
                    break;

                case EqlEvalTokenTypes.FULL_OUTERJOIN_EXPR:
                    joinType = OuterJoinType.FULL;
                    break;

                default:
                    throw new ArgumentException("Node type " + node.Type + " not a recognized outer join node type");

            }

            // get subnodes representing the expression
            ExprIdentNode left = (ExprIdentNode) astExprNodeMap.Fetch( node.getFirstChild() ) ;
            ExprIdentNode right = (ExprIdentNode) astExprNodeMap.Fetch( node.getFirstChild().getNextSibling() ) ;

            // remove from AST-to-expression node map
            astExprNodeMap.Remove(node.getFirstChild());
            astExprNodeMap.Remove(node.getFirstChild().getNextSibling());

            OuterJoinDesc outerJoinDesc = new OuterJoinDesc(joinType, left, right);
            statementSpec.OuterJoinDescList.Add(outerJoinDesc);
        }

        private void leaveGroupBy(AST node)
        {
            log.Debug(".leaveGroupBy");

            // there must be some expressions under the group by in our map
            if (astExprNodeMap.Count < 1)
            {
                throw new SystemException("Group-by clause generated no expression nodes");
            }

            AST child = node.getFirstChild();

            // For each child to the group-by AST node there must be a generated ExprNode
            while (child != null)
            {
                // get top expression node for the child node
                ExprNode exprNode = astExprNodeMap.Fetch(child);
                if (exprNode == null)
                {
                    throw new SystemException("Expression node as a result of group-by child node not found in collection");
                }

                statementSpec.GroupByExpressions.Add(exprNode);
                child = child.getNextSibling();
            }

            // Clear the map - all expression node should be gone
            astExprNodeMap.Clear();
        }

        private void leaveInsertInto(AST node)
        {
            log.Debug(".leaveInsertInto");

            AST child = node.getFirstChild();

            // istream or rstream
            bool isStream = true;
            if (child.Type == EqlEvalTokenTypes.RSTREAM)
            {
                isStream = false;
                child = child.getNextSibling();
            }
            if (child.Type == EqlEvalTokenTypes.ISTREAM)
            {
                child = child.getNextSibling();
            }

            // alias
            String eventAliasName = child.getText();
            InsertIntoDesc insertIntoDesc = new InsertIntoDesc(isStream, eventAliasName);

            // optional columns
            child = child.getNextSibling();
            if ((child != null) && (child.Type == EqlEvalTokenTypes.INSERTINTO_EXPRCOL))
            {
                // Each child to the insert-into AST node represents a column name
                child = child.getFirstChild();
                while (child != null)
                {
                    insertIntoDesc.Add(child.getText());
                    child = child.getNextSibling();
                }
            }

            statementSpec.InsertIntoDesc = insertIntoDesc;
        }

        private void leaveOrderByElement(AST node)
        {
            log.Debug(".leaveOrderByElement");
            if ((astExprNodeMap.Count > 1) || ((astExprNodeMap.Count == 0)))
            {
                throw new ASTWalkException("Unexpected AST tree contains zero or more then 1 child element for root");
            }

            // Get expression node sub-tree from the AST nodes placed so far
            ExprNode exprNode = astExprNodeMap.FirstValue;
            astExprNodeMap.Clear();

            // Get optional ascending or descending qualifier
            bool descending = false;
            if (node.getFirstChild().getNextSibling() != null)
            {
                descending = node.getFirstChild().getNextSibling().Type == EqlEvalTokenTypes.DESC;
            }

            // Add as order-by element
            statementSpec.OrderByList.Add(new Pair<ExprNode, Boolean>(exprNode, descending));
        }

        private void leaveConcat(AST node)
        {
            ExprConcatNode concatNode = new ExprConcatNode();
            astExprNodeMap[node] = concatNode;
        }

        private void leaveEvery(AST node)
        {
            log.Debug(".leaveEvery");
            EvalEveryNode everyNode = new EvalEveryNode();
            astPatternNodeMap[node] = everyNode;
        }

        private void leaveFilter(AST node)
        {
            log.Debug(".leaveFilter");

	        AST startNode = node.getFirstChild();
	        String optionalPatternTagName = null;
	        if (startNode.Type == EVENT_FILTER_NAME_TAG)
	        {
	            optionalPatternTagName = startNode.getText();
	            startNode = startNode.getNextSibling();
	        }

	        // Determine event type
	        String eventName = startNode.getText();

	        AST currentNode = startNode.getNextSibling();
	        List<ExprNode> exprNodes = new List<ExprNode>();
	        while(currentNode != null)
	        {
	            ExprNode exprNode = astExprNodeMap.Fetch(currentNode);
	            if (exprNode == null)
	            {
	                throw new IllegalStateException("Expression node for AST node not found for type " + currentNode.Type);
	            }
	            exprNodes.Add(exprNode);
	            astExprNodeMap.Remove(currentNode);
	            currentNode = currentNode.getNextSibling();
	        }

	        FilterSpecRaw rawFilterSpec = new FilterSpecRaw(eventName, exprNodes);
	        if (isProcessingPattern)
	        {
	            EvalFilterNode filterNode = new EvalFilterNode(rawFilterSpec, optionalPatternTagName);
	            astPatternNodeMap[node] = filterNode;
	        }
	        else
	        {
	            // for event streams we keep the filter spec around for use when the stream definition is completed
	            filterSpec = rawFilterSpec;

	            // clear the sub-nodes for the filter since the event property expressions have been processed
	            // by building the spec
	            astExprNodeMap.Clear();
	        }
        }

        private void leaveFollowedBy(AST node)
        {
            log.Debug(".leaveFollowedBy");
            EvalFollowedByNode fbNode = new EvalFollowedByNode();
            astPatternNodeMap[node] = fbNode;
        }

        private void leaveAnd(AST node)
        {
            log.Debug(".leaveAnd");
            EvalAndNode andNode = new EvalAndNode();
            astPatternNodeMap[node] = andNode;
        }

        private void leaveOr(AST node)
        {
            log.Debug(".leaveOr");
            EvalOrNode orNode = new EvalOrNode();
            astPatternNodeMap[node] = orNode;
        }

        private void leaveInSet(AST node)
        {
            log.Debug(".leaveInSet");

            ExprInNode inNode = new ExprInNode(node.Type == EqlEvalTokenTypes.NOT_IN_SET);
            astExprNodeMap[node] = inNode;
        }

		private void leaveInRange(AST node)
	    {
	        log.Debug(".leaveInRange");

	        // The second node must be braces
	        AST bracesNode = node.getFirstChild().getNextSibling();
	        if ((bracesNode.Type != LBRACK) && ((bracesNode.Type != LPAREN)))
	        {
	            throw new IllegalStateException("Invalid in-range syntax, no braces but type '" + bracesNode.Type + "'");
	        }
	        bool isLowInclude = bracesNode.Type == LBRACK;

	        // The fifth node must be braces
	        bracesNode = bracesNode.getNextSibling().getNextSibling().getNextSibling();
	        if ((bracesNode.Type != RBRACK) && ((bracesNode.Type != RPAREN)))
	        {
	            throw new IllegalStateException("Invalid in-range syntax, no braces but type '" + bracesNode.Type + "'");
	        }
	        bool isHighInclude = bracesNode.Type == RBRACK;

	        ExprBetweenNode betweenNode = new ExprBetweenNode(isLowInclude, isHighInclude, node.Type == NOT_IN_RANGE);
	        astExprNodeMap[node] = betweenNode;
	    }

        private void leaveBetween(AST node)
        {
            log.Debug(".leaveBetween");

			ExprBetweenNode betweenNode = new ExprBetweenNode(true, true, node.Type == NOT_BETWEEN);
            astExprNodeMap[node] = betweenNode;
        }

        private void leaveLike(AST node)
        {
            log.Debug(".leaveLike");

            bool isNot = node.Type == EqlEvalTokenTypes.NOT_LIKE;
            ExprLikeNode likeNode = new ExprLikeNode(isNot);
            astExprNodeMap[node] = likeNode;
        }

        private void leaveRegexp(AST node)
        {
            log.Debug(".leaveRegexp");

            bool isNot = node.Type == EqlEvalTokenTypes.NOT_REGEXP;
            ExprRegexpNode regExpNode = new ExprRegexpNode(isNot);
            astExprNodeMap[node] = regExpNode;
        }

        private void leaveNot(AST node)
        {
            log.Debug(".leaveNot");

            if (isProcessingPattern)
            {
                EvalNotNode notNode = new EvalNotNode();
                astPatternNodeMap[node] = notNode;
            }
            else
            {
                ExprNotNode notNode = new ExprNotNode();
                astExprNodeMap[node] = notNode;
            }
        }

        private void leaveGuard(AST node)
        {
            log.Debug(".leaveGuard");

            // Get the object information from AST
            AST startGuard = node.getFirstChild().getNextSibling();
            String objectNamespace = startGuard.getText();
            String objectName = startGuard.getNextSibling().getText();

            List<Object> objectParams = new List<Object>();

            AST child = startGuard.getNextSibling().getNextSibling();
            while (child != null)
            {
                Object _object = ASTParameterHelper.makeParameter(child);
                objectParams.Add(_object);
                child = child.getNextSibling();
            }

	        PatternGuardSpec guardSpec = new PatternGuardSpec(objectNamespace, objectName, objectParams);

	        // try out the specification at compile time since the node may not get used till later
	        GuardFactory factory;
	        try
	        {
	            factory = patternObjectResolutionService.Create(guardSpec);
	            factory.GuardParameters = objectParams;
	        }
	        catch (PatternObjectException e)
	        {
	            throw new ASTWalkException(e.Message, e);
	        }
	        catch (GuardParameterException e)
	        {
	            throw new ASTWalkException(e.Message, e);
	        }

	        EvalGuardNode guardNode = new EvalGuardNode(factory);
            astPatternNodeMap[node] = guardNode;
        }

        private void leaveCaseNode(AST node, bool inCase2)
        {
            log.Debug(".leaveCase2Node inCase2=" + inCase2);

            if (astExprNodeMap.Count == 0)
            {
                throw new ASTWalkException("Unexpected AST tree contains zero child element for case node");
            }
            AST childNode = node.getFirstChild();
            if ((astExprNodeMap.Count == 1) && (childNode.Type != EqlEvalTokenTypes.WHEN))
            {
                throw new ASTWalkException("AST tree doesn not contain at least when node for case node");
            }

            ExprCaseNode caseNode = new ExprCaseNode(inCase2);
            astExprNodeMap[node] = caseNode;
        }

        private void leaveObserver(AST node)
        {
            log.Debug(".leaveObserver");

            // Get the object information from AST
            String objectNamespace = node.getFirstChild().getText();
            String objectName = node.getFirstChild().getNextSibling().getText();

			List<Object> objectParams = new List<Object>();

            AST child = node.getFirstChild().getNextSibling().getNextSibling();
            while (child != null)
            {
                Object _object = ASTParameterHelper.makeParameter(child);
				objectParams.Add(_object);
                child = child.getNextSibling();
            }

	       PatternObserverSpec observerSpec = new PatternObserverSpec(objectNamespace, objectName, objectParams);

	        // try out the specification at compile time since the node may not get used till later
	        ObserverFactory factory;
	        try
	        {
	            factory = patternObjectResolutionService.Create(observerSpec);
	            factory.ObserverParameters = objectParams;
	        }
	        catch (PatternObjectException e)
	        {
	            throw new ASTWalkException(e.Message, e);
	        }
	        catch (ObserverParameterException e)
	        {
	            throw new ASTWalkException(e.Message, e);
	        }

	        EvalObserverNode observerNode = new EvalObserverNode(factory);
            astPatternNodeMap[node] = observerNode;
        }

        private void leaveSelectClause(AST node)
        {
            log.Debug(".leaveSelectClause");

            int nodeType = node.getFirstChild().Type;
            if (nodeType == EqlEvalTokenTypes.RSTREAM)
            {
                statementSpec.SelectStreamDirEnum = SelectClauseStreamSelectorEnum.RSTREAM_ONLY;
            }
            if (nodeType == EqlEvalTokenTypes.ISTREAM)
            {
                statementSpec.SelectStreamDirEnum = SelectClauseStreamSelectorEnum.ISTREAM_ONLY;
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
