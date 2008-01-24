package net.esper.core;

import net.esper.client.EPSubscriberException;
import net.esper.util.JavaClassHelper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map;

public class StatementResultNaturalStrategyFactory 
{
    public static StatementResultNaturalStrategy create(Object subscriber,
                                                        Class[] selectClauseTypes,
                                                        String[] selectClauseColumns)
            throws EPSubscriberException
    {
        // Locate update methods
        Method subscriptionMethod = null;
        ArrayList<Method> updateMethods = new ArrayList<Method>();
        for (Method method : subscriber.getClass().getMethods())
        {
            if ((method.getName().equals("update")) &&
                (Modifier.isPublic(method.getModifiers())))
            {
                updateMethods.add(method);
            }
        }

        // none found
        if (updateMethods.size() == 0)
        {
            String message = "Subscriber object does not provide a method by name 'update'";
            throw new EPSubscriberException(message);
        }

        // match to parameters
        boolean isMapArrayDelivery = false;
        boolean isObjectArrayDelivery = false;
        boolean isSingleRowMap = false;
        boolean isSingleRowObjectArr = false;
        boolean isTypeArrayDelivery = false;
        for (Method method : updateMethods)
        {
            Class[] parameters = method.getParameterTypes();

            if (parameters.length == selectClauseTypes.length)
            {
                boolean fitsParameters = true;
                for (int i = 0; i < parameters.length; i++)
                {
                    Class boxedExpressionType = JavaClassHelper.getBoxedType(selectClauseTypes[i]);
                    Class boxedParameterType = JavaClassHelper.getBoxedType(parameters[i]);
                    if ((boxedExpressionType != null) && (!JavaClassHelper.isAssignmentCompatible(boxedExpressionType, boxedParameterType)))
                    {
                        fitsParameters = false;
                        break;
                    }
                }
                if (fitsParameters)
                {
                    subscriptionMethod = method;
                    break;
                }
            }

            if ((parameters.length == 1) && (parameters[0] == Map.class))
            {
                isSingleRowMap = true;
                subscriptionMethod = method;
                break;
            }
            if ((parameters.length == 1) && (parameters[0] == Object[].class))
            {
                isSingleRowObjectArr = true;
                subscriptionMethod = method;
                break;
            }

            if ((parameters.length == 2) && (parameters[0] == Map[].class) && (parameters[1] == Map[].class))
            {
                subscriptionMethod = method;
                isMapArrayDelivery = true;
                break;
            }
            if ((parameters.length == 2) && (parameters[0] == Object[][].class) && (parameters[1] == Object[][].class))
            {
                subscriptionMethod = method;
                isObjectArrayDelivery = true;
                break;
            }
            // Handle uniform underlying or column type array dispatch
            if ((parameters.length == 2) && (parameters[0].equals(parameters[1])) && (parameters[0].isArray())
                    && (selectClauseTypes.length == 1))
            {
                Class componentType = parameters[0].getComponentType();
                if (JavaClassHelper.isAssignmentCompatible(selectClauseTypes[0], componentType))
                {
                    subscriptionMethod = method;
                    isTypeArrayDelivery = true;
                    break;
                }
            }
        }

        if (subscriptionMethod == null)
        {
            if (updateMethods.size() > 1)
            {
                String message = "Could not find a suitable update method";
                throw new EPSubscriberException(message);
            }
            else if (updateMethods.size() == 1)
            {
                Class[] parameters = updateMethods.get(0).getParameterTypes();
                String parametersDesc = JavaClassHelper.getParameterAsString(parameters);
                if (parameters.length != selectClauseTypes.length)
                {
                    String message = "Subscriber method named 'update' does not take the expected number of parameters, expecting " + 
                            selectClauseTypes.length + " parameters but found " + parameters.length;
                    throw new EPSubscriberException(message);
                }
                for (int i = 0; i < parameters.length; i++)
                {
                    Class boxedExpressionType = JavaClassHelper.getBoxedType(selectClauseTypes[i]);
                    Class boxedParameterType = JavaClassHelper.getBoxedType(parameters[i]);
                    if ((boxedExpressionType != null) && (!JavaClassHelper.isAssignmentCompatible(boxedExpressionType, boxedParameterType)))
                    {
                        String message = "Parameter type for parameter number " + i + " is not assignable, " +
                                "expecting type '" + selectClauseTypes[i] + "' but found type '"
                                + parameters[i] + "'";
                        throw new EPSubscriberException(message);
                    }
                }
            }
            else
            {
                String message = "Could not find a suitable update method";
                throw new EPSubscriberException(message);
            }
        }

        if (isMapArrayDelivery)
        {
            return new StatementResultNaturalStrategyMap(subscriber, subscriptionMethod, selectClauseColumns);            
        }
        else if (isObjectArrayDelivery)
        {
            return new StatementResultNaturalStrategyObjectArr(subscriber, subscriptionMethod);            
        }
        else if (isTypeArrayDelivery)
        {
            return new StatementResultNaturalStrategyTypeArr(subscriber, subscriptionMethod);
        }

        // Try to find the "start", "end" and "updateRStream" methods
        Method startMethod = null;
        Method endMethod = null;
        Method rStreamMethod = null;
        try {
            startMethod = subscriber.getClass().getMethod("updateStart", new Class[] {int.class, int.class});
        }
        catch (NoSuchMethodException e) {
            // expected
        }

        try {
            endMethod = subscriber.getClass().getMethod("updateEnd", new Class[] {});
        }
        catch (NoSuchMethodException e) {
            // expected
        }

        try {
            rStreamMethod = subscriber.getClass().getMethod("updateRStream", subscriptionMethod.getParameterTypes());
        }
        catch (NoSuchMethodException e) {
            // expected
        }

        DeliveryConvertor convertor;
        if (isSingleRowMap)
        {
            convertor = new DeliveryConvertorMap(selectClauseColumns);
        }
        else if (isSingleRowObjectArr)
        {
            convertor = new DeliveryConvertorObjectArr();
        }
        else
        {
            convertor = new DeliveryConvertorNull();
        }

        return new StatementResultNaturalStrategyImpl(subscriber, convertor, subscriptionMethod, startMethod, endMethod, rStreamMethod);
    }
}
