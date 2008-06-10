package com.espertech.esper.client.soda;

import java.io.StringWriter;

/**
 * Match-Until construct for use in pattern expressions.
 */
public class PatternMatchUntilExpr extends PatternExprBase
{
    private Integer low;
    private Integer high;

    /**
     * Ctor - for use to create a pattern expression tree, without pattern child expression.
     */
    public PatternMatchUntilExpr()
    {
    }

    /**
     * Ctor - for use when adding required child nodes later.
     * @param low - low number of matches, or null if no lower boundary
     * @param high - high number of matches, or null if no high boundary
     */
    public PatternMatchUntilExpr(Integer low, Integer high)
    {
        this.low = low;
        this.high = high;
    }

    /**
     * Ctor.
     * @param low - low number of matches, or null if no lower boundary
     * @param high - high number of matches, or null if no high boundary
     * @param match - the pattern expression that is sought to match repeatedly
     * @param until - the pattern expression that ends matching (optional, can be null)
     */
    public PatternMatchUntilExpr(Integer low, Integer high, PatternExpr match, PatternExpr until)
    {
        this.low = low;
        this.high = high;
        this.addChild(match);
        this.addChild(until);
    }

    public Integer getLow()
    {
        return low;
    }

    public void setLow(Integer low)
    {
        this.low = low;
    }

    public Integer getHigh()
    {
        return high;
    }

    public void setHigh(Integer high)
    {
        this.high = high;
    }

    public void toEPL(StringWriter writer)
    {
        if ((low != null) || (high != null))
        {
            writer.write("[");
            if ((low != null) && (high != null))
            {
                if (low == high)
                {
                    writer.write(Integer.toString(low));
                }
                else
                {
                    writer.write(Integer.toString(low));
                    writer.write("..");
                    writer.write(Integer.toString(high));
                }
            }
            else if (low != null)
            {
                writer.write(Integer.toString(low));
                writer.write("..");
            }
            else
            {
                writer.write("..");
                writer.write(Integer.toString(high));
            }
            writer.write("] ");
        }

        writer.write('(');
        this.getChildren().get(0).toEPL(writer);
        writer.write(')');

        if (this.getChildren().size() > 1)
        {
            writer.write(" until ");
            writer.write('(');
            this.getChildren().get(1).toEPL(writer);
            writer.write(')');
        }
    }
}
