/*
 * Created on Apr 23, 2006
 *
 */
using System;
using System.Collections.Generic;
using System.Security.Cryptography;

using net.esper.compat;

namespace net.esper.example.transaction.sim
{
    /** Utils that generate random entries for various fields.
     * @author Hans Gilde
     *
     */
    public class FieldGenerator
    {
        private readonly Random random = RandomUtil.GetNewInstance();

        public static readonly IList<string> CUSTOMERS;
        public static readonly IList<string> SUPPLIERS;

        static FieldGenerator()
        {
            do
            {
                List<string> l = new List<string>();
                l.Add("RED");
                l.Add("ORANGE");
                l.Add("YELLOW");
                l.Add("GREEN");
                l.Add("BLUE");
                l.Add("INDIGO");
                l.Add("VIOLET");
                CUSTOMERS = new ReadOnlyList<string>(l);
            } while (false);

            do
            {
                List<string> l = new List<string>();
                l.Add("WASHINGTON");
                l.Add("ADAMS");
                l.Add("JEFFERSON");
                l.Add("MADISON");
                l.Add("MONROE");
                SUPPLIERS = new ReadOnlyList<string>(l);
            } while (false);
        }

        public String GetRandomCustomer()
        {
            return CUSTOMERS[random.Next(CUSTOMERS.Count - 1)];
        }

        public String GetRandomSupplier()
        {
            return SUPPLIERS[random.Next(SUPPLIERS.Count - 1)];
        }

        public long randomLatency(long time)
        {
            return time + random.Next(1000);
        }
    }
}
