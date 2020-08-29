package robot;

public class UAV implements Runnable {
	String station;
	String id;
	public UAV(String station) {
		super();
		this.station = station;
		this.id = "uav" + System.currentTimeMillis();
	}

	@Override
	public void run() {
		System.out.println("working");
	}

}
