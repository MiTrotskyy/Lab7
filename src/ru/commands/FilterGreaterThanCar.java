package ru.commands;

import ru.general.human_being_controller.HumanBeingMap;

public class FilterGreaterThanCar extends Command{
    private String carName;
    /**
     * Проход по карте элементов {@link ru.shared.HumanBeingController.HumanBeing}, проверка и вывод, если поле Car больше заданного
     * @param humanBeingMap класс с коллекцией, над которой производятся действия
     */
    @Override
    public void execute(HumanBeingMap humanBeingMap) {
//        for (Map.Entry e : humanBeingMap.getHumanBeingTreeMap().entrySet()) {
//            if (humanBeingMap.getHumanBeingTreeMap().get(e.getKey()).getCar().getName().compareTo(carName) > 0) {
//                System.out.println("Key: " + e.getKey() + " Value: " + e.getValue().toString());
//            }
//        }
        if (humanBeingMap.getHumanBeingTreeMap().isEmpty()){
            updateMessage("Collection is empty.");
        }else {
            humanBeingMap.entrySet().stream()
                    .filter(entry -> entry.getValue().getCar() != null && entry.getValue().getCar().getName().compareTo(carName) > 0)
                    .forEach(entry -> updateMessage("Key: " + entry.getKey() + " Value: " + entry.getValue().toString() + "\n"));
        }
    }

    @Override
    public boolean isValid() {
        if (getValue().isEmpty()) {
            System.out.println("Invalid input. Field must not be empty.");
            return false;
        }else{
            carName = getValue();
            return true;
        }
    }
}
