using System;
namespace net.esper.view
{
	
	/// <summary>
	/// Enumerates the valid values for each view's public fields. The name of the field or property can be used
	/// to obtain values from the view rather than using the hardcoded String value for the field.
	/// </summary>
    public class ViewFieldEnum
    {
        /// <summary> Count.</summary>
        public static readonly ViewFieldEnum UNIVARIATE_STATISTICS__COUNT = new ViewFieldEnum("count");

        /// <summary> Sum.</summary>
        public static readonly ViewFieldEnum UNIVARIATE_STATISTICS__SUM = new ViewFieldEnum("sum");

        /// <summary> Average.</summary>
        public static readonly ViewFieldEnum UNIVARIATE_STATISTICS__AVERAGE = new ViewFieldEnum("average");

        /// <summary> Standard dev population.</summary>
        public static readonly ViewFieldEnum UNIVARIATE_STATISTICS__STDDEVPA = new ViewFieldEnum("stddevpa");

        /// <summary> Standard dev.</summary>
        public static readonly ViewFieldEnum UNIVARIATE_STATISTICS__STDDEV = new ViewFieldEnum("stddev");

        /// <summary> Variance.</summary>
        public static readonly ViewFieldEnum UNIVARIATE_STATISTICS__VARIANCE = new ViewFieldEnum("variance");

        /// <summary> Weighted average.</summary>
        public static readonly ViewFieldEnum WEIGHTED_AVERAGE__AVERAGE = new ViewFieldEnum("average");

        /// <summary> Correlation.</summary>
        public static readonly ViewFieldEnum CORRELATION__CORRELATION = new ViewFieldEnum("correlation");

        /// <summary> Slope.</summary>
        public static readonly ViewFieldEnum REGRESSION__SLOPE = new ViewFieldEnum("slope");

        /// <summary> Y-intercept.</summary>
        public static readonly ViewFieldEnum REGRESSION__YINTERCEPT = new ViewFieldEnum("YIntercept");

        /// <summary> Size.</summary>
        public static readonly ViewFieldEnum SIZE_VIEW__SIZE = new ViewFieldEnum("size");

        /// <summary> Cube.</summary>
        public static readonly ViewFieldEnum MULTIDIM_OLAP__CUBE = new ViewFieldEnum("cube");

        /// <summary> Measures in an OLAP cube.</summary>
        public static readonly String[] MULTIDIM_OLAP__MEASURES =
	    { 
		    ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.Name, 
		    ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.Name, 
		    ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.Name, 
		    ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.Name, 
		    ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.Name, 
		    ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.Name
	    };

        static ViewFieldEnum()
        {
        	Array.Sort( MULTIDIM_OLAP__MEASURES ) ;
        }

        private readonly String name;

        ViewFieldEnum(String name)
        {
            this.name = name;
        }

        /// <summary> Returns the field name of fields that contain data within a view's posted objects.</summary>
        /// <returns> field name for use with DataSchema to obtain values out of objects.
        /// </returns>
        public String Name
        {
            get { return name ; }
        }
    }
}
