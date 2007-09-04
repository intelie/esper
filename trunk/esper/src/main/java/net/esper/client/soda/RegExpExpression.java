package net.esper.client.soda;

import java.io.StringWriter;

public class RegExpExpression extends ExpressionBase
{
    public RegExpExpression()
    {
    }

    public RegExpExpression(Expression left, Expression right)
    {
        this(left, right, null);
    }

    public RegExpExpression(Expression left, Expression right, Expression escape)
    {
        this.getChildren().add(left);
        this.getChildren().add(right);
        if (escape != null)
        {
            this.getChildren().add(escape);
        }
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("(");
        this.getChildren().get(0).toEQL(writer);
        writer.write(" regexp ");
        this.getChildren().get(1).toEQL(writer);

        if (this.getChildren().size() > 2)
        {
            writer.write(" escape ");
            this.getChildren().get(2).toEQL(writer);
        }
        writer.write(")");
    }
}
