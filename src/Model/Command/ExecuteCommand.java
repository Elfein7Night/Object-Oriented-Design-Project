package Model.Command;

import Model.MyException;

public interface ExecuteCommand {
    void execute() throws MyException;
}
