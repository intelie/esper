namespace net.esper.client
{
    public enum PropertyResolutionStyle
    {
        /// <summary>
        /// Properties are only matched if the names are identical in name
        /// and case to the original property name.
        /// </summary>

        CASE_SENSITIVE,

        /// <summary>
        /// Properties are matched if the names are identical.  A case insensitive
        /// search is used and will choose the first property that matches
        /// the name exactly or the first property that matches case insensitively
        /// should no match be found.
        /// </summary>

        CASE_INSENSITIVE,

        /// <summary>
        /// Properties are matched if the names are identical.  A case insensitive
        /// search is used and will choose the first property that matches
        /// the name exactly case insensitively.  If more than one 'name' can be
        /// mapped to the property an exception is thrown.
        /// </summary>
        
        DISTINCT_CASE_INSENSITIVE
    }
}
