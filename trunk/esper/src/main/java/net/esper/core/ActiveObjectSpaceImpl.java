package net.esper.core;

import net.esper.client.ann.EPSubscriberMethod;
import net.esper.client.EPException;
import net.esper.util.UuidGenerator;
import net.esper.eql.spec.StatementSpecRaw;
import net.esper.eql.spec.ActiveObjectSpec;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.HashMap;

public class ActiveObjectSpaceImpl implements ActiveObjectSpace
{
    private EPServicesContext services;
    private Map<String, Object> subscribers;

    public ActiveObjectSpaceImpl(EPServicesContext services)
    {
        this.services = services;
        subscribers = new HashMap<String, Object>();
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

                StatementSpecRaw raw = EPAdministratorImpl.compileEQL(epl, null, services);
                raw.setActiveObjectSpec(new ActiveObjectSpec(objectId, method.getName(), method.getParameterTypes()));
                services.getStatementLifecycleSvc().createAndStart(raw, epl, false, null);
            }
        }
        
        // TODO
        // (1) Minimal version: mapping to select to method, performance, base API
        // (2) Support parameterized annotations
        // (3) Support insert into
        // (4) Support start/stop
        // (5) Handle iterator
        // (6) Handle persistence
        // (7) Support for detecting a pattern or a epl

        // Interrogate all methods; get annotations
        // Compile and start statement; put Map<Object, List<EPStatement>>
        // Take: stops and removes statements

        
    }

    public void take(Object activeObject)
    {

    }
}
