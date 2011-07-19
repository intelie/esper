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
import com.espertech.esper.util.AuditPath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

public class ExprIdentNodeEvaluatorLogging extends ExprIdentNodeEvaluator
{
    private static final Log auditLog = LogFactory.getLog(AuditPath.AUDIT_LOG);

    private final String propertyName;
    private final String statementName;

    public ExprIdentNodeEvaluatorLogging(int streamNum, EventPropertyGetter propertyGetter, Class propertyType, String propertyName, String statementName) {
        super(streamNum, propertyGetter, propertyType);
        this.propertyName = propertyName;
        this.statementName = statementName;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
    {
        Object result = super.evaluate(eventsPerStream, isNewData, exprEvaluatorContext);
        if (auditLog.isInfoEnabled()) {
            auditLog.info("Statement " + statementName + " property " + propertyName + " value " + result);
        }
        return result;
    }
}
