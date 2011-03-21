/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.*;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.event.map.MapEventType;
import com.espertech.esper.util.JavaClassHelper;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExprDotNodeUtility
{
    public static UniformPair<ExprDotEval[]> getChainEvaluators(ExprDotEvalTypeInfo inputType,
                                                   List<ExprChainedSpec> chainSpec,
                                                   ValidationContext validationContext,
                                                   boolean isDuckTyping,
                                                   StreamTypeService streamTypeService)
            throws ExprValidationException
    {
        List<ExprDotEval> methodEvals = new ArrayList<ExprDotEval>();
        ExprDotEvalTypeInfo currentInputType = inputType;
        EnumMethodEnum lastLambdaFunc = null;
        ExprChainedSpec lastElement = chainSpec.isEmpty() ? null : chainSpec.get(chainSpec.size() - 1);

        for (ExprChainedSpec chain : chainSpec) {
            lastLambdaFunc = null;  // reset

            // compile parameters for chain element
            ExprEvaluator[] paramEvals = new ExprEvaluator[chain.getParameters().size()];
            Class[] paramTypes = new Class[chain.getParameters().size()];
            for (int i = 0; i < chain.getParameters().size(); i++) {
                paramEvals[i] = chain.getParameters().get(i).getExprEvaluator();
                paramTypes[i] = paramEvals[i].getType();
            }

            // check if special 'size' method
            if ( (currentInputType.isScalar() && currentInputType.getScalar().isArray()) ||
                 (currentInputType.getComponent() != null)) {
                if (chain.getName().toLowerCase().equals("size") && paramTypes.length == 0 && lastElement == chain) {
                    ExprDotEvalArraySize sizeExpr = new ExprDotEvalArraySize();
                    methodEvals.add(sizeExpr);
                    currentInputType = sizeExpr.getTypeInfo();
                    continue;
                }
                if (chain.getName().toLowerCase().equals("get") && paramTypes.length == 1 && JavaClassHelper.getBoxedType(paramTypes[0]) == Integer.class) {
                    Class componentType = currentInputType.getComponent() != null ? currentInputType.getComponent() : currentInputType.getScalar().getComponentType();
                    ExprDotEvalArrayGet get = new ExprDotEvalArrayGet(paramEvals[0], componentType);
                    methodEvals.add(get);
                    currentInputType = get.getTypeInfo();
                    continue;
                }
            }

            // resolve lambda
            if (EnumMethodEnum.isLambda(chain.getName())) {
                EnumMethodEnum lambdaFunc = EnumMethodEnum.fromName(chain.getName());
                ExprDotEvalEnumMethod eval = (ExprDotEvalEnumMethod) JavaClassHelper.instantiate(ExprDotEval.class, lambdaFunc.getImplementation().getName());
                eval.init(lambdaFunc, chain.getName(), currentInputType, chain.getParameters(), validationContext, streamTypeService);
                currentInputType = eval.getTypeInfo();
                if (currentInputType == null) {
                    throw new IllegalStateException("Enumeration method '" + chain.getName() + "' has not returned type information");
                }
                methodEvals.add(eval);
                lastLambdaFunc = lambdaFunc;
                continue;
            }

            // try to resolve as property if the last method returned a type
            if (currentInputType.getEventType() != null) {
                Class type = currentInputType.getEventType().getPropertyType(chain.getName());
                EventPropertyGetter getter = currentInputType.getEventType().getGetter(chain.getName());
                if (type != null && getter != null) {
                    ExprDotEvalProperty noduck = new ExprDotEvalProperty(getter, ExprDotEvalTypeInfo.scalarOrUnderlying(JavaClassHelper.getBoxedType(type)));
                    methodEvals.add(noduck);
                    currentInputType = ExprDotEvalTypeInfo.scalarOrUnderlying(noduck.getTypeInfo().getScalar());
                    continue;
                }

                // preresolve as method
                try {
                    if (currentInputType.isScalar()) {
                        validationContext.getMethodResolutionService().resolveMethod(currentInputType.getScalar(), chain.getName(), paramTypes);
                    }
                }
                catch (Exception ex) {
                    throw new ExprValidationException("Could not resolve '" + chain.getName() + "' to a property of event type '" + currentInputType.getEventType().getName() + "' or method on type '" + currentInputType + "'");
                }
            }

            // Try to resolve the method
            if (currentInputType.isScalar()) {
                try
                {
                    Method method = validationContext.getMethodResolutionService().resolveMethod(currentInputType.getScalar(), chain.getName(), paramTypes);
                    FastClass declaringClass = FastClass.create(Thread.currentThread().getContextClassLoader(), method.getDeclaringClass());
                    FastMethod fastMethod = declaringClass.getMethod(method);
                    ExprDotMethodEvalNoDuck noduck = new ExprDotMethodEvalNoDuck(fastMethod, paramEvals);
                    methodEvals.add(noduck);
                    currentInputType = noduck.getTypeInfo();
                }
                catch(Exception e)
                {
                    if (!isDuckTyping) {
                        throw new ExprValidationException(e.getMessage(), e);
                    }
                    else {
                        ExprDotMethodEvalDuck duck = new ExprDotMethodEvalDuck(validationContext.getMethodResolutionService(), chain.getName(), paramTypes, paramEvals);
                        methodEvals.add(duck);
                        currentInputType = duck.getTypeInfo();
                    }
                }
                continue;
            }

            String message = "Could not find event property, enumeration method or instance method named '" +
                    chain.getName() + " in " + currentInputType.toTypeName();
            throw new ExprValidationException(message);
        }

        ExprDotEval[] intermediateEvals = methodEvals.toArray(new ExprDotEval[methodEvals.size()]);

        if (lastLambdaFunc != null) {
            if (currentInputType.getEventTypeColl() != null) {
                methodEvals.add(new ExprDotEvalUnpackCollEventBean(currentInputType.getEventTypeColl()));
            }
            else if (currentInputType.getEventType() != null) {
                methodEvals.add(new ExprDotEvalUnpackBean(currentInputType.getEventType()));
            }
        }

        ExprDotEval[] unpackingEvals = methodEvals.toArray(new ExprDotEval[methodEvals.size()]);
        return new UniformPair<ExprDotEval[]>(intermediateEvals, unpackingEvals);
    }

    public static MapEventType makeTransientMapType(String enumMethod, String propertyName, Class type) {
        Map<String, Object> propsResult = new HashMap<String, Object>();
        propsResult.put(propertyName, type);
        String typeName = enumMethod + "__" + propertyName;
        return new MapEventType(EventTypeMetadata.createAnonymous(typeName), typeName, null, propsResult, null, null);
    }
}
