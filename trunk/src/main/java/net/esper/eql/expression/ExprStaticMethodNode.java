package net.esper.eql.expression;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.esper.event.EventBean;

/**
 * Represents an invocation of a static library method in the expression tree.
 */
public class ExprStaticMethodNode extends ExprNode 
{
	private final String className;
	private final String methodName;
	private Class[] paramTypes;
	private Method staticMethod;
	
	/**
	 * Ctor.
	 * @param className - the declaring class for the method that this node will invoke
	 * @param methodName - the name of the method that this node will invoke
	 */	
	public ExprStaticMethodNode(String className, String methodName)
	{
		if(className == null)
		{
			throw new NullPointerException("Class name is null");
		}
		if(methodName == null)
		{
			throw new NullPointerException("Method name is null");
		}
	
		this.className = className;
		this.methodName = methodName;
	}
	
	/**
	 * @return the static method that this node invokes
	 */
	public Method getStaticMethod() 
	{
		return staticMethod;
	}
	
	/**
	 * @return the class that declared the static method
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @return the name of the method
	 */
	
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @return the types of the child nodes of this node
	 */
	public Class[] getParamTypes() {
		return paramTypes;
	}

	public String toExpressionString() 
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(className);
		buffer.append(".");
		buffer.append(methodName);

		buffer.append("(");		
		String appendString = "";
		for(ExprNode child : getChildNodes())
		{
			buffer.append(appendString);
			buffer.append(child.toExpressionString());
			appendString = ", ";
		}
		buffer.append(")");
		
		return buffer.toString();
	}

	public boolean equalsNode(ExprNode node) 
	{
		if(!(node instanceof ExprStaticMethodNode))
		{
			return false;
		}

		if(staticMethod == null)
		{
			throw new IllegalStateException("ExprStaticMethodNode has not been validated");
		}
		else
		{
			ExprStaticMethodNode otherNode = (ExprStaticMethodNode) node;
			return staticMethod.equals(otherNode.staticMethod);
		}
	}

	public void validate(StreamTypeService streamTypeService) throws ExprValidationException 
	{
		// Get the types of the childNodes
		List<ExprNode> childNodes = this.getChildNodes();
		paramTypes = new Class[childNodes.size()];
		int count = 0;
		for(ExprNode childNode : childNodes)
		{
			paramTypes[count++] = childNode.getType();	
		}
		
		// Try to resolve the method
		try
		{
			staticMethod = StaticMethodResolver.resolveMethod(className, methodName, paramTypes);
		}
		catch(Exception e)
		{
			throw new ExprValidationException(e.getMessage());
		}
	}

	public Class getType() throws ExprValidationException 
	{
		if(staticMethod == null)
		{
			throw new IllegalStateException("ExprStaticMethodNode has not been validated");
		}
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
