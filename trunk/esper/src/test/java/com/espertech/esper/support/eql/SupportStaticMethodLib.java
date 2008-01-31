package com.espertech.esper.support.eql;

import com.espertech.esper.support.bean.SupportBean_S0;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;

import java.util.Map;
import java.util.HashMap;

public class SupportStaticMethodLib 
{
    public static boolean compareEvents(SupportMarketDataBean beanOne, SupportBean beanTwo)
    {
        return beanOne.getSymbol().equals(beanTwo.getString());
    }

    public static Map fetchMapMetadata()
    {
        Map<String, Class> values = new HashMap<String, Class>();
        values.put("mapstring", String.class);
        values.put("mapint", Integer.class);
        return values;
    }

    public static Map fetchMapArrayMetadata()
    {
        Map<String, Class> values = new HashMap<String, Class>();
        values.put("mapstring", String.class);
        values.put("mapint", Integer.class);
        return values;
    }

    public static Map[] fetchMapArray(String string, int id)
    {
        if (id < 0)
        {
            return null;
        }

        if (id == 0)
        {
            return new Map[0];
        }

        Map[] rows = new Map[id];
        for (int i = 0; i < id; i++)
        {
            Map<String, Object> values = new HashMap<String, Object>();
            rows[i] = values;

            values.put("mapstring", "|" + string + "_" + i + "|");
            values.put("mapint", i + 100);
        }

        return rows;
    }

    public static Map fetchMap(String string, int id)
    {
        if (id < 0)
        {
            return null;
        }

        Map<String, Object> values = new HashMap<String, Object>();
        if (id == 0)
        {
            return values;
        }
        
        values.put("mapstring", "|" + string + "|");
        values.put("mapint", id + 1);
        return values;
    }

    public static Map convertEventMap(Map<String, Object> values)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("one", values.get("one"));
        result.put("two", "|" + values.get("two") + "|");
        return result;
    }

    public static SupportBean convertEvent(SupportMarketDataBean bean)
    {
        return new SupportBean(bean.getSymbol(), (bean.getVolume()).intValue());
    }

    public static Object staticMethod(Object object)
	{
		return object;
	}
	
	public static void throwException() throws Exception
	{
		throw new Exception("throwException text here");
	}

    public static SupportBean throwExceptionBeanReturn() throws Exception
    {
        throw new Exception("throwException text here");
    }

    public static boolean isStringEquals(String value, String compareTo)
    {
        return value.equals(compareTo);
    }

    public static double minusOne(double value)
    {
        return value - 1;
    }

    public static String appendPipe(String string, String value)
    {
        return string + "|" + value;
    }

    public static SupportBean_S0 fetchObjectAndSleep(String fetchId, int passThroughNumber, long msecSleepTime)
    {
        try
        {
            Thread.sleep(msecSleepTime);
        }
        catch (InterruptedException e)
        {
        }
        return new SupportBean_S0(passThroughNumber, "|" + fetchId + "|");
    }

    public static FetchedData fetchObjectNoArg()
    {
        return new FetchedData("2");
    }

    public static FetchedData fetchObject(String id)
    {
        if (id == null)
        {
            return null;
        }
        return new FetchedData("|" + id + "|");
    }

    public static FetchedData[] fetchArrayNoArg()
    {
        return new FetchedData[] { new FetchedData("1") }; 
    }

    public static FetchedData[] fetchArrayGen(int numGenerate)
    {
        if (numGenerate < 0)
        {
            return null;
        }
        if (numGenerate == 0)
        {
            return new FetchedData[0];
        }
        if (numGenerate == 1)
        {
            return new FetchedData[] { new FetchedData("A") };
        }

        FetchedData[] fetched = new FetchedData[numGenerate];
        for (int i = 0; i < numGenerate; i++)
        {
            int c = 'A' + i;
            fetched[i] = new FetchedData(Character.toString((char)c));
        }
        return fetched;
    }

    public static long passthru(long value)
    {
        return value;
    }

    public static void sleep(long msec)
    {
        try
        {
            Thread.sleep(msec);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException("Interrupted during sleep", e);
        }
    }

    public static String delimitPipe(String string)
    {
        return "|" + string + "|";
    }

    public static class FetchedData
    {
        private String id;

        public FetchedData(String id)
        {
            this.id = id;
        }

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }
    }
}
