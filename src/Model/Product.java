package Model;

import java.io.Serializable;
import java.util.Comparator;

public class Product implements Serializable {

    public final String name;
    public final String serialNum;
    private final int storePrice;
    private final int customerPrice;
    private final Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public int getCustomerPrice() {
        return customerPrice;
    }

    public int getStorePrice() {
        return storePrice;
    }

    public Product(String _name,
                   String _serialNum,
                   int _storePrice,
                   int _customerPrice,
                   Customer _customer
    ) {
        name = _name;
        serialNum = _serialNum;
        storePrice = _storePrice;
        customerPrice = _customerPrice;
        customer = _customer;
    }

    public static Comparator<Product> compareBySerialNum() {
        return Comparator.comparing(a -> a.serialNum);
    }

    public static Comparator<Product> compareBySerialNumReversed() {
        return (a, b) -> b.serialNum.compareTo(a.serialNum);
    }

    public static Comparator<Product> compareByInsertOrder() {
        return (a, b) -> 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", serialNum='" + serialNum + '\'' +
                ", storePrice=" + storePrice +
                ", customerPrice=" + customerPrice +
                ", purchasingCustomer=" + customer +
                '}';
    }
}
