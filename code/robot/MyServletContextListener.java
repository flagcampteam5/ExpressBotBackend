package robot;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener{
	List<Thread> threads = new ArrayList<>();
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String[] stationNames = {"1", "2", "3"};
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

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		for (Thread t: threads) {
			t.interrupt();
		}
		
	}
}
