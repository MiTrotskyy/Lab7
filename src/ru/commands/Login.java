package ru.commands;

import ru.data_base.DataBaseController;
import ru.general.User;
import ru.general.human_being_controller.HumanBeingMap;

public class Login extends Command {

    public Login() {
        this.setMessage("login");
    }

    @Override
    public void execute(HumanBeingMap humanBeingMap) {
        User checkUser = DataBaseController.getUser(this.getUser().getUsername());
        if (checkUser == null) {
            this.setMessage("Username doesn't exist");
        } else if (!checkUser.getPassword().equals(getUser().getPassword())) {
                this.setMessage("Invalid password");
            } else {
            this.setMessage("Login complete");
        }
    }

    @Override
    public boolean isValid() {
        return this.getMessage().equals("Login complete");
    }
}
