package Model;

import java.io.Serializable;

public class Customer implements Serializable, Receiver, Sender {

    public final String name;
    private final String phoneNumber;
    private boolean subscribedStatus;

    public String getPhoneNumber() {
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

    public Customer(String _name, String _phoneNumber, boolean _subscribed) throws MyException {
        validatePhoneNumber(_phoneNumber);
        name = _name;
        phoneNumber = _phoneNumber;
        subscribedStatus = _subscribed;
    }

    private void validatePhoneNumber(String _phoneNumber) throws MyException {
        if (!_phoneNumber.matches("[0-9]+")) {
            throw new MyException("Phone Number Can Only Contain Digits");
        }
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
