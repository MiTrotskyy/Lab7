package ru.general.basic_classes;

import java.util.ArrayList;

/**
 * Enum класс возможных настроений
 */

public enum Mood{
    SADNESS,
    LONGING,
    GLOOM,
    RAGE,
    FRENZY;

    /**
     * Метод получения всех значений в виде динамического массива их строчных представлений
     * @return массив ArrayList с значениями класса
     */

    public static ArrayList<String> getArrayList(){
        Mood[] moodArray = Mood.values();
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 5; i++){
            list.add(moodArray[i].toString());
        }
        return list;
    }
}