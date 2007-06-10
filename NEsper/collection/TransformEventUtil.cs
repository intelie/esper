///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using net.esper.events;

namespace net.esper.collection
{
    /// <summary>
	/// Utility for reading and transforming a source event iterator.
	/// Works with a {@link TransformEventMethod} as the transformation method.
	/// </summary>

    public class TransformEventUtil
    {
        /// <summary>
        /// Transforms the enumerator using the transform method supplied.
        /// </summary>
        /// <param name="sourceEnum">The source enum.</param>
        /// <param name="transformEventMethod">The transform event method.</param>
        /// <returns></returns>
        public static IEnumerator<EventBean> TransformEnumerator(IEnumerator<EventBean> sourceEnum, TransformEventMethod transformEventMethod)
        {
            while (sourceEnum.MoveNext())
            {
                yield return transformEventMethod(sourceEnum.Current);
            }
        }
    }
} // End of namespace
