package net.esper.eql.expression;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import net.esper.event.EventBean;

/**
 * Represents an invocation of a static library method in the expression tree.
 */
public class ExprStaticMethodNode extends ExprNode 
{
	private final Method staticMethod;
	/**
	 * Ctor.
	 * @param staticMethod - the static method that will be invoked when this node is evaluated
	 */
	public ExprStaticMethodNode(Method staticMethod)
	{
		if(staticMethod == null)
		{
			throw new NullPointerException();
		}
		
		int modifiers = staticMethod.getModifiers();
		if(!Modifier.isPublic(modifiers))
		{
			throw new IllegalArgumentException("The method invoked by ExprStaticMethodNode must be public");
		}
		if(!Modifier.isStatic(modifiers))
		{
			throw new IllegalArgumentException("The method invoked by ExprStaticMethodNode must be static");
		}
		
		this.staticMethod = staticMethod;
	}
	
	/**
	 * @return the static method that this node invokes
	 */
	public Method getStaticMethod() 
	{
		return staticMethod;
	}
	
	public String toExpressionString() 
	{
		return staticMethod.toString();
	}

	public boolean equalsNode(ExprNode node) 
	{
		if(!(node instanceof ExprStaticMethodNode))
		{
			return false;
		}
		
		Method otherMethod = ((ExprStaticMethodNode) node).staticMethod;
		return staticMethod.equals(otherMethod);
	}

	public void validate(StreamTypeService streamTypeService) throws ExprValidationException 
	{
		Class[] parameters = staticMethod.getParameterTypes();
		List<ExprNode> childNodes = this.getChildNodes();
		
		// Check that the number of parameters is the same 
		// as the number of child nodes
		if(parameters.length != childNodes.size())
		{
			throw new ExprValidationException("Incorrent number of parameters to the static method " + staticMethod);
		}

		// Check that the parameters can be assigned to 
		// from the child nodes
		int count = 0;
		for(ExprNode childNode : childNodes)
		{
			if(!StaticMethodResolver.isValidTypeConversion(parameters[count++], childNode.getType()))
			{
				throw new ExprValidationException("Incorrent parameter type for the static method " + staticMethod);
			}
		}
	}

	public Class getType() throws ExprValidationException 
	{
		return staticMethod.getReturnType();
	}

	public Object evaluate(EventBean[] eventsPerStream) 
	{
		List<ExprNode> childNodes = this.getChildNodes();

		Object[] args = new Object[childNodes.size()];
		int count = 0;
		for(ExprNode childNode : childNodes)
		{
			args[count++] = childNode.evaluate(eventsPerStream);
		}
		
		// The method is static so the object it is invoked on
		// can be null
		Object obj = null;
		try {
			return staticMethod.invoke(obj, args);
			
		// The possible exceptions are temporarily
		// stifled
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
