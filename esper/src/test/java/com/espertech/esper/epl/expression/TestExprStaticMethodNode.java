package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.*;
import com.espertech.esper.util.MethodResolver;
import junit.framework.TestCase;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        EngineImportService engineImportService = new EngineImportServiceImpl(true);
        engineImportService.addImport("java.lang.*");
        methodResolutionService = new MethodResolutionServiceImpl(engineImportService, null, true);
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
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", makeSpec("max", intThree, intFive), true);
        validate(root);

        Integer result = Math.max(3,5);
        assertEquals(result, root.getExprEvaluator().evaluate(null, false, null));
    }

    public void testIntegerInt() throws Exception
    {
        Method staticMethod = this.getClass().getMethod("staticIntMethod", Integer.class);
        ExprNode child = new ExprStaticMethodNode("Math", makeSpec("max", intThree, intFive), true);
        ExprStaticMethodNode parent = new ExprStaticMethodNode(this.getClass().getName(), makeSpec("staticIntMethod", child), true);
        validate(parent);

        int result = Math.max(3, 5);
        assertEquals(result, parent.getExprEvaluator().evaluate(null, false, null));
    }

    public void testMaxIntShort() throws Exception
    {
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", makeSpec("max",intThree, shortNine), true);
        validate(root);

        short nine = 9;
        Integer result = Math.max(3,nine);
        assertEquals(result, root.getExprEvaluator().evaluate(null, false, null));
    }

    public void testMaxDoubleInt() throws Exception
    {
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", makeSpec("max", doubleEight, intFive), true);
        validate(root);

        Double result = Math.max(8d,5);
        assertEquals(result, root.getExprEvaluator().evaluate(null, false, null));
    }

    public void testMaxDoubleDouble() throws Exception
    {
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", makeSpec("max", doubleEight, doubleFour), true);
        validate(root);

        Double result = Math.max(8d,4d);
        assertEquals(result, root.getExprEvaluator().evaluate(null, false, null));
    }

    public void testPowDoubleDouble() throws Exception
    {
        Method pow = Math.class.getMethod("pow", double.class, double.class);
        ExprStaticMethodNode root = new ExprStaticMethodNode("Math", makeSpec("pow", doubleEight, doubleFour), true);
        validate(root);

        Double result = Math.pow(8d,4d);
        assertEquals(result, root.getExprEvaluator().evaluate(null, false, null));
    }

    public void testValueOfInt() throws Exception
    {
        Method valueOf = Integer.class.getMethod("valueOf", String.class);
        ExprStaticMethodNode root = new ExprStaticMethodNode("Integer", makeSpec("valueOf", stringTen), true);
        validate(root);

        Integer result = Integer.valueOf("10");
        assertEquals(result, root.getExprEvaluator().evaluate(null, false, null));
    }

    private void validate(ExprNode node) throws Exception
    {
        node.getValidatedSubtree(streamTypeService, methodResolutionService, null, null, null, null);
    }

    private List<ExprChainedSpec> makeSpec(String method, ExprNode...expr)
    {
        List<ExprChainedSpec> chained = new ArrayList<ExprChainedSpec>();
        chained.add(new ExprChainedSpec(method, Arrays.asList(expr)));
        return chained;
    }

    public void nonstaticMethod(){}

    public static void staticMethod(){}

    public static int staticIntMethod(Integer param){return param;}
}
