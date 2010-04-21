package com.espertech.esper.client.deploy;

import java.util.Set;
import java.util.List;

/**
 * Represents an EPL statement as part of a {@link Module}.
 */
public class ModuleItem {
    private String expression;
    private boolean commentOnly;
    private int lineNumber;

    /**
     * Ctor.
     * @param expression EPL
     * @param commentOnly true if the statement consists only of comments or whitespace
     */
    public ModuleItem(String expression, boolean commentOnly, int lineNumber) {
        this.expression = expression;
        this.commentOnly = commentOnly;
        this.lineNumber = lineNumber;
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
}
