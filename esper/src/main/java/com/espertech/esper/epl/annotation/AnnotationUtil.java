/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.annotation;

import com.espertech.esper.client.EPStatementException;
import com.espertech.esper.client.annotation.Hint;
import com.espertech.esper.client.annotation.HintEnum;
import com.espertech.esper.client.annotation.HookType;
import com.espertech.esper.client.annotation.Hook;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.core.EngineImportException;
import com.espertech.esper.epl.core.EngineImportService;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.spec.AnnotationDesc;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.SimpleTypeCaster;
import com.espertech.esper.util.SimpleTypeCasterFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * Utility to handle EPL statement annotations.
 */
public class AnnotationUtil
{
    private static final Log log = LogFactory.getLog(AnnotationUtil.class);
    
    /**
     * Compile annotation objects from descriptors.
     * @param annotationSpec spec for annotations
     * @param engineImportService engine imports
     * @param eplStatement statement expression
     * @return annotations
     */
    public static Annotation[] compileAnnotations(List<AnnotationDesc> annotationSpec, EngineImportService engineImportService, String eplStatement)
    {
        Annotation[] annotations;
        try
        {
            annotations = AnnotationUtil.compileAnnotations(annotationSpec, engineImportService);
        }
        catch (AnnotationException e)
        {
            throw new EPStatementException("Failed to process statement annotations: " + e.getMessage(), eplStatement);
        }
        catch (RuntimeException ex)
        {
            String message = "Unexpected exception compiling annotations in statement, please consult the log file and report the exception: " + ex.getMessage();
            log.error(message, ex);
            throw new EPStatementException(message, eplStatement);
        }
        return annotations;
    }

    /**
     * Compiles annotations to an annotation array.
     * @param desc a list of descriptors
     * @param engineImportService for resolving the annotation class
     * @return annotations or empty array if none
     * @throws AnnotationException if annotations could not be created
     */
    private static Annotation[] compileAnnotations(List<AnnotationDesc> desc, EngineImportService engineImportService)
            throws AnnotationException
    {
        Annotation[] annotations = new Annotation[desc.size()];
        for (int i = 0; i < desc.size(); i++)
        {
            annotations[i] = createProxy(desc.get(i), engineImportService);
            if (annotations[i] instanceof Hint)
            {
                HintEnum.validate(annotations[i]);
            }
        }

        return annotations;
    }

