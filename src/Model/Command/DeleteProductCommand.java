package Model.Command;

public class DeleteProductCommand implements ExecuteCommand {

    private final StoreCommand storeCommand;
    private final String serialNum;

    public DeleteProductCommand(StoreCommand storeCommand, String serialNum) {
        this.storeCommand = storeCommand;
        this.serialNum = serialNum;
    }

    @Override
    public void execute() {
        storeCommand.deleteProduct(serialNum);
    }
}
