package ru.general.human_being_controller;

import ru.general.basic_classes.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Класс HumanBeing
 */

public class HumanBeing implements Serializable {
    /** Поле id, не может быть null, значение больше 0, генерируется автоматически*/
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    /**Поле name, не может быть null, не пустое*/
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**Поле Coordinates, не может быть null*/
    private Coordinates coordinates; //Поле не может быть null
    /**Поле creationDate, не может быть null, генерируется автоматически*/
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /**Поле realHero, не может быть null*/
    private boolean realHero;
    /**Поле hasToothpick, не может быть null*/
    private Boolean hasToothpick; //Поле не может быть null
    /**Поле impactSpeed, не может быть null, значение больше чем -300*/
    private Double impactSpeed; //Поле не может быть null
    /**Поле weaponType, не может быть null*/
    private WeaponType weaponType; //Поле не может быть null
    /**Поле mood, не может быть null*/
    private Mood mood; //Поле не может быть null
    /**Поле car, может быть null*/
    private Car car; //Поле может быть null

    /**
     *
     * @param name имя
     * @param coordinates координаты
     * @param realHero героичность
     * @param hasToothpick наличие зубной палочки
     * @param impactSpeed скорость удара
     * @param weaponType тип оружия
     * @param mood настроение
     * @param car машина
     */
    public HumanBeing(String name, Coordinates coordinates, boolean realHero, Boolean hasToothpick, Double impactSpeed, WeaponType weaponType, Mood mood, Car car) {
        this.id = 0L;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = ZonedDateTime.now();
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.weaponType = weaponType;
        this.mood = mood;
        this.car = car;
    }

    public HumanBeing() {
        this.id = 0L;
        this.creationDate = ZonedDateTime.now();
    }

    /**
     * Геттер id
     * @return id
     */
    public Long getId() {
        return id;
    }
    /**
     * Cеттер id
     * @param id id
     */

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Геттер name
     * @return name
     */

    public String getName() {
        return name;
    }

    /**
     * Сеттер name
     * @param name name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Геттер Coordinates
     * @return coordinates
     */

    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Сеттер Coordinates
     * @param coordinates coordinates
     */

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Геттер creationDate
     * @return creationDate
     */

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Сеттер creationDate
     * @param creationDate creationDate
     */

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Геттер realHero
     * @return realHero
     */

    public boolean getRealHero() {
        return realHero;
    }

    /** Сеттер realHero
     * @param realHero realHero
     */

    public void setRealHero(boolean realHero) {
        this.realHero = realHero;
    }

    /**
     * Геттер hasToothpick
     * @return hasToothpick
     */

    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    /**
     * Сеттер hasToothpick
     * @param hasToothpick hasToothpick
     */

    public void setHasToothpick(Boolean hasToothpick) {
        this.hasToothpick = hasToothpick;
    }

    /**
     * Геттер impactSpeed
     * @return impactSpeed
     */

    public Double getImpactSpeed() {
        return impactSpeed;
    }

    /**
     * Сеттер impactSpeed
     * @param impactSpeed impactSpeed
     */

    public void setImpactSpeed(Double impactSpeed) {
        this.impactSpeed = impactSpeed;
    }

    /**
     * Геттер weaponType
     * @return weaponType
     */

    public WeaponType getWeaponType() {
        return weaponType;
    }

    /**
     * Сеттер weaponType
     * @param weaponType weaponType
     */

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }

    /**
     * Геттер mood
     * @return mood
     */

    public Mood getMood() {
        return mood;
    }

    /**
     * Cеттер mood
     * @param mood mood
     */

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    /**
     * Геттер car
     * @return car
     */

    public Car getCar() {
        return car;
    }

    /**
     * Сеттер car
     * @param car car
     */

    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * Переопределение метода equals
     * @param o Object
     * @return true при равенстве объектов, false в обратном случае
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HumanBeing)) return false;
        HumanBeing that = (HumanBeing) o;
        return getRealHero() == that.getRealHero() &&
                getId().equals(that.getId()) &&
                getName().equals(that.getName()) &&
                getCoordinates().equals(that.getCoordinates()) &&
                getCreationDate().equals(that.getCreationDate()) &&
                getHasToothpick().equals(that.getHasToothpick()) &&
                getImpactSpeed().equals(that.getImpactSpeed()) &&
                getWeaponType() == that.getWeaponType() &&
                getMood() == that.getMood() &&
                getCar().equals(that.getCar());
    }

    /**
     * Переопределение метода hashCode
     * @return hash code
     */

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCoordinates(), getCreationDate(), getRealHero(), getHasToothpick(), getImpactSpeed(), getWeaponType(), getMood(), getCar());
    }

    /**
     * Переопределение метода toString
     * @return строковое представление объекта
     */

    @Override
    public String toString() {
        return "HumanBeing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", realHero=" + realHero +
                ", hasToothpick=" + hasToothpick +
                ", impactSpeed=" + impactSpeed +
                ", weaponType=" + weaponType +
                ", mood=" + mood +
                ", car=" + car +
                "}\n";
    }
}