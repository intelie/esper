// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

namespace net.esper.support.bean
{
	public class SupportBeanNumeric
	{
	    private int intOne;
	    private int intTwo;

	    public SupportBeanNumeric(int intOne, int intTwo)
	    {
	        this.intOne = intOne;
	        this.intTwo = intTwo;
	    }

	    public int GetIntOne()
	    {
	        return intOne;
	    }

	    public int GetIntTwo()
	    {
	        return intTwo;
	    }
	}
} // End of namespace
