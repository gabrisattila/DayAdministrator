package Classes;

import java.util.Arrays;

import static java.util.Objects.isNull;

public class I18N {

    public final static String dataExcelsPath = "C:\\Users\\asus\\Desktop\\Minden\\Adat\\";

    public static final String MeasureExcelFileName = "Mérőszámok.xlsx";

    public static final String MoneyExcelFileName = "Kiadások - Bevételek.xlsx";

    public static final String TimeExcelFileName = "Az_Idő_Maga.xlsx";


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
