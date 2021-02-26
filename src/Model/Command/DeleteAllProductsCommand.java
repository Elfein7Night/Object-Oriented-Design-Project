package Model.Command;

public class DeleteAllProductsCommand implements ExecuteCommand {

    private final StoreCommand storeCommand;

    public DeleteAllProductsCommand(StoreCommand storeCommand) {
        this.storeCommand = storeCommand;
    }

    @Override
    public void execute() {
        storeCommand.deleteAllProducts();
    }
}
