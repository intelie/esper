package com.espertech.esper.client.deploy;

import java.util.Set;
import java.util.List;

/**
 * Represents an EPL statement as part of a {@link Module}.
 * <p>
 * Character position start and end are only available for non-comment only.
 */
public class ModuleItem {
    private String expression;
    private boolean commentOnly;
    private int lineNumber;
    private int charPosStart;
    private int charPosEnd;

    /**
     * Ctor.
     * @param expression EPL
     * @param commentOnly true if the statement consists only of comments or whitespace
     * @param lineNumber line number
     * @param charPosStart character position of start of segment
     * @param charPosEnd character position of end of segment
     */
    public ModuleItem(String expression, boolean commentOnly, int lineNumber, int charPosStart, int charPosEnd)
    {
        this.expression = expression;
        this.commentOnly = commentOnly;
        this.lineNumber = lineNumber;
        this.charPosStart = charPosStart;
        this.charPosEnd = charPosEnd;
    }

    /**
     * Returns the EPL.
     * @return expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Sets the EPL.
     * @param expression to set
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * Returns true to indicate comments-only expression.
     * @return comments-only indicator
     */
    public boolean isCommentOnly() {
        return commentOnly;
    }

    /**
     * Set true to indicate comments-only expression.
     * @param commentOnly comments-only indicator
     */
    public void setCommentOnly(boolean commentOnly) {
        this.commentOnly = commentOnly;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    public int getCharPosStart()
    {
        return charPosStart;
    }

    public void setCharPosStart(int charPosStart)
    {
        this.charPosStart = charPosStart;
    }

    public int getCharPosEnd()
    {
        return charPosEnd;
    }

    public void setCharPosEnd(int charPosEnd)
    {
        this.charPosEnd = charPosEnd;
    }
}
