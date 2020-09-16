package ru.commands;

import ru.general.human_being_controller.HumanBeingMap;

/**
 * Класс, реализующий команду remove_key key - удаление элемента по ключу
 */

public class RemoveKey extends Command{
    private Integer key;
    /**
     * Проверка, что key - целое число, что есть элемент с данным ключом и вызов метода {@link HumanBeingMap#removeHumanBeing(Integer)}
     * @param humanBeingMap класс с коллекцией, над которой производятся действия
     */
    @Override
    public void execute(HumanBeingMap humanBeingMap) {
        if (humanBeingMap.get(key) == null){
            setMessage("Element with this key doesn't exist. To get a list of elements use \"show\" command.");
        }else{
            setMessage("Element " + humanBeingMap.get(key).toString() + " removed");
            humanBeingMap.removeHumanBeing(key);
        }
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
