using System;

using net.esper.compat;

namespace net.esper.support.bean
{
    /// <summary>
    /// indexed[0].mapped('0ma').value = 0ma0
    /// </summary>
    
    public class SupportBeanCombinedProps
    {
        public NestedLevOne[] Array
        {
            get { return indexed; }
        }

        public static String[] PROPERTIES = new String[]
        {
        	"indexed[]",
        	"Array"
        };

        public static SupportBeanCombinedProps MakeDefaultBean()
        {
            NestedLevOne[] nested = new NestedLevOne[4]; // [3] left empty on purpose
            nested[0] = new NestedLevOne(new String[][] {
                new String[] { "0ma", "0ma0" },
                new String[] { "0mb", "0ma1" }
            });
            nested[1] = new NestedLevOne(new String[][] {
                new String[] { "1ma", "1ma0" },
                new String[] { "1mb", "1ma1" }
            });
            nested[2] = new NestedLevOne(new String[][] {
                new String[] { "2ma", "valueOne" }, 
                new String[] { "2mb", "2ma1" }
            });

            return new SupportBeanCombinedProps(nested);
        }

        private NestedLevOne[] indexed;

        public SupportBeanCombinedProps(NestedLevOne[] indexed)
        {
            this.indexed = indexed;
        }

        public virtual NestedLevOne GetIndexed(int index)
        {
            return indexed[index];
        }

        public class NestedLevOne
        {
            private readonly EDictionary<String, NestedLevTwo> map = new HashDictionary<String, NestedLevTwo>();

            public NestedLevOne(String[][] keysAndValues)
            {
                for (int i = 0; i < keysAndValues.Length; i++)
                {
                    map.Put(keysAndValues[i][0], new NestedLevTwo(keysAndValues[i][1]));
                }
            }

            public virtual NestedLevTwo GetMapped(String key)
            {
                return map.Fetch(key, null);
            }

            public EDictionary<String, NestedLevTwo> GetMapProp()
            {
                return map;
            }
        }

        public class NestedLevTwo
        {
            virtual public String Value
            {
                get { return _value;  }
            }

            public string GetValue()
            {
                return _value;
            }

            public void SetValue(string value)
            {
                _value = value;
            }

            private String _value;

            public NestedLevTwo(String value)
            {
                this._value = value;
            }
        }
    }
}
