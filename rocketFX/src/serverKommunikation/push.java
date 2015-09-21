package serverKommunikation;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.PrivateChannel;
import com.pusher.client.channel.PrivateChannelEventListener;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;

public class push {

	public static void main(String[] args) {
	
		// Create a new Pusher instance
		//HttpAuthorizer authorizer = new HttpAuthorizer("http://example.com/some_auth_endpoint");
		//PusherOptions options = new PusherOptions().setAuthorizer(authorizer);
		Pusher pusher = new Pusher("d7d48c4729507d0b320f");
		
		// Subscribe to a channel
		Channel channel = pusher.subscribe("test_channel");

		//Bind to listen for events called "my-event" sent to "my-channel"
		channel.bind("my_event", new PrivateChannelEventListener() {
			
			@Override
			public void onEvent(String arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSubscriptionSucceeded(String arg0) {
				System.out.println("Connected");
				
			}
			
			@Override
			public void onAuthenticationFailure(String arg0, Exception arg1) {
				System.out.println("Fehler");
				
			}
		});
		
		pusher.connect(new ConnectionEventListener() {
		    @Override
		    public void onConnectionStateChange(ConnectionStateChange change) {
		        System.out.println("State changed to " + change.getCurrentState() +
		                           " from " + change.getPreviousState());
		    }

		    @Override
		    public void onError(String message, String code, Exception e) {
		        System.out.println("There was a problem connecting!");
		    }
		}, ConnectionState.ALL);
		
}
}