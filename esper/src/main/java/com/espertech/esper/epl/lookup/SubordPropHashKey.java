/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.lookup;

import com.espertech.esper.epl.join.plan.QueryGraphValueEntryHashKeyed;

/**
 * Holds property information for joined properties in a lookup.
 */
public class SubordPropHashKey
{
    private final QueryGraphValueEntryHashKeyed hashKey;
    private final Integer optionalKeyStreamNum;
    private final Class coercionType;

    public SubordPropHashKey(QueryGraphValueEntryHashKeyed hashKey, Integer optionalKeyStreamNum, Class coercionType) {
        this.hashKey = hashKey;
        this.optionalKeyStreamNum = optionalKeyStreamNum;
        this.coercionType = coercionType;
    }

    public Integer getOptionalKeyStreamNum() {
        return optionalKeyStreamNum;
    }

    public QueryGraphValueEntryHashKeyed getHashKey() {
        return hashKey;
    }

    public Class getCoercionType() {
        return coercionType;
    }
}
