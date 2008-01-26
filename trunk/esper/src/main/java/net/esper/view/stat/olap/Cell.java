package net.esper.view.stat.olap;

/**
 * Cell is the analytic values or variable tracked by a cube.
 */
public interface Cell
{
    /**
     * Returns the value.
     * @return double value
     */
    public double getValue();
}
