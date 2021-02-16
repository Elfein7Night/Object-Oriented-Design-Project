package Model;

public class Customer implements Receiver, Sender {

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


    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", subscribedStatus=" + subscribedStatus +
                '}';
    }

    @Override
    public void receiveMSG(Sender sender, Message msg) {
        sendMSG((Receiver) sender, new Message(name));
    }

    @Override
    public void sendMSG(Receiver receiver, Message msg) {
        receiver.receiveMSG(this, msg);
    }
}
