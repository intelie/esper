using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.type;
using net.esper.view;

namespace net.esper.support.view
{

    /// <summary>
    /// Convenience class for making view specifications from class and string arrays.
    /// </summary>

    public class SupportViewSpecFactory
    {
        public static IList<ViewSpec> makeSpecListOne()
        {
            IList<ViewSpec> specifications = new List<ViewSpec>();

            ViewSpec specOne = makeSpec("win", "length",
                new Type[] { typeof(int) }, new String[] { "1000" });
            ViewSpec specTwo = makeSpec("stat", "uni",
                new Type[] { typeof(string) }, new String[] { "\"price\"" });
            ViewSpec specThree = makeSpec("std", "lastevent", null, null);

            specifications.Add(specOne);
            specifications.Add(specTwo);
            specifications.Add(specThree);

            return specifications;
        }

        public static IList<ViewSpec> makeSpecListTwo()
        {
            IList<ViewSpec> specifications = new List<ViewSpec>();

            ViewSpec specOne = makeSpec("std", "groupby",
                    new Type[] { typeof(string) }, new String[] { "\"symbol\"" });

            specifications.Add(specOne);

            return specifications;
        }

        public static IList<ViewSpec> makeSpecListThree()
        {
            IList<ViewSpec> specifications = new List<ViewSpec>();

            ViewSpec specOne = SupportViewSpecFactory.makeSpec("win", "length",
                    new Type[] { typeof(int) }, new String[] { "1000" });
            ViewSpec specTwo = SupportViewSpecFactory.makeSpec("std", "unique",
                    new Type[] { typeof(string) }, new String[] { "\"symbol\"" });

            specifications.Add(specOne);
            specifications.Add(specTwo);

            return specifications;
        }

        public static IList<ViewSpec> makeSpecListFour()
        {
            IList<ViewSpec> specifications = new List<ViewSpec>();

            ViewSpec specOne = SupportViewSpecFactory.makeSpec("win", "length",
                    new Type[] { typeof(int) }, new String[] { "1000" });
            ViewSpec specTwo = SupportViewSpecFactory.makeSpec("stat", "uni",
                    new Type[] { typeof(string) }, new String[] { "\"price\"" });
            ViewSpec specThree = SupportViewSpecFactory.makeSpec("std", "size", null, null);

            specifications.Add(specOne);
            specifications.Add(specTwo);
            specifications.Add(specThree);

            return specifications;
        }

        public static IList<ViewSpec> makeSpecListFive()
        {
            IList<ViewSpec> specifications = new List<ViewSpec>();

            ViewSpec specOne = makeSpec("win", "time",
                    new Type[] { typeof(int) }, new String[] { "10000" });
            specifications.Add(specOne);

            return specifications;
        }

        public static ViewSpec makeSpec(String _namespace, String viewName, Type[] paramTypes, String[] paramValues)
        {
            return new ViewSpec(_namespace, viewName, makeParams(paramTypes, paramValues));
        }

        private static List<Object> makeParams(Type[] clazz, String[] values)
        {
            List<Object> _params = new List<Object>();

            if (clazz == null)
            {
                return _params;
            }

            for (int i = 0; i < clazz.Length; i++)
            {
                PrimitiveValue placeholder = PrimitiveValueFactory.create(clazz[i]);
                placeholder.Parse(values[i]);
                Object value = placeholder.ValueObject;
                _params.Add(value);
            }

            return _params;
        }
    }
}
