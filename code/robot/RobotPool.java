package robot;

import java.util.ArrayList;
import java.util.List;

public class RobotPool {
	public static void main(String[] args) {
		
		List<Thread> threads = new ArrayList<>();
		String[] stationNames = {"1", "2", "3"};
		//threads.add(new Thread(new Robot("1")));
//		for (int i = 0; i < stationNames.length; i++) {
//			for (int j = 0; j < 2; j++) {
//				threads.add(new Thread(new Robot(stationNames[i])));
//			}
//			for (int j = 0; j < 2; j++) {
//				threads.add(new Thread(new Drone(stationNames[i])));
//			}
//		}

		for (int i = 0; i < stationNames.length; i++) {
			threads.add(new Thread(new Robot(stationNames[i])));
//			for (int j = 0; j < 2; j++) {
//				threads.add(new Thread(new Robot(stationNames[i])));
//			}
			threads.add(new Thread(new Drone(stationNames[i])));
//			for (int j = 0; j < 2; j++) {
//				threads.add(new Thread(new Drone(stationNames[i])));
//			}
		}
		for (Thread t: threads) {
			t.start();
		}	
	}

}
