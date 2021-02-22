package Model;

import java.util.*;
import java.util.stream.Collectors;

public class Store {

    private TreeMap<String, Product> productsMap;
    private final FileManager fileManager;
    private final Stack<MapMemento> history;
    private final SubscribersNotifier subscribersNotifier;

    public Store() {
        fileManager = new FileManager();
        history = new Stack<>();
        subscribersNotifier = SubscribersNotifier.getInstance();
    }

    public boolean initMap(int order) {
        switch (order) {
            case 1:
                productsMap = new TreeMap<>(Product.compareBySerialNum());
                break;
            case 2:
                productsMap = new TreeMap<>(Product.compareBySerialNumReversed());
                break;
            case 3:
                productsMap = new TreeMap<>(Product.compareByInsertOrder());
                break;
            default:
                productsMap = new TreeMap<>();
        }

        if (fileManager.fileExists) {
            loadProductsFromFile();
            return true;
        }
        return false;
    }

    private void addProduct(Product product) {
        history.push(createMemento());
        productsMap.put(product.getSerialNum(), product);
        fileManager.add(product);
    }

    public void undoAdd() throws MyException {
        try {
            setMemento(history.pop());
            fileManager.clear();
            productsMap.values().forEach(fileManager::add);
        } catch (EmptyStackException e) {
            throw new MyException("No Operations To Undo...");
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

    public List<Pair<String, Integer>> getProfits() {
        return productsMap.values().stream()
                .map(product -> new Pair<>(
                        product.getSerialNum(),
                        product.getCustomerPrice() - product.getStorePrice())
                ).collect(Collectors.toList());
    }

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

    public List<Message> getSubscriptionsResponses() {
        return subscribersNotifier.getReceiveMessages();
    }
}
