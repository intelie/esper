package com.espertech.esper.event.rev;

import com.espertech.esper.client.ConfigurationException;
import com.espertech.esper.client.ConfigurationRevisionEventType;
import com.espertech.esper.client.ConfigurationVariantStream;
import com.espertech.esper.event.EventAdapterException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.StatementStopService;

import java.util.*;

/**
 * Service for handling revision event types.
 * <p>
 * Each named window instance gets a dedicated revision processor. 
 */
public class RevisionServiceImpl implements RevisionService
{
    private final EventAdapterService eventAdapterService;
    private final Map<String, RevisionSpec> specificationsByRevisionAlias;
    private final Map<String, RevisionProcessor> processorsByNamedWindow;
    private final Map<String, RevisionProcessor> variantProcessors;

    /**
     * Ctor.
     */
    public RevisionServiceImpl(EventAdapterService eventAdapterService)
    {
        this.eventAdapterService = eventAdapterService;
        this.specificationsByRevisionAlias = new HashMap<String, RevisionSpec>();
        this.processorsByNamedWindow = new HashMap<String, RevisionProcessor>();
        variantProcessors = new HashMap<String, RevisionProcessor>();
    }

    public void init(Map<String, ConfigurationRevisionEventType> config, EventAdapterService eventAdapterService)
            throws ConfigurationException
    {
        for (Map.Entry<String, ConfigurationRevisionEventType> entry : config.entrySet())
        {
            initializeSpecifications(entry.getKey(), entry.getValue(), eventAdapterService);
        }
    }

    public void addVariantEventType(String variantEventTypeAlias, ConfigurationVariantStream variantStreamConfig, EventAdapterService eventAdapterService) throws ConfigurationException
    {
        Set<EventType> types = new LinkedHashSet<EventType>();
        for (String alias : variantStreamConfig.getVariantTypeAliases())
        {
            EventType type = eventAdapterService.getExistsTypeByAlias(alias);
            if (type == null)
            {
                throw new ConfigurationException("Event type by name '" + alias + "' could not be found for use in variant event type configuration by name '" + variantEventTypeAlias + "'");
            }
            types.add(type);
        }

        EventType[] eventTypes = types.toArray(new EventType[types.size()]);
        VariantRevisionProcessor processor = new VariantRevisionProcessor(variantEventTypeAlias, eventTypes);
        eventAdapterService.addTypeByAlias(variantEventTypeAlias, processor.getEventType());
        variantProcessors.put(variantEventTypeAlias, processor);
    }

    public void addRevisionEventType(String alias, ConfigurationRevisionEventType config, EventAdapterService eventAdapterService)
            throws ConfigurationException
    {
        initializeSpecifications(alias, config, eventAdapterService);
    }    

    public EventType createRevisionType(String namedWindowName, String alias, StatementStopService statementStopService, EventAdapterService eventAdapterService)
    {
        RevisionSpec spec = specificationsByRevisionAlias.get(alias);
        RevisionProcessor processor;
        if (spec.getPropertyRevision() == ConfigurationRevisionEventType.PropertyRevision.OVERLAY_DECLARED)
        {
            processor = new RevisionProcessorDeclared(alias, spec, statementStopService, eventAdapterService);
        }
        else
        {
            processor = new RevisionProcessorMerge(alias, spec, statementStopService, eventAdapterService);
        }

        processorsByNamedWindow.put(namedWindowName, processor);
        return processor.getEventType();
    }

    public RevisionProcessor getRevisionProcessor(String alias)
    {
        RevisionProcessor proc = processorsByNamedWindow.get(alias);
        if (proc != null)
        {
            return proc;
        }
        return variantProcessors.get(alias);
    }

    public EventType getIsNamedWindowRevisionType(String namedWindowName, EventType eventType)
    {
        RevisionProcessor revisionProcessor = processorsByNamedWindow.get(namedWindowName);
        if (revisionProcessor == null)
        {
            // TODO refine
            if (variantProcessors.containsKey(namedWindowName))
            {
                return variantProcessors.get(namedWindowName).getEventType();
            }
            return null;
        }
        boolean isRevisionableType = revisionProcessor.validateRevisionableEventType(eventType);
        if (!isRevisionableType)
        {
            throw new EventAdapterException("Selected event type is not a valid base or delta event type of revision event type '"
                    + revisionProcessor.getRevisionEventTypeAlias() + "'");
        }
        return revisionProcessor.getEventType();
    }

    public EventType getRevisionUnderlyingType(String alias)
    {
        RevisionSpec spec = specificationsByRevisionAlias.get(alias);
        return spec.getBaseEventType();
    }
    
    public boolean isRevisionTypeAlias(String revisionTypeAlias)
    {
        return specificationsByRevisionAlias.containsKey(revisionTypeAlias);
    }

