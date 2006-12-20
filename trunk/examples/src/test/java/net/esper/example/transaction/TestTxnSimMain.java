package net.esper.example.transaction;

import net.esper.example.transaction.sim.TxnGenMain;
import junit.framework.TestCase;

public class TestTxnSimMain extends TestCase
{
    public void testTiny() throws Exception
    {
        TxnGenMain main = new TxnGenMain(20, 200);
        main.run();
    }

    public void testSmall() throws Exception
    {
        TxnGenMain main = new TxnGenMain(1000, 3000);
        main.run();
    }
}
