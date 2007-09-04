package net.esper.client.soda;

import java.io.StringWriter;

public class BetweenExpression extends ExpressionBase
{
    private boolean isLowEndpointIncluded;
    private boolean isHighEndpointIncluded;
    private boolean isNotBetween;

    public BetweenExpression(Expression datapoint, Expression lower, Expression higher)
    {
        this(datapoint, lower, higher, true, true, false);
    }

    public BetweenExpression(boolean lowEndpointIncluded, boolean highEndpointIncluded, boolean notBetween)
    {
        isLowEndpointIncluded = lowEndpointIncluded;
        isHighEndpointIncluded = highEndpointIncluded;
        isNotBetween = notBetween;
    }

    public BetweenExpression(Expression datapoint, Expression lower, Expression higher, boolean lowEndpointIncluded, boolean highEndpointIncluded, boolean notBetween)
    {
        this.getChildren().add(datapoint);
        this.getChildren().add(lower);
        this.getChildren().add(higher);

        isLowEndpointIncluded = lowEndpointIncluded;
        isHighEndpointIncluded = highEndpointIncluded;
        isNotBetween = notBetween;
    }

    public void toEQL(StringWriter writer)
    {
        if ((isLowEndpointIncluded) && (isHighEndpointIncluded))
        {
            writer.write('(');
            this.getChildren().get(0).toEQL(writer);
            writer.write(" between ");
            this.getChildren().get(1).toEQL(writer);
            writer.write(" and ");
            this.getChildren().get(2).toEQL(writer);
            writer.write(')');
        }
        else
        {
            writer.write('(');
            this.getChildren().get(0).toEQL(writer);
            writer.write(" in ");
            if (isLowEndpointIncluded)
            {
                writer.write('[');
            }
            else
            {
                writer.write('(');
            }
            this.getChildren().get(1).toEQL(writer);
            writer.write(':');
            this.getChildren().get(2).toEQL(writer);
            if (isHighEndpointIncluded)
            {
                writer.write(']');
            }
            else
            {
                writer.write(')');
            }
            writer.write(')');
        }
    }

    public boolean isLowEndpointIncluded()
    {
        return isLowEndpointIncluded;
    }

    public void setLowEndpointIncluded(boolean lowEndpointIncluded)
    {
        isLowEndpointIncluded = lowEndpointIncluded;
    }

    public boolean isHighEndpointIncluded()
    {
        return isHighEndpointIncluded;
    }

    public void setHighEndpointIncluded(boolean highEndpointIncluded)
    {
        isHighEndpointIncluded = highEndpointIncluded;
    }

    public boolean isNotBetween()
    {
        return isNotBetween;
    }

    public void setNotBetween(boolean notBetween)
    {
        isNotBetween = notBetween;
    }
}
