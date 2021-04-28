package Model.Observer.Singleton;

import Model.Observer.Message;
import Model.Observer.Receiver;
import Model.Observer.Sender;

import java.util.ArrayList;
import java.util.List;

public class SubscribersNotifier implements Sender, Receiver {

    private static SubscribersNotifier _instance = null;

    public static SubscribersNotifier getInstance() {
        if (_instance == null)
            _instance = new SubscribersNotifier();
        return _instance;
    }

    private final List<Message> receivedMessages;

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    private SubscribersNotifier() {
        receivedMessages = new ArrayList<>();
    }

    @Override
    public void receiveMSG(Sender sender, Message msg) {
        receivedMessages.add(msg);
    }

    @Override
    public void sendMSG(Receiver receiver, Message msg) {
        receiver.receiveMSG(this, msg);
    }

}
