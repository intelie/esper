package net.esper.example.autoid;

import junit.framework.TestCase;

public class TestAutoIdSimMain extends TestCase
{
    public void testRun() throws Exception
    {
        AutoIdSimMain main = new AutoIdSimMain(10);
        main.run();
    }
}
