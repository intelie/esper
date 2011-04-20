package com.espertech.esper.epl.datetime.reformatop;

import java.util.Calendar;
import java.util.Date;

public interface ReformatOp {
    public Object evaluate(Long ts);
    public Object evaluate(Date d);
    public Object evaluate(Calendar cal);
    public Class getReturnType();
}
