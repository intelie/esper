package com.espertech.esper.view;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esper.util.AuditPath;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;

public class ViewProxy implements java.lang.reflect.InvocationHandler {

    private static final Log auditLog = LogFactory.getLog(AuditPath.AUDIT_LOG);
    private static Method target = JavaClassHelper.getMethodByName(View.class, "update");

    private final String statementName;
    private final String viewName;
    private final View view;

    public static Object newInstance(String statementName, String viewName, View view) {
        return java.lang.reflect.Proxy.newProxyInstance(
                 view.getClass().getClassLoader(),
                JavaClassHelper.getSuperInterfaces(view.getClass()),
                new ViewProxy(statementName, viewName, view));
    }

    public ViewProxy(String statementName, String viewName, View view) {
        this.statementName = statementName;
        this.viewName = viewName;
        this.view = view;
    }

    public Object invoke(Object proxy, Method m, Object[] args)
            throws Throwable {

        if (!m.equals(target)) {
            return m.invoke(view, args);
        }

        Object result = m.invoke(view, args);
        if (auditLog.isInfoEnabled()) {
            EventBean[] newData = (EventBean[]) args[0];
            EventBean[] oldData = (EventBean[]) args[1];
            auditLog.info("Statement " + statementName + " view " + viewName + " insert {" + EventBeanUtility.summarize(newData) + "} remove {" + EventBeanUtility.summarize(oldData) + "}");
        }
        return result;
    }
}

