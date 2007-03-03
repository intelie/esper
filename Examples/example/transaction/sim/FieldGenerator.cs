/*
 * Created on Apr 23, 2006
 *
 */
using System;
using System.Collections.Generic;
using System.Security.Cryptography;

namespace net.esper.example.transaction.sim
{
    /** Utils that generate random entries for various fields.
     * @author Hans Gilde
     *
     */
    public class FieldGenerator
    {
        private readonly Random random = RandomUtil.GetNewInstance();

        public static readonly IList<String> CUSTOMERS;
        public static readonly List<String> SUPPLIERS;

        static FieldGenerator()
        {
            do
            {
                List<String> l = new List<String>();
                l.Add("RED");
                l.Add("ORANGE");
                l.Add("YELLOW");
                l.Add("GREEN");
                l.Add("BLUE");
                l.Add("INDIGO");
                l.Add("VIOLET");
                CUSTOMERS = Collections.unmodifiableList(l);
            } while (false);

            do
            {
                List<String> l = new List<String>();
                l.Add("WASHINGTON");
                l.Add("ADAMS");
                l.Add("JEFFERSON");
                l.Add("MADISON");
                l.Add("MONROE");
                SUPPLIERS = Collections.unmodifiableList(l);
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