///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.collection;
using net.esper.eql.agg;
using net.esper.eql.core;
using net.esper.type;

namespace net.esper.eql.agg
{
	/// <summary>Min/max aggregator for all values.</summary>
	public class MinMaxAggregator : AggregationMethod
	{
	    private readonly MinMaxTypeEnum minMaxTypeEnum;
	    private readonly Type returnType;

	    private SortedRefCountedSet<Object> refSet;

	    /// <summary>Ctor.</summary>
	    /// <param name="minMaxTypeEnum">
	    /// enum indicating to return minimum or maximum values
	    /// </param>
	    /// <param name="returnType">is the value type returned by aggregator</param>
	    public MinMaxAggregator(MinMaxTypeEnum minMaxTypeEnum, Type returnType)
	    {
	        this.minMaxTypeEnum = minMaxTypeEnum;
	        this.returnType = returnType;
	        this.refSet = new SortedRefCountedSet<Object>();
	    }

	    public void Enter(Object item)
	    {
	        if (item == null)
	        {
	            return;
	        }
	        refSet.Add(item);
	    }

	    public void Leave(Object item)
	    {
	        if (item == null)
	        {
	            return;
	        }
            refSet.Remove(item);
	    }

	    public Object Value
	    {
            get
            {
                if (minMaxTypeEnum == MinMaxTypeEnum.MAX)
                {
                    return refSet.MaxValue;
                }
                else
                {
                    return refSet.MinValue;
                }
            }
	    }

	    public Type ValueType
	    {
            get { return returnType; }
	    }

	    public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
	    {
	        return methodResolutionService.MakeMinMaxAggregator(minMaxTypeEnum, returnType);
	    }
	}
} // End of namespace
