package com.espertech.esper.core;

import com.espertech.esper.client.Configuration;

public interface Configurator {
    public EPServiceProviderSPI configure(ConfiguratorContext context, Configuration configuration);
}
