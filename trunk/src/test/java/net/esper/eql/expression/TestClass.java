package net.esper.eql.expression;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Aug 12, 2006
 * Time: 8:30:06 AM
 * To change this template use File | Settings | File Templates.
 */
class BaseClass
{

}

class SubClass extends BaseClass {}

public class TestClass
{
    private static Class[] aClasses = new Class[]{Integer.class, int.class};
    public static void main(String[] args)
    {
        Integer i = 1;
        for (int j=0; j<aClasses.length; j++)
        {
            if (i.getClass()  == aClasses[j])
            {
                System.out.println("Found it!");
            }
        }
        BaseClass myRef = new SubClass();
        if (myRef instanceof SubClass)
        {
            System.out.println("True!");            
        }
    }
}
