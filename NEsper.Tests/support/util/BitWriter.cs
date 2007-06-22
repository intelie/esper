using System;
using System.Collections.Generic;
using System.Text;

namespace net.esper.support.util
{
    public class BitWriter
    {
        public static string Write(int value)
        {
            int currentBit = 1 << 32;

            StringBuilder builder = new StringBuilder();

            for( int ii = 0 ; ii < 32 ; ii++ )
            {
                builder.Append((value & currentBit) == 0 ? '0' : '1');
                currentBit >>= 1;
            }

            return builder.ToString();
        }
    }
}
