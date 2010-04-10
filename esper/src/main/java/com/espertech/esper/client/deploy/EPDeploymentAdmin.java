package com.espertech.esper.client.deploy;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

/**
 * Service to package and deploy EPL statements organized into an EPL module.
 */
public interface EPDeploymentAdmin
{
    /**
     * Read the input stream and return the module. It is up to the calling method to close the stream when done.
     * @param stream to read
     * @param moduleUri uri of the module
     * @return module
     * @throws IOException when the io operation failed
     * @throws ParseException when parsing of the module failed
     */
    public Module read(InputStream stream, String moduleUri) throws IOException, ParseException;

    /**
     * Read the resource by opening from classpath and return the module.
     * @param resource name of the classpath resource
     * @return module
     * @throws IOException when the resource could not be read
     * @throws ParseException when parsing of the module failed
     */
    public Module read(String resource) throws IOException, ParseException;

    /**
     * Read the module by reading the text file and return the module.
     * @param file the file to read
     * @return module
     * @throws IOException when the file could not be read
     * @throws ParseException when parsing of the module failed
     */
    public Module read(File file) throws IOException, ParseException;

    /**
     * Read the module by reading from the URL provided and return the module.
     * @param url the URL to read
     * @return module
     * @throws IOException when the url input stream could not be read
     * @throws ParseException when parsing of the module failed
     */
    public Module read(URL url) throws IOException, ParseException;

    /**
     * Parse the module text passed in, returning the module.
     * @param eplModuleText to parse
     * @return module
     * @throws IOException when the parser failed to read the string buffer
     * @throws ParseException when parsing of the module failed
     */
    public Module parse(String eplModuleText) throws IOException, ParseException;

    /**
     * Compute a deployment order among the modules passed in considering their uses-dependency declarations
     * and considering the already-deployed modules.
     * <p>
     * The operation also checks and reports circular dependencies.
     * <p>
     * Pass in @{link DeploymentOrderOptions} to customize the behavior if this method. When passing no options
     * or passing default options, the default behavior checks uses-dependencies and circular dependencies.
     * @param modules to determine ordering for
     * @param options operation options or null for default options
     * @return ordered modules
     * @throws DeploymentOrderException when any module dependencies are not satisfied
     */
    public DeploymentOrder getDeploymentOrder(Collection<Module> modules, DeploymentOrderOptions options) throws DeploymentOrderException;

    /**
     * Deploy a single module returning a deployment id to use when undeploying statements as well as
     * additional statement-level information.
     * <p>
     * Pass in @{link DeploymentOptions} to customize the behavior. When passing no options or passing default options,
     * the operation first compiles all EPL statements before starting each statement, fails-fast on the first statement that fails to start
     * and rolls back (destroys) any started statement on a failure. 
     * @param module to deploy
     * @param options operation options or null for default options
     * @return deployment id in a result object with statement detail
     * @throws DeploymentException when the deployment fails, contains a list of deployment failures
     */
    public DeploymentResult deploy(Module module, DeploymentOptions options) throws DeploymentException;

    /**
     * Undeploy a single module, this operation destroys all statements previously associated to a deployed module.
     * @param deploymentId of the deployment to undeploy.
     * @return result object with statement-level detail
     */
    public UndeploymentResult undeploy(String deploymentId);

    /**
     * Return deployment ids of all currently deployed modules.
     * @return array of deployment ids
     */
    public String[] getDeployments();

    /**
     * Returns the deployment information for a given deployment.
     * @param deploymentId to return the deployment information for.
     * @return deployment info
     */
    public DeploymentInformation getDeployment(String deploymentId);

    /**
     * Returns deployment information for all deployed modules.
     * @return deployment information.
     */
    public DeploymentInformation[] getDeploymentInformation();
}
