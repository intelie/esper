/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.rsi;

import com.espertech.esper.example.stockticker.eventbean.StockTick;

public class RSIEvent {

    private StockTick _tick;
    private double _avgLoss, _avgGain;
    private double _rs, _rsi;

    public RSIEvent(StockTick tick_, double avgLoss_, double avgGain_, double rs_, double rsi_)
    {
        _tick = tick_;
        _avgLoss = avgLoss_;
        _avgGain = avgGain_;
        _rs = rs_;
        _rsi = rsi_;
    }

    public StockTick getTick()
    {
        return _tick;
    }

    public double getAvgLoss()
    {
        return _avgLoss;
    }

    public double getAvgGain()
    {
        return _avgGain;
    }

    public double getRS()
    {
        return _rs;
    }

    public double getRSI()
    {
        return _rsi;
    }

    public String toString()
    {
        return _tick.toString() +
                "  avgLoss=" + _avgLoss +
                "  avgGain=" + _avgGain +
                " RS=" + _rs + " RSI=" + + _rsi;
    }

}
