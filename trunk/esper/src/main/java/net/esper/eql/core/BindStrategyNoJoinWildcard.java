package net.esper.eql.core;

import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.spec.ActiveObjectSpec;
import net.esper.util.JavaClassHelper;
import net.esper.event.EventBean;
import net.esper.event.EventType;

public class BindStrategyNoJoinWildcard implements BindStrategy
{
    public BindStrategyNoJoinWildcard(ActiveObjectSpec activeObjectSpec, EventType eventType)
            throws ExprValidationException
    {
        Class[] paramTypes = activeObjectSpec.getParameters();
        if ((paramTypes.length != 1) && (paramTypes.length != 2))
        {
            throw new ExprValidationException("Method parameter number does not match select-clause parameter number");
        }
        if (paramTypes.length == 2)
        {
            if (paramTypes[0] != paramTypes[1])
            {
                throw new ExprValidationException("Method parameter types must be the same");
            }
        }

        Class underlyingType = eventType.getUnderlyingType();

        // Use component type if we are deliverying to an array
        Class parameterType = paramTypes[0];
        if (parameterType.isArray())
        {
            parameterType = parameterType.getComponentType();
        }
        
        if (!JavaClassHelper.isAssignmentCompatible(underlyingType, parameterType))
        {
            throw new ExprValidationException("Method parameter type '" + paramTypes[0].getName() +
                    "' is not assignable from select column typed '" + underlyingType.getName() +
                    "' of wildcard expression");
        }
    }

    public Object[] process(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object[] parameters = new Object[1];
        parameters[0] = eventsPerStream[0].getUnderlying();
        return parameters;
    }
}
