/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.stat.olap;


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
