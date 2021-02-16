package Model;

import java.util.ArrayList;
import java.util.List;

public class SubscribersNotifier implements Sender, Receiver {

    private static SubscribersNotifier _instance = null;

    public static SubscribersNotifier getInstance() {
        if (_instance == null)
            _instance = new SubscribersNotifier();
        return _instance;
    }

    private final List<Message> receiveMessages;

    public List<Message> getReceiveMessages() {
        return receiveMessages;
    }

    private SubscribersNotifier() {
        receiveMessages = new ArrayList<>();
    }

    @Override
    public void receiveMSG(Sender sender, Message msg) {
        //TODO
    }

    @Override
    public void sendMSG(Receiver receiver, Message msg) {
        receiver.receiveMSG(this, msg);
    }

}
