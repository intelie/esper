package com.espertech.esper.event.rev;

import com.espertech.esper.client.ConfigurationException;
import com.espertech.esper.client.ConfigurationRevisionEvent;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventType;

import java.util.HashMap;
import java.util.Map;

public class RevisionServiceImpl implements RevisionService
{
    private final Map<String, RevisionSpec> specificationsByRevisionAlias;
    private final Map<String, RevisionProcessor> processorsByNamedWindow;

    public RevisionServiceImpl()
    {
        this.specificationsByRevisionAlias = new HashMap<String, RevisionSpec>();
        this.processorsByNamedWindow = new HashMap<String, RevisionProcessor>();
    }

    public void init(Map<String, ConfigurationRevisionEvent> config, EventAdapterService eventAdapterService)
            throws ConfigurationException
    {
        for (Map.Entry<String, ConfigurationRevisionEvent> entry : config.entrySet())
        {
            initializeSpecifications(entry.getKey(), entry.getValue(), eventAdapterService);
        }
    }

    public void add(String alias, ConfigurationRevisionEvent config, EventAdapterService eventAdapterService)
            throws ConfigurationException
    {
        initializeSpecifications(alias, config, eventAdapterService);
    }    

    public RevisionProcessor getRevisionProcessor(String alias)
    {
        return processorsByNamedWindow.get(alias);
    }

    public RevisionEventType getNamedWindowRevisionType(String namedWindowName)
    {
        return processorsByNamedWindow.get(namedWindowName).getEventType();
    }

    public EventType getRevisionUnderlyingType(String alias)
    {
        RevisionSpec spec = specificationsByRevisionAlias.get(alias);
        return spec.getFullEventType();
    }
    
    public boolean isRevisionTypeAlias(String revisionTypeAlias)
    {
        return specificationsByRevisionAlias.containsKey(revisionTypeAlias);
    }

    public RevisionEventType createRevisionType(String namedWindowName, String alias)
    {
        RevisionSpec spec = specificationsByRevisionAlias.get(alias);
        RevisionProcessor processor = new RevisionProcessor(namedWindowName, spec);
        processorsByNamedWindow.put(namedWindowName, processor);
        return processor.getEventType();
    }

    public RevisionEventType getRevisionType(String alias)
    {
        RevisionProcessor processor = processorsByNamedWindow.get(alias);
        return processor.getEventType();
    }

    private void initializeSpecifications(String revisionEventTypeAlias, ConfigurationRevisionEvent config, EventAdapterService eventAdapterService)
            throws ConfigurationException
    {
        if (config.getAliasFullEventType() == null)
        {
            throw new ConfigurationException("Required full event type alias is not set in the configuration for revision event type '" + revisionEventTypeAlias + "'");
        }

        // get full types
        EventType fullEventType = eventAdapterService.getExistsTypeByAlias(config.getAliasFullEventType());
        if (fullEventType == null)
        {
            throw new ConfigurationException("Could not locate event type for alias '" + config.getAliasFullEventType() + "' in the configuration for revision event type '" + revisionEventTypeAlias + "'");
        }

        // get alias types
        EventType[] deltaTypes = new EventType[config.getAliasDeltaEventTypes().size()];
        String[] deltaAliases = new String[config.getAliasDeltaEventTypes().size()];
        int count = 0;
        for (String deltaAlias : config.getAliasDeltaEventTypes())
        {
            EventType deltaEventType = eventAdapterService.getExistsTypeByAlias(deltaAlias);
            if (deltaEventType == null)
            {
                throw new ConfigurationException("Could not locate event type for alias '" + deltaAlias + "' in the configuration for revision event type '" + revisionEventTypeAlias + "'");
            }
            deltaTypes[count] = deltaEventType;
            deltaAliases[count] = deltaAlias;
            count++;
        }

        // the key properties must be set
        if ((config.getKeyPropertyNames() == null) || (config.getKeyPropertyNames().length == 0))
        {
            throw new ConfigurationException("Required key properties are not set in the configuration for revision event type '" + revisionEventTypeAlias + "'");            
        }

        // make sure the key properties exist the full type and all delta types
        checkKeysExist(fullEventType, config.getAliasFullEventType(), config.getKeyPropertyNames(), revisionEventTypeAlias);
        for (int i = 0; i < deltaTypes.length; i++)
        {
            checkKeysExist(deltaTypes[i], deltaAliases[i], config.getKeyPropertyNames(), revisionEventTypeAlias);
        }

        String keyPropertyNames[] = PropertyGroupBuilder.copyAndSort(config.getKeyPropertyNames());

        // build the property groups
        RevisionSpec processor = new RevisionSpec(fullEventType, deltaTypes, deltaAliases, keyPropertyNames);
        specificationsByRevisionAlias.put(revisionEventTypeAlias, processor);
    }

    private void checkKeysExist(EventType fullEventType, String alias, String[] keyProperties, String revisionEventTypeAlias)
    {
        String propertyNames[] = fullEventType.getPropertyNames();
        for (String keyProperty : keyProperties)
        {
            boolean exists = false;
            for (String propertyName : propertyNames)
            {
                if (propertyName.equals(keyProperty))
                {
                    exists = true;
                    break;
                }
            }

            if (!exists)
            {
                throw new ConfigurationException("Key property '" + keyProperty + "' as defined in the configuration for revision event type '" + revisionEventTypeAlias + "' does not exists in event type '" + alias + "'");
            }
        }
    }
}
