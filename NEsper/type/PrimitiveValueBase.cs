using System;

namespace net.esper.type
{
	/// <summary> Abstract class for literal values supplied in an event expression string and prepared expression values supplied
	/// by set methods.
	/// </summary>
    public abstract class PrimitiveValueBase : PrimitiveValue
    {
        virtual public bool _Boolean
        {
            set { throw new NotSupportedException(); }
        }

        virtual public sbyte _Byte
        {
            set { throw new NotSupportedException(); }
        }

        virtual public float _Float
        {
            set { throw new NotSupportedException(); }
        }

        virtual public int _Int
        {
            set { throw new NotSupportedException(); }
        }

        virtual public short _Short
        {
            set { throw new NotSupportedException(); }
        }

        virtual public String _String
        {
            set { throw new NotSupportedException(); }
        }

        virtual public Double _Double
        {
            set { throw new NotSupportedException(); }
        }

        virtual public long _Long
        {
            set { throw new NotSupportedException(); }
        }
        
        public abstract PrimitiveValueType Type { get;}
        public abstract Object ValueObject { get;}
        public virtual void Parse(String[] values)
        {
            Parse(values[0]);
        }

        public abstract void Parse(String param1);
    }
}