package net.esper.regression.event;

import junit.framework.TestCase;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import net.esper.support.bean.SupportBean_S0;
import net.esper.collection.Pair;
import net.esper.collection.ArrayBackedCollection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestPerf extends TestCase
{
    // TODO - remove class
    public void testPerf() throws Exception
    {
        SupportBean_S0 event = new SupportBean_S0(1);

        FastClass clazz = FastClass.create(event.getClass());
        FastMethod method = clazz.getMethod("getId",null);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
        {
            assertEquals(1, method.invoke(event, null));
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        System.out.println("delta=" + delta);
    }

    public void testPerf2() throws Exception
    {
        SupportBean_S0 event = new SupportBean_S0(1);

        Method method = event.getClass().getMethod("getId",null);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
        {
            assertEquals(1, method.invoke(event, null));
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        System.out.println("delta=" + delta);
    }

    public void testPerf3() throws Exception
    {
        SupportBean_S0 event = new SupportBean_S0(1);
        Method method = event.getClass().getMethod("getId",null);

        Method[] methods = new Method[1000];
        methods[1] = method;
        Class[] classes = new Class[1000];
        classes[1] = SupportBean_S0.class;

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++)
        {
            for (int j = 0; j < methods.length; j++)
            {
                if (classes[j] == event.getClass())
                {
                    assertEquals(1, methods[j].invoke(event, null));
                    break;
                }
            }
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        System.out.println("delta=" + delta);
    }

    public void testPerf4() throws Exception
    {
        SupportBean_S0 event = new SupportBean_S0(1);
        Method method = event.getClass().getMethod("getId",null);

        ArrayList<Pair<Class, Method>> pairs = new ArrayList<Pair<Class, Method>>();
        pairs.add(new Pair<Class, Method>(event.getClass(), event.getClass().getMethod("getId",null)));

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++)
        {
            for (Pair<Class, Method> pair : pairs)
            {
                if (pair.getFirst() == event.getClass())
                {
                    assertEquals(1, pair.getSecond().invoke(event, null));
                }
            }
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        System.out.println("delta=" + delta);
    }

    public void testPerf5() throws Exception
    {
        SupportBean_S0 event = new SupportBean_S0(1);
        Method method = event.getClass().getMethod("getId",null);

        CopyOnWriteArrayList<Pair<Class, Method>> pairs = new CopyOnWriteArrayList<Pair<Class, Method>>();
        //pairs.add(new Pair<Class, Method>(String.class, event.getClass().getMethod("getId",null)));
        //pairs.add(new Pair<Class, Method>(String.class, event.getClass().getMethod("getId",null)));
        //pairs.add(new Pair<Class, Method>(String.class, event.getClass().getMethod("getId",null)));
        pairs.add(new Pair<Class, Method>(event.getClass(), event.getClass().getMethod("getId",null)));

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++)
        {
            for (int j = 0; j < pairs.size(); j++)
            {
                Pair<Class, Method> pair = pairs.get(j); 
                if (pair.getFirst() == event.getClass())
                {
                    assertEquals(1, pair.getSecond().invoke(event, null));
                }
            }
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        System.out.println("delta=" + delta);
    }

    public void testPerf6() throws Exception
    {
        SupportBean_S0 event = new SupportBean_S0(1);

        Map<Class, Method> methods = new HashMap<Class, Method>();
        methods.put(event.getClass(), event.getClass().getMethod("getId",null));

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++)
        {
            Method method = methods.get(event.getClass());
            assertEquals(1, method.invoke(event, null));
        }
        long end = System.currentTimeMillis();
        long delta = end - start;
        System.out.println("delta=" + delta);
    }
}
