package net.esper.eql.core;

import net.esper.eql.spec.SelectExprElementCompiledSpec;
import net.esper.eql.spec.ActiveObjectSpec;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.util.MethodResolver;

import java.util.List;
import java.util.Arrays;

public class SelectExprMethodProcessor implements SelectExprProcessor
{
    private final SelectExprProcessor syntheticProcessor;
    private final List<SelectExprElementCompiledSpec> selectionList;
    private ExprNode[] expressionNodes;
    private Class[] types;

    public SelectExprMethodProcessor(SelectExprProcessor syntheticProcessor,
                                     List<SelectExprElementCompiledSpec> selectionList,
                                     ActiveObjectSpec activeObjectSpec)
            throws ExprValidationException
    {
        this.syntheticProcessor = syntheticProcessor;
        this.selectionList = selectionList;

        // Get expression nodes
        expressionNodes = new ExprNode[selectionList.size()];
        types = new Class[selectionList.size()];
        for (int i = 0; i < selectionList.size(); i++)
        {
            expressionNodes[i] = selectionList.get(i).getSelectExpression();
            types[i] = expressionNodes[i].getType();
        }

        // Get method footprint
        Class[] expected = activeObjectSpec.getParameters();
        if (!Arrays.deepEquals(types, expected))
        {
            throw new ExprValidationException("Method parameter number or type do not match select-clause column number or type");
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
