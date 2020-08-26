package robot;

import java.util.ArrayList;
import java.util.List;

import external.TaskQConnection;

public class RobotPool {
	public static void main(String[] args) {
		
		List<Thread> threads = new ArrayList<>();
		String[] stationNames = {"a", "b", "c"};
		for (int i = 0; i < stationNames.length; i++) {
			for (int j = 0; j < 4; i++) {
				threads.add(new Thread(new Robot(stationNames[i])));
			}
			for (int j = 0; j < 4; i++) {
				threads.add(new Thread(new UAV(stationNames[i])));
			}
		}
		for (Thread t: threads) {
			t.start();
		}	
	}

}
