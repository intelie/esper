package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;
import net.esper.collection.Pair;

import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Represents the CAST(expression, type) function is an expression tree.
 */
public class ExprCastNode extends ExprNode
{
    private final String classIdentifier;
    private Class targetType;
    private Caster caster;
    private boolean isNumeric;

    public ExprCastNode(String classIdentifier)
    {
        this.classIdentifier = classIdentifier;
    }

    public String getClassIdentifier()
    {
        return classIdentifier;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
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
            if (targetType == Integer.class)
            {
                caster = new IntCaster();
                isNumeric = true;
            }
            else if (targetType == Long.class)
            {
                caster = new LongCaster();
                isNumeric = true;
            }
            else if (targetType == Double.class)
            {
                caster = new DoubleCaster();
                isNumeric = true;
            }
            else if (targetType == Float.class)
            {
                caster = new FloatCaster();
                isNumeric = true;
            }
            else if (targetType == Short.class)
            {
                caster = new ShortCaster();
                isNumeric = true;
            }
            else if (targetType == Byte.class)
            {
                caster = new ByteCaster();
                isNumeric = true;
            }
            else
            {
                caster = new TypeCaster(targetType);
            }
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
            caster = new TypeCaster(targetType);
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

    public interface Caster
    {
        public Object cast(Object object);
    }

    public class DoubleCaster implements Caster
    {
        public Object cast(Object object)
        {
            return ((Number) object).doubleValue();
        }
    }

    public class FloatCaster implements Caster
    {
        public Object cast(Object object)
        {
            return ((Number) object).floatValue();
        }
    }

    public class LongCaster implements Caster
    {
        public Object cast(Object object)
        {
            return ((Number) object).longValue();
        }
    }

    public class IntCaster implements Caster
    {
        public Object cast(Object object)
        {
            return ((Number) object).intValue();
        }
    }

    public class ShortCaster implements Caster
    {
        public Object cast(Object object)
        {
            return ((Number) object).shortValue();
        }
    }

    public class ByteCaster implements Caster
    {
        public Object cast(Object object)
        {
            return ((Number) object).byteValue();
        }
    }

    public class TypeCaster implements Caster
    {
        private Class typeToCastTo;
        private CopyOnWriteArraySet<Pair<Class, Boolean>> pairs = new CopyOnWriteArraySet<Pair<Class, Boolean>>();

        public TypeCaster(Class typeToCastTo)
        {
            this.typeToCastTo = typeToCastTo;
        }

        public Object cast(Object object)
        {
            if (object.getClass() == typeToCastTo)
            {
                return object;
            }

            // check cache to see if this is cast-able
            for (Pair<Class, Boolean> pair : pairs)
            {
                if (pair.getFirst() == typeToCastTo)
                {
                    if (!pair.getSecond())
                    {
                        return null;
                    }
                    return object;
                }
            }

            // Not found in cache, add to cache;
            synchronized(this)
            {
                // search cache once more
                for (Pair<Class, Boolean> pair : pairs)
                {
                    if (pair.getFirst() == typeToCastTo)
                    {
                        if (!pair.getSecond())
                        {
                            return null;
                        }
                        return object;
                    }
                }

                // Determine if any of the super-types and interfaces that the object implements or extends
                // is the same as any of the target types
                Set<Class> classesToCheck = new HashSet<Class>();
                ExprInstanceofNode.getSuper(object.getClass(), classesToCheck);

                if (classesToCheck.contains(typeToCastTo))
                {
                    pairs.add(new Pair<Class, Boolean>(object.getClass(), true));
                    return object;
                }
                pairs.add(new Pair<Class, Boolean>(object.getClass(), false));
                return null;                
            }
        }
    }
}
