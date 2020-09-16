package ru.commands;



import ru.client.ClientCommandData;
import ru.client.ClientSide;
import ru.general.human_being_controller.HumanBeingMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Класс, реализующий команду execute_script filename, исполняющую команды из данного файла
 */

public class ExecuteScript extends Command {

    private ClientCommandData commandData;
    private ArrayList<String> workingScripts;

    public ExecuteScript(ArrayList<String> workingScripts) {
        commandData = new ClientCommandData(true);
        this.workingScripts = workingScripts;
    }

    public ExecuteScript() {
        commandData = new ClientCommandData(true);
        this.workingScripts = new ArrayList<>();
    }

    /**
     * Переопределенный метод реализующий проверку на вызов сразу двух execute_script, если команда отсутствует-создается
     *
     * @param humanBeingMap класс с коллекцией, над которой производятся действия
     */
    @Override
    public void execute(HumanBeingMap humanBeingMap) {
    }

    @Override
    public boolean isValid() {
        String fileName = getValue();
        try (BufferedReader bufferedReader = new BufferedReader((new FileReader(fileName)))) {
            workingScripts.add("execute_script " + fileName);
            String input = bufferedReader.readLine();
            while (input != null) {
                if (input.contains("execute_script")) {
                    if (workingScripts.contains(input)) {
                        System.out.println(input.split(" ")[1] + " is already working. Command skipped.\n");
                    } else {
                        Command command = new ExecuteScript(workingScripts);
                        System.out.println(input.split(" ")[1]);
                        command.setValue(input.split(" ")[1]);
                        command.setUser(getUser());
                        command.isValid();
//                        command.execute(humanBeingMap);
                    }
                } else {
                    commandData.setCommand(input);
                    Command command = commandData.getCommand();
                    command.setUser(getUser());
                    command = ClientSide.exchangeCommands(command);
                    System.out.println(command.getMessage());
                    if (command.getMessage().contains("Logging out")){
                        System.exit(0);
                    }
                }
                input = bufferedReader.readLine();
            }
        } catch (IOException ex) {
            System.out.println("File " + fileName + " not found or access denied.\n");
        } catch (Exception e) {
            System.out.println("Invalid input in execute_script " + fileName + "\n");
            e.printStackTrace();
        }
        System.out.println("script " + fileName + " finished working");
        setMessage("script");
        workingScripts.remove("execute_script " + fileName);
        return true;
    }
}