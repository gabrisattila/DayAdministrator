package Classes.I18N;


import Classes.OwnFileTypes.Excel;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.Objects.isNull;

public class I18N {

    //region For job device
/*
    public final static String dataExcelsPath = "C:\\Users\\gabri\\Desktop\\JOB\\own\\";

    public static final String probaExcelFileName = "Proba.xlsm";

    public static final String MeasureExcelFileName = "Measures_Proba.xlsx";

    public static final String MoneyExcelFileName = "Money_Proba.xlsx";

    public static final String TimeExcelFileName = "The_Time_Proba.xlsm";

    //endregion

    //region For home device

    public final static String dataExcelsPath = "C:\\Users\\asus\\Desktop\\Minden\\Adat\\";

    public static final String MeasureExcelFileName = "Measures_Proba.xlsx";

    public static final String MoneyExcelFileName = "Money_Proba.xlsx";

    public static final String TimeExcelFileName = "The_Time_Proba.xlsx";

    //endregion

    public static final String delimiterBetweenTextParts = ";";

    public static final String WorkbookOpenMacroSkeleton =
            "Sub Workbook_Open()\n" +
            "    \n" +
            "    callMacros\n" +
            "\n" +
            "End Sub";

    public static final String CallSubMacroSkeleton = "Call macroName(params)";

    public static final String MacroParamSkeleton = "param,";

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

    public static double roundTo2(double value){
        return round(value, 2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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

    public static String trimPartAfterTheFirstParentheses(String string){
        if (string.contains("(")) {
            int x = 1;
            String firstWord = getFirstXWordsOfAString(string, x);
            while (!firstWord.contains("(")) {
                x++;
                firstWord = getFirstXWordsOfAString(string, x);
            }
            x--;
            return getFirstXWordsOfAString(string, x);
        }else {
            return string;
        }
    }

    public static String getFirstXWordsOfAString(String string, int INeedThatMuchWord){
        StringBuilder builder = new StringBuilder();
        String[] split = string.split(" ");
        int counter = 0;
        for (String s : split){
            if (counter < INeedThatMuchWord) {
                builder.append(s).append(" ");
                counter++;
            }
            else {
                break;
            }
        }
        return builder.toString().trim();
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
            LocalDate.parse(string, formatter);
            return true;
        }catch (RuntimeException e){
            formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            try {
                LocalDate.parse(string, formatter);
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

        public static String getTitleOfAnAction(String action, actionType typeOfAction) {

            switch (typeOfAction){
                case Értékes -> {
                    for (actionGroup headLine : ÉrtékesActionTerms.keySet()){
                        if (ÉrtékesActionTermsContains(headLine, action))
                            return headLine.toString();
                    }
                }
                case Szükséges -> {
                    for (actionGroup headLine : SzükségesActionTerms.keySet()){
                        if (SzükségesActionTermsContains(headLine, action))
                            return headLine.toString();
                    }
                }
                case Szabadidő -> {
                    for (actionGroup headLine : SzabadidőActionTerms.keySet()){
                        if (SzabadidőActionTermsContains(headLine, action))
                            return headLine.toString();
                    }
                }
            }
            return "";
        }

        public static String lower(String term){
            return term.toLowerCase();
        }

        public static final Map<actionGroup, List<String>> ÉrtékesActionTerms = new HashMap<>(){{
            put(actionGroup.Munka, new ArrayList<>(){{
                add("meló"); add("munka"); add("meló:"); add("munka:"); add("interjúk"); add("interjú");
                add("próba feladat"); add("emailek"); add("levelek"); add("levelezés"); add("próbafeladat"); add("próbafeladatok");
                add("próba feladatok"); add("munka keresés"); add("munkakeresés");

            }});
            put(actionGroup.Olvasás, new ArrayList<>(){{
                add("olv."); add("olv"); add("olvas"); add("olvas."); add("olvasás"); add(" olv");
            }});
            put(actionGroup.Írás, new ArrayList<>(){{
                add("írás"); add("ír"); add("ír."); add("levél");
            }});
            put(actionGroup.Önálló_munka, new ArrayList<>());
            put(actionGroup.Videózás, new ArrayList<>(){{
                add("vágás"); add("vág"); add("vág."); add("daily vlog"); add("vlog"); add("videó");
            }});
            put(actionGroup.Sport, new ArrayList<>(){{
                add("futás"); add("fut."); add("fut"); add("nyújtás"); add("nyújt."); add("nyújt"); add("fekvő"); add("plank");
                add("planche"); add("planc"); add("planch"); add(" km "); add("body"); add("full body"); add("tánc"); add("parkour");
                add("kondi"); add("edzés"); add("workout");
            }});
            put(actionGroup.Tanulás, new ArrayList<>(){{
                add("anki"); add("tan."); add("tanul."); add("tanulás"); add("gyak."); add("gyak.");
                add("tanuló kártyák"); add("tananyag"); add("töri"); add("matek");
            }});
            put(actionGroup.IG_ért, new ArrayList<>(){{
                add("sujtás"); add("megbeszélés"); add("akció"); add("matricázás");
                add("matric"); add("anyag"); add(" ig "); add(" ig. ");
            }});
            put(actionGroup.Templom, new ArrayList<>(){{
                add("mise"); add("lelkiismeret vizsgálat"); add("gyónás"); add("szertartás"); add("gyak.");
            }});
            put(actionGroup.Egyéb_értékes, new ArrayList<>(){{
                add("naptár"); add("tervezése"); add("jövő heti"); add("jövő hét"); add("terv"); add("intézés");
            }});
        }};

        public static final Map<actionGroup, List<String>> SzükségesActionTerms = new HashMap<>(){{
            put(actionGroup.Reggeli_tevékenységek, new ArrayList<>(){{
                add("reggeli"); add("reggel"); add("borotválkozás"); add("borot."); add("fogmosás"); add("hideg zuhany"); add("zuhany");
                add("hideg"); add("méreckedés"); add("mér."); add("mérés"); add("póz."); add("pózol."); add("pózól"); add("pózolás");
            }});
            put(actionGroup.Ebéd_és_vagy_főzés, new ArrayList<>(){{
                add("ebéd"); add("főzés"); add("főz."); add("főz"); add("sali."); add("sali"); add("saláta"); add("másnapra");
                add("napra"); add("elrakás"); add("elrak."); add("készítés"); add("készít"); add("készít");
                add("elkészítés"); add("elkészít.");
            }});
            put(actionGroup.Esti_tevékenységek, new ArrayList<>(){{
                add("fogmosás"); add("fog."); add("tus"); add("hajmosás"); add("haj."); add("vacsi"); add("vacsora"); add("vacsi.");
                add("másnapra"); add("előkészítés"); add("előkészít."); add("fekvés"); add("tus"); add("este");}});
            put(actionGroup.Utazás_és_vagy_készülődés, new ArrayList<>(){{
                add("ut."); add("utazás"); add("utaz"); add("utaz.");
            }});
            put(actionGroup.Bevásárlás, new ArrayList<>(){{
                add("bev."); add("bev.:"); add("bevásárlás"); add("vásárlás"); add("bev:");
                add("spar"); add("coop"); add("aldi"); add("lidl"); add("príma"); add("prima"); add("cba");
            }});
            put(actionGroup.Takarítás, new ArrayList<>(){{
                add("takarítás"); add("nagy takarítás"); add("takar."); add("tak."); add("takarít"); add("takarít.");
                add("porszívózás"); add("porsz."); add("por."); add("felmosás"); add("felmos."); add("interjúk");
                add("portörlés"); add("seprés"); add("söprés"); add("rend."); add("rend rakás"); add("rakás");
            }});
            put(actionGroup.Mosás, new ArrayList<>(){{
                add("mosás"); add("teregetés"); add("mos."); add("ter."); add("tereget."); add("tereget");
                add("ruhák beszedése"); add("ruhák"); add("beszedése"); add("beszed.");
            }});
            put(actionGroup.Vasalás, new ArrayList<>(){{
                add("vasalás"); add("vas."); add("vasal.");
            }});
            put(actionGroup.Daily, new ArrayList<>(){{
                add("napi"); add("napi teendők"); add("teendők"); add("összegzés"); add("összegzése");
            }});
            put(actionGroup.Egyéb_szükséges, new ArrayList<>(){{
                add("pénz felvétel"); add("bürokrácia"); add("ügyintézés"); add("gyak"); add("intézés");
            }});
        }};

        public static final Map<actionGroup, List<String>> SzabadidőActionTerms = new HashMap<>(){{
            put(actionGroup.Család, new ArrayList<>(){{
                add("film"); add("filmnézés"); add("gyak"); add("apa"); add("anya"); add("levi"); add("levente"); add("eme"); add("emese");
                add("apa"); add("társas"); add("beszélgetés"); add("család"); add("családi ebéd"); add("családi");
            }});
            put(actionGroup.Kórus, new ArrayList<>(){{
                add("próba"); add("fellépés"); add("koncert"); add("konc."); add("apa"); add("kórus");
            }});
            put(actionGroup.IG_szabad, new ArrayList<>(){{
                add("tali");
            }});
            put(actionGroup.Időtöltés_Mással, new ArrayList<>(){{
                add("lilla"); add("gerda"); add("d. geri"); add("máté"); add("v. geri"); add("geri"); add("telefon"); add("teló"); add("schönbi");
                add("b. réka"); add("marci"); add("kávé"); add("mandi"); add("lugosi"); add("apa"); add("berni"); add("színház");
            }});
            put(actionGroup.Időtöltés_több_emberrel, new ArrayList<>(){{
                add("kórusos"); add("srácok"); add("kolis"); add("kkf"); add("mozi");
            }});
        }};

        public static boolean ÉrtékesActionTermsContains(actionGroup group, String action){
            return ActionTermsContains(ÉrtékesActionTerms, group, action);
        }

        public static boolean SzükségesActionTermsContains(actionGroup group, String action){
            return ActionTermsContains(SzükségesActionTerms, group, action);
        }

        public static boolean SzabadidőActionTermsContains(actionGroup group, String action){
            return ActionTermsContains(SzabadidőActionTerms, group, action);
        }

        private static boolean ActionTermsContains(Map<actionGroup, List<String>> map, actionGroup group, String action){
            if (group.toString().equals(action))
                return true;
            action = lower(action);
            return notNull(map.get(group)) && map.get(group).contains(action);
        }

        public enum actionType {
            Értékes, Szükséges, Szabadidő;

            public static actionType getActionType(String action) {
                for (actionGroup key : ÉrtékesActionTerms.keySet()){
                    if (ÉrtékesActionTermsContains(key, action))
                        return Értékes;
                }
                for (actionGroup key : SzükségesActionTerms.keySet()){
                    if (SzükségesActionTermsContains(key, action))
                        return Szükséges;
                }
                for (actionGroup key : SzabadidőActionTerms.keySet()){
                    if (SzabadidőActionTermsContains(key, action))
                        return Szabadidő;
                }
                return null;
            }

            public static actionType getActionType(actionGroup actionGroup){
                switch (actionGroup){
                    case Munka, Olvasás, Írás, Önálló_munka, Videózás, Közélet, Sport, IG_ért, Tanulás, Templom, Egyéb_értékes -> {
                        return Értékes;
                    }
                    case Reggeli_tevékenységek, Ebéd_és_vagy_főzés, Esti_tevékenységek, Utazás_és_vagy_készülődés, Bevásárlás, Takarítás, Mosás, Mosogatás, Vasalás, Daily, Egyéb_szükséges -> {
                        return Szükséges;
                    }
                    case Család, Kórus, IG_szabad, Időtöltés_Mással, Időtöltés_több_emberrel, Egyéb_szabad -> {
                        return Szabadidő;
                    }
                }
                throw new RuntimeException("\nYou trying to get actionType from actionGroup, but the group you just given is not valid.\n");
            }
        }

        public enum actionGroup {
            Munka, Olvasás, Írás, Önálló_munka, Videózás, Közélet, Sport, IG_ért, Tanulás, Templom, Egyéb_értékes,

            Reggeli_tevékenységek, Ebéd_és_vagy_főzés, Esti_tevékenységek, Utazás_és_vagy_készülődés, Bevásárlás, Takarítás, Mosás, Mosogatás, Vasalás, Daily, Egyéb_szükséges,

            Család, Kórus, IG_szabad, Időtöltés_Mással, Időtöltés_több_emberrel, Egyéb_szabad;

            @Override
            public String toString() {
                if (super.toString().contains("_"))
                    return super.toString().replace("_", " ");
                return super.toString();
            }

            public static actionGroup getActionGroup(String action){
                for (actionGroup group : ÉrtékesActionTerms.keySet()){
                    if (ÉrtékesActionTermsContains(group, action))
                        return group;
                }
                for (actionGroup group : SzükségesActionTerms.keySet()){
                    if (SzükségesActionTermsContains(group, action))
                        return group;
                }
                for (actionGroup group : SzabadidőActionTerms.keySet()){
                    if (SzabadidőActionTermsContains(group, action))
                        return group;
                }
                return null;
            }
        }

    }

}
