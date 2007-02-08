using System;
using System.Collections.Generic;

namespace net.esper.support.bean
{

    /// <summary> Legacy Java class for testing non-JavaBean style accessor methods.</summary>
    public class SupportLegacyBean
    {
        private String legacyBeanVal;
        private String[] stringArray;
        private IDictionary<String, String> mapped;
        private LegacyNested legacyNested;

        public String fieldLegacyVal;
        public String[] fieldStringArray;
        public IDictionary<String, String> fieldMapped;
        public LegacyNested fieldNested;

        public SupportLegacyBean(String legacyBeanVal)
            : this(legacyBeanVal, null, null, null)
        {
        }

        public SupportLegacyBean(String[] stringArray)
            : this(null, stringArray, null, null)
        {
        }

        public SupportLegacyBean(String legacyBeanVal, String[] stringArray, IDictionary<String, String> mapped, String legacyNested)
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

        public virtual String readLegacyBeanVal()
        {
            return legacyBeanVal;
        }

        public virtual String[] readStringArray()
        {
            return stringArray;
        }

        public virtual String readStringIndexed(int i)
        {
            return stringArray[i];
        }

        public virtual String readMapByKey(String key)
        {
            return mapped[key];
        }

        public virtual IDictionary<string,string> readMap()
        {
            return mapped;
        }

        public virtual LegacyNested readLegacyNested()
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

            public virtual String readNestedValue()
            {
                return fieldNestedValue;
            }
        }
    }
}
