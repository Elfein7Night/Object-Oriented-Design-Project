package Model.Command;

import Model.Product;

import java.util.List;

public class GetAllProductsCommand implements GetCommand<List<Product>> {

    private final StoreCommand storeCommand;

    public GetAllProductsCommand(StoreCommand storeCommand) {
        this.storeCommand = storeCommand;
    }

    @Override
    public List<Product> get() {
        return storeCommand.getAllProducts();
    }
}
