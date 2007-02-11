using System;

namespace net.esper.support.bean
{
    public class SupportOverrideOneA : SupportOverrideOne
    {
        override public String Val
        {
            get { return valOneA; }
        }

        private String valOneA;

        public SupportOverrideOneA(String valOneA, String valOne, String valBase)
            : base(valOne, valBase)
        {
            this.valOneA = valOneA;
        }
    }
}