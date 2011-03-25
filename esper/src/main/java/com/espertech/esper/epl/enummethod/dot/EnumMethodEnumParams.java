package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.epl.methodbase.DotMethodFP;
import com.espertech.esper.epl.methodbase.DotMethodFPInputEnum;
import com.espertech.esper.epl.methodbase.DotMethodFPParam;
import com.espertech.esper.epl.methodbase.DotMethodFPParamTypeEnum;

public class EnumMethodEnumParams {

    public static final DotMethodFP[] NOOP_REVERSE = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.ANY),
            };

    public static final DotMethodFP[] COUNTOF_FIRST_LAST = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.ANY),
                    new DotMethodFP(DotMethodFPInputEnum.ANY, new DotMethodFPParam(1, "predicate", DotMethodFPParamTypeEnum.BOOLEAN)),
            };

    public static final DotMethodFP[] TAKE_TAKELAST = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.ANY),
                    new DotMethodFP(DotMethodFPInputEnum.ANY, new DotMethodFPParam(0, "count", DotMethodFPParamTypeEnum.NUMERIC)),
            };

    public static final DotMethodFP[] AGGREGATE_FP = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.ANY,
                                            new DotMethodFPParam(0, "initialization-value", DotMethodFPParamTypeEnum.ANY),
                                            new DotMethodFPParam(2, "(result, next)", DotMethodFPParamTypeEnum.ANY)),
            };

    public static final DotMethodFP[] ALLOF_ANYOF = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.ANY, new DotMethodFPParam(1, "predicate", DotMethodFPParamTypeEnum.BOOLEAN)),
            };

    public static final DotMethodFP[] MIN_MAX = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.SCALAR_ANY),
                    new DotMethodFP(DotMethodFPInputEnum.EVENTCOLL, new DotMethodFPParam(1, "predicate", DotMethodFPParamTypeEnum.ANY)),
            };

    public static final DotMethodFP[] SELECTFROM_MINBY_MAXBY = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.EVENTCOLL, new DotMethodFPParam(1, "predicate", DotMethodFPParamTypeEnum.ANY)),
            };

    public static final DotMethodFP[] AVERAGE_SUMOF = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.SCALAR_NUMERIC),
                    new DotMethodFP(DotMethodFPInputEnum.EVENTCOLL, new DotMethodFPParam(1, "predicate", DotMethodFPParamTypeEnum.NUMERIC))
            };

    public static final DotMethodFP[] MOST_LEAST_FREQ = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.SCALAR_ANY),
                    new DotMethodFP(DotMethodFPInputEnum.EVENTCOLL, new DotMethodFPParam(1, "predicate", DotMethodFPParamTypeEnum.ANY))
            };

    public static final DotMethodFP[] MAP = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.EVENTCOLL, new DotMethodFPParam(1, "key-selector", DotMethodFPParamTypeEnum.ANY),
                                            new DotMethodFPParam(1, "value-selector", DotMethodFPParamTypeEnum.ANY)),
            };

    public static final DotMethodFP[] GROUP = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.EVENTCOLL, new DotMethodFPParam(1, "key-selector", DotMethodFPParamTypeEnum.ANY)),
                    new DotMethodFP(DotMethodFPInputEnum.EVENTCOLL, new DotMethodFPParam(1, "key-selector", DotMethodFPParamTypeEnum.ANY),
                                            new DotMethodFPParam(1, "value-selector", DotMethodFPParamTypeEnum.ANY)),
            };

    public static final DotMethodFP[] ORDERBY = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.SCALAR_ANY),
                    new DotMethodFP(DotMethodFPInputEnum.EVENTCOLL, new DotMethodFPParam(1, "compare-selector", DotMethodFPParamTypeEnum.ANY)),
            };

    public static final DotMethodFP[] WHERE_FP = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.ANY, new DotMethodFPParam(1, "predictate", DotMethodFPParamTypeEnum.BOOLEAN)),
                    new DotMethodFP(DotMethodFPInputEnum.ANY, new DotMethodFPParam(2, "(predictate, index)", DotMethodFPParamTypeEnum.BOOLEAN))
            };

    public static final DotMethodFP[] UNION_FP = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.ANY, new DotMethodFPParam(0, "collection", DotMethodFPParamTypeEnum.ANY)),
            };

    public static final DotMethodFP[] SEQ_EQUALS_FP = new DotMethodFP[] {
                    new DotMethodFP(DotMethodFPInputEnum.SCALAR_ANY, new DotMethodFPParam(0, "sequence", DotMethodFPParamTypeEnum.ANY)),
            };
}
