package net.esper.adapter.csv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.TreeMap;

import sun.rmi.runtime.NewThreadAction;

import net.esper.adapter.csv.CSVTimer;

import junit.framework.TestCase;

public class TestCSVTimer extends TestCase
{
	private Integer currentCallback;
	
	protected void setUp()
	{
		currentCallback = null;
	}
	
	public void testStart() throws Exception
	{
		CSVTimer timer = new CSVTimer();
		
		timer.schedule(new TestTimerTask(0), 0);
		timer.schedule(new TestTimerTask(1), 400);
		timer.schedule(new TestTimerTask(2), 600);
		
		timer.start();
		
		Thread.sleep(300);
		assertCallbackAndReset(0);
		
		Thread.sleep(200);
		assertCallbackAndReset(1);
		

		Thread.sleep(200);
		assertCallbackAndReset(2);
		
		timer.schedule(new TestTimerTask(3), 800);
		
		Thread.sleep(100);
		assertCallbackAndReset(3);
		
		Thread.sleep(200);
		assertNull(currentCallback);
	}
	
	private void assertCallbackAndReset(Integer ident)
	{
		assertNotNull(currentCallback);
		assertEquals(ident, currentCallback);
		currentCallback = null;
	}
	
	private class TestTimerTask extends TimerTask
	{
		private final int ident;
		
		public TestTimerTask(int ident)
		{
			this.ident = ident;
		}
		
		public void run()
		{
			currentCallback = ident;
		}
	}
}
