package net.esper.type;


/**
 * Enumeration for the type of arithmatic to use.
 */
public enum MinMaxTypeEnum
{
    /**
     * Max.
     */
    MAX ("max"),

    /**
     * Min.
     */
    MIN ("min");

    private String expressionText;

    private MinMaxTypeEnum(String expressionText)
    {
        this.expressionText = expressionText;
    }

    /**
     * Returns textual representation of enum.
     * @return text for enum
     */
    public String getExpressionText()
    {
        return expressionText;
    }
}
