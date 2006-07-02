package net.esper.client;

import junit.framework.TestCase;

import java.net.URL;
import java.io.File;

public class TestConfiguration extends TestCase
{
    private final static String SAMPLE_EVENT_NAME = "MySampleEvent";
    private final static String SAMPLE_EVENT_CLASS = "com.mycompany.myapp.MySampleEvent";

    private Configuration config;

    public void setUp()
    {
        config = new Configuration();
    }

    public void testDefault() throws Exception
    {
        config.configure();
        assertConfig(config);
    }

    public void testString() throws Exception
    {
        config.configure(Configuration.ESPER_DEFAULT_CONFIG);
        assertConfig(config);
    }

    public void testURL() throws Exception
    {
        URL url = this.getClass().getClassLoader().getResource(Configuration.ESPER_DEFAULT_CONFIG);
        config.configure(url);
        assertConfig(config);
    }

    public void testFile() throws Exception
    {
        URL url = this.getClass().getClassLoader().getResource(Configuration.ESPER_DEFAULT_CONFIG);
        File file = new File(url.toURI());
        config.configure(file);
        assertConfig(config);
    }

    public void testAddEventMapping()
    {
        config.addEventTypeAlias(SAMPLE_EVENT_NAME, SAMPLE_EVENT_CLASS);
        assertConfig(config);
    }

    private void assertConfig(Configuration config)
    {
        assertEquals(1, config.getEventTypeAliases().size());
        assertEquals(SAMPLE_EVENT_CLASS, config.getEventTypeAliases().get(SAMPLE_EVENT_NAME));
    }
}
