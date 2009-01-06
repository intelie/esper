/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.servershell.jmx;

import com.espertech.esper.client.UpdateListener;

public interface EPServiceProviderJMXMBean
{
    public void createEPL(String expression, String statementName);
    public void createEPL(String expression, String statementName, UpdateListener listener);
    public void destroy(String statementName);
}
