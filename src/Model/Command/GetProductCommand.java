package Model.Command;

import Model.Product;

public class GetProductCommand implements GetCommand<Product> {

    private final StoreCommand storeCommand;
    private final String serialNum;

    public GetProductCommand(StoreCommand storeCommand, String serialNum) {
        this.storeCommand = storeCommand;
        this.serialNum = serialNum;
    }

    @Override
    public Product get() {
        return storeCommand.getProduct(serialNum);
    }
}
