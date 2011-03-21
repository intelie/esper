/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.enummethod.dot.ExprDotStaticMethodWrap;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ExprDotEvalStaticMethod implements ExprEvaluator
{
    private static final Log log = LogFactory.getLog(ExprDotEvalStaticMethod.class);

    private final String classOrPropertyName;
	private final FastMethod staticMethod;
    private final ExprEvaluator[] childEvals;
    private final boolean isConstantParameters;
    private final ExprDotEval[] chainEval;
    private final ExprDotStaticMethodWrap resultWrapLambda;

    private boolean isCachedResult;
    private Object cachedResult;

    public ExprDotEvalStaticMethod(String classOrPropertyName,
                                   FastMethod staticMethod,
                                   ExprEvaluator[] childEvals,
                                   boolean constantParameters,
                                   ExprDotStaticMethodWrap resultWrapLambda,
                                   ExprDotEval[] chainEval)
    {
        this.classOrPropertyName = classOrPropertyName;
        this.staticMethod = staticMethod;
        this.childEvals = childEvals;
        this.isConstantParameters = constantParameters;
        this.resultWrapLambda = resultWrapLambda;
        this.chainEval = chainEval;
    }

    public Class getType()
    {
        if (chainEval.length == 0) {
            return staticMethod.getReturnType();
        }
        else {
            return chainEval[chainEval.length - 1].getTypeInfo().getScalar();
        }
    }

    public Map<String, Object> getEventType() {
        return null;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
	{
        if ((isConstantParameters) && (isCachedResult))
        {
            return cachedResult;
        }

		Object[] args = new Object[childEvals.length];
		for(int i = 0; i < args.length; i++)
		{
			args[i] = childEvals[i].evaluate(eventsPerStream, isNewData, exprEvaluatorContext);
		}

		// The method is static so the object it is invoked on
		// can be null
		Object obj = null;
		try
		{
            Object result = staticMethod.invoke(obj, args);
            if (isConstantParameters)
            {
                cachedResult = result;
                isCachedResult = true;
            }

            if (resultWrapLambda != null) {
                result = resultWrapLambda.convert(result);
            }

            if (chainEval.length == 0) {
                return result;
            }

            for (int i = 0; i < chainEval.length; i++) {
                result = chainEval[i].evaluate(result, eventsPerStream, isNewData, exprEvaluatorContext);
                if (result == null) {
                    return result;
                }
            }
            return result;
		}
		catch (InvocationTargetException e)
		{
            String message = "Method '" + staticMethod.getName() +
                    "' of class '" + classOrPropertyName +
                    "' reported an exception: " +
                    e.getTargetException();
            log.error(message, e.getTargetException());
		}
        return null;
    }
}
