using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.example.matchmaker.eventbean;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.example.matchmaker.monitor
{
    public class MatchMakingMonitor
    {
        public const double PROXIMITY_RANGE = 1;

        private static EHashSet<int> existingUsers = new EHashSet<int>();
        private static EPServiceProvider epService;

        private int mobileUserId;
        private EPStatement locateOther;

        public MatchMakingMonitor()
        {
            epService = EPServiceProviderManager.GetDefaultProvider();

            // Get called for any user showing up
            EPStatement factory = epService.EPAdministrator.CreatePattern("every user=" + typeof(MobileUserBean).FullName);

            factory.AddListener(new UpdateListener(
                delegate(EventBean[] newEvents, EventBean[] oldEvents)
                {
                    MobileUserBean user = (MobileUserBean)newEvents[0]["User"];

                    // No action if user already known
                    if (existingUsers.Contains(user.UserId))
                    {
                        return;
                    }

                    log.Debug(".update New user encountered, user=" + user.UserId);

                    existingUsers.Add(user.UserId);
                    new MatchMakingMonitor(user);
                }));
        }

        public MatchMakingMonitor(MobileUserBean mobileUser)
        {
            this.mobileUserId = mobileUser.UserId;

            // Create patterns that listen to other users
            SetupPatterns(mobileUser);

            // Listen to my own location changes so my data is up-to-date
            EPStatement locationChange = epService.EPAdministrator.CreatePattern(
                    "every myself=" + typeof(MobileUserBean).FullName +
                    "(userId=" + mobileUser.UserId + ")");

            locationChange.AddListener(new UpdateListener(
                delegate(EventBean[] newEvents, EventBean[] oldEvents)
                {
                    // When my location changed, re-establish pattern
                    locateOther.RemoveAllListeners();
                    MobileUserBean myself = (MobileUserBean)newEvents[0]["Myself"];
                    SetupPatterns(myself);
                }));
        }

        private void SetupPatterns(MobileUserBean mobileUser)
        {
            double locXLow = mobileUser.LocationX - PROXIMITY_RANGE;
            double locXHigh = mobileUser.LocationX + PROXIMITY_RANGE;
            double locYLow = mobileUser.LocationY - PROXIMITY_RANGE;
            double locYHigh = mobileUser.LocationY + PROXIMITY_RANGE;

            this.locateOther = epService.EPAdministrator.CreatePattern(
                    "every other=" + typeof(MobileUserBean).FullName +
                    "(locationX in [" + locXLow + ":" + locXHigh + "]," +
                    "locationY in [" + locYLow + ":" + locYHigh + "]," +
                    "myGender='" + mobileUser.PreferredGender + "'," +
                    "myAgeRange='" + mobileUser.PreferredAgeRange + "'," +
                    "myHairColor='" + mobileUser.PreferredHairColor + "'," +
                    "preferredGender='" + mobileUser.MyGender + "'," +
                    "preferredAgeRange='" + mobileUser.MyAgeRange + "'," +
                    "preferredHairColor='" + mobileUser.MyHairColor + "'" +
                    ")");

            locateOther.AddListener(new UpdateListener(
                delegate(EventBean[] newEvents, EventBean[] oldEvents)
                {
                    MobileUserBean other = (MobileUserBean)newEvents[0]["Other"];
                    MatchAlertBean alert = new MatchAlertBean(other.UserId, this.mobileUserId);
                    epService.EPRuntime.Emit(alert);
                }));
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
