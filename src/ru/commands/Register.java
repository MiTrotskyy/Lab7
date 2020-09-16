package ru.commands;

import ru.data_base.DataBaseController;
import ru.general.User;
import ru.general.human_being_controller.HumanBeingMap;

public class Register extends Command {

    public Register() {
        this.setMessage("register");
    }

    @Override
    public void execute(HumanBeingMap humanBeingMap) {
        User checkUser = DataBaseController.getUser(this.getUser().getUsername());
        if (checkUser != null) {
            this.setMessage("This username is already taken");
        } else {
            DataBaseController.createUser(this.getUser());
            this.setMessage("Registration complete");
        }
    }

    @Override
    public boolean isValid() {
        return getMessage().equals("Registration complete");
    }
}
