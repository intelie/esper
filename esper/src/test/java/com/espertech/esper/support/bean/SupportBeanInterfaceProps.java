package com.espertech.esper.support.bean;

public class SupportBeanInterfaceProps
{
    private ISupportA isa;
    private ISupportAImplSuperG isg;
    
    public ISupportA getIsa()
    {
        return isa;
    }

    public void setIsa(ISupportA isa)
    {
        this.isa = isa;
    }

    public ISupportAImplSuperG getIsg()
    {
        return isg;
    }

    public void setIsg(ISupportAImplSuperG isg)
    {
        this.isg = isg;
    }
}
