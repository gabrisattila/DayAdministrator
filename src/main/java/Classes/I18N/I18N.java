package Classes.I18N;

import Classes.ModifyWorkBooks.ThreeMainType;

import java.util.*;

import static Classes.ModifyWorkBooks.ThreeMainType.*;
import static java.util.Objects.isNull;

public class I18N {

    public final static String dataExcelsPath = "C:\\Users\\asus\\Desktop\\Minden\\Adat\\";

    public static final String MeasureExcelFileName = "Mérőszámok.xlsx";

    public static final String MoneyExcelFileName = "Kiadások - Bevételek.xlsx";

    public static final String TimeExcelFileName = "Az_Idő_Maga.xlsx";

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

    public static final Map<ThreeMainType, List<String[]>> WORKBOOK_TYPE_PER_HEADLINE_LIST = new HashMap<>(){{
        put(Time, new ArrayList<>(){{
            add(SUMMATimeExcelHeadTitles); add(ÉrtékesTimeExcelHeadTitles);
            add(SzükségesTimeExcelHeadTitles); add(SzabadidőTimeExcelHeadTitles);
        }});
        put(Money, new ArrayList<>(){{
            add(KiadásokMoneyExcelHeadTitles); add(BevételekMoneyExcelHeadTitles);
            add(SUMMAMoneyExcelHeadTitles);
        }});
        put(Measures, new ArrayList<>(){{
            add(AdatokMeasuresExcelHeadTitles);
        }});
    }};

    public static final String delimiterBetweenTextParts = "/";

    public static final int MAX_NUMBER_OF_WORKBOOKS = 3;


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


    public static class ActionTerms{

        public static final Map<String, List<String>> ÉrtékesActionTerms = new HashMap<>(){{
            put("Meló", new ArrayList<>(){{
                add("Meló"); add("Meló:"); add("Munka"); add("Munka"); add("meló"); add("munka"); add("MELÓ"); add("MUNKA");
                add("Interjúk"); add("Interjú"); add("interjúk"); add("interjú"); add("Interjúk"); add("próba feladat"); add("emailek");
                add("Emailek"); add("Próba feladat"); add("Próbafeladat"); add("próbafeladat"); add("Próba feladatok"); add("Próbafeladatok");
                add("próbafeladatok"); add("próba feladatok"); add("Munka keresés"); add("Munkakeresés"); add("munka keresés"); add("munkakeresés");

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
                add("");
            }});
            put("Ebéd és vagy főzés", new ArrayList<>(){{
                add("");
            }});
            put("Esti tevékenységek", new ArrayList<>(){{
                add("");
            }});
            put("Utazás (Tevékenység nélkül / Valós idejű)", new ArrayList<>(){{
                add("");
            }});
            put("Bevásárlás", new ArrayList<>(){{
                add("");
            }});
            put("Takarítás", new ArrayList<>(){{
                add("");
            }});
            put("Mosás + teregetés", new ArrayList<>(){{
                add("");
            }});
            put("Mosogatás", new ArrayList<>(){{
                add("");
            }});
            put("Vasalás", new ArrayList<>(){{
                add("");
            }});
            put("Daily", new ArrayList<>(){{
                add("");
            }});
            put("Egyéb (Összesen / Darabolva)", new ArrayList<>(){{
                add("");
            }});
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
