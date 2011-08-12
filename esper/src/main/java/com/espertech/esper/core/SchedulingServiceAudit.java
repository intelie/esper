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

package com.espertech.esper.core;

import com.espertech.esper.schedule.*;
import com.espertech.esper.util.AuditPath;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class SchedulingServiceAudit implements SchedulingServiceSPI {

    private static final Log auditLog = LogFactory.getLog(AuditPath.AUDIT_LOG);

    private final String statementName;
    private final SchedulingServiceSPI spi;

    public SchedulingServiceAudit(String statementName, SchedulingServiceSPI spi) {
        this.statementName = statementName;
        this.spi = spi;
    }

    public boolean isScheduled(ScheduleHandle handle) {
        return spi.isScheduled(handle);
    }

    public ScheduleSet take(Set<String> statementId) {
        return spi.take(statementId);
    }

    public void apply(ScheduleSet scheduleSet) {
        spi.apply(scheduleSet);
    }

    public Long getNearestTimeHandle() {
        return spi.getNearestTimeHandle();
    }

    public Map<String, Long> getStatementSchedules() {
        return spi.getStatementSchedules();
    }

    public void add(long afterMSec, ScheduleHandle handle, ScheduleSlot slot) throws ScheduleServiceException {
        if (auditLog.isInfoEnabled()) {
            StringWriter message = new StringWriter();
            message.write("Statement ");
            message.write(statementName);
            message.write(" schedule after ");
            message.write(Long.toString(afterMSec));
            message.write(" handle ");
            printHandle(message, handle);
            auditLog.info(message);

            modifyCreateProxy(handle);
        }
        spi.add(afterMSec, handle, slot);
    }

    public void add(ScheduleSpec scheduleSpec, ScheduleHandle handle, ScheduleSlot slot) throws ScheduleServiceException {
        if (auditLog.isInfoEnabled()) {
            StringWriter message = new StringWriter();
            message.write("Statement ");
            message.write(statementName);
            message.write(" schedule add ");
            message.write(scheduleSpec.toString());
            message.write(" handle ");
            printHandle(message, handle);
            auditLog.info(message);

            modifyCreateProxy(handle);
        }
        spi.add(scheduleSpec, handle, slot);
    }

    public void remove(ScheduleHandle handle, ScheduleSlot slot) throws ScheduleServiceException {
        if (auditLog.isInfoEnabled()) {
            StringWriter message = new StringWriter();
            message.write("Statement ");
            message.write(statementName);
            message.write(" schedule remove handle ");
            printHandle(message, handle);
            auditLog.info(message);
        }
        spi.remove(handle, slot);
    }

    public void setTime(long timestamp) {
        spi.setTime(timestamp);
    }

    public void evaluate(Collection<ScheduleHandle> handles) {
        spi.evaluate(handles);
    }

    public void destroy() {
        spi.destroy();
    }

    public int getTimeHandleCount() {
        return spi.getTimeHandleCount();
    }

    public Long getFurthestTimeHandle() {
        return spi.getFurthestTimeHandle();
    }

    public int getScheduleHandleCount() {
        return spi.getScheduleHandleCount();
    }

    public long getTime() {
        return spi.getTime();
    }

    private void printHandle(StringWriter message, ScheduleHandle handle) {
        if (handle instanceof EPStatementHandleCallback) {
            EPStatementHandleCallback callback = (EPStatementHandleCallback) handle;
            JavaClassHelper.writeInstance(message, callback.getScheduleCallback(), true);
        }
        else {
            JavaClassHelper.writeInstance(message, handle, true);
        }
    }

    private void modifyCreateProxy(ScheduleHandle handle) {
        if (!(handle instanceof EPStatementHandleCallback)) {
            return;
        }
        EPStatementHandleCallback callback = (EPStatementHandleCallback) handle;
        ScheduleHandleCallback sc = (ScheduleHandleCallback) ScheduleHandleCallbackProxy.newInstance(statementName, callback.getScheduleCallback());
        callback.setScheduleCallback(sc);
    }
}
