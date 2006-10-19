package net.esper.client;

import junit.framework.TestCase;

public class TestEPServiceProviderManager extends TestCase
{
    public void testGetInstance()
    {
        Configuration configuration = new Configuration();

        EPServiceProvider runtimeDef1 = EPServiceProviderManager.getDefaultProvider();
        EPServiceProvider runtimeA1 = EPServiceProviderManager.getProvider("A");
        EPServiceProvider runtimeB = EPServiceProviderManager.getProvider("B");
        EPServiceProvider runtimeA2 = EPServiceProviderManager.getProvider("A");
        EPServiceProvider runtimeDef2 = EPServiceProviderManager.getDefaultProvider(configuration);
        EPServiceProvider runtimeA3 = EPServiceProviderManager.getProvider("A", configuration);

        assertNotNull(runtimeDef1);
        assertNotNull(runtimeA1);
        assertNotNull(runtimeB);
        assertTrue(runtimeDef1 == runtimeDef2);
        assertTrue(runtimeA1 == runtimeA2);
        assertTrue(runtimeA1 == runtimeA3);
        assertFalse(runtimeA1 == runtimeDef1);
        assertFalse(runtimeA1 == runtimeB);
    }

    public void testInvalid()
    {
        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("x", "xxx.noclass");

        try
        {
            EPServiceProviderManager.getProvider("someURI", configuration);
            fail();
        }
        catch (ConfigurationException ex)
        {
            // Expected
        }
    }
}