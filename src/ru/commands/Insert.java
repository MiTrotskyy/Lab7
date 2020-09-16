package ru.commands;

import ru.data_base.DataBaseController;
import ru.general.human_being_controller.*;

import javax.xml.crypto.Data;

/**
 * Класс, реализующий команду insert key {element} - вставка в коллекцию элемента по ключу
 */

public class Insert extends Command{
    private Integer key;
    private HumanBeing humanBeing;
    /**
     * Создаётся объект класса {@link HumanBeingReader}, из него HumanBeing добавляется в коллекцию
     * @param humanBeingMap класс с коллекцией, над которой производятся действия
     */
    @Override
    public void execute(HumanBeingMap humanBeingMap) {
        if (!DataBaseController.existingKey(key)) {
            humanBeingMap.addHumanBeing(key, humanBeing);
            setMessage("Element "+ humanBeing.toString() + " added in collection");
        } else {
            setMessage("Element with this key already exist");
        }
    }

    @Override
    public boolean isValid() {
        try{
            key = Integer.parseInt(getValue());
            HumanBeingReader humanBeingReader = new HumanBeingReader();
            humanBeing = humanBeingReader.getHumanBeing();
            return true;
        }catch(NumberFormatException e){
            System.out.println("Key must be integer");
            return false;
        }
    }
}
