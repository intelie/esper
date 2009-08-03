package com.espertech.esper.rowregex;

public enum RegexNFATypeEnum
{
    SINGLE(false, false, null),
    ZERO_TO_MANY(true, true, true),
    ONE_TO_MANY(true, false, true),
    ONE_OPTIONAL(false, true, true),
    ZERO_TO_MANY_RELUCTANT(true, true, false),
    ONE_TO_MANY_RELUCTANT(true, false, false),
    ONE_OPTIONAL_RELUCTANT(false, true, false);

    private boolean multipleMatches;
    private boolean optional;
    private Boolean greedy;

    private RegexNFATypeEnum(boolean multipleMatches, boolean optional, Boolean greedy) {
        this.multipleMatches = multipleMatches;
        this.optional = optional;
        this.greedy = greedy;
    }

    public boolean isMultipleMatches() {
        return multipleMatches;
    }

    public boolean isOptional() {
        return optional;
    }

    public Boolean isGreedy()
    {
        return greedy;
    }

    public static RegexNFATypeEnum fromString(String code, String reluctantQuestion)
    {
        boolean reluctant = false;
        if (reluctantQuestion != null)
        {
            if (!reluctantQuestion.equals("?"))
            {
                throw new IllegalArgumentException("Invalid code for pattern type: " + code + " reluctant '" + reluctantQuestion + "'");
            }
            reluctant = true;
        }

        if (code == null)
        {
            return SINGLE;
        }
        if (code.equals("*"))
        {
            return reluctant ? ZERO_TO_MANY_RELUCTANT : ZERO_TO_MANY;
        }
        if (code.equals("+"))
        {
            return reluctant ? ONE_TO_MANY_RELUCTANT : ONE_TO_MANY;
        }
        if (code.equals("?"))
        {
            return reluctant ? ONE_OPTIONAL_RELUCTANT : ONE_OPTIONAL;
        }
        throw new IllegalArgumentException("Invalid code for pattern type: " + code);
    }

    public String getOptionalPostfix() {
        if (this == SINGLE)
        {
            return "";
        }
        if (this == ZERO_TO_MANY)
        {
            return "*";
        }
        if (this == ONE_TO_MANY)
        {
            return "+";
        }
        if (this == ONE_OPTIONAL)
        {
            return "?";
        }
        throw new IllegalArgumentException("Invalid pattern type: " + this);
    }
}
