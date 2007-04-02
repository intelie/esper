using System;
using net.esper.view.std;
using net.esper.view.ext;
using net.esper.view.window;
using net.esper.view.stat;

namespace net.esper.view
{
    /// <summary>
    /// Enum for all build-in views.
    /// </summary>

    public class ViewEnum
    {
        /// <summary> Length window.</summary>
        public static readonly ViewEnum LENGTH_WINDOW = new ViewEnum("win", "length", typeof(LengthWindowView), true, null);

        /// <summary> Time window.</summary>
        public static readonly ViewEnum TIME_WINDOW = new ViewEnum("win", "time", typeof(TimeWindowView), true, null);

        /// <summary> Time batch.</summary>
        public static readonly ViewEnum TIME_BATCH = new ViewEnum("win", "time_batch", typeof(TimeBatchView), true, null);

        /// <summary> Externally timed window.</summary>
        public static readonly ViewEnum EXT_TIMED_WINDOW = new ViewEnum("win", "ext_timed", typeof(ExternallyTimedWindowView), true, null);

        /// <summary> Size view.</summary>
        public static readonly ViewEnum SIZE = new ViewEnum("std", "size", typeof(SizeView), false, null);

        /// <summary> Last event.</summary>
        public static readonly ViewEnum LAST_EVENT = new ViewEnum("std", "lastevent", typeof(LastElementView), false, null);

        /// <summary> Unique.</summary>
        public static readonly ViewEnum UNIQUE_BY_PROPERTY = new ViewEnum("std", "unique", typeof(UniqueByPropertyView), true, null);

        /// <summary> Group-by "merge".</summary>
        public static readonly ViewEnum GROUP_MERGE = new ViewEnum("std", "merge", typeof(MergeView), true, null);

        /// <summary> Group-by.</summary>
        public static readonly ViewEnum GROUP_PROPERTY = new ViewEnum("std", "groupby", typeof(GroupByView), true, GROUP_MERGE);

        /// <summary> Univariate statistics.</summary>
        public static readonly ViewEnum UNIVARIATE_STATISTICS = new ViewEnum("stat", "uni", typeof(UnivariateStatisticsView), true, null);

        /// <summary> Weighted avg.</summary>
        public static readonly ViewEnum WEIGHTED_AVERAGE = new ViewEnum("stat", "weighted_avg", typeof(WeightedAverageView), true, null);

        /// <summary> Correlation.</summary>
        public static readonly ViewEnum CORRELATION = new ViewEnum("stat", "correl", typeof(CorrelationView), true, null);

        /// <summary> Linest.</summary>
        public static readonly ViewEnum REGRESSION_LINEST = new ViewEnum("stat", "linest", typeof(RegressionLinestView), true, null);

        /// <summary> Cubes.</summary>
        public static readonly ViewEnum MULTIDIM_VIEW = new ViewEnum("stat", "cube", typeof(MultiDimStatsView), true, null);

        /// <summary> Sorted window.</summary>
        public static readonly ViewEnum SORT_WINDOW = new ViewEnum("ext", "sort", typeof(SortWindowView), true, null);

        /// <summary>
        /// All of the "values" in the pseudo-enum ViewEnum.
        /// </summary>

        public static readonly ViewEnum[] Values = new ViewEnum[]
        {
            LENGTH_WINDOW,
            TIME_WINDOW,
            TIME_BATCH,
            EXT_TIMED_WINDOW,
            SIZE,
            LAST_EVENT,
            UNIQUE_BY_PROPERTY,
            GROUP_MERGE,
            GROUP_PROPERTY,
            UNIVARIATE_STATISTICS,
            WEIGHTED_AVERAGE,
            CORRELATION,
            REGRESSION_LINEST,
            MULTIDIM_VIEW,
            SORT_WINDOW,
        };

        private readonly String nspace;
        private readonly String name;
        private readonly Type clazz;
        private readonly Boolean isRequiresParameters;
        private readonly ViewEnum mergeView;

        ViewEnum(String nspace, String name, Type clazz, Boolean isRequiresParameters, ViewEnum mergeView)
        {
            this.nspace = nspace;
            this.name = name;
            this.clazz = clazz;
            this.isRequiresParameters = isRequiresParameters;
            this.mergeView = mergeView;
        }

        /// <summary> Returns namespace that the object belongs to.</summary>
        /// <returns> namespace
        /// </returns>

        public String Namespace
        {
            get { return nspace; }
        }

        /// <summary> Returns name of the view that can be used to reference the view in a view expression.</summary>
        /// <returns> short name of view
        /// </returns>

        public String Name
        {
            get { return name; }
        }

        /// <summary> Gets the implementation class underlying the view.</summary>
        /// <returns> view implementation class
        /// </returns>

        public Type Clazz
        {
            get { return clazz; }
        }

        /// <summary> Returns true if the view requires one or more parameters, false if the view doesn't require any parameters.</summary>
        /// <returns> true if at least one parameter is mandatory, false if there are no mandatory parameters
        /// </returns>

        public Boolean IsRequiresParameters
        {
            get { return isRequiresParameters; }
        }

        /// <summary> Returns the enumeration value of the view for merging the data generated by another view.</summary>
        /// <returns> view enum for the merge view
        /// </returns>

        public ViewEnum MergeView
        {
            get { return mergeView; }
        }

        /// <summary>
        /// Returns the view enumeration value given the name of the view.
        /// </summary>
        /// <param name="nspace">The nspace.</param>
        /// <param name="name">is the short name of the view as used in view expressions</param>
        /// <returns>
        /// view enumeration value, or null if no such view name is among the enumerated values
        /// </returns>

        public static ViewEnum ForName(String nspace, String name)
        {
            foreach (ViewEnum viewEnum in ViewEnum.Values)
            {
                if ((viewEnum.Namespace.Equals(nspace)) && (viewEnum.Name.Equals(name)))
                {
                    return viewEnum;
                }
            }

            return null;
        }
    }
}