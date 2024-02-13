package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Editor Editor;

    private static Scanner TextScanner;

    private static String DayText;


    public static void main(String[] args) throws IOException {
        AdministrateDay();
    }


    public static void AdministrateDay() throws IOException {
        TextScanner = new Scanner(System.in);

        DayText = TextScanner.nextLine();

        Editor = new Editor(DayText);

        Editor.modifyWorkbooks();
    }
}