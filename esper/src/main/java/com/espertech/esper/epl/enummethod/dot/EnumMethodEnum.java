package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.epl.enummethod.eval.*;

public enum EnumMethodEnum {

    AGGREGATE("aggregate", EnumMethodReturnType.VALUE, ExprDotEvalAggregate.class, EnumMethodEnumParams.AGGREGATE_FP),

    ALLOF("allOf", EnumMethodReturnType.VALUE, ExprDotEvalAllOf.class, EnumMethodEnumParams.SINGLE_PREDICATE_BOOL),
    ANYOF("anyOf", EnumMethodReturnType.VALUE, ExprDotEvalAnyOf.class, EnumMethodEnumParams.SINGLE_PREDICATE_BOOL),

    TOMAP("toMap", EnumMethodReturnType.VALUE, ExprDotEvalToMap.class, EnumMethodEnumParams.MAP),
    GROUPBY("groupBy", EnumMethodReturnType.VALUE, ExprDotEvalGroupBy.class, EnumMethodEnumParams.GROUP),

    COUNTOF("countOf", EnumMethodReturnType.VALUE, ExprDotEvalCountOf.class, EnumMethodEnumParams.EMPTY_PLUS_PRED_FP),
    MIN("min", EnumMethodReturnType.VALUE, ExprDotEvalMin.class, EnumMethodEnumParams.SINGLE_PREDICATE_ANY),
    MAX("max", EnumMethodReturnType.VALUE, ExprDotEvalMax.class, EnumMethodEnumParams.SINGLE_PREDICATE_ANY),
    AVERAGE("average", EnumMethodReturnType.VALUE, ExprDotEvalAverage.class, EnumMethodEnumParams.SINGLE_PREDICATE_NUMERIC),
    SUMOF("sumOf", EnumMethodReturnType.VALUE, ExprDotEvalSumOf.class, EnumMethodEnumParams.SINGLE_PREDICATE_NUMERIC),

    SELECTFROM("selectFrom", EnumMethodReturnType.ITERATOR_VALUE, ExprDotEvalSelectFrom.class, EnumMethodEnumParams.SINGLE_PREDICATE_ANY),

    FIRST("firstOf", EnumMethodReturnType.BEAN, ExprDotEvalFirstOf.class, EnumMethodEnumParams.SINGLE_PREDICATE_BOOL),
    LAST("lastOf", EnumMethodReturnType.BEAN, ExprDotEvalLastOf.class, EnumMethodEnumParams.SINGLE_PREDICATE_BOOL),
    MINBY("minBy", EnumMethodReturnType.BEAN, ExprDotEvalMinBy.class, EnumMethodEnumParams.SINGLE_PREDICATE_ANY),
    MAXBY("maxBy", EnumMethodReturnType.BEAN, ExprDotEvalMaxBy.class, EnumMethodEnumParams.SINGLE_PREDICATE_ANY),

    TAKE("take", EnumMethodReturnType.ITERATOR_BEAN, ExprDotEvalTake.class, EnumMethodEnumParams.SINGLE_NUMERIC_FP),
    TAKELAST("takeLast", EnumMethodReturnType.ITERATOR_BEAN, ExprDotEvalTake.class, EnumMethodEnumParams.SINGLE_NUMERIC_FP),
    TAKEWHILE("takeWhile", EnumMethodReturnType.ITERATOR_BEAN, ExprDotEvalTakeWhile.class, EnumMethodEnumParams.WHERE_FP),
    TAKEWHILELAST("takeWhileLast", EnumMethodReturnType.ITERATOR_BEAN, ExprDotEvalTakeWhile.class, EnumMethodEnumParams.WHERE_FP),
    ORDERBY("orderBy", EnumMethodReturnType.ITERATOR_BEAN, ExprDotEvalOrderByAscDesc.class, EnumMethodEnumParams.ORDERBY),
    ORDERBYDESC("orderByDesc", EnumMethodReturnType.ITERATOR_BEAN, ExprDotEvalOrderByAscDesc.class, EnumMethodEnumParams.ORDERBY),
    WHERE("where", EnumMethodReturnType.ITERATOR_BEAN, ExprDotEvalWhere.class, EnumMethodEnumParams.WHERE_FP),
    UNION("union", EnumMethodReturnType.ITERATOR_BEAN, ExprDotEvalUnion.class, EnumMethodEnumParams.UNION_FP),
    REVERSE("reverse", EnumMethodReturnType.ITERATOR_BEAN, ExprDotEvalReverse.class, EnumMethodEnumParams.EMPTY_FP),
    NOOP("esperInternalNoop", EnumMethodReturnType.ITERATOR_BEAN, ExprDotEvalNoOp.class, EnumMethodEnumParams.EMPTY_FP),
    ;

    private String nameCamel;
    private EnumMethodReturnType returnValueType;
    private Class implementation;
    private EnumMethodFootprint[] footprints;

    private EnumMethodEnum(String nameCamel, EnumMethodReturnType returnValueType, Class implementation, EnumMethodFootprint[] footprints) {
        this.nameCamel = nameCamel;
        this.returnValueType = returnValueType;
        this.implementation = implementation;
        this.footprints = footprints;
    }

    public EnumMethodReturnType getReturnValueType() {
        return returnValueType;
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
