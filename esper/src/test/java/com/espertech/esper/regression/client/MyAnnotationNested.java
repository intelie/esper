package com.espertech.esper.regression.client;

public @interface MyAnnotationNested
{
    MyAnnotationNestableSimple nestableSimple();
    MyAnnotationNestableValues nestableValues();
    MyAnnotationNestableNestable nestableNestable();
}
