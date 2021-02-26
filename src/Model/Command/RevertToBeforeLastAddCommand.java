package Model.Command;

import Model.MyException;

public class RevertToBeforeLastAddCommand implements ExecuteCommand {

    private final StoreCommand storeCommand;

    public RevertToBeforeLastAddCommand(StoreCommand storeCommand){
        this.storeCommand = storeCommand;
    }

    @Override
    public void execute() throws MyException {
        storeCommand.revertToBeforeLastAdd();
    }
}
