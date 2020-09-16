package ru.general.basic_classes;

import java.io.Serializable;

/**
 * Класс Coordinates
 */

public class Coordinates implements Serializable {
    /**Поле для координаты x*/
    private double x;
    /**Поле для координаты y*/
    private Double y;

    /**
     * Конструктор без параметров
     */
    public Coordinates() {
        this(0d, 0d);
    }

    /**
     * Конструктор с координатами x и y
     * @param x X
     * @param y Y
     */

    public Coordinates(double x, Double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Сеттер для x
     * @param x x
     */

    public void setX(double x) {
        this.x = x;
    }

    /**
     * Сеттер для y
     * @param y y
     */

    public void setY(Double y) {
        this.y = y;
    }

    /**
     * Геттер x
     * @return поле x
     */

    public double getX() {
        return x;
    }

    /**
     * Геттер  y
     * @return поле y
     */

    public Double getY() {
        return y;
    }

    /**
     * Переопределение метода toString
     * @return Строковое представление класса
     */

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}