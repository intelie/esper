package net.esper.eql.core;

import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.ActiveObjectSpec;
import net.esper.eql.spec.SelectExprElementCompiledSpec;
import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;

import java.util.List;

public class BindStrategyFieldWise implements BindStrategy
{
    private ExprNode[] expressionNodes;

    public BindStrategyFieldWise(List<SelectExprElementCompiledSpec> selectionList,
                                   ActiveObjectSpec activeObjectSpec)
            throws ExprValidationException
    {
        // Get expression nodes
        expressionNodes = new ExprNode[selectionList.size()];
        Class[] exprTypes = new Class[selectionList.size()];
        for (int i = 0; i < selectionList.size(); i++)
        {
            expressionNodes[i] = selectionList.get(i).getSelectExpression();
            exprTypes[i] = expressionNodes[i].getType();
        }

        // Get method footprint
        Class[] paramTypes = activeObjectSpec.getParameters();

        // Assert that we are assignable for all paramTypes
        if (exprTypes.length != paramTypes.length)
        {
            throw new ExprValidationException("Method parameter number does not match select-clause parameter number");
        }
        for (int i = 0; i < paramTypes.length; i++)
        {
            Class boxedExpressionType = JavaClassHelper.getBoxedType(exprTypes[i]);
            Class boxedParameterType = JavaClassHelper.getBoxedType(paramTypes[i]);
            if ((exprTypes[i] != null) && (!JavaClassHelper.isAssignmentCompatible(boxedExpressionType, boxedParameterType)))
            {
                throw new ExprValidationException("Method parameter type '" + paramTypes[i].getName() +
                        "' is not assignable from select column typed '" + exprTypes[i].getName() +
                        "' of expression '" + expressionNodes[i].toExpressionString() + "'");
            }
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

        return parameters;
    }
}
