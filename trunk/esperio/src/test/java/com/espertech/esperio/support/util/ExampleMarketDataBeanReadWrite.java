/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.support.util;

import com.espertech.esperio.regression.adapter.TestCSVAdapterUseCases;

public class ExampleMarketDataBeanReadWrite extends TestCSVAdapterUseCases.ExampleMarketDataBean
{
    public double getValue() {
        return this.getPrice() * this.getVolume();
    }
}
