package com.espertech.esper.client.soda;

public enum ExpressionPrecedenceEnum {

    UNARY(11),
    MULTIPLY(10),
    ADDITIVE(9),
    CONCAT(8),
    RELATIONAL_BETWEEN_IN(7),
    EQUALS(6),
    NEGATED(5),
    BITWISE(4),
    AND(3),
    OR(2),
    CASE(1),

    MINIMUM(Integer.MIN_VALUE);

    private final int level;

    ExpressionPrecedenceEnum(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
