package com.espertech.esper.client.annotation;

import java.lang.annotation.Annotation;

/**
 * Enumeration of audit values. Since audits may be a comma-separate list in a single @Audit annotation
 * they are listed as enumeration values here.
 */
public enum AuditEnum
{
    /**
     * For use with property audit.
     */
    PROPERTY("PROPERTY", false, false),

    /**
     * For use with expression audit.
     */
    EXPR("EXPR", false, false)
    ;

    private final String value;
    private final boolean acceptsParameters;
    private final boolean requiresParameters;

    private AuditEnum(String value, boolean acceptsParameters, boolean requiresParameters)
    {
        this.value = value.toUpperCase();
        this.acceptsParameters = acceptsParameters;
        if (acceptsParameters)
        {
            this.requiresParameters = true;
        }
        else
        {
            this.requiresParameters = requiresParameters;
        }
    }

    /**
     * Returns the constant.
     * @return constant
     */
    public String getValue()
    {
        return value;
    }

    /**
     * True if the hint accepts params.
     * @return indicator
     */
    public boolean isAcceptsParameters()
    {
        return acceptsParameters;
    }

    /**
     * True if the hint requires params.
     * @return indicator
     */
    public boolean isRequiresParameters()
    {
        return requiresParameters;
    }

    /**
     * Check if the hint is present in the annotations provided.
     * @param annotations the annotations to inspect
     * @return indicator
     */
    public Audit getAudit(Annotation[] annotations)
    {
        if (annotations == null)
        {
            return null;
        }

        for (Annotation annotation : annotations)
        {
            if (!(annotation instanceof Audit))
            {
                continue;
            }

            Audit auditAnnotation = (Audit) annotation;
            String auditAnnoValue = auditAnnotation.value();
            if (auditAnnoValue.equals("*")) {
                return auditAnnotation;
            }

            boolean isListed = AnnotationUtil.isListed(auditAnnoValue, value);
            if (isListed) {
                return auditAnnotation;
            }
        }
        return null;
    }

    /**
     * Returns null or value assigned.
     * @param auditAnnotation annotation
     * @return value
     */
    public String getAuditAssignedValue(Audit auditAnnotation)
    {
        if (!acceptsParameters)
        {
            return null;
        }
        return AnnotationUtil.getAssignedValue(auditAnnotation.value(), value);
    }
}