///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Text;

using net.esper.compat;

namespace net.esper.util
{
	/// <summary>
	/// Generates a UUID.
	///<p>
	/// A Universally Unique Identifier (UUID) is a 128 bit number generated according to an algorithm
	/// that is garanteed to be unique in time A space from all other UUIDs.
	/// </p>
	/// </summary>
	/// <author>
	/// <a href="mailto:jboner@codehaus.org">Jonas Bonér</a>
	/// </author>
    public class UuidGenerator
    {
        /// <summary>Random seeder.</summary>
        private static SecureRandom s_seeder = null;

        /// <summary>Mid value, needed for calculation.</summary>
        private static String s_midValue = null;

        /// <summary>Defines if the generator is initialized or not.</summary>
        private static bool s_initialized = false;

        /// <summary>Private constructor to prevent subclassing</summary>
        private UuidGenerator()
        {
        }

        /// <summary>Returns a unique uuid.</summary>
        /// <param name="obj">the calling object (this)</param>
        /// <returns>a unique uuid</returns>
        public static String Generate(Object obj)
        {
            if (!s_initialized)
            {
                Initialize(obj);
            }
            long timeNow = System.CurrentTimeMillis();

            // get int value as unsigned
            int timeLow = (int)timeNow & 0xFFFFFFFF;
            int node = s_seeder.NextInt();
            return (HexFormat(timeLow, 8) + s_midValue + HexFormat(node, 8));
        }

        static Object staticLockObj = new Object();

        /// <summary>Initializes the factory.</summary>
        /// <param name="obj">
        /// is used to determine a hash code to include in the UUID generation
        /// </param>
        private static void Initialize(Object obj)
        {
            lock (staticLockObj)
            {
                try
                {
                    InetAddress inet = InetAddress.LocalHost;
                    byte[] bytes = inet.Address;
                    String hexInetAddress = HexFormat(getInt(bytes), 8);
                    String thisHashCode = HexFormat(System.IdentityHashCode(obj), 8);
                    s_midValue = hexInetAddress + thisHashCode;
                    s_seeder = new SecureRandom();
                    s_seeder.NextInt();
                }
                catch (java.net.UnknownHostException e)
                {
                    throw new Error("can not initialize the UuidGenerator generator");
                }
                s_initialized = true;
            }
        }

        private static int GetInt(byte[] abyte)
        {
            int i = 0;
            int j = 24;
            for (int k = 0; j >= 0; k++)
            {
                int l = abyte[k] & 0xff;
                i += (l << j);
                j -= 8;
            }
            return i;
        }

        private static String HexFormat(int i, int j)
        {
            String s = Integer.ToHexString(i);
            return PadHex(s, j) + s;
        }

        private static String PadHex(String str, int i)
        {
            StringBuilder buf = new StringBuilder();
            if (str.Length() < i)
            {
                for (int j = 0; j < (i - str.Length()); j++)
                {
                    buf.Append('0');
                }
            }
            return buf.ToString();
        }
    }
} // End of namespace
