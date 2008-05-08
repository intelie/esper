package com.espertech.esper.client;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class ConfigurationVariantStream implements Serializable
{
    private List<String> variantTypeAliases;
    private boolean isAcceptsAnyType;


    // test property policies:
    //   at least one type must know property
    //   all properties exists, all properties are object types, there is no type checking

    /**
     * Ctor.
     */
    public ConfigurationVariantStream()
    {
        variantTypeAliases = new ArrayList<String>();
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
        CONFIGURED_TYPES,
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
