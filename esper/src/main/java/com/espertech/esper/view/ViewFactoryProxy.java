package com.espertech.esper.view;

import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprEvaluatorProxy;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.util.JavaClassHelper;

import java.lang.reflect.Method;

public class ViewFactoryProxy implements java.lang.reflect.InvocationHandler {

    private static Method target = JavaClassHelper.getMethodByName(ViewFactory.class, "makeView");

    private String statementName;
    private ViewFactory viewFactory;
    private String viewName;

    public static Object newInstance(String statementName, ViewFactory viewFactory, String viewName) {
        return java.lang.reflect.Proxy.newProxyInstance(
                viewFactory.getClass().getClassLoader(),
                new Class[]{ViewFactory.class},
                new ViewFactoryProxy(statementName, viewFactory, viewName));
    }

    public ViewFactoryProxy(String statementName, ViewFactory viewFactory, String viewName) {
        this.statementName = statementName;
        this.viewFactory = viewFactory;
        this.viewName = viewName;
    }

    public Object invoke(Object proxy, Method m, Object[] args)
            throws Throwable {

        if (!m.equals(target)) {
            return m.invoke(viewFactory, args);
        }

        View view = (View) m.invoke(viewFactory, args);
        return ViewProxy.newInstance(statementName, viewName, view);
    }
}

