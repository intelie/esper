package com.espertech.esper.client.soda;

import java.io.StringWriter;

public class FrequencyExpression extends ExpressionBase
{
    public FrequencyExpression(Expression numericParameter)
    {
        this.getChildren().add(numericParameter);
    }

    public void toEPL(StringWriter writer)
    {
        writer.append("*/");
        this.getChildren().get(0).toEPL(writer);
    }
}
