/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

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
    private Double frequency;
    private String frequencyVariable;
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
     * @param selector is the events to select
     * @param unit the unit for the frequency
     * @param frequencyVariable is the variable providing the output limit frequency
     * @return clause
     */
    public static OutputLimitClause create(OutputLimitSelector selector, String frequencyVariable, OutputLimitUnit unit)
    {
        return new OutputLimitClause(selector, frequencyVariable, unit);
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
     * Creates an output limit clause.
     * @param frequencyVariable is the variable name providing output rate frequency values
     * @param unit the unit for the frequency
     * @return clause
     */
    public static OutputLimitClause create(String frequencyVariable, OutputLimitUnit unit)
    {
        return new OutputLimitClause(OutputLimitSelector.ALL, frequencyVariable, unit);
    }

    /**
     * Ctor.
     * @param selector is the events to select
     * @param frequency a frequency to output at
     * @param unit the unit for the frequency
     */
    public OutputLimitClause(OutputLimitSelector selector, Double frequency, OutputLimitUnit unit)
    {
        this.selector = selector;
        this.frequency = frequency;
        this.unit = unit;
    }

    /**
     * Ctor.
     * @param selector is the events to select
     * @param unit the unit for the frequency
     * @param frequencyVariable is the variable name providing output rate frequency values
     */
    public OutputLimitClause(OutputLimitSelector selector, String frequencyVariable, OutputLimitUnit unit)
    {
        this.selector = selector;
        this.frequencyVariable = frequencyVariable;
        this.unit = unit;
    }

    /**
     * Ctor.
     * @param selector is the events to select
     * @param frequency a frequency to output at
     * @param unit the unit for the frequency
     * @param frequencyVariable is the variable name providing output rate frequency values
     */
    public OutputLimitClause(OutputLimitSelector selector, Double frequency, String frequencyVariable, OutputLimitUnit unit)
    {
        this.selector = selector;
        this.frequency = frequency;
        this.frequencyVariable = frequencyVariable;
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
    public Double getFrequency()
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
     * Returns the variable name of the variable providing output rate frequency values, or null if the frequency is a fixed value.
     * @return variable name or null if no variable is used
     */
    public String getFrequencyVariable()
    {
        return frequencyVariable;
    }

    /**
     * Sets the variable name of the variable providing output rate frequency values, or null if the frequency is a fixed value.
     * @param frequencyVariable variable name or null if no variable is used
     */
    public void setFrequencyVariable(String frequencyVariable)
    {
        this.frequencyVariable = frequencyVariable;
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
            if (frequencyVariable == null)
            {
                writer.write(Double.toString(frequency));
            }
            else
            {
                writer.write(frequencyVariable);
            }
        }
        else
        {
            if (frequencyVariable == null)
            {
                writer.write(Integer.toString(frequency.intValue()));
            }
            else
            {
                writer.write(frequencyVariable);
            }
        }
        writer.write(' ');
        writer.write(unit.getText());
    }
}
