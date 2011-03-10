/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.enummethod.dot.*;
import com.espertech.esper.util.JavaClassHelper;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ExprDotNodeUtility
{
    public static UniformPair<ExprDotEval[]> getChainEvaluators(Class inputType,
                                                   EventType lambdaType,
                                                   List<ExprChainedSpec> chainSpec,
                                                   ValidationContext validationContext,
                                                   boolean isDuckTyping,
                                                   StreamTypeService streamTypeService)
            throws ExprValidationException
    {
        List<ExprDotEval> methodEvals = new ArrayList<ExprDotEval>();

        Class currentInputType = inputType;
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

            if (currentInputType == null) {
                throw new ExprValidationException("Cannot invoke method '" + chain.getName() + "' on this return type, for subqueries select the (*) wildcard operator");
            }

            // check if special 'size' method
            if (currentInputType.isArray() &&
                    chain.getName().toLowerCase().equals("size") &&
                    paramTypes.length == 0 &&
                    lastElement == chain) {
                ExprDotEvalArraySize sizeExpr = new ExprDotEvalArraySize();
                methodEvals.add(sizeExpr);
                currentInputType = sizeExpr.getResultType();
                continue;
            }

            // resolve 'get'
            if (currentInputType.isArray() &&
                    chain.getName().toLowerCase().equals("get") &&
                    paramTypes.length == 1 &&
                    JavaClassHelper.getBoxedType(paramTypes[0]) == Integer.class) {
                ExprDotEvalArrayGet get = new ExprDotEvalArrayGet(paramEvals[0], currentInputType.getComponentType());
                methodEvals.add(get);
                currentInputType = get.getResultType();
                continue;
            }

            // resolve lambda
            if (EnumMethodEnum.isLambda(chain.getName())) {
                EnumMethodEnum lambdaFunc = EnumMethodEnum.fromName(chain.getName());
                ExprDotEvalEnumMethod eval = (ExprDotEvalEnumMethod) JavaClassHelper.instantiate(ExprDotEval.class, lambdaFunc.getImplementation().getName());
                eval.init(lambdaFunc, chain.getName(), lambdaType, currentInputType, chain.getParameters(), validationContext, streamTypeService);
                methodEvals.add(eval);
                currentInputType = eval.getResultType();
                lastLambdaFunc = lambdaFunc;
                continue;
            }

            // Try to resolve the method
            try
            {
                Method method = validationContext.getMethodResolutionService().resolveMethod(currentInputType, chain.getName(), paramTypes);
                FastClass declaringClass = FastClass.create(Thread.currentThread().getContextClassLoader(), method.getDeclaringClass());
                FastMethod fastMethod = declaringClass.getMethod(method);
                ExprDotMethodEvalNoDuck noduck = new ExprDotMethodEvalNoDuck(fastMethod, paramEvals);
                methodEvals.add(noduck);
                currentInputType = noduck.getResultType();
            }
            catch(Exception e)
            {
                if (!isDuckTyping) {
                    throw new ExprValidationException(e.getMessage(), e);
                }
                else {
                    ExprDotMethodEvalDuck duck = new ExprDotMethodEvalDuck(validationContext.getMethodResolutionService(), chain.getName(), paramTypes, paramEvals);
                    methodEvals.add(duck);
                    currentInputType = duck.getResultType();
                }
            }
        }

        ExprDotEval[] intermediateEvals = methodEvals.toArray(new ExprDotEval[methodEvals.size()]);

        if (lastLambdaFunc != null) {
            if (lastLambdaFunc.getReturnValueType() == EnumMethodReturnType.ITERATOR_BEAN) {
                methodEvals.add(new ExprDotEvalUnpackCollEventBean());
            }
            else if (lastLambdaFunc.getReturnValueType() == EnumMethodReturnType.ITERATOR_VALUE) {
                methodEvals.add(new ExprDotEvalUnpackCollValue());
            }
            else if (lastLambdaFunc.getReturnValueType() == EnumMethodReturnType.BEAN) {
                methodEvals.add(new ExprDotEvalUnpackBean(lambdaType));
            }
        }

        ExprDotEval[] unpackingEvals = methodEvals.toArray(new ExprDotEval[methodEvals.size()]);
        return new UniformPair<ExprDotEval[]>(intermediateEvals, unpackingEvals);
    }
}
