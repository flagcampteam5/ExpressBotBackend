package entity;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;


public class Item {
	private String itemId;
	private Set<String> addressFrom;
	private Set<String> addressTo;
	private String parcelSender;
	private String parcelReceiver;
	private String station1;
	private String station2;
	private String bot1;
	private String bot2;
	private String phase;
	
	private Item(ItemBuilder builder) {
		this.itemId = builder.itemId;
		this.addressFrom = builder.addressFrom;
		this.addressTo = builder.addressTo;
		this.parcelSender = builder.parcelSender;
		this.parcelReceiver = builder.parcelReceiver;
		this.station1 = builder.station1;
		this.station2 = builder.station2;
		this.bot1 = builder.bot1;
		this.bot2 = builder.bot2;
		this.phase = builder.phase;
	}

	
	public String getItemId() {
		return itemId;
	}
	public Set<String> getAddressFrom() {
		return addressFrom;
	}
	public Set<String> getAddressTo() {
		return addressTo;
	}
	public String getParcelSender() {
		return parcelSender;
	}
	public String getParcelReceiver() {
		return parcelReceiver;
	}
	public String getStation1() {
		return station1;
	}
	public String getStation2() {
		return station2;
	}
	public String getBot1() {
		return bot1;
	}
	public String getBot2() {
		return bot2;
	}
	public String getPhase() {
		return phase;
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put("item_id", itemId);
		obj.put("addressFrom", new JSONArray(addressFrom));
		obj.put("addressTo", new JSONArray(addressTo));
		obj.put("parcelSender", parcelSender);
		obj.put("parcelReceiver", parcelReceiver);
		obj.put("station1", station1);
		obj.put("station2", station2);
		obj.put("bot1", bot1);
		obj.put("bot2", bot2);
		obj.put("phase", phase);
		return obj;
	}
	
	public static class ItemBuilder {
		private String itemId;
		private Set<String> addressFrom;
		private Set<String> addressTo;
		private String parcelSender;
		private String parcelReceiver;
		private String station1;
		private String station2;
		private String bot1;
		private String bot2;
		private String phase;
		
		public ItemBuilder setItemId(String itemId) {
			this.itemId = itemId;
			return this;
		}
		public ItemBuilder setAddressFrom(Set<String> addressFrom) {
			this.addressFrom = addressFrom;
			return this;
		}
		public ItemBuilder setAddressTo(Set<String> addressTo) {
			this.addressTo = addressTo;
			return this;
		}
		public ItemBuilder setParcelSender(String parcelSender) {
			this.parcelSender = parcelSender;
			return this;
		}
		public ItemBuilder setParcelReceiver(String parcelReceiver) {
			this.parcelReceiver = parcelReceiver;
			return this;
		}
		public ItemBuilder setStation1(String station1) {
			this.station1 = station1;
			return this;
		}
		public ItemBuilder setStation2(String station2) {
			this.station2 = station2;
			return this;
		}
		public ItemBuilder setBot1(String bot1) {
			this.bot1 = bot1;
			return this;
		}
		public ItemBuilder setBot2(String bot2) {
			this.bot2 = bot2;
			return this;
		}
		public ItemBuilder setPhase(String phase) {
			this.phase = phase;
			return this;
		}

		public Item build() {
			return new Item(this);
		}
	}
	
}
