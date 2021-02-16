package Model;

public class Product {

    public final String name;
    public final String serialNum;
    private int storePrice;
    private int customerPrice;
    private Customer purchasingCustomer;

    public Product(String _name,
                   String _serialNum,
                   int _storePrice,
                   int _customerPrice,
                   Customer _purchasingCustomer
    ) {
        name = _name;
        serialNum = _serialNum;
        storePrice = _storePrice;
        customerPrice = _customerPrice;
        purchasingCustomer = _purchasingCustomer;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", serialNum='" + serialNum + '\'' +
                ", storePrice=" + storePrice +
                ", customerPrice=" + customerPrice +
                ", purchasingCustomer=" + purchasingCustomer +
                '}';
    }
}
