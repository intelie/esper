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

package com.espertech.esper.support.epl;

import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.spec.OuterJoinDesc;
import com.espertech.esper.type.OuterJoinType;

public class SupportOuterJoinDescFactory
{
    public static OuterJoinDesc makeDesc(String propOne, String streamOne, String propTwo, String streamTwo, OuterJoinType type) throws Exception
    {
        ExprIdentNode identNodeOne = new ExprIdentNodeImpl(propOne, streamOne);
        ExprIdentNode identNodeTwo = new ExprIdentNodeImpl(propTwo, streamTwo);

        ExprValidationContext context = ExprValidationContextFactory.make(new SupportStreamTypeSvc3Stream());
        identNodeOne.validate(context);
        identNodeTwo.validate(context);
        OuterJoinDesc desc = new OuterJoinDesc(type, identNodeOne, identNodeTwo, null, null);

        return desc;
    }
}
