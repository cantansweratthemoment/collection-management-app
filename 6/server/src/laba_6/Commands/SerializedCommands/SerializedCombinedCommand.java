package laba_6.Commands.SerializedCommands;
import laba_6.Commands.Command;
import java.io.Serializable;

/**
 * Команда с объектом и аргументом.
 */
public class SerializedCombinedCommand implements Serializable {
    private Command command;
    private Object object;
    private String arg;

    public SerializedCombinedCommand(Command command, Object object, String arg) {
        this.command = command;
        this.object = object;
        this.arg = arg;
    }

    public Command getCommand() {
        return command;
    }

    public Object getObject() {
        return object;
    }

    public String getArg() {
        return arg;
    }
}