package net.esper.example.rsi;

import net.esper.example.stockticker.eventbean.StockTick;

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
