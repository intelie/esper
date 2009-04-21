package com.espertech.esper.epl.annotation;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.core.EngineImportException;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.spec.AnnotationDesc;
import com.espertech.esper.util.SimpleTypeCaster;
import com.espertech.esper.util.SimpleTypeCasterFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class AnnotationUtil
{
    public static Annotation[] compileAnnotations(List<AnnotationDesc> desc, MethodResolutionService methodResolutionService)
            throws AnnotationException
    {
        Annotation[] annotations = new Annotation[desc.size()];
        for (int i = 0; i < desc.size(); i++)
        {
            annotations[i] = createProxy(desc.get(i), methodResolutionService);
        }
        return annotations;
    }

    private static Annotation createProxy(AnnotationDesc desc, MethodResolutionService methodResolutionService)
            throws AnnotationException
    {
        // resolve class
        final Class annotationClass;
        try
        {
            annotationClass = methodResolutionService.resolveClass(desc.getName());
        }
        catch (EngineImportException e)
        {
            throw new AnnotationException("Failed to resolve @-annotation class: " + e.getMessage());
        }

        // obtain Annotation class properties
        List<AnnotationAttribute> annotationAttributeLists = getAttributes(annotationClass);
        Set<String> allAttributes = new HashSet<String>();
        Set<String> requiredAttributes = new HashSet<String>();
        for (AnnotationAttribute annotationAttribute : annotationAttributeLists)
        {
            allAttributes.add(annotationAttribute.getName());
            if (annotationAttribute.getDefaultValue() != null)
            {
                requiredAttributes.add(annotationAttribute.getName());
            }
        }

        // get attribute values
        List<String> providedValues = new ArrayList<String>();
        for (Pair<String, Object> annotationValuePair : desc.getProperties())
        {
            providedValues.add(annotationValuePair.getFirst());
        }

        // for all attributes determine value
        final Map<String, Object> properties = new HashMap<String, Object>();
        for (AnnotationAttribute annotationAttribute : annotationAttributeLists)
        {
            // find value pair for this attribute
            String attributeName = annotationAttribute.getName();
            Pair<String, Object> pairFound = null;
            for (Pair<String, Object> annotationValuePair : desc.getProperties())
            {
                if (annotationValuePair.getFirst().equals(attributeName))
                {
                    pairFound = annotationValuePair;
                }
            }

            Object valueProvided = pairFound == null ? null : pairFound.getSecond();
            Object value = getFinalValue(annotationClass, annotationAttribute, valueProvided, methodResolutionService);
            properties.put(attributeName, value);
            providedValues.remove(attributeName);
            requiredAttributes.remove(attributeName);
        }

        if (requiredAttributes.size() > 0)
        {
            throw new AnnotationException("Annotation '" + annotationClass.getSimpleName() + "' requires a value for attribute '" + requiredAttributes.iterator().next() + "'");            
        }

        if (providedValues.size() > 0)
        {
            if (allAttributes.contains(providedValues.get(0)))
            {
                throw new AnnotationException("Annotation '" + annotationClass.getSimpleName() + "' has duplicate attribute '" + providedValues.get(0) + "'");
            }
            else
            {
                throw new AnnotationException("Annotation '" + annotationClass.getSimpleName() + "' does not have an attribute '" + providedValues.get(0) + "'");
            }
        }

        // return handler
        final String toStringResult = desc.getName();
        InvocationHandler handler = new EPLAnnotationInvocationHandler(annotationClass, properties, toStringResult);
        return (Annotation) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {annotationClass}, handler);
    }

    private static Object getFinalValue(Class annotationClass, AnnotationAttribute annotationAttribute, Object value, MethodResolutionService methodResolutionService) throws AnnotationException
    {
        if (value == null)
        {
            if (annotationAttribute.getDefaultValue() == null)
            {
                throw new AnnotationException("Annotation '" + annotationClass.getSimpleName() + "' requires a value for attribute '" + annotationAttribute.getName() + "'");
            }
            return annotationAttribute.getDefaultValue();
        }

        // handle non-array
        if (!annotationAttribute.getType().isArray())
        {
            // handle primitive value
            if (!annotationAttribute.getType().isAnnotation())
            {
                SimpleTypeCaster caster = SimpleTypeCasterFactory.getCaster(value.getClass(), annotationAttribute.getType());
                Object finalValue = caster.cast(value);
                if (finalValue == null)
                {
                    throw new AnnotationException("Annotation '" + annotationClass.getSimpleName() + "' requires a " +
                            annotationAttribute.getType().getSimpleName() + "-typed value for attribute '" + annotationAttribute.getName() + "' but received " +
                            "a " + value.getClass().getSimpleName() + "-typed value");
                }
                return finalValue;
            }
            else
            {
                // nested annotation
                if (!(value instanceof AnnotationDesc))
                {
                    throw new AnnotationException("Annotation '" + annotationClass.getSimpleName() + "' requires a " +
                            annotationAttribute.getType().getSimpleName() + "-typed value for attribute '" + annotationAttribute.getName() + "' but received " +
                            "a " + value.getClass().getSimpleName() + "-typed value");
                }
                return createProxy((AnnotationDesc) value, methodResolutionService);
            }
        }

        if (!value.getClass().isArray())
        {
            throw new AnnotationException("Annotation '" + annotationClass.getSimpleName() + "' requires a " +
                    annotationAttribute.getType().getSimpleName() + "-typed value for attribute '" + annotationAttribute.getName() + "' but received " +
                    "a " + value.getClass().getSimpleName() + "-typed value");
        }

        Object array = Array.newInstance(annotationAttribute.getType().getComponentType(), Array.getLength(value));
        for (int i = 0; i < Array.getLength(value); i++)
        {
            Object arrayValue = Array.get(value, i);
            if (arrayValue == null)
            {
                throw new AnnotationException("Annotation '" + annotationClass.getSimpleName() + "' requires a " +
                        "non-null value for array elements for attribute '" + annotationAttribute.getName() + "'");
            }
            SimpleTypeCaster caster = SimpleTypeCasterFactory.getCaster(arrayValue.getClass(), annotationAttribute.getType().getComponentType());
            Object finalValue = caster.cast(arrayValue);
            if (finalValue == null)
            {
                throw new AnnotationException("Annotation '" + annotationClass.getSimpleName() + "' requires a " +
                        annotationAttribute.getType().getComponentType().getSimpleName() + "-typed value for array elements for attribute '" + annotationAttribute.getName() + "' but received " +
                        "a " + arrayValue.getClass().getSimpleName() + "-typed value");
            }
            Array.set(array, i, finalValue);
        }
        return array;
    }

    private static List<AnnotationAttribute> getAttributes(Class annotationClass)
    {
        List<AnnotationAttribute> props = new ArrayList<AnnotationAttribute>();
        Method[] methods = annotationClass.getMethods();
        if (methods == null)
        {
            return Collections.EMPTY_LIST;
        }

        for (int i = 0; i < methods.length; i++)
        {
            if (methods[i].getReturnType() == void.class)
            {
                continue;
            }
            if (methods[i].getParameterTypes().length > 0)
            {
                continue;
            }
            if ((methods[i].getName().equals("class")) ||
                (methods[i].getName().equals("getClass")) ||
                (methods[i].getName().equals("toString")) ||
                (methods[i].getName().equals("annotationType")) ||
                (methods[i].getName().equals("hashCode")))
            {
                continue;
            }

            props.add(new AnnotationAttribute(methods[i].getName(), methods[i].getReturnType(), methods[i].getDefaultValue()));
        }
        return props;
    }
}
