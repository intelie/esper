package com.espertech.esper.client.deploy;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

public interface DeploymentAdmin
{
    public Module read(InputStream stream, String moduleUri) throws IOException, ParseException;
    public Module read(String resource) throws IOException, ParseException;
    public Module read(File file) throws IOException, ParseException;
    public Module read(URL url) throws IOException, ParseException;
    public Module parse(String eplModuleText) throws IOException, ParseException;

    public DeploymentOrder getDeploymentOrder(Module[] modules, DeploymentOrderOptions options) throws DeploymentOrderException;

    public DeploymentResult deploy(Module module, DeploymentOptions options) throws DeploymentException;
    public UndeploymentResult undeploy(String deploymentId);

    public String[] getDeployments();
    public DeploymentInformation getDeployment(String deploymentId);
    public DeploymentInformation[] getDeploymentInformation();
}
