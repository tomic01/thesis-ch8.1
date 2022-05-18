package instAwarePlanning.tests.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class SendRecieveTest {
	
	final static public String adress = "localhost";
	final static public int port = 12347;

	public static volatile Queue<String> MsgQueue = new LinkedList<>();

	public static void main(String[] args) throws IOException {

		Socket socket = new Socket(adress, port);
		

		SendRecieveTest.StartListening(socket);
		//SendRecieveTest.StartListening2(socket);
		SendRecieveTest.StartDispatching(socket);

		String msgToSend = new String(
				"publish#D7.1#[{(id 1222) (type give) (robot pepper01) (apar sponge 1.0 100 1.1)}]");
		String msgToSend2 = new String(
				"publish#D7.1#[{(id 2222) (type give) (robot pepper02) (apar sponge 1.0 100 1.1)}]");

		System.out.println("Sending message 1");
		SendRecieveTest.addMessage(msgToSend);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Sending message 2");
		SendRecieveTest.addMessage(msgToSend2);
		
		System.out.println("All send");

	}
	
	public synchronized static void addMessage(String msg) {
		MsgQueue.add(msg);
	}
	
	public static void StartDispatching(Socket socket) {
		new Thread() {
			@Override
			public void run() {
				try {
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					
					while(true) {
						String msg = MsgQueue.poll();
						
						if (msg!=null) {
							System.out.println("[DispatchSender] Sending: " + msg);
							out.println(msg);
						}
						
						Thread.sleep(1000);
					}
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void StartListening(Socket socket) {
		new Thread() {

			@Override
			public void run() { // read from the input stream
				System.out.println("[FeedbackClient] Listening Started ");
				try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
					String line;
					while ((line = in.readLine()) != null) {
						System.out.println("[FeedbackClient] Recieved: " + line);
					}
				} catch (IOException e) {

				}

			}
		}.start();

	}
	
	public static void StartListening2(Socket socket) {
		new Thread() {

			@Override
			public void run() { // read from the input stream
				
				System.out.println("[FeedbackClient] Listening Started ");
				
				while(true) {
					try {
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						System.out.println("[FeedbackClient] Recieved: " + in.readLine());
					}
					catch (IOException e) {

					}
					
				}
				
			}
		}.start();

	}

}
