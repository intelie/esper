package net.esper.eql.expression;

import net.esper.type.MinMaxTypeEnum;
import net.esper.collection.SortedRefCountedSet;

/**
 * Represents the min/max(distinct? ...) aggregate function is an expression tree.
 */
public class ExprMinMaxAggrNode extends ExprAggregateNode
{
    private final MinMaxTypeEnum minMaxTypeEnum;
    private Aggregator computer;

    /**
     * Ctor.
     * @param distinct - indicator whether distinct values of all values min/max
     * @param minMaxTypeEnum - enum for whether to minimum or maximum compute
     */
    public ExprMinMaxAggrNode(boolean distinct, MinMaxTypeEnum minMaxTypeEnum)
    {
        super(distinct);
        this.minMaxTypeEnum = minMaxTypeEnum;
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 1)
        {
            throw new ExprValidationException(minMaxTypeEnum.toString() + " node must have exactly 1 child node");
        }

        ExprNode child = this.getChildNodes().get(0);

        computer = new MinMaxAggregator(minMaxTypeEnum, child.getType());
    }

    public Aggregator getAggregationFunction()
    {
        if (computer == null)
        {
            throw new IllegalStateException("Node has not been initalized through validate call");
        }
        return computer;
    }

    public Class getType() throws ExprValidationException
    {
        return computer.getValueType();
    }

    public final boolean equalsNodeAggregate(ExprAggregateNode node)
    {
        if (!(node instanceof ExprMinMaxAggrNode))
        {
            return false;
        }

        ExprMinMaxAggrNode other = (ExprMinMaxAggrNode) node;
        return other.minMaxTypeEnum == this.minMaxTypeEnum;
    }


    protected String getAggregationFunctionName()
    {
        return minMaxTypeEnum.getExpressionText();
    }

    /**
     * Min/max aggregator for all values.
     */
    public static class MinMaxAggregator implements Aggregator
    {
        private final MinMaxTypeEnum minMaxTypeEnum;
        private final Class returnType;

        private SortedRefCountedSet<Object> refSet;

        /**
         * Ctor.
         * @param minMaxTypeEnum - enum indicating to return minimum or maximum values
         * @param returnType - is the value type returned by aggregator
         */
        public MinMaxAggregator(MinMaxTypeEnum minMaxTypeEnum, Class returnType)
        {
            this.minMaxTypeEnum = minMaxTypeEnum;
            this.returnType = returnType;
            this.refSet = new SortedRefCountedSet<Object>();
        }

        public void enter(Object object)
        {
            if (object == null)
            {
                return;
            }
            refSet.add(object);
        }

        public void leave(Object object)
        {
            if (object == null)
            {
                return;
            }
            refSet.remove(object);
        }

        public Object getValue()
        {
            if (minMaxTypeEnum == MinMaxTypeEnum.MAX)
            {
                return refSet.maxValue();
            }
            else
            {
                return refSet.minValue();
            }
        }

        public Class getValueType()
        {
            return returnType;
        }

        public Aggregator newAggregator()
        {
            return new MinMaxAggregator(minMaxTypeEnum, returnType);
        }
    }
}
