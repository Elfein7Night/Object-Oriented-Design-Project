package Model;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class Store {

    private Map<String, Product> map;
    private final FileManager fileManager;
    private final Stack<Memento> history;
    private final SubscribersNotifier subscribersNotifier;

    public Store() {
        map = new TreeMap<>();
        fileManager = new FileManager();
        history = new Stack<>();
        subscribersNotifier = SubscribersNotifier.getInstance();
    }

    public void addProduct(Product product) {
        history.push(createMemento());
        map.put(product.serialNum, product);
        fileManager.add(product);
    }

    // memento + fileManager
    public void undoAdd() {
        setMemento(history.pop());
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
        map.values().stream()
                .map(Product::getCustomer)
                .filter(Customer::isSubscribed)
                .forEach(customer -> subscribersNotifier.sendMSG(customer, new Message(message)));

        return subscribersNotifier.getReceiveMessages();
    }

    public void loadProductsFromFile() {
        map = fileManager.getMapFromFile();
    }

    public void setMemento(Memento memento) {
        map = memento.getMemento();
    }

    public Memento createMemento() {
        return new Memento(map);
    }

    public Product createProduct(
            String name,
            String serialNum,
            int storePrice,
            int customerPrice,
            String customerName,
            String phoneNumber,
            boolean subscribedStatus
    ) {

        return null;
    }
}
