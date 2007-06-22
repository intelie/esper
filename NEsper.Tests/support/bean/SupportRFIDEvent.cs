///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.support.bean
{
	public class SupportRFIDEvent
	{
	    private String locationReportId;
	    private String mac;
	    private String zoneID;

	    public SupportRFIDEvent(String mac, String zoneID)
	        : this(null, mac, zoneID)
	    {
	    }

	    public SupportRFIDEvent(String locationReportId, String mac, String zoneID)
	    {
	        this.locationReportId = locationReportId;
	        this.mac = mac;
	        this.zoneID = zoneID;
	    }

	    public String GetLocationReportId()
	    {
	        return locationReportId;
	    }

	    public String GetMac()
	    {
	        return mac;
	    }

	    public String GetZoneID()
	    {
	        return zoneID;
	    }
	}
} // End of namespace
