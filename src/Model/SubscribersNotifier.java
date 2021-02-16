package Model;

import java.util.List;

public class SubscribersNotifier implements Sender, Receiver {

    private List<List<Message>> receiveMessages;

    @Override
    public void receiveMSG(Sender sender, Message msg) {

    }

    @Override
    public void sendMSG(Receiver receiver, Message msg) {

    }

}
