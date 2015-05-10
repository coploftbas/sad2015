package my.rest.test.persistence;

import java.util.List;
import java.util.Set;

public class License {

	private int id;
	private String number;
	private String province;
	private String vehicleColor;
	private String vehicleBrand;
	private String vehicleType;
	private String ownerName;

	//private List<Ticket> tickets;

//	public List<Ticket> getTickets() {
//		return tickets;
//	}
//
//	public void setTickets(List<Ticket> tickets) {
//		this.tickets = tickets;
//	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public License() {
		super();
	}

	public License(String owner, String number, String province,
			String vehicleColor, String vehicleBrand, String vehicleType) {
		super();
		this.ownerName = owner;
		this.number = number;
		this.province = province;
		this.vehicleColor = vehicleColor;
		this.vehicleBrand = vehicleBrand;
		this.vehicleType = vehicleType;
	}

}
