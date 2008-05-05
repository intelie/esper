package com.espertech.esper.event.rev;

import com.espertech.esper.client.ConfigurationException;
import com.espertech.esper.client.ConfigurationRevisionEventType;
import com.espertech.esper.event.EventAdapterException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.StatementStopService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RevisionServiceImpl implements RevisionService
{
    private final Map<String, RevisionSpec> specificationsByRevisionAlias;
    private final Map<String, RevisionProcessor> processorsByNamedWindow;

    public RevisionServiceImpl()
    {
        this.specificationsByRevisionAlias = new HashMap<String, RevisionSpec>();
        this.processorsByNamedWindow = new HashMap<String, RevisionProcessor>();
    }

    public void init(Map<String, ConfigurationRevisionEventType> config, EventAdapterService eventAdapterService)
            throws ConfigurationException
    {
        for (Map.Entry<String, ConfigurationRevisionEventType> entry : config.entrySet())
        {
            initializeSpecifications(entry.getKey(), entry.getValue(), eventAdapterService);
        }
    }

    public void add(String alias, ConfigurationRevisionEventType config, EventAdapterService eventAdapterService)
            throws ConfigurationException
    {
        initializeSpecifications(alias, config, eventAdapterService);
    }    

    public EventType createRevisionType(String namedWindowName, String alias, StatementStopService statementStopService, EventAdapterService eventAdapterService)
    {
        RevisionSpec spec = specificationsByRevisionAlias.get(alias);
        RevisionProcessor processor;
        if (spec.getPropertyRevision() == ConfigurationRevisionEventType.PropertyRevision.DECLARED)
        {
            processor = new RevisionProcessorDeclared(alias, spec, statementStopService);
        }
        else
        {
            processor = new RevisionProcessorExists(alias, spec, statementStopService);
        }

        processorsByNamedWindow.put(namedWindowName, processor);
        return processor.getEventType();
    }

    public RevisionProcessor getRevisionProcessor(String alias)
    {
        return processorsByNamedWindow.get(alias);
    }

    public EventType getIsNamedWindowRevisionType(String namedWindowName, EventType eventType)
    {
        RevisionProcessor revisionProcessor = processorsByNamedWindow.get(namedWindowName);
        if (revisionProcessor == null)
        {
            return null;
        }
        boolean isRevisionableType = revisionProcessor.validateRevisionableEventType(eventType);
        if (!isRevisionableType)
        {
            throw new EventAdapterException("Selected event type is not a valid full or delta event type of revision event type '"
                    + revisionProcessor.getRevisionEventTypeAlias() + "'");
        }
        return revisionProcessor.getEventType();
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

    private void initializeSpecifications(String revisionEventTypeAlias, ConfigurationRevisionEventType config, EventAdapterService eventAdapterService)
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

        // key property names shared between full and delta must have the same type
        String keyPropertyNames[] = PropertyGroupBuilder.copyAndSort(config.getKeyPropertyNames());
        for (String key : keyPropertyNames)
        {
            Class typeProperty = fullEventType.getPropertyType(key);
            for (EventType dtype : deltaTypes)
            {
                Class dtypeProperty = dtype.getPropertyType(key);
                if ((dtypeProperty != null) && (typeProperty != dtypeProperty))
                {
                    throw new ConfigurationException("Key property named '" + key + "' does not have the same type for full and delta types of revision event type '" + revisionEventTypeAlias + "'");
                }
            }
        }

        // determine non-key properties: those overridden by any delta, and those simply only present on the full event type
        String nonkeyPropertyNames[] = PropertyGroupBuilder.uniqueExclusiveSort(fullEventType.getPropertyNames(), keyPropertyNames);
        Set<String> fullEventOnlyProperties = new HashSet<String>();
        Set<String> changesetPropertyNames = new HashSet<String>();
        for (String nonKey : nonkeyPropertyNames)
        {
            boolean overriddenProperty = false;
            for (EventType type : deltaTypes)
            {
                if (type.isProperty(nonKey))
                {
                    changesetPropertyNames.add(nonKey);
                    overriddenProperty = true;
                    break;
                }
            }
            if (!overriddenProperty)
            {
                fullEventOnlyProperties.add(nonKey);
            }
        }

        String changesetProperties[] = changesetPropertyNames.toArray(new String[changesetPropertyNames.size()]);
        String fullEventOnlyPropertyNames[] = fullEventOnlyProperties.toArray(new String[fullEventOnlyProperties.size()]);

        // verify that all changeset properties match event type
        for (String changesetProperty : changesetProperties)
        {
            Class typeProperty = fullEventType.getPropertyType(changesetProperty);
            for (EventType dtype : deltaTypes)
            {
                Class dtypeProperty = dtype.getPropertyType(changesetProperty);
                if ((dtypeProperty != null) && (typeProperty != dtypeProperty))
                {
                    throw new ConfigurationException("Key property named '" + changesetProperty + "' does not have the same type for full and delta types of revision event type '" + revisionEventTypeAlias + "'");
                }
            }
        }

        // build the property groups
        RevisionSpec processor = new RevisionSpec(config.getPropertyRevision(), fullEventType, deltaTypes, deltaAliases, keyPropertyNames, changesetProperties, fullEventOnlyPropertyNames);
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
