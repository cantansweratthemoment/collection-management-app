package laba_6.Commands.SerializedCommands;

import laba_6.Commands.Command;

import java.io.Serializable;

public class SerializedSimplyCommand implements Serializable {
    private Command command;

    public SerializedSimplyCommand(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}