package com.espertech.esper.epl.enummethod.dot;

public class EnumMethodEnumParams {

    public static final EnumMethodFootprint[] NOOP_REVERSE = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.ANY),
            };

    public static final EnumMethodFootprint[] COUNTOF_FIRST_LAST = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.ANY),
                    new EnumMethodFootprint(EnumMethodInputEnum.ANY, new EnumMethodParam(1, "predicate", EnumMethodEnumParamType.BOOLEAN)),
            };

    public static final EnumMethodFootprint[] TAKE_TAKELAST = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.ANY),
                    new EnumMethodFootprint(EnumMethodInputEnum.ANY, new EnumMethodParam(0, "count", EnumMethodEnumParamType.NUMERIC)),
            };

    public static final EnumMethodFootprint[] AGGREGATE_FP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.ANY,
                                            new EnumMethodParam(0, "initialization-value", EnumMethodEnumParamType.ANY),
                                            new EnumMethodParam(2, "(result, next)", EnumMethodEnumParamType.ANY)),
            };

    public static final EnumMethodFootprint[] ALLOF_ANYOF = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.ANY, new EnumMethodParam(1, "predicate", EnumMethodEnumParamType.BOOLEAN)),
            };

    public static final EnumMethodFootprint[] MIN_MAX = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.SCALAR_ANY),
                    new EnumMethodFootprint(EnumMethodInputEnum.EVENTCOLL, new EnumMethodParam(1, "predicate", EnumMethodEnumParamType.ANY)),
            };

    public static final EnumMethodFootprint[] SELECTFROM_MINBY_MAXBY = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.EVENTCOLL, new EnumMethodParam(1, "predicate", EnumMethodEnumParamType.ANY)),
            };

    public static final EnumMethodFootprint[] AVERAGE_SUMOF = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.SCALAR_NUMERIC),
                    new EnumMethodFootprint(EnumMethodInputEnum.EVENTCOLL, new EnumMethodParam(1, "predicate", EnumMethodEnumParamType.NUMERIC))
            };

    public static final EnumMethodFootprint[] MAP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.EVENTCOLL, new EnumMethodParam(1, "key-selector", EnumMethodEnumParamType.ANY),
                                            new EnumMethodParam(1, "value-selector", EnumMethodEnumParamType.ANY)),
            };

    public static final EnumMethodFootprint[] GROUP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.EVENTCOLL, new EnumMethodParam(1, "key-selector", EnumMethodEnumParamType.ANY)),
                    new EnumMethodFootprint(EnumMethodInputEnum.EVENTCOLL, new EnumMethodParam(1, "key-selector", EnumMethodEnumParamType.ANY),
                                            new EnumMethodParam(1, "value-selector", EnumMethodEnumParamType.ANY)),
            };

    public static final EnumMethodFootprint[] ORDERBY = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.SCALAR_ANY),
                    new EnumMethodFootprint(EnumMethodInputEnum.EVENTCOLL, new EnumMethodParam(1, "compare-selector", EnumMethodEnumParamType.ANY)),
            };

    public static final EnumMethodFootprint[] WHERE_FP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.ANY, new EnumMethodParam(1, "predictate", EnumMethodEnumParamType.BOOLEAN)),
                    new EnumMethodFootprint(EnumMethodInputEnum.ANY, new EnumMethodParam(2, "(predictate, index)", EnumMethodEnumParamType.BOOLEAN))
            };

    public static final EnumMethodFootprint[] UNION_FP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.ANY, new EnumMethodParam(0, "collection", EnumMethodEnumParamType.ANY)),
            };

    public static final EnumMethodFootprint[] SEQ_EQUALS_FP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(EnumMethodInputEnum.SCALAR_ANY, new EnumMethodParam(0, "sequence", EnumMethodEnumParamType.ANY)),
            };
}
