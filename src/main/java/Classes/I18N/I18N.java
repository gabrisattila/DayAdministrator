package Classes.I18N;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.Objects.isNull;

public class I18N {

    //region For job device

    public final static String dataExcelsPath = "C:\\Users\\gabri\\Desktop\\JOB\\own\\";

    public static final String probaExcelFileName = "Proba.xlsx";

    public static final String MeasureExcelFileName = "Measures_Proba.xlsx";

    public static final String MoneyExcelFileName = "Money_Proba.xlsx";

    public static final String TimeExcelFileName = "The_Time_Proba.xlsx";


    //endregion
/*
    //region For home device

    public final static String dataExcelsPath = "C:\\Users\\asus\\Desktop\\Minden\\Adat\\";

    public static final String MeasureExcelFileName = "Measures_Proba.xlsx";

    public static final String MoneyExcelFileName = "Money_Proba.xlsx";

    public static final String TimeExcelFileName = "The_Time_Proba.xlsx";

    //endregion
*/
    public static final String delimiterBetweenTextParts = ";";

    public static final String[] SUMMATimeExcelHeadTitles =
            new String[]{
                    "Ébredés", "Lefekvés", "Elalvás", "Power Nap", "Alvás", "Aktív ébrenlét", "Értékes SUMMA (Óra / %)",
                    "Szükséges SUMMA (Óra / %)", "Szabadidő SUMMA (Óra / %)", "Me time (Óra / %)"
            };

    public static final String[] ÉrtékesTimeExcelHeadTitles =
            new String[]{
                    "Munka (Összes / Darabolva)", "Olvasás (Összes / Darabolva)", "Írás (Összes / Darabolva)",
                    "Önálló munka (Összes / Darabolva)", "Videózás (Összes / Darabolva)", "Sport (Összes / Darabolva)",
                    "IG (Összes / Darabolva)", "Tanulás (Összes / Darabolva)", "Templom (Összes / Darabolva)",
                    "Egyéb (Összes / Darabolva)", "SUM"
            };

    public static final String[] SzükségesTimeExcelHeadTitles =
            new String[]{
                    "Reggeli tevékenységek", "Ebéd és vagy főzés", "Esti tevékenységek", "Utazás (Tevékenység nélkül / Valós idejű)",
                    "Bevásárlás", "Takarítás", "Mosás + teregetés", "Mosogatás", "Vasalás", "Daily", "Egyéb (Összesen / Darabolva)",
                    "SUM"
            };

    public static final String[] SzabadidőTimeExcelHeadTitles =
            new String[]{
                    "Család (Összes / Darabolva)", "Kórus", "IG", "Időtöltés Mással", "Időtöltés több emberrel", "Egyéb", "Me Time",
                    "SUM"
            };

    public static final String[] AdatokMeasuresExcelHeadTitles =
            new String[]{
                    "Kávé db", "Súly (kg)", "Telefon idő", "Cigi cél", "Cigi", "J O :("
            };

    public static final String[] KiadásokMoneyExcelHeadTitles =
            new String[]{
                    "Élelem (KP / Kártya)", "Tisztálkodás (KP / Kártya)", "Utazás (KP / Kártya)", "Háztartás (KP / Kártya)",
                    "Készpénz kártyára (KP)", "Kártyáról készpénzre (Kártya)", "Számlák (Kártya)", "Diákhitel+ (Kártya)",
                    "Telefon (Kártya)", "Lakbér + rezsi (Kártya)", "Cigi (KP / Kártya)", "Étel ami nem főzés (KP / Kártya)",
                    "Kocsma (KP / Kártya)", "Alkohol (KP / Kártya)", "Könyv (KP / Kártya)", "Színház (KP / Kártya)",
                    "Újság (KP / Kártya)", "Egyéb (KP / Kártya)", "SUM (KP / Kártya)"
            };

    public static final String[] BevételekMoneyExcelHeadTitles =
            new String[]{
                    "KP", "Kártya", "Honnan (Folyamatosan frissülő típusok):", "Munka", "Otthon", "Munkanélküli támogatás",
                    "Készpénz kártyára", "Kártyáról készpénz"
            };

    public static final String[] SUMMAMoneyExcelHeadTitles =
            new String[]{
                    "Kezdőérték - KP", "Kezdőérték - Kártya", "Aktuális - KP", "Aktuális - Kártya",
                    "Záró érték - KP ", "Záró érték - Kártya", "Félretett - KP", "Félretett - Kártya",
                    "Jelenleg félretett KP:", "Jelenleg félretett Kártya:"
            };


    public static final Map<String, List<String>> usuals = new HashMap<>(){{
        //TODO Define usuals for
        // "Értékes", "Szükséges", "Szabadidő"
    }};

    public static <T> T[] swap(T[] array, int i, int j){
        if (array.length < i || array.length < j)
            throw new IndexOutOfBoundsException("We wanted to swap the " + i + ". and the " + j + ". element\n" +
                    "of the\n" + Arrays.toString(array) + "\nbut there was too few elements in it.");

        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;

        return array;
    }

    public static boolean notNull(Object o){
        return !isNull(o);
    }

