package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.util.*;

import java.math.BigInteger;
import java.math.BigDecimal;

/**
 * Represents the CAST(expression, type) function is an expression tree.
 */
public class ExprCastNode extends ExprNode
{
    private final String classIdentifier;
    private Class targetType;
    private CasterParserComputer casterParserComputer;
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

        Class fromType = this.getChildNodes().get(0).getType();

        // identify target type
        // try the primitive names including "string"
        SimpleTypeCaster caster;
        targetType = JavaClassHelper.getPrimitiveClassForName(classIdentifier.trim());
        if (targetType != null)
        {
            targetType = JavaClassHelper.getBoxedType(targetType);
            caster = SimpleTypeCasterFactory.getCaster(fromType, targetType);
            isNumeric = caster.isNumericCast();
        }
        else if (classIdentifier.trim().toLowerCase().equals("BigInteger".toLowerCase()))
        {
            targetType = BigInteger.class;
            caster = SimpleTypeCasterFactory.getCaster(fromType, targetType);
            isNumeric = true;
        }
        else if (classIdentifier.trim().toLowerCase().equals("BigDecimal".toLowerCase()))
        {
            targetType = BigDecimal.class;
            caster = SimpleTypeCasterFactory.getCaster(fromType, targetType);
            isNumeric = true;
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

        // to-string
        if (targetType == String.class)
        {
            casterParserComputer = new StringXFormComputer();
        }
        // parse
        else if (fromType == String.class)
        {
            SimpleTypeParser parser = SimpleTypeParserFactory.getParser(JavaClassHelper.getBoxedType(targetType));
            casterParserComputer = new StringParserComputer(parser);
        }
        // numeric cast with check
        else if (isNumeric)
        {
            casterParserComputer = new NumberCasterComputer(caster);
        }
        // non-numeric cast
        else
        {
            casterParserComputer = new NonnumericCasterComputer(caster);
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

        return casterParserComputer.compute(result);
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

    public interface CasterParserComputer
    {
        public Object compute(Object input);
    }

    public static class StringXFormComputer implements CasterParserComputer
    {
        public Object compute(Object input)
        {
            return input.toString();
        }
    }

    public static class NumberCasterComputer implements CasterParserComputer
    {
        private final SimpleTypeCaster numericTypeCaster;

        public NumberCasterComputer(SimpleTypeCaster numericTypeCaster)
        {
            this.numericTypeCaster = numericTypeCaster;
        }

        public Object compute(Object input)
        {
            if (input instanceof Number)
            {
                return numericTypeCaster.cast(input);
            }
            return null;
        }
    }

    public static class StringParserComputer implements CasterParserComputer
    {
        private final SimpleTypeParser parser;

        public StringParserComputer(SimpleTypeParser parser)
        {
            this.parser = parser;
        }

        public Object compute(Object input)
        {
            return parser.parse(input.toString());
        }
    }

    public static class NonnumericCasterComputer implements CasterParserComputer
    {
        private final SimpleTypeCaster caster;

        public NonnumericCasterComputer(SimpleTypeCaster numericTypeCaster)
        {
            this.caster = numericTypeCaster;
        }

        public Object compute(Object input)
        {
            return caster.cast(input);
        }
    }

}
