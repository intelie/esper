package net.esper.client.soda;

import java.io.StringWriter;

public class LikeExpression extends ExpressionBase
{
    private Character optionalEscape;

    public LikeExpression(Expression left, Expression right)
    {
        this(left, right, null);
    }

    public LikeExpression(Expression left, Expression right, Character escape)
    {
        this.getChildren().add(left);
        this.getChildren().add(right);
        optionalEscape = escape;        
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("(");
        this.getChildren().get(0).toEQL(writer);
        writer.write(" like ");
        this.getChildren().get(1).toEQL(writer);

        if (optionalEscape != null)
        {
            writer.write(" escape ");
            this.getChildren().get(1).toEQL(writer);
        }
        writer.write(")");
    }
}
