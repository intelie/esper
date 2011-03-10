package com.espertech.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

/**
 * Represents a single annotation.
 */
public class ExpressionDeclaration implements Serializable {
    private static final long serialVersionUID = 2404842336644400196L;

    private String name;
    private List<String> parameterNames;
    private Expression expression;

    /**
     * Ctor.
     */
    public ExpressionDeclaration() {
    }

    /**
     * Ctor.
     * @param name of annotation
     */
    public ExpressionDeclaration(String name, List<String> parameterNames, Expression expression) {
        this.name = name;
        this.parameterNames = parameterNames;
        this.expression = expression;
    }

    /**
     * Returns expression name.
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets expression name.
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public List<String> getParameterNames() {
        return parameterNames;
    }

    public void setParameterNames(List<String> parameterNames) {
        this.parameterNames = parameterNames;
    }

    /**
     * Print.
     * @param writer to print to
     * @param expressionDeclarations expression declarations
     */
    public static void toEPL(StringWriter writer, List<ExpressionDeclaration> expressionDeclarations) {
        if ((expressionDeclarations == null) || (expressionDeclarations.isEmpty())) {
            return;
        }

        String delimiter = "";
        String writerDelimiter = "";
        for (ExpressionDeclaration part : expressionDeclarations) {
            writerDelimiter = " ";
            writer.append(delimiter);
            part.toEPL(writer);
            delimiter = " ";
        }
        writer.append(writerDelimiter);
    }

    /**
     * Print part.
     * @param writer to write to
     */
    public void toEPL(StringWriter writer) {
        writer.append("expression ");
        writer.append(name);
        writer.append(" {");
        if (parameterNames.size() == 1) {
            writer.append(parameterNames.get(0));
        }
        else if (!parameterNames.isEmpty()) {
            String delimiter = "";
            writer.append("(");
            for (String name : parameterNames) {
                writer.append(delimiter);
                writer.append(name);
                delimiter = ", ";
            }
            writer.append(")");
        }

        if (!parameterNames.isEmpty()) {
            writer.append(" => ");
        }
        expression.toEPL(writer, ExpressionPrecedenceEnum.MINIMUM);
        writer.append("}");
    }
}
