package net.esper.client.soda;

import java.util.ArrayList;
import java.util.List;
import java.io.StringWriter;

public class PatternStream extends ProjectedStream
{
    private PatternExpr expression;

    public static PatternStream create(PatternExpr expression)
    {
        return new PatternStream(expression);
    }

    public static PatternStream create(PatternExpr expression, String optStreamName)
    {
        return new PatternStream(expression, optStreamName);
    }

    public PatternStream(PatternExpr expression)
    {
        this(expression, null);
    }

    public PatternStream(PatternExpr expression, String optStreamName)
    {
        super(new ArrayList<View>(), optStreamName);
        this.expression = expression;
    }

    public PatternExpr getExpression()
    {
        return expression;
    }

    public void setExpression(PatternExpr expression)
    {
        this.expression = expression;
    }

    public void toEQLStream(StringWriter writer)
    {
        writer.write("pattern [");
        expression.toEQL(writer);
        writer.write(']');
    }
}
