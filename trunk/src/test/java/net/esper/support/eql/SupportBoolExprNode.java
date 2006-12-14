package net.esper.support.eql;

import net.esper.eql.core.AutoImportService;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.event.EventBean;

public class SupportBoolExprNode extends ExprNode
{
    private boolean evaluateResult;

    public SupportBoolExprNode(boolean evaluateResult)
    {
        this.evaluateResult = evaluateResult;
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
    }

    public Class getType()
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return evaluateResult;
    }

    public String toExpressionString()
    {
        return null;
    }

    public boolean equalsNode(ExprNode node)
    {
        throw new UnsupportedOperationException("not implemented");
    }    
}
