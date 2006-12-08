package net.esper.eql.expression;

import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;
import net.esper.eql.core.AggregationResultFuture;
import net.esper.eql.core.Aggregator;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.UniqueValueAggregator;

import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.LinkedList;

/**
 * Base expression node that represents an aggregation function such as 'sum' or 'count'.
 * <p>
 * In terms of validation each concrete aggregation node must implement it's own validation.
 * <p>
 * In terms of evaluation this base class will ask the assigned {@link net.esper.eql.core.AggregationResultFuture} for the current state,
 * using a column number assigned to the node.
 * <p>
 * Concrete subclasses must supply an aggregation state prototype node {@link Aggregator} that reflects
 * each group's (there may be group-by critera) current aggregation state.
 */
public abstract class ExprAggregateNode extends ExprNode
{
	private AggregationResultFuture aggregationResultFuture;
	private int column;

    /**
     * Indicator for whether the aggregation is distinct - i.e. only unique values are considered.
     */
    protected boolean isDistinct;

    /**
     * Ctor.
     * @param distinct - sets the flag indicatating whether only unique values should be aggregated
     */
    protected ExprAggregateNode(boolean distinct)
    {
        isDistinct = distinct;
    }

    /**
     * Returns the aggregation state prototype for use in grouping aggregation states per group-by keys.
     * @return prototype aggregation state as a factory for aggregation states per group-by key value
     */
    public Aggregator getPrototypeAggregator()
    {
        if (!isDistinct)
        {
            return getAggregationFunction();
        }
        else
        {
            return new UniqueValueAggregator(getAggregationFunction());
        }
    }

    /**
     * Returns the aggregation state prototype for use in grouping aggregation states per group-by keys.
     * @return prototype aggregation state as a factory for aggregation states per group-by key value
     */
    protected abstract Aggregator getAggregationFunction();

    /**
     * Returns the aggregation function name for representation in a generate expression string.
     * @return aggregation function name
     */
    protected abstract String getAggregationFunctionName();

    /**
     * Return true if a expression aggregate node semantically equals the current node, or false if not.
     * <p>For use by the equalsNode implementation which compares the distinct flag.
     * @param node to compare to
     * @return true if semantically equal, or false if not equals
     */
    protected abstract boolean equalsNodeAggregate(ExprAggregateNode node);

    /**
     * Assigns to the node the future which can be queried for the current aggregation state at evaluation time.
     * @param aggregationResultFuture - future containing state
     * @param column - column to hand to future for easy access
     */
	public void setAggregationResultFuture(AggregationResultFuture aggregationResultFuture, int column)
    {
        this.aggregationResultFuture = aggregationResultFuture;
        this.column = column;
    }

	public final Object evaluate(EventBean[] events, boolean isNewData)
	{
		return aggregationResultFuture.getValue(column);
	}

    /**
     * Populates into the supplied list all aggregation functions within this expression, if any.
     * <p>Populates by going bottom-up such that nested aggregates appear first.
     * <p>I.e. sum(volume * sum(price)) would put first A then B into the list with A=sum(price) and B=sum(volume * A)
     * @param topNode is the expression node to deep inspect
     * @param aggregateNodes is a list of node to populate into
     */
    public static void getAggregatesBottomUp(ExprNode topNode, List<ExprAggregateNode> aggregateNodes)
    {
        // Map to hold per level of the node (1 to N depth) of expression node a list of aggregation expr nodes, if any
        // exist at that level
        TreeMap<Integer, List<ExprAggregateNode>> aggregateExprPerLevel = new TreeMap<Integer, List<ExprAggregateNode>>();

        // Recursively enter all aggregate functions and their level into map
        recursiveAggregateEnter(topNode, aggregateExprPerLevel, 1);

        // Done if none found
        if (aggregateExprPerLevel.size() == 0)
        {
            return;
        }

        // From the deepest (highest) level to the lowest, add aggregates to list
        int deepLevel = aggregateExprPerLevel.lastKey();
        for (int i = deepLevel; i >= 1; i--)
        {
            List<ExprAggregateNode> list = aggregateExprPerLevel.get(i);
            if (list == null)
            {
                continue;
            }
            aggregateNodes.addAll(list);
        }
    }

    /**
     * Returns true if the aggregation node is only aggregatig distinct values, or false if
     * aggregating all values.
     * @return true if 'distinct' keyword was given, false if not
     */
    public boolean isDistinct()
    {
        return isDistinct;
    }

    public final boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprAggregateNode))
        {
            return false;
        }

        ExprAggregateNode other = (ExprAggregateNode) node;

        if (other.isDistinct != this.isDistinct)
        {
            return false;
        }

        return this.equalsNodeAggregate(other);
    }

    private static void recursiveAggregateEnter(ExprNode currentNode, Map<Integer, List<ExprAggregateNode>> aggregateExprPerLevel, int currentLevel)
    {
        // ask all child nodes to enter themselves
        for (ExprNode node : currentNode.getChildNodes())
        {
            recursiveAggregateEnter(node, aggregateExprPerLevel, currentLevel + 1);
        }

        if (!(currentNode instanceof ExprAggregateNode))
        {
           return;
        }

        // Add myself to list, I'm an aggregate function
        List<ExprAggregateNode> aggregates = aggregateExprPerLevel.get(currentLevel);
        if (aggregates == null)
        {
            aggregates = new LinkedList<ExprAggregateNode>();
            aggregateExprPerLevel.put(currentLevel, aggregates);
        }
        aggregates.add((ExprAggregateNode)currentNode);
    }

    /**
     * For use by implementing classes, validates the aggregation node expecting
     * a single numeric-type child node.
     * @param streamTypeService - types represented in streams
     * @return numeric type of single child
     * @throws ExprValidationException if the validation failed
     */
    protected final Class validateSingleNumericChild(StreamTypeService streamTypeService)
        throws ExprValidationException
    {
        if (this.getChildNodes().size() != 1)
        {
            throw new ExprValidationException(getAggregationFunctionName() + " node must have exactly 1 child node");
        }

        ExprNode child = this.getChildNodes().get(0);
        Class childType = child.getType();
        if (!JavaClassHelper.isNumeric(childType))
        {
            throw new ExprValidationException("Implicit conversion from datatype '" +
                    childType.getName() +
                    "' to numeric is not allowed");
        }

        return childType;
    }

    /**
     * Renders the aggregation function expression.
     * @return expression string is the textual rendering of the aggregation function and it's sub-expression
     */
    public final String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getAggregationFunctionName());
        buffer.append("(");

        if (isDistinct)
        {
            buffer.append("distinct ");
        }

        if (this.getChildNodes().size() > 0)
        {
            buffer.append(this.getChildNodes().get(0).toExpressionString());
        }
        else
        {
            buffer.append("*");            
        }

        buffer.append(")");

        return buffer.toString();
    }
}