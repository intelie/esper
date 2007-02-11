using System;

namespace net.esper.support.bean
{
    public class SupportOverrideOne : SupportOverrideBase
    {
        override public String Val
        {
            get { return valOne; }
        }

        private String valOne;

        public SupportOverrideOne(String valOne, String valBase)
            : base(valBase)
        {
            this.valOne = valOne;
        }
    }
}
