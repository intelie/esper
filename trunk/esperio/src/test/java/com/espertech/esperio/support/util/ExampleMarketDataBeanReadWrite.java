package com.espertech.esperio.support.util;

import com.espertech.esperio.regression.adapter.TestCSVAdapterUseCases;

public class ExampleMarketDataBeanReadWrite extends TestCSVAdapterUseCases.ExampleMarketDataBean
{
    public double getValue() {
        return this.getPrice() * this.getVolume();
    }
}
