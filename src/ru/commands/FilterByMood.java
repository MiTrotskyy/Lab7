package ru.commands;

import ru.general.basic_classes.Mood;
import ru.general.human_being_controller.HumanBeingMap;

import java.util.ArrayList;

/**
 * Класс, реализующий команду filter_by_mood mood, вывод элементов коллекции по значению поля mood
 */
public class FilterByMood extends Command{
    private Mood mood;
    /**
     * Значение проверяется на наличие в {@link Mood}, при наличии находятся и выводятся все элементы карты в {@link HumanBeingMap} у которых поле mood равно заданному
     * @param humanBeingMap класс с коллекцией, над которой производятся действия
     */
    @Override
    public void execute(HumanBeingMap humanBeingMap) {
        if (humanBeingMap.isEmpty()){
            updateMessage("Collection is empty.");
        }else {
            humanBeingMap.entrySet().stream()
                    .filter(entry -> entry.getValue().getMood().equals(mood))
                    .forEach(entry -> updateMessage("Key: " + entry.getKey() + " Value: " + entry.getValue().toString() + "\n"));
        }
    }

    @Override
    public boolean isValid() {
        ArrayList<String> moodList = Mood.getArrayList();
        try {
            if (!(moodList.contains(getValue().toUpperCase()))) {
                System.out.println("Invalid input. Please use this command with mood from the list: " + String.join(", ", moodList));
                return false;
            }else {
                mood = Mood.valueOf(getValue().toUpperCase());
                return true;
            }
        }catch(NullPointerException e){
            System.out.println("Invalid input. Please use this command with mood from the list: " + String.join(", ", moodList));
            return false;
        }
    }
}
