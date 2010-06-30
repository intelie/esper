package com.espertech.esper.regression.client;

public @interface MyAnnotationValueDefaulted
{
    public abstract String value() default "XYZ";
}
