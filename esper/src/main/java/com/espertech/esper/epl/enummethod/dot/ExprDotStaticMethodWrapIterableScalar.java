package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.bean.BeanEventType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class ExprDotStaticMethodWrapIterableScalar implements ExprDotStaticMethodWrap {

    private static final Log log = LogFactory.getLog(ExprDotStaticMethodWrapArrayScalar.class);

    private final String methodName;
    private final Class componentType;

    public ExprDotStaticMethodWrapIterableScalar(String methodName, Class componentType) {
        this.methodName = methodName;
        this.componentType = componentType;
    }

    public ExprDotEvalTypeInfo getTypeInfo() {
        return ExprDotEvalTypeInfo.componentColl(componentType);
    }

    public Collection convert(Object result) {
        if (result == null) {
            return null;
        }
        if (!(result instanceof Iterable)) {
            log.warn("Expected iterable-type input from method '" + methodName + "' but received " + result.getClass());
            return null;
        }
        ArrayList items = new ArrayList();
        Iterator iterator = ((Iterable) result).iterator();
        for (;iterator.hasNext();)
        {
            items.add(iterator.next());
        }
        return items;
    }
}
