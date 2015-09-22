package serverKommunikation;

import com.pusher.client.AuthorizationFailureException;
import com.pusher.client.Authorizer;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;

public class PushSchnittstelle implements ConnectionEventListener, PrivateChannelEventListener {

	// Variablen definieren
	//Werden von GUI uebergeben
	private String apiKey;
	private String apiSecret;
	
	//Stehen fest
	static final String apiChannel = "private-channel";
	static final String apiEvent = "MoveToAgent";

	private Pusher pusher;
	private PrivateChannel privateChannel;
	private PusherOptions opt = new PusherOptions();
	
	public PushSchnittstelle(String apiKey, String apiSecret){
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		
		//Authorizer einbinden
		Authorizer auth = new Authorizer() {
					
			public String authorize(String channelName, String socketId) throws AuthorizationFailureException {
        
				System.out.println("The clients channelName: " + channelName);
		    	System.out.println("The clients socketId: " + socketId);

						
				// String to sign ::= <webSocketId>:<channelName>
				String message = socketId + ":" + channelName;
						
				String hash = HmacSHA256.getHmacSHA256HexDigest(apiSecret, message);
						
				// compose the entire authentication signature
				// <signature> ::= "{"auth":"<appId>:<hash>"}"
				String signature = "{\"auth\":\"" + apiKey + ":" + hash + "\"}";
						
				return signature;
			} // end of authorize
					
		}; //end of authorizer
		
		//PusherOptions definieren
		opt.setEncrypted(true);
		opt.setAuthorizer(auth);
		
	} //end of constructor
	
	//Communicate Methode sendet spielzug an Server und gibt StringArray vom Server zurück
	public String[] communicate(int spielzug){
		
		String[] returnString = new String[4];
		
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
				
				//Bei erfolgreicher Registrierung wird der Spielzug versendet.
				if ("CONNECTED".equals(pusher.getConnection().getState().toString())) {
					privateChannel.trigger("client-event", "{\"move\": \"" + spielzug + "\"}");
					System.out.println("Nachricht wurde gesendet.");
				}
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
	
		//wait for event and break when server response is received;
		while(true){
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if (returnString[0] != null){
				break;
			}
		}//end of while
		
		//Testweise Ausgabe des Returnstrings in der Konsole
		for (int i=0; i<returnString.length; i++){
			System.out.println(returnString[i]);
		}
		
		return returnString;
		
	}//end of communicate
	
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
