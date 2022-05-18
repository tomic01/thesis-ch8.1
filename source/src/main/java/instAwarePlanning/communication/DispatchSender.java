package instAwarePlanning.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

public class DispatchSender {

	public DispatchSender(Socket socket, Queue<String> MsgQueue) {
		
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

}
