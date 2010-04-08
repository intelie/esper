/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core.deploy;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.deploy.*;
import com.espertech.esper.core.EPAdministratorSPI;
import com.espertech.esper.util.DependencyGraph;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.URL;
import java.util.*;

public class DeploymentAdminImpl implements DeploymentAdmin
{
    public static String newline = System.getProperty("line.separator");
    private static Log log = LogFactory.getLog(DeploymentAdminImpl.class);

    private final EPAdministratorSPI epService;
    private final DeploymentStateService deploymentStateService; 

    public DeploymentAdminImpl(EPAdministratorSPI epService, DeploymentStateService deploymentStateService)
    {
        this.epService = epService;
        this.deploymentStateService = deploymentStateService;
    }

    public Module read(InputStream stream, String uri) throws IOException, ParseException
    {
        if (log.isDebugEnabled()) {
            log.debug("Reading module from input stream");
        }
        return readInternal(stream, uri);
    }

    public Module read(File file) throws IOException, ParseException {
        if (log.isDebugEnabled()) {
            log.debug("Reading resource '" + file.getAbsolutePath() + "'");
        }
        return readInternal(new FileInputStream(file), file.getAbsolutePath());
    }

    public Module read(URL url) throws IOException, ParseException {
        if (log.isDebugEnabled())
        {
            log.debug( "Reading resource from url: " + url.toString() );
        }
        return readInternal(url.openStream(), url.toString());
    }

    public Module read(String resource) throws IOException, ParseException {
        if (log.isDebugEnabled()) {
            log.debug("Reading resource '" + resource + "'");
        }
        String stripped = resource.startsWith("/") ? resource.substring(1) : resource;

        InputStream stream = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader!=null) {
            stream = classLoader.getResourceAsStream( stripped );
        }
        if ( stream == null ) {
            stream = DeploymentAdminImpl.class.getResourceAsStream( resource );
        }
        if ( stream == null ) {
            stream = DeploymentAdminImpl.class.getClassLoader().getResourceAsStream( stripped );
        }
        if ( stream == null ) {
           throw new IOException("Failed to find resource '" + resource + "' in classpath");
        }

