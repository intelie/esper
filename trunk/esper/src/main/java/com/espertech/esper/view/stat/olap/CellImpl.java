/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.stat.olap;

import com.espertech.esper.util.MetaDefItem;


/**
 * Hold the measure information which consists of a double value.
 */
public final class CellImpl implements Cell, MetaDefItem
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
