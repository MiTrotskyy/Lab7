package ru.client;

import ru.commands.*;

import java.io.Serializable;
import java.util.*;

public class ClientCommandData implements Serializable {

    private Command command;

    private Map<String, Command> commandMap = new TreeMap<>();

    public ClientCommandData(boolean isExecuteScript){
        if (!isExecuteScript) {
            commandMap.put("execute_script", new ExecuteScript());
        }
        commandMap.put("login", new Login());
        commandMap.put("register", new Register());
        commandMap.put("help", new Help());
        commandMap.put("info", new Info());
        commandMap.put("show", new Show());
        commandMap.put("insert", new Insert());
        commandMap.put("update", new Update());
        commandMap.put("remove_key", new RemoveKey());
        commandMap.put("clear", new Clear());
        commandMap.put("exit", new Exit());
        commandMap.put("remove_greater", new RemoveGreater());
        commandMap.put("remove_lower", new RemoveLower());
        commandMap.put("remove_greater_key", new RemoveGreaterKey());
        commandMap.put("filter_by_mood", new FilterByMood());
        commandMap.put("filter_greater_than_car", new FilterGreaterThanCar());
        commandMap.put("print_descending", new PrintDescending());
    }

    public Command getCommand(){
        return this.command;
    }

    public void setCommand(String input){
        if (input.isEmpty()){
            return;
        }
        this.command = null;
        String[] values = input.split(" ");
        if (values.length == 1){
            Command command = commandMap.get(values[0]);
            if (command != null){
                command.setValue(null);
                this.command = command;
            } else {
                System.out.println("Command doesn't exist");
            }

        }
        if (values.length == 2){
            Command command = commandMap.get(values[0]);
            if (command != null) {
                command.setValue(values[1]);
                this.command = command;
            } else{
                System.out.println("Command doesn't exist");
            }
        }
    }
    public boolean isValid(){
        return command.isValid();
    }
}