package net.esper.util;

import junit.framework.TestCase;

import java.net.URL;

import net.esper.client.EPException;

public class TestResourceLoader extends TestCase
{
    private final static String TEST_RESOURCE = "regression/esper.test.readconfig.cfg.xml";

    public void testResolveResourceAsURL()
    {
        URL url = ResourceLoader.getClasspathResourceAsURL("somefile", TEST_RESOURCE);
        assertNotNull(url);

        try
        {
            ResourceLoader.getClasspathResourceAsURL("somefile", "xxx");
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }
    }

    public void testClasspathOrURL()
    {
        URL url = this.getClass().getClassLoader().getResource(TEST_RESOURCE);
        URL urlAfterResolve = ResourceLoader.resolveClassPathOrURLResource("a", url.toString());
        assertEquals(url, urlAfterResolve);

        URL url3 = ResourceLoader.resolveClassPathOrURLResource("a", "file:///xxx/a.b");
        assertEquals("file:/xxx/a.b", url3.toString());

        try
        {
            ResourceLoader.resolveClassPathOrURLResource("a", "b");
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }
    }
}
