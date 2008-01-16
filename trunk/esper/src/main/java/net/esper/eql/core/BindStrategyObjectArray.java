package net.esper.eql.core;

import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.SelectClauseExprCompiledSpec;
import net.esper.eql.spec.SelectClauseElementCompiled;
import net.esper.event.EventBean;

import java.util.List;

public class BindStrategyObjectArray implements BindStrategy
{
    private ExprNode[] expressionNodes;

    public BindStrategyObjectArray(List<SelectClauseElementCompiled> selectionList)
            throws ExprValidationException
    {
        // Get expression nodes
        expressionNodes = new ExprNode[selectionList.size()];
        Class[] exprTypes = new Class[selectionList.size()];
        for (int i = 0; i < selectionList.size(); i++)
        {
            // TODO expressionNodes[i] = selectionList.get(i).getSelectExpression();
            exprTypes[i] = expressionNodes[i].getType();
        }
    }

    public Object[] process(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object[] parameters = new Object[expressionNodes.length];

        for (int i = 0; i < parameters.length; i++)
        {
            Object result = expressionNodes[i].evaluate(eventsPerStream, isNewData);
            parameters[i] = result;
        }

        return new Object[] {parameters};
    }
}
