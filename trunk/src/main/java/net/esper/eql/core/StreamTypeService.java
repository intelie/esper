package net.esper.eql.core;

import net.esper.event.EventType;
import net.esper.eql.core.DuplicatePropertyException;
import net.esper.eql.core.PropertyNotFoundException;
import net.esper.eql.core.PropertyResolutionDescriptor;
import net.esper.eql.core.StreamNotFoundException;

/**
 * Service supplying stream number and property type information.
 */
public interface StreamTypeService
{
    /**
     * Returns the offset of the stream and the type of the property for the given property name,
     * by looking through the types offered and matching up.
     * @param propertyName - property name in event
     * @return descriptor with stream number, property type and property name
     * @throws DuplicatePropertyException to indicate property was found twice
     * @throws PropertyNotFoundException to indicate property could not be resolved
     */
    public PropertyResolutionDescriptor resolveByPropertyName(String propertyName)
            throws DuplicatePropertyException, PropertyNotFoundException;

    /**
     * Returns the offset of the stream and the type of the property for the given property name,
     * by using the specified stream name to resolve the property.
     * @param streamName - name of stream
     * @param propertyName - property name in event
     * @return descriptor with stream number, property type and property name
     * @throws PropertyNotFoundException to indicate property could not be resolved
     * @throws StreamNotFoundException to indicate stream name could not be resolved
     */
    public PropertyResolutionDescriptor resolveByStreamAndPropName(String streamName, String propertyName)
            throws PropertyNotFoundException, StreamNotFoundException;

    /**
     * Returns an array of event stream names in the order declared.
     * @return stream names
     */
    public String[] getStreamNames();

    /**
     * Returns an array of event types for each event stream in the order declared.
     * @return event types
     */
    public EventType[] getEventTypes();
}
