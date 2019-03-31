package hackton.health.eir;


import java.util.ArrayList;

public class TestData {
    static public int[] vector = {1,2,2,3,3,4,1,5,5,6,6,7,0,1,1,14,14,8,8,9,9,10,14,11,11,12,12,13,0,1,1,2,0,1,1,5};
    static public int[] angle = {0,1,1,2,3,4,4,5,6,7,14,15,16,17,8,9,9,10,11,12,12,13};
    /**
     * pos1x position1 x axis
     */
    static public double[][] pos1x = {
            {-0.62916666,-0.417806,-0.28017583,-0.0049153836,0.2605143,-0.44729823,-0.727474,-0.9486654,0.14746094,0.46204436,0.7717123,0.10813806,0.49153647,0.8405273,0.1277994}
            ,{0.4980904,0.28181425,0.091753386,0.21627612,0.41944444,0.37356764,0.23593748,0.2031685,-0.26215282,-0.33424476,-0.38667536,-0.0065538133,0.10486121,0.2490451,-0.13763018}
            ,{0.078645766,0.078645766,-0.13763018,-0.3080295,-0.3407986,0.29492188,0.42599836,0.43255207,-0.08519967,-0.18350697,-0.3080295,0.24249138,0.3539063,0.45876732,0.07209204}
            ,{-0.13763018,0.13107646,-0.019661441,-0.3932292,-0.727474,0.3473524,0.13763018,-0.18350697,0.20972222,0.078645766,-0.09175348,0.5046441,0.57673615,0.63572055,0.36046}
            ,{0.23593748,-0.026215253,-0.24249129,-0.144184,0.07209204,0.13107646,0.45221362,0.727474,-0.3932292,-0.49153647,-0.616059,-0.078645855,0.026215253,0.13763018,-0.23593748}
    };
    /**
     * pos1y position1 y axis
     */
    static public double[][] pos1y = {
            {-0.15833336,-0.04583335,-0.12083334,-0.06666666,-0.054166675,0.070833325,-0.008333325,-0.14583331,0.06666672,0.0,-0.09583336,0.23333335,0.27499998,0.30833328,0.14999998}
            ,{0.20937502,0.125,0.16250002,0.32187498,0.40625,0.043749988,-0.09062505,-0.20937502,-0.12812495,-0.34687495,-0.553125,-0.15625,-0.359375,-0.56562495,-0.140625}
            ,{0.40625,0.203125,0.17500001,0.39375,0.625,0.16874999,0.375,0.59375,-0.25,-0.50312495,-0.73749995,-0.25,-0.51874995,-0.765625,-0.25}
            ,{0.41875,0.29062498,0.21562499,0.29062498,0.40625,0.30312502,0.50625,0.653125,-0.11249995,-0.34062505,-0.5625,-0.10625005,-0.36562502,-0.6,-0.109375}
            ,{0.3,0.16874999,0.18437499,0.421875,0.571875,0.100000024,0.21562499,0.38125002,-0.24062502,-0.49687505,-0.734375,-0.25,-0.515625,-0.75,-0.24687505}
    };


    static public double[][] pos2x = {
            {-0.026215253,-0.026215253,-0.33424476,-0.5374132,-0.7536893,0.25559902,0.45876732,0.6684895,-0.22938366,-0.38012156,-0.54396707,0.13763018,0.288368,0.48498258,-0.045876693}
            ,{0.026215253,0.013107627,-0.26870665,-0.32769096,-0.111414924,0.26870665,0.41944444,0.2031685,-0.11796874,-0.4128906,-0.43910587,0.2490451,0.53085935,0.62261295,0.06553814}
            ,{0.039322883,0.0065539074,-0.21627603,-0.32113713,-0.36701393,0.22282985,0.36701393,0.45221362,-0.18350697,-0.5046441,-0.4980903,0.13107646,0.47842884,0.47187495,-0.03276907}
            ,{-0.13763018,-0.1507379,-0.32113713,-0.37356773,-0.47842884,0.026215253,0.31458342,0.1507378,-0.28181425,-0.6357205,-0.62261283,-0.052430604,0.22938375,0.5636285,-0.17039934}
            ,{0.10486121,0.07209204,-0.078645855,-0.3473525,-0.6619358,0.1966146,0.38012156,0.3014758,-0.111414924,-0.3932292,-0.32769096,0.10486121,0.37356764,0.6488282,-0.0065538133}

    };
    static public double[][] pos2y = {
            {0.5875,0.3625,0.315625,0.15312499,0.015625,0.309375,0.12812501,-0.043750048,-0.068750024,-0.33124995,-0.58124995,-0.071874976,-0.34687495,-0.61249995,-0.071874976}
            ,{0.34375,0.13437498,0.07499999,-0.140625,-0.24374998,0.087499976,-0.115625024,-0.24374998,-0.234375,-0.25,-0.48749995,-0.21249998,-0.24687505,-0.51250005,-0.22500002}
            ,{0.21875,0.021875024,0.0,0.203125,0.409375,-0.015625,0.18437499,0.39375,-0.28437495,-0.28125,-0.546875,-0.27499998,-0.34687495,-0.57500005,-0.28125}
            ,{0.27499998,0.14999998,0.118749976,-0.006250024,-0.10625005,0.115625024,0.046875,-0.034374952,-0.16250002,-0.203125,-0.39999998,-0.15937495,-0.296875,-0.46562505,-0.16250002}
            ,{0.328125,0.20625001,0.18124998,0.17500001,0.20625001,0.16562498,0.0625,0.003125012,-0.068750024,-0.109375,-0.31875002,-0.078125,-0.16562498,-0.28750002,-0.07500005}
    };

