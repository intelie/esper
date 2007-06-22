using System;

using net.esper.compat;

namespace net.esper.support.bean
{
    public class SupportBeanComplexProps
    {
        private String _simpleProperty;
        private EDictionary<string, string> _mappedProps;
        private int[] _indexedProps;
        private SupportBeanSpecialGetterNested _nested;
        private EDictionary<String, String> _mapProperty;
        private int[] _arrayProperty;

        public static String[] PROPERTIES = {
    	    "simpleProperty",
    	    "mapped()",
    	    "indexed[]",
    	    "mapProperty",
    	    "MapProperty",
    	    "arrayProperty",
    	    "ArrayProperty",
    	    "nested",
    	    "Nested"
        };

        public static SupportBeanComplexProps MakeDefaultBean()
        {
            EDictionary<string, string> properties = new HashDictionary<string, string>();
            properties.Add("keyOne", "valueOne");
            properties.Add("keyTwo", "valueTwo");

            EDictionary<string, string> mapProp = new HashDictionary<String, String>();
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
            this._indexedProps = indexedProps;
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
            this._simpleProperty = simpleProperty;
            this._mappedProps = mappedProps;
            this._indexedProps = indexedProps;
            this._mapProperty = mapProperty;
            this._arrayProperty = arrayProperty;
            this._nested = new SupportBeanSpecialGetterNested(nestedValue, nestedNestedValue);
        }

        public String SimpleProperty
        {
            get { return _simpleProperty; }
        }

        public EDictionary<String, String> MapProperty
        {
            get { return _mapProperty; }
        }

        public String GetMapped(String key)
        {
            return (String)_mappedProps.Fetch(key);
        }

        public int GetIndexed(int index)
        {
            return _indexedProps[index];
        }

        public SupportBeanSpecialGetterNested Nested
        {
            get { return _nested; }
        }

        public int[] ArrayProperty
        {
            get { return _arrayProperty; }
        }

        public int[] GetArrayProperty()
        {
            return _arrayProperty;
        }

        public void SetIndexed(int index, int value)
        {
            _indexedProps[index] = value;
        }

        public class SupportBeanSpecialGetterNested
        {
            private String _nestedValue;
            private SupportBeanSpecialGetterNestedNested _nestedNested;

            public SupportBeanSpecialGetterNested(String nestedValue, String nestedNestedValue)
            {
                this._nestedValue = nestedValue;
                this._nestedNested = new SupportBeanSpecialGetterNestedNested(nestedNestedValue);
            }

            public String NestedValue
            {
                get { return _nestedValue; }
            }

            public SupportBeanSpecialGetterNestedNested NestedNested
            {
                get { return _nestedNested; }
            }
        }

        public class SupportBeanSpecialGetterNestedNested
        {
            private String _nestedNestedValue;

            public SupportBeanSpecialGetterNestedNested(String nestedNestedValue)
            {
                this._nestedNestedValue = nestedNestedValue;
            }

            public String NestedNestedValue
            {
                get { return _nestedNestedValue; }
            }
        }
    }
}
