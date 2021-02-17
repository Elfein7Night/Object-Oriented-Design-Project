package Model;

import java.util.List;

public class StoreCommand implements Command {

    private final Store store;

    public StoreCommand() {
        store = new Store();
    }

    public void initMap(int order) {
        store.initMap(order);
    }

    @Override
    public void addProduct(
            String name,
            String serialNum,
            int storePrice,
            int customerPrice,
            String customerName,
            String phoneNumber,
            boolean subscribedStatus
    ) throws MyException {
        store.createProduct(
                name,
                serialNum,
                storePrice,
                customerPrice,
                customerName,
                phoneNumber,
                subscribedStatus
        );
    }

    @Override
    public void undoAdd() throws MyException {
        store.undoAdd();
    }

    @Override
    public void deleteProduct(String serialNum) {
        store.deleteProduct(serialNum);
    }

    @Override
    public void deleteAllProducts() {
        store.deleteAllProducts();
    }

    @Override
    public Product getProduct(String serialNum) {
        return store.getProduct(serialNum);
    }

    @Override
    public List<Product> getAllProducts() {
        return store.getAllProducts();
    }

    @Override
    public List<Pair<String, Integer>> getProfits() {
        return store.getProfits();
    }

    @Override
    public List<Message> notifySubscriptions(String message) {
        return store.notifySubscriptions(message);
    }

    @Override
    public void loadProductsFromFile() {
        store.loadProductsFromFile();
    }
}