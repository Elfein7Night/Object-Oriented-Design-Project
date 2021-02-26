package Model.Command;

import Model.Pair;

import java.util.List;

public class GetProfitsCommand implements GetCommand<List<Pair<String, Integer>>> {

    private final StoreCommand storeCommand;

    public GetProfitsCommand(StoreCommand storeCommand) {
        this.storeCommand = storeCommand;
    }

    @Override
    public List<Pair<String, Integer>> get() {
        return storeCommand.getProfits();
    }
}
