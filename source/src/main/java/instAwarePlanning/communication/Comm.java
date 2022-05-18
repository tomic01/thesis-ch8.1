package instAwarePlanning.communication;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

public class Comm {

	final static public String adress = "localhost";
	final static public int port = 12347;
	
	private Socket socket;
	private FeedbackClient feedback;
	
	// NOTE: 
	// volatile is to ensure update of the MsgQueue in different threads running on different processors
	// Without it, the static MsgQueue is cached on the processor running thread and thus not updated
	private static volatile Queue<String> MsgQueue = new LinkedList<>();
	
	public Comm() {
		try {
			socket = new Socket(adress, port);
			feedback = new FeedbackClient(socket);
			new DispatchSender(socket, MsgQueue);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void addDispatchMsg(String msg) {
		MsgQueue.add(msg);
	}
	
	public void registerToFeedback(IFeedbackObserver observer) {
		feedback.register(observer);
	}
	
	public void unRegisterToFeedback(IFeedbackObserver observer) {
		feedback.unregister(observer);
	}
}
