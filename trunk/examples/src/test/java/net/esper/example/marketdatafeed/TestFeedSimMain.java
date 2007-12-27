package net.esper.example.marketdatafeed;

import junit.framework.TestCase;

public class TestFeedSimMain extends TestCase
{
    public void testRun() throws Exception
    {
        FeedSimMain main = new FeedSimMain(100, 50, 5, false);
        main.run();
    }
}
