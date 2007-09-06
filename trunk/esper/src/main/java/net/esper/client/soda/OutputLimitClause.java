/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;

/**
 * An output limit clause defines how to limit output of statements and consists of
 * a selector specifiying which events to select to output, a frequency and a unit.
 */
public class OutputLimitClause implements Serializable
{
    private static final long serialVersionUID = 0L;

    private OutputLimitSelector selector;
    private double frequency;
    private OutputLimitUnit unit;

    /**
     * Creates an output limit clause.
     * @param selector is the events to select
     * @param frequency a frequency to output at
     * @param unit the unit for the frequency
     * @return clause
     */
    public static OutputLimitClause create(OutputLimitSelector selector, double frequency, OutputLimitUnit unit)
    {
        return new OutputLimitClause(selector, frequency, unit);
    }

    /**
     * Creates an output limit clause.
     * @param frequency a frequency to output at
     * @param unit the unit for the frequency
     * @return clause
     */
    public static OutputLimitClause create(double frequency, OutputLimitUnit unit)
    {
        return new OutputLimitClause(OutputLimitSelector.ALL, frequency, unit);
    }

    /**
     * Ctor.
     * @param selector is the events to select
     * @param frequency a frequency to output at
     * @param unit the unit for the frequency
     */
    public OutputLimitClause(OutputLimitSelector selector, double frequency, OutputLimitUnit unit)
    {
        this.selector = selector;
        this.frequency = frequency;
        this.unit = unit;
    }

    /**
     * Returns the selector indicating the events to output.
     * @return selector
     */
    public OutputLimitSelector getSelector()
    {
        return selector;
    }

    /**
     * Sets the selector indicating the events to output.
     * @param selector to set
     */
    public void setSelector(OutputLimitSelector selector)
    {
        this.selector = selector;
    }

    /**
     * Returns output frequency.
     * @return frequency of output
     */
    public double getFrequency()
    {
        return frequency;
    }

    /**
     * Sets output frequency.
     * @param frequency is the frequency of output
     */
    public void setFrequency(double frequency)
    {
        this.frequency = frequency;
    }

    /**
     * Returns the unit the frequency is in.
     * @return unit for the frequency.
     */
    public OutputLimitUnit getUnit()
    {
        return unit;
    }

    /**
     * Sets the unit the frequency is in.
     * @param unit is the unit for the frequency
     */
    public void setUnit(OutputLimitUnit unit)
    {
        this.unit = unit;
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
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
