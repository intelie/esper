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

package com.espertech.esper.schedule;

import com.espertech.esper.core.SchedulingServiceAudit;
import com.espertech.esper.util.AuditPath;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringWriter;
import java.lang.reflect.Method;

public class ScheduleHandleCallbackProxy implements java.lang.reflect.InvocationHandler {

    private static final Log auditLog = LogFactory.getLog(AuditPath.AUDIT_LOG);
    private static Method target = JavaClassHelper.getMethodByName(ScheduleHandleCallback.class, "scheduledTrigger");

    private final String statementName;
    private final ScheduleHandleCallback scheduleHandleCallback;

    public static Object newInstance(String statementName, ScheduleHandleCallback scheduleHandleCallback) {
        return java.lang.reflect.Proxy.newProxyInstance(
                scheduleHandleCallback.getClass().getClassLoader(),
                JavaClassHelper.getSuperInterfaces(scheduleHandleCallback.getClass()),
                new ScheduleHandleCallbackProxy(statementName, scheduleHandleCallback));
    }

    public ScheduleHandleCallbackProxy(String statementName, ScheduleHandleCallback scheduleHandleCallback) {
        this.statementName = statementName;
        this.scheduleHandleCallback = scheduleHandleCallback;
    }

    public Object invoke(Object proxy, Method m, Object[] args)
            throws Throwable {

        if (m.getName().equals(target.getName())) {
            if (auditLog.isInfoEnabled()) {
                StringWriter message = new StringWriter();
                message.write("Statement ");
                message.write(statementName);
                message.write(" schedule trigger handle ");
                JavaClassHelper.writeInstance(message, scheduleHandleCallback, true);
                auditLog.info(message);
            }
        }

        return m.invoke(scheduleHandleCallback, args);
    }
}

