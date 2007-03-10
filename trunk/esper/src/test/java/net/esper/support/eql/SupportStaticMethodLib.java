package net.esper.support.eql;

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
}
