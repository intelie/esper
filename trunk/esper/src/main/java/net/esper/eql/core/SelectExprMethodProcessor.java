package net.esper.eql.core;

import net.esper.eql.spec.SelectExprElementCompiledSpec;
import net.esper.eql.spec.ActiveObjectSpec;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;

import java.util.List;

public class SelectExprMethodProcessor implements SelectExprProcessor
{
    private final SelectExprProcessor syntheticProcessor;
    private final List<SelectExprElementCompiledSpec> selectionList;
    private ExprNode[] expressionNodes;

    public SelectExprMethodProcessor(SelectExprProcessor syntheticProcessor,
                                     List<SelectExprElementCompiledSpec> selectionList,
                                     ActiveObjectSpec activeObjectSpec)
            throws ExprValidationException
    {
        this.syntheticProcessor = syntheticProcessor;
        this.selectionList = selectionList;

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
            if ((exprTypes[i] != null && (!boxedParameterType.isAssignableFrom(boxedExpressionType))))
            {
                throw new ExprValidationException("Method parameter type '" + paramTypes[i].getName() +
                        "' is not assignable from select column typed '" + exprTypes[i].getName() +
                        "' of expression '" + expressionNodes[i].toExpressionString() + "'");
            }
        }
    }

    public EventType getResultEventType()
    {
        return syntheticProcessor.getResultEventType();
    }

    public EventBean process(EventBean[] eventsPerStream, boolean isNewData, boolean isSynthesize)
    {
        Object[] parameters = new Object[expressionNodes.length];

        for (int i = 0; i < parameters.length; i++)
        {
            Object result = expressionNodes[i].evaluate(eventsPerStream, isNewData);
            parameters[i] = result;
        }

        EventBean syntheticEvent = null;
        EventType syntheticEventType = null;
        if (isSynthesize)
        {
            syntheticEvent = syntheticProcessor.process(eventsPerStream, isNewData, isSynthesize);
            syntheticEventType = syntheticEvent.getEventType();
        }

        return new NaturalEventBean(syntheticEventType, parameters, syntheticEvent);
    }
}
