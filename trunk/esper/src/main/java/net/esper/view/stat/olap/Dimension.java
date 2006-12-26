package net.esper.view.stat.olap;


/**
 * Dimensions are a structural attribute of cubes. A dimension is an ordinate within a multidimensional
 * cube, consisting of a list of values (members). Each member designates a unique position along its ordinate.
 */
public interface Dimension
{
    /**
     * Returns the event property name or names providing the member values for the dimension.
     * @return array of property names
     */
    public String[] getPropertyNames();

    /**
     * Returns the member values for the dimension.
     * @return array of members
     */
    public DimensionMember[] getMembers();
}
