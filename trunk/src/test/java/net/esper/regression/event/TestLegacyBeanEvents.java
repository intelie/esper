package net.esper.regression.event;

import net.esper.client.*;
import net.esper.support.bean.SupportLegacyBean;
import net.esper.support.util.SupportUpdateListener;

import java.util.Map;
import java.util.HashMap;

import junit.framework.TestCase;

public class TestLegacyBeanEvents extends TestCase
{
    private SupportLegacyBean legacyBean;
    private EPServiceProvider epService;

    protected void setUp()
    {
        Map<String, String> mappedProperty = new HashMap<String, String>();
        mappedProperty.put("key1", "value1");
        mappedProperty.put("key2", "value2");
        legacyBean = new SupportLegacyBean("leg", new String[] {"a", "b"}, mappedProperty, "nest");
    }

    public void testAutoMethodResolution()
    {
        Configuration config = new Configuration();
        
        ConfigurationEventTypeLegacy legacyDef = new ConfigurationEventTypeLegacy();
        legacyDef.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.PUBLIC);
        legacyDef.setCodeGeneration(ConfigurationEventTypeLegacy.CodeGeneration.DISABLED);
        config.addEventTypeAlias("MyLegacyEvent", SupportLegacyBean.class.getName(), legacyDef);

        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        String clazzName = SupportLegacyBean.class.getName();
        String statementText = "select readLegacyBeanVal as simple," +
                    "readLegacyNested as nested," +
                    "readLegacyNested.readNestedValue as nested," +
                    "readStringArray[1] as array," +
                    "readStringIndexed[2] as indexed," +
                    "readMapByKey('a') as mapped," +
                    "readMap('b') as mapDirect" +
                    " from MyLegacyEvent.win:length(5)";

        EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(legacyBean);
        assertEquals("leg", listener.getLastNewData()[0].get("simple"));
    }

    public void testConfigured()
    {
        String statementText = "select legacyBeanVal as simple," +
                    "legacyNested as nested," +
                    "legacyNested.nestedValue as nested," +
                    "stringArray[1] as array," +
                    "stringIndex[2] as indexed," +
                    "mapByKey('a') as mapped," +
                    "mapPlain('b') as mapDirect" +
                    " from MyLegacyEvent.win:length(5)";

        EPStatement statement = epService.getEPAdministrator().createEQL(statementText);
        SupportUpdateListener listener = new SupportUpdateListener();
        statement.addListener(listener);

        epService.getEPRuntime().sendEvent(legacyBean);
        assertEquals("leg", listener.getLastNewData()[0].get("simple"));
    }
}
