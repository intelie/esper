package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.util.SimpleTypeCaster;
import com.espertech.esper.util.SimpleTypeCasterFactory;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.SimpleTypeCasterAnyType;

/**
 * Represents the CAST(expression, type) function is an expression tree.
 */
public class ExprCastNode extends ExprNode
{
    private final String classIdentifier;
    private Class targetType;
    private SimpleTypeCaster caster;
    private boolean isNumeric;

    /**
     * Ctor.
     * @param classIdentifier the the name of the type to cast to
     */
    public ExprCastNode(String classIdentifier)
    {
        this.classIdentifier = classIdentifier;
    }

    /**
     * Returns the name of the type of cast to.
     * @return type name
     */
    public String getClassIdentifier()
    {
        return classIdentifier;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 1)
        {
            throw new ExprValidationException("Cast function node must have exactly 1 child node");
        }

        // try the primitive names including "string"
        targetType = JavaClassHelper.getPrimitiveClassForName(classIdentifier.trim());
        if (targetType != null)
        {
            targetType = JavaClassHelper.getBoxedType(targetType);
            caster = SimpleTypeCasterFactory.getCaster(targetType);
            isNumeric = caster.isNumericCast();
        }
        else
        {
            try
            {
                targetType = JavaClassHelper.getClassForName(classIdentifier.trim());
            }
            catch (ClassNotFoundException e)
            {
                throw new ExprValidationException("Class as listed in cast function by name '" + classIdentifier + "' cannot be loaded", e);
            }
            caster = new SimpleTypeCasterAnyType(targetType);
        }
    }

    public boolean isConstantResult()
    {
        return false;
    }

    public Class getType() throws ExprValidationException
    {
        return targetType;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object result = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);
        if (result == null)
        {
            return null;
        }

        // Numeric
        if (isNumeric)
        {
            if (result instanceof Number)
            {
                return caster.cast(result);
            }
            return null;
        }
        else if (targetType == String.class)
        {
            return result.toString();
        }
        else
        {
            return caster.cast(result);
        }
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("cast(");
        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(", ");
        buffer.append(classIdentifier);
        buffer.append(')');
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprCastNode))
        {
            return false;
        }
        ExprCastNode other = (ExprCastNode) node;
        if (other.classIdentifier.equals(this.classIdentifier))
        {
            return true;
        }

        return false;
    }
}
