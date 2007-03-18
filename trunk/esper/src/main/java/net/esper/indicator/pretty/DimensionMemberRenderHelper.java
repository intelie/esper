/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.indicator.pretty;

import java.util.Arrays;

import net.esper.view.stat.olap.DimensionMember;

/**
 * Utility methods for rendering dimension members as a string.
 */
public final class DimensionMemberRenderHelper
{
    /**
     * Renders dimension members as a String.
     * @param dimensionMember is the dimension dimensionMember to render
     * @return rendered dimensionMember
     */
    public static String renderMember(DimensionMember dimensionMember)
    {
        Object[] values = dimensionMember.getValues();

        if (values.length == 0)
        {
            return renderPropertyNames(dimensionMember.getDimension().getPropertyNames());
        }

        return renderObjects(dimensionMember.getValues());
    }

    private static String renderPropertyNames(String[] propertyNames)
    {
        if (propertyNames.length == 1)
        {
            return propertyNames[0];
        }
        return Arrays.toString(propertyNames);
    }

    private static String renderObjects(Object[] values)
    {
        if (values.length == 1)
        {
            return values[0].toString();
        }
        return Arrays.toString(values);
    }
}