        return readInternal(stream, resource);
    }

    private Module readInternal(InputStream stream, String resourceName) throws IOException, ParseException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringWriter buffer = new StringWriter();
        String strLine;
        while ((strLine = br.readLine()) != null)   {
            buffer.append(strLine);
            buffer.append(newline);
        }
        stream.close();

        return readInternal(buffer.toString(), resourceName);
    }

    private Module readInternal(String buffer, String resourceName) throws IOException, ParseException {

        List<String> semicolonSegments = EPLModuleUtil.parse(buffer.toString());
        List<ParseNode> nodes = new ArrayList<ParseNode>();
        for (String segment : semicolonSegments) {
            nodes.add(EPLModuleUtil.getModule(segment, resourceName));
        }

        String moduleName = null;
        int count = 0;
        for (ParseNode node : nodes) {
            if (node instanceof ParseNodeComment) {
                continue;
            }
            if (node instanceof ParseNodeModule) {
                if (moduleName != null) {
                    throw new ParseException("Duplicate use of the 'module' keyword for resource '" + resourceName + "'");
                }
                if (count > 0) {
                    throw new ParseException("The 'module' keyword must be the first declaration in the module file for resource '" + resourceName + "'");
                }
                moduleName = ((ParseNodeModule) node).getModuleName();
            }
            count++;
        }

        Set<String> uses = new LinkedHashSet<String>();
        Set<String> imports = new LinkedHashSet<String>();
        count = 0;
        for (ParseNode node : nodes) {
            if ((node instanceof ParseNodeComment) || (node instanceof ParseNodeModule)) {
                continue;
            }
            String message = "The 'uses' and 'import' keywords must be the first declaration in the module file or follow the 'module' declaration";
            if (node instanceof ParseNodeUses) {
                if (count > 0) {
                    throw new ParseException(message);
                }
                uses.add(((ParseNodeUses) node).getUses());
                continue;
            }
            if (node instanceof ParseNodeImport) {
                if (count > 0) {
                    throw new ParseException(message);
                }
                imports.add(((ParseNodeImport) node).getImported());
                continue;
            }
            count++;
        }

        List<ModuleItem> items = new ArrayList<ModuleItem>();
        for (ParseNode node : nodes) {
            if ((node instanceof ParseNodeComment) || (node instanceof ParseNodeExpression)) {
                boolean isComments = (node instanceof ParseNodeComment);
                items.add(new ModuleItem(node.getText(), isComments));
            }
        }

        return new Module(moduleName, resourceName, uses, imports, items);
    }

    public synchronized DeploymentResult deploy(Module module, DeploymentOptions options) throws DeploymentException {

        if (options == null) {
            options = new DeploymentOptions();
        }

        if (log.isDebugEnabled()) {
            log.debug("Deploying module " + module);
        }
        if (module.getImports() != null) {
            for (String imported : module.getImports()) {
                if (log.isDebugEnabled()) {
                    log.debug("Adding import " + imported);
                }
                epService.getConfiguration().addImport(imported);
            }
        }

        String deploymentId = deploymentStateService.nextDeploymentId();

        if (options.isCompile()) {
            List<DeploymentItemException> exceptions = new ArrayList<DeploymentItemException>();
            for (ModuleItem item : module.getItems()) {
                try {
                    epService.compileEPL(item.getExpression());
                }
                catch (EPException ex) {
                    exceptions.add(new DeploymentItemException(ex.getMessage(), item.getExpression(), ex));
                }
            }

            if (!exceptions.isEmpty()) {
                throw buildException("Compilation failed", module, exceptions);
            }
        }

        if (options.isCompileOnly()) {
            return null;
        }

        List<DeploymentItemException> exceptions = new ArrayList<DeploymentItemException>();
        List<String> statementNames = new ArrayList<String>();
        List<EPStatement> statements = new ArrayList<EPStatement>();
        for (ModuleItem item : module.getItems()) {
            try {
                EPStatement stmt = epService.createEPL(item.getExpression());
                statementNames.add(stmt.getName());
                statements.add(stmt);
            }
            catch (EPException ex) {
                exceptions.add(new DeploymentItemException(ex.getMessage(), item.getExpression(), ex));
                if (options.isFailFast()) {
                    break;                        
                }
            }
        }

        if (!exceptions.isEmpty()) {
            if (options.isRollbackOnFail()) {
                log.debug("Rolling back intermediate statements for deployment");
                for (EPStatement stmt : statements) {
                    try {
                        stmt.destroy();
                    }
                    catch (Exception ex) {
                        log.debug("Failed to destroy created statement during rollback: " + ex.getMessage(), ex);
                    }
                }
            }
            throw buildException("Deployment failed", module, exceptions);
        }

        String[] statementNamesArr = statementNames.toArray(new String[statementNames.size()]);
        Set<String> moduleUses = (module.getUses() == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(module.getUses()));
        DeploymentInformation desc = new DeploymentInformation(deploymentId, module.getName(), module.getUrl(), moduleUses, Calendar.getInstance(), statementNamesArr);
        deploymentStateService.addDeployment(desc);

        if (log.isDebugEnabled()) {
            log.debug("Module " + module + " was successfully deployed.");
        }
        return new DeploymentResult(desc.getDeploymentId(), Collections.unmodifiableList(statements));
    }

    private DeploymentException buildException(String msg, Module module, List<DeploymentItemException> exceptions)
    {
        String message = msg;
        if (module.getName() != null) {
            message += " in module '" + module.getName() + "'";
        }
        if (module.getUrl() != null) {
            message += " in module url '" + module.getUrl() + "'";
        }
        if (exceptions.size() == 1) {
            message += " : " + exceptions.get(0).getMessage();
        }
        return new DeploymentException(message, exceptions);
    }

    public Module parse(String eplModuleText) throws IOException, ParseException
    {
        return readInternal(eplModuleText, null);
    }

    public UndeploymentResult undeploy(String deploymentId)
    {
        DeploymentInformation info = deploymentStateService.getDeployment(deploymentId);
        if (info == null) {
            return null;
        }

        String[] reverted = new String[info.getStatementNames().length];
        for (int i = 0; i < info.getStatementNames().length; i++) {
            reverted[i] = info.getStatementNames()[info.getStatementNames().length - 1 - i];
        }

        Set<String> statementNames = new LinkedHashSet<String>();
        for (String statementName : reverted) {
            EPStatement statement = epService.getStatement(statementName);
            if (statement == null) {
                log.debug("Deployment id '" + deploymentId + "' statement name '" + statementName + "' not found");
                continue;
            }
            if (statement.isDestroyed()) {
                continue;
            }
            try {
                statement.destroy();
            }
            catch (RuntimeException ex) {
                log.warn("Unexpected exception destroying statement: " + ex.getMessage(), ex);
            }
            statementNames.add(statementName);
        }

        deploymentStateService.remove(deploymentId);
        return new UndeploymentResult(deploymentId, statementNames);
    }

    public synchronized String[] getDeployments()
    {
        return deploymentStateService.getDeployments();
    }

    public DeploymentInformation getDeployment(String deploymentId)
    {
        return deploymentStateService.getDeployment(deploymentId);
    }

    public DeploymentInformation[] getDeploymentInformation()
    {
        return deploymentStateService.getAllDeployments();
    }

    public DeploymentOrder getDeploymentOrder(Module[] modules, DeploymentOrderOptions options) throws DeploymentOrderException
    {
        if (options == null) {
            options = new DeploymentOrderOptions();
        }
        String[] deployments = deploymentStateService.getDeployments();

        List<Module> proposedModules = new ArrayList<Module>();
        proposedModules.addAll(Arrays.asList(modules));

        Set<String> availableModuleNames = new HashSet<String>();
        for (Module proposedModule : proposedModules) {
            if (proposedModule.getName() != null) {
                availableModuleNames.add(proposedModule.getName());
            }
        }

        // Collect all uses-dependencies of existing modules
        Map<String, Set<String>> usesPerModuleName = new HashMap<String, Set<String>>();
        for (String deployment : deployments) {
            DeploymentInformation info = deploymentStateService.getDeployment(deployment);
            if (info == null) {
                continue;
            }
            if ((info.getModuleName() == null) || (info.getModuleUses() == null)) {
                continue;
            }
            Set<String> usesSet = usesPerModuleName.get(info.getModuleName());
            if (usesSet == null) {
                usesSet = new HashSet<String>();
                usesPerModuleName.put(info.getModuleName(), usesSet);
            }
            usesSet.addAll(info.getModuleUses());
        }

        // Collect uses-dependencies of proposed modules
        for (Module proposedModule : proposedModules) {

            // check uses-dependency is available
            if (options.isCheckUses()) {
                if (proposedModule.getUses() != null) {
                    for (String uses : proposedModule.getUses()) {
                        if (availableModuleNames.contains(uses)) {
                            continue;
                        }
                        String message = "Module-dependency not found";
                        if (proposedModule.getName() != null) {
                            message += " as declared by module '" + proposedModule.getName() + "'";
                        }
                        message += " for uses-declaration '" + uses + "'";
                        throw new DeploymentOrderException(message);
                    }
                }
            }
            
            if ((proposedModule.getName() == null) || (proposedModule.getUses() == null)) {
                continue;
            }
            Set<String> usesSet = usesPerModuleName.get(proposedModule.getName());
            if (usesSet == null) {
                usesSet = new HashSet<String>();
                usesPerModuleName.put(proposedModule.getName(), usesSet);
            }
            usesSet.addAll(proposedModule.getUses());
        }

        Map<String, SortedSet<Integer>> proposedModuleNames = new HashMap<String, SortedSet<Integer>>();
        Map<Integer, Module> proposedModuleNumbers = new HashMap<Integer, Module>();
        int count = 0;
        for (Module proposedModule : proposedModules) {
            SortedSet<Integer> moduleNumbers = proposedModuleNames.get(proposedModule.getName());
            if (moduleNumbers == null) {
                moduleNumbers = new TreeSet<Integer>();
                proposedModuleNames.put(proposedModule.getName(), moduleNumbers);
            }
            proposedModuleNumbers.put(count, proposedModule);
            moduleNumbers.add(count);
            count++;
        }

        DependencyGraph graph = new DependencyGraph(proposedModules.size());
        int fromModule = 0;
        for (Module proposedModule : proposedModules) {
            if ((proposedModule.getUses() == null) || (proposedModule.getUses().isEmpty())) {
                fromModule++;
                continue;
            }
            SortedSet<Integer> dependentModuleNumbers = new TreeSet<Integer>();
            for (String use : proposedModule.getUses()) {
                SortedSet<Integer> moduleNumbers = proposedModuleNames.get(use);
                if (moduleNumbers == null) {
                    continue;
                }
                dependentModuleNumbers.addAll(moduleNumbers);
            }
            dependentModuleNumbers.remove(fromModule);
            graph.addDependency(fromModule, dependentModuleNumbers);
            fromModule++;
        }

        if (options.isCheckCircularDependency()) {
            Stack<Integer> circular = graph.getFirstCircularDependency();
            if (circular != null) {
                String message = "";
                String delimiter = "";
                for (int i : circular) {
                    message += delimiter;
                    message += "module '" + proposedModules.get(i).getName() + "'";
                    delimiter = " uses (depends on) ";
                }
                throw new DeploymentOrderException("Circular dependency detected in module uses-relationships: " + message);
            }
        }

        List<Module> reverseDeployList = new ArrayList<Module>();
        Set<Integer> ignoreList = new HashSet<Integer>();
        while(ignoreList.size() < proposedModules.size()) {

            // seconardy sort according to the order of listing
            Set<Integer> rootNodes = new TreeSet<Integer>(new Comparator<Integer>() {
                public int compare(Integer o1, Integer o2)
                {
                    return -1 * o1.compareTo(o2);
                }
            });
            rootNodes.addAll(graph.getRootNodes(ignoreList));

            if (rootNodes.isEmpty()) {   // circular dependency could cause this
                for (int i = 0; i < proposedModules.size(); i++) {
                    if (!ignoreList.contains(i)) {
                        rootNodes.add(i);
                        break;
                    }
                }
            }

            for (Integer root : rootNodes) {
                ignoreList.add(root);
                reverseDeployList.add(proposedModules.get(root));
            }
        }
        
        Collections.reverse(reverseDeployList);
        return new DeploymentOrder(reverseDeployList);
    }
}
