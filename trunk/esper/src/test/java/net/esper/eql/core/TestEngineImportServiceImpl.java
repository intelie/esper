package net.esper.eql.core;

import junit.framework.TestCase;
import net.esper.support.eql.SupportPluginAggregationMethodOne;

public class TestEngineImportServiceImpl extends TestCase
{
    EngineImportServiceImpl engineImportService;

    public void setUp()
    {
        this.engineImportService = new EngineImportServiceImpl();
    }

    public void testAddAggregation() throws EngineImportException
    {
        engineImportService.addAggregation("abc", "abcdef.G");
        engineImportService.addAggregation("abcDefGhk", "ab");
        engineImportService.addAggregation("a", "Yh");

        tryInvalidAddAggregation("g h", "");
        tryInvalidAddAggregation("gh", "j j");
        tryInvalidAddAggregation("abc", "hhh");
    }

    public void testResolveAggregationMethod() throws Exception
    {
        engineImportService.addAggregation("abc", SupportPluginAggregationMethodOne.class.getName());
        assertTrue(engineImportService.resolveAggregation("abc") instanceof SupportPluginAggregationMethodOne);
    }

    public void testInvalidResolveAggregation(String funcName) throws Exception
    {
        try
        {
            engineImportService.resolveAggregation("abc");
        }
        catch (EngineImportUndefinedException ex)
        {
            // expected
        }
        
        engineImportService.addAggregation("abc", "abcdef.G");
        try
        {
            engineImportService.resolveAggregation("abc");
        }
        catch (EngineImportException ex)
        {
            // expected
        }
    }

    public void testResolveClass() throws Exception
    {
        String className = "java.lang.Math";
        Class expected = Math.class;
        assertEquals(expected, engineImportService.resolveClass(className));

        engineImportService.addImport("java.lang.Math");
        assertEquals(expected, engineImportService.resolveClass(className));

        engineImportService.addImport("java.lang.*");
        className = "String";
        expected = String.class;
        assertEquals(expected, engineImportService.resolveClass(className));
    }

    public void testResolveClassInvalid()
    {
        String className = "Math";
        try
        {
            engineImportService.resolveClass(className);
            fail();
        }
        catch (ClassNotFoundException e)
        {
            // Expected
        }
    }

    public void testAddImport() throws EngineImportException
    {
        engineImportService.addImport("java.lang.Math");
        assertEquals(1, engineImportService.getImports().length);
        assertEquals("java.lang.Math", engineImportService.getImports()[0]);

        engineImportService.addImport("java.lang.*");
        assertEquals(2, engineImportService.getImports().length);
        assertEquals("java.lang.Math", engineImportService.getImports()[0]);
        assertEquals("java.lang.*", engineImportService.getImports()[1]);
    }

    public void testAddImportInvalid()
    {
        try
        {
            engineImportService.addImport("java.lang.*.*");
            fail();
        }
        catch (EngineImportException e)
        {
            // Expected
        }

        try
        {
            engineImportService.addImport("java.lang..Math");
            fail();
        }
        catch (EngineImportException e)
        {
            // Expected
        }
    }

    private void tryInvalidAddAggregation(String funcName, String className)
    {
        try
        {
            engineImportService.addAggregation(funcName, className);
        }
        catch (EngineImportException ex)
        {
            // expected
        }
    }
}
