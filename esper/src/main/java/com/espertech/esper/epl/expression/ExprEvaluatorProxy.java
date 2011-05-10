package com.espertech.esper.epl.expression;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esper.util.AuditPath;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Collection;

public class ExprEvaluatorProxy implements java.lang.reflect.InvocationHandler {

    private static final Log auditLog = LogFactory.getLog(AuditPath.AUDIT_LOG);
    private static Method targetEvaluate = JavaClassHelper.getMethodByName(ExprEvaluator.class, "evaluate");
    private static Method targetEvaluateCollEvents = JavaClassHelper.getMethodByName(ExprEvaluatorEnumeration.class, "evaluateGetROCollectionEvents");
    private static Method targetEvaluateCollScalar = JavaClassHelper.getMethodByName(ExprEvaluatorEnumeration.class, "evaluateGetROCollectionScalar");
    private static Method targetEvaluateBean = JavaClassHelper.getMethodByName(ExprEvaluatorEnumeration.class, "evaluateGetEventBean");

    private final String statementName;
    private final String expressionToString;
    private final ExprEvaluator evaluator;

    public static Object newInstance(String statementName, String expressionToString, ExprEvaluator evaluator) {
        return java.lang.reflect.Proxy.newProxyInstance(
                evaluator.getClass().getClassLoader(),
                JavaClassHelper.getSuperInterfaces(evaluator.getClass()),
                new ExprEvaluatorProxy(statementName, expressionToString, evaluator));
    }

    public ExprEvaluatorProxy(String statementName, String expressionToString, ExprEvaluator evaluator) {
        this.statementName = statementName;
        this.expressionToString = expressionToString;
        this.evaluator = evaluator;
    }

    public Object invoke(Object proxy, Method m, Object[] args)
            throws Throwable {

        if (m.equals(targetEvaluate)) {
            Object result = m.invoke(evaluator, args);
            if (auditLog.isInfoEnabled()) {
                auditLog.info("Statement " + statementName + " expression " + expressionToString + " result " + result);
            }
            return result;
        }

        if (m.equals(targetEvaluateCollEvents)) {
            Object result = m.invoke(evaluator, args);
            if (auditLog.isInfoEnabled()) {
                Collection<EventBean> resultBeans = (Collection<EventBean>) result;
                String out = "null";
                if (resultBeans != null) {
                    if (resultBeans.isEmpty()) {
                        out = "{}";
                    }
                    else {
                        StringWriter buf = new StringWriter();
                        int count = 0;
                        for (EventBean event : resultBeans) {
                            buf.append(" Event ");
                            buf.append(Integer.toString(count++));
                            buf.append(":");
                            EventBeanUtility.appendEvent(buf, event);
                        }
                        out = buf.toString();
                    }
                }
                auditLog.info("Statement " + statementName + " expression " + expressionToString + " result " + out);
            }
            return result;
        }

        if (m.equals(targetEvaluateCollScalar)) {
            Object result = m.invoke(evaluator, args);
            if (auditLog.isInfoEnabled()) {
                auditLog.info("Statement " + statementName + " expression " + expressionToString + " result " + result);
            }
            return result;
        }

        if (m.equals(targetEvaluateBean)) {
            Object result = m.invoke(evaluator, args);
            if (auditLog.isInfoEnabled()) {
                String out = "null";
                if (result != null) {
                    StringWriter buf = new StringWriter();
                    EventBeanUtility.appendEvent(buf, (EventBean) result);
                    out = buf.toString();
                }
                auditLog.info("Statement " + statementName + " expression " + expressionToString + " result " + out);
            }
            return result;
        }
        return m.invoke(evaluator, args);
    }
}

