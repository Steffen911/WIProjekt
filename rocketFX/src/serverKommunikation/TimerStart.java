package serverKommunikation;

import java.util.Timer;

public class TimerStart{
	
	public void startTimer(int centisekunden){
		Timer timer = new Timer();

		// Start in int sekunden
    	timer.schedule( new Task(), (centisekunden * 100) );

	}
}
