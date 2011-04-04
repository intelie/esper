package com.espertech.esper.client.annotation;

/**
 * Annotation for use in EPL statements to add a debug.
 */
public @interface Audit
{
    String value() default "*";
}
