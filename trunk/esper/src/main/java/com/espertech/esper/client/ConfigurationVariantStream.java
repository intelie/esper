package com.espertech.esper.client;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Configures a variant stream.
 */
public class ConfigurationVariantStream implements Serializable
{
    private List<String> variantTypeAliases;
    private TypeVariance typeVariance;

    /**
     * Ctor.
     */
    public ConfigurationVariantStream()
    {
        variantTypeAliases = new ArrayList<String>();
        typeVariance = TypeVariance.PREDEFINED;
    }

    /**
     * Returns the type variance setting specifying whether the variant stream accepts event of
     * only the predefined types or any type.
     * @return type variance setting
     */
    public TypeVariance getTypeVariance()
    {
        return typeVariance;
    }

    /**
     * Sets the type variance setting specifying whether the variant stream accepts event of
     * only the predefined types or any type.
     * @param typeVariance type variance setting
     */
    public void setTypeVariance(TypeVariance typeVariance)
    {
        this.typeVariance = typeVariance;
    }

    /**
     * Returns the aliases of event types that a predefined for the variant stream.
     * @return predefined types in the variant stream
     */
    public List<String> getVariantTypeAliases()
    {
        return variantTypeAliases;
    }

    /**
     * Adds an aliases of an event types that is one of the predefined event typs allowed for the variant stream.
     * @param eventTypeAlias name of the event type to allow in the variant stream
     */
    public void addEventTypeAlias(String eventTypeAlias)
    {
        variantTypeAliases.add(eventTypeAlias);
    }

    /**
     * Enumeration specifying whether only the predefine types or any type of event is accepted by the variant stream.
     */
    public enum TypeVariance
    {
        /**
         * Allow only the predefined types to be inserted into the stream.
         */
        PREDEFINED,

        /**
         * Allow any types to be inserted into the stream.
         */
        ANY
    }
}
