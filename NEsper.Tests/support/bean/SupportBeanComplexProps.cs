using System;

using net.esper.compat;

namespace net.esper.support.bean
{
    public class SupportBeanComplexProps
    {
        private String simpleProperty;
        private EDictionary<string,string> mappedProps;
        private int[] indexedProps;
        private SupportBeanSpecialGetterNested nested;
        private EDictionary<String, String> mapProperty;
        private int[] arrayProperty;

        public static String[] PROPERTIES = {
    	    "simpleProperty", "mapped()", "indexed[]", "mapProperty", "arrayProperty", "nested"
        };

        public static SupportBeanComplexProps makeDefaultBean()
        {
            EDictionary<string, string> properties = new EHashDictionary<string, string>();
            properties.Add("keyOne", "valueOne");
            properties.Add("keyTwo", "valueTwo");

            EDictionary<string, string> mapProp = new EHashDictionary<String, String>();
            mapProp.Add("xOne", "yOne");
            mapProp.Add("xTwo", "yTwo");

            int[] arrayProp = new int[] { 10, 20, 30 };

            return new SupportBeanComplexProps(
                "simple", properties, new int[] { 1, 2 }, mapProp, arrayProp,
                "nestedValue",
                "nestedNestedValue");
        }

        public SupportBeanComplexProps(int[] indexedProps)
        {
            this.indexedProps = indexedProps;
        }

        public SupportBeanComplexProps(
            String simpleProperty, 
            EDictionary<String, String> mappedProps,
            int[] indexedProps,
            EDictionary<String, String> mapProperty,
            int[] arrayProperty,
            String nestedValue,
            String nestedNestedValue)
        {
            this.simpleProperty = simpleProperty;
            this.mappedProps = mappedProps;
            this.indexedProps = indexedProps;
            this.mapProperty = mapProperty;
            this.arrayProperty = arrayProperty;
            this.nested = new SupportBeanSpecialGetterNested(nestedValue, nestedNestedValue);
        }

        public String SimpleProperty
        {
            get { return simpleProperty; }
        }

        public EDictionary<String, String> MapProperty
        {
            get { return mapProperty; }
        }

        public String getMapped(String key)
        {
            return (String)mappedProps.Fetch(key);
        }

        public int getIndexed(int index)
        {
            return indexedProps[index];
        }

        public SupportBeanSpecialGetterNested Nested
        {
            get { return nested; }
        }

        public int[] ArrayProperty
        {
            get { return arrayProperty; }
        }

        public void setIndexed(int index, int value)
        {
            indexedProps[index] = value;
        }

        public class SupportBeanSpecialGetterNested
        {
            private String nestedValue;
            private SupportBeanSpecialGetterNestedNested nestedNested;

            public SupportBeanSpecialGetterNested(String nestedValue, String nestedNestedValue)
            {
                this.nestedValue = nestedValue;
                this.nestedNested = new SupportBeanSpecialGetterNestedNested(nestedNestedValue);
            }

            public String NestedValue
            {
                get { return nestedValue; }
            }

            public SupportBeanSpecialGetterNestedNested NestedNested
            {
                get { return nestedNested; }
            }
        }

        public class SupportBeanSpecialGetterNestedNested
        {
            private String nestedNestedValue;

            public SupportBeanSpecialGetterNestedNested(String nestedNestedValue)
            {
                this.nestedNestedValue = nestedNestedValue;
            }

            public String NestedNestedValue
            {
                get { return nestedNestedValue; }
            }
        }
    }
}
