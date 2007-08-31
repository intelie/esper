package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;

public class OutputLimitClause implements Serializable
{
    private OutputLimitSelector selector;
    private double frequency;
    private OutputLimitUnit unit;

    public static OutputLimitClause create(OutputLimitSelector selector, double frequency, OutputLimitUnit unit)
    {
        return new OutputLimitClause(selector, frequency, unit);
    }

    public static OutputLimitClause create(double frequency, OutputLimitUnit unit)
    {
        return new OutputLimitClause(OutputLimitSelector.ALL, frequency, unit);
    }

    public OutputLimitClause(OutputLimitSelector selector, double frequency, OutputLimitUnit unit)
    {
        this.selector = selector;
        this.frequency = frequency;
        this.unit = unit;
    }

    public OutputLimitSelector getSelector()
    {
        return selector;
    }

    public void setSelector(OutputLimitSelector selector)
    {
        this.selector = selector;
    }

    public double getFrequency()
    {
        return frequency;
    }

    public void setFrequency(double frequency)
    {
        this.frequency = frequency;
    }

    public OutputLimitUnit getUnit()
    {
        return unit;
    }

    public void setUnit(OutputLimitUnit unit)
    {
        this.unit = unit;
    }

    public void toEQL(StringWriter writer)
    {
        if (selector != OutputLimitSelector.ALL)
        {
            writer.write(selector.getText());
            writer.write(" ");
        }
        writer.write("every ");
        if (unit != OutputLimitUnit.EVENTS)
        {
            writer.write(Double.toString(frequency));
        }
        else
        {
            writer.write(Integer.toString((int)frequency));
        }
        writer.write(' ');
        writer.write(unit.getText());
    }
}
