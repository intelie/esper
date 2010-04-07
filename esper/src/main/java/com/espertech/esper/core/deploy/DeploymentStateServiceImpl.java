/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core.deploy;

import com.espertech.esper.client.deploy.DeploymentInformation;
import com.espertech.esper.util.UuidGenerator;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DeploymentStateServiceImpl implements DeploymentStateService
{
    private final Map<String, DeploymentInformation> deployments;

    public DeploymentStateServiceImpl()
    {
        deployments = new ConcurrentHashMap<String, DeploymentInformation>();
    }

    public String nextDeploymentId()
    {
        return UuidGenerator.generate();
    }

    public void addDeployment(DeploymentInformation descriptor)
    {
        deployments.put(descriptor.getDeploymentId(), descriptor);
    }

    public void remove(String deploymentId)
    {
        deployments.remove(deploymentId);
    }

    public String[] getDeployments()
    {
        Set<String> keys = deployments.keySet();
        return keys.toArray(new String[keys.size()]);
    }

    public DeploymentInformation getDeployment(String deploymentId)
    {
        return deployments.get(deploymentId);
    }

    public void destroy()
    {
        deployments.clear();
    }
}
