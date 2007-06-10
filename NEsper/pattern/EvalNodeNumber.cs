///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.util;

namespace net.esper.pattern
{
	/// <summary>
	/// A node number assigned to evaluation nodes in a tree-structure.
	/// <p>
	/// Represents node numbers as an array of short. Root nodes get an empty array while each level of child
	/// node adds an element. New child nodes are obtained from a parent and subsequent child nodes from the last sibling node.
	/// </summary>
	public class EvalNodeNumber : MetaDefItem
	{
	    private short[] number;

	    /// <summary>Constructs a root node number.</summary>
	    public EvalNodeNumber()
	    {
	        number = new short[0];
	    }

	    private EvalNodeNumber(short[] number)
	    {
	        this.number = number;
	    }

	    /// <summary>Returns the child number.</summary>
	    /// <returns>child number</returns>
	    public short GetChildNumber()
	    {
	        return number[number.Length - 1];
	    }

	    /// <summary>Returns true for a root node, false for child nodes.</summary>
	    /// <returns>true if root, false if child node</returns>
	    public bool IsRoot()
	    {
	        return number.Length == 0;
	    }

	    /// <summary>
	    /// Returns the parent's node number, of null if this is the root node number.
	    /// </summary>
	    /// <returns>parent node number</returns>
	    public EvalNodeNumber GetParentNumber()
	    {
	        if (isRoot())
	        {
	            return null;
	        }
	        short[] num = new short[number.Length - 1];
            Array.Copy(number, 0, num, 0, number.Length - 1);
	        return new EvalNodeNumber(num);
	    }

	    /// <summary>Returns a new child node number.</summary>
	    /// <returns>child node number</returns>
	    public EvalNodeNumber NewChildNumber()
	    {
	        short[] num = new short[number.Length + 1];
            Array.Copy(number, 0, num, 0, number.Length);
	        num[number.Length] = 0;
	        return new EvalNodeNumber(num);
	    }

	    /// <summary>
	    /// Returns a new sibling node number based on the current node. This call is invalid for root nodes.
	    /// </summary>
	    /// <returns>sibling node number</returns>
	    public EvalNodeNumber NewSiblingNumber()
	    {
	        int size = number.Length;
	        if (size == 0)
	        {
	            throw new IllegalStateException("Cannot create a new node number for root node");
	        }

	        short[] num = new short[size];
            Array.Copy(number, 0, num, 0, size);
	        short next = number[size - 1];
	        num[size - 1] = ++next;
	        return new EvalNodeNumber(num);
	    }

	    public override String ToString()
	    {
	        return Arrays.ToString(number);
	    }

	    /// <summary>Returns the node number representation in an array of short.</summary>
	    /// <returns>node number as short array</returns>
	    public short[] GetNumber()
	    {
	        return number;
	    }

	    public override bool Equals(Object o)
	    {
	        if (this == o)
	        {
	            return true;
	        }
	        if (o == null || GetType() != o.GetType())
	        {
	            return false;
	        }

	        EvalNodeNumber that = (EvalNodeNumber) o;

	        if (!Arrays.Equals(number, that.number))
	        {
	            return false;
	        }

	        return true;
	    }

	    public override int GetHashCode()
	    {
	        return Arrays.HashCode(number);
	    }
	}
} // End of namespace
