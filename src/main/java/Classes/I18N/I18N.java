package Classes.I18N;


import java.util.*;

import static java.util.Objects.isNull;

public class I18N {

    public final static String dataExcelsPath = "C:\\Users\\asus\\Desktop\\Minden\\Adat\\";

    public static final String MeasureExcelFileName = "Mérőszámok.xlsx";

    public static final String MoneyExcelFileName = "Kiadások - Bevételek.xlsx";

    public static final String TimeExcelFileName = "Az_Idő_Maga.xlsx";


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
        int lastSeparatorIndex = path.lastIndexOf('/');
        if (lastSeparatorIndex >= 0 && lastSeparatorIndex < path.length() - 1) {
            return path.substring(lastSeparatorIndex + 1);
        } else {
            // Ha nem találjuk a mappa elválasztójelét vagy a string üres, akkor visszatérünk az eredeti stringgel
            return path;
        }
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



    public static class ActionTerms{

        public static String lower(String term){
            return term.toLowerCase();
        }

        public static final Map<String, List<String>> ÉrtékesActionTerms = new HashMap<>(){{
            put("Meló", new ArrayList<>(){{
                add("meló"); add("munka"); add("meló:"); add("munka:"); add("interjúk"); add("interjú");
                add("próba feladat"); add("emailek"); add("próbafeladat"); add("próbafeladatok");
                add("próba feladatok"); add("Munkakeresés"); add("munka keresés"); add("munkakeresés");

            }});
            put("Olvasás", new ArrayList<>(){{
                add("");
            }});
            put("Írás", new ArrayList<>(){{
                add("");
            }});
            put("Önálló Munka", new ArrayList<>(){{
                add("");
            }});
            put("Videózás", new ArrayList<>(){{
                add("");
            }});
            put("Sport", new ArrayList<>(){{
                add("");
            }});
            put("Tanulás", new ArrayList<>(){{
                add("");
            }});
            put("Templom", new ArrayList<>(){{
                add("");
            }});
            put("Egyéb", new ArrayList<>(){{
                add("");
            }});
        }};

        public static final Map<String, List<String>> SzükségesActionTerms = new HashMap<>(){{
            put("Reggeli tevékenységek", new ArrayList<>(){{
                add("reggeli"); add("reggel"); add("borotválkozás"); add("borot."); add("fogmosás"); add("hideg zuhany"); add("zuhany");
                add("hideg"); add("méreckedés"); add("mér."); add("mérés"); add("póz."); add("pózol."); add("pózól"); add("pózolás");
            }});
            put("Ebéd és vagy főzés", new ArrayList<>(){{
                add("ebéd"); add("főzés"); add("főz."); add("főz"); add("sali."); add("sali"); add("saláta"); add("másnapra");
                add("napra"); add("elrakás"); add("elrak."); add("készítés"); add("készít"); add("készít"); add("előkészít");
                add("előkész."); add("elkészítés"); add("elkészít.");
            }});
            put("Esti tevékenységek", new ArrayList<>(){{
                add("fogmosás"); add("fog."); add("tus"); add("hajmosás"); add("haj."); add("vacsi"); add("vacsora"); add("vacsi.");
                add("sali."); add("sali"); add("saláta"); add("másnapra"); add("előkészítés"); add("előkészít.");
            }});
            put("Utazás (Tevékenység nélkül / Valós idejű)", new ArrayList<>(){{
                add("ut."); add("utazás"); add("utaz");
            }});
            put("Bevásárlás", new ArrayList<>(){{
                add("bev."); add("bev.:"); add("bevásárlás"); add("vásárlás"); add("bev:");
                add("spar"); add("coop"); add("aldi"); add("lidl"); add("príma"); add("prima");
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
            put("Egyéb (Összesen / Darabolva)", new ArrayList<>());
        }};

        public static final Map<String, List<String>> SzabadidőActionTerms = new HashMap<>(){{
            put("Család (Összesen / Darabolva)", new ArrayList<>(){{
                add("");
            }});
            put("Kórus", new ArrayList<>(){{
                add("");
            }});
            put("IG", new ArrayList<>(){{
                add("");
            }});
            put("Időtöltés mással", new ArrayList<>(){{
                add("");
            }});
            put("Időtöltés több emberrel", new ArrayList<>(){{
                add("");
            }});
            put("Egyéb", new ArrayList<>(){{
                add("");
            }});
        }};
    }

}
