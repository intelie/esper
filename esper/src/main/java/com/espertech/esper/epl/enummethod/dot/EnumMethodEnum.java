package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.epl.enummethod.eval.*;

public enum EnumMethodEnum {

    AGGREGATE("aggregate", ExprDotEvalAggregate.class, EnumMethodEnumParams.AGGREGATE_FP),

    ALLOF("allOf", ExprDotEvalAllOfAnyOf.class, EnumMethodEnumParams.ALLOF_ANYOF),
    ANYOF("anyOf", ExprDotEvalAllOfAnyOf.class, EnumMethodEnumParams.ALLOF_ANYOF),

    TOMAP("toMap", ExprDotEvalToMap.class, EnumMethodEnumParams.MAP),
    GROUPBY("groupBy", ExprDotEvalGroupBy.class, EnumMethodEnumParams.GROUP),

    COUNTOF("countOf", ExprDotEvalCountOf.class, EnumMethodEnumParams.COUNTOF_FIRST_LAST),
    MIN("min", ExprDotEvalMinMax.class, EnumMethodEnumParams.MIN_MAX),
    MAX("max", ExprDotEvalMinMax.class, EnumMethodEnumParams.MIN_MAX),
    AVERAGE("average", ExprDotEvalAverage.class, EnumMethodEnumParams.AVERAGE_SUMOF),
    SUMOF("sumOf", ExprDotEvalSumOf.class, EnumMethodEnumParams.AVERAGE_SUMOF),

    SELECTFROM("selectFrom", ExprDotEvalSelectFrom.class, EnumMethodEnumParams.SELECTFROM_MINBY_MAXBY),

    FIRST("firstOf", ExprDotEvalFirstLastOf.class, EnumMethodEnumParams.COUNTOF_FIRST_LAST),
    LAST("lastOf", ExprDotEvalFirstLastOf.class, EnumMethodEnumParams.COUNTOF_FIRST_LAST),
    MINBY("minBy", ExprDotEvalMinByMaxBy.class, EnumMethodEnumParams.SELECTFROM_MINBY_MAXBY),
    MAXBY("maxBy", ExprDotEvalMinByMaxBy.class, EnumMethodEnumParams.SELECTFROM_MINBY_MAXBY),

    TAKE("take", ExprDotEvalTakeAndTakeLast.class, EnumMethodEnumParams.TAKE_TAKELAST),
    TAKELAST("takeLast", ExprDotEvalTakeAndTakeLast.class, EnumMethodEnumParams.TAKE_TAKELAST),
    TAKEWHILE("takeWhile", ExprDotEvalTakeWhileAndLast.class, EnumMethodEnumParams.WHERE_FP),
    TAKEWHILELAST("takeWhileLast", ExprDotEvalTakeWhileAndLast.class, EnumMethodEnumParams.WHERE_FP),
    ORDERBY("orderBy", ExprDotEvalOrderByAscDesc.class, EnumMethodEnumParams.ORDERBY),
    ORDERBYDESC("orderByDesc", ExprDotEvalOrderByAscDesc.class, EnumMethodEnumParams.ORDERBY),
    WHERE("where", ExprDotEvalWhere.class, EnumMethodEnumParams.WHERE_FP),
    UNION("union", ExprDotEvalUnion.class, EnumMethodEnumParams.UNION_FP),
    REVERSE("reverse", ExprDotEvalReverse.class, EnumMethodEnumParams.NOOP_REVERSE),
    NOOP("esperInternalNoop", ExprDotEvalNoOp.class, EnumMethodEnumParams.NOOP_REVERSE),

    SEQUENCE_EQUALS("sequenceequals", ExprDotEvalSequenceEqual.class, EnumMethodEnumParams.SEQ_EQUALS_FP),
    ;

    private final String nameCamel;
    private final Class implementation;
    private final EnumMethodFootprint[] footprints;

    private EnumMethodEnum(String nameCamel, Class implementation, EnumMethodFootprint[] footprints) {
        this.nameCamel = nameCamel;
        this.implementation = implementation;
        this.footprints = footprints;
    }

    public String getNameCamel() {
        return nameCamel;
    }

    public EnumMethodFootprint[] getFootprints() {
        return footprints;
    }

    public static boolean isLambda(String name) {
        for (EnumMethodEnum e : EnumMethodEnum.values()) {
            if (e.getNameCamel().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static EnumMethodEnum fromName(String name) {
        for (EnumMethodEnum e : EnumMethodEnum.values()) {
            if (e.getNameCamel().toLowerCase().equals(name.toLowerCase())) {
                return e;
            }
        }
        return null;
    }

    public Class getImplementation() {
        return implementation;
    }
}
