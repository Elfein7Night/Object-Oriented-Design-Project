package Model;

//Observer
public interface Receiver {
	void receiveMSG(Sender sender, Message msg);
}
