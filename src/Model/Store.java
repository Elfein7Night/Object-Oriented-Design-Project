package Model;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class Store {

    private Map<String, Product> map;
    private FileManager fileManager;
    private Stack<Memento> stack = new Stack<>();

    public Store() {
        map = new TreeMap<>();
    }

    public void addProduct(Product product) {
        stack.push(createMemento());
        map.put(product.serialNum, product);
        fileManager.add(product);
    }

    // memento + fileManager
    public void undoAdd() {
        setMemento(stack.pop());
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

    public void setMemento(Memento memento) {
        map = memento.getMemento();
    }

    public Memento createMemento() {
        return new Memento(map);
    }
}
