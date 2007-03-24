package net.esper.eql.core;

import junit.framework.TestCase;

public class TestEngineImportServiceImpl extends TestCase
{
    EngineImportServiceImpl engineImportService;

    public void setUp()
    {
        this.engineImportService = new EngineImportServiceImpl(new String[0], null);
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

    public void testAddImport()
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
        catch (IllegalArgumentException e)
        {
            // Expected
        }

        try
        {
            engineImportService.addImport("java.lang..Math");
            fail();
        }
        catch (IllegalArgumentException e)
        {
            // Expected
        }
    }
}
