package net.esper.eql.expression;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.esper.event.EventBean;
import net.esper.type.BitWiseOpEnum;
import net.esper.util.JavaClassHelper;

/**
 * Represents the bit-wise operators in an expression tree.
 */
public class ExprBitWiseNode extends ExprNode {

    private final BitWiseOpEnum _bitWiseOpEnum;
    private BitWiseOpEnum.Computer _bitWiseOpEnumComputer;
    private Class _resultType;

    /**
     * Ctor.
     * @param bitWiseOpEnum_ - type of math
     */
    public ExprBitWiseNode(BitWiseOpEnum bitWiseOpEnum_)
    {
    	_bitWiseOpEnum = bitWiseOpEnum_;
    }	
    
    public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 2)
        {
            throw new ExprValidationException("BitWise node must have 2 child nodes");
        }

        for (ExprNode child : getChildNodes())
        {
            Class childType = child.getType();
            if ((!JavaClassHelper.isBoolean(childType)) && (!JavaClassHelper.isNumeric(childType)))
            {
            	throw new ExprValidationException("Invalid datatype for bitwise " +
            			childType.getName() + " is not allowed");
            }
        }

        // Determine result type, set up compute function
        Class childTypeOne = this.getChildNodes().get(0).getType();
        Class childTypeTwo = this.getChildNodes().get(1).getType();
    	if ((JavaClassHelper.isFloatingPointClass(childTypeOne)) || (JavaClassHelper.isFloatingPointClass(childTypeTwo)))
    	{
    		throw new ExprValidationException("Invalid type for bitwise " + _bitWiseOpEnum.getComputeDescription()  + " operator");
    	}        	        	
    	else
    	{
    		Class childBoxedTypeOne = JavaClassHelper.getBoxedType(childTypeOne) ;
    		Class childBoxedTypeTwo = JavaClassHelper.getBoxedType(childTypeTwo) ;
        	if (childBoxedTypeOne == childBoxedTypeTwo)
        	{
                _resultType = childBoxedTypeOne;
                _bitWiseOpEnumComputer = _bitWiseOpEnum.getComputer(_resultType);        		
        	}
        	else
        	{
        		throw new ExprValidationException("Both nodes muts be of the same type for bitwise " + _bitWiseOpEnum.getComputeDescription()  + " operator");        		
        	}
    	}
    }

    public Class getType() throws ExprValidationException
    {
        return _resultType;
    }

    public Object evaluate(EventBean[] eventsPerStream)
    {
        Object valueChildOne = this.getChildNodes().get(0).evaluate(eventsPerStream);
        Object valueChildTwo = this.getChildNodes().get(1).evaluate(eventsPerStream);

        if ((valueChildOne == null) || (valueChildTwo == null))
        {
            return null;
        }

        // bitWiseOpEnumComputer is initialized by validation
        Object result = _bitWiseOpEnumComputer.compute((Object) valueChildOne, (Object) valueChildTwo);
        return result;
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprBitWiseNode))
        {
            return false;
        }

        ExprBitWiseNode other = (ExprBitWiseNode) node;

        if (other._bitWiseOpEnum != _bitWiseOpEnum)
        {
            return false;
        }

        return true;
    }    

    public String toExpressionString()
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("(");

        buffer.append(getChildNodes().get(0).toExpressionString());
        buffer.append(_bitWiseOpEnum.getComputeDescription());
        buffer.append(getChildNodes().get(1).toExpressionString());

        buffer.append(")");
        return buffer.toString();
    }

    private static final Log log = LogFactory.getLog(ExprBitWiseNode.class);
}
