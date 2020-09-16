package ru.general.basic_classes;

import java.util.ArrayList;

/**
 * Enum класс возможных типов оружия
 */

public enum WeaponType {
    AXE,
    PISTOL,
    KNIFE,
    MACHINE_GUN;

    /**
     * Метод получения всех значений в виде динамического массива их строчных представлений
     * @return массив ArrayList с значениями класса
     */
    public static ArrayList<String> getArrayList(){
        WeaponType[] weaponTypeArray = WeaponType.values();
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 4; i++){
            list.add(weaponTypeArray[i].toString());
        }
        return list;
    }
}