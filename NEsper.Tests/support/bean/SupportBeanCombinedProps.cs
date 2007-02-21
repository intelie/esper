using System;

using net.esper.compat;

namespace net.esper.support.bean
{
    /// <summary>
    /// indexed[0].mapped('0ma').value = 0ma0
    /// </summary>
    
    public class SupportBeanCombinedProps
    {
        public NestedLevOne[] array
        {
            get { return indexed; }
        }
        
        public NestedLevOne[] Array
        {
            get { return indexed; }
        }

        public static String[] PROPERTIES = new String[] {
        	"indexed[]",
        	"array",
        	"Array"
        };

        public static SupportBeanCombinedProps makeDefaultBean()
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

        public virtual NestedLevOne getIndexed(int index)
        {
            return indexed[index];
        }

        public class NestedLevOne
        {
            private EDictionary<String, NestedLevTwo> map = new EHashDictionary<String, NestedLevTwo>();

            public NestedLevOne(String[][] keysAndValues)
            {
                for (int i = 0; i < keysAndValues.Length; i++)
                {
                    map.Put(keysAndValues[i][0], new NestedLevTwo(keysAndValues[i][1]));
                }
            }

            public virtual NestedLevTwo getMapped(String key)
            {
                return map.Fetch(key, null);
            }

            public EDictionary<String, NestedLevTwo> getMapprop()
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

            virtual public String value
            {
                get { return _value; }
            }

            private String _value;

            public NestedLevTwo(String value)
            {
                this._value = value;
            }
        }
    }
}
