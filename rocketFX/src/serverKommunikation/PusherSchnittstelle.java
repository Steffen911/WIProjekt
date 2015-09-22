package serverKommunikation;

import com.pusher.client.AuthorizationFailureException;
import com.pusher.client.Authorizer;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;

public class PusherSchnittstelle implements ConnectionEventListener, PrivateChannelEventListener {

	// Variablen definieren
	//Werden von GUI uebergeben
	static final String apiKey = "d7d48c4729507d0b320f";
	static final String apiSecret ="b8130fe6eec5ae953e6a";
	
	//Stehen fest
	static final String apiChannel = "private-channel";
	static final String apiEvent = "MoveToAgent";

	private final Pusher pusher;
	private final PrivateChannel privateChannel;

	public PusherSchnittstelle() {

		//PusherOptions definieren
		PusherOptions opt = new PusherOptions();
		opt.setEncrypted(true);
		
		//Authorizer einbinden
		Authorizer auth = new Authorizer() {
			
			public String authorize(String channelName, String socketId) throws AuthorizationFailureException
			{

                System.out.println("The clients channelName: " + channelName);
                System.out.println("The clients socketId: " + socketId);

				
				// String to sign ::= <webSocketId>:<channelName>
				String message = socketId + ":" + channelName;
				
				String hash = HmacSHA256.getHmacSHA256HexDigest(apiSecret, message);
				
				// compose the entire authentication signature
				// <signature> ::= "{"auth":"<appId>:<hash>"}"
				String signature = "{\"auth\":\"" + apiKey + ":" + hash + "\"}";
				
				return signature; // ... wer auch immer das dann auswertet.
			}
			
		};
		
		opt.setAuthorizer(auth);
		

		// Pusherinstanz erzeugen & Connection durchfuehren
		pusher = new Pusher(apiKey, opt);
		pusher.disconnect();
		pusher.connect(this);

		// Channel abonnieren
		privateChannel = pusher.subscribePrivate(apiChannel, new PrivateChannelEventListener() {

			// Authentication-Fehler ausgeben
			@Override
			public void onAuthenticationFailure(String message, Exception e) {
				System.out.println(String.format("Authentication-Fehler: [%s], Exception: [%s]", message, e));
			}

			// Erfolgreiche Subscription melden
			@Override
			public void onSubscriptionSucceeded(String arg0) {
				System.out.println("Subscription zu Channel " + apiChannel + " durchgefuehrt.");
			}

			// Event
			@Override
			public void onEvent(String arg0, String arg1, String arg2) {
				System.out.println(arg0 + arg1 + arg2);
			}
		});

		// Channel binden und Events abfangen

		privateChannel.bind(apiEvent, new PrivateChannelEventListener() {
			@Override
			public void onEvent(String channel, String event, String data) {
				System.out.println("Event empfangen: " + data);
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
				e1.printStackTrace();
			}

			 //move ist der Spalten int -> Spielzug
			 int move = 5;
			 
			if ("CONNECTED".equals(pusher.getConnection().getState().toString())) {
				privateChannel.trigger("client-event", "{\"move\": \"" + move + "\"}");
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