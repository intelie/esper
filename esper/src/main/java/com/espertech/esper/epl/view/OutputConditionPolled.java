/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.view;


public interface OutputConditionPolled
{
	/**
	 * Update the output condition.
	 * @param newEventsCount - number of new events incoming
     * @param oldEventsCount  - number of old events incoming
     */
	public boolean updateOutputCondition(int newEventsCount, int oldEventsCount);
}