package ru.general.human_being_controller;

import ru.general.basic_classes.*;

import java.util.ArrayList;
import java.util.Scanner;

public class HumanBeingReader {
    private Scanner in;
    private HumanBeing humanBeing;
    public HumanBeingReader(){
        in = new Scanner(System.in);
        this.humanBeing = new HumanBeing(readName(), readCoordinates(), readRealHero(), readHasToothpick(), readImpactSpeed(), readWeaponType(), readMood(), readCar());
    }
    private String readName(){
        System.out.println("Enter name: ");
        String name = in.nextLine();
        while (name.isEmpty()){
            System.out.println("Name should not be empty. Enter correct name: ");
            name = in.nextLine();
        }
        return name;
    }

    public HumanBeing getHumanBeing() {
        return humanBeing;
    }

    private Coordinates readCoordinates(){
        Double inputX = null, inputY = null;
        while(inputX == null) {
            System.out.println("Enter x coordinate, must be less than 60: ");
            try {
                inputX = Double.parseDouble(in.nextLine());
                if (!(inputX <= 60) ){
                    inputX = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Coordinate should be a number.");
            }
        }
        System.out.println("Enter y coordinate: ");
        while(inputY == null) {
            try {
                inputY = Double.parseDouble(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Coordinate should be a number.");
            }
        }
        Coordinates coordinates = new Coordinates(inputX, inputY);
        return coordinates;
    }
    private boolean readRealHero(){
        System.out.println("Is he a real hero? (true or false)");
        String inputRealHero = in.nextLine().toLowerCase();
        while (!(inputRealHero.equals("false") || inputRealHero.equals("true"))){
            System.out.println("Invalid input, must be true or false.\nIs he a real hero?");
            inputRealHero = in.nextLine().toLowerCase();
        }
        return Boolean.parseBoolean(inputRealHero);
    }
    private boolean readHasToothpick(){
        System.out.println("Does he have a toothpick? (true or false)");
        String inputHasToothpick = in.nextLine().toLowerCase();
        while (!(inputHasToothpick.equals("false") || inputHasToothpick.equals("true"))){
            System.out.println("Invalid input, must be true or false.\nDoes he have a toothpick?");
            inputHasToothpick = in.nextLine().toLowerCase();
        }
        return Boolean.parseBoolean(inputHasToothpick);
    }
    private double readImpactSpeed(){
        System.out.println("Enter impact speed(must be greater that -300): ");
        Double ImpactSpeed = null;
        while (ImpactSpeed == null){
            try{
                ImpactSpeed = Double.parseDouble(in.nextLine());
                if (ImpactSpeed <= -300){
                    System.out.println("Value must be greater than -300.\nEnter impact speed: ");
                    ImpactSpeed = null;
                }
            } catch (NumberFormatException e){
                System.out.println("Value must be a number.\nEnter impact speed: ");
            }
        }
        return ImpactSpeed;
    }
    private WeaponType readWeaponType(){
        ArrayList<String> weaponTypeList = WeaponType.getArrayList();
        System.out.println("Enter weapon type from the list: " + String.join(", ", weaponTypeList));
        String input = in.nextLine().toUpperCase();
        while (!weaponTypeList.contains(input)){
            System.out.println("Invalid input. Please enter weapon type from the list: " + String.join(", ", weaponTypeList));
            input = in.nextLine().toUpperCase();
        };
        return WeaponType.valueOf(input);
    }
    private Mood readMood(){
        ArrayList<String> moodList = Mood.getArrayList();
        System.out.println("Enter mood from the list: " + String.join(", ", moodList));
        String input = in.nextLine().toUpperCase();
        while (!(moodList.contains(input))){
            System.out.println("Invalid input. Please enter mood from the list: " + String.join(", ", moodList));
            input = in.nextLine().toUpperCase();
        };
        return Mood.valueOf(input);
    }
    private Car readCar(){
        Car car = new Car();
        System.out.println("Enter the name of the car, or leave this field empty for \"null\" value: ");
        String value = in.nextLine();
        if (value.isEmpty()){
            return null;
        }else {
            car.setName(value);
            System.out.println("Is this car cool? (true or false)");
            String cool = in.nextLine().toLowerCase();
            while (!(cool.equals("false") || cool.equals("true"))){
                System.out.println("Invalid input, must be true or false.\nIs this car cool?");
                cool = in.nextLine().toLowerCase();
            }
            car.setCool(Boolean.parseBoolean(cool));
        }
        return car;
    }
}