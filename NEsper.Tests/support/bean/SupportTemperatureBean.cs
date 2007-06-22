// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

namespace net.esper.support.bean
{
	public class SupportTemperatureBean
	{
	    private String geom;

	    public SupportTemperatureBean(String geom)
	    {
	        this.geom = geom;
	    }

	    public String GetGeom()
	    {
	        return geom;
	    }
	}
} // End of namespace
