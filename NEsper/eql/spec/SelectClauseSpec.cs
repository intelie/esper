///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.util;

namespace net.esper.eql.spec
{
	/// <summary>
	/// Encapsulates the parsed select expressions in a select-clause in an EQL statement.
	/// </summary>
	public class SelectClauseSpec : MetaDefItem
	{
		private List<SelectExprElementRawSpec> selectList;
		private bool isUsingWildcard;

	    /// <summary>Ctor.</summary>
	    public SelectClauseSpec()
		{
			selectList = new ArrayList<SelectExprElementRawSpec>();
		}

	    /// <summary>Ctor.</summary>
	    /// <param name="selectList">for a populates list of select expressions</param>
	    public SelectClauseSpec(List<SelectExprElementRawSpec> selectList)
		{
			this.selectList = selectList;
		}

	    /// <summary>Adds an select expression within the select clause.</summary>
	    /// <param name="element">is the expression to add</param>
	    public void Add(SelectExprElementRawSpec element)
		{
			selectList.Add(element);
		}

	    /// <summary>Returns the list of select expressions.</summary>
	    /// <returns>list of expressions</returns>
	    public List<SelectExprElementRawSpec> SelectList
		{
            get { return selectList; }
		}

	    /// <summary>
	    /// Gets or sets a value indicating if a wildcard was found in the select clause
	    /// </summary>
	    /// <returns>true for wildcard found</returns>
	    public bool IsUsingWildcard
		{
            get { return isUsingWildcard; }
            set { this.isUsingWildcard = value; }
        }
	}
} // End of namespace