    public static String getFileNameFromPath(String path) {
        // Az elérési út végéről kinyerjük a fájl nevét
        int lastSeparatorIndex = path.lastIndexOf('\\');
        if (lastSeparatorIndex >= 0 && lastSeparatorIndex < path.length() - 1) {
            return path.substring(lastSeparatorIndex + 1);
        } else {
            // Ha nem találjuk a mappa elválasztójelét vagy a string üres, akkor visszatérünk az eredeti stringgel
            return path;
        }
    }

    public static boolean textContainsString(String text, String string){
        return KMPSearch(string.toLowerCase(), text.toLowerCase());
    }

    /**
     * KMP mintaillesztő algoritmus
     * @param pat a minta string amit keresünk a második paraméterben
     * @param txt a string amiben keressük a mintát
     */
    public static boolean KMPSearch(String pat, String txt)
    {
        int M = pat.length();
        int N = txt.length();

        if (M == 0 || N == 0){
            return false;
        }

        // create lps[] that will hold the longest
        // prefix suffix values for pattern
        int[] lps = new int[M];
        int j = 0; // index for pat[]
        // Preprocess the pattern (calculate lps[]
        // array)
        computeLPSArray(pat, M, lps);
        int i = 0; // index for txt[]
        while (i < N) {
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
                j = lps[j - 1];
                return true;
            }// mismatch after j matches
            else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                // Do not match lps[0..lps[j-1]] characters,
                // they will match anyway
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
        return false;
    }

    private static void computeLPSArray(String pat, int M, int[] lps)
    {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0
        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            }
            else // (pat[i] != pat[len])
            {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0) {
                    len = lps[len - 1];
                    // Also, note that we do not increment
                    // i here
                }
                else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

    public static boolean isDate(String string){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
        try {
            LocalDate date = LocalDate.parse(string, formatter);
            return true;
        }catch (RuntimeException e){
            formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            try {
                LocalDate date = LocalDate.parse(string, formatter);
                return true;
            }catch (RuntimeException ex) {
                return false;
            }
        }
    }

    public static LocalDate toDate(String s){
        DateTimeFormatter formatter;
        if (s.charAt(s.length() - 1) == '.'){
            formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.");
        }else {
            formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        }
        return LocalDate.parse(s, formatter);
    }


    /**
     * Always use to lower when search for action terms
     */
    public static class ActionTerms{

        public enum actionType {Értékes, Szükséges, Szabadidő}

        public static actionType getTypeOfAction(String action) throws AskTheUserForInformation {
            for (String key : ÉrtékesActionTerms.keySet()){
                for (String értékes : ÉrtékesActionTerms.get(key)){
                    if (textContainsString(action, értékes)){
                        return actionType.Értékes;
                    }
                }
            }
            for (String key : SzükségesActionTerms.keySet()){
                for (String szükséges : SzükségesActionTerms.get(key)){
                    if (textContainsString(action, szükséges)){
                        return actionType.Szükséges;
                    }
                }
            }
            for (String key : SzabadidőActionTerms.keySet()){
                for (String szabadidő : SzabadidőActionTerms.get(key)){
                    if (textContainsString(action, szabadidő)){
                        return actionType.Szabadidő;
                    }
                }
            }
            throw new AskTheUserForInformation(action, "Time");
        }

        public static String getTitleOfAnAction(String action, actionType typeOfAction) {
            switch (typeOfAction){
                case Értékes -> {
                    for (String headLine : ÉrtékesActionTerms.keySet()){
                        if (ÉrtékesActionTerms.get(headLine).contains(action))
                            return headLine;
                    }
                }
                case Szükséges -> {
                    for (String headLine : SzükségesActionTerms.keySet()){
                        if (SzükségesActionTerms.get(headLine).contains(action))
                            return headLine;
                    }
                }
                case Szabadidő -> {
                    for (String headLine : SzabadidőActionTerms.keySet()){
                        if (SzabadidőActionTerms.get(headLine).contains(action))
                            return headLine;
                    }
                }
            }
            return "";
        }

        public static String lower(String term){
            return term.toLowerCase();
        }

