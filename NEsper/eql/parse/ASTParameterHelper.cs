using System;
using System.Collections.Generic;

using antlr.collections;

using net.esper.compat;
using net.esper.eql.generated;
using net.esper.type;

using org.apache.commons.logging;

namespace net.esper.eql.parse
{
    /// <summary> Parse AST parameter nodes including constants, arrays, lists.
    /// Distinguishes between uniform and non-uniform arrays.
    /// </summary>
    public class ASTParameterHelper : EqlEvalTokenTypes
    {

        /// <summary> Returns the parse Object for the parameter/constant AST node whose text to parse.</summary>
        /// <param name="parameterNode">- AST node to parse
        /// </param>
        /// <returns> object value
        /// </returns>
        /// <throws>  ASTWalkException is thrown to indicate a parse error </throws>
        public static Object makeParameter(AST parameterNode)
        {
            return parseConstant(parameterNode);
        }

        private static Object parseConstant(AST node)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".parseConstant Node type=" + node.Type + " text=" + node.getText());
            }

            switch (node.Type)
            {

                case EqlEvalTokenTypes.NUM_INT:
                case EqlEvalTokenTypes.INT_TYPE:
                case EqlEvalTokenTypes.LONG_TYPE:
                case EqlEvalTokenTypes.BOOL_TYPE:
                case EqlEvalTokenTypes.FLOAT_TYPE:
                case EqlEvalTokenTypes.DOUBLE_TYPE:
                case EqlEvalTokenTypes.STRING_TYPE: return ASTConstantHelper.parse(node);

                case EqlEvalTokenTypes.NUMERIC_PARAM_FREQUENCY: return makeFrequency(node);

                case EqlEvalTokenTypes.NUMERIC_PARAM_RANGE: return makeRange(node);

                case EqlEvalTokenTypes.STAR: return new WildcardParameter();

                case EqlEvalTokenTypes.NUMERIC_PARAM_LIST: return makeList(node);

                case EqlEvalTokenTypes.ARRAY_PARAM_LIST: return makeArray(node);

                case EqlEvalTokenTypes.TIME_PERIOD: return makeTimePeriod(node);

                default:
                    throw new ASTWalkException("Unexpected constant of type " + node.Type + " encountered");

            }
        }

        private static TimePeriodParameter makeTimePeriod(AST node)
        {
            AST child = node.getFirstChild();
            double result = 0;

            while (child != null)
            {
                ValueType numValue = (ValueType)parseConstant(child.getFirstChild());
                double partValue = Convert.ToDouble(numValue);

                switch (child.Type)
                {

                    case EqlEvalTokenTypes.MILLISECOND_PART:
                        result += partValue / 1000d;
                        break;

                    case EqlEvalTokenTypes.SECOND_PART:
                        result += partValue;
                        break;

                    case EqlEvalTokenTypes.MINUTE_PART:
                        result += 60 * partValue;
                        break;

                    case EqlEvalTokenTypes.HOUR_PART:
                        result += 60 * 60 * partValue;
                        break;

                    case EqlEvalTokenTypes.DAY_PART:
                        result += 24 * 60 * 60 * partValue;
                        break;

                    default:
                        throw new SystemException("Illegal part of interval encountered, type=" + child.Type + " text=" + child.getText());

                }

                child = child.getNextSibling();
            }

            return new TimePeriodParameter(result);
        }

        private static Object makeList(AST node)
        {
            ListParameter list = new ListParameter();

            AST child = node.getFirstChild();
            while (child != null)
            {
                Object parsedChild = parseConstant(child);

                if (parsedChild is Int32)
                {
                    list.Add(new IntParameter((Int32)parsedChild));
                }
                else
                {
                    list.Add((NumberSetParameter)parsedChild);
                }
                child = child.getNextSibling();
            }

            return list;
        }

        private static Object makeFrequency(AST node)
        {
            int frequency = IntValue.parseString(node.getFirstChild().getText());
            return new FrequencyParameter(frequency);
        }

        private static Object makeRange(AST node)
        {
            int low = IntValue.parseString(node.getFirstChild().getText());
            int high = IntValue.parseString(node.getFirstChild().getNextSibling().getText());
            return new RangeParameter(low, high);
        }

        private static Object makeArray(AST node)
        {
            // Determine the distinct node types in the AST
            ISet<Int32> nodeTypes = new EHashSet<Int32>();
            AST child = node.getFirstChild();

            while (child != null)
            {
                nodeTypes.Add(child.Type);
                child = child.getNextSibling();
            }

            if (nodeTypes.Count == 0)
            {
                return new Object[0];
            }
            else if (nodeTypes.Count == 1)
            {
                return makeUniform(node);
            }
            else
            {
                return makeNonUniform(node);
            }
        }

        private static Object makeNonUniform(AST node)
        {
            int count = node.getNumberOfChildren();
            Object[] result = new Object[count];

            AST child = node.getFirstChild();
            int index = 0;
            while (child != null)
            {
                result[index++] = parseConstant(child);
                child = child.getNextSibling();
            }

            return result;
        }

        private static Object makeUniform(AST node)
        {
            int count = node.getNumberOfChildren();
            String[] values = new String[count];

            AST child = node.getFirstChild();
            int index = 0;
            while (child != null)
            {
                values[index++] = child.getText();
                child = child.getNextSibling();
            }

            Object result = parseStringArray(node.getFirstChild().Type, values);
            return result;
        }

        private static Object parseStringArray(int nodeType, String[] nodeValues)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".parseStringArray Node type=" + nodeType + " values=" + CollectionHelper.Render(nodeValues));
            }

            switch (nodeType)
            {

                case EqlEvalTokenTypes.INT_TYPE: return IntValue.parseString(nodeValues);

                case EqlEvalTokenTypes.LONG_TYPE: return LongValue.parseString(nodeValues);

                case EqlEvalTokenTypes.BOOL_TYPE: return BoolValue.parseString(nodeValues);

                case EqlEvalTokenTypes.FLOAT_TYPE: return FloatValue.parseString(nodeValues);

                case EqlEvalTokenTypes.DOUBLE_TYPE: return DoubleValue.parseString(nodeValues);

                case EqlEvalTokenTypes.STRING_TYPE: return StringValue.parseString(nodeValues);

                default:
                    throw new SystemException("Unexpected constant of type " + nodeType + " encountered");

            }
        }

        private static readonly Log log = LogFactory.GetLog(typeof(ASTParameterHelper));
    }
}
