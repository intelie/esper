using System;

namespace net.esper.example.matchmaker.eventbean
{
    public class MobileUserBean
    {
        private int userId;
        private double locationX;
        private double locationY;
        private Gender myGender;
        private HairColor myHairColor;
        private AgeRange myAgeRange;
        private Gender preferredGender;
        private HairColor preferredHairColor;
        private AgeRange preferredAgeRange;

        public MobileUserBean(int userId, double locationX, double locationY, Gender myGender, HairColor myHairColor, AgeRange myAgeRange, Gender preferredGender, HairColor preferredHairColor, AgeRange preferredAgeRange)
        {
            this.userId = userId;
            this.locationX = locationX;
            this.locationY = locationY;
            this.myGender = myGender;
            this.myHairColor = myHairColor;
            this.myAgeRange = myAgeRange;
            this.preferredGender = preferredGender;
            this.preferredHairColor = preferredHairColor;
            this.preferredAgeRange = preferredAgeRange;
        }

        public int UserId
        {
            get { return userId; }
        }

        public double LocationX
        {
            get { return locationX; }
        }

        public double LocationY
        {
            get { return locationY; }
            set { locationY = value; }
        }

        public void SetLocation(double locationX, double locationY)
        {
            this.locationX = locationX;
            this.locationY = locationY;
        }

        public String MyGender
        {
            get { return myGender.ToString(); }
        }

        public String MyHairColor
        {
            get { return myHairColor.ToString(); }
        }

        public String MyAgeRange
        {
            get { return myAgeRange.ToString(); }
        }

        public String PreferredGender
        {
            get { return preferredGender.ToString(); }
        }

        public String PreferredHairColor
        {
            get { return preferredHairColor.ToString(); }
        }

        public String PreferredAgeRange
        {
            get { return preferredAgeRange.ToString(); }
        }
    }
}