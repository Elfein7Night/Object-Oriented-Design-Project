package Model;

import java.util.*;
import java.util.stream.Collectors;

public class Store {

    private Map<String, Product> map;
    private final FileManager fileManager;
    private final Stack<Memento> history;
    private final SubscribersNotifier subscribersNotifier;

    public Store() {
        fileManager = new FileManager();
        history = new Stack<>();
        subscribersNotifier = SubscribersNotifier.getInstance();
    }

    public void initMap(int order) {
        switch (order) {
            case 1:
                map = new TreeMap<>(Product.compareBySerialNum());
                break;
            case 2:
                map = new TreeMap<>(Product.compareBySerialNumReversed());
                break;
            case 3:
                map = new TreeMap<>(Product.compareByInsertOrder());
                break;
            default:
                map = new TreeMap<>();
        }
    }

    private void addProduct(Product product) {
        history.push(createMemento());
        map.put(product.serialNum, product);
        fileManager.add(product);
    }

    public void undoAdd() throws MyException {
        try {
            setMemento(history.pop());
            fileManager.clear();
            map.values().forEach(fileManager::add);
        } catch (EmptyStackException e) {
            throw new MyException("No Operations To Undo...");
        }
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
        return new ArrayList<>(map.values());
    }

    public List<Pair<String, Integer>> getProfits() {
        return map.values().stream()
                .map(product -> new Pair<>(
                        product.serialNum,
                        product.getCustomerPrice() - product.getStorePrice())
                ).collect(Collectors.toList());
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

    public void createProduct(
            String name,
            String serialNum,
            int storePrice,
            int customerPrice,
            String customerName,
            String phoneNumber,
            boolean subscribedStatus
    ) throws MyException {
        Product product = new Product(
                name,
                serialNum,
                storePrice,
                customerPrice,
                new Customer(customerName, phoneNumber, subscribedStatus)
        );
        addProduct(product);
    }
}
