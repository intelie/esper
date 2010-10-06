package com.espertech.esper.epl.expression;

import junit.framework.TestCase;
import com.espertech.esper.support.epl.SupportExprNode;
import com.espertech.esper.support.epl.SupportExprNodeFactory;
import com.espertech.esper.support.epl.SupportStreamTypeSvc1Stream;
import com.espertech.esper.epl.core.*;

public class TestExprNode extends TestCase
{
    public void testGetValidatedSubtree() throws Exception
    {
        SupportExprNode.setValidateCount(0);

        // Confirms all child nodes validated
        // Confirms depth-first validation
        SupportExprNode topNode = new SupportExprNode(Boolean.class);

        SupportExprNode parent_1 = new SupportExprNode(Boolean.class);
        SupportExprNode parent_2 = new SupportExprNode(Boolean.class);

        topNode.addChildNode(parent_1);
        topNode.addChildNode(parent_2);

        SupportExprNode supportNode1_1 = new SupportExprNode(Boolean.class);
        SupportExprNode supportNode1_2 = new SupportExprNode(Boolean.class);
        SupportExprNode supportNode2_1 = new SupportExprNode(Boolean.class);
        SupportExprNode supportNode2_2 = new SupportExprNode(Boolean.class);

        parent_1.addChildNode(supportNode1_1);
        parent_1.addChildNode(supportNode1_2);
        parent_2.addChildNode(supportNode2_1);
        parent_2.addChildNode(supportNode2_2);

        topNode.getValidatedSubtree(null, null, null, null, null, null);

        assertEquals(1, supportNode1_1.getValidateCountSnapshot());
        assertEquals(2, supportNode1_2.getValidateCountSnapshot());
        assertEquals(3, parent_1.getValidateCountSnapshot());
        assertEquals(4, supportNode2_1.getValidateCountSnapshot());
        assertEquals(5, supportNode2_2.getValidateCountSnapshot());
        assertEquals(6, parent_2.getValidateCountSnapshot());
        assertEquals(7, topNode.getValidateCountSnapshot());
    }

    public void testIdentToStaticMethod() throws ExprValidationException, EngineImportException
    {
        StreamTypeService typeService = new SupportStreamTypeSvc1Stream();
        EngineImportService engineImportService = new EngineImportServiceImpl(true);
        engineImportService.addImport("java.lang.*");
        MethodResolutionService methodResolutionService = new MethodResolutionServiceImpl(engineImportService, null, true);

        ExprNode identNode = new ExprIdentNode("Integer.valueOf(\"3\")");
        ExprNode result = identNode.getValidatedSubtree(typeService, methodResolutionService, null, null, null, null);
        assertTrue(result instanceof ExprStaticMethodNode);
        assertEquals(Integer.valueOf("3"), result.getExprEvaluator().evaluate(null, false, null));

        identNode = new ExprIdentNode("Integer.valueOf(\'3\')");
        result = identNode.getValidatedSubtree(typeService, methodResolutionService, null, null, null, null);
        assertTrue(result instanceof ExprStaticMethodNode);
        assertEquals(Integer.valueOf("3"), result.getExprEvaluator().evaluate(null, false, null));

        identNode = new ExprIdentNode("UknownClass.nonexistentMethod(\"3\")");
        try
        {
            result = identNode.getValidatedSubtree(typeService, methodResolutionService, null, null, null, null);
            fail();
        }
        catch(ExprValidationException e)
        {
            // Expected
        }

        identNode = new ExprIdentNode("unknownMap(\"key\")");
        try
        {
            result = identNode.getValidatedSubtree(typeService, methodResolutionService, null, null, null, null);
            fail();
        }
        catch(ExprValidationException e)
        {
            // Expected
        }
    }

    public void testDeepEquals() throws Exception
    {
        assertFalse(ExprNodeUtility.deepEquals(SupportExprNodeFactory.make2SubNodeAnd(), SupportExprNodeFactory.make3SubNodeAnd()));
        assertFalse(ExprNodeUtility.deepEquals(SupportExprNodeFactory.makeEqualsNode(), SupportExprNodeFactory.makeMathNode()));
        assertTrue(ExprNodeUtility.deepEquals(SupportExprNodeFactory.makeMathNode(), SupportExprNodeFactory.makeMathNode()));
        assertFalse(ExprNodeUtility.deepEquals(SupportExprNodeFactory.makeMathNode(), SupportExprNodeFactory.make2SubNodeAnd()));
        assertTrue(ExprNodeUtility.deepEquals(SupportExprNodeFactory.make3SubNodeAnd(), SupportExprNodeFactory.make3SubNodeAnd()));
    }

    public void testParseMappedProp()
    {
        ExprNode.MappedPropertyParseResult result = ExprNode.parseMappedProperty("a.b('c')");
        assertEquals("a", result.getClassName());
        assertEquals("b", result.getMethodName());
        assertEquals("c", result.getArgString());

        result = ExprNode.parseMappedProperty("SupportStaticMethodLib.delimitPipe('POLYGON ((100.0 100, \", 100 100, 400 400))')");
        assertEquals("SupportStaticMethodLib", result.getClassName());
        assertEquals("delimitPipe", result.getMethodName());
        assertEquals("POLYGON ((100.0 100, \", 100 100, 400 400))", result.getArgString());

        result = ExprNode.parseMappedProperty("a.b.c.d.e('f.g.h,u.h')");
        assertEquals("a.b.c.d", result.getClassName());
        assertEquals("e", result.getMethodName());
        assertEquals("f.g.h,u.h", result.getArgString());

        result = ExprNode.parseMappedProperty("a.b.c.d.E(\"hfhf f f f \")");
        assertEquals("a.b.c.d", result.getClassName());
        assertEquals("E", result.getMethodName());
        assertEquals("hfhf f f f ", result.getArgString());

        result = ExprNode.parseMappedProperty("c.d.doit(\"kf\"kf'kf\")");
        assertEquals("c.d", result.getClassName());
        assertEquals("doit", result.getMethodName());
        assertEquals("kf\"kf'kf", result.getArgString());

        result = ExprNode.parseMappedProperty("c.d.doit('kf\"kf'kf\"')");
        assertEquals("c.d", result.getClassName());
        assertEquals("doit", result.getMethodName());
        assertEquals("kf\"kf'kf\"", result.getArgString());

        result = ExprNode.parseMappedProperty("f('a')");
        assertEquals(null, result.getClassName());
        assertEquals("f", result.getMethodName());
        assertEquals("a", result.getArgString());

        assertNull(ExprNode.parseMappedProperty("('a')"));
        assertNull(ExprNode.parseMappedProperty(""));
    }
}
