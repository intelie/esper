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
import com.espertech.esper.client.EventPropertyGetter;
import net.sf.cglib.reflect.FastMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class ExprIdentEvalSize implements ExprEvaluator
{
    private static final Log log = LogFactory.getLog(ExprIdentEvalSize.class);

    private final int streamNum;
    private final EventPropertyGetter getter;

    public ExprIdentEvalSize(int streamNum, EventPropertyGetter getter)
    {
        this.streamNum = streamNum;
        this.getter = getter;
    }

    public Class getType()
    {
        return Integer.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
	{
        EventBean event = eventsPerStream[streamNum];
        if (event == null) {
            return null;
        }
        Object result = getter.get(event);
        if (result == null) {
            return result;
        }
        if (result.getClass().isArray()) {
            return Array.getLength(result);
        }
        if (result instanceof Collection) {
            return ((Collection) result).size();
        }
        if (result instanceof Map) {
            return ((Map) result).size();
        }
        return null;
    }
}
