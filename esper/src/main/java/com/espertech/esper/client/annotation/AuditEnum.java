package com.espertech.esper.client.annotation;

import com.espertech.esper.epl.annotation.AnnotationUtil;

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
    PROPERTY("PROPERTY"),

    /**
     * For use with expression audit.
     */
    EXPRESSION("EXPRESSION"),

    /**
     * For use with expression audit.
     */
    EXPRESSION_NESTED("EXPRESSION-NESTED"),

    /**
     * For use with expression-definition audit.
     */
    EXPRDEF("EXPRDEF"),

    /**
     * For use with view audit.
     */
    VIEW("VIEW"),

    /**
     * For use with pattern audit.
     */
    PATTERN("PATTERN"),

    /**
     * For use with pattern audit.
     */
    PATTERNINSTANCES("PATTERN-INSTANCES"),

    /**
     * For use with stream-audit.
     */
    STREAM("STREAM"),

    /**
     * For use with stream-audit.
     */
    SCHEDULE("SCHEDULE");

    private final String value;

    private AuditEnum(String value)
    {
        this.value = value.toUpperCase();
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
}