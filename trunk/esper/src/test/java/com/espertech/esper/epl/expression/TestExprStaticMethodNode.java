package com.espertech.esper.epl.expression;

import java.lang.reflect.Method;

import junit.framework.TestCase;
import com.espertech.esper.util.MethodResolver;
import com.espertech.esper.epl.core.*;

public class TestExprStaticMethodNode extends TestCase
{
    MethodResolver staticMethodResolver;
    StreamTypeService streamTypeService;
    MethodResolutionService methodResolutionService;
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
        EngineImportService engineImportService = new EngineImportServiceImpl();
        engineImportService.addImport("java.lang.*");
        methodResolutionService = new MethodResolutionServiceImpl(engineImportService, true);
        staticMethodResolver = new MethodResolver();
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
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "max", true);
        root.addChildNode(intThree);
        root.addChildNode(intFive);
        validate(root);

        assertEquals(maxInt, root.getStaticMethod());
        Integer result = Math.max(3,5);
        assertEquals(result, root.evaluate(null, false));
    }

    public void testIntegerInt() throws Exception
    {
        Method staticMethod = this.getClass().getMethod("staticIntMethod", Integer.class);
        ExprStaticMethodNode parent = new ExprStaticMethodNode(this.getClass().getName(), "staticIntMethod",  true);
        ExprNode child = new ExprStaticMethodNode("Math", "max", true);
        child.addChildNode(intThree);
        child.addChildNode(intFive);
        parent.addChildNode(child);
        validate(parent);

        assertEquals(staticMethod, parent.getStaticMethod());
        int result = Math.max(3, 5);
        assertEquals(result, parent.evaluate(null, false));
    }

    public void testMaxIntShort() throws Exception
    {
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "max",true);
        root.addChildNode(intThree);
        root.addChildNode(shortNine);
        validate(root);

        assertEquals(maxInt, root.getStaticMethod());
        short nine = 9;
        Integer result = Math.max(3,nine);
        assertEquals(result, root.evaluate(null, false));
    }

    public void testMaxDoubleInt() throws Exception
    {
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "max", true);
        root.addChildNode(doubleEight);
        root.addChildNode(intFive);
        validate(root);

        assertEquals(maxDouble, root.getStaticMethod());
        Double result = Math.max(8d,5);
        assertEquals(result, root.evaluate(null, false));
    }

    public void testMaxDoubleDouble() throws Exception
    {
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "max", true);
        root.addChildNode(doubleEight);
        root.addChildNode(doubleFour);
        validate(root);

        assertEquals(maxDouble, root.getStaticMethod());
        Double result = Math.max(8d,4d);
        assertEquals(result, root.evaluate(null, false));
    }

    public void testPowDoubleDouble() throws Exception
    {
        Method pow = Math.class.getMethod("pow", double.class, double.class);
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "pow", true);
        root.addChildNode(doubleEight);
        root.addChildNode(doubleFour);
        validate(root);

        assertEquals(pow, root.getStaticMethod());
        Double result = Math.pow(8d,4d);
        assertEquals(result, root.evaluate(null, false));
    }

    public void testValueOfInt() throws Exception
    {
        Method valueOf = Integer.class.getMethod("valueOf", String.class);
        ExprStaticMethodNode root = new ExprStaticMethodNode("Integer", "valueOf", true);
        root.addChildNode(stringTen);
        validate(root);

        assertEquals(valueOf, root.getStaticMethod());
        Integer result = Integer.valueOf("10");
        assertEquals(result, root.evaluate(null, false));
    }

    private void validate(ExprNode node) throws Exception
    {
        node.getValidatedSubtree(streamTypeService, methodResolutionService, null, null, null);
    }

    public void nonstaticMethod(){}

    public static void staticMethod(){}

    public static int staticIntMethod(Integer param){return param;}
}
