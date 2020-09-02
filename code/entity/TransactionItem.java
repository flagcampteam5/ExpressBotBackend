package entity;

import org.json.JSONObject;

public class TransactionItem {
	
	private String trans_id;	
	private int station_id; 
	private boolean start_device_is_robot;
	private boolean end_device_is_robot;
	private int status_id;
	private double pick_up_lat;
	private double pick_up_lon;
	private double deliver_location_lat;
	private double deliver_location_lon;
	private int timestamp;
	
	public String getTrans_id() {
		return trans_id;
	}

	public int getStation_id() {
		return station_id;
	}


	public boolean isStart_device_is_robot() {
		return start_device_is_robot;
	}

	public boolean isEnd_device_is_robot() {
		return end_device_is_robot;
	}

	public int getStatus_id() {
		return status_id;
	}

	public double getPick_up_lat() {
		return pick_up_lat;
	}

	public double getPick_up_lon() {
		return pick_up_lon;
	}

	public double getDeliver_location_lat() {
		return deliver_location_lat;
	}

	public double getDeliver_location_lon() {
		return deliver_location_lon;
	}

	public int getTimestamp() {
		return timestamp;
	}
	

	private TransactionItem(TransactionItemBuilder builder) {
		this.trans_id = builder.trans_id;
		this.station_id = builder.station_id;
		this.start_device_is_robot = builder.start_device_is_robot;
		this.end_device_is_robot = builder.end_device_is_robot;
		this.status_id = builder.status_id;
		this.pick_up_lat = builder.pick_up_lat;
		this.pick_up_lon = builder.pick_up_lon;
		this.deliver_location_lat = builder.deliver_location_lat;
		this.deliver_location_lon = builder.deliver_location_lon;
		this.timestamp = builder.timestamp;		
	}

	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put("trans_id", trans_id);
		obj.put("station_id", station_id);
		obj.put("start_device_is_robot", start_device_is_robot);
		obj.put("end_device_is_robot", end_device_is_robot);
		obj.put("status_id", status_id);
		obj.put("pick_up_lat", pick_up_lat);
		obj.put("pick_up_lon", pick_up_lon);
		obj.put("deliver_location_lat", deliver_location_lat);
		obj.put("deliver_location_lon", deliver_location_lon);	
		obj.put("timestamp", timestamp);	
		return obj;
	}

	public static class TransactionItemBuilder {
		
		private String trans_id;
		private int station_id;
		private boolean start_device_is_robot;
		private boolean end_device_is_robot;
		public int status_id;
		public double pick_up_lat;
		public double pick_up_lon;
		public double deliver_location_lat;
		public double deliver_location_lon;
		public int timestamp;
		
		public void setTrans_id(String trans_id) {
			this.trans_id = trans_id;
		}

		public void setStation_id(int station_id) {
			this.station_id = station_id;
		}

		public void setStart_device_is_robot(boolean start_device_is_robot) {
			this.start_device_is_robot = start_device_is_robot;
		}

		public void setEnd_device_is_robot(boolean end_device_is_robot) {
			this.end_device_is_robot = end_device_is_robot;
		}

		public void setStatus_id(int status_id) {
			this.status_id = status_id;
		}

		public void setPick_up_lat(double pick_up_lat) {
			this.pick_up_lat = pick_up_lat;
		}

		public void setPick_up_lon(double pick_up_lon) {
			this.pick_up_lon = pick_up_lon;
		}

		public void setDeliver_location_lat(double deliver_location_lat) {
			this.deliver_location_lat = deliver_location_lat;
		}

		public void setDeliver_location_lon(double deliver_location_lon) {
			this.deliver_location_lon = deliver_location_lon;
		}

		public void setTimestamp(int timestamp) {
			this.timestamp = timestamp;
		}
		
		public TransactionItem build() {
			return new TransactionItem(this);
		}
	}

}
