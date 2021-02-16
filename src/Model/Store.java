package Model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Store {


    private Map<String, Product> map;
    private FileManager fileManager;


    public Store() {
        map = new TreeMap<>();
    }

    public void addProduct(Product product) {
        map.put(product.serialNum, product);
        fileManager.add(product);
    }

    // memento + fileManager
    public void undoAdd() {

    }

    public void deleteProduct(String serialNum) {
        fileManager.remove(serialNum);
    }

    public void deleteAllProducts() {
        fileManager.clear();
    }

    public Product getProduct(String serialNum) {
        return map.get(serialNum);
    }

    public List<Product> getAllProducts() {
        return null;
    }

    public List<Pair<String, Integer>> getProfits() {
        return null;
    }

    public List<Message> notifySubscriptions(String message) {
        return null;
    }

    public void loadProductsFromFile() {

    }
}
