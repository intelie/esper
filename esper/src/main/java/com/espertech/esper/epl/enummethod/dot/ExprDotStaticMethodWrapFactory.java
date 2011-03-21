package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.epl.expression.ExprChainedSpec;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.bean.BeanEventType;
import com.espertech.esper.util.JavaClassHelper;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class ExprDotStaticMethodWrapFactory {

    public static ExprDotStaticMethodWrap make(Method method, EventAdapterService eventAdapterService, List<ExprChainedSpec> modifiedChain) {

        if (modifiedChain.isEmpty() || (!EnumMethodEnum.isLambda(modifiedChain.get(0).getName()))) {
            return null;
        }

        if (method.getReturnType().isArray()) {
            Class componentType = method.getReturnType().getComponentType();
            if (componentType == null || JavaClassHelper.isJavaBuiltinDataType(componentType)) {
                return new ExprDotStaticMethodWrapArrayScalar(method.getName(), componentType);
            }
            BeanEventType type = (BeanEventType) eventAdapterService.addBeanType(componentType.getName(), componentType, false, false, false);
            return new ExprDotStaticMethodWrapArrayEvents(eventAdapterService, type);
        }

        if (JavaClassHelper.isImplementsInterface(method.getReturnType(), Collection.class)) {
            Class genericType = JavaClassHelper.getGenericReturnType(method, true);
            if (genericType == null || JavaClassHelper.isJavaBuiltinDataType(genericType)) {
                return new ExprDotStaticMethodWrapCollection(method.getName(), genericType);
            }
        }

        if (JavaClassHelper.isImplementsInterface(method.getReturnType(), Iterable.class)) {
            Class genericType = JavaClassHelper.getGenericReturnType(method, true);
            if (genericType == null || JavaClassHelper.isJavaBuiltinDataType(genericType)) {
                return new ExprDotStaticMethodWrapIterableScalar(method.getName(), genericType);
            }
            BeanEventType type = (BeanEventType) eventAdapterService.addBeanType(genericType.getName(), genericType, false, false, false);
            return new ExprDotStaticMethodWrapIterableEvents(eventAdapterService, type);
        }
        return null;
    }

}



