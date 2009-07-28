package com.espertech.esper.support.epl;

import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.spec.OuterJoinDesc;
import com.espertech.esper.type.OuterJoinType;

public class SupportOuterJoinDescFactory
{
    public static OuterJoinDesc makeDesc(String propOne, String streamOne, String propTwo, String streamTwo, OuterJoinType type) throws Exception
    {
        ExprIdentNode identNodeOne = new ExprIdentNode(propOne, streamOne);
        ExprIdentNode identNodeTwo = new ExprIdentNode(propTwo, streamTwo);

        identNodeOne.validate(new SupportStreamTypeSvc3Stream(), null, null, null, null, null);
        identNodeTwo.validate(new SupportStreamTypeSvc3Stream(), null, null, null, null, null);
        OuterJoinDesc desc = new OuterJoinDesc(type, identNodeOne, identNodeTwo, null, null);

        return desc;
    }
}
