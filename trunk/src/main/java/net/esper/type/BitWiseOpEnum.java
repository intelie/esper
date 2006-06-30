package net.esper.type;

import net.esper.collection.MultiKey;

import java.util.Map;
import java.util.HashMap;

/**
 * Enum representing relational types of operation.
 */
public enum BitWiseOpEnum
{
	   /**
     * Bitwise and.
     */
    BAND ("&"),
    /**
     * Bitwise or.
     */
    BOR ("|"),
    /**
     * Bitwise xor.
     */
    BXOR ("^");

    private static Map<MultiKey<Object>, BitWiseOpEnum.Computer> computers;

    private String expressionText;

    private BitWiseOpEnum(String expressionText)
    {
        this.expressionText = expressionText;
    }

    static
    {
        computers = new HashMap<MultiKey<Object>, BitWiseOpEnum.Computer>();
        computers.put(new MultiKey<Object>(new Object[] {Byte.class, BAND}), new BAndByte());
        computers.put(new MultiKey<Object>(new Object[] {Short.class, BAND}), new BAndShort());
        computers.put(new MultiKey<Object>(new Object[] {Integer.class, BAND}), new BAndInt());
        computers.put(new MultiKey<Object>(new Object[] {Long.class, BAND}), new BAndLong());
        computers.put(new MultiKey<Object>(new Object[] {Boolean.class, BAND}), new BAndBoolean());
        computers.put(new MultiKey<Object>(new Object[] {Byte.class, BOR}), new BOrByte());
        computers.put(new MultiKey<Object>(new Object[] {Short.class, BOR}), new BOrShort());
        computers.put(new MultiKey<Object>(new Object[] {Integer.class, BOR}), new BOrInt());
        computers.put(new MultiKey<Object>(new Object[] {Long.class, BOR}), new BOrLong());
        computers.put(new MultiKey<Object>(new Object[] {Boolean.class, BOR}), new BOrBoolean());
        computers.put(new MultiKey<Object>(new Object[] {Byte.class, BXOR}), new BXorByte());
        computers.put(new MultiKey<Object>(new Object[] {Short.class, BXOR}), new BXorShort());
        computers.put(new MultiKey<Object>(new Object[] {Integer.class, BXOR}), new BXorInt());
        computers.put(new MultiKey<Object>(new Object[] {Long.class, BXOR}), new BXorLong());
        computers.put(new MultiKey<Object>(new Object[] {Boolean.class, BXOR}), new BXorBoolean());        
        
        
    }

    /**
     * Returns number or boolean computation for the target coercion type.
     * @param coercedType - target type
     * @return number cruncher
     */
    public Computer getComputer(Class coercedType)
    {
        if ( (coercedType != Byte.class) &&
             (coercedType != Short.class) &&
             (coercedType != Integer.class) &&              
             (coercedType != Long.class) &&
             (coercedType != Boolean.class))
        {
            throw new IllegalArgumentException("Expected base numeric or boolean type for computation result but got type " + coercedType);
        }
        MultiKey<Object> key = new MultiKey<Object>(new Object[] {coercedType, this});
        return computers.get(key);
    }

    /**
     * Computer for relational op.
     */
    public interface Computer
    {
        /**
         * Computes using the 2 numbers or boolean a result object.
         * @param d1 is the first number or boolean
         * @param d2 is the second number or boolean
         * @return result
         */
    	
        public Object compute(Object objOne, Object objTwo);
    }