    private static Annotation createProxy(AnnotationDesc desc, EngineImportService engineImportService)
            throws AnnotationException
    {
        // resolve class
        final Class annotationClass;
        try
        {
            annotationClass = engineImportService.resolveClass(desc.getName());
        }
        catch (EngineImportException e)
        {
            throw new AnnotationException("Failed to resolve @-annotation class: " + e.getMessage());
        }

        // obtain Annotation class properties
        List<AnnotationAttribute> annotationAttributeLists = getAttributes(annotationClass);
        Set<String> allAttributes = new HashSet<String>();
        Set<String> requiredAttributes = new LinkedHashSet<String>();
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
        for (Pair<String, Object> annotationValuePair : desc.getAttributes())
        {
            providedValues.add(annotationValuePair.getFirst());
        }

        // for all attributes determine value
        final Map<String, Object> properties = new LinkedHashMap<String, Object>();
        for (AnnotationAttribute annotationAttribute : annotationAttributeLists)
        {
            // find value pair for this attribute
            String attributeName = annotationAttribute.getName();
            Pair<String, Object> pairFound = null;
            for (Pair<String, Object> annotationValuePair : desc.getAttributes())
            {
                if (annotationValuePair.getFirst().equals(attributeName))
                {
                    pairFound = annotationValuePair;
                }
            }

            Object valueProvided = pairFound == null ? null : pairFound.getSecond();
            Object value = getFinalValue(annotationClass, annotationAttribute, valueProvided, engineImportService);
            properties.put(attributeName, value);
            providedValues.remove(attributeName);
            requiredAttributes.remove(attributeName);
        }

        if (requiredAttributes.size() > 0)
        {
            List<String> required = new ArrayList<String>(requiredAttributes);
            Collections.sort(required);
            throw new AnnotationException("Annotation '" + annotationClass.getSimpleName() + "' requires a value for attribute '" + required.iterator().next() + "'");
        }

        if (providedValues.size() > 0)
        {
            List<String> provided = new ArrayList<String>(providedValues);
            Collections.sort(provided);
            if (allAttributes.contains(provided.get(0)))
            {
                throw new AnnotationException("Annotation '" + annotationClass.getSimpleName() + "' has duplicate attribute values for attribute '" + provided.get(0) + "'");
            }
            else
            {
                throw new AnnotationException("Annotation '" + annotationClass.getSimpleName() + "' does not have an attribute '" + provided.get(0) + "'");
            }
        }

        // return handler
        InvocationHandler handler = new EPLAnnotationInvocationHandler(annotationClass, properties);
        return (Annotation) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {annotationClass}, handler);
    }

    private static Object getFinalValue(Class annotationClass, AnnotationAttribute annotationAttribute, Object value, EngineImportService engineImportService) throws AnnotationException
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
                return createProxy((AnnotationDesc) value, engineImportService);
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

        Collections.sort(props, new Comparator<AnnotationAttribute>()
        {
            public int compare(AnnotationAttribute o1, AnnotationAttribute o2)
            {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return props;
    }

    public static boolean isListed(String list, String lookedForValue) {
        if (list == null)
        {
            return false;
        }

        lookedForValue = lookedForValue.trim().toUpperCase();
        list = list.trim().toUpperCase();

        if (list.toUpperCase().equals(lookedForValue))
        {
            return true;
        }

        if (list.indexOf('=') != -1)
        {
            String hintName = list.substring(0, list.indexOf('='));
            if (hintName.trim().toUpperCase().equals(lookedForValue))
            {
                return true;
            }
        }

        String[] items = list.split(",");
        for (String item : items)
        {
            String listItem = item.trim().toUpperCase();
            if (listItem.equals(lookedForValue))
            {
                return true;
            }

            if (listItem.indexOf('=') != -1)
            {
                String listItemName = listItem.substring(0, listItem.indexOf('='));
                if (listItemName.trim().toUpperCase().equals(lookedForValue))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public static Annotation findAnnotation(Annotation[] annotations, Class annotationClass) {
        if (!annotationClass.isAnnotation()) {
            throw new IllegalArgumentException("Class " + annotationClass.getName() + " is not an annotation class");
        }
        if (annotations == null || annotations.length == 0) {
            return null;
        }
        for (Annotation anno : annotations) {
            if (JavaClassHelper.isImplementsInterface(anno.getClass(), annotationClass)) {
                return anno;
            }
        }
        return null;
    }

    public static String getAssignedValue(String value, String enumValue) {

        String valMixed = value.trim();
        String val = valMixed.toUpperCase();

        if (val.indexOf(",") == -1)
        {
            if (val.indexOf('=') == -1)
            {
                return null;
            }

            String hintName = val.substring(0, val.indexOf('='));
            if (!hintName.equals(enumValue))
            {
                return null;
            }
            return valMixed.substring(val.indexOf('=') + 1, val.length());
        }

        String[] hints = valMixed.split(",");
        for (String hint : hints)
        {
            int indexOfEquals = hint.indexOf('=');
            if (indexOfEquals == -1)
            {
                continue;
            }

            val = hint.substring(0, indexOfEquals).trim().toUpperCase();
            if (!val.equals(enumValue))
            {
                continue;
            }

            String strValue = hint.substring(indexOfEquals + 1).trim();
            if (strValue.length() == 0)
            {
                return null;
            }
            return strValue;
        }
        return null;
    }
}
