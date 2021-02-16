package Model;

import java.io.Serializable;

public class Product implements Serializable {

    public final String name;
    public final String serialNum;
    private final int storePrice;
    private final int customerPrice;
    private final Customer customer;

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
