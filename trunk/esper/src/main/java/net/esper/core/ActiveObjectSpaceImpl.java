package net.esper.core;

import net.esper.client.ann.EPSubscriberMethod;
import net.esper.client.EPException;
import net.esper.client.EPStatement;
import net.esper.util.UuidGenerator;
import net.esper.eql.spec.StatementSpecRaw;
import net.esper.eql.spec.ActiveObjectSpec;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class ActiveObjectSpaceImpl implements ActiveObjectSpace
{
    private EPServicesContext services;
    private Map<String, Object> subscribers;
    private Map<Object, List<EPStatement>> statements;

    public ActiveObjectSpaceImpl(EPServicesContext services)
    {
        this.services = services;
        subscribers = new HashMap<String, Object>();
        statements = new HashMap<Object, List<EPStatement>>();
    }

    public Object getSubscriber(String subscriberId)
    {
        Object subscriber = subscribers.get(subscriberId);
        if (subscriber == null)
        {
            throw new EPException("Cannot find subscriber by id");
        }
        return subscriber;
    }

    public void write(Object activeObject)
    {
        String objectId = UuidGenerator.generate(activeObject);
        subscribers.put(objectId, activeObject);

        // Traverse annotations
        for (Method method : activeObject.getClass().getMethods()) {
            if (!Modifier.isPublic(method.getModifiers())) {
                continue;
            }

            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations)
            {
                if (!(annotation.annotationType() == EPSubscriberMethod.class))
                {
                    continue;
                }
                
                // Eligible annotation
                EPSubscriberMethod subAnn = (EPSubscriberMethod) annotation;
                String epl = subAnn.epl();
                String stmtName = subAnn.name();

                // Compile and start
                StatementSpecRaw raw = EPAdministratorImpl.compileEQL(epl, stmtName, services);
                raw.setActiveObjectSpec(new ActiveObjectSpec(objectId, method.getName(), method.getParameterTypes()));
                EPStatement stmt = services.getStatementLifecycleSvc().createAndStart(raw, epl, false, stmtName);

                List<EPStatement> statementList = statements.get(activeObject);
                if (statementList == null)
                {
                    statementList = new ArrayList<EPStatement>();
                    statements.put(activeObject, statementList);
                }
                statementList.add(stmt);
            }
        }        
    }

    public void take(Object activeObject)
    {
        List<EPStatement> statementList = statements.get(activeObject);
        if (statementList != null)
        {
            for (EPStatement stmt : statementList)
            {
                stmt.stop();
            }
        }        
    }
}
