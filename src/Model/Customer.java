package Model;

public class Customer {

    public final String name;
    private final int phoneNumber;
    private boolean subscribedStatus;

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public boolean isSubscribed() {
        return subscribedStatus;
    }

    public void setSubscribedStatus(boolean subscribedStatus) {
        this.subscribedStatus = subscribedStatus;
    }

    public Customer(String _name, int _phoneNumber, boolean _subscribed) {
        name = _name;
        phoneNumber = _phoneNumber;
        subscribedStatus = _subscribed;
    }

}
