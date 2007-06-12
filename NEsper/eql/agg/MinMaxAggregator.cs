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

        /// <summary>
        /// Enters the specified item.
        /// </summary>
        /// <param name="item">The item.</param>
	    public void Enter(Object item)
	    {
	        if (item == null)
	        {
	            return;
	        }
	        refSet.Add(item);
	    }

        /// <summary>
        /// Leaves the specified item.
        /// </summary>
        /// <param name="item">The item.</param>
	    public void Leave(Object item)
	    {
	        if (item == null)
	        {
	            return;
	        }
            refSet.Remove(item);
	    }

        /// <summary>
        /// Returns the current value held.
        /// </summary>
        /// <value></value>
        /// <returns>current value</returns>
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

        /// <summary>
        /// Returns the type of the current value.
        /// </summary>
        /// <value></value>
        /// <returns>type of values held</returns>
	    public Type ValueType
	    {
            get { return returnType; }
	    }

        /// <summary>
        /// Make a new, initalized aggregation state.
        /// </summary>
        /// <param name="methodResolutionService">for use in creating new aggregation method instances as a factory</param>
        /// <returns>initialized copy of the aggregator</returns>
	    public AggregationMethod NewAggregator(MethodResolutionService methodResolutionService)
	    {
	        return methodResolutionService.MakeMinMaxAggregator(minMaxTypeEnum, returnType);
	    }
	}
} // End of namespace