    private void initializeSpecifications(String revisionEventTypeAlias, ConfigurationRevisionEventType config, EventAdapterService eventAdapterService)
            throws ConfigurationException
    {
        if ((config.getAliasBaseEventTypes() == null) || (config.getAliasBaseEventTypes().size() == 0))
        {
            throw new ConfigurationException("Required base event type alias is not set in the configuration for revision event type '" + revisionEventTypeAlias + "'");
        }

        if (config.getAliasBaseEventTypes().size() > 1)
        {
            throw new ConfigurationException("Only one base event type alias may be added to revision event type '" + revisionEventTypeAlias + "', multiple base types are not yet supported");
        }

        // get base types
        String baseEventTypeAlias = config.getAliasBaseEventTypes().iterator().next();
        EventType baseEventType = eventAdapterService.getExistsTypeByAlias(baseEventTypeAlias);
        if (baseEventType == null)
        {
            throw new ConfigurationException("Could not locate event type for alias '" + baseEventTypeAlias + "' in the configuration for revision event type '" + revisionEventTypeAlias + "'");
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

        // make sure the key properties exist the base type and all delta types
        checkKeysExist(baseEventType, baseEventTypeAlias, config.getKeyPropertyNames(), revisionEventTypeAlias);
        for (int i = 0; i < deltaTypes.length; i++)
        {
            checkKeysExist(deltaTypes[i], deltaAliases[i], config.getKeyPropertyNames(), revisionEventTypeAlias);
        }

        // key property names shared between base and delta must have the same type
        String keyPropertyNames[] = PropertyUtility.copyAndSort(config.getKeyPropertyNames());
        for (String key : keyPropertyNames)
        {
            Class typeProperty = baseEventType.getPropertyType(key);
            for (EventType dtype : deltaTypes)
            {
                Class dtypeProperty = dtype.getPropertyType(key);
                if ((dtypeProperty != null) && (typeProperty != dtypeProperty))
                {
                    throw new ConfigurationException("Key property named '" + key + "' does not have the same type for base and delta types of revision event type '" + revisionEventTypeAlias + "'");
                }
            }
        }

        RevisionSpec specification = null;
        // In the "declared" type the change set properties consist of only :
        //   (base event type properties) minus (key properties) minus (properties only on base event type)
        if (config.getPropertyRevision() == ConfigurationRevisionEventType.PropertyRevision.OVERLAY_DECLARED)
        {
            // determine non-key properties: those overridden by any delta, and those simply only present on the base event type
            String nonkeyPropertyNames[] = PropertyUtility.uniqueExclusiveSort(baseEventType.getPropertyNames(), keyPropertyNames);
            Set<String> baseEventOnlyProperties = new HashSet<String>();
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
                    baseEventOnlyProperties.add(nonKey);
                }
            }

            String changesetProperties[] = changesetPropertyNames.toArray(new String[changesetPropertyNames.size()]);
            String baseEventOnlyPropertyNames[] = baseEventOnlyProperties.toArray(new String[baseEventOnlyProperties.size()]);
            PropertyUtility.removePropNamePostfixes(changesetProperties);
            PropertyUtility.removePropNamePostfixes(baseEventOnlyPropertyNames);

            // verify that all changeset properties match event type
            for (String changesetProperty : changesetProperties)
            {
                Class typeProperty = baseEventType.getPropertyType(changesetProperty);
                for (EventType dtype : deltaTypes)
                {
                    Class dtypeProperty = dtype.getPropertyType(changesetProperty);
                    if ((dtypeProperty != null) && (typeProperty != dtypeProperty))
                    {
                        throw new ConfigurationException("Property named '" + changesetProperty + "' does not have the same type for base and delta types of revision event type '" + revisionEventTypeAlias + "'");
                    }
                }
            }

            specification = new RevisionSpec(config.getPropertyRevision(), baseEventType, deltaTypes, deltaAliases, keyPropertyNames, changesetProperties, baseEventOnlyPropertyNames, false, null);
        }
        else
        {
            // In the "exists" type the change set properties consist of all properties: base event properties plus delta types properties
            Set<String> allProperties = new HashSet<String>();
            allProperties.addAll(Arrays.asList(baseEventType.getPropertyNames()));
            for (EventType deltaType : deltaTypes)
            {
                allProperties.addAll(Arrays.asList(deltaType.getPropertyNames()));
            }

            String[] allPropertiesArr = allProperties.toArray(new String[allProperties.size()]);
            String[] changesetProperties = PropertyUtility.uniqueExclusiveSort(allPropertiesArr, keyPropertyNames);
            PropertyUtility.removePropNamePostfixes(allPropertiesArr);
            PropertyUtility.removePropNamePostfixes(changesetProperties);

            // All properties must have the same type, if a property exists for any given type
            boolean hasContributedByDelta = false;
            boolean[] contributedByDelta = new boolean[changesetProperties.length];
            count = 0;
            for (String property : changesetProperties)
            {
                if (property.endsWith("[]"))
                {
                    property = property.replace("[]", "");
                }
                if (property.endsWith("()"))
                {
                    property = property.replace("()", "");
                }

                Class basePropertyType = baseEventType.getPropertyType(property);
                Class typeTemp = null;
                if (basePropertyType != null)
                {
                    typeTemp = basePropertyType;
                }
                else
                {
                    hasContributedByDelta = true;
                    contributedByDelta[count] = true;
                }
                for (EventType dtype : deltaTypes)
                {
                    Class dtypeProperty = dtype.getPropertyType(property);
                    if (dtypeProperty != null)
                    {
                        if ((typeTemp != null) && (dtypeProperty != typeTemp))
                        {
                            throw new ConfigurationException("Property named '" + property + "' does not have the same type for base and delta types of revision event type '" + revisionEventTypeAlias + "'");
                        }

                    }
                    typeTemp = dtypeProperty;
                }
                count++;
            }

            // Compile changeset
            specification = new RevisionSpec(config.getPropertyRevision(), baseEventType, deltaTypes, deltaAliases, keyPropertyNames, changesetProperties, new String[0], hasContributedByDelta, contributedByDelta);
        }
        specificationsByRevisionAlias.put(revisionEventTypeAlias, specification);
    }

    private void checkKeysExist(EventType baseEventType, String alias, String[] keyProperties, String revisionEventTypeAlias)
    {
        String propertyNames[] = baseEventType.getPropertyNames();
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
