package ru.commands;

import ru.general.human_being_controller.HumanBeing;
import ru.general.human_being_controller.HumanBeingMap;

import java.util.TreeMap;

/**
 * Класс, реализующий команду remove_greater_by_key key, удаляющуй все элементы у которых ключ превышает заданный
 */
public class RemoveGreaterKey extends Command{
    private Integer key;
    /**
     * Проверка что ключ - целое число и вызов команды {@link HumanBeingMap#removeGreaterByKey(int)}
     * @param humanBeingMap класс с коллекцией, над которой производятся действия
     */
    @Override
    public void execute(HumanBeingMap humanBeingMap) {
        TreeMap<Integer, HumanBeing> updatedMap = new TreeMap<>();
        humanBeingMap.keySet().stream()
                .filter(entry -> entry <= key)
                .forEach(entry -> updatedMap.put(key, humanBeingMap.getHumanBeingTreeMap().get(key)));
        humanBeingMap.setHumanBeingTreeMap(updatedMap);
        setMessage("Elements with key greater than " + key + " removed");
    }

    @Override
    public boolean isValid() {
        try{
            key = Integer.parseInt(getValue());
            return true;
        }catch(NumberFormatException e){
            System.out.println("Key must be integer");
            return false;
        }
    }
}