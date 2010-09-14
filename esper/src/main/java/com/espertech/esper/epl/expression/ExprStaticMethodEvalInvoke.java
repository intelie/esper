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
import com.espertech.esper.epl.core.*;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ExprStaticMethodEvalInvoke implements ExprEvaluator
{
    private static final Log log = LogFactory.getLog(ExprStaticMethodEvalInvoke.class);

    private final String classOrPropertyName;
	private final FastMethod staticMethod;
    private final ExprEvaluator[] childEvals;
    private final boolean isConstantParameters;

    private boolean isCachedResult;
    private Object cachedResult;

    public ExprStaticMethodEvalInvoke(String classOrPropertyName, FastMethod staticMethod, ExprEvaluator[] childEvals, boolean constantParameters)
    {
        this.classOrPropertyName = classOrPropertyName;
        this.staticMethod = staticMethod;
        this.childEvals = childEvals;
        isConstantParameters = constantParameters;
    }

    public Class getType()
    {
        return staticMethod.getReturnType();
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
