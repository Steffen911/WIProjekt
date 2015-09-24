package serverKommunikation;

import java.util.Timer;

public class TimerStart{
	
	public void startTimer(int sekunden){
		Timer timer = new Timer();

		// Start in int sekunden
    	timer.schedule( new Task(), (sekunden * 1000) );

	}
}
