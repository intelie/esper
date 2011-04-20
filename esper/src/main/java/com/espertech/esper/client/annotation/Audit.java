package com.espertech.esper.client.annotation;

/**
 * Annotation for use in EPL statements to add a debug.
 */
public @interface Audit
{
    /**
     * Comma-separated list of keywords (not case-sentitive), see {@link AuditEnum} for a list of keywords.
     * @return comma-separated list of audit keywords
     */
    String value() default "*";
}
