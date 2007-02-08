using System;

namespace net.esper.util
{
    /// <summary>
    ///  Utility for performing a SQL Like comparsion.
    /// </summary>

    public class LikeUtil
    {
        virtual internal bool EquivalentToFalsePredicate
        {
            get
            {
                return isNull;
            }

        }
        virtual internal bool EquivalentToEqualsPredicate
        {
            get
            {
                return iFirstWildCard == -1;
            }

        }
        virtual internal bool EquivalentToNotNullPredicate
        {
            get
            {

                if (isNull || !hasWildcards())
                {
                    return false;
                }

                for (int i = 0; i < wildCardType.Length; i++)
                {
                    if (wildCardType[i] != LikeUtil.PERCENT_CHAR)
                    {
                        return false;
                    }
                }

                return true;
            }

        }
        virtual internal bool EquivalentToBetweenPredicate
        {
            get
            {

                return iFirstWildCard > 0 && iFirstWildCard == wildCardType.Length - 1 && cLike[iFirstWildCard] == '%';
            }

        }
        virtual internal bool EquivalentToBetweenPredicateAugmentedWithLike
        {
            get
            {
                return iFirstWildCard > 0 && cLike[iFirstWildCard] == '%';
            }

        }
        private const int UNDERSCORE_CHAR = 1;
        private const int PERCENT_CHAR = 2;

        private char[] cLike;
        private int[] wildCardType;
        private int iLen;
        private bool isIgnoreCase;
        private int iFirstWildCard;
        private bool isNull;
        private char? escapeChar;

        /// <summary> Ctor.</summary>
        /// <param name="pattern">is the SQL-like pattern to</param>
        /// <param name="escape">is the escape character</param>
        /// <param name="ignorecase">is true to ignore the case, or false if not</param>

        public LikeUtil(String pattern, char? escape, bool ignorecase)
        {
            escapeChar = escape;
            isIgnoreCase = ignorecase;
            normalize(pattern);
        }

        /// <summary> Execute the string.</summary>
        /// <param name="compareString">is the string to compare
        /// </param>
        /// <returns> true if pattern matches, or false if not
        /// </returns>

        public virtual bool? compare(String compareString)
        {
            if (compareString == null)
            {
                return null;
            }

            if (isIgnoreCase)
            {
                compareString = compareString.ToUpper();
            }

            return compareAt(compareString, 0, 0, compareString.Length) ? true : false;
        }

        /// <summary> Resets the search pattern.</summary>
        /// <param name="pattern">is the new pattern to match against
        /// </param>

        public virtual void resetPattern(String pattern)
        {
            normalize(pattern);
        }

        private bool compareAt(String s, int i, int j, int jLen)
        {

            for (; i < iLen; i++)
            {
                switch (wildCardType[i])
                {
                    case 0:  // general character
                        if ((j >= jLen) || (cLike[i] != s[j++]))
                        {
                            return false;
                        }
                        break;


                    case LikeUtil.UNDERSCORE_CHAR:  // underscore: do not test this character
                        if (j++ >= jLen)
                        {
                            return false;
                        }
                        break;


                    case LikeUtil.PERCENT_CHAR:  // percent: none or any character(s)
                        if (++i >= iLen)
                        {
                            return true;
                        }

                        while (j < jLen)
                        {
                            if ((cLike[i] == s[j]) && compareAt(s, i, j, jLen))
                            {
                                return true;
                            }

                            j++;
                        }

                        return false;
                }
            }

            if (j != jLen)
            {
                return false;
            }

            return true;
        }

        private void normalize(String pattern)
        {

            isNull = pattern == null;

            if (!isNull && isIgnoreCase)
            {
                pattern = pattern.ToUpper();
            }

            iLen = 0;
            iFirstWildCard = -1;

            int l = pattern == null ? 0 : pattern.Length;

            cLike = new char[l];
            wildCardType = new int[l];

            bool bEscaping = false, bPercent = false;

            for (int i = 0; i < l; i++)
            {
                char c = pattern[i];

                if (!bEscaping)
                {
                    if (escapeChar != null && escapeChar.Value == c)
                    {
                        bEscaping = true;

                        continue;
                    }
                    else if (c == '_')
                    {
                        wildCardType[iLen] = LikeUtil.UNDERSCORE_CHAR;

                        if (iFirstWildCard == -1)
                        {
                            iFirstWildCard = iLen;
                        }
                    }
                    else if (c == '%')
                    {
                        if (bPercent)
                        {
                            continue;
                        }

                        bPercent = true;
                        wildCardType[iLen] = LikeUtil.PERCENT_CHAR;

                        if (iFirstWildCard == -1)
                        {
                            iFirstWildCard = iLen;
                        }
                    }
                    else
                    {
                        bPercent = false;
                    }
                }
                else
                {
                    bPercent = false;
                    bEscaping = false;
                }

                cLike[iLen++] = c;
            }

            for (int i = 0; i < iLen - 1; i++)
            {
                if ((wildCardType[i] == LikeUtil.PERCENT_CHAR) && (wildCardType[i + 1] == LikeUtil.UNDERSCORE_CHAR))
                {
                    wildCardType[i] = LikeUtil.UNDERSCORE_CHAR;
                    wildCardType[i + 1] = LikeUtil.PERCENT_CHAR;
                }
            }
        }

        internal virtual bool hasWildcards()
        {
            return iFirstWildCard != -1;
        }
    }
}
