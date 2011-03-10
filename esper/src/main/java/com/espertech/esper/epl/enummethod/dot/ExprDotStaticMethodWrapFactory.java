package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprChainedSpec;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.bean.BeanEventType;
import com.espertech.esper.util.JavaClassHelper;

import java.lang.reflect.Method;
import java.util.List;

public class ExprDotStaticMethodWrapFactory {

    public static ExprDotStaticMethodWrap make(Method method, EventAdapterService eventAdapterService, List<ExprChainedSpec> modifiedChain) {

        if (modifiedChain.isEmpty() || (!EnumMethodEnum.isLambda(modifiedChain.get(0).getName()))) {
            return null;
        }

        if (method.getReturnType().isArray()) {
            Class componentType = method.getReturnType().getComponentType();
            if (componentType == null || JavaClassHelper.isJavaBuiltinDataType(componentType)) {
                return null;
            }
            BeanEventType type = (BeanEventType) eventAdapterService.addBeanType(componentType.getName(), componentType, false, false, false);
            return new ExprDotStaticMethodWrapArray(eventAdapterService, type);
        }

        if (JavaClassHelper.isImplementsInterface(method.getReturnType(), Iterable.class)) {
            Class genericType = JavaClassHelper.getGenericReturnType(method, true);
            if (genericType == null || JavaClassHelper.isJavaBuiltinDataType(genericType)) {
                return null;
            }
            BeanEventType type = (BeanEventType) eventAdapterService.addBeanType(genericType.getName(), genericType, false, false, false);
            return new ExprDotStaticMethodWrapIterable(eventAdapterService, type);
        }

        Class clazz = JavaClassHelper.getGenericReturnType(method, true);
        EventType rootLambdaEventType = null;
        if (clazz != null) {
            rootLambdaEventType = eventAdapterService.addBeanType(clazz.getName(), clazz, false, false, false);
        }
        return null;
    }

}



