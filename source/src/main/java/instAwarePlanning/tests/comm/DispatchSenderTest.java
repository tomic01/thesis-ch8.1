package instAwarePlanning.tests.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import instAwarePlanning.SAM.SamSimple;

public class DispatchSenderTest {
	
	final static public String adress = "localhost";
	final static public int port = 12347;
	Socket socketSend;
	String msg1 = "publish#D7.1#[{(id 2222) (type give) (robot pepper01) (apar sponge 1.0 100 1.1)}]";

	public static void main(String[] args) throws IOException {

		//new SamSimple();
		
		String msgToSend = new String("publish#D7.1#[{(id 1222) (type give) (robot pepper01) (apar sponge 1.0 100 1.1)}]");

		// DispatchSenderTest senderTest = new DispatchSenderTest(socketSend, toSend);
		DispatchSenderTest test = new DispatchSenderTest();
		test.Dispatch(msgToSend);
		
		String msgToSend2 = new String("publish#D7.1#[{(id 2222) (type give) (robot pepper02) (apar sponge 1.0 100 1.1)}]");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		DispatchSenderTest test2 = new DispatchSenderTest();
		test2.Dispatch(msgToSend2);
		
	}

	public DispatchSenderTest() throws UnknownHostException, IOException {
		socketSend = new Socket(adress, port);
	}

	public void Dispatch(String msg) {

		new Thread() {
			@Override
			public void run() {
				try {
					
					//socketSend = new Socket(adress, port);
					PrintWriter out = new PrintWriter(socketSend.getOutputStream(), true);
					System.out.println("[DispatchSender] Sending: " + msg);
					out.println(msg);

					//BufferedReader input = new BufferedReader(new InputStreamReader(socketSend.getInputStream()));
					//System.out.println(input.readLine());
					
					Thread.sleep(Integer.MAX_VALUE);
					
					socketSend.close();
					
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				} finally {
					
				}

				// BufferedReader input = new BufferedReader(new InputStreamReader(socket.
				// getInputStream()));
				// while (!input.readLine().isEmpty()) {
				// String answer = input.readLine();
				// System.out.println(answer);
				// System.out.print(".");
				// }

				// System.out.println("All sent");

			}
		}.start();

	}
}

//@Override
//public void run() {
//	Socket client;
//	try {
//		client = new Socket("localhost", 12347);
//		OutputStream outToServer = client.getOutputStream();
//		DataOutputStream out = new DataOutputStream(outToServer);
//		//System.out.println("[DispatchSender] Sending: " + dispatchMsg);
//		
//		out.writeBytes(dispatchMsg);
//		
//		
//		client.close();
//
//	} catch (IOException e) {
//		System.out.println("[DispatchSender] Failed to send message over the socket");
//		System.out.println(e);
//	}
//}

//public static void dispatchMsg(String dispatchMsg) {
//	Socket client;
//	try {
//		client = new Socket("localhost", 12347);
//		OutputStream outToServer = client.getOutputStream();
//		DataOutputStream out = new DataOutputStream(outToServer);
//		System.out.println("[DispatchSender] Sending: " + dispatchMsg);
//		out.writeBytes(dispatchMsg);
//		client.close();
//
//	} catch (IOException e) {
//		System.out.println("[DispatchSender] Failed to send message over the socket");
//		System.out.println(e);
//	}
//}
