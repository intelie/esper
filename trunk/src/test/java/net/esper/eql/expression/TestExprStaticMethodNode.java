package net.esper.eql.expression;

import java.lang.reflect.Method;

import junit.framework.TestCase;
import net.esper.util.StaticMethodResolver;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.AutoImportServiceImpl;
import net.esper.eql.core.StreamTypeService;

public class TestExprStaticMethodNode extends TestCase
{
    StaticMethodResolver staticMethodResolver;
    StreamTypeService streamTypeService;
    AutoImportService autoImportService;
    ExprNode intThree;
    ExprNode intFive;
    ExprNode shortNine;
    ExprNode doubleFour;
    ExprNode doubleEight;
    ExprNode stringTen;
    Method maxInt;
    Method maxDouble;

    protected void setUp() throws Exception
    {
        streamTypeService = null;
        autoImportService = new AutoImportServiceImpl(new String[] {"java.lang.*"});
        staticMethodResolver = new StaticMethodResolver();
        intThree = new ExprConstantNode(3);
        intFive = new ExprConstantNode(5);
        short nine = 9;
        shortNine = new ExprConstantNode(nine);
        doubleFour = new ExprConstantNode(4d);
        doubleEight = new ExprConstantNode(8d);
        stringTen = new ExprConstantNode("10");
        maxInt = Math.class.getMethod("max", int.class, int.class);
        maxDouble = Math.class.getMethod("max", double.class, double.class);
    }

    public void testMaxIntInt() throws Exception
    {
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "max");
        root.addChildNode(intThree);
        root.addChildNode(intFive);
        validate(root);

        assertEquals(maxInt, root.getStaticMethod());
        Integer result = Math.max(3,5);
        assertEquals(result, root.evaluate(null));
    }

    public void testIntegerInt() throws Exception
    {
        Method staticMethod = this.getClass().getMethod("staticIntMethod", Integer.class);
        ExprStaticMethodNode parent = new ExprStaticMethodNode(this.getClass().getName(), "staticIntMethod");
        ExprNode child = new ExprStaticMethodNode("Math", "max");
        child.addChildNode(intThree);
        child.addChildNode(intFive);
        parent.addChildNode(child);
        validate(parent);

        assertEquals(staticMethod, parent.getStaticMethod());
        int result = Math.max(3, 5);
        assertEquals(result, parent.evaluate(null));
    }

    public void testMaxIntShort() throws Exception
    {
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "max");
        root.addChildNode(intThree);
        root.addChildNode(shortNine);
        validate(root);

        assertEquals(maxInt, root.getStaticMethod());
        short nine = 9;
        Integer result = Math.max(3,nine);
        assertEquals(result, root.evaluate(null));
    }

    public void testMaxDoubleInt() throws Exception
    {
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "max");
        root.addChildNode(doubleEight);
        root.addChildNode(intFive);
        validate(root);

        assertEquals(maxDouble, root.getStaticMethod());
        Double result = Math.max(8d,5);
        assertEquals(result, root.evaluate(null));
    }

    public void testMaxDoubleDouble() throws Exception
    {
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "max");
        root.addChildNode(doubleEight);
        root.addChildNode(doubleFour);
        validate(root);

        assertEquals(maxDouble, root.getStaticMethod());
        Double result = Math.max(8d,4d);
        assertEquals(result, root.evaluate(null));
    }

    public void testPowDoubleDouble() throws Exception
    {
        Method pow = Math.class.getMethod("pow", double.class, double.class);
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "pow");
        root.addChildNode(doubleEight);
        root.addChildNode(doubleFour);
        validate(root);

        assertEquals(pow, root.getStaticMethod());
        Double result = Math.pow(8d,4d);
        assertEquals(result, root.evaluate(null));
    }

    public void testValueOfInt() throws Exception
    {
        Method valueOf = Integer.class.getMethod("valueOf", String.class);
        ExprStaticMethodNode root = new ExprStaticMethodNode("Integer", "valueOf");
        root.addChildNode(stringTen);
        validate(root);

        assertEquals(valueOf, root.getStaticMethod());
        Integer result = Integer.valueOf("10");
        assertEquals(result, root.evaluate(null));
    }

    private void validate(ExprNode node) throws Exception
    {
        node.getValidatedSubtree(streamTypeService, autoImportService);
    }

    public void nonstaticMethod(){}

    public static void staticMethod(){}

    public static int staticIntMethod(Integer param){return param;}
}