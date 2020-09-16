package ru.commands;

import ru.general.human_being_controller.HumanBeingMap;

/**
 * Класс, реализующий программу clear, очищающую коллекцию
 */
public class Clear extends Command{
    /**
     * Обращение к методу {@link HumanBeingMap#clearHumanBeingMap()}, очищающего коллекцию
     * @param humanBeingMap класс с коллекцией, над которой производятся действия
     */

    @Override
    public void execute(HumanBeingMap humanBeingMap) {
        humanBeingMap.clearHumanBeingMap();
        setMessage("Collection cleared\n");
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
