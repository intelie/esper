package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.core.EngineImportException;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.event.bean.BeanEventType;
import com.espertech.esper.event.bean.InternalWritablePropDescriptor;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.TypeWidener;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EventBeanManufacturerBean implements EventBeanManufacturer
{
    private static Log log = LogFactory.getLog(EventBeanManufacturerBean.class);

    private BeanEventType beanEventType;
    private EventAdapterService service;
    private FastClass fastClass;
    private List<InternalWritablePropDescriptor> writables;

    private FastMethod[] writeMethods;
    private ExprEvaluator[] expressionNodes;
    private TypeWidener[] wideners;
    private FastMethod factoryMethod;

    public EventBeanManufacturerBean(EventAdapterService service, FastClass fastClass, List<InternalWritablePropDescriptor> writables, BeanEventType beanEventType)
    {
        this.service = service;
        this.fastClass = fastClass;
        this.beanEventType = beanEventType;
        this.writables = writables;
    }

    public void initialize(boolean isUsingWildcard, StreamTypeService typeService, ExprEvaluator[] expressionNodes, String[] columnNames, Object[] expressionReturnTypes, MethodResolutionService methodResolutionService)
            throws ExprValidationException
    {
        // see if we use a factory method
        if (beanEventType.getFactoryMethodName() != null)
        {
            factoryMethod = resolveFactoryMethod(fastClass, beanEventType.getFactoryMethodName(), methodResolutionService);
        }
        else
        {
            try
            {
                fastClass.newInstance();
            }
            catch (InvocationTargetException e)
            {
                String message = "Failed to instantiate class '" + fastClass.getJavaClass().getName() + "', define a factory method if the class has no default constructor: " + e.getTargetException().getMessage();
                log.info(message, e);
                throw new ExprValidationException(message, e.getTargetException());
            }
            catch (IllegalArgumentException e)
            {
                String message = "Failed to instantiate class '" + fastClass.getJavaClass().getName() + "', define a factory method if the class has no default constructor";
                log.info(message, e);
                throw new ExprValidationException(message, e);
            }
        }

        // assign write methods to each column for matching columns
        this.expressionNodes = expressionNodes;
        this.wideners = new TypeWidener[columnNames.length];
        writeMethods = new FastMethod[columnNames.length];
        Set<String> unassignedColumnNames = new HashSet<String>(Arrays.asList(columnNames));
        for (int i = 0; i < columnNames.length; i++)
        {
            for (InternalWritablePropDescriptor desc : writables)
            {
                if (!desc.getPropertyName().equals(columnNames[i]))
                {
                    continue;
                }

                String columnName = columnNames[i];
                Method writeMethod = desc.getWriteMethod();

                Object columnType = expressionReturnTypes[i];
                if (!(columnType instanceof Class) && (columnType != null))
                {
                    String message = "Invalid assignment of column '" + columnName +
                            "' of type '" + columnType +
                            "' to write method '" + writeMethod.getName() +
                            "' parameterized by '" + JavaClassHelper.getParameterAsString(writeMethod.getParameterTypes()) +
                            "', column and parameter types mismatch";
                    throw new ExprValidationException(message);
                }
                
                Class columnClass = (Class) columnType;
                Class columnClassBoxed = JavaClassHelper.getBoxedType((Class) columnType);
                Class targetClass = writeMethod.getParameterTypes()[0];
                Class targetClassBoxed = JavaClassHelper.getBoxedType(writeMethod.getParameterTypes()[0]);

                if ((columnClass == null) && (targetClass.isPrimitive()))
                {
                    String message = "Invalid assignment of column '" + columnName +
                            "' of null type to write method '" + writeMethod.getName() +
                            "' parameterized by '" + JavaClassHelper.getParameterAsString(writeMethod.getParameterTypes()) +
                            "', nullable type mismatch";
                    throw new ExprValidationException(message);
                }
                else if (columnClassBoxed != targetClassBoxed)
                {
                    if (columnClassBoxed == String.class && targetClassBoxed == Character.class)
                    {
                        wideners[i] = new StringToCharCoercer();
                    }
                    else if (!JavaClassHelper.isAssignmentCompatible(columnClassBoxed, targetClassBoxed))
                    {
                        String message = "Invalid assignment of column '" + columnName +
                                "' of type '" + columnClass.getName() +
                                "' to write method '" + writeMethod.getName() +
                                "' parameterized by '" + JavaClassHelper.getParameterAsString(writeMethod.getParameterTypes()) +
                                "', column and parameter types mismatch";
                        throw new ExprValidationException(message);
                    }
                }

                writeMethods[i] = fastClass.getMethod(desc.getWriteMethod());
                unassignedColumnNames.remove(columnName);
                break;
            }
        }

        if (!unassignedColumnNames.isEmpty())
        {
            String message = "Column '" + unassignedColumnNames.iterator().next() +
                    "' could not be assigned to any of the properties of class '" + fastClass.getJavaClass().getName() + "' (missing column names or setter method?)";
            throw new ExprValidationException(message);            
        }
    }

    public EventBean manufacture(EventBean[] eventsPerStream, boolean newData)
    {
        Object out;
        if (factoryMethod == null)
        {
            try
            {
                out = fastClass.newInstance();
            }
            catch (InvocationTargetException e)
            {
                String message = "Unexpected exception encountered invoking newInstance on class '" + fastClass.getJavaClass().getName() + "': " + e.getTargetException().getMessage(); 
                log.error(message, e);
                return null;
            }
        }
        else
        {
            try
            {
                out = factoryMethod.invoke(null, null);
            }
            catch (InvocationTargetException e)
            {
                String message = "Unexpected exception encountered invoking factory method '" + factoryMethod.getName() + "' on class '" + factoryMethod.getJavaMethod().getDeclaringClass().getName() + "': " + e.getTargetException().getMessage(); 
                log.error(message, e);
                return null;
            }
        }

        Object[] params = new Object[1];
        for (int i = 0; i < writeMethods.length; i++)
        {
            if (writeMethods[i] == null)
            {
                continue;
            }

            Object evalResult = expressionNodes[i].evaluate(eventsPerStream, newData);
            if ((evalResult != null) && (wideners[i] != null))
            {
                evalResult = wideners[i].widen(evalResult);
            }
            params[0] = evalResult;

            try
            {
                writeMethods[i].invoke(out, params);
            }
            catch (InvocationTargetException e)
            {
                String message = "Unexpected exception encountered invoking setter-method '" + writeMethods[i] + "' on class '" +
                        fastClass.getJavaClass().getName() + "' : " + e.getTargetException().getMessage(); 
                log.error(message, e);
            }
        }

        return service.adapterForTypedBean(out, beanEventType);
    }

    private class StringToCharCoercer implements TypeWidener
    {
        public Object widen(Object input)
        {
            String result = input.toString();
            if ((result != null) && (result.length() > 0))
            {
                return result.charAt(0);
            }
            return null;
        }
    }

    private static FastMethod resolveFactoryMethod(FastClass fastClass, String factoryMethodName, MethodResolutionService methodResolutionService)
            throws ExprValidationException
    {
        int lastDotIndex = factoryMethodName.lastIndexOf('.');
        if (lastDotIndex == -1)
        {
            try
            {
                Method method = methodResolutionService.resolveMethod(fastClass.getJavaClass(), factoryMethodName, new Class[0]);
                return fastClass.getMethod(method);
            }
            catch (EngineImportException e)
            {
                String message = "Failed to resolve configured factory method '" + factoryMethodName +
                        "' expected to exist for class '" + fastClass.getName() + "'";
                log.info(message, e);
                throw new ExprValidationException(message, e); 
            }
        }

        String className = factoryMethodName.substring(0, lastDotIndex);
        String methodName = factoryMethodName.substring(lastDotIndex + 1);
        try
        {
            Method method = methodResolutionService.resolveMethod(className, methodName, new Class[0]);
            FastClass fastClassFactory = FastClass.create(method.getDeclaringClass());
            return fastClassFactory.getMethod(method);
        }
        catch (EngineImportException e)
        {
            String message = "Failed to resolve configured factory method '" + methodName + "' expected to exist for class '" + className + "'";
            log.info(message, e);
            throw new ExprValidationException(message, e);
        }
    }    
}