    static public double[][] pos3x = {
            {0.3080295,0.288368,0.08519967,0.0,0.19006069,0.47187495,0.49153647,0.3539063,0.10486121,-0.18350697,-0.4653212,0.3473524,0.57673615,0.7864584,0.22282985}
            ,{0.0065539074,-0.0065538133,-0.18350697,-0.4456598,-0.72092015,0.17039934,0.4063368,0.68815106,-0.144184,-0.38012156,-0.616059,0.078645766,0.32113713,0.57018226,-0.03276907}

    };
    static public double[][] pos3y = {
            {0.465625,0.32187498,0.28125,0.14375001,0.22187501,0.27499998,0.12812501,0.20937502,0.006250024,-0.16562498,-0.32500005,0.0,-0.19687498,-0.35312498,0.003125012}
            ,{0.39687502,0.25625002,0.23124999,0.18124998,0.19687498,0.22187501,0.16250002,0.16874999,-0.037500024,-0.20937502,-0.36874998,-0.040624976,-0.21875,-0.37812495,-0.040624976}

    };



    static public int[] connection = {14,8, 8, 9, 9, 10, 14, 11, 11, 12, 12, 13, 1, 2, 2, 3, 3, 4, 1, 5, 5, 6, 6, 7, 1, 0, 1, 14 };

    public static String[] timeBefore = {"6-8","8-10","13-18","22-24","22","26-28","30-32","32-35","37-41"};
    public static String[] healthCheck={"First contact with Neuvola","First visit to Meternity clinic","Health check of a family","A public heath check","pregnancy certificate","Second public heath nurse/midwife check","Home visit / nurse visit", "Doctor check","Health check"};
    public static String[] healthCheckInfo= {"Clinics’ centralised appointment booking and counselling, on weekdays at 8:30 am–2:30 pm, tel. 09 8162 2800 (local network charge/mobile phone charge)",
            "First visit to nurse / midwife: 1h30min",
            "Nurse/midwife + doctor 2 times in total",
            "A public health check with nurse / midwife: 30min",
            "When you are 154 days or about 5 months into your pregnancy, you can get a certificate of pregnancy from the maternity and child welfare clinic. You need it to claim maternity grant and maternity allowance from Kela.",
            "Second public health check with nurse / midwife: 30min",
            "A visit to a nurse / midwife, or a home visit to a firstborn: 30 min or 2h 30min",
            "A health check with doctor: 30min",
            "Health Visitor / Midwife Reception visit every 2 weeks, more often if necessary: 30min"
    };
    public static String[] healthCheckWeb = {"https://www.espoo.fi/en-US/Families/Pregnancy_Childbirth_Baby_in_Family/Maternity_clinics_and_Child_health_clinics/Maternity_and_child_health_clinics_in_each_area",
            "https://thl.fi/fi/web/lapset-nuoret-ja-perheet/peruspalvelut/aitiys_ja_lastenneuvola/aitiysneuvola/maaraaikaisten-terveystarkastusten-maara-ja-ajankohdat",
            "https://thl.fi/fi/web/lapset-nuoret-ja-perheet/peruspalvelut/aitiys_ja_lastenneuvola/aitiysneuvola/maaraaikaisten-terveystarkastusten-maara-ja-ajankohdat",
            "https://thl.fi/fi/web/lapset-nuoret-ja-perheet/peruspalvelut/aitiys_ja_lastenneuvola/aitiysneuvola/maaraaikaisten-terveystarkastusten-maara-ja-ajankohdat",
            "https://www.kela.fi/web/en/families-quick-guide",
            "https://thl.fi/fi/web/lapset-nuoret-ja-perheet/peruspalvelut/aitiys_ja_lastenneuvola/aitiysneuvola/maaraaikaisten-terveystarkastusten-maara-ja-ajankohdat",
            "https://thl.fi/fi/web/lapset-nuoret-ja-perheet/peruspalvelut/aitiys_ja_lastenneuvola/aitiysneuvola/maaraaikaisten-terveystarkastusten-maara-ja-ajankohdat",
            "https://thl.fi/fi/web/lapset-nuoret-ja-perheet/peruspalvelut/aitiys_ja_lastenneuvola/aitiysneuvola/maaraaikaisten-terveystarkastusten-maara-ja-ajankohdat",
            "https://thl.fi/fi/web/lapset-nuoret-ja-perheet/peruspalvelut/aitiys_ja_lastenneuvola/aitiysneuvola/maaraaikaisten-terveystarkastusten-maara-ja-ajankohdat"

    };
    public static String[] kelaBenefit={null,null,null,null,"1.Claim maternity allowance 2. Maternity benefit application",null,null,null,null};
    public static String[] kelaBenefitInfo={null,null,null,null,"1.You can take a maternity leave 30-50 working days or about 5-8 weeks before your expected due date. The maternity allowance is paid for 105 working days, i.e., for about 4 months.2.You can get the maternity grant either in the form of a maternity package or as a sum of 170 euros. The maternity package is delivered about two weeks after being granted to you.",
            null,null,null,null
    };
    public static String[] kelaBenefitWeb={null,null,null,null,"https://www.kela.fi/web/en/maternity-grant-how-to-claim, https://www.kela.fi/web/en/maternity-allowance-how-to-claim",null,
            null,null,null
    };

