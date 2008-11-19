package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.parse.EPLTreeWalker;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.type.TimePeriodParameter;
import com.espertech.esper.util.JavaClassHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ExprTimePeriod extends ExprNode
{
    private static final Log log = LogFactory.getLog(EPLTreeWalker.class);

    private ExprNode day;
    private ExprNode hour;
    private ExprNode minute;
    private ExprNode second;
    private ExprNode millisecond;

    public ExprTimePeriod(ExprNode day, ExprNode hour, ExprNode minute, ExprNode second, ExprNode millisecond)
    {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millisecond = millisecond;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        validate(millisecond);
        validate(second);
        validate(minute);
        validate(hour);
        validate(day);
    }

    private void validate(ExprNode expression) throws ExprValidationException
    {
        if (expression == null)
        {
            return;
        }
        Class returnType = expression.getType();
        if (!JavaClassHelper.isNumeric(returnType))
        {
            throw new ExprValidationException("Time period expression requires a numeric parameter type");
        }
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        double seconds = 0;
        if (millisecond != null)
        {
            double value = eval(millisecond);
            if (value != 0)
            {
                seconds += eval(millisecond) / 1000d;
            }
        }
        if (second != null)
        {
            seconds += eval(second);
        }
        if (minute != null)
        {
            seconds += eval(minute) * 60;
        }
        if (hour != null)
        {
            seconds += eval(hour) * 60 * 60;
        }
        if (day != null)
        {
            seconds += eval(day) * 24 * 60 * 60;
        }
        return new TimePeriodParameter(seconds);
    }

    public Class getType()
    {
        return TimePeriodParameter.class;
    }

    public boolean isConstantResult()
    {
        boolean result = true;
        result &= ((millisecond != null) ? millisecond.isConstantResult() : true);
        result &= ((second != null) ? second.isConstantResult() : true);
        result &= ((minute != null) ? minute.isConstantResult() : true);
        result &= ((hour != null) ? hour.isConstantResult() : true);
        result &= ((day != null) ? day.isConstantResult() : true);
        return result;
    }

    public String toExpressionString()
    {
        StringBuffer buf = new StringBuffer();
        if (day != null)
        {
            buf.append(day.toExpressionString());
            buf.append(" days ");
        }
        if (hour != null)
        {
            buf.append(hour.toExpressionString());
            buf.append(" hours ");
        }
        if (minute != null)
        {
            buf.append(minute.toExpressionString());
            buf.append(" minutes ");
        }
        if (second != null)
        {
            buf.append(second.toExpressionString());
            buf.append(" seconds ");
        }
        if (millisecond != null)
        {
            buf.append(millisecond.toExpressionString());
            buf.append(" milliseconds ");
        }
        return buf.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprTimePeriod))
        {
            return false;
        }
        ExprTimePeriod other = (ExprTimePeriod) node;
        boolean result = true;
        result &= compare(millisecond, other.millisecond);
        result &= compare(second, other.second);
        result &= compare(minute, other.minute);
        result &= compare(hour, other.hour);
        result &= compare(day, other.day);
        return result;
    }

    private boolean compare(ExprNode one, ExprNode two)
    {
        if ((one == null) && (two == null))
        {
            return true;
        }
        if ((one != null) && (two == null))
        {
            return false;
        }
        if (one != null)
        {
            return one.equalsNode(two);
        }
        return false;
    }

    private double eval(ExprNode expr)
    {
        if (expr == null)
        {
            return 0;
        }
        Object value = expr.evaluate(null, true);
        if (value == null)
        {
            log.warn("Time period expression returned a null value for expression '" + expr.toExpressionString() + "'");
            return 0;
        }
        if (value instanceof BigDecimal)
        {
            return ((BigDecimal) value).doubleValue();
        }
        if (value instanceof BigInteger)
        {
            return ((BigInteger) value).doubleValue();
        }
        return ((Number) value).doubleValue();
    }

    public ExprNode getMillisecond()
    {
        return millisecond;
    }

    public ExprNode getSecond()
    {
        return second;
    }

    public ExprNode getMinute()
    {
        return minute;
    }

    public ExprNode getHour()
    {
        return hour;
    }

    public ExprNode getDay()
    {
        return day;
    }
}
