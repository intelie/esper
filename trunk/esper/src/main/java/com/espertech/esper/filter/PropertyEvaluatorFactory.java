package com.espertech.esper.filter;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.FragmentEventType;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.epl.spec.PropertyEvalAtom;
import com.espertech.esper.epl.spec.PropertyEvalSpec;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;

import java.util.ArrayList;
import java.util.List;

public class PropertyEvaluatorFactory
{
    public static PropertyEvaluator makeEvaluator(PropertyEvalSpec spec,
                                                  EventType sourceEventType,
                                                  String optionalSourceStreamName,
                                                  MethodResolutionService methodResolutionService,
                                                  TimeProvider timeProvider,
                                                  VariableService variableService,
                                                  String engineURI)
            throws ExprValidationException
    {
        int length = spec.getAtoms().size();
        EventPropertyGetter[] getters = new EventPropertyGetter[length];
        FragmentEventType types[] = new FragmentEventType[length];
        EventType currentEventType = sourceEventType;
        ExprNode whereClauses[] = new ExprNode[length];

        List<EventType> streamEventTypes = new ArrayList<EventType>();
        streamEventTypes.add(sourceEventType);
        List<String> streamNames = new ArrayList<String>();
        streamNames.add(optionalSourceStreamName);

        for (int i = 0; i < length; i++)
        {
            PropertyEvalAtom atom = spec.getAtoms().get(i);

            // obtain property info
            String propertyName = atom.getPropertyName();
            FragmentEventType fragmentEventType = currentEventType.getFragmentType(propertyName);
            if (fragmentEventType == null)
            {
                throw new ExprValidationException("Property expression '" + propertyName + "' against type '" + currentEventType.getName() + "' does not return a fragmentable property value");
            }
            EventPropertyGetter getter = currentEventType.getGetter(propertyName);
            if (getter == null)
            {
                throw new ExprValidationException("Property expression '" + propertyName + "' against type '" + currentEventType.getName() + "' does not return a fragmentable property value");
            }

            // validate where clause, if any
            streamEventTypes.add(fragmentEventType.getFragmentType());
            streamNames.add(atom.getOptionalAsName());
            if (atom.getOptionalWhereClause() != null)
            {
                EventType[] whereTypes = streamEventTypes.toArray(new EventType[streamEventTypes.size()]);
                String[] whereStreamNames = streamNames.toArray(new String[streamNames.size()]);
                String[] typeNames = new String[whereTypes.length];
                for (int j = 0; j < typeNames.length; j++)
                {
                    typeNames[j] = whereTypes[j].getName();
                }
                StreamTypeService streamTypeService = new StreamTypeServiceImpl(whereTypes, whereStreamNames, engineURI, typeNames);
                whereClauses[i] = atom.getOptionalWhereClause().getValidatedSubtree(streamTypeService, methodResolutionService, null, timeProvider, variableService);
            }

            currentEventType = fragmentEventType.getFragmentType();
            types[i] = fragmentEventType;
            getters[i] = getter;
        }

        if (length == 1)
        {
            return new PropertyEvaluatorSimple(getters[0], types[0], whereClauses[0]);
        }
        else
        {
            return new PropertyEvaluatorNested(getters, types, whereClauses);
        }
    }
}