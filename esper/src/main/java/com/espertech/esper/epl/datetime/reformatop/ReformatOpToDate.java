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

public class ReformatOpToDate implements ReformatOp {
    private static final Log log = LogFactory.getLog(ReformatOpToDate.class);

    public Object evaluate(Long ts) {
        return new Date(ts);
    }

    public Object evaluate(Date d) {
        return d;
    }

    public Object evaluate(Calendar cal) {
        return cal.getTime();
    }

    public Class getReturnType() {
        return Date.class;
    }
}
