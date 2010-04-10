package com.espertech.esper.client.deploy;

import java.util.Set;
import java.util.List;
import java.io.StringWriter;

/**
 * Represent a deployment unit consisting of deployment declarative information (module name, uses and imports)
 * as well as EPL statements represented by {@link ModuleItem}. May have an additional user object and archive name
 * and uri pointing to the module source attached.
 */
public class Module {
    private String name;
    private String uri;
    private Set<String> uses;
    private Set<String> imports;
    private List<ModuleItem> items;
    private String archiveName;
    private Object userObject;

    /**
     * Ctor.
     * @param name module name
     * @param uri module uri
     * @param uses names of modules that this module depends on
     * @param imports the Java class imports
     * @param items EPL statements
     */
    public Module(String name, String uri, Set<String> uses, Set<String> imports, List<ModuleItem> items) {
        this.name = name;
        this.uri = uri;
        this.uses = uses;
        this.imports = imports;
        this.items = items;
    }

    /**
     * Returns the name of the archive this module originated from, or null if not applicable.
     * @return archive name
     */
    public String getArchiveName() {
        return archiveName;
    }

    /**
     * Set the name of the archive this module originated from, or null if not applicable.
     * @param archiveName archive name
     */
    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }

    /**
     * Returns the optional user object that may be attached to the module.
     * @return user object
     */
    public Object getUserObject() {
        return userObject;
    }

    /**
     * Sets an optional user object that may be attached to the module.
     * @param userObject user object
     */
    public void setUserObject(Object userObject) {
        this.userObject = userObject;
    }

    /**
     * Returns the module name, if provided.
     * @return module name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the module name or null if none provided.
     * @param name module name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the module URI if provided.
     * @return module URI
     */
    public String getUri()
    {
        return uri;
    }

    /**
     * Sets the module URI or null if none provided.
     * @param uri of module
     */
    public void setUri(String uri)
    {
        this.uri = uri;
    }

    /**
     * Returns the dependencies the module may have on other modules.
     * @return module dependencies
     */
    public Set<String> getUses() {
        return uses;
    }

    /**
     * Sets the dependencies the module may have on other modules.
     * @param uses module dependencies
     */
    public void setUses(Set<String> uses) {
        this.uses = uses;
    }

    /**
     * Returns a list of statements (some may be comments only) that make up the module.
     * @return statements
     */
    public List<ModuleItem> getItems() {
        return items;
    }

    /**
     * Sets a list of statements (some may be comments only) that make up the module.
     * @param items statements
     */
    public void setItems(List<ModuleItem> items) {
        this.items = items;
    }

    /**
     * Returns the imports defined by the module.
     * @return module imports
     */
    public Set<String> getImports()
    {
        return imports;
    }

    /**
     * Sets the imports defined by the module.
     * @param imports module imports
     */
    public void setImports(Set<String> imports)
    {
        this.imports = imports;
    }

    public String toString() {
        StringWriter buf = new StringWriter();
        if (name == null) {
            buf.append("(unnamed)");
        }
        else {
            buf.append("'" + name + "'");
        }
        if (uri != null) {
            buf.append(" uri '" + uri + "'");
        }
        return buf.toString();
    }
}
