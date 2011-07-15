package com.espertech.esper.epl.join.plan;

import com.espertech.esper.epl.expression.ExprNode;

public class QueryGraphValueEntryRangeRelOp extends QueryGraphValueEntryRange {

    private final ExprNode expression;
    private final boolean isBetweenPart; // indicate that this is part of a between-clause or in-clause

    public QueryGraphValueEntryRangeRelOp(QueryGraphRangeEnum type, ExprNode expression, boolean isBetweenPart) {
        super(type);
        if (type.isRange()) {
            throw new IllegalArgumentException("Invalid ctor for use with ranges");
        }
        this.expression = expression;
        this.isBetweenPart = isBetweenPart;
    }

    public ExprNode getExpression() {
        return expression;
    }

    public boolean isBetweenPart() {
        return isBetweenPart;
    }

    public String toQueryPlan() {
        return getType().getStringOp() + " on " + expression.toExpressionString();
    }
}
