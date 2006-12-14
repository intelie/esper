package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;
import net.esper.client.EPException;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Represents the regexp-clause in an expression tree.
 */
public class ExprRegexpNode extends ExprNode
{
    private final boolean isNot;

    private Pattern pattern;
    private boolean isNumericValue;
    private boolean isConstantPattern;

    /**
     * Ctor.
     * @param not is true if the it's a "not regexp" expression, of false for regular regexp 
     */
    public ExprRegexpNode(boolean not)
    {
        this.isNot = not;
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 2)
        {
            throw new ExprValidationException("The regexp operator requires 2 child expressions");
        }

        // check pattern child node
        ExprNode patternChildNode = this.getChildNodes().get(1);
        Class patternChildType = patternChildNode.getType();
        if (patternChildType != String.class)
        {
            throw new ExprValidationException("The regexp operator requires a String-type pattern expression");
        }
        if (this.getChildNodes().get(1) instanceof ExprConstantNode)
        {
            isConstantPattern = true;
        }

        // check eval child node - can be String or numeric
        Class evalChildType = this.getChildNodes().get(0).getType();
        isNumericValue = JavaClassHelper.isNumeric(evalChildType);
        if ((evalChildType != String.class) && (!isNumericValue))
        {
            throw new ExprValidationException("The regexp operator requires a String or numeric type left-hand expression");
        }
    }

    public Class getType()
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        if (pattern == null)
        {
            String patternText = (String) this.getChildNodes().get(1).evaluate(eventsPerStream, isNewData);
            if (patternText == null)
            {
                return null;
            }
            try
            {
                pattern = Pattern.compile(patternText);
            }
            catch (PatternSyntaxException ex)
            {
                throw new EPException("Error compiling regex pattern '" + patternText + "'", ex);
            }
        }
        else
        {
            if (!isConstantPattern)
            {
                String patternText = (String) this.getChildNodes().get(1).evaluate(eventsPerStream, isNewData);
                if (patternText == null)
                {
                    return null;
                }
                try
                {
                    pattern = Pattern.compile(patternText);
                }
                catch (PatternSyntaxException ex)
                {
                    throw new EPException("Error compiling regex pattern '" + patternText + "'", ex);
                }
            }
        }

        Object evalValue = this.getChildNodes().get(0).evaluate(eventsPerStream, isNewData);
        if (evalValue == null)
        {
            return null;
        }

        if (isNumericValue)
        {
            evalValue = evalValue.toString();
        }

        Boolean result = pattern.matcher((String) evalValue).matches();

        if (isNot)
        {
            return !result;
        }

        return result;
    }

    public boolean equalsNode(ExprNode node_)
    {
        if (!(node_ instanceof ExprRegexpNode))
        {
            return false;
        }

        ExprRegexpNode other = (ExprRegexpNode) node_;
        if (this.isNot != other.isNot)
        {
            return false;
        }
        return true;
    }

    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();

        buffer.append(this.getChildNodes().get(0).toExpressionString());

        if (isNot)
        {
            buffer.append(" not");
        }
        buffer.append(" regexp ");
        buffer.append(this.getChildNodes().get(1).toExpressionString());

        return buffer.toString();
    }
}
