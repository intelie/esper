package com.espertech.esper.client.deploy;

import java.util.Set;
import java.util.List;
import java.io.StringWriter;

public class Module {
    private String name;
    private String url;
    private Set<String> uses;
    private Set<String> imports;
    private List<ModuleItem> items;

    public Module(String name, String url, Set<String> uses, Set<String> imports, List<ModuleItem> items) {
        this.name = name;
        this.url = url;
        this.uses = uses;
        this.imports = imports;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public Set<String> getUses() {
        return uses;
    }

    public void setUses(Set<String> uses) {
        this.uses = uses;
    }

    public List<ModuleItem> getItems() {
        return items;
    }

    public void setItems(List<ModuleItem> items) {
        this.items = items;
    }

    public Set<String> getImports()
    {
        return imports;
    }

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
        if (url != null) {
            buf.append(" url '" + url + "'");
        }
        return buf.toString();
    }
}
