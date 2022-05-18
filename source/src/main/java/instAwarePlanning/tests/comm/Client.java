package instAwarePlanning.tests.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
	Socket socket;
	ArrayList<String> feedback;
	public static void main(String[] args) {
		new Client();
	}

	public Client() {
		try {
			socket = new Socket("localhost", 12347);

			new Thread() {

				@Override
				public void run() { // read from the input stream
					System.out.println("Listening: ");
					try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
						String line;
						while ((line = in.readLine()) != null) {
							System.out.println("Server said: " + line);
							feedback = getFeedbackIdFromString(line);
							System.out.println("feedback_id: " + feedback);
							
						}
					} catch (IOException e) {

					}

				}

			}.start();

		} catch (IOException e) {

		}

	}
	
	private ArrayList<String> getFeedbackIdFromString(String line) {
		String[] parts = line.split("#");
		ArrayList<String> feedback = new ArrayList<String>();
		String rawParamString = parts[3];
		
		Pattern p = Pattern.compile("\\((.*?)\\)");
		Matcher m = p.matcher(rawParamString);
		
		while(m.find()) {
			feedback.add(m.group(1));
		    System.out.println(m.group(1));
		}
		
		return feedback;
	}
}
