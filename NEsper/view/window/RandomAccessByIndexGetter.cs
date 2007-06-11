///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using net.esper.view.ext;

namespace net.esper.view.window
{
	/// <summary>Getter that provides an index at runtime.</summary>
	public class RandomAccessByIndexGetter 
        : IStreamRandomAccess.IStreamRandomAccessUpdateObserver
        , IStreamSortedRandomAccess.IStreamRandomAccessUpdateObserver
	{
	    private RandomAccessByIndex randomAccessByIndex;

	    /// <summary>Returns the index for access.</summary>
	    /// <returns>index</returns>
	    public RandomAccessByIndex Accessor
	    {
	        get { return randomAccessByIndex; }
	    }

	    public void Updated(IStreamRandomAccess iStreamRandomAccess)
	    {
	        this.randomAccessByIndex = iStreamRandomAccess;
	    }

	    public void Updated(IStreamSortedRandomAccess iStreamSortedRandomAccess)
	    {
	        this.randomAccessByIndex = iStreamSortedRandomAccess;
	    }
	}
} // End of namespace
