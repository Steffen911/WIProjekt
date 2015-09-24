package serverKommunikation;

import java.util.Timer;

public class TimerStart{
	
	public void startTimer(int sekunden){
		Timer timer = new Timer();

		// Start in 2 Sekunden
    	timer.schedule( new Task(), (sekunden * 1000) );

		// Start in einer Sekunde dann Ablauf alle 5 Sekunden
	    //timer.schedule( new Task(), 1000, 5000 );
	}
}
