package com.espertech.esper.example.trivia;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;

import java.io.InputStream;

public class TriviaExample {

    public EPServiceProvider setup() {

        Configuration config = new Configuration();
        config.getEngineDefaults().getExecution().setPrioritized(true);
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);

        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.initialize();

        // Resolve "trivia.epl" file.
        InputStream inputFile = this.getClass().getClassLoader().getResourceAsStream("trivia.epl");
        if (inputFile == null) {
            inputFile = this.getClass().getClassLoader().getResourceAsStream("etc/trivia.epl");
        }
        if (inputFile == null) {
            throw new RuntimeException("Failed to find file 'trivia.epl' in classpath or relative to classpath");
        }

        try {
            engine.getEPAdministrator().getDeploymentAdmin().readDeploy(inputFile, null, null, null);
        }
        catch (Exception e) {
            throw new RuntimeException("Error deploying EPL from 'trivia.epl': " + e.getMessage(), e);
        }

        return engine;
    }
}

