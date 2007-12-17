package net.esper.client;

import junit.framework.TestCase;

public class TestConfigurationDBRef extends TestCase
{
    public void testTypeMapping()
    {
        tryInvalid("sometype", "Unsupported java type 'sometype' when expecting any of: [String, BigDecimal, Boolean, Byte, Short, Int, Long, Float, Double, ByteArray, SqlDate, SqlTime, SqlTimestamp]");

        ConfigurationDBRef config = new ConfigurationDBRef();
        config.addJavaSqlTypesBinding(1, "int");
    }

    private void tryInvalid(String type, String text)
    {
        try
        {
            ConfigurationDBRef config = new ConfigurationDBRef();
            config.addJavaSqlTypesBinding(1, type);
        }
        catch (ConfigurationException ex)
        {
            assertEquals(text, ex.getMessage());
        }
    }
}
