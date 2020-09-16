package ru.commands;

import ru.general.human_being_controller.HumanBeingMap;

import java.util.Map;

/**
 * Класс, реализующий команду show - вывод элементов коллекции в строковом представлении
 */

public class Show extends Command{
    /**
     * Проверка коллекции на пустоту, а затем вывод элементов с ключами
     * @param humanBeingMap класс с коллекцией, над которой производятся действия
     */

    public void execute(HumanBeingMap humanBeingMap) {
        if (humanBeingMap.isEmpty()){
            setMessage("Collection is empty.");
        }else {
            setMessage("");
            for (Map.Entry e : humanBeingMap.entrySet()){
                updateMessage("Key: " + e.getKey() + " Value: " + e.getValue().toString() + "\n");
            }
        }
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
