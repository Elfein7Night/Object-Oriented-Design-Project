package Model.Command;

import Model.*;
import Model.Iterator.FileManager;
import Model.Memento.MapMemento;
import Model.Observer.Customer;
import Model.Observer.Message;
import Model.Observer.Singleton.SubscribersNotifier;

import java.util.*;
import java.util.stream.Collectors;

public class Store implements StoreCommand {

    private TreeMap<String, Product> productsMap;
    private final FileManager fileManager;
    private final Stack<MapMemento> history;
    private final SubscribersNotifier subscribersNotifier;

    // for a safer and more readable switch case implementation.
    public enum Order {BY_SERIAL_NUM, BY_SERIAL_NUM_REVERSED, BY_INSERT_ORDER}

    public Store() {
        fileManager = new FileManager();
        history = new Stack<>();
        subscribersNotifier = SubscribersNotifier.getInstance();
    }

    public boolean initMap(Order order) {
        switch (order) {
            case BY_SERIAL_NUM:
                productsMap = new TreeMap<>(Product.compareBySerialNum());
                break;
            case BY_SERIAL_NUM_REVERSED:
                productsMap = new TreeMap<>(Product.compareBySerialNumReversed());
                break;
            case BY_INSERT_ORDER:
                productsMap = new TreeMap<>(Product.compareByInsertOrder());
                break;
            default:
                productsMap = new TreeMap<>();
        }

        if (!fileManager.isEmpty()) {
            loadProductsFromFile();
            return true;    // indicate products were loaded
        }
        return false;
    }

    public void addProduct(
            String name,
            String serialNum,
            int storePrice,
            int customerPrice,
            String customerName,
            String phoneNumber,
            boolean subscribedStatus
    ) throws MyException {
        addProduct(new Product(
                name,
                serialNum,
                storePrice,
                customerPrice,
                new Customer(customerName, phoneNumber, subscribedStatus)
        ));
    }

    private void addProduct(Product product) {
        history.push(createMemento());
        productsMap.put(product.getSerialNum(), product);
        fileManager.add(product);
    }

    public void revertToBeforeLastAdd() throws MyException {
        try {
            setMemento(history.pop());                          //  revert map
            fileManager.clear();                                //  \
            productsMap.values().forEach(fileManager::add);     //  - revert file
        } catch (EmptyStackException e) {
            throw new MyException("No States To Revert To...");
        }
    }

    public void deleteProduct(String serialNum) {
        /*
            only if we deleted something we reload from file,
            no need to such heavy operations for no reason
         */
        if (fileManager.remove(serialNum)) {
            productsMap.clear();
            loadProductsFromFile();
        }
    }

    public void deleteAllProducts() {
        fileManager.clear();
        productsMap.clear();
        loadProductsFromFile();
    }

    public Product getProduct(String serialNum) {
        return productsMap.get(serialNum);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productsMap.values());
    }

    // map the products into a list of pairs, each pair is the product's serialNum and its profit.
    public List<Pair<String, Integer>> getProfits() {
        return productsMap.values().stream()
                .map(product -> new Pair<>(
                        product.getSerialNum(),
                        product.getCustomerPrice() - product.getStorePrice()))
                .collect(Collectors.toList());
    }

    // Get all subscribed customers, then notify them.
    public void notifySubscriptions(String message) {
        productsMap.values().stream()
                .map(Product::getCustomer)
                .filter(Customer::isSubscribed)
                .forEach(customer -> subscribersNotifier.sendMSG(customer, new Message(message)));
    }

    private void loadProductsFromFile() {
        fileManager.loadMapFromFile(productsMap);
    }

    public void setMemento(MapMemento memento) {
        productsMap = memento.getMemento();
    }

    public MapMemento createMemento() {
        return new MapMemento(productsMap);
    }

    public List<Message> getSubscriptionsResponses() {
        return subscribersNotifier.getReceiveMessages();
    }
}
