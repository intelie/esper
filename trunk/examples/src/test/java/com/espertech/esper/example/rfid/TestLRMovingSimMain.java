package com.espertech.esper.example.rfid;

import junit.framework.TestCase;

public class TestLRMovingSimMain extends TestCase
{
    public void testSim() throws Exception
    {
        LRMovingSimMain main = new LRMovingSimMain(1, 100, 5, true);
        main.run();
    }
}
