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

package com.espertech.esper.epl.datetime.reformatop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;
import java.util.Date;

public class ReformatOpToMillisec implements ReformatOp {
    private static final Log log = LogFactory.getLog(ReformatOpToMillisec.class);

    public Object evaluate(Long ts) {
        return ts;
    }

    public Object evaluate(Date d) {
        return d.getTime();
    }

    public Object evaluate(Calendar cal) {
        return cal.getTimeInMillis();
    }

    public Class getReturnType() {
        return Long.class;
    }
}
