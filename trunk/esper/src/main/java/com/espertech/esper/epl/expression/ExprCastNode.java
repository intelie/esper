package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.util.*;

/**
 * Represents the CAST(expression, type) function is an expression tree.
 */
public class ExprCastNode extends ExprNode
{
    private final String classIdentifier;
    private Class targetType;
    private CastExprExecutor executor;

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

        // get source type
        Class sourceType = this.getChildNodes().get(0).getType();
        sourceType = JavaClassHelper.getBoxedType(sourceType);

        // detemine target type
        targetType = JavaClassHelper.getPrimitiveClassForName(classIdentifier.trim());

        // try to load target type
        if (targetType == null)
        {
            try
            {
                targetType = JavaClassHelper.getClassForName(classIdentifier.trim());
            }
            catch (ClassNotFoundException e)
            {
                throw new ExprValidationException("Class as listed in cast function by name '" + classIdentifier + "' cannot be loaded", e);
            }
        }

        // casting to a built-in type
        if (JavaClassHelper.isJavaBuiltinDataType(targetType))
        {
            targetType = JavaClassHelper.getBoxedType(targetType);

            // no conversion
            if (targetType == sourceType)
            {
                executor = new NoCastExecutor();
            }
            // print
            else if (targetType == String.class)
            {
                executor = new StringTargetExecutor();
            }
            // parse
            else if (sourceType == String.class)
            {
                SimpleTypeParser parser = SimpleTypeParserFactory.getParser(targetType);
                executor = new ParseExecutor(parser);
            }
            // coerce between numeric types
            else if ((JavaClassHelper.isNumeric(sourceType)) && (JavaClassHelper.isNumeric(targetType)))
            {
                SimpleTypeCaster caster = SimpleTypeCasterFactory.getCaster(targetType);
                executor = new UncheckedExecutor(caster);
            }
            // checked coerce to numeric
            else if (JavaClassHelper.isNumeric(targetType))
            {
                SimpleTypeCaster caster = SimpleTypeCasterFactory.getCaster(targetType);
                executor = new CheckedNumericCoercionExecutor(caster);
            }
            else
            {
                throw new ExprValidationException("Cannot cast from type '" + sourceType.getSimpleName() + "' to type '" + targetType.getSimpleName() + "'");
            }
        }
        else
        {
            // casting to a non-builtin type
            if (targetType == sourceType)
            {
                executor = new NoCastExecutor();
            }
            else
            {
                SimpleTypeCaster caster = new SimpleTypeCasterAnyType(targetType);
                executor = new UncheckedExecutor(caster);
            }
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

        return executor.cast(result);
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

    public interface CastExprExecutor
    {
        public Object cast(Object input);
    }

    public class ParseExecutor implements CastExprExecutor
    {
        private SimpleTypeParser parser;

        public ParseExecutor(SimpleTypeParser parser)
        {
            this.parser = parser;
        }

        public Object cast(Object input)
        {
            return parser.parse((String)input);
        }
    }

    public class CheckedNumericCoercionExecutor implements CastExprExecutor
    {
        private SimpleTypeCaster caster;

        public CheckedNumericCoercionExecutor(SimpleTypeCaster caster)
        {
            this.caster = caster;
        }

        public Object cast(Object input)
        {
            if (input instanceof Number)
            {
                return caster.cast(input);
            }
            return null;
        }
    }

    public class UncheckedExecutor implements CastExprExecutor
    {
        private final SimpleTypeCaster caster;

        public UncheckedExecutor(SimpleTypeCaster caster)
        {
            this.caster = caster;
        }

        public Object cast(Object input)
        {
            return caster.cast(input);
        }
    }

    public class StringTargetExecutor implements CastExprExecutor
    {
        public Object cast(Object input)
        {
            return input.toString();
        }
    }

    public class NoCastExecutor implements CastExprExecutor
    {
        public Object cast(Object input)
        {
            return input;
        }
    }
}
