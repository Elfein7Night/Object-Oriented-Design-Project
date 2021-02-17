package Model;

import java.util.List;

public interface Command {

    void addProduct(
            String name,
            String serialNum,
            int storePrice,
            int customerPrice,
            String customerName,
            String phoneNumber,
            boolean subscribedStatus
    ) throws MyException;

    void undoAdd();

    void deleteProduct(String serialNum);

    void deleteAllProducts();

    Product getProduct(String serialNum);

    List<Product> getAllProducts();

    List<Pair<String, Integer>> getProfits();

    List<Message> notifySubscriptions(String message);

    void loadProductsFromFile();

}
