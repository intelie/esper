package net.esper.eql.core;

import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.SelectClauseExprCompiledSpec;
import net.esper.event.EventBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BindStrategyMap implements BindStrategy
{
    private ExprNode[] expressionNodes;
    private String[] columnNames;

    public BindStrategyMap(List<SelectClauseExprCompiledSpec> selectionList)
            throws ExprValidationException
    {
        // Get expression nodes
        expressionNodes = new ExprNode[selectionList.size()];
        columnNames = new String[selectionList.size()];

        for (int i = 0; i < selectionList.size(); i++)
        {
            expressionNodes[i] = selectionList.get(i).getSelectExpression();
            columnNames[i] = selectionList.get(i).getAssignedName();
        }
    }

    public Object[] process(EventBean[] eventsPerStream, boolean isNewData)
    {
        Map<String, Object> values = new HashMap<String, Object>();

        for (int i = 0; i < expressionNodes.length; i++)
        {
            Object result = expressionNodes[i].evaluate(eventsPerStream, isNewData);
            values.put(columnNames[i], result);
        }

        return new Object[] {values};
    }
}
