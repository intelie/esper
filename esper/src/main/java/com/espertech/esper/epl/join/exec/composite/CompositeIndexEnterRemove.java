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

import java.util.HashSet;
import java.util.Map;

public interface CompositeIndexEnterRemove {

    public void enter(EventBean event, Map parent);
    public void setNext(CompositeIndexEnterRemove next);
    public void remove(EventBean event, Map parent);
    public void getAll(HashSet<EventBean> result, Map parent);
}
