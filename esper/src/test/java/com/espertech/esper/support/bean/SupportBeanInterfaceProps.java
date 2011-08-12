/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

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
