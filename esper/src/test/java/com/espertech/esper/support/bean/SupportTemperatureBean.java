/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.support.bean;

public class SupportTemperatureBean
{
    private String geom;

    private SupportTemperatureBean()
    {
        // need a private ctor for testing
    }

    public SupportTemperatureBean(String geom)
    {
        this.geom = geom;
    }

    public String getGeom()
    {
        return geom;
    }

    public void setGeom(String geom)
    {
        this.geom = geom;
    }
}
