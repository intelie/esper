package com.espertech.esper.client.soda;

import java.io.StringWriter;
import java.util.List;

public class NewOperatorExpression extends ExpressionBase {

    private List<String> columnNames;

    public NewOperatorExpression() {
    }

    public NewOperatorExpression(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public ExpressionPrecedenceEnum getPrecedence()
    {
        return ExpressionPrecedenceEnum.NEGATED;
    }

    public void toPrecedenceFreeEPL(StringWriter writer)
    {
        writer.write("new { ");
        String delimiter = "";
        for (int i = 0; i < this.getChildren().size(); i++) {
            writer.append(delimiter);
            writer.append(columnNames.get(i));
            Expression expr = this.getChildren().get(i);

            boolean outputexpr = true;
            if (expr instanceof PropertyValueExpression) {
                PropertyValueExpression prop = (PropertyValueExpression) expr;
                if (prop.getPropertyName().equals( columnNames.get(i))) {
                    outputexpr = false;
                }
            }

            if (outputexpr) {
                writer.append(" = ");
                expr.toEPL(writer, this.getPrecedence());
            }
            delimiter = ", ";
        }
        writer.write(" }");
    }
}
