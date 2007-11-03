package net.esper.eql.spec;

import net.esper.eql.expression.ExprNode;

public class OnDeleteDesc
{
    private String windowName;
    private String optionalAsName;
    private ExprNode joinExpr;

    public OnDeleteDesc(String windowName, String optionalAsName, ExprNode joinExpr)
    {
        this.windowName = windowName;
        this.optionalAsName = optionalAsName;
        this.joinExpr = joinExpr;
    }

    public String getWindowName()
    {
        return windowName;
    }

    public String getOptionalAsName()
    {
        return optionalAsName;
    }

    public ExprNode getJoinExpr()
    {
        return joinExpr;
    }

    public void setJoinExpr(ExprNode joinExpr)
    {
        this.joinExpr = joinExpr;
    }
}
