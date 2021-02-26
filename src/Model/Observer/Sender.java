package Model.Observer;

//Observable
public interface Sender {
	void sendMSG(Receiver receiver, Message msg);
}
