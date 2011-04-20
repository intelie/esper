package com.espertech.esper.epl.datetime;

import com.espertech.esper.epl.methodbase.DotMethodFP;
import com.espertech.esper.epl.methodbase.DotMethodFPInputEnum;
import com.espertech.esper.epl.methodbase.DotMethodFPParam;
import com.espertech.esper.epl.methodbase.DotMethodFPParamTypeEnum;

public class DatetimeMethodEnumParams {

    public static final DotMethodFP[] WITHTIME = new DotMethodFP[] {
                new DotMethodFP(DotMethodFPInputEnum.SCALAR_ANY,
                        new DotMethodFPParam("an integer-type hour", DotMethodFPParamTypeEnum.SPECIFIC, Integer.class),
                        new DotMethodFPParam("an integer-type minute", DotMethodFPParamTypeEnum.SPECIFIC, Integer.class),
                        new DotMethodFPParam("an integer-type second", DotMethodFPParamTypeEnum.SPECIFIC, Integer.class),
                        new DotMethodFPParam("an integer-type millis", DotMethodFPParamTypeEnum.SPECIFIC, Integer.class))
            };

    public static final DotMethodFP[] WITHDATE = new DotMethodFP[] {
                new DotMethodFP(DotMethodFPInputEnum.SCALAR_ANY,
                        new DotMethodFPParam("an integer-type year", DotMethodFPParamTypeEnum.SPECIFIC, Integer.class),
                        new DotMethodFPParam("an integer-type month", DotMethodFPParamTypeEnum.SPECIFIC, Integer.class),
                        new DotMethodFPParam("an integer-type day", DotMethodFPParamTypeEnum.SPECIFIC, Integer.class))
            };

    public static final DotMethodFP[] PLUSMINUS = new DotMethodFP[] {
                new DotMethodFP(DotMethodFPInputEnum.SCALAR_ANY,
                        new DotMethodFPParam(0, "a numeric-type millisecond", DotMethodFPParamTypeEnum.NUMERIC)),
                new DotMethodFP(DotMethodFPInputEnum.SCALAR_ANY,
                        new DotMethodFPParam("a time period", DotMethodFPParamTypeEnum.SPECIFIC, TimePeriod.class))
            };

    public static final DotMethodFP[] CALFIELD = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.SCALAR_ANY,
                            new DotMethodFPParam("a string-type calendar field name", DotMethodFPParamTypeEnum.SPECIFIC, String.class)),
            };

    public static final DotMethodFP[] CALFIELD_PLUS_INT = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.SCALAR_ANY,
                            new DotMethodFPParam("a string-type calendar field name", DotMethodFPParamTypeEnum.SPECIFIC, String.class),
                            new DotMethodFPParam("an integer-type value", DotMethodFPParamTypeEnum.SPECIFIC, Integer.class)),
            };

    public static final DotMethodFP[] NOPARAM = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.SCALAR_ANY)
            };
}
