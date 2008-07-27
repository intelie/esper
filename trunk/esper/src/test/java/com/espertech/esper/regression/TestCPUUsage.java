package com.espertech.esper.regression;

import junit.framework.TestCase;

import java.lang.management.ThreadMXBean;
import java.lang.management.ManagementFactory;
import java.math.BigInteger;

// TODO - Document:
//   Aggregation uses long value (BigInt) and double (BigDec) for: avedev, stddev, median; for all others the result is BigInt/BigDec
//   
public class TestCPUUsage extends TestCase
{
    private final static boolean IS_A = false;

    public void setIS_A(boolean IS_A)
    {
        //this.IS_A = IS_A;
    }

    public void testLongCost()
    {
        long one = 20;
        long two = 4;
        double x = two / one;
        BigInteger bigint = BigInteger.valueOf(1233);

        long start = System.currentTimeMillis();
        Number l = new Long(10101002);
        //Number l = new Integer(10101002);
        Integer intVal = 10101002;
        for (int i = 0; i < 100000000; i++)
        {
            if (l.longValue() == intVal.longValue())
            {

            }
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        System.out.println(delta);
    }

    public void testBooleanCost()
    {
        calcIt(100);

        long start = System.currentTimeMillis();
        calcIt(20000);
        long end = System.currentTimeMillis();
        long delta = end - start;
        System.out.println(delta);
    }

    private long calcIt(int num)
    {
        long x = 0;
        for (int i = 0; i < num; i++)
        {
            x += calcIt2(i);
            if (IS_A)
            {
                return 0;
            }
        }
        return x;
    }

    private long calcIt2(int num)
    {
        long x = 0;
        for (int i = 0; i < 1000; i++)
        {
            x += calcIt3(num, i);
            if (IS_A)
            {
                return 0;
            }
        }
        return x;
    }

    private long calcIt3(int num, int num2)
    {
        long x = 0;
        for (int i = 0; i < 100; i++)
        {
            x += num + num2 + i;
            if (IS_A)
            {
                return 0;
            }
        }
        return x;
    }

    private void calc(int index)
    {
        int a = 5;
        for (int j = 0; j < index; j++)
        {
            if (IS_A)
            {
                a++;
            }
            //
        }        
    }

    public static void main(String[] args)
    {
        ThreadMXBean newBean = ManagementFactory.getThreadMXBean();
        if (!newBean.isCurrentThreadCpuTimeSupported())
        {
            System.out.println("CPU Usage monitoring is not avaliable!");
        }
        else
        {
            newBean.setThreadCpuTimeEnabled(true);
        }

        // The order of these two calls matters
        long start = System.nanoTime();
        long base_cpu = newBean.getCurrentThreadUserTime();

        for (int i = 0; i < 100; i++)
        {
            for (int j = 0; j < 1000000; j++)
            {
                Math.sin(j);
            }

            // The order of these two calls matters
            long cpu = newBean.getCurrentThreadUserTime();

            long time = System.nanoTime();

            System.out.println(i + " load: " +
                    (cpu - base_cpu) * 100.0 / (time - start));
        }
    }
}
