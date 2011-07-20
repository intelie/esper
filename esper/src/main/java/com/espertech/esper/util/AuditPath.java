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

package com.espertech.esper.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Global boolean for enabling and disable audit path reporting.
 */
public class AuditPath {

    private static final Log log = LogFactory.getLog(AuditPath.class);

    /**
     * Log destination for the query plan logging.
     */
    public static final String QUERYPLAN_LOG = "com.espertech.esper.queryplan"; 

    /**
     * Log destination for the JDBC logging.
     */
    public static final String JDBC_LOG = "com.espertech.esper.jdbc"; 

    /**
     * Log destination for the audit logging.
     */
    public static final String AUDIT_LOG = "com.espertech.esper.audit"; 

    /**
     * Public access.
     */
    public static boolean isAuditEnabled = false;

    /**
     * Sets execution path debug logging.
     * @param auditEnabled true if metric reporting should be enabled
     */
    public static void setAuditEnabled(boolean auditEnabled)
    {
        if (auditEnabled)
        {
            log.debug("Audit reporting has been enabled.");
        }
        else
        {
            log.debug("Audit reporting has been disabled.");
        }
        isAuditEnabled = auditEnabled;
    }
}