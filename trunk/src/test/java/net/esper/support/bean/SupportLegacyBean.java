package net.esper.support.bean;

import java.util.Map;

/**
 * Legacy Java class for testing non-JavaBean style accessor methods.
 */
public class SupportLegacyBean
{
    private String legacyBeanVal;
    private String[] stringArray;
    private Map<String, String> mapped;
    private LegacyNested legacyNested;

    public String fieldLegacyVal;
    public String[] fieldStringArray;
    public Map<String, String> fieldMapped;
    public LegacyNested fieldNested;

    public SupportLegacyBean(String legacyBeanVal)
    {
        this(legacyBeanVal, null, null, null);        
    }

    public SupportLegacyBean(String[] stringArray)
    {
        this(null, stringArray, null, null);
    }

    public SupportLegacyBean(String legacyBeanVal, String[] stringArray, Map<String, String> mapped, String legacyNested)
    {
        this.legacyBeanVal = legacyBeanVal;
        this.stringArray = stringArray;
        this.mapped = mapped;
        this.legacyNested = new LegacyNested(legacyNested);

        this.fieldLegacyVal = legacyBeanVal;
        this.fieldStringArray = stringArray;
        this.fieldMapped = mapped;
        this.fieldNested = this.legacyNested;
    }

    public String readLegacyBeanVal()
    {
        return legacyBeanVal;
    }

    public String[] readStringArray()
    {
        return stringArray;
    }

    public String readStringIndexed(int i)
    {
        return stringArray[i];
    }

    public String readMapByKey(String key)
    {
        return mapped.get(key);
    }

    public Map readMap()
    {
        return mapped;
    }

    public LegacyNested readLegacyNested()
    {
        return legacyNested;
    }

    public class LegacyNested
    {
        public String fieldNestedValue;

        public LegacyNested(String nestedValue)
        {
            this.fieldNestedValue = nestedValue;
        }

        public String readNestedValue()
        {
            return fieldNestedValue;
        }
    }
}
