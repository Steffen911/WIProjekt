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
	
	//TimerMethode wird initialisiert
	TimerStart ts = new TimerStart();
		
	//Spielzugdauer wird festgelegt
	int centisekunden;
	
	//Stehen fest
	static final String apiChannel = "private-channel";
	static final String apiEvent = "MoveToAgent";

	private Pusher pusher;
	private PrivateChannel privateChannel;
	private PusherOptions opt = new PusherOptions();
	
	public PushSchnittstelle(String apiKey, String apiSecret, int centisekunden, String spielerwahl){
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.centisekunden = centisekunden;
		
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
	
	public void connect(){
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
					}
				});

	}

	public void disconnect(){
		pusher.disconnect();	
	}
	
	//Writer sendet spielzug an Server
	public void writer(int spielzug){
	
		//Bei erfolgreicher Registrierung wird der Spielzug versendet.
		if ("CONNECTED".equals(pusher.getConnection().getState().toString())) {
			privateChannel.trigger("client-event", "{\"move\": \"" + spielzug + "\"}");
			System.out.println("Nachricht wurde gesendet.");
		}
		
	}
	
	//Communicate Methode sendet spielzug an Server und gibt StringArray vom Server zurueck
	public String[] communicate(int spielzug){
		
		writer(spielzug);				
		return reader();
		
	}//end of communicate
	
	//Reader gibt Serverausgabe zurück
	public String[] reader(){
		//String[0] = freigabe
		//String[1] = satzstatus
		//String[2] = gegnerzug
		//String[3] = sieger
		String[] returnString = new String[4];
		
				// Channel binden und Events abfangen
				privateChannel.bind(apiEvent, new PrivateChannelEventListener() {
					@Override
					public void onEvent(String channel, String event, String data) {
						
						//TODO: Es werden mehrfach Events empfangen
						ts.startTimer(centisekunden);
						
						//Cut the message part from string
						data = data.replace("{\"message\":\"", "");
						data = data.replace("\"}", "");
						
						System.out.println("Event empfangen: " + data);
						
						//data hat die Form "true # Satz spielen # 2 # offen"
						String[] dataSplit = data.split(" # ");
						
						for(int i=0; i<returnString.length;i++){
							returnString[i] = dataSplit[i];
						}
									
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
			if (returnString[0] != null){
				return returnString;
			}
					
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
		}//end of while

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
