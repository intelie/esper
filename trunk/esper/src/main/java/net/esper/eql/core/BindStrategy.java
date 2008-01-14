package net.esper.eql.core;

import net.esper.eql.spec.SelectExprElementCompiledSpec;
import net.esper.eql.spec.ActiveObjectSpec;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.util.JavaClassHelper;
import net.esper.event.EventType;
import net.esper.event.EventBean;

import java.util.List;

public interface BindStrategy
{
    public Object[] process(EventBean[] eventsPerStream, boolean isNewData);
}
