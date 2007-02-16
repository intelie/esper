package net.esper.eql.expression;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.util.JavaClassHelper;
import net.esper.util.LikeUtil;
import net.esper.event.EventBean;

/**
 * Represents the like-clause in an expression tree.
 */
public class ExprLikeNode extends ExprNode
{
    private final boolean isNot;

    private boolean isNumericValue;
    private boolean isConstantPattern;
    private LikeUtil likeUtil;

    /**
     * Ctor.
     * @param not is true if this is a "not like", or false if just a like
     */
    public ExprLikeNode(boolean not)
    {
        this.isNot = not;
    }

    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewResourceDelegate viewResourceDelegate) throws ExprValidationException
    {
        if ((this.getChildNodes().size() != 2) && (this.getChildNodes().size() != 3))
        {
            throw new ExprValidationException("The 'like' operator requires 2 (no escape) or 3 (with escape) child expressions");
        }

        // check eval child node - can be String or numeric
        Class evalChildType = this.getChildNodes().get(0).getType();
        isNumericValue = JavaClassHelper.isNumeric(evalChildType);
        if ((evalChildType != String.class) && (!isNumericValue))
        {
            throw new ExprValidationException("The 'like' operator requires a String or numeric type left-hand expression");
        }

        // check pattern child node
        ExprNode patternChildNode = this.getChildNodes().get(1);
        Class patternChildType = patternChildNode.getType();
        if (patternChildType != String.class)
        {
            throw new ExprValidationException("The 'like' operator requires a String-type pattern expression");
        }
        if (this.getChildNodes().get(1) instanceof ExprConstantNode)
        {
            isConstantPattern = true;
        }

        // check escape character node
        if (this.getChildNodes().size() == 3)
        {
            ExprNode escapeChildNode = this.getChildNodes().get(2);
            if (escapeChildNode.getType() != String.class)
            {
                throw new ExprValidationException("The 'like' operator escape parameter requires a character-type value");
            }
        }
    }

    public Class getType()
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        if (likeUtil == null)
        {
            String patternVal = (String) this.getChildNodes().get(1).evaluate(eventsPerStream, isNewData);
            if (patternVal == null)
            {
                return null;
            }
            String escape = "\\";
            Character escapeCharacter = null;
            if (this.getChildNodes().size() == 3)
            {
                escape = (String) this.getChildNodes().get(2).evaluate(eventsPerStream, isNewData);
            }
            if (escape.length() > 0)
            {
                escapeCharacter = escape.charAt(0);
            }
            likeUtil = new LikeUtil(patternVal, escapeCharacter, false);
        }
        else
        {
            if (!isConstantPattern)
            {
                String patternVal = (String) this.getChildNodes().get(1).evaluate(eventsPerStream, isNewData);
                if (patternVal == null)
                {
                    return null;
                }
                likeUtil.resetPattern(patternVal);
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

        Boolean result = likeUtil.compare( (String) evalValue);

        if (isNot)
        {
            return !result;
        }

        return result;
    }

    public boolean equalsNode(ExprNode node_)
    {
        if (!(node_ instanceof ExprLikeNode))
        {
            return false;
        }

        ExprLikeNode other = (ExprLikeNode) node_;
        if (this.isNot != other.isNot)
        {
            return false;
        }
        return true;
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.getChildNodes().get(0).toExpressionString());

        if (isNot)
        {
            buffer.append(" not");
        }

        buffer.append(" like ");
        buffer.append(this.getChildNodes().get(1).toExpressionString());

        if (this.getChildNodes().size() == 3)
        {
            buffer.append(" escape ");
            buffer.append(this.getChildNodes().get(2).toExpressionString());
        }

        return buffer.toString();
    }
}
