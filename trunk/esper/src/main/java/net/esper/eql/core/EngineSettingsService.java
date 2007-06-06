package net.esper.eql.core;

import net.esper.eql.agg.AggregationSupport;
import net.esper.client.ConfigurationEngineDefaults;

import java.lang.reflect.Method;

/**
 * Service for engine-level settings around threading and concurrency.
 */
public class EngineSettingsService
{
    private ConfigurationEngineDefaults config;

    public EngineSettingsService(ConfigurationEngineDefaults config)
    {
        this.config = config;
    }

    public ConfigurationEngineDefaults getEngineSettings()
    {
        return config;   
    }
}
