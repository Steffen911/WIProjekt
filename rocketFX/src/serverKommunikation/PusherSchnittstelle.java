package serverKommunikation;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;

public class PusherSchnittstelle implements ConnectionEventListener, PrivateChannelEventListener {

	// Variablen definieren
	static final String api_id = "142236";
	static final String api_key = "d7d48c4729507d0b320f";
	static final String api_channel = "private-channel";
	static final String api_event = "MoveToAgent";

	private final Pusher pusher;
	private final PrivateChannel privateChannel;

	public PusherSchnittstelle() {

		// Authorizer einbinden
		// Pfad fuer Authorizer nur Authorizter! Client API
		HttpAuthorizer authorizer = new HttpAuthorizer("http://kc-holzkoepfe.de/rocket/auth.php?pw=rocket");
		PusherOptions opt = new PusherOptions();
		opt.setEncrypted(true);
		opt.setAuthorizer(authorizer);

		// Pusherinstanz erzeugen & Connection durchfuehren
		pusher = new Pusher(api_key, opt);
		pusher.disconnect();
		pusher.connect(this);

		// Channel abonnieren
		privateChannel = pusher.subscribePrivate(api_channel, new PrivateChannelEventListener() {

			// Authentication-Fehler ausgeben
			@Override
			public void onAuthenticationFailure(String message, Exception e) {
				System.out.println(String.format("Authentication-Fehler: [%s], Exception: [%s]", message, e));
			}

			// Erfolgreiche Subscription melden
			@Override
			public void onSubscriptionSucceeded(String arg0) {
				System.out.println("Subscription zu Channel " + api_channel + " durchgefuehrt.");
			}

			// Event
			@Override
			public void onEvent(String arg0, String arg1, String arg2) {
				System.out.println(arg0 + arg1 + arg2);
			}
		});

		// Channel binden und Events abfangen

		privateChannel.bind(api_event, new PrivateChannelEventListener() {
			@Override
			public void onEvent(String channel, String event, String data) {
				System.out.println("Event empfangen:\n " + data);
			}

			@Override
			public void onSubscriptionSucceeded(String arg0) {
			}

			@Override
			public void onAuthenticationFailure(String arg0, Exception arg1) {
			}
		});

		System.out.println(pusher.getConnection().getState().toString());

		// Dauerschleife zum Empfang von Events
		while (true) {

			 try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if ("CONNECTED".equals(pusher.getConnection().getState().toString())) {
				privateChannel.trigger("client-event", "{\"data\": \"4\"}");
				System.out.println("Nachricht wurde gesendet.");
			}
			
		}
	}

	// Methoden
	@Override
	public void onEvent(String arg0, String arg1, String arg2) {
		System.out.println(arg0 + arg1 + arg2);
	}

	@Override
	public void onSubscriptionSucceeded(String arg0) {
		System.out.println(arg0);
	}

	@Override
	public void onConnectionStateChange(ConnectionStateChange arg0) {
		System.out.println(arg0.getCurrentState().toString());
	}

	@Override
	public void onError(String arg0, String arg1, Exception arg2) {
		System.out.println(arg0 + arg1 + arg2);
	}

	@Override
	public void onAuthenticationFailure(String arg0, Exception arg1) {
		System.out.println(arg0 + arg1);
	}
	
}