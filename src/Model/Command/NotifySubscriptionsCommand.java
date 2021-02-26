package Model.Command;

public class NotifySubscriptionsCommand implements ExecuteCommand {

    private final StoreCommand storeCommand;
    private final String message;

    public NotifySubscriptionsCommand(StoreCommand storeCommand, String message) {
        this.storeCommand = storeCommand;
        this.message = message;
    }

    @Override
    public void execute() {
        storeCommand.notifySubscriptions(message);
    }
}
