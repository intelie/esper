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

package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.join.table.PropertyCompositeEventTable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface CompositeIndexLookup {
    public void lookup(Map parent, Set<EventBean> result);
    public void setNext(CompositeIndexLookup action);
}
