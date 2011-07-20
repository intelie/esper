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

import java.util.ArrayList;
import java.util.List;

public class SupportBean_Container {

    private List<SupportBean> beans;

    public SupportBean_Container(List<SupportBean> beans) {
        this.beans = beans;
    }

    public List<SupportBean> getBeans() {
        return beans;
    }

    public void setBeans(List<SupportBean> beans) {
        this.beans = beans;
    }
}
