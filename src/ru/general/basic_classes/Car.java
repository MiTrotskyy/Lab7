package ru.general.basic_classes;

import java.io.Serializable;

/**
 * Класс Car
 */

public class Car implements Serializable {
    /** Поле для названия машины, может быть null*/
    private String name; //Поле может быть null
    /**Поле для "крутости" машины*/
    private Boolean cool;

    /**
     * Конструктор для машины без параметра*/
    public Car() {
        this("Undefined", false);
    }

    /**
     * Конструктор для машины с именем name и крутостью cool
     * @param name имя машины
     * @param cool крутость машины
     */
    public Car(String name, Boolean cool) {
        this.name = name;
        this.cool = cool;
    }

    /**
     * Сеттер для поля name
     * @param name имя машины
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Сеттер для поля cool
     * @param cool крутость машины
     */
    public void setCool(Boolean cool) {
        this.cool = cool;
    }

    /**
     * Геттер поля name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Геттер поля cool
     * @return cool
     */
    public Boolean getCool() {
        return cool;
    }

    /**
     * Переопределение метода toString
     * @return Строковое представление класса
     */
    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", cool=" + cool +
                '}';
    }
}