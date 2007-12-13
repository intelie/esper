package net.esper.support.eql;

import net.esper.support.bean.SupportBean_S0;

public class SupportStaticMethodLib 
{
	public static Object staticMethod(Object object)
	{
		return object;
	}
	
	public static void throwException() throws Exception
	{
		throw new Exception("SupportStaticMethod.exceptionThrower throwing a fit");
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