    /**After birth pregnancy period：40 weeks */

    public static String[] afterBirthTime={"1","5-12","12","22"};
    public static String[] healthCheckafter={"Home visit","Post-natal examination", null,null};
    public static String[] healthCheckafterInfo={"Nursing / midwife reception or home visit:60 min/2h 30min","Post-natal examination by a doctor or nurse / midwife: 30min",null,null};
    public static String[] healthCheckafterInfoWeb={"https://thl.fi/fi/web/lapset-nuoret-ja-perheet/peruspalvelut/aitiys_ja_lastenneuvola/aitiysneuvola/maaraaikaisten-terveystarkastusten-maara-ja-ajankohdat", "https://thl.fi/fi/web/lapset-nuoret-ja-perheet/peruspalvelut/aitiys_ja_lastenneuvola/aitiysneuvola/maaraaikaisten-terveystarkastusten-maara-ja-ajankohdat",null,null};
    public static String[] activities={null,null,"Parental leave begins and apply for the patenity allowance",null};
    public static String[] activitiesInfo={null,null,"The parental leave begins after the maternity leave. The child will be about 3 months old at this time. Parents can agree between themselves how to use the parental leave entitlement. During the parental leave, Kela pays parental allowance for 158 working days (a little over half a year). Fathers can take paternity leave after the birth of their child. The paternity leave can last up to 54 working days or about 9 weeks. During this period, both parents can stay at home at the same time for up to 18 working days.",null};
    public static String[] activitiesInfoWeb={null,null,"https://www.kela.fi/web/en/paternity-allowance-how-to-claim",null};
    public static String[] kelaBenefitafter={"Child benefit",null,null,null};
    public static String[] kelaBenefitafterInfo={"Kela pays a child benefit for children under 17 years of age who live in Finland. You can get the child benefit from the beginning of the month following childbirth.",null,null,null};
    public static String[] kelaBanefitafterWeb={"https://www.kela.fi/web/en/child-benefit-how-to-claim",null,null,null};
    public static String[] registration= {"personal Kela card",null,null,null};
    public static String[] registrationInfo= {"When a child is born in Finland, information about this goes directly from the hospital to the population register and from there to Kela. Once the child has been named, Kela sends a personal Kela card for the child.",null,null,null};
    public static String[] registrationWeb= {null,null,null,null};
    public static String[] Vaccination={null,null,null,"Meningitis, pneumonia, blood poisoning and ear infection"};
    public static String[] VaccinationInfo={null,null,null,"PCV 13 vaccine (Prevenar) is recommended for people over 5 years of age and adults who, due to their illness or medication, are particularly at risk of serious pneumococcal infection or complication. Pneumococcal vaccinations in risk groups"};
    public static String[] VaccinationWeb={null,null,null,"https://thl.fi/fi/web/rokottaminen/rokotteet/pneumokokkirokote/pneumokokkikonjugaattirokote-eli-pcv-rokote"};


    private static TestData testData;

    private static ArrayList<Integer> numberOfExercise= new ArrayList<>();
    //private constructor.
    private TestData(){

        //Prevent form the reflection api.
        if (testData != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static TestData getInstance(){
        if (testData == null){ //if there is no instance available... create new one
            testData = new TestData();
        }

        return testData;
    }

    public void addExec(int n){
        numberOfExercise.add(n);
    }

    public static ArrayList<Integer> getNumberOfExercise() {
        return numberOfExercise;
    }
}

