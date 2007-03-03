/*
 * Created on Apr 22, 2006
 *
 */

using System;
using System.Security.Cryptography;

namespace net.esper.example.transaction.sim
{
    /** Just so we can swap between Random and SecureRandom.
     * 
     * @author Hans Gilde
     *
     */
    public class RandomUtil
    {
        public static Random GetNewInstance()
        {
            return new Random();
        }
    }
}