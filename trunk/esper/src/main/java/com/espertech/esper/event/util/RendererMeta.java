package com.espertech.esper.event.util;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventPropertyDescriptor;
import com.espertech.esper.client.FragmentEventType;

import java.util.ArrayList;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RendererMeta
{
    private static final Log log = LogFactory.getLog(RendererMeta.class);

    private final GetterPair[] simpleProperties;
    private final GetterPair[] indexProperties;
    private final NestedGetterPair[] nestedProperties;

    private static JSONOutput jsonStringOutput = new JSONOutputString();
    private static JSONOutput xmlStringOutput = new XMLOutputString();
    private static JSONOutput baseOutput = new JSONOutputBase();

    public RendererMeta(EventType eventType, Stack<EventTypePropertyPair> stack, RendererMetaOptions options)
    {
        ArrayList<GetterPair> gettersSimple = new ArrayList<GetterPair>();
        ArrayList<GetterPair> gettersIndexed = new ArrayList<GetterPair>();
        ArrayList<NestedGetterPair> gettersNested = new ArrayList<NestedGetterPair>();

        EventPropertyDescriptor[] descriptors = eventType.getPropertyDescriptors();
        for (EventPropertyDescriptor desc : descriptors)
        {
            String propertyName = desc.getPropertyName();

            if ((!desc.isIndexed()) && (!desc.isMapped()) && (!desc.isFragment()))
            {
                EventPropertyGetter getter = eventType.getGetter(propertyName);
                if (getter == null)
                {
                    log.warn("No getter returned for event type '" + eventType.getName() + "' and property '" + propertyName + "'");
                    continue;
                }
                gettersSimple.add(new GetterPair(getter, propertyName, getOutput(desc.getPropertyType(), options)));
            }

            if (desc.isIndexed() && !desc.isRequiresIndex() && (!desc.isFragment()))
            {
                EventPropertyGetter getter = eventType.getGetter(propertyName);
                if (getter == null)
                {
                    log.warn("No getter returned for event type '" + eventType.getName() + "' and property '" + propertyName + "'");
                    continue;
                }
                gettersIndexed.add(new GetterPair(getter, propertyName, getOutput(desc.getPropertyType(), options)));
            }

            if (desc.isFragment())
            {
                EventPropertyGetter getter = eventType.getGetter(propertyName);
                FragmentEventType fragmentType = eventType.getFragmentType(propertyName);
                if (getter == null)
                {
                    log.warn("No getter returned for event type '" + eventType.getName() + "' and property '" + propertyName + "'");
                    continue;
                }
                if (fragmentType == null)
                {
                    log.warn("No fragment type returned for event type '" + eventType.getName() + "' and property '" + propertyName + "'");
                    continue;
                }

                EventTypePropertyPair pair = new EventTypePropertyPair(fragmentType.getFragmentType(), propertyName);
                if ((options.isPreventLooping() && stack.contains(pair)))
                {
                    continue;   // prevent looping behavior on self-references
                }

                stack.push(pair);
                RendererMeta fragmentMetaData = new RendererMeta(fragmentType.getFragmentType(), stack, options);
                stack.pop();
                
                gettersNested.add(new NestedGetterPair(getter, propertyName, fragmentMetaData, fragmentType.isIndexed()));
            }
        }

        simpleProperties = gettersSimple.toArray(new GetterPair[gettersSimple.size()]);
        indexProperties = gettersIndexed.toArray(new GetterPair[gettersIndexed.size()]);
        nestedProperties = gettersNested.toArray(new NestedGetterPair[gettersNested.size()]);
    }

    public GetterPair[] getSimpleProperties()
    {
        return simpleProperties;
    }

    public GetterPair[] getIndexProperties()
    {
        return indexProperties;
    }

    public NestedGetterPair[] getNestedProperties()
    {
        return nestedProperties;
    }

    private JSONOutput getOutput(Class type, RendererMetaOptions options)
    {
        if (type.isArray())
        {
            type = type.getComponentType();
        }
        if ((type == String.class) || (type == Character.class) || (type == char.class))
        {
            if (options.isXMLOutput())
            {
                return xmlStringOutput;
            }
            else
            {
                return jsonStringOutput;
            }
        }
        else
        {
            return baseOutput;
        }
    }
}
