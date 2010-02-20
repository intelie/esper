package com.espertech.esper.client.soda;

public enum PatternExprPrecedenceEnum {

    MAXIMIM(Integer.MAX_VALUE),

    ATOM(7),
    GUARD(6),
    EVERY_NOT(5),
    MATCH_UNTIL(4),
    AND(3),
    OR(2),
    FOLLOWED_BY(1),

    MINIMUM(Integer.MIN_VALUE);

    private final int level;

    PatternExprPrecedenceEnum(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
