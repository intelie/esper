/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.rfid;

public class LocationReport
{
    private String assetId;
    private int zone;

    public LocationReport(String assetId, int zone)
    {
        this.assetId = assetId;
        this.zone = zone;
    }

    public String getAssetId()
    {
        return assetId;
    }

    public int getZone()
    {
        return zone;
    }
}
