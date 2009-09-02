package com.espertech.esper.client.annotation;

import com.espertech.esper.epl.annotation.AnnotationException;

import java.lang.annotation.Annotation;

/**
 * Enumeration of hint values. Since hints may be a comma-separate list in a single @Hint annotation
 * they are listed as enumeration values here.
 */
public enum HintEnum
{
    /**
     * For use with match_recognize, iterate-only matching.
     */
    ITERATE_ONLY("ITERATE_ONLY"),

    /**
     * For use with group-by, reclaim groups.
     */
    DISABLE_RECLAIM_GROUP("DISABLE_RECLAIM_GROUP");

    private final String value;

    private HintEnum(String value)
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
    public boolean containedIn(Annotation[] annotations)
    {
        if (annotations == null)
        {
            return false;
        }

        for (Annotation annotation : annotations)
        {
            if (!(annotation instanceof Hint))
            {
                continue;
            }

            String hintVal = ((Hint) annotation).value();
            if (hintVal == null)
            {
                continue;
            }

            if (hintVal.trim().toUpperCase().equals(value))
            {
                return true;
            }

            String[] hints = hintVal.split(",");
            for (int i = 0; i < hints.length; i++)
            {
                if (hints[i].trim().toUpperCase().equals(value))
                {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Validate a hint annotation ensuring it contains only recognized hints.
     * @param annotation to validate
     * @throws AnnotationException if an invalid text was found
     */
    public static void validate(Annotation annotation) throws AnnotationException
    {
        if (!(annotation instanceof Hint))
        {
            return;
        }

        Hint hint = (Hint) annotation;

        for (HintEnum val : HintEnum.values())
        {
            if (val.getValue().equals(hint.value().trim().toUpperCase()))
            {
                return;
            }
        }

        String[] hints = hint.value().split(",");
        for (int i = 0; i < hints.length; i++)
        {
            boolean found = false;
            for (HintEnum val : HintEnum.values())
            {
                if (val.getValue().equals(hints[i].trim().toUpperCase()))
                {
                    found = true;
                    break;
                }
            }

            if (!found)
            {
                throw new AnnotationException("Hint annotation value '" + hints[i] + "' is not one of the known values");
            }
        }
    }
}