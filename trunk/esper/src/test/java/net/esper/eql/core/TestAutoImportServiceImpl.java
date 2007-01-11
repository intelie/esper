package net.esper.eql.core;

import junit.framework.TestCase;
import net.esper.eql.core.AutoImportServiceImpl;

public class TestAutoImportServiceImpl extends TestCase
{
    AutoImportServiceImpl autoImportService;

    public void setUp()
    {
        this.autoImportService = new AutoImportServiceImpl(new String[0]);
    }

    public void testResolveClass() throws Exception
    {
        String className = "java.lang.Math";
        Class expected = Math.class;
        assertEquals(expected, autoImportService.resolveClass(className));

        autoImportService.addImport("java.lang.Math");
        assertEquals(expected, autoImportService.resolveClass(className));

        autoImportService.addImport("java.lang.*");
        className = "String";
        expected = String.class;
        assertEquals(expected, autoImportService.resolveClass(className));
    }

    public void testResolveClassInvalid()
    {
        String className = "Math";
        try
        {
            autoImportService.resolveClass(className);
            fail();
        }
        catch (ClassNotFoundException e)
        {
            // Expected
        }
    }

    public void testAddImport()
    {
        autoImportService.addImport("java.lang.Math");
        assertEquals(1, autoImportService.getImports().length);
        assertEquals("java.lang.Math", autoImportService.getImports()[0]);

        autoImportService.addImport("java.lang.*");
        assertEquals(2, autoImportService.getImports().length);
        assertEquals("java.lang.Math", autoImportService.getImports()[0]);
        assertEquals("java.lang.*", autoImportService.getImports()[1]);
    }

    public void testAddImportInvalid()
    {
        try
        {
            autoImportService.addImport("java.lang.*.*");
            fail();
        }
        catch (IllegalArgumentException e)
        {
            // Expected
        }

        try
        {
            autoImportService.addImport("java.lang..Math");
            fail();
        }
        catch (IllegalArgumentException e)
        {
            // Expected
        }
    }
}
