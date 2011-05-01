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
