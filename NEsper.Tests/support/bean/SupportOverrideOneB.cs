using System;

namespace net.esper.support.bean
{
    public class SupportOverrideOneB : SupportOverrideOne
    {
        override public String Val
        {
            get { return valOneB; }
        }

        private String valOneB;

        public SupportOverrideOneB(String valOneB, String valOne, String valBase)
            : base(valOne, valBase)
        {
            this.valOneB = valOneB;
        }
    }
}