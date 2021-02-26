package Model.Command;

import Model.Observer.Message;

import java.util.List;

public class GetSubscriptionsResponsesCommand implements GetCommand<List<Message>> {

    private final StoreCommand storeCommand;

    public GetSubscriptionsResponsesCommand(StoreCommand storeCommand) {
        this.storeCommand = storeCommand;
    }

    @Override
    public List<Message> get() {
        return storeCommand.getSubscriptionsResponses();
    }
}
