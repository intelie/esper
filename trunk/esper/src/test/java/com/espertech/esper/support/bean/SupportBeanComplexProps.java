package com.espertech.esper.support.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.Serializable;

public class SupportBeanComplexProps implements Serializable, SupportMarkerInterface
{
    private String simpleProperty;
	private Properties mappedProps;
	private int[] indexedProps;
	private SupportBeanSpecialGetterNested nested;
	private Map<String, String> mapProperty;
	private int[] arrayProperty;
    
    public static String[] PROPERTIES = 
            { "simpleProperty", "mapped", "indexed", "mapProperty", "arrayProperty", "nested"};
		
	public static SupportBeanComplexProps makeDefaultBean()
	{
        Properties properties = new Properties();
        properties.put("keyOne", "valueOne");        
        properties.put("keyTwo", "valueTwo");        
        
        Map<String, String> mapProp = new HashMap<String, String>();
        mapProp.put("xOne", "yOne");
        mapProp.put("xTwo", "yTwo");
        
        int[] arrayProp = new int[] {10, 20, 30};

        return new SupportBeanComplexProps("simple", properties, new int[] {1, 2}, mapProp, arrayProp, "nestedValue", "nestedNestedValue");
	}
	
    public SupportBeanComplexProps(int[] indexedProps)
    {
        this.indexedProps = indexedProps;
    }

	public SupportBeanComplexProps(String simpleProperty, Properties mappedProps, int[] indexedProps, Map<String, String> mapProperty, int[] arrayProperty, String nestedValue, String nestedNestedValue)
	{
        this.simpleProperty = simpleProperty;
		this.mappedProps = mappedProps;
		this.indexedProps = indexedProps;
		this.mapProperty = mapProperty;
		this.arrayProperty = arrayProperty;
        this.nested = new SupportBeanSpecialGetterNested(nestedValue, nestedNestedValue);
	}

    public String getSimpleProperty()
    {
        return simpleProperty;
    }

    public void setSimpleProperty(String simpleProperty)
    {
        this.simpleProperty = simpleProperty;
    }

    public Map<String, String> getMapProperty()
	{
		return mapProperty;
	}
	
	public String getMapped(String key)
	{
		return (String) mappedProps.get(key);
	}
	
	public int getIndexed(int index)
	{
		return indexedProps[index];
	}

	public SupportBeanSpecialGetterNested getNested()
	{
		return nested;
	}
		
	public int[] getArrayProperty()
	{
		return arrayProperty;
	}

    public void setIndexed(int index, int value)
    {
        indexedProps[index] = value;
    }

    public static class SupportBeanSpecialGetterNested implements Serializable
	{
		private String nestedValue;
        private SupportBeanSpecialGetterNestedNested nestedNested;

		public SupportBeanSpecialGetterNested(String nestedValue, String nestedNestedValue)
		{
			this.nestedValue = nestedValue;
            this.nestedNested = new SupportBeanSpecialGetterNestedNested(nestedNestedValue);
		}
	
		public String getNestedValue() 
		{
			return nestedValue;
		}

        public void setNestedValue(String nestedValue)
        {
            this.nestedValue = nestedValue;
        }

        public SupportBeanSpecialGetterNestedNested getNestedNested()
        {
            return nestedNested;
        }

        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }

            SupportBeanSpecialGetterNested that = (SupportBeanSpecialGetterNested) o;

            if (!nestedValue.equals(that.nestedValue))
            {
                return false;
            }

            return true;
        }

        public int hashCode()
        {
            return nestedValue.hashCode();
        }
    }

    public static class SupportBeanSpecialGetterNestedNested implements Serializable
    {
        private String nestedNestedValue;

        public SupportBeanSpecialGetterNestedNested(String nestedNestedValue)
        {
            this.nestedNestedValue = nestedNestedValue;
        }

        public String getNestedNestedValue()
        {
            return nestedNestedValue;
        }

        public void setNestedNestedValue(String nestedNestedValue)
        {
            this.nestedNestedValue = nestedNestedValue;
        }
    }
}