    /**
     * Computer for type-specific arith. operations.
     */
    /**
     * Bit Wise And.
     */
    public static class BAndByte implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Byte n1 = (Byte) objOne;
        	Byte n2 = (Byte) objTwo;
            Byte result = (byte) (n1.byteValue() & n2.byteValue());
            return result;
        }
    }
    /**
     * Bit Wise Or.
     */    
    public static class BOrByte implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Byte n1 = (Byte) objOne;
        	Byte n2 = (Byte) objTwo;
            Byte result = (byte) (n1.byteValue() | n2.byteValue());
            return result;
        }
    }
    /**
     * Bit Wise Xor.
     */        
    public static class BXorByte implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Byte n1 = (Byte) objOne;
        	Byte n2 = (Byte) objTwo;
            Byte result = (byte) (n1.byteValue() ^ n2.byteValue());
            return result;
        }
    }    
    
    /**
     * Computer for type-specific arith. operations.
     */
    /**
     * Bit Wise And.
     */    
    public static class BAndShort implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Short n1 = (Short) objOne;
        	Short n2 = (Short) objTwo;
            Short result = (short) (n1.shortValue() & n2.shortValue());
            return result;
        }
    }    
    /**
     * Bit Wise Or.
     */    
    public static class BOrShort implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Short n1 = (Short) objOne;
        	Short n2 = (Short) objTwo;
            Short result = (short) (n1.shortValue() | n2.shortValue());
            return result;
        }
    }    
    /**
     * Bit Wise Xor.
     */        
    public static class BXorShort implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Short n1 = (Short) objOne;
        	Short n2 = (Short) objTwo;
            Short result = (short) (n1.shortValue() ^ n2.shortValue());
            return result;
        }
    }    

    /**
     * Computer for type-specific arith. operations.
     */
    /**
     * Bit Wise And.
     */            
    public static class BAndInt implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Integer n1 = (Integer) objOne;
        	Integer n2 = (Integer) objTwo;
            Integer result = n1.intValue() & n2.intValue();
            return result;
        }
    }
    /**
     * Bit Wise Or.
     */            
    public static class BOrInt implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Integer n1 = (Integer) objOne;
        	Integer n2 = (Integer) objTwo;
        	Integer result = n1.intValue() | n2.intValue();
            return result;
        }
    }
    /**
     * Bit Wise Xor.
     */            
    public static class BXorInt implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Integer n1 = (Integer) objOne;
        	Integer n2 = (Integer) objTwo;
        	Integer result = n1.intValue() ^ n2.intValue();
            return result;
        }
    }
    
    /**
     * Computer for type-specific arith. operations.
     */
    /**
     * Bit Wise And.
     */        
    public static class BAndLong implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Long n1 = (Long) objOne;
        	Long n2 = (Long) objTwo;
            Long result = n1.longValue() & n2.longValue();
            return result;
        }
    }
    /**
     * Bit Wise Or.
     */    
    public static class BOrLong implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Long n1 = (Long) objOne;
        	Long n2 = (Long) objTwo;
        	Long result = n1.longValue() | n2.longValue();
            return result;
        }
    }
    /**
     * Bit Wise Xor.
     */    
    public static class BXorLong implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Long n1 = (Long) objOne;
        	Long n2 = (Long) objTwo;
        	Long result = n1.longValue() ^ n2.longValue();
            return result;
        }
    }

    /**
     * Computer for type-specific arith. operations.
     */
    /**
     * Bit Wise And.
     */            
    public static class BAndBoolean implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Boolean b1 = (Boolean) objOne;
        	Boolean b2 = (Boolean) objTwo;
        	Boolean result = b1.booleanValue() & b2.booleanValue();
            return result;
        }
    }
    /**
     * Bit Wise Or.
     */            
    public static class BOrBoolean implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Boolean b1 = (Boolean) objOne;
        	Boolean b2 = (Boolean) objTwo;
        	Boolean result = b1.booleanValue() | b2.booleanValue();
            return result;
        }
    }                
    /**
     * Bit Wise Xor.
     */            
    public static class BXorBoolean implements Computer
    {
        public Object compute(Object objOne, Object objTwo)
        {
        	Boolean b1 = (Boolean) objOne;
        	Boolean b2 = (Boolean) objTwo;
        	Boolean result = b1.booleanValue() ^ b2.booleanValue();
            return result;
        }
    }                
    
    /**
     * Returns string rendering of enum.
     * @return bitwise operator string
     */
    public String getComputeDescription()
    {
    	return expressionText;
    }
}
