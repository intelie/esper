package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.event.EventBean;
import net.esper.collection.Pair;
import net.esper.util.JavaClassHelper;
import net.esper.schedule.TimeProvider;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents the INSTANCEOF(a,b,...) function is an expression tree.
 */
public class ExprInstanceofNode extends ExprNode
{
    private final String[] classIdentifiers;

    private Class[] classes;
    private CopyOnWriteArrayList<Pair<Class, Boolean>> resultCache = new CopyOnWriteArrayList<Pair<Class, Boolean>>();

    /**
     * Ctor.
     * @param classIdentifiers is a list of type names to check type for
     */
    public ExprInstanceofNode(String[] classIdentifiers)
    {
        this.classIdentifiers = classIdentifiers;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 1)
        {
            throw new ExprValidationException("Instanceof node must have 1 child expression node supplying the expression to test");
        }
        if ((classIdentifiers == null) || (classIdentifiers.length == 0))
        {
            throw new ExprValidationException("Instanceof node must have 1 or more class identifiers to verify type against");
        }

        Set<Class> classList = getClassSet(classIdentifiers);
        classes = classList.toArray(new Class[0]);
    }

    public boolean isConstantResult()
    {
        return false;
    }

    public Class getType() throws ExprValidationException
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        Object result = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);
        if (result == null)
        {
            return false;
        }

        // return cached value
        for (Pair<Class, Boolean> pair : resultCache)
        {
            if (pair.getFirst() == result.getClass())
            {
                return pair.getSecond();
            }
        }

        return checkAddType(result.getClass());
    }

    // Checks type and adds to cache
    private synchronized Boolean checkAddType(Class type)
    {
        // check again in synchronized block
        for (Pair<Class, Boolean> pair : resultCache)
        {
            if (pair.getFirst() == type)
            {
                return pair.getSecond();
            }
        }

        // get the types superclasses and interfaces, and their superclasses and interfaces
        Set<Class> classesToCheck = new HashSet<Class>();
        getSuper(type, classesToCheck);
        classesToCheck.add(type);

        // check type against each class
        boolean fits = false;
        for (Class clazz : classes)
        {
            if (classesToCheck.contains(clazz))
            {
                fits = true;
                break;
            }
        }

        resultCache.add(new Pair<Class, Boolean>(type, fits));
        return fits;
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("instanceof(");
        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(", ");

        String delimiter = "";
        for (int i = 0; i < classIdentifiers.length; i++)
        {
            buffer.append(delimiter);
            buffer.append(classIdentifiers[i]);
            delimiter = ", ";
        }
        buffer.append(')');
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprInstanceofNode))
        {
            return false;
        }
        ExprInstanceofNode other = (ExprInstanceofNode) node;
        if (Arrays.equals(other.classIdentifiers, classIdentifiers))
        {
            return true;
        }
        return false;
    }

    private static void getSuperInterfaces(Class clazz, Set<Class> result)
    {
        Class interfaces[] = clazz.getInterfaces();

        for (int i = 0; i < interfaces.length; i++)
        {
            result.add(interfaces[i]);
            getSuperInterfaces(interfaces[i], result);
        }
    }

    private static void getSuperClasses(Class clazz, Set<Class> result)
    {
        Class superClass = clazz.getSuperclass();
        if (superClass == null)
        {
            return;
        }

        result.add(superClass);
        getSuper(superClass, result);
    }

    /**
     * Populates all interface and superclasses for the given class, recursivly.
     * @param clazz to reflect upon
     * @param result set of classes to populate
     */
    protected static void getSuper(Class clazz, Set<Class> result)
    {
        getSuperInterfaces(clazz, result);
        getSuperClasses(clazz, result);
    }

    /**
     * Returns the list of class names or types to check instance of.
     * @return class names
     */
    public String[] getClassIdentifiers()
    {
        return classIdentifiers;
    }

    private Set<Class> getClassSet(String[] classIdentifiers)
            throws ExprValidationException
    {
        Set<Class> classList = new HashSet<Class>();
        for (String className : classIdentifiers)
        {
            Class clazz;

            // try the primitive names including "string"
            clazz = JavaClassHelper.getPrimitiveClassForName(className.trim());
            if (clazz != null)
            {
                classList.add(clazz);
                classList.add(JavaClassHelper.getBoxedType(clazz));
                continue;
            }

            // try to look up the class, not a primitive type name
            try
            {
                clazz = JavaClassHelper.getClassForName(className.trim());
            }
            catch (ClassNotFoundException e)
            {
                throw new ExprValidationException("Class as listed in instanceof function by name '" + className + "' cannot be loaded", e);
            }

            // Add primitive and boxed types, or type itself if not built-in
            classList.add(JavaClassHelper.getPrimitiveType(clazz));
            classList.add(JavaClassHelper.getBoxedType(clazz));
        }
        return classList;
    }
}
