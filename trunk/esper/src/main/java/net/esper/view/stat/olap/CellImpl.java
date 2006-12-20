package net.esper.view.stat.olap;


/**
 * Hold the measure information which consists of a double value.
 */
public final class CellImpl implements Cell
{
    private final double value;

    /**
     * Constructor.
     * @param value is the measure value
     */
    public CellImpl(double value)
    {
        this.value = value;
    }

    public final double getValue()
    {
        return value;
    }
}
