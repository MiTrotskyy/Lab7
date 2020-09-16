package ru.commands;

import ru.general.User;
import ru.general.human_being_controller.HumanBeingMap;

/**
 * интерфейс для работы с командами
 */

public interface Executable {
    /**
     * @param humanBeingMap объект класса с коллекцией, над которой производятся действия
     */
    void execute(HumanBeingMap humanBeingMap);
    /**
     * @param user объект класса с данными пользователя
     */
}
