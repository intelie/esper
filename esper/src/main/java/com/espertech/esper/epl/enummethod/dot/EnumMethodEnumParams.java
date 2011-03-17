package com.espertech.esper.epl.enummethod.dot;

public class EnumMethodEnumParams {

    public static final EnumMethodFootprint[] EMPTY_FP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(),
            };

    public static final EnumMethodFootprint[] SINGLE_PREDICATE_BOOL_OR_NONE = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(),
                    new EnumMethodFootprint(new EnumMethodParam(1, "predicate", EnumMethodEnumParamType.BOOLEAN)),
            };

    public static final EnumMethodFootprint[] SINGLE_NUMERIC_FP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(),
                    new EnumMethodFootprint(new EnumMethodParam(0, "count", EnumMethodEnumParamType.NUMERIC)),
            };

    public static final EnumMethodFootprint[] AGGREGATE_FP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(new EnumMethodParam(0, "initialization-value", EnumMethodEnumParamType.ANY),
                                            new EnumMethodParam(2, "(result, next)", EnumMethodEnumParamType.ANY)),
            };

    public static final EnumMethodFootprint[] SINGLE_PREDICATE_BOOL = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(new EnumMethodParam(1, "predicate", EnumMethodEnumParamType.BOOLEAN)),
            };

    public static final EnumMethodFootprint[] SINGLE_PREDICATE_ANY = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(new EnumMethodParam(1, "predicate", EnumMethodEnumParamType.ANY)),
            };

    public static final EnumMethodFootprint[] SINGLE_PREDICATE_NUMERIC = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(new EnumMethodParam(1, "predicate", EnumMethodEnumParamType.NUMERIC))
            };

    public static final EnumMethodFootprint[] MAP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(new EnumMethodParam(1, "key-selector", EnumMethodEnumParamType.ANY),
                                            new EnumMethodParam(1, "value-selector", EnumMethodEnumParamType.ANY)),
            };

    public static final EnumMethodFootprint[] GROUP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(new EnumMethodParam(1, "key-selector", EnumMethodEnumParamType.ANY)),
                    new EnumMethodFootprint(new EnumMethodParam(1, "key-selector", EnumMethodEnumParamType.ANY),
                                            new EnumMethodParam(1, "value-selector", EnumMethodEnumParamType.ANY)),
            };

    public static final EnumMethodFootprint[] ORDERBY = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(new EnumMethodParam(1, "compare-selector", EnumMethodEnumParamType.ANY)),
            };

    public static final EnumMethodFootprint[] WHERE_FP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(new EnumMethodParam(1, "predictate", EnumMethodEnumParamType.BOOLEAN)),
                    new EnumMethodFootprint(new EnumMethodParam(2, "(predictate, index)", EnumMethodEnumParamType.BOOLEAN))
            };

    public static final EnumMethodFootprint[] UNION_FP = new EnumMethodFootprint[] {
                    new EnumMethodFootprint(new EnumMethodParam(0, "collection", EnumMethodEnumParamType.ANY)),
            };
}
