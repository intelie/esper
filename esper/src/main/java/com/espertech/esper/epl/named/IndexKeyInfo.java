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

package com.espertech.esper.epl.named;

import com.espertech.esper.epl.join.plan.CoercionDesc;
import com.espertech.esper.epl.lookup.SubordPropHashKey;
import com.espertech.esper.epl.lookup.SubordPropRangeKey;

import java.util.List;

public class IndexKeyInfo {

    private List<SubordPropHashKey> orderedKeyProperties;
    private CoercionDesc orderedKeyCoercionTypes;
    private List<SubordPropRangeKey> orderedRangeDesc;
    private CoercionDesc orderedRangeCoercionTypes;

    public IndexKeyInfo(List<SubordPropHashKey> orderedKeyProperties, CoercionDesc orderedKeyCoercionTypes, List<SubordPropRangeKey> orderedRangeDesc, CoercionDesc orderedRangeCoercionTypes) {
        this.orderedKeyProperties = orderedKeyProperties;
        this.orderedKeyCoercionTypes = orderedKeyCoercionTypes;
        this.orderedRangeDesc = orderedRangeDesc;
        this.orderedRangeCoercionTypes = orderedRangeCoercionTypes;
    }

    public List<SubordPropHashKey> getOrderedHashProperties() {
        return orderedKeyProperties;
    }

    public CoercionDesc getOrderedKeyCoercionTypes() {
        return orderedKeyCoercionTypes;
    }

    public List<SubordPropRangeKey> getOrderedRangeDesc() {
        return orderedRangeDesc;
    }

    public CoercionDesc getOrderedRangeCoercionTypes() {
        return orderedRangeCoercionTypes;
    }
}
