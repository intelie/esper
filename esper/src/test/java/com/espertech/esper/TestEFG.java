package com.espertech.esper;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import junit.framework.TestCase;

public class TestEFG extends TestCase {

    public void testEFG() throws Exception {
        String module = "        @Name(\"FAA - Schemas - ClaimEvent\") \n" +
                "        create schema ClaimEvent (enterpriseId integer, providerId String, specialty String, claimNum String, memberId String, \n" +
                "                                                    diagnoses String[], edcCodes String[], cpt4 String, rxMG String, \n" +
                "                                                    serviceDate java.util.Date, age integer, sex String); \n" +
                "\n" +
                "        @Name(\"FAA - Schemas - Duplicate Procedures \") \n" +
                "        create schema DuplicateProcedures (original ClaimEvent, duplicate ClaimEvent, rule int, detail String); \n" +
                "\n" +
                "\n" +
                "        @Name(\"FAA - Ectomies- Route unilateral ectomies \") \n" +
                "        insert into UnilateralEctomy \n" +
                "        select c ,  'a' as anatomy \n" +
                "        from ClaimEvent c; \n";


         String epl = "        @Name(\"FAA - Ectomies - Detect second unilateral ectomy \") \n" +
                "        insert into DuplicateProcedures \n" +
                "        select e1.c as original, e2.c as duplicate, 4 as rule, e2.anatomy as detail \n" +
                "        from pattern [every e1 = UnilateralEctomy \n" +
                "                               -> (timer:interval(30 days) and not UnilateralEctomy(c.memberId = e1.c.memberId, anatomy = e1.anatomy)) \n" +
                "                               -> e2 = UnilateralEctomy(c.memberId = e1.c.memberId, anatomy = e1.anatomy)] \n";

        Configuration config = new Configuration();
        
        EPServiceProvider engine = EPServiceProviderManager.getDefaultProvider(config);
        engine.getEPAdministrator().getDeploymentAdmin().parseDeploy(module, null, null, null);
        engine.getEPAdministrator().createEPL(epl);

    }
}
