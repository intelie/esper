package com.espertech.esper.client;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class ConfigurationVariantStream implements Serializable
{
    private List<String> variantTypeAliases;
    private TypeVariance typeVariance;
    private PropertyVariance propertyVariance;

    /**
     * Ctor.
     */
    public ConfigurationVariantStream()
    {
        variantTypeAliases = new ArrayList<String>();
        typeVariance = TypeVariance.CONFIGURED_TYPES;
        propertyVariance = PropertyVariance.REQUIRE_TYPE_MATCH; 
    }

    public TypeVariance getTypeVariance()
    {
        return typeVariance;
    }

    public void setTypeVariance(TypeVariance typeVariance)
    {
        this.typeVariance = typeVariance;
    }

    public PropertyVariance getPropertyVariance()
    {
        return propertyVariance;
    }

    public void setPropertyVariance(PropertyVariance propertyVariance)
    {
        this.propertyVariance = propertyVariance;
    }

    public List<String> getVariantTypeAliases()
    {
        return variantTypeAliases;
    }

    public void addEventTypeAlias(String eventTypeAlias)
    {
        variantTypeAliases.add(eventTypeAlias);
    }

    public enum TypeVariance
    {
        // allow only the types configured
        CONFIGURED_TYPES,

        // allow any types inserted into stream
        ANY_TYPES
    }

    public enum PropertyVariance
    {
        // The stream only provides those properties for which each variant type provides the same property type
        REQUIRE_TYPE_MATCH,

        // The stream only provides those properties for which each variant type has a property but the type does not need to match
        REQUIRE_NAME_MATCH,

        // The stream provides all properties for which at least one variant type has a property
        PARTIAL_NAME_MATCH,

        // The stream provides any property asked for an returns an Object type
        ANY
    }
}
