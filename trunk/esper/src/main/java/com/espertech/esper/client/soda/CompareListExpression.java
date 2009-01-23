package com.espertech.esper.client.soda;

import java.io.StringWriter;

public class CompareListExpression extends ExpressionBase
{
    private String operator;
    private boolean isAll;

    public CompareListExpression(boolean all, String operator)
    {
        isAll = all;
        this.operator = operator;
    }

    public boolean isAll()
    {
        return isAll;
    }

    public void setAll(boolean all)
    {
        isAll = all;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public void toEPL(StringWriter writer)
    {
        this.getChildren().get(0).toEPL(writer);
        writer.write(" ");
        writer.write(operator);
        if (isAll)
        {
            writer.write(" all (");
        }
        else
        {
            writer.write(" any (");
        }

        String delimiter = "";
        for (int i = 1; i < this.getChildren().size(); i++)
        {
            writer.write(delimiter);
            this.getChildren().get(i).toEPL(writer);
            delimiter = ", ";
        }
        writer.write(')');
    }
}
