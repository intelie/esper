package net.esper.client.soda;

import java.io.StringWriter;

public class CaseWhenThenExpression extends ExpressionBase
{
    public CaseWhenThenExpression()
    {
    }

    public CaseWhenThenExpression add(Expression when, Expression then)
    {
        int size = this.getChildren().size();
        if (size % 2 == 0)
        {
            this.addChild(when);
            this.addChild(then);
        }
        else
        {
            // add next to last as the last node is the else clause
            this.getChildren().add(this.getChildren().size() - 1, when);
            this.getChildren().add(this.getChildren().size() - 1, then);
        }
        return this;
    }

    public CaseWhenThenExpression setElse(Expression elseExpr)
    {
        int size = this.getChildren().size();
        // remove last node representing the else
        if (size % 2 != 0)
        {
            this.getChildren().remove(size - 1);
        }
        this.addChild(elseExpr);
        return this;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("case");
        int index = 0;
        while(index < this.getChildren().size() - 1)
        {
            writer.write(" when ");
            getChildren().get(index).toEQL(writer);
            index++;
            if (index == this.getChildren().size())
            {
                throw new IllegalStateException("Invalid case-when expression, count of when-to-then nodes not matching");
            }
            writer.write(" then ");
            getChildren().get(index).toEQL(writer);
            index++;
        }

        if (index < this.getChildren().size())
        {
            writer.write(" else ");
            getChildren().get(index).toEQL(writer);
        }
        writer.write(" end");
    }
}
