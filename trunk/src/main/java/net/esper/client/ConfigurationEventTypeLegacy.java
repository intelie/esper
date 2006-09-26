package net.esper.client;

import java.util.List;
import java.util.LinkedList;

public class ConfigurationEventTypeLegacy
{
    private AccessorStyle accessorStyle;
    private CodeGeneration codeGeneration;
    private List<LegacyMethodPropDesc> methodProperties;
    private List<LegacyFieldPropDesc> fieldProperties;

    public ConfigurationEventTypeLegacy()
    {
        accessorStyle = AccessorStyle.JAVABEAN;
        codeGeneration = CodeGeneration.ENABLED;
        methodProperties = new LinkedList<LegacyMethodPropDesc>();
        fieldProperties = new LinkedList<LegacyFieldPropDesc>();
    }

    public enum AccessorStyle {     // ensure the names match the configuration schema type restriction defs
        JAVABEAN,
        EXPLICIT,
        PUBLIC
    }
    
    public enum CodeGeneration {    // ensure the names match the configuration schema type restriction defs
        ENABLED,
        DISABLED
    }

    public void setAccessorStyle(AccessorStyle accessorStyle)
    {
        this.accessorStyle = accessorStyle;
    }

    public void setCodeGeneration(CodeGeneration codeGeneration)
    {
        this.codeGeneration = codeGeneration;
    }

    public AccessorStyle getAccessorStyle()
    {
        return accessorStyle;
    }

    public CodeGeneration getCodeGeneration()
    {
        return codeGeneration;
    }

    public List<LegacyMethodPropDesc> getMethodProperties()
    {
        return methodProperties;
    }

    public List<LegacyFieldPropDesc> getFieldProperties()
    {
        return fieldProperties;
    }

    public void addMethodProperty(String name, String accessorMethod)
    {
        methodProperties.add(new LegacyMethodPropDesc(name, accessorMethod));
    }

    public void addFieldProperty(String name, String accessorField)
    {
        fieldProperties.add(new LegacyFieldPropDesc(name, accessorField));
    }

    public class LegacyFieldPropDesc
    {
        private String name;
        private String accessorFieldName;

        public LegacyFieldPropDesc(String name, String accessorFieldName)
        {
            this.name = name;
            this.accessorFieldName = accessorFieldName;
        }

        public String getName()
        {
            return name;
        }

        public String getAccessorFieldName()
        {
            return accessorFieldName;
        }
    }

    public class LegacyMethodPropDesc
    {
        private String name;
        private String accessorMethodName;

        public LegacyMethodPropDesc(String name, String accessorMethodName)
        {
            this.name = name;
            this.accessorMethodName = accessorMethodName;
        }

        public String getName()
        {
            return name;
        }

        public String getAccessorMethodName()
        {
            return accessorMethodName;
        }
    }
}
