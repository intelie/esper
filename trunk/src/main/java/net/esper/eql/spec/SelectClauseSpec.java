package net.esper.eql.spec;

import java.util.ArrayList;
import java.util.List;

public class SelectClauseSpec
{
	private List<SelectExprElementUnnamedSpec> selectList;
	private boolean isUsingWildcard;
	
	public SelectClauseSpec()
	{
		selectList = new ArrayList<SelectExprElementUnnamedSpec>();
	}
	
	public SelectClauseSpec(List<SelectExprElementUnnamedSpec> selectList)
	{
		this.selectList = selectList;
	}
	
	public void setIsUsingWildcard(boolean isUsingWildcard)
	{
		this.isUsingWildcard = isUsingWildcard;
	}
	
	public void add(SelectExprElementUnnamedSpec element)
	{
		selectList.add(element);
	}
	
	public List<SelectExprElementUnnamedSpec> getSelectList()
	{
		return selectList;
	}
	
	public boolean isUsingWildcard()
	{
		return isUsingWildcard;
	}
}
