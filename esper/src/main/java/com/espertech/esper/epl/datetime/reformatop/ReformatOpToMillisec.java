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
