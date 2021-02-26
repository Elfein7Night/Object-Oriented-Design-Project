package Model.Command;

import Model.Observer.Message;
import Model.MyException;
import Model.Pair;
import Model.Product;

import java.util.List;

public interface StoreCommand {

    void addProduct(
            String name,
            String serialNum,
            int storePrice,
            int customerPrice,
            String customerName,
            String phoneNumber,
            boolean subscribedStatus
    ) throws MyException;

    void revertToBeforeLastAdd() throws MyException;

    void deleteProduct(String serialNum);

    void deleteAllProducts();

    Product getProduct(String serialNum);

    List<Product> getAllProducts();

    List<Pair<String, Integer>> getProfits();

    void notifySubscriptions(String message);

    List<Message> getSubscriptionsResponses();
}
