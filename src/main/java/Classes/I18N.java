package Classes;

import java.util.Arrays;

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
                    "Reggeli rutin", "Ebéd és vagy főzés", "Esti rutin", "Utazás (Tevékenység nélkül / Valós idejű)",
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

}
