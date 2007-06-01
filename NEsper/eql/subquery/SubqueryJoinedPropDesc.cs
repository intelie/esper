///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

namespace net.esper.eql.subquery
{
    /// <summary>
    /// Holds property information for joined properties in a subquery.
    /// </summary>
    public class SubqueryJoinedPropDesc
    {
        private String indexPropName;
        private Type coercionType;
        private String keyPropName;
        private int? keyStreamId;

        /// <summary>Ctor.</summary>
        /// <param name="indexPropName">is the property name of the indexed field</param>
        /// <param name="coercionType">is the type to coerce to</param>
        /// <param name="keyPropName">is the property name of the key field</param>
        /// <param name="keyStreamId">is the stream number of the key field</param>
        public SubqueryJoinedPropDesc(String indexPropName, Type coercionType, String keyPropName, int? keyStreamId)
        {
            this.indexPropName = indexPropName;
            this.coercionType = coercionType;
            this.keyPropName = keyPropName;
            this.keyStreamId = keyStreamId;
        }

        /// <summary>Returns the property name of the indexed field.</summary>
        /// <returns>property name of indexed field</returns>
        public String IndexPropName
        {
            get { return indexPropName; }
        }

        /// <summary>Returns the coercion type of key to index field.</summary>
        /// <returns>type to coerce to</returns>
        public Type CoercionType
        {
            get { return coercionType; }
        }

        /// <summary>Returns the property name of the key field.</summary>
        /// <returns>property name of key field</returns>
        public String KeyPropName
        {
            get { return keyPropName; }
        }

        /// <summary>Returns the stream id of the key field.</summary>
        /// <returns>stream id</returns>
        public int? KeyStreamId
        {
            get { return keyStreamId; }
        }

        /// <summary>Returns the key stream numbers.</summary>
        /// <param name="descList">a list of descriptors</param>
        /// <returns>key stream numbers</returns>
        public static int[] GetKeyStreamNums(Collection<SubqueryJoinedPropDesc> descList)
        {
            int[] streamIds = new int[descList.Size()];
            int count = 0;
            foreach (SubqueryJoinedPropDesc desc in descList)
            {
                streamIds[count++] = desc.GetKeyStreamId();
            }
            return streamIds;
        }

        /// <summary>Returns the key property names.</summary>
        /// <param name="descList">a list of descriptors</param>
        /// <returns>key property names</returns>
        public static String[] GetKeyProperties(ICollection<SubqueryJoinedPropDesc> descList)
        {
            String[] result = new String[descList.Size()];
            int count = 0;
            foreach (SubqueryJoinedPropDesc desc in descList)
            {
                result[count++] = desc.GetKeyPropName();
            }
            return result;
        }

        /// <summary>Returns the key coercion types.</summary>
        /// <param name="descList">a list of descriptors</param>
        /// <returns>key coercion types</returns>
        public static Type[] GetCoercionTypes(ICollection<SubqueryJoinedPropDesc> descList)
        {
            Type[] result = new Class[descList.Size()];
            int count = 0;
            foreach (SubqueryJoinedPropDesc desc in descList)
            {
                result[count++] = desc.GetCoercionType();
            }
            return result;
        }
    }
}