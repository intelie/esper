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

package com.espertech.esper.pattern;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esper.util.AuditPath;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class EvalAuditInstanceCount {

    private static final Log auditLog = LogFactory.getLog(AuditPath.AUDIT_LOG);

    private final Map<EvalNode, Integer> counts;

    public EvalAuditInstanceCount() {
        counts = new HashMap<EvalNode, Integer>();
    }

    public void decreaseRefCount(EvalNode evalNode, EvalAuditStateNode current, String patternExpr, String statementName) {
        Integer count = counts.get(evalNode);
        if (count == null) {
            return;
        }
        count--;
        if (count <= 0) {
            counts.remove(evalNode);
            print(current, patternExpr, statementName, false, 0);
            return;
        }
        counts.put(evalNode, count);
        print(current, patternExpr, statementName, false, count);


    }

    public void increaseRefCount(EvalNode evalNode, EvalAuditStateNode current, String patternExpr, String statementName) {
        Integer count = counts.get(evalNode);
        if (count == null) {
            count = 1;
        }
        else {
            count++;
        }
        counts.put(evalNode, count);
        print(current, patternExpr, statementName, true, count);
    }

    private static void print(EvalAuditStateNode current, String patternExpression, String statementName, boolean added, int count) {
        if (!auditLog.isInfoEnabled()) {
            return;
        }

        StringWriter writer = new StringWriter();

        writer.write("Statement ");
        writer.write(statementName);
        writer.write(" pattern-instance ");
        EvalAuditStateNode.writePatternExpr(current, patternExpression, writer);

        if (added) {
            writer.write(" increased to " + count);
        }
        else {
            writer.write(" decreased to " + count);
        }

        auditLog.info(writer.toString());
    }
}
