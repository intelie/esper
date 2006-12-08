package net.esper.eql.expression;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.esper.client.EPException;
import net.esper.event.EventBean;
import net.esper.util.StaticMethodResolver;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewFactoryDelegate;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

/**
 * Represents an invocation of a static library method in the expression tree.
 */
public class ExprStaticMethodNode extends ExprNode 
{
	private final String className;
	private final String methodName;
	private Class[] paramTypes;
	private FastMethod staticMethod;
	
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
     * Returns the static method.
	 * @return the static method that this node invokes
	 */
	protected Method getStaticMethod()
	{
		return staticMethod.getJavaMethod();
	}
	
	/**
     * Returns the class name.
	 * @return the class that declared the static method
	 */
	public String getClassName() {
		return className;
	}

	/**
     * Returns the method name.
	 * @return the name of the method
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
     * Returns parameter descriptor.
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

	public void validate(StreamTypeService streamTypeService, AutoImportService autoImportService, ViewFactoryDelegate viewFactoryDelegate) throws ExprValidationException
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
			Method method = StaticMethodResolver.resolveMethod(className, methodName, paramTypes, autoImportService);
			FastClass declaringClass = FastClass.create(method.getDeclaringClass());
			staticMethod = declaringClass.getMethod(method);
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

	public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
	{
		List<ExprNode> childNodes = this.getChildNodes();

		Object[] args = new Object[childNodes.size()];
		int count = 0;
		for(ExprNode childNode : childNodes)
		{
			args[count++] = childNode.evaluate(eventsPerStream, isNewData);
		}
		
		// The method is static so the object it is invoked on
		// can be null
		Object obj = null;
		try 
		{
			return staticMethod.invoke(obj, args);
		} 
		catch (InvocationTargetException e) 
		{
			throw new EPException(e);
		}
	}
}
