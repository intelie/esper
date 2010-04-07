package com.espertech.esper.client.deploy;

import java.util.Set;
import java.util.List;

public class ModuleItem {
    private String expression;
    private boolean commentOnly;

    public ModuleItem(String expression, boolean commentOnly) {
        this.expression = expression;
        this.commentOnly = commentOnly;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public boolean isCommentOnly() {
        return commentOnly;
    }

    public void setCommentOnly(boolean commentOnly) {
        this.commentOnly = commentOnly;
    }
}
