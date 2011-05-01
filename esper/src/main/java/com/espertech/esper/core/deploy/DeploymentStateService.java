/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core.deploy;

import com.espertech.esper.client.deploy.*;

/**
 * Interface for a service maintaining deployment state.
 */
public interface DeploymentStateService
{
    /**
     * Allocates a new deployment id.
     * @return deployment id
     */
    public String nextDeploymentId();

    /**
     * Returns a list of deployment ids of deployments.
     * @return deployment ids
     */
    public String[] getDeployments();

    /**
     * Returns the deployment informaton for a given deployment id.
     * @param deploymentId id
     * @return deployment information
     */
    public DeploymentInformation getDeployment(String deploymentId);

    /**
     * Returns deployment information for all deployments.
     * @return array of deployment info
     */
    public DeploymentInformation[] getAllDeployments();

    /**
     * Add or update the deployment information using the contained deployment id as a key.
     * @param descriptor to store
     */
    public void addUpdateDeployment(DeploymentInformation descriptor);

    /**
     * Destroy the service.
     */
    public void destroy();

    /**
     *
     * @param deploymentId
     */
    public void remove(String deploymentId);
}
