package instAwarePlanning.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FeedbackClient implements IObservable {

	List<IFeedbackObserver> observers = new ArrayList<>();

	public FeedbackClient(Socket socket) {

		new Thread() {
			@Override
			public void run() { // read from the input stream
				System.out.println("[FeedbackClient] Listening Started ");

				try {
					BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

					String line;
					while ((line = in.readLine()) != null) {
						System.out.println("[FeedbackClient] Recieved: " + line);
						for (IFeedbackObserver obs : observers) {
							// System.out.println("[FeedbackClient] Updating Observers...");
							obs.Update(line);
						}
					}
				} catch (IOException e) {

				}

			}

		}.start();

	}

	@Override
	public void register(IFeedbackObserver obs) {
		System.out.println("[FeedbackClient] Component registered: " + obs.toString());
		observers.add(obs);

	}

	@Override
	public void unregister(IFeedbackObserver obs) {
		System.out.println("[FeedbackClient] Component UN-registered: " + obs.toString());
		observers.remove(obs);
	}
}
