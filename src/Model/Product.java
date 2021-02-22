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

    public String getSerialNum() {
        return serialNum;
    }

    public int getCustomerPrice() {
        return customerPrice;
    }

    public int getStorePrice() {
        return storePrice;
    }

    public Product(
            String _name,
            String _serialNum,
            int _storePrice,
            int _customerPrice,
            Customer _customer
    ) throws MyException {
        if (_storePrice < 0) throw new MyException("Store Price Can't Be Negative");
        if (_customerPrice < 0) throw new MyException("Customer Price Can't Be Negative");

        name = _name;
        serialNum = _serialNum;
        storePrice = _storePrice;
        customerPrice = _customerPrice;
        customer = _customer;
    }

    public static Comparator<String> compareBySerialNum() {
        return Comparator.naturalOrder();
    }

    public static Comparator<String> compareBySerialNumReversed() {
        return Comparator.reverseOrder();
    }

    public static Comparator<String> compareByInsertOrder() {
        return (a, b) -> 1;
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


    /*  getters specifically for PropertyValueFactory lookup    */

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return customer.getPhoneNumber();
    }

    public String getCustomerName() {
        return customer.getCustomerName();
    }

    public boolean isSubscribedStatus() {
        return customer.isSubscribed();
    }

    /*  ******************************************************* */
}