        public static final Map<String, List<String>> ÉrtékesActionTerms = new HashMap<>(){{
            put("Munka", new ArrayList<>(){{
                add("meló"); add("munka"); add("meló:"); add("munka:"); add("interjúk"); add("interjú");
                add("próba feladat"); add("emailek"); add("levelek"); add("levelezés"); add("próbafeladat"); add("próbafeladatok");
                add("próba feladatok"); add("munka keresés"); add("munkakeresés");

            }});
            put("Olvasás", new ArrayList<>(){{
                add("olv."); add("olv"); add("olvas"); add("olvas."); add("olvasás"); add(" olv");
            }});
            put("Írás", new ArrayList<>(){{
                add("írás"); add("ír"); add("ír."); add("levél");
            }});
            put("Videózás", new ArrayList<>(){{
                add("vágás"); add("vág"); add("vág."); add("daily vlog"); add("vlog");
            }});
            put("Sport", new ArrayList<>(){{
                add("futás"); add("fut."); add("fut"); add("nyújtás"); add("nyújt."); add("nyújt"); add("fekvő"); add("plank");
                add("planche"); add("planc"); add("planch"); add(" km "); add("body"); add("full body"); add("tánc"); add("parkour");
                add("kondi"); add("edzés"); add("workout");
            }});
            put("Tanulás", new ArrayList<>(){{
                add("anki"); add("tan."); add("tanul."); add("tanulás"); add("gyak."); add("gyak.");
                add("tanuló kártyák"); add("tananyag"); add("töri"); add("matek");
            }});
            put("IG", new ArrayList<>(){{
                add("sujtás"); add("megbeszélés"); add("akció"); add("matric"); add("anyag"); add(" ig "); add(" ig. ");
            }});
            put("Templom", new ArrayList<>(){{
                add("mise"); add("lelkiismeret vizsgálat"); add("gyónás"); add("szertartás"); add("gyak.");
            }});
            put("Egyéb", new ArrayList<>(){{
                add("naptár"); add("tervezése"); add("jövő heti"); add("jövő hét"); add("terv"); add("intézés");
            }});
        }};

        public static final Map<String, List<String>> SzükségesActionTerms = new HashMap<>(){{
            put("Reggeli tevékenységek", new ArrayList<>(){{
                add("reggeli"); add("reggel"); add("borotválkozás"); add("borot."); add("fogmosás"); add("hideg zuhany"); add("zuhany");
                add("hideg"); add("méreckedés"); add("mér."); add("mérés"); add("póz."); add("pózol."); add("pózól"); add("pózolás");
            }});
            put("Ebéd és vagy főzés", new ArrayList<>(){{
                add("ebéd"); add("főzés"); add("főz."); add("főz"); add("sali."); add("sali"); add("saláta"); add("másnapra");
                add("napra"); add("elrakás"); add("elrak."); add("készítés"); add("készít"); add("készít");
                add("elkészítés"); add("elkészít.");
            }});
            put("Esti tevékenységek", new ArrayList<>(){{
                add("fogmosás"); add("fog."); add("tus"); add("hajmosás"); add("haj."); add("vacsi"); add("vacsora"); add("vacsi.");
                add("másnapra"); add("előkészítés"); add("előkészít.");
            }});
            put("Utazás (Tevékenység nélkül / Valós idejű)", new ArrayList<>(){{
                add("ut."); add("utazás"); add("utaz"); add("utaz.");
            }});
            put("Bevásárlás", new ArrayList<>(){{
                add("bev."); add("bev.:"); add("bevásárlás"); add("vásárlás"); add("bev:");
                add("spar"); add("coop"); add("aldi"); add("lidl"); add("príma"); add("prima"); add("cba");
            }});
            put("Takarítás", new ArrayList<>(){{
                add("takarítás"); add("nagy takarítás"); add("takar."); add("tak."); add("takarít"); add("takarít.");
                add("porszívózás"); add("porsz."); add("por."); add("felmosás"); add("felmos."); add("interjúk");
                add("portörlés"); add("seprés"); add("söprés"); add("rend."); add("rend rakás"); add("rakás");
            }});
            put("Mosás + teregetés", new ArrayList<>(){{
                add("mosás"); add("teregetés"); add("mos."); add("ter."); add("tereget."); add("tereget");
                add("ruhák beszedése"); add("ruhák"); add("beszedése"); add("beszed.");
            }});
            put("Vasalás", new ArrayList<>(){{
                add("vasalás"); add("vas."); add("vasal.");
            }});
            put("Daily", new ArrayList<>(){{
                add("napi"); add("napi teendők"); add("teendők"); add("összegzés"); add("összegzése");
            }});
            put("Egyéb (Összesen / Darabolva)", new ArrayList<>(){{
                add("pénz felvétel"); add("bürokrácia"); add("ügyintézés"); add("gyak"); add("intézés");
            }});
        }};

        public static final Map<String, List<String>> SzabadidőActionTerms = new HashMap<>(){{
            put("Család (Összesen / Darabolva)", new ArrayList<>(){{
                add("film"); add("filmnézés"); add("gyak"); add("apa"); add("anya"); add("levi"); add("levente"); add("eme"); add("emese");
                add("apa"); add("társas"); add("beszélgetés"); add("család"); add("családi ebéd"); add("családi");
            }});
            put("Kórus", new ArrayList<>(){{
                add("próba"); add("fellépés"); add("koncert"); add("konc."); add("apa"); add("kórus");
            }});
            put("IG", new ArrayList<>(){{
                add("tali");
            }});
            put("Időtöltés mással", new ArrayList<>(){{
                add("lilla"); add("gerda"); add("d. geri"); add("máté"); add("v. geri"); add("geri"); add("telefon"); add("teló"); add("schönbi");
                add("b. réka"); add("marci"); add("kávé"); add("mandi"); add("lugosi"); add("apa"); add("berni"); add("színház");
            }});
            put("Időtöltés több emberrel", new ArrayList<>(){{
                add("kórusos"); add("srácok"); add("kolis"); add("kkf"); add("mozi");
            }});
        }};
    }

}
