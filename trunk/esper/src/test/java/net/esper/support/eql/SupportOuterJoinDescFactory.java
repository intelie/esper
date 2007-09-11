package net.esper.support.eql;

import net.esper.eql.expression.*;
import net.esper.eql.spec.OuterJoinDesc;
import net.esper.type.OuterJoinType;

public class SupportOuterJoinDescFactory
{
    public static OuterJoinDesc makeDesc(String propOne, String streamOne, String propTwo, String streamTwo, OuterJoinType type) throws Exception
    {
        ExprIdentNode identNodeOne = new ExprIdentNode(propOne, streamOne);
        ExprIdentNode identNodeTwo = new ExprIdentNode(propTwo, streamTwo);

        identNodeOne.validate(new SupportStreamTypeSvc3Stream(), null, null, null);
        identNodeTwo.validate(new SupportStreamTypeSvc3Stream(), null, null, null);
        OuterJoinDesc desc = new OuterJoinDesc(type, identNodeOne, identNodeTwo);

        return desc;
    }
}
