package laba_8.Commands.SerializedCommands;

import laba_8.Commands.Command;

import java.io.Serializable;

public class SerializedObjectCommand implements Serializable {
    private Command command;
    private Object object;

    public SerializedObjectCommand(Command command, Object object) {
        this.command = command;
        this.object = object;
    }

    public Command getCommand() {
        return command;
    }

    public Object getObject() {
        return object;
    }
}